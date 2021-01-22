package Record;

import java.awt.Window.Type;

import javax.swing.JFrame;

public class ScreenRecordTest {
	public static void main(String args[]) {
		ScreenRecorder window = new ScreenRecorder();
		window.setVisible(true);
		//window.setType(Type.UTILITY);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
