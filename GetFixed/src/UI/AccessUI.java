package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;


import javax.swing.JTextField;
import javax.swing.JButton;

import Control.CtrSession;

import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccessUI extends JFrame {
	private JPanel contentPane;
	private JTextField textPassword;
	private JTextField textUser;
	private CtrSession ctrSession = new CtrSession();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccessUI frame = new AccessUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AccessUI() {
		setTitle("Log in GetFixed");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 343, 213);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel panel = new Panel();
		panel.setBounds(10, 10, 306, 158);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(41, 25, 77, 14);
		panel.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(41, 71, 75, 14);
		panel.add(lblPassword);

		textUser = new JTextField();
		textUser.setBounds(147, 22, 86, 20);
		panel.add(textUser);
		textUser.setColumns(10);

		textPassword = new JTextField();
		textPassword.setBounds(147, 68, 86, 20);
		panel.add(textPassword);
		textPassword.setColumns(10);

		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (logIn()) {
					dispose();
					
					MainUI main = new MainUI();
					main.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Username or password is incorrect!", "Login error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLogIn.setBounds(104, 122, 89, 23);
		panel.add(btnLogIn);

	}

	public boolean logIn() {
		int login = -1;
		String name;
		String password = textPassword.getText();
		try {
			name = (textUser.getText());
			login = ctrSession.login(name, password);
		} catch (Exception ex) {

		}

		if (login > -1)
			return true;
		else
			return false;
	}

}
