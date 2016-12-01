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
	private DatagramSocket sock;
	private DatagramPacket pack;
	private byte[] receiveBuffer;
	private String hostAddress = null;
	private JPanel statePanel;
	private boolean first = true;
	final AudioFormat format = getFormat(); 


	public  String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setStatePanel(JPanel statePanel) {
		this.statePanel = statePanel;
	}

	public AudioServer(String hostAddress, JPanel statePanel, int port){
		try{
			sock = new DatagramSocket(port);
			AudioFormat af = new AudioFormat(8000.0f,8,1,true,false);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
			setStatePanel(statePanel);
			setHostAddress(hostAddress);
			setPort(port);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void receiveAudio()
	{
		try{			
			String addresses = InetAddress.getLocalHost().getHostAddress();

			Font font = new Font("µ¸¿ò" , Font.BOLD,20);
			JLabel lblState = new JLabel();
			lblState.setText("Waiting to connect to" + hostAddress + "\n");
			lblState.setFont(font);
			statePanel.add(lblState);
			statePanel.validate();
			statePanel.repaint();

			receiveBuffer = new byte[30000];
			while(true)
			{
				try {

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

					int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
					byte buffer[] = new byte[bufferSize];

					try {
						int count;
						count = ais.read(buffer, 0, buffer.length);
						if (count > 0) {
							line.write(buffer, 0, count);

							if(first)
							{
								JLabel chatState = new JLabel();
								lblState.setText("Start Voice chat" + "\n");
								lblState.setFont(font);
								statePanel.add(chatState);
								statePanel.validate();
								statePanel.repaint();
								first = false;
								Thread.sleep(1000);
							}
							
							JLabel sendState = new JLabel();
							lblState.setText("Get voice..." + "\n");
							lblState.setFont(font);
							statePanel.add(sendState);
							statePanel.validate();
							statePanel.repaint();
						}
					} catch (IOException e) {
						System.err.println("I/O problems: " + e);
						System.exit(-3);
					}
				} catch (LineUnavailableException e) {
					System.err.println("Line unavailable: " + e);
					System.exit(-4);
				}
			}
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
}
