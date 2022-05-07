package iafenvoy.MCDeamon4J.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import iafenvoy.MCDeamon4J.Logger.Logger;

public class Config {
  private final HashMap<String, String> values = new HashMap<>();
  private final String path, pName;

  public Config(String path, String pName) {
    this.path = path;
    this.pName = pName;
  }

  public boolean load() {
    try {
      // read file
      File file = new File(path);
      Long fileLength = file.length();
      InputStream stream = new FileInputStream(file);
      byte[] bytes = new byte[fileLength.intValue()];
      stream.read(bytes);
      stream.close();
      // get values
      JsonObject config = JsonParser.parseString(new String(bytes)).getAsJsonObject();
      for (Map.Entry<String, JsonElement> entry : config.entrySet())
        values.put(entry.getKey(), entry.getValue().getAsString());
    } catch (FileNotFoundException e) {
      Logger.warn("Config file for " + pName + " not found, creating new one.");
    } catch (IOException e) {
      Logger.error("An IOException occur : " + e.getMessage());
      Logger.error(pName + " will not be loaded");
      return false;
    } catch (JsonParseException e) {
      Logger.error("Config file for " + pName + " is not valid");
      Logger.error(pName + " will not be loaded");
      return false;
    }
    return true;
  }

  public boolean save(){
    //TODO: save config
    return false;
  }

  public String getValue(String key) {
    return values.get(key);
  }

  public void setValue(String key, String value) {
    values.put(key, value);
  }
}
