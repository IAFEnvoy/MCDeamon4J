package iafenvoy.MCDeamon4J.Util.Interface;

import java.util.regex.Pattern;

public interface CustomCallback {
  public Pattern getPattern();

  public boolean execute(String command);
}
