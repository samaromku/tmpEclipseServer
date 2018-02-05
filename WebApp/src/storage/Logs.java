package storage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.*;

public class Logs {
    PrintWriter writer;
    Logger logger = Logger.getLogger("conlog");

    Handler handler;



    String file = "logs.txt";


    public void createOrOpen() {
        try {
            handler = new FileHandler("concrte.log");
            handler.setFormatter(new XMLFormatter());
            logger.addHandler(handler);
            logger.info("logger info message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void logWrite(String messageLog){
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            writer.println(messageLog);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writerClose(){
        writer.close();
    }


}
