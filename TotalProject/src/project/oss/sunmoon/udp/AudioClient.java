package project.oss.sunmoon.udp;

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
	//@SuppressWarnings("unused")
	private AudioFormat getFormat = getFormat(); //����� ������� ����
	ByteArrayOutputStream data; //����Ʈ ������
	TargetDataLine targetDataLine; //����� ���
	private boolean flag = false;
	private int port;
	
	public void setPort(int port) {
		this.port = port;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
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

	//	public static void main(String[] args) {
	//		@SuppressWarnings("unused")
	//		DatagramSocket socket = null;
	//	}

	public AudioClient(String ipAddress, int port){
		//byte buffer[] = new byte[bufferSize];
		DatagramSocket socket = null;
		data = new ByteArrayOutputStream(); //����Ʈ �����ͷ� ����
		//InputStream input = new ByteArrayInputStream(audio); //����Ʈ �����͸� ����
		stopaudioCapture = false; //����� ����
		setPort(port);
		
		Runnable sendRunner = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					DatagramSocket socket = new DatagramSocket();
					InetAddress ip = InetAddress.getByName(ipAddress); //������ ip��ȣ
					while(true)
					{						//						
						//byte audio[] = out.getByteArray(); //������ ���� ������
						System.out.println("���⼭ ���ѷ��� Ŭ���̾�Ʈ");
						while(!stopaudioCapture){ //����� ������ �ƴϸ�
							//int cnt = targetDataLine.read(buffer, 0, buffer.length); //���� ���� ����
							//if(cnt > 0){ //���� ���̰� 0���� ũ��
							String str = "ASDFASDF";
							byte[] buffer = str.getBytes();
							DatagramPacket sendpacket = new DatagramPacket(buffer, buffer.length, ip, port);
							//�ְ� ���� �����Ϳ� ���� Ŭ����
							socket.send(sendpacket); 
							//��Ŷ ����
							//}
						}
						socket.close(); 
						//���� ����
					}

				} catch(IOException e){ //����
					e.printStackTrace(); //���� ��Ȳ �ذ�
				}
			}
		};
		
		Thread thread = new Thread(sendRunner);
		thread.start();

	}
}
