package iafenvoy.MCDeamon4J.Plugin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import iafenvoy.MCDeamon4J.Config.Config;
import iafenvoy.MCDeamon4J.Util.Identifier;
import iafenvoy.MCDeamon4J.Util.Exception.InvalidPluginException;
import iafenvoy.MCDeamon4J.Util.Exception.NotAPluginFileException;

public class Plugin {
  private final Identifier id;
  private final String author, version;
  private PluginInitializer initializer;
  private final Config config;
  public boolean toLoad = true;

  public Plugin(String path) throws IOException, NotAPluginFileException, InvalidPluginException {
    // try to read mcd.plugin.json file in the root of another jar file
    ZipFile pFile = new ZipFile(path);
    ZipEntry entry = pFile.getEntry("mcd.plugin.json");
    if (entry == null) {
      pFile.close();
      throw new NotAPluginFileException(path);
    }
    JarInputStream jis = new JarInputStream(pFile.getInputStream(entry));
    // read mcd.plugin.json file
    StringBuilder sb = new StringBuilder();
    int c;
    while ((c = jis.read()) != -1)
      sb.append((char) c);
    jis.close();
    pFile.close();
    // parse mcd.plugin.json file using gson
    JsonObject ele = JsonParser.parseString(sb.toString()).getAsJsonObject();
    // get plugin id and name
    if (!ele.has("id") || !ele.has("name"))
      throw new InvalidPluginException(path, "id or name");
    id = new Identifier(ele.get("name").getAsString(), ele.get("id").getAsString());
    // get plugin author and version
    author = ele.has("author") ? ele.get("author").getAsString() : "Unknown";
    version = ele.has("version") ? ele.get("version").getAsString() : "Unknown";
    // get plugin initializer
    if (!ele.has("entrypoint"))
      throw new InvalidPluginException(path, "entrypoint");
    String entrypoint = ele.get("entrypoint").getAsString();
    try {
      initializer = (PluginInitializer) Class.forName(entrypoint).getConstructor().newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    // get config path
    String configPath = "./config/" + id.getId() + ".json";
    if (ele.has("configpath"))
      configPath = ele.get("configpath").getAsString();
    config = new Config(configPath, id.getName());
  }

  public Identifier getID() {
    return id;
  }

  public String getAuthor() {
    return author;
  }

  public String getVersion() {
    return version;
  }

  public PluginInitializer getInitializer() {
    return initializer;
  }

  public Config getConfig() {
    return config;
  }
}
