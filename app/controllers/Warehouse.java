package controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import play.data.Form;
import play.data.format.Formatters;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.form;

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
    
    Formatters.register(models.Warehouse.class, new Formatters.SimpleFormatter<models.Warehouse>() {
      @Override
      public models.Warehouse parse(String text, Locale locale) throws ParseException {
        models.Warehouse warehouse = models.Warehouse.find().where().eq("warehouseId", text).findUnique();
        if (warehouse == null) {
          throw new ParseException("Could not find matching Warehouse", 0);
        }
        return warehouse;
      }

      @Override
      public String print(models.Warehouse t, Locale locale) {
        return t.getWarehouseId();
      }
    });
    
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
