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
  public Long primaryKey;
  
  @Required
  public String stockItemId;
  
  @Required
  @ManyToOne(cascade = CascadeType.ALL)
  public Warehouse warehouse;
  
  @Required
  @ManyToOne(cascade = CascadeType.ALL)
  public Product product;
  
  @Required
  public long quantity;

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
}
