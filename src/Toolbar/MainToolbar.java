package Toolbar;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

import Magnifier.MagnifierToolbar;
import Paint.PaintFrame;

import java.awt.FlowLayout;

import static org.bytedeco.javacpp.opencv_core.cvFlip;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics2D;

public class MainToolbar extends JFrame{
	
	
	static MagnifierToolbar magnifier;
	static PaintFrame paintframe;
	private JFrame frame;
	static CanvasFrame canvas = null;
	static VideoInputFrameGrabber webcam = new VideoInputFrameGrabber(0);
	Timer t;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		MainToolbar window = new MainToolbar();
		window.setVisible(true);
		window.setAlwaysOnTop(true);
	}

	/**
	 * Create the application.
	 */
	public MainToolbar() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//frame = new JFrame();
		setResizable(false);
		setTitle("工具列");
		setBounds(100, 100, 492, 140);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(0,3));
		//canvas.setVisible(false);
		
		JPanel panel = new JPanel(null);
		getContentPane().add(panel);
		
		JButton btnNewButton_3 = new JButton("放大鏡");
		btnNewButton_3.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				magnifier = new MagnifierToolbar();
				magnifier.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				magnifier.setSize(280,380);
				magnifier.setFocusable( true );
				magnifier.setVisible(true);
			}
		});
		btnNewButton_3.setBounds(31, 33, 100, 40);
		panel.add(btnNewButton_3);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		ActionListener checkWebCam = (ActionEvent e) -> {
        	if(!canvas.isVisible()) {
        		try {
					webcam.stop();
					//thread.stop();
				} catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        };
		JButton btnNewButton_1 = new JButton("前鏡頭錄影");
		btnNewButton_1.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				//thread.setDaemon(true);
				Thread thread = new Thread(WebcamRunnable);
		        thread.start();
		        t = new Timer(20, checkWebCam);
		        t.start();
			}
		});
		btnNewButton_1.setBounds(10, 33, 142, 40);
		panel_1.add(btnNewButton_1);
		btnNewButton_1.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnNewButton = new JButton("畫筆");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PaintFrame paintFrame = new PaintFrame();
			    paintFrame.setVisible(true);
			}
		});
		btnNewButton.setBounds(31, 33, 100, 40);
		panel_2.add(btnNewButton);
		btnNewButton.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
	}
	static Runnable WebcamRunnable = new Runnable() {
		@Override
		public void run() {	
			canvas = new CanvasFrame("WebCam Demo");
	    	canvas.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
	    	//canvas.setAlwaysOnTop(true);
	        
			try {
		        webcam.setImageWidth(400);
		        webcam.setImageHeight(300);
		        FrameGrabber grabber = webcam;
		        
		        grabber.start();

		        org.bytedeco.javacv.Frame img;
		        
		        while (true){
		            img = grabber.grab();
		            if (img != null) {
		                canvas.showImage(img);
		            }			        
		        }
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
        
    };
	
}
