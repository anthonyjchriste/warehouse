package controllers;

import java.util.List;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.form;

public class Address extends Controller {
  public static Result index() {
    List<models.Address> addresss = models.Address.find().findList();
    return ok(addresss.isEmpty() ? "No addresss" : addresss.toString());
  }
  
  public static Result details(String addressId) {
    models.Address address = models.Address.find().where().eq("addressId", addressId).findUnique();
    return (address == null) ? notFound("No address found") : ok(address.toString());
  }
  
  public static Result newAddress() {
    Form<models.Address> addressForm = form(models.Address.class).bindFromRequest();
    
    if(addressForm.hasErrors()) {
      return badRequest("Address ID and address fields are required.");
    }
    
    models.Address address = addressForm.get();
    address.save();
    return ok(address.toString());
  }
  
  public static Result delete(String addressId) {
    models.Address address = models.Address.find().where().eq("addressId", addressId).findUnique();
    
    if(address != null) {
      address.delete();
    }
    return ok();
  }
}
