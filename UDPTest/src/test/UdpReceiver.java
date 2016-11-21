package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpReceiver 
{
	
	public static void main(String[] args)
	{
		try{
			int port = 10000;
			DatagramSocket socket = new DatagramSocket(port);
			byte[] msg = new byte[512];
			
			DatagramPacket p = new DatagramPacket(msg, msg.length);
			System.out.println("수신 대기중 ... ");
			socket.receive(p);
			System.out.println("수신 완료");
			System.out.println("ip : " + p.getAddress());
			System.out.println("수신 내용  : " + new String(msg));
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
