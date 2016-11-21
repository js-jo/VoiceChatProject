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
	private ByteArrayOutputStream msg;
	public InetAddressTest()
	{
		try {
			msg = new ByteArrayOutputStream();
			InetAddress ip = InetAddress.getByName("127.0.0.1");
			DatagramSocket socket = new DatagramSocket();
			byte[] buf = msg.toByteArray();
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
	public ByteArrayOutputStream getMsg() {
		return msg;
	}
	public void setMsg(ByteArrayOutputStream msg) {
		this.msg = msg;
	}
	
	
}
