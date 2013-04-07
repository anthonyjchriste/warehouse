package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
  public Long id;
  @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
  public Warehouse warehouse;
  public String address;

  public Address(String address) {
    this.address = address;
  }

  public static Finder<Long, Address> find() {
    return new Finder<>(Long.class, Address.class);
  }

}
