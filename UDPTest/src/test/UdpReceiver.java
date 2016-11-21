package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpReceiver 
{
	 private boolean flag = true;
	 final int port = 9000;
	 DatagramSocket socket;
	 public UdpReceiver()
	 {
		try{
			socket = new DatagramSocket(port);
			byte[] msg = new byte[512];
			
			
			System.out.println("수신 대기중 ... ");
			while(flag)
			{
				DatagramPacket p = new DatagramPacket(msg, msg.length);
				socket.receive(p);
				System.out.println(new String(msg));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
