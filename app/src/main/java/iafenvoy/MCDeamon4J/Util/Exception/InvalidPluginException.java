package iafenvoy.MCDeamon4J.Util.Exception;

public class InvalidPluginException extends Exception {
  public InvalidPluginException(String filePath, String missingField) {
    super("File " + filePath + " has a invalid mcd.plugin.json.\nMissing field : " + missingField);
  }
}
