package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import play.db.ebean.Model;

/**
 * Models a Warehouse entity.
 * 
 * @author Anthony Christe
 */
@Entity
public class Warehouse extends Model {
  private static final long serialVersionUID = 4112985926784791592L;

  @Id
  public Long id;
  public String name;
  @OneToOne(cascade = CascadeType.ALL)
  public Address address;
  @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
  public List<StockItem> stockItems = new ArrayList<>();

  public Warehouse(String name, Address address) {
    this.name = name;
    this.address = address;
  }

  public static Finder<Long, Warehouse> find() {
    return new Finder<>(Long.class, Warehouse.class);
  }
}
