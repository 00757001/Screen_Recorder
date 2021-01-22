package Magnifier;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MagnifierToolbar extends JFrame implements KeyListener{
	private final BorderLayout layout;
	private final JLabel Title1;
	private final JButton DownMagnifyButton;
	private final JButton UpMagnifyButton;
	private JLabel MagnifyPercent;
	private final JPanel MagnifyPanel;
	private final JPanel ZoomPanel;
	private int Level = 100;
	private ZoomOnMouse zm = null;
	private static int ImgNum = 0;
	
	public MagnifierToolbar(){
		super("自由縮放截圖工具");
		layout = new BorderLayout();
		getContentPane().setLayout(layout);
		ZoomPanel = new JPanel();  
		try {
            zm = new ZoomOnMouse();                 
            ZoomPanel.add(zm.getGui());              
        } catch (AWTException e) {
            e.printStackTrace();
        }
		//ZoomPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		
		Title1 = new JLabel("放大倍率:");
		Title1.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 16));
		MagnifyPercent = new JLabel("100% ");
		MagnifyPercent.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 16));
		DownMagnifyButton = new JButton("–");
		DownMagnifyButton.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		DownMagnifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Level <= 50) {
					JOptionPane.showMessageDialog(null, "不能更小了！", "警告", JOptionPane.WARNING_MESSAGE);
					return ;
				}
				Level -= 50;
				String tempLevel = Level + "%";
				MagnifyPercent.setText(tempLevel);
				zm.setZoom(Level);
			}
		});
		DownMagnifyButton.setBackground(Color.WHITE);
		
		UpMagnifyButton = new JButton();
		UpMagnifyButton.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 24));
		UpMagnifyButton.setText("+");
		UpMagnifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Level >= 300) {
					JOptionPane.showMessageDialog(null, "不能更大了！", "警告", JOptionPane.WARNING_MESSAGE);
					return ;
				}
				Level += 50;
				String tempLevel = Level + "%";
				MagnifyPercent.setText(tempLevel);
				zm.setZoom(Level);
			}
		});
		UpMagnifyButton.setBackground(Color.WHITE);
		UpMagnifyButton.setFocusable(false);
		DownMagnifyButton.setFocusable(false);
		MagnifyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		MagnifyPanel.add(Title1);
		MagnifyPanel.add(DownMagnifyButton);
		MagnifyPanel.add(MagnifyPercent);
		MagnifyPanel.add(UpMagnifyButton);
		MagnifyPanel.setBorder(BorderFactory.createTitledBorder("TOOL"));
		getContentPane().add(MagnifyPanel,BorderLayout.NORTH);
		this.addKeyListener(this);

		
		getContentPane().add(ZoomPanel);
	}
	public void CaptureFrame(Component component) {
	    Rectangle rect = component.getBounds();
	    try {
	        String format = "png";
	        
			String fileName = "Capture"+ ImgNum  +".png";
	        BufferedImage captureImage =
	        		new BufferedImage(rect.width, rect.height,
	                                    BufferedImage.TYPE_INT_ARGB);
	        component.paint(captureImage.getGraphics());
	 
	        ImageIO.write(captureImage, format, new File(fileName));
	        JOptionPane.showMessageDialog(this,"截圖已保存","訊息",JOptionPane.INFORMATION_MESSAGE);
	        ImgNum++;
	    } catch (IOException ex) {
	        System.err.println(ex);
	    }
	}
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.printf("%s%n",e.getKeyChar());
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			CaptureFrame(ZoomPanel);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
}
