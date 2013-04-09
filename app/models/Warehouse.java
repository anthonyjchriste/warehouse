package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import play.data.validation.Constraints.Required;
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
  public Long primaryKey;
  
  @Required
  public String warehouseId;
  
  @Required
  public String name;
  
  @Required
  @OneToOne(cascade = CascadeType.ALL)
  public Address address;
  
  @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
  public List<StockItem> stockItems = new ArrayList<>();

  public Warehouse(String warehouseId, String name, Address address) {
    this.warehouseId = warehouseId;
    this.name = name;
    this.address = address;
  }

  public static Finder<Long, Warehouse> find() {
    return new Finder<>(Long.class, Warehouse.class);
  }
  
  @Override
  public String toString() {
    return String.format("[Warehouse %s %s]", this.warehouseId, this.name);
  }
}
