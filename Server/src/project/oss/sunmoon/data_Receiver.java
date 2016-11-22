package project.oss.sunmoon;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class data_Receiver extends Thread
{
	public static final int port = 7001;
	public static final String ipAddr = "210.119.34.106";
	public static final int Buffer_Size = 500;
	private DatagramSocket sock;
	private DatagramPacket pack;
	private byte[] receiveBuffer;
	SourceDataLine inSpeaker = null;

	public data_Receiver(){
		try{
			sock = new DatagramSocket(port);
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
			
			while(true)
			{
				System.out.println("SADFASD");
				sock.receive(pack);
				byte[] bmsg = pack.getData();
				String msg = new String(bmsg,0,pack.getLength());
				inSpeaker.write(receiveBuffer, 0, receiveBuffer.length);
				inSpeaker.drain();
				System.out.println("수신내용 : "+ pack.getAddress().getHostAddress() + msg);
				pack.setLength(receiveBuffer.length);
				//sock.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		data_Receiver receiver = new data_Receiver();
		receiver.receiveMessage();
	}
}
