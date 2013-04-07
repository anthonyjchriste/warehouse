package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
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
  public Long id;
  public String name;
  public String description;
  @ManyToMany(cascade = CascadeType.ALL)
  public List<Tag> tags = new ArrayList<>();
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  public List<StockItem> stockItems = new ArrayList<>();

  public Product(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public static Finder<Long, Product> find() {
    return new Finder<>(Long.class, Product.class);
  }
}
