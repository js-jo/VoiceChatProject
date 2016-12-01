package project.oss.sunmoon.UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import project.oss.sunmoon.udp.AudioClient;
import project.oss.sunmoon.udp.AudioServer;

import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Toolkit;

public class VoiceUI extends JFrame {

	private Thread staySendThread;
	private AudioServer receiver;
	private JPanel contentPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private HintTextField iptext;
	private JButton btnNewButton;
	private JPanel panel_3;
	private JLabel hint;
	private final int port = 10002;
	private boolean flag = false;	
	private Font font = new Font("돋움" , Font.BOLD,20);
	private static final Pattern PATTERN = Pattern.compile(
			"^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VoiceUI frame = new VoiceUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VoiceUI() {
		setTitle("Voice Chat");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VoiceUI.class.getResource("/project/oss/sunmoon/UI/micImage.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel ipPanel = new JPanel();
		northPanel.add(ipPanel);
		ipPanel.setLayout(new BorderLayout(5, 10));
		
		iptext = new HintTextField("아이피를 입력해주세요.");
		ipPanel.add(iptext);
		iptext.setColumns(20);
		
		JPanel btnPanle = new JPanel();
		ipPanel.add(btnPanle, BorderLayout.EAST);
		btnPanle.setLayout(new BorderLayout(0, 0));
		
		JButton connectBtn = new JButton("Connect");

		btnPanle.add(connectBtn, BorderLayout.NORTH);
		
		JButton disconnectBtn = new JButton("Exit");
		disconnectBtn.setEnabled(false);
		btnPanle.add(disconnectBtn, BorderLayout.SOUTH);
		
		JPanel labelPanel = new JPanel();
		ipPanel.add(labelPanel, BorderLayout.SOUTH);
		labelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel stateLabel = new JLabel("State");
		stateLabel.setFont(new Font("굴림", Font.BOLD, 18));
		labelPanel.add(stateLabel, BorderLayout.SOUTH);
		
		JPanel statePanel = new JPanel();
		statePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(statePanel, BorderLayout.CENTER);
		statePanel.setLayout(new BoxLayout(statePanel, BoxLayout.Y_AXIS));
		
		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String ip = iptext.getText();
				
				if(PATTERN.matcher(ip).matches())
				{
					statePanel.removeAll();
					connectBtn.setEnabled(false);
					disconnectBtn.setEnabled(true);
					iptext.setEditable(false);

					JLabel lblState = new JLabel();
					lblState.setFont(font);
					statePanel.add(lblState);
					statePanel.validate();
					statePanel.repaint();
					
					
					Runnable stayRunner = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							receiver = new AudioServer(ip, statePanel, port);
							receiver.receiveAudio();							
						}
					};
					
					//client.setFlag(true);
					staySendThread = new Thread(stayRunner);
					staySendThread.start();
					
					AudioClient client = new AudioClient(ip, port);
				}
				else
				{
					statePanel.removeAll();
					JLabel lblState = new JLabel();
					lblState.setText("Non IP.");
					lblState.setFont(font);
					statePanel.add(lblState);
					statePanel.validate();
					statePanel.repaint();
				}
			}
			
			
		});
		
		disconnectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectBtn.setEnabled(true);
				disconnectBtn.setEnabled(false);
				iptext.setEditable(true);
				System.exit(-2);
				//staySendThread.interrupt();
			}
		});

	}

}
