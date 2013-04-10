package controllers;

import java.util.List;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.form;

public class StockItem extends Controller {
  public static Result index() {
    List<models.StockItem> stockItems = models.StockItem.find().findList();
    return ok(stockItems.isEmpty() ? "No stockItems" : stockItems.toString());
  }
  
  public static Result details(String stockItemId) {
    models.StockItem stockItem = models.StockItem.find().where().eq("stockItemId", stockItemId).findUnique();
    return (stockItem == null) ? notFound("No stockItem found") : ok(stockItem.toString());
  }
  
  public static Result newStockItem() {
    Form<models.StockItem> stockItemForm = form(models.StockItem.class).bindFromRequest();
    
    if(stockItemForm.hasErrors()) {
      return badRequest("Stock Item ID, Warehouse, Product, and Quanity fields are required.");
    }
    
    models.StockItem stockItem = stockItemForm.get();
    stockItem.save();
    return ok(stockItem.toString());
  }
  
  public static Result delete(String stockItemId) {
    models.StockItem stockItem = models.StockItem.find().where().eq("stockItemId", stockItemId).findUnique();
    
    if(stockItem != null) {
      stockItem.delete();
    }
    
    return ok();
  }
}
