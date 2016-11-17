package kr.oss.sunmoon;

import java.io.*; //클라이언트, 서버를 사용하는 소켓 선언
import java.net.*; //네트워크 소켓선언
 
public class AudioClient{
	private static final int bufferSize = 0;

	public static void main(String[] args) {

		try{
			DatagramSocket socket = new DatagramSocket(null);
			//데이터를 주고받기 위해서 udp 소켓 연결
			InetSocketAddress ip = new InetSocketAddress("220.69.166.100",6800);
			//연결을 원하는 소켓 주소 작성
			byte[] send = new byte[1024];
			byte[] receive = new byte[1024];
			//보내고 받을 데이터 바이트 배열로 변환

			socket.bind(ip); //연결
			System.out.println("연결을 완료하였습니다.");
			
			DatagramPacket sendpacket = new DatagramPacket(send, send.length, ip);
			//각 정보를 담고 전송
			socket.send(sendpacket); //패킷 전송
			
			DatagramPacket receivepacket = new DatagramPacket(receive, receive.length);
			socket.receive(receivepacket);
		}
		
		catch(IOException e){ //예외
			e.printStackTrace(); //예외 상황 해결
			}
		}
}
