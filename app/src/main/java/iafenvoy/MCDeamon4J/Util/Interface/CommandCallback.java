package iafenvoy.MCDeamon4J.Util.Interface;

public interface CommandCallback {
  public boolean execute(String command);

  public String getRootCommand();
}
