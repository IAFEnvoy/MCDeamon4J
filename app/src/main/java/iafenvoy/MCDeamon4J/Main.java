package iafenvoy.MCDeamon4J;

import java.io.File;

import iafenvoy.MCDeamon4J.Config.Config;
import iafenvoy.MCDeamon4J.Logger.Logger;
import iafenvoy.MCDeamon4J.Plugin.PluginLoader;
import iafenvoy.MCDeamon4J.Plugin.Registry;
import iafenvoy.MCDeamon4J.Util.KeyBoardInput;

public class Main {
  static String startCommand = "java -Xmx2G -jar fabric-server-launch.jar nogui";
  static String serverPath = "C:\\Users\\allen\\Downloads\\1.16.5 fabric";

  public static void main(String[] args) {
    KeyBoardInput.startListen();
    Logger.info("Start loading Minecraft Deamon For Java");
    Logger.info("Loading config...");
    Config config = new Config("./config/mcd4j.json", "MCDeamon4J");
    if (config.load())
      Logger.info("Succeeded loading config.");
    else {
      Logger.error("An error occurred while loading config.");
      Logger.error("Stop loading");
      Runtime.getRuntime().exit(1);
    }

    // load plugins
    Logger.info("Loading plugins...");
    String pluginPath = config.getValue("pluginPath");
    if (pluginPath == null) {
      Logger.warn("Plugin path not found, using default path.");
      pluginPath = "./plugins";
    }
    File pluginFolder = new File(pluginPath);
    if (!pluginFolder.exists()) {
      Logger.warn("Plugin folder not found, create it.");
      pluginFolder.mkdirs();
    }
    PluginLoader.loadPlugins(pluginFolder);
    Logger.info("Loading plugins' config...");
    PluginLoader.loadPluginsConfig();
    Logger.info("Initializing plugins...");
    PluginLoader.initializePlugins();
    Logger.info("Successfully load " + Registry.plugins.size() + " plugins.");

    ProcessBuilder builder = new ProcessBuilder(startCommand.split(" "));
    // String serverPath = config.getValue("serverPath");
    // if (serverPath == null)
    // serverPath = "./server/";
    builder.directory(new File(serverPath));
    Logger.info("Redirect server path to " + serverPath);
    builder.redirectErrorStream(false);

    Logger.info("Server will start in 5 seconds...");
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Server.runServer(builder);
  }
}
