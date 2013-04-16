package util;

import play.db.ebean.Model.Finder;

public interface Bindable<T> {
  public Class<T> getClassType();
}
