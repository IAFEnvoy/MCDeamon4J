package iafenvoy.MCDeamon4J.Plugin.EventRunner;

import iafenvoy.MCDeamon4J.Plugin.Registry;
import iafenvoy.MCDeamon4J.Util.Interface.ServerEvent;

public class ServerEventRunner {
  public static boolean serverStart = false;

  public static void onStartServer() {
    if (serverStart)
      return;
    serverStart = true;
    for (ServerEvent event : Registry.serverEvent.values())
      event.onServerStart();
  }

  public static void onStopServer() {
    if (!serverStart)
      return;
    serverStart = false;
    for (ServerEvent event : Registry.serverEvent.values())
      event.onServerStop();
  }
}
