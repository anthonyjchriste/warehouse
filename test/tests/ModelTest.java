package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import java.util.List;
import models.Address;
import models.Product;
import models.StockItem;
import models.Tag;
import models.Warehouse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.FakeApplication;

public class ModelTest {
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
  public void testModel() {
    // Create 1 tag that's associated with 1 StockItem for 1 Product
    Tag tag = new Tag("Tag");
    Product product = new Product("productId", "Product", "Description");
    product.getTags().add(tag);
    tag.getProducts().add(product);

    // Create 1 address that's associated with 1 Warehouse
    Address address = new Address("addressId", "Address");
    Warehouse warehouse = new Warehouse("warehouseId", "Warehouse", address);
    address.setWarehouse(warehouse);
    StockItem stockItem = new StockItem("stockItemId", warehouse, product, 100);
    warehouse.getStockItems().add(stockItem);

    warehouse.save();
    tag.save();
    product.save();
    address.save();
    stockItem.save();

    List<Warehouse> warehouses = Warehouse.find().findList();
    List<Tag> tags = Tag.find().findList();
    List<Product> products = Product.find().findList();
    List<Address> addresses = Address.find().findList();
    List<StockItem> stockItems = StockItem.find().findList();

    assertEquals("Checking warehouse", warehouses.size(), 1);
    assertEquals("Checking tags", tags.size(), 1);
    assertEquals("Checking products", products.size(), 1);
    assertEquals("Checking addresses", addresses.size(), 1);
    assertEquals("Checking stockItems", stockItems.size(), 1);

    // Check that we've recovered all relationships
    assertEquals("Warehouse-StockItem", warehouses.get(0).getStockItems().get(0), stockItems.get(0));
    assertEquals("StockItem-Warehouse", stockItems.get(0).getWarehouse(), warehouses.get(0));
    assertEquals("Warehouse-Address", warehouses.get(0).getAddress(), addresses.get(0));
    assertEquals("Address-Warehouse", addresses.get(0), warehouses.get(0).getAddress());
    assertEquals("Product-StockItem", products.get(0).getStockItems().get(0), stockItems.get(0));
    assertEquals("StockItem-Product", stockItems.get(0).getProduct(), products.get(0));
    assertEquals("Product-Tag", products.get(0).getTags().get(0), tags.get(0));
    assertEquals("Tag-Product", tags.get(0).getProducts().get(0), products.get(0));

    product.getTags().clear();
    product.save();
    assertTrue("Previously retrieved product still has tag", !products.get(0).getTags().isEmpty());
    assertTrue("Fresh Product has no Tag", Product.find().findList().get(0).getTags().isEmpty());
    assertTrue("Fresh Tag has no Products", Tag.find().findList().get(0).getProducts().isEmpty());
    tag.delete();
    assertTrue("No more Tags in database", Tag.find().findList().isEmpty());
  }

}
