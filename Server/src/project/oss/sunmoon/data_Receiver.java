package project.oss.sunmoon;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class data_Receiver
{
	public static final int port = 7000;
	public static final String ipAddr = "192.168.219.104";
	public static final int Buffer_Size = 500;
	private MulticastSocket sock;
	private DatagramPacket pack;
	private byte[] receiveBuffer;
	byte[] inSound;
	SourceDataLine inSpeaker = null; //바이트의 버퍼링을 처리하여 믹서에 전달

	public data_Receiver(){
		try{
			sock = new MulticastSocket(port);
			sock.joinGroup(InetAddress.getByName(ipAddr));

			AudioFormat af = new AudioFormat(8000.0f,8,1,true,false);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
			inSpeaker = (SourceDataLine)AudioSystem.getLine(info);
			inSpeaker.open(af);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	private void receiveMessage()
	{
		try{
			receiveBuffer = new byte[Buffer_Size];
			pack = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			sock.receive(pack);
			inSpeaker.write(receiveBuffer, 0, receiveBuffer.length);
			inSpeaker.drain();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		data_Receiver receiver = new data_Receiver();

		while(true)
		{
			receiver.receiveMessage();
		}
	}
}
