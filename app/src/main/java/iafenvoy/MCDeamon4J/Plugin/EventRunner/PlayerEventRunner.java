package iafenvoy.MCDeamon4J.Plugin.EventRunner;

import java.util.ArrayList;
import java.util.List;

import iafenvoy.MCDeamon4J.Plugin.Registry;
import iafenvoy.MCDeamon4J.Util.Interface.PlayerEvent;

public class PlayerEventRunner {
  private static List<String> playerList = new ArrayList<>();

  public static void onPlayerJoin(String player) {
    if (!ServerEventRunner.serverStart)
      return;
    playerList.add(player);
    for (PlayerEvent event : Registry.playerEvent.values())
      event.onPlayerJoin(player);
  }

  public static void onPlayerQuit(String player) {
    if (!ServerEventRunner.serverStart)
      return;
    playerList.remove(player);
    for (PlayerEvent event : Registry.playerEvent.values())
      event.onPlayerQuit(player);
  }

  public static void onPlayerChat(String player, String message) {
    if (!ServerEventRunner.serverStart)
      return;
    for (PlayerEvent event : Registry.playerEvent.values())
      event.onPlayerChat(player, message);
  }
}
