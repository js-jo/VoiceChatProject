package project.oss.sunmoon;
 
import java.io.*; //클라이언트, 서버를 사용하는 소켓 선언
import java.net.*; //네트워크 소켓선언
import javax.sound.sampled.*; //사운드 선언
 
public class AudioClient{
	
	private static final int bufferSize = 0;
	byte buffer[] = new byte[bufferSize];
	@SuppressWarnings("unused")
	private AudioFormat getFormat;
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		AudioClient audio = new AudioClient();
	}
	
	public AudioClient(){
		boolean stopaudioCapture = false; //오디오 멈춤
		
		try{
			DatagramSocket socket = new DatagramSocket(null);
			//데이터를 주고받기 위해서 udp 소켓 연결
			InetAddress ip = InetAddress.getByName("192.168.219.104");
			DatagramPacket sendpacket = new DatagramPacket(buffer, buffer.length, ip, 7000);
			//socket.send(socket);
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
 