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
  private Long primaryKey;
  
  @Required
  private String addressId;
  
  @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
  private Warehouse warehouse;
  
  @Required
  private String address;

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

  public Long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
  
  

}
