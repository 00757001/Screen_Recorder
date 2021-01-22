package Paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PaintFrame extends JFrame implements KeyListener{
    final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 1);

    PaintPanel paintPanel = new PaintPanel();

    public PaintFrame() {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);

        setUndecorated(true);
        setFocusable(true);
       // setBackground(new Color(0, 0, 0, 0));

        
        
        //add(paintPanel);
        //setContentPane(paintPanel);
        pack();
        
        paintPanel.setOpaque(false);
        
        addKeyListener(this);
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("The key has been pressed");
            paintPanel.setVisible(false);
            this.dispose();
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}