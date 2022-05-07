package iafenvoy.MCDeamon4J.Util;

import jcurses.system.InputChar;
import jcurses.system.Toolkit;

public class KeyBoardInput {
  public static String content = "";
  public static boolean running = true;

  public static void startListen() {
    restoreInputText();
    running = true;
    new Thread(() -> {
      while (running) {
        InputChar c = Toolkit.readCharacter();
        if (c.isSpecialCode()) {
          if (c.getCode() == InputChar.KEY_BACKSPACE)
            if (content.length() > 0) {
              content = content.substring(0, content.length() - 1);
              System.out.print("\b \b");
            }
        } else {
          char ch = c.getCharacter();
          if (ch == '\n')
            System.out.println("get \\n!");
          if (ch >= ' ' && ch <= '~') {
            content += ch;
            System.out.print(ch);
          }
        }
      }
    }).start();
  }

  public static void removeInputText() {
    // remove text in console by \b
    System.out.print("\b".repeat(content.length() + 2));
  }

  public static void restoreInputText() {
    // restore text in console
    System.out.print(" >" + content);
  }
}