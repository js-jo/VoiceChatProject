package project.oss.sunmoon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class VoiceCaptureThread extends Thread
{

	protected boolean running;
	ByteArrayOutputStream out;
	final AudioFormat format = getFormat();
	DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
	
	int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
	byte buffer[] = new byte[bufferSize];
	
	
	public byte[] getBuffer() {
		return buffer;
	}

	public VoiceCaptureThread() throws LineUnavailableException
	{
		line.open(format);
		line.start();
	}
	
	public void run()
	{
		//기능 구현
		out = new ByteArrayOutputStream();
		running = true;
		try {
			while (running) {
				int count = line.read(buffer, 0, buffer.length);
				if (count > 0) {
					out.write(buffer, 0, count);
				}
			}
			out.close();
		} catch (IOException e) {
			System.err.println("I/O problems: " + e);
			System.exit(-1);
		}
	}
	
	
	private AudioFormat getFormat() 
	{
		float sampleRate = 8000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
}

