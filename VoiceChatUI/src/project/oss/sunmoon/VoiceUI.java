package project.oss.sunmoon;

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

	private JPanel contentPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private HintTextField iptext;
	private JButton btnNewButton;
	private JPanel panel_3;
	private JLabel hint;
	private boolean flag = false;	
	
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
		setIconImage(Toolkit.getDefaultToolkit().getImage(VoiceUI.class.getResource("/project/oss/sunmoon/micImage.png")));
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
		
		JButton disconnectBtn = new JButton("Disconnect");
		disconnectBtn.setEnabled(false);
		disconnectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Disconnect누를때 이벤트");
				
				connectBtn.setEnabled(true);
				disconnectBtn.setEnabled(false);
				iptext.setEditable(true);
			}
		});
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
		//statePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("");
		statePanel.add(lblNewLabel);
		
		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String ip = iptext.getText();
				if(PATTERN.matcher(ip).matches())
				{
					statePanel.removeAll();
					connectBtn.setEnabled(false);
					disconnectBtn.setEnabled(true);
					iptext.setEditable(false);
					
					//statePanel.removeAll();
					JLabel lblState = new JLabel();
					lblState.setText("Connected with " + iptext.getText() + "\n");
					statePanel.add(lblState);
					statePanel.validate();
					statePanel.repaint();		
				}
				else
				{
					JLabel lblState = new JLabel();
					lblState.setText("Non IP.");
					statePanel.add(lblState);
					statePanel.validate();
					statePanel.repaint();
				}
			}
			
			
		});
	}

}
