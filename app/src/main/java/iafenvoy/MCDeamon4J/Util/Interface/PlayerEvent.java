package iafenvoy.MCDeamon4J.Util.Interface;

public interface PlayerEvent {
  public void onPlayerJoin(String player);

  public void onPlayerQuit(String player);

  public void onPlayerChat(String player, String message);
}
