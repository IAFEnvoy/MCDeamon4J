package iafenvoy.MCDeamon4J.Util.Exception;

public class NotAPluginFileException extends Exception {
  public NotAPluginFileException(String filePath) {
    super("The file " + filePath + " is not a plugin file.");
  }
}
