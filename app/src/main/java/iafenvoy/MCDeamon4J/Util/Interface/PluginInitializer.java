package iafenvoy.MCDeamon4J.Util.Interface;

public interface PluginInitializer {
  public void onPreload();

  public void onLoad();

  public String getVersion();

  public String getID();

  public String getName();
}
