package project.oss.sunmoon.udp;

import java.awt.Font;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AudioServer extends Thread
{
	private int port;
	public static final int Buffer_Size = 500;
	private DatagramSocket sock;
	private DatagramPacket pack;
	private byte[] receiveBuffer;
	SourceDataLine inSpeaker = null;
	private String hostAddress = null;
	private boolean flag = false;
	private JPanel statePanel;
	
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
			receiveBuffer = new byte[Buffer_Size];
			pack = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			String addresses = InetAddress.getLocalHost().getHostAddress();

			
			System.out.println(addresses);
			System.out.println(hostAddress);
			if(addresses.equals(hostAddress))
			{
				Font font = new Font("돋움" , Font.BOLD,20);
				JLabel lblState = new JLabel();
				lblState.setText("Connecting sucess with" + hostAddress + "\n");
				lblState.setFont(font);
				statePanel.add(lblState);
				statePanel.validate();
				statePanel.repaint();

				setFlag(true);
			}
			while(flag)
			{
				//System.out.println("SADFASD");
				sock.receive(pack);
				System.out.println("여기서 무한루프 서버");
				
				byte[] bmsg = pack.getData();
				String msg = new String(bmsg,0,pack.getLength());
				inSpeaker.write(receiveBuffer, 0, receiveBuffer.length);
				inSpeaker.drain();
				System.out.println("수신내용 : "+ pack.getAddress().getHostAddress() + msg);
				pack.setLength(receiveBuffer.length);
			}
			sock.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	//	public static void main(String[] args)
	//	{
	//		AudioServer receiver = new AudioServer();
	//		receiver.receiveMessage();
	//	}
}
