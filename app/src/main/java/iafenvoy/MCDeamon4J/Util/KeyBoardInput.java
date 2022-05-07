package iafenvoy.MCDeamon4J.Util;

public class KeyBoardInput {
  public static String content = "";
  public static boolean running = true;

  public static void startListen() {
    running = true;
    new Thread(() -> {
      while (running) {
        try {
          byte b[] = new byte[1];
          System.in.read(b);
          char c = (char) b[0];
          System.out.println("hit :" + c);//Here to output key input
          if (c == '\b') {
            if (content.length() > 0) {
              content = content.substring(0, content.length() - 1);
            }
          } else if (c == '\r') {
            //TODO
          } else if (c == '\n') {
            //TODO
          } else {
            content += (char) c;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  public static void removeInputText() {
    // remove text in console by \b
    System.out.print("\b".repeat(content.length()));
  }

  public static void restoreInputText() {
    // restore text in console
    System.out.print(content);
  }
}