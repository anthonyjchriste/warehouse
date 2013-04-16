package util;

import java.text.ParseException;
import java.util.Locale;
import play.data.format.Formatters;
import play.db.ebean.Model;

public class CustomFieldBinder {
  
  public static <T> void bindById(Bindable<T> bindable) {
    Class<T> clazz = bindable.getClassType();
    
    Formatters.register(clazz, new Formatters.SimpleFormatter<T>() {
      @Override
      public T parse(String text, Locale locale) throws ParseException {
        T result = ((Bindable) clazz).getIdString();
      }

      @Override
      public String print(T t, Locale locale) {
        // TODO Auto-generated method stub
        return null;
      }
    });
  }
}
