package Record;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TimerTask;

import org.jcodec.api.awt.AWTSequenceEncoder;
import java.util.logging.*;

public class ScreenRecorderTask extends TimerTask {

    //declare variables
    AWTSequenceEncoder encoder;

    Robot robot;
    Rectangle screenDimension;
    
    BufferedImage image;
   
    public ScreenRecorderTask(AWTSequenceEncoder sequenceEncoder, Rectangle rectangle) {
    
        encoder = sequenceEncoder;
        screenDimension = rectangle;
       
        try {
            robot = new Robot();
        } catch (AWTException ex) {

            Logger.getLogger(ScreenRecorderTask.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        RecordTimer.start();

    }

    @Override
    public void run() {
               
        System.out.println("encoding...");
        image = robot.createScreenCapture(screenDimension); //capture current screen
            
       try {
            encoder.encodeImage(image); //encoding image files
        } catch (IOException ex) {
            Logger.getLogger(ScreenRecorderTask.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}