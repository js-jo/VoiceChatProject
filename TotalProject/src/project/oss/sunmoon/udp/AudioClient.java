package project.oss.sunmoon.udp;

import java.io.ByteArrayInputStream;
//바이트 데이터 관련 소켓 선언
import java.io.ByteArrayOutputStream;
//클라이언트, 서버를 사용하는 소켓 선언
import java.io.IOException;
import java.io.InputStream;
//네트워크 소켓 선언
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//사운드 선언
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
//오디오 출력 소켓 선언
import javax.sound.sampled.TargetDataLine;

public class AudioClient{

	boolean stopaudioCapture = false; //오디오 멈춤
	//private static final int bufferSize = 10000; //버퍼사이즈 지정
	//@SuppressWarnings("unused")
	private AudioFormat getFormat = getFormat(); //오디오 재생형식 지정
	ByteArrayOutputStream data; //바이트 데이터
	TargetDataLine targetDataLine; //오디오 출력
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
		//		DatagramSocket socket = null;
		//		data = new ByteArrayOutputStream(); //바이트 데이터로 저장
		//		//InputStream input = new ByteArrayInputStream(audio); //바이트 데이터를 읽음
		//		stopaudioCapture = false; //오디오 멈춤
		//		setPort(port);
		//		final AudioFormat format = getFormat();
		//		int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
		//		byte buffer[] = new byte[bufferSize];
		//		

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
						DatagramSocket socket = new DatagramSocket();
						InetAddress ip = InetAddress.getByName(ipAddress); //전송할 ip번호
						while(true)
						{						//						
							//byte audio[] = out.getByteArray(); //전송할 음성 데이터
							System.out.println("여기서 무한루프 클라이언트");
							while(!stopaudioCapture){ //오디오 멈춤이 아니면
								int cnt = line.read(buffer, 0, buffer.length); //버퍼 길이 읽음
								if(cnt > 0){ //버퍼 길이가 0보다 크면
									//String str = "야이 멍청아";
									//byte[] buffer = str.getBytes();
									DatagramPacket sendpacket = new DatagramPacket(buffer, buffer.length, ip, port);
									//주고 받을 데이터와 관련 클래스
									socket.send(sendpacket); 
									//패킷 전송
								}
							}
							socket.close(); 
							//소켓 종료
						}

					}catch(IOException e){ //예외
						e.printStackTrace(); //예외 상황 해결
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
