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
	private int bufferSize;
	private byte[] buf;
	public InetAddressTest()
	{
		try {
			msg = new ByteArrayOutputStream();
			buf = new byte[bufferSize];
			InetAddress ip = InetAddress.getByName("127.0.0.1");
			DatagramSocket socket = new DatagramSocket();
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

	public int getBufferSize() {
		return bufferSize;
	}
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	public byte[] getBuf() {
		return buf;
	}
	public void setBuf(byte[] buf) {
		this.buf = buf;
	}
	
}
