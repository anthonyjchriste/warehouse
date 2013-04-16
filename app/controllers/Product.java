package controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import play.data.Form;
import play.data.format.Formatters;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.form;

public class Product extends Controller {
  public static Result index() {
    List<models.Product> products = models.Product.find().findList();
    return ok(products.isEmpty() ? "No products" : products.toString());
  }
  
  public static Result details(String productId) {
    models.Product product = models.Product.find().where().eq("productId", productId).findUnique();
    return (product == null) ? notFound("No product found") : ok(product.toString());
  }
  
  public static Result newProduct() {
    
    Formatters.register(models.Product.class, new Formatters.SimpleFormatter<models.Product>() {
      @Override
      public models.Product parse(String text, Locale locale) throws ParseException {
        models.Product product = models.Product.find().where().eq("productId", text).findUnique();
        if (product == null) {
          throw new ParseException("Could not find matching Product", 0);
        }
        return product;
      }

      @Override
      public String print(models.Product t, Locale locale) {
        return t.getProductId();
      }
    });
    
    Form<models.Product> productForm = form(models.Product.class).bindFromRequest();
    
    if(productForm.hasErrors()) {
      return badRequest("Product ID and name is required");
    }
    
    models.Product product = productForm.get();
    product.save();
    return ok(product.toString());
  }
  
  public static Result delete(String productId) {
    models.Product product = models.Product.find().where().eq("productId", productId).findUnique();
    
    if(product != null) {
      product.delete();
    }
    
    return ok();
  }
}
