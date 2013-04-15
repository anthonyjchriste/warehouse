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
  private Long primaryKey;
  
  @Required
  private String warehouseId;
  
  @Required
  private String name;
  
  @Required
  @OneToOne(cascade = CascadeType.ALL)
  private Address address;
  
  @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
  private List<StockItem> stockItems = new ArrayList<>();

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

  public Long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getWarehouseId() {
    return warehouseId;
  }

  public void setWarehouseId(String warehouseId) {
    this.warehouseId = warehouseId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<StockItem> getStockItems() {
    return stockItems;
  }

  public void setStockItems(List<StockItem> stockItems) {
    this.stockItems = stockItems;
  }
  
  
}
