package iafenvoy.MCDeamon4J.Plugin;

import java.util.HashMap;

import iafenvoy.MCDeamon4J.Util.Identifier;
import iafenvoy.MCDeamon4J.Util.Interface.CommandCallback;
import iafenvoy.MCDeamon4J.Util.Interface.PlayerEvent;
import iafenvoy.MCDeamon4J.Util.Interface.ServerEvent;

public class Registry {
  public static HashMap<Identifier, Plugin> plugins = new HashMap<>();
  public static HashMap<String, CommandCallback> commandCallback = new HashMap<>();
  public static HashMap<Identifier, PlayerEvent> playerEvent = new HashMap<>();
  public static HashMap<Identifier, ServerEvent> serverEvent = new HashMap<>();

  public static void registerPlugin(Plugin object, Identifier id) {
    plugins.put(id, object);
  }

  public static void registerCommand(String command, CommandCallback callback) {
    commandCallback.put(command, callback);
  }

  public static void registerPlayerEvent(Identifier id, PlayerEvent event) {
    playerEvent.put(id, event);
  }

  public static void registerServerEvent(Identifier id, ServerEvent event) {
    serverEvent.put(id, event);
  }
}
