package Magnifier;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


class ZoomOnMouse {

    PointerInfo pi;
    JPanel gui;
    JLabel output;
    Timer t;
    private double Zoom = 1;

    public ZoomOnMouse() throws AWTException {
        
    	final int size = 256;
    	gui = new JPanel(new BorderLayout(2, 2));
        final BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        final JLabel zoomLabel = new JLabel(new ImageIcon(bi));
        gui.add(zoomLabel, BorderLayout.CENTER);
        ActionListener zoomListener = (ActionEvent e) -> {
        	Robot robot;
			try{
				robot = new Robot();
				pi = MouseInfo.getPointerInfo();
	            Point p = pi.getLocation();
	            Rectangle2D r = new Rectangle2D.Double(p.x - (size/(2 * Zoom)),p.y - (size/(2 * Zoom)),size/Zoom,size/Zoom);
	            BufferedImage temp = robot.createScreenCapture(r.getBounds());
	            
	            
	            Graphics2D g = (Graphics2D) bi.getGraphics();
	            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	            g.drawImage(temp,0,0,size,size,null);
	            zoomLabel.repaint();
			}catch (AWTException e1) {
				e1.printStackTrace();
			}
        	
        };
        t = new Timer(30, zoomListener);
        t.start();
    }

    
    public Component getGui() {
        return gui;
    }
    public void setZoom(int level) {
    	Zoom = (double)level / 100;
    }
    
}