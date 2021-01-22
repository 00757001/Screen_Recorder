package Record;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ExecFfmpeg {
    
    
	public static void executeFfMpeg(String ffmpegPath, String audioInputPath, String videoInputPath, String videoOutputPath) throws IOException {
	 	List<String> command=new java.util.ArrayList<String>();
	 	command.add(ffmpegPath);
		command.add("-i");
		command.add(audioInputPath);
		command.add("-i");
		command.add(videoInputPath);
		command.add("-y");
		command.add(videoOutputPath);

		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		InputStream errorStream = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(isr);
		if (br != null) {
			br.close();
		}
		if (isr != null) {
			isr.close();
		}
		if (errorStream != null) {
			errorStream.close();
		}
	}
}