package project.oss.sunmoon.udp;

//����Ʈ ������ ���� ���� ����
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

//Ŭ���̾�Ʈ, ������ ����ϴ� ���� ����
import java.io.IOException;
import java.io.InputStream;

//��Ʈ��ũ ���� ����
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//���� ����
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

//����� ��� ���� ����
import javax.sound.sampled.TargetDataLine;

public class AudioClient{

	boolean stopaudioCapture = false; //����� ����
	private AudioFormat getFormat = getFormat(); //����� ������� ����
	ByteArrayOutputStream data; //����Ʈ ������
	TargetDataLine targetDataLine; //����� ���
	private boolean flag = false; //����
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

	private AudioFormat getFormat() {
		float sampleRate = 8000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	public AudioClient(String ipAddress, int port){

		try {		
			final AudioFormat format = getFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			TargetDataLine line;
			line = (TargetDataLine) AudioSystem.getLine(info);		
			line.open(format);
			line.start();
			Runnable sendRunner = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
						byte buffer[] = new byte[bufferSize];
						DatagramSocket socket = new DatagramSocket(); //������ ���� ����
						InetAddress ip = InetAddress.getByName(ipAddress); //������ ip��ȣ
						while(true)
						{							
							System.out.println("���⼭ ���ѷ��� Ŭ���̾�Ʈ");
							while(!stopaudioCapture){ //����� ������ �ƴϸ�
								int cnt = line.read(buffer, 0, buffer.length); //���� ���� ����
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

					}catch(IOException e){ //���� ó��
						e.printStackTrace();
					}
				}
			};

			Thread thread = new Thread(sendRunner);
			thread.start();

		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
