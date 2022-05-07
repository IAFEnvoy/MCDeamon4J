package iafenvoy.MCDeamon4J.Logger;

import iafenvoy.MCDeamon4J.Util.KeyBoardInput;

public class Logger {
  public static void log(String msg) {
    print("\033[34m[Server]\033[0m" + msg);
  }

  public static void info(String msg) {
    print("\033[32m[MCD4J/Info]\033[0m       " + msg);
  }

  public static void warn(String msg) {
    print("\033[33m[MCD4J/Warn]\033[0m       " + msg);
  }

  public static void error(String msg) {
    print("\033[31m[MCD4J/Error]\033[0m      " + msg);
  }

  private static void print(String msg) {
    KeyBoardInput.removeInputText();
    System.out.println(msg);
    KeyBoardInput.restoreInputText();
  }
}
