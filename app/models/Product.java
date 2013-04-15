package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * Models a Product entity.
 * 
 * @author Anthony Christe
 */
@Entity
public class Product extends Model {
  private static final long serialVersionUID = -1715163072748363949L;

  @Id
  private Long primaryKey;
  
  @Required
  private String productId;
  
  @Required
  private String name;
  
  private String description;
  
  @ManyToMany(cascade = CascadeType.ALL)
  private List<Tag> tags = new ArrayList<>();
  
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<StockItem> stockItems = new ArrayList<>();

  public Product(String productId, String name, String description) {
    this.productId = productId;
    this.name = name;
    this.description = description;
  }

  public static Finder<Long, Product> find() {
    return new Finder<>(Long.class, Product.class);
  }
  
  @Override
  public String toString() {
    return String.format("[Product %s %s %s]", this.productId, this.name, this.description);
  }

  public Long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public List<StockItem> getStockItems() {
    return stockItems;
  }

  public void setStockItems(List<StockItem> stockItems) {
    this.stockItems = stockItems;
  }
  
  
}
