package controllers;

import static play.data.Form.form;
import java.util.List;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Warehouse extends Controller {
  public static Result index() {
    List<models.Warehouse> warehouses = models.Warehouse.find().findList();
    return ok(warehouses.isEmpty() ? "No warehouses" : warehouses.toString());
  }
  
  public static Result details(String warehouseId) {
    models.Warehouse warehouse = models.Warehouse.find().where().eq("warehouseId", warehouseId).findUnique();
    return (warehouse == null) ? notFound("No warehouse found") : ok(warehouse.toString());
  }
  
  public static Result newWarehouse() {
    Form<models.Warehouse> warehouseForm = form(models.Warehouse.class).bindFromRequest();
    
    if(warehouseForm.hasErrors()) {
      return badRequest("Warehouse ID, name, and address fields are required.");
    }
    
    models.Warehouse warehouse = warehouseForm.get();
    warehouse.save();
    return ok(warehouse.toString());
  }
  
  public static Result delete(String warehouseId) {
    models.Warehouse warehouse = models.Warehouse.find().where().eq("warehouseId", warehouseId).findUnique();
    
    if(warehouse != null) {
      warehouse.delete();
    }
    return ok();
  }
}
