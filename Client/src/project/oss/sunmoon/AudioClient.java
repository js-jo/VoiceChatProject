package project.oss.sunmoon;
 
import java.io.*; //Ŭ���̾�Ʈ, ������ ����ϴ� ���� ����
import java.net.*; //��Ʈ��ũ ���ϼ���
import javax.sound.sampled.*; //���� ����
 
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
		boolean stopaudioCapture = false; //����� ����
		
		try{
			DatagramSocket socket = new DatagramSocket(null);
			//�����͸� �ְ�ޱ� ���ؼ� udp ���� ����
			InetAddress ip = InetAddress.getByName("192.168.219.104");
			DatagramPacket sendpacket = new DatagramPacket(buffer, buffer.length, ip, 7000);
			//socket.send(socket);
		} catch(IOException e){ //����
			e.printStackTrace(); //���� ��Ȳ �ذ�
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
 