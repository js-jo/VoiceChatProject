package project.oss.sunmoon.udp;

import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AudioServer extends Thread
{
	private static int port;
	public static final int Buffer_Size = 500;
	private DatagramSocket sock;
	private DatagramPacket pack;
	private byte[] receiveBuffer;
	SourceDataLine inSpeaker = null;
	private static String hostAddress = null;
	private boolean flag = false;
	private JPanel statePanel;
	final AudioFormat format = getFormat(); 
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setStatePanel(JPanel statePanel) {
		this.statePanel = statePanel;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public boolean getFlag() {
		return flag;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public AudioServer(String hostAddress, JPanel statePanel, int port){
		try{
			sock = new DatagramSocket(port);
			AudioFormat af = new AudioFormat(8000.0f,8,1,true,false);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
			inSpeaker = (SourceDataLine)AudioSystem.getLine(info);
			inSpeaker.open(af);
			setStatePanel(statePanel);
			setHostAddress(hostAddress);
			setPort(port);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void receiveMessage()
	{
		try{			
			String addresses = InetAddress.getLocalHost().getHostAddress();
			
			System.out.println(addresses);
			System.out.println(hostAddress);
			
			Font font = new Font("돋움" , Font.BOLD,20);
			JLabel lblState = new JLabel();
			lblState.setText("Connecting sucess with" + hostAddress + "\n");
			lblState.setFont(font);
			statePanel.add(lblState);
			statePanel.validate();
			statePanel.repaint();

			setFlag(true);
			
			while(flag)
			{
				//System.out.println("SADFASD");
				//sock.receive(pack);	
				try {
					receiveBuffer = new byte[Buffer_Size];
					pack = new DatagramPacket(receiveBuffer, receiveBuffer.length);
					sock.receive(pack);								
					
					byte audio[] = pack.getData();
					InputStream input = new ByteArrayInputStream(audio);
					final AudioFormat format = getFormat();
					final AudioInputStream ais = new AudioInputStream(input, format, audio.length / format.getFrameSize());
					DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
					final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
					line.open(format);
					line.start();
					Runnable runner = new Runnable() {
						int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
						byte buffer[] = new byte[bufferSize];

						public void run() {
							try {
								int count;
								while ((count = ais.read(buffer, 0, buffer.length)) != -1) {
									if (count > 0) {
										line.write(buffer, 0, count);
									}
								}
								line.drain();
								line.close();
							} catch (IOException e) {
								System.err.println("I/O problems: " + e);
								System.exit(-3);
							}
						}
					};
					Thread playThread = new Thread(runner);
					playThread.start();
				} catch (LineUnavailableException e) {
					System.err.println("Line unavailable: " + e);
					System.exit(-4);
				}
//				String msg = new String(bmsg,0,pack.getLength());
//				inSpeaker.write(bmsg, 0, bmsg.length);
//				inSpeaker.drain();
//				System.out.println("수신내용 : "+ pack.getAddress().getHostAddress() + msg);
//				pack.setLength(receiveBuffer.length);
			}
			sock.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	private AudioFormat getFormat() {
		float sampleRate = 8000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

//		public static void main(String[] args)
//		{
//			AudioServer receiver = new AudioServer(hostAddress, null, port);
//			receiver.receiveMessage();
//		}
}
