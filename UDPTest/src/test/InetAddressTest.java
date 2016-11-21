package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class InetAddressTest 
{
	public InetAddressTest(byte[] buf,int bufferSize)
	{
		try {
			ByteArrayOutputStream msg = new ByteArrayOutputStream();
			InetAddress ip = InetAddress.getByName("127.0.0.1");
			DatagramSocket socket = new DatagramSocket();
			int port = 9000;
			DatagramPacket p = new DatagramPacket(buf, bufferSize, ip, port);
			socket.send(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
