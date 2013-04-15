package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * Models a StockItem entity.
 * 
 * @author Anthony Christe
 */
@Entity
public class StockItem extends Model {
  private static final long serialVersionUID = -7681398919929300442L;

  @Id
  private Long primaryKey;
  
  @Required
  private String stockItemId;
  
  @Required
  @ManyToOne(cascade = CascadeType.ALL)
  private Warehouse warehouse;
  
  @Required
  @ManyToOne(cascade = CascadeType.ALL)
  private Product product;
  
  @Required
  private long quantity;

  public StockItem(String stockItemId, Warehouse warehouse, Product product, long quantity) {
    this.stockItemId = stockItemId;
    this.warehouse = warehouse;
    this.product = product;
    this.quantity = quantity;
  }

  public static Finder<Long, StockItem> find() {
    return new Finder<>(Long.class, StockItem.class);
  }
  
  @Override
  public String toString() {
    return String.format("[StockItem %s %d]", this.stockItemId, this.quantity);
  }

  public Long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getStockItemId() {
    return stockItemId;
  }

  public void setStockItemId(String stockItemId) {
    this.stockItemId = stockItemId;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public long getQuantity() {
    return quantity;
  }

  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }
  
  
}
