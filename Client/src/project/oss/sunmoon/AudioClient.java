package project.oss.sunmoon;

//클라이언트, 서버를 사용하는 소켓 선언
import java.io.IOException;
//네트워크 소켓선언
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//사운드 선언
import javax.sound.sampled.AudioFormat;

public class AudioClient{

	private static final int bufferSize = 500; //버퍼사이즈 지정
	byte buffer[] = new byte[bufferSize];
	@SuppressWarnings("unused")
	private AudioFormat getFormat;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		DatagramSocket socket = null;
		AudioClient audio = new AudioClient();
	}

	public AudioClient(){
		//boolean stopaudioCapture = false; //오디오 멈춤

		try{
			int port = 7000;
			byte[] message ="oh".getBytes(); //전송할 데이터
			InetAddress ip = InetAddress.getByName("210.119.34.106"); //전송할 ip번호
			DatagramPacket sendpacket = new DatagramPacket(message, message.length, ip, port);
			//주고 받을 데이터와 관련 클래스
			DatagramSocket socket = new DatagramSocket();
			//데이터 송수신 클래스
			socket.send(sendpacket); 
			//패킷 전송
			socket.close(); 
			//소켓 종료

		} catch(IOException e){ //예외
			e.printStackTrace(); //예외 상황 해결
		}
	}

	@SuppressWarnings("unused")
	private AudioFormat getFormat() {
		float sampleRate = 8000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
}
