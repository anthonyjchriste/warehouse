package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import play.db.ebean.Model;

/**
 * Models a Tag entity.
 * 
 * @author Anthony Christe
 */
@Entity
public class Tag extends Model {
  private static final long serialVersionUID = -8417172117934560476L;

  @Id
  public Long primaryKey;
  
  public String tagId;
  public String name;
  @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
  public List<Product> products = new ArrayList<>();

  public Tag(String tagId) {
    this.tagId = tagId;
  }

  public String validate() {
    return ("Tag".equals(this.tagId)) ? "Invalid Tag Name" : null;
  }
  
  public static Finder<Long, Tag> find() {
    return new Finder<>(Long.class, Tag.class);
  }
  
  @Override
  public String toString() {
    return String.format("[Tag %s]", this.tagId);
  }
}