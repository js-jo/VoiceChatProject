package project.oss.sunmoon;

//Ŭ���̾�Ʈ, ������ ����ϴ� ���� ����
import java.io.IOException;
//��Ʈ��ũ ���ϼ���
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//���� ����
import javax.sound.sampled.AudioFormat;

public class AudioClient{

	private static final int bufferSize = 500; //���ۻ����� ����
	byte buffer[] = new byte[bufferSize];
	@SuppressWarnings("unused")
	private AudioFormat getFormat;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		DatagramSocket socket = null;
		AudioClient audio = new AudioClient();
	}

	public AudioClient(){
		//boolean stopaudioCapture = false; //����� ����

		try{
			int port = 7000;
			byte[] message ="oh".getBytes(); //������ ������
			InetAddress ip = InetAddress.getByName("210.119.34.106"); //������ ip��ȣ
			DatagramPacket sendpacket = new DatagramPacket(message, message.length, ip, port);
			//�ְ� ���� �����Ϳ� ���� Ŭ����
			DatagramSocket socket = new DatagramSocket();
			//������ �ۼ��� Ŭ����
			socket.send(sendpacket); 
			//��Ŷ ����
			socket.close(); 
			//���� ����

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
