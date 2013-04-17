package tests; 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.status;
import static play.test.Helpers.stop;
import java.util.HashMap;
import java.util.Map;
import models.Product;
import models.StockItem;
import models.Tag;
import models.Warehouse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;

public class ControllerTest {
  private FakeApplication application;
  
  @Before
  public void startApp() {
    application = fakeApplication(inMemoryDatabase());
    start(application);
  }
  
  @After
  public void stopApp() {
    stop(application);
  }
  
  @Test
  public void testProductController() {
    // Test GET /product on an empty database
    Result result = callAction(controllers.routes.ref.Product.index());
    assertTrue("Empty products", contentAsString(result).contains("No products"));
    
    // Test GET /product on a database containing a single product.
    String productId = "Product-01";
    Product product = new Product(productId, "French Press", "Coffee Maker");
    product.save();
    result = callAction(controllers.routes.ref.Product.index());
    assertTrue("One Product", contentAsString(result).contains(productId));
    
    // Test GET /product/Product-01
    result = callAction(controllers.routes.ref.Product.details(productId));
    assertTrue("Product detail", contentAsString(result).contains(productId));
    
    // Test GET /product/BadProductId and make sure we get a 404
    result = callAction(controllers.routes.ref.Product.details("BadProductId"));
    assertEquals("Product detail (bad)", NOT_FOUND, status(result));
    
    // Test POST /products (with simulated, valid from data).
    Map<String, String> productData = new HashMap<>();
    productData.put("productId", "Product-02");
    productData.put("name", "Baby Gaggia");
    productData.put("description", "Expresso machine");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(productData);
    result = callAction(controllers.routes.ref.Product.newProduct(), request);
    assertEquals("Create new product", OK, status(result));
    
    // Test POST /products (with simulated, invalid form data).
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Product.newProduct(), request);
    assertEquals("Create bad product fails", BAD_REQUEST, status(result));
    
    // Test DELETE /products/Product-01 (a valid ProductId).
    result = callAction(controllers.routes.ref.Product.delete(productId));
    assertEquals("Delete current product OK", OK, status(result));
    result = callAction(controllers.routes.ref.Product.details(productId));
    assertEquals("Deleted product gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Product.delete(productId));
    assertEquals("Delete missing product also OK", OK, status(result));
  }
  
  @Test
  public void testTagController() {
    // Test GET /tags on an empty database.
    Result result = callAction(controllers.routes.ref.Tag.index());
    assertTrue("Empty tags", contentAsString(result).contains("No Tags"));
    
    // Test GET /tag on a database containing a single Tag.
    String tagId = "Tag-01";
    Tag tag = new Tag(tagId);
    tag.save();
    result = callAction(controllers.routes.ref.Tag.index());
    assertTrue("One tag", contentAsString(result).contains(tagId));
    
    // Test GET /tags/Tag-01
    result = callAction(controllers.routes.ref.Tag.details(tagId));
    assertTrue("Tag detail", contentAsString(result).contains(tagId));
    
    // Test GET /tags/BadTagId and make sure we get a 404.
    result = callAction(controllers.routes.ref.Tag.details("BadTagId"));
    assertEquals("Tag detail (bad)", NOT_FOUND, status(result));
    
    // Test POST /tags (with simulated, valid form data).
    Map<String, String> tagData = new HashMap<>();
    tagData.put("tagId", "Tag-02");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(tagData);
    result = callAction(controllers.routes.ref.Tag.newTag(), request);
    assertEquals("Create new tag", OK, status(result));
    
    // Test POST /tags (with invalid tag: tags cannot be named "Tag").
    // Illustrates use of the validate() method in models.Tag.
    request = fakeRequest();
    tagData.put("tagId", "Tag");
    request.withFormUrlEncodedBody(tagData);
    result = callAction(controllers.routes.ref.Tag.newTag(), request);
    assertEquals("Create bad tag fails", BAD_REQUEST, status(result));
    
    // Test DELETE /tags/Tag-01 (a valid TagID).
    result = callAction(controllers.routes.ref.Tag.delete(tagId));
    assertEquals("Delete current tag OK", OK, status(result));
    result = callAction(controllers.routes.ref.Tag.details(tagId));
    assertEquals("Deleted tag gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Tag.delete(tagId));
    assertEquals("Delete missing tag also OK", OK, status(result));
  }
  
  @Test
  public void testStockItemController() {
    // Test GET /tags on an empty database.
    Result result = callAction(controllers.routes.ref.StockItem.index());
    assertTrue("Empty", contentAsString(result).contains("No stockItems"));
    
    // Need some extra objects in order to create our StockItem.
    Warehouse warehouse = new Warehouse("Warehouse-01", "name", "address");
    Product product = new Product("Product-01", "produce", "description");
    warehouse.save();
    product.save();
    
    // Test GET /stockitem on a database containing a single StockItem.
    String stockItemId = "StockItem-01";
    StockItem stockItem = new StockItem(stockItemId, warehouse, product, 1L);
    stockItem.save();
    result = callAction(controllers.routes.ref.StockItem.index());
    assertTrue("One StockItem", contentAsString(result).contains(stockItemId));
    
    // Test GET /stockitems/StockItem-01
    result = callAction(controllers.routes.ref.StockItem.details(stockItemId));
    assertTrue("StockItem detail", contentAsString(result).contains(stockItemId));
    
    // Test GET /stockitems/BadStockItemId and make sure we get a 404.
    result = callAction(controllers.routes.ref.StockItem.details("BadStockItemId"));
    assertEquals("StockItem detail (bad)", NOT_FOUND, status(result));

    // Test POST /stockitems (with simulated, valid form data).
    warehouse = new Warehouse("Warehouse-02", "name", "address");
    product = new Product("Product-02", "produce", "description");
    warehouse.save();
    product.save();
    Map<String, String> stockItemData = new HashMap<>();
    stockItemData.put("stockItemId", "StockItem-02");
    stockItemData.put("warehouse", "Warehouse-02");
    stockItemData.put("product", "Product-02");
    stockItemData.put("amount", "1");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(stockItemData);
    result = callAction(controllers.routes.ref.StockItem.newStockItem(), request);
    assertEquals("Create new StockItem", OK, status(result));
    
    
    // Test POST /stockitems (with invalid stockitem).
    request = fakeRequest();
    result = callAction(controllers.routes.ref.StockItem.newStockItem(), request);
    assertEquals("Create bad StockItem fails", BAD_REQUEST, status(result));
    
    
    // Test DELETE /stockitems/StockItem-01 (a valid StockItemId).
    result = callAction(controllers.routes.ref.StockItem.delete(stockItemId));
    assertEquals("Delete current StockItem OK", OK, status(result));
    result = callAction(controllers.routes.ref.StockItem.details(stockItemId));
    assertEquals("Deleted StockItem gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.StockItem.delete(stockItemId));
    assertEquals("Delete missing StockItem also OK", OK, status(result));
  }
  
  @Test
  public void testWarehouseController() {
    // Test GET /warehouses on an empty database.
    Result result = callAction(controllers.routes.ref.Warehouse.index());
    assertTrue("Empty", contentAsString(result).contains("No warehouses"));
    
    // Test GET /warehouse on a database containing a single warehouse.
    String warehouseId = "Warehouse-01";
    Warehouse warehouse = new Warehouse(warehouseId, "name", "address");
    warehouse.save();
    result = callAction(controllers.routes.ref.Warehouse.index());
    assertTrue("One warehouse", contentAsString(result).contains(warehouseId));
    
    // Test GET /warehouses/Warehouse-01
    result = callAction(controllers.routes.ref.Warehouse.details(warehouseId));
    assertTrue("Warehouse detail", contentAsString(result).contains(warehouseId));
    
    // Test GET /warehouses/BadWarehouseId and make sure we get a 404.
    result = callAction(controllers.routes.ref.Warehouse.details("BadWarehouseId"));
    assertEquals("Warehouse detail (bad)", NOT_FOUND, status(result));

    // Test POST /warehouses (with simulated, valid form data).
    Map<String, String> warehouseData = new HashMap<>();
    warehouseData.put("warehouseId", "Warehouse-02");
    warehouseData.put("name", "name2");
    // TODO: Eliminate addressField. How to bind string to address when handled by constructor
    warehouseData.put("addressField", "address");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(warehouseData);
    result = callAction(controllers.routes.ref.Warehouse.newWarehouse(), request);
    assertEquals("Create new warehouse", OK, status(result));
    
    
    // Test POST /warehouses (with invalid tag).
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Warehouse.newWarehouse(), request);
    assertEquals("Create bad warehouse fails", BAD_REQUEST, status(result));
    
    
    // Test DELETE /warehouses/Warehouse-01 (a valid warehouseId).
    result = callAction(controllers.routes.ref.Warehouse.delete(warehouseId));
    assertEquals("Delete current warehouse OK", OK, status(result));
    result = callAction(controllers.routes.ref.Warehouse.details(warehouseId));
    assertEquals("Deleted warehouse gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Warehouse.delete(warehouseId));
    assertEquals("Delete missing warehouse also OK", OK, status(result));
  }
  
}
