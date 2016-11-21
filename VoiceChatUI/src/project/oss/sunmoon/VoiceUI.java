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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

public class VoiceUI extends JFrame {

	private JPanel contentPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextField iptext;
	private JButton btnNewButton;
	private JPanel panel_3;
	private boolean flag = false;		
	
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
		
		iptext = new JTextField();
		ipPanel.add(iptext);

		iptext.setColumns(20);
		iptext.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				String str = iptext.getText();
				if(!flag)
				{
					if(str.compareTo("") == 0)
					{
						iptext.setText("아이피를 입력하세요");
					}
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
			}});
	
		iptext.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				if(!flag)
				{
					flag = true;
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated asmethod stub
				System.out.println("assdfsdfsdfdf");
				if(flag)
				{
					String str = iptext.getText();
					if(str == "")
					{
						flag = false;
					}
				}
			}});
		
		JPanel panel_5 = new JPanel();
		ipPanel.add(panel_5, BorderLayout.EAST);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JButton connectBtn = new JButton("Connect");
		panel_5.add(connectBtn);
		
		JButton disconnectBtn = new JButton("Disconnect");
		panel_5.add(disconnectBtn, BorderLayout.SOUTH);
		
		JPanel panel_4 = new JPanel();
		ipPanel.add(panel_4, BorderLayout.SOUTH);
		
		JPanel statePanle = new JPanel();
		statePanle.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(statePanle, BorderLayout.CENTER);
		statePanle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}

}
