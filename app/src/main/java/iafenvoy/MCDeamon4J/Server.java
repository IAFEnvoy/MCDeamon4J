package iafenvoy.MCDeamon4J;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import iafenvoy.MCDeamon4J.Logger.Logger;

public class Server {
  public static void runServer(ProcessBuilder builder) {
    Logger.info("Starting Server...");
    Scanner scanner = new Scanner(System.in);
    Process process;
    try {
      process = builder.start();
    } catch (IOException e) {
      Logger.error("An IOException occur : ");
      e.printStackTrace();
      Logger.error("An error occurred while starting server.");
      Logger.error("Stop loading");
      scanner.close();
      return;
    }
    // start process
    BufferedReader readStdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
    BufferedReader readStderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
    BufferedWriter writeStdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          String outTemp = "", errTemp = "";
          while ((outTemp = readStdout.readLine()) != null || (errTemp = readStderr.readLine()) != null) {
            if (outTemp != null && outTemp != "")
              Logger.log(outTemp);
            if (errTemp != null && errTemp != "")
              Logger.log(errTemp);
          }
        } catch (IOException e) {
          Logger.error("An IOException occur : ");
          e.printStackTrace();
          Logger.error("An error occurred while running Server");
          try {
            writeStdin.write("stop");
          } catch (IOException e1) {
          }
          Logger.error("Stopping Server");
          // TODO: save all config
          return;
        }
      }
    });
    thread.setName("server");
    thread.start();
    // send input from scanner to server
    while (true) {
      String input = scanner.nextLine();
      if (input.equals("stop")) {
        Logger.info("Stopping Server...");
        try {
          writeStdin.write("stop");
          process.waitFor();
        } catch (IOException e) {
          Logger.error("An IOException occur : ");
          e.printStackTrace();
          Logger.error("An error occurred while stopping server.");
          break;
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Logger.info("Succeeded stopping server.");
      } else {
        try {
          writeStdin.write(input);
        } catch (IOException e) {
          Logger.error("An IOException occur : ");
          e.printStackTrace();
          Logger.error("An error occurred while sending input to server.");
          break;
        }
      }
    }
    scanner.close();
  }
}
