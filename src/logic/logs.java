package logic;

import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class logs {
   public static void makeLog(Exception exception){
      Logger logger = Logger.getLogger("MyLog");
      logger.setUseParentHandlers(false);
      FileHandler fh;
      try {
         SimpleDateFormat format = new SimpleDateFormat("M-d_HH-mm-ss");
         fh = new FileHandler("logs/logs.log",true);
         logger.addHandler(fh);
         SimpleFormatter formatter = new SimpleFormatter();
         fh.setFormatter(formatter);
         logger.info("Log: "+exception);
      } catch (Exception e) {
         logger.info("Log making log: "+e);
      }
   }
}
