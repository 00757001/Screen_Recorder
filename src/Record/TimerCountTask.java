package Record;
import java.util.TimerTask;

import javax.swing.JLabel;

public class TimerCountTask extends TimerTask{
    
    //declare variables
    JLabel label;
    int timeInSec = 0;

    public TimerCountTask(JLabel jLabel) {
        label = jLabel; // initialize the label
    }

    @Override
    public void run() {
        label.setText(""+timeInSec+"秒"); // set the label text to current count value
      
        timeInSec++; //increment count by one
    }
    
}