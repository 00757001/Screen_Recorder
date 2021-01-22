package Record;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jcodec.api.awt.AWTSequenceEncoder;

import Toolbar.MainToolbar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class ScreenRecorder extends JFrame{
    public JFrame frame;
	JButton StartRecordButton;
	JButton StopRecordButton;
	JLabel lblNewLabel;
	JLabel StateLabel;
	JLabel lblNewLabel_2;
	JLabel RecordTimeLabel;
	JLabel lblNewLabel_4;
	JLabel TimerLabel;
	
	Rectangle rectangle;
    AWTSequenceEncoder encoder;
    ScreenRecorderTask recorderTask;
    TimerCountTask countTask;

    public static Timer timerRecord;
    Timer timerCount;

    boolean isRecording = false;
    File f;

    AudioRecorder audioRecorder;
	    
	public ScreenRecorder() {
		super("Computer Dummy Savior");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 537, 393);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		StartRecordButton = new JButton("開始錄影");
		StartRecordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
				initScreenRecorderObjects("video");
		        scheduleTimerTasks();
		        audioRecorder = new AudioRecorder();
		        audioRecorder.start();
			}
		});
		StartRecordButton.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		StartRecordButton.setBounds(26, 33, 209, 56);
		getContentPane().add(StartRecordButton);
		
		StopRecordButton = new JButton("停止錄影");
		StopRecordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isRecording) {
		            stopScreenRecording();
		            audioRecorder.stopRecording();
		            try {
		            	//URL url = ClassLoader.getSystemResource("ffmpeg.exe");
		                ExecFfmpeg.executeFfMpeg("src/ffmpeg.exe", "audio.wav", "video.mp4", "output.mp4");
		            } catch (IOException ex) {
		            	System.out.println("Cannot find ffmpeg.exe");
		            }
		        }
		        isRecording = false;
			}
		});
		StopRecordButton.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		StopRecordButton.setBounds(26, 116, 209, 56);
		getContentPane().add(StopRecordButton);
			
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 182, 474, 2);
		getContentPane().add(separator);
		
		lblNewLabel = new JLabel("錄影狀態:");
		lblNewLabel.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		lblNewLabel.setBounds(47, 194, 122, 35);
		getContentPane().add(lblNewLabel);
		
		StateLabel = new JLabel("尚未開始錄影");
		StateLabel.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		StateLabel.setBounds(179, 199, 173, 25);
		getContentPane().add(StateLabel);
		
		lblNewLabel_2 = new JLabel("錄影時間:");
		lblNewLabel_2.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(47, 239, 122, 25);
		getContentPane().add(lblNewLabel_2);
		
		RecordTimeLabel = new JLabel("0 分鐘");
		RecordTimeLabel.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		RecordTimeLabel.setBounds(179, 239, 243, 23);
		getContentPane().add(RecordTimeLabel);
		
		lblNewLabel_4 = new JLabel("計時器:");
		lblNewLabel_4.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		lblNewLabel_4.setBounds(47, 274, 77, 25);
		getContentPane().add(lblNewLabel_4);
		
		TimerLabel = new JLabel("0秒");
		TimerLabel.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		TimerLabel.setBounds(179, 275, 197, 22);
		getContentPane().add(TimerLabel);
		
		JButton ToolbarButton = new JButton("工具列");
		ToolbarButton.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		ToolbarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainToolbar mainToolbar = new MainToolbar();
				mainToolbar.setVisible(true);
				mainToolbar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		ToolbarButton.setBounds(311, 33, 173, 56);
		getContentPane().add(ToolbarButton);
	}
	private void initScreenRecorderObjects(String fileName) {

        rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

       //create a new file
        f = new File(fileName + ".mp4");

        try {
            // initialize the encoder
            encoder = AWTSequenceEncoder.createSequenceEncoder(f, 24 / 8);
        } catch (IOException ex) {
            Logger.getLogger(ScreenRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
	private void scheduleTimerTasks() {

        isRecording = true;

        int delay = 1000 / 24;

        RecordTimer.reset();

        timerRecord = new Timer("Thread TimerRecord");

        timerCount = new Timer("Thread TimerCount");

        recorderTask = new ScreenRecorderTask(encoder, rectangle);
        countTask = new TimerCountTask(TimerLabel);

        timerRecord.scheduleAtFixedRate(recorderTask, 0, delay);
        timerCount.scheduleAtFixedRate(countTask, 0, 1000);

        StateLabel.setText("正在錄影...");

    }
	private void stopScreenRecording() {

        RecordTimer.stop();

        StateLabel.setText("停止錄影");
        TimerLabel.setText("0秒");
        String tempTimeText = RecordTimer.getTimeInMin()+"分鐘 "+RecordTimer.getModSec()+"秒";
        RecordTimeLabel.setText(tempTimeText);

        //cancel the timer for time counting here
        timerCount.cancel();
        timerCount.purge();

        // stop the timer from executing the task here
        timerRecord.cancel();
        timerRecord.purge();

        recorderTask.cancel();
        countTask.cancel();

        try {
            encoder.finish();// finish  encoding
            System.out.println("encoding finish " + (RecordTimer.getTimeInSec()) + "s");
            //StateLabel.setText("recorder Stopped...");
            //TimerLabel.setText("" + RecordTimer.getTimeInMin() + "min");

        } catch (IOException ex) {
            Logger.getLogger(ScreenRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
	public static String getResource(String rsc) {
	      String val = "";

	      try {
	         // input stream
	         InputStream i = ScreenRecorder.class.getResourceAsStream(rsc);
	         BufferedReader r = new BufferedReader(new InputStreamReader(i));

	         // reads each line
	         String l;
	         while((l = r.readLine()) != null) {
	            val = val + l;
	         } 
	         i.close();
	      } catch(Exception e) {
	         System.out.println(e);
	      }
	      return val;
	   }
	 
}
