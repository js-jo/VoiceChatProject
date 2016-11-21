package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class InetAddressTest 
{
	public static void main(String[] args)
	{
		try {
			InetAddress ip = InetAddress.getByName("127.0.0.1");
			DatagramSocket socket = new DatagramSocket();
			String msg = "°Ç¾Æ ¾È³ç";
			byte[] buf = msg.getBytes();
			int port = 10000;
			DatagramPacket p = new DatagramPacket(buf, buf.length, ip, port);
			socket.send(p);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
