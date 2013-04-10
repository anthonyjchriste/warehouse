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
import models.Tag;
import models.StockItem;
import models.Address;
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
  public void testProductControllers() {
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
  }
}
