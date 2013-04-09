package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * Models an Address entity.
 * 
 * @author Anthony Christe
 */
@Entity
public class Address extends Model {
  private static final long serialVersionUID = 5297816712253544981L;

  @Id
  public Long primaryKey;
  
  @Required
  String addressId;
  
  @Required
  @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
  public Warehouse warehouse;
  
  @Required
  public String address;

  public Address(String addressId, String address) {
    this.addressId = addressId;
    this.address = address;
  }

  public static Finder<Long, Address> find() {
    return new Finder<>(Long.class, Address.class);
  }
  
  @Override
  public String toString() {
    return String.format("[Address %s %s]", this.addressId, this.address);
  }

}
