package Paint;

import java.util.ArrayList;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PaintPanel extends JPanel 
{
   final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 1);
   private Point [][] points;
   private int width;
   private int height; 

   public PaintPanel()
   {
      setBackground(COLOR_TRANSPARENT);
      setOpaque(true);

      Canvas drawArea = new Canvas();
      add(drawArea);
  
      JWindow drawingFrame = new JWindow();
      drawingFrame.setBackground(COLOR_TRANSPARENT);
      drawingFrame.setContentPane(this);
      drawingFrame.pack();
  
      Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      
      width = (int)screenSize.getWidth();
      height = (int)screenSize.getHeight();
      points = new Point[width][height];

      drawingFrame.setSize(width, height);
      drawingFrame.setVisible(true);
      drawingFrame.setLocationRelativeTo(null);

      addMouseMotionListener(
         new MouseMotionAdapter() 
         {  
           
            @Override
            public void mouseDragged(MouseEvent event)
            {
               //if(event.getButton() == MouseEvent.BUTTON1)
               //Point currentPoint = event.getPoint();
               if(SwingUtilities.isLeftMouseButton(event))
                  points[event.getX()][event.getY()] = event.getPoint();
               else if(SwingUtilities.isRightMouseButton(event))
               {
                  for(int i = 0; i < 10; i++)
                     for(int j = 0; j < 10; j++)
                        points[event.getX() - 5 + i][event.getY() - 5 + j] = null;
               }
               repaint();
            } 
         } 
      ); 
   }

   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      g.setColor(Color.RED);

      for(int i = 0; i < width; i++)
      {
         for(int j = 0; j < height; j++)
         {
            if(points[i][j] != null)
               g.fillOval(i, j, 10, 10);
         }
      }
      /*
      for (Point point : points)
         g.fillOval(point.x, point.y, 4, 4);
      */
      } 
}
