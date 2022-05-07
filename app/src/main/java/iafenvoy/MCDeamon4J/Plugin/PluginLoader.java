package iafenvoy.MCDeamon4J.Plugin;

import java.io.File;
import java.io.IOException;

import iafenvoy.MCDeamon4J.Logger.Logger;
import iafenvoy.MCDeamon4J.Util.Exception.InvalidPluginException;
import iafenvoy.MCDeamon4J.Util.Exception.NotAPluginFileException;

public class PluginLoader {
  public static void loadPlugins(File folder) {
    // read all jar file in plugin folder, create Plugin object and register them
    File[] files = folder.listFiles();
    for (File file : files) {
      if (file.getName().endsWith(".jar")) {
        try {
          Plugin plugin = new Plugin(file.getAbsolutePath());
          Registry.registerPlugin(plugin, plugin.getID());
        } catch (IOException e) {
          Logger.error("Fail to load a plugin: " + file.getName());
          Logger.error("An IOException occured :");
          e.printStackTrace();
        } catch (NotAPluginFileException e) {
          Logger.warn("File " + file.getName() + " is not a plugin file, ignore it.");
        } catch (InvalidPluginException e) {
          Logger.error("File " + file.getName() + " has a invalid mcd.json file, the plugin will not be loaded.");
        }
      }
    }
  }

  public static void loadPluginsConfig() {
    for (Plugin plugin : Registry.plugins.values())
      if (plugin.toLoad)
        if (!plugin.getConfig().load()) {
          Logger.error("Fail to load config for plugin " + plugin.getID());
          Logger.error("The plugin will not be loaded");
          plugin.toLoad = false;
        }
  }

  public static void initializePlugins() {
    for (Plugin plugin : Registry.plugins.values())
      if (plugin.toLoad)
        plugin.getInitializer().onLoad();
  }
}
