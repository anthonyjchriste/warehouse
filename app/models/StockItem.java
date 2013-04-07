package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
  public Long id;
  @ManyToOne(cascade = CascadeType.ALL)
  public Warehouse warehouse;
  @ManyToOne(cascade = CascadeType.ALL)
  public Product product;
  public long quantity;

  public StockItem(Warehouse warehouse, Product product, long quantity) {
    this.warehouse = warehouse;
    this.product = product;
    this.quantity = quantity;
  }

  public static Finder<Long, StockItem> find() {
    return new Finder<>(Long.class, StockItem.class);
  }
}
