package project.oss.sunmoon;

//����Ʈ ������ ���� ���� ����
import java.io.ByteArrayOutputStream;
//Ŭ���̾�Ʈ, ������ ����ϴ� ���� ����
import java.io.IOException;
//��Ʈ��ũ ���� ����
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//���� ����
import javax.sound.sampled.AudioFormat;
//����� ��� ���� ����
import javax.sound.sampled.TargetDataLine;

public class AudioClient{

	boolean stopaudioCapture = false; //����� ����
	private static final int bufferSize = 10000; //���ۻ����� ����
	@SuppressWarnings("unused")
	private AudioFormat getFormat; //����� ������� ����
	ByteArrayOutputStream data; //����Ʈ ������
	TargetDataLine targetDataLine; //����� ���
	
	@SuppressWarnings("unused")
	private AudioFormat getFormat() {
		float sampleRate = 8000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		DatagramSocket socket = null;
		@SuppressWarnings("unused")
		AudioClient audio = new AudioClient();
	}

	public AudioClient(){
		byte buffer[] = new byte[bufferSize];
		
		data = new ByteArrayOutputStream(); //����Ʈ �����ͷ� ����
		//InputStream input = new ByteArrayInputStream(audio); //����Ʈ �����͸� ����
		stopaudioCapture = false; //����� ����
		
		try{
			int port = 7001;
			while(true)
			{
				DatagramSocket socket = new DatagramSocket();
				//������ �ۼ��� Ŭ����
				InetAddress ip = InetAddress.getByName("210.119.34.106"); //������ ip��ȣ
				//byte audio[] = out.getByteArray(); //������ ���� ������
		
				while(!stopaudioCapture){ //����� ������ �ƴϸ�
					int cnt = targetDataLine.read(buffer, 0, buffer.length); //���� ���� ����
					if(cnt > 0){ //���� ���̰� 0���� ũ��
						DatagramPacket sendpacket = new DatagramPacket(buffer, buffer.length, ip, port);
						//�ְ� ���� �����Ϳ� ���� Ŭ����
						socket.send(sendpacket); 
						//��Ŷ ����
					}
				}
				socket.close(); 
			    //���� ����
			}

		} catch(IOException e){ //����
			e.printStackTrace(); //���� ��Ȳ �ذ�
		}
	}
}
