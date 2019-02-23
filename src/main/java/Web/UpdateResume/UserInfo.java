package Web.UpdateResume;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class UserInfo extends JDialog implements ActionListener, ItemListener{

	private static final long serialVersionUID = 1L;
	private JTextField emailTF;
	private JPasswordField passPF;
	private JPasswordField confirmPassPF;
	private JCheckBox allCB;
	private String email = "";
	private String password = "";
	private boolean savedForAll = false;
	private boolean status = false;
	
	public UserInfo() {
		
		//instantiate components
		JPanel mainPanel = new JPanel();
		JPanel textPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JLabel emailL = new JLabel("Email");
		JLabel passL = new JLabel("Password");
		JLabel confirmPassL = new JLabel("Confirm Password");
		emailTF = new JTextField(20);
		passPF = new JPasswordField(20);
		confirmPassPF = new JPasswordField(20);
		allCB = new JCheckBox("Save Email and Password for All");
		JButton confirmB = new JButton("Confirm");
		JButton resetB = new JButton("Reset");
		GroupLayout layout = new GroupLayout(textPanel);
		
		//configuration
		this.setModal(true);
		setTitle("User Information");
		setSize(400,200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		textPanel.setLayout(layout);
		
		//add listeners
		confirmB.addActionListener(this);
		resetB.addActionListener(this);
		allCB.addItemListener(this);
		
		//add components to panel
		layout.setHorizontalGroup( 
				layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(emailL)
							.addComponent(passL)
							.addComponent(confirmPassL))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(emailTF)
							.addComponent(passPF)
							.addComponent(confirmPassPF)
							.addComponent(allCB))
		);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(emailL)
							.addComponent(emailTF))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(passL)
							.addComponent(passPF))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(confirmPassL)
							.addComponent(confirmPassPF))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(allCB))
		);
		
		buttonPanel.add(confirmB);
		buttonPanel.add(resetB);
		mainPanel.add(textPanel);
		mainPanel.add(buttonPanel);
		
		//set panel to frame
		setContentPane(mainPanel);
			
	}

	//get email
	public String getEmail() {
		return email;
	}
	
	//get savedForAll
	public boolean isSavedForAll() {
		return savedForAll;
	}
	
	//get password
	public String getPassword() {
		return password;
	}
	
	//get status
	public boolean getStatus() {
		return status;
	}
	
	//reset all fields
	public void reset() {
		emailTF.setText("");
		passPF.setText("");
		confirmPassPF.setText("");
		allCB.setSelected(false);
		email = "";
		password = "";
		savedForAll = false;
		status = false;
	}
	
	//set UserInfo
	public void setUserInfo() {
		this.reset();
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		
		//confirm button clicked
		if(event.equals("Confirm")) {
			
			//check if user input email
			if(!emailTF.getText().isEmpty()) {
				
				//check if passwords match 
				if(String.valueOf(passPF.getPassword()).equals(String.valueOf(confirmPassPF.getPassword())) && !String.valueOf(passPF.getPassword()).isEmpty() && !String.valueOf(confirmPassPF.getPassword()).isEmpty()) {
					email = emailTF.getText();
					password = String.valueOf(passPF.getPassword());
					status = true;
					this.setVisible(false);
				}//if end
				else {
					JOptionPane.showMessageDialog(null, "Please enter matching password.", "Missing Information", JOptionPane.ERROR_MESSAGE);
					passPF.setText("");
					confirmPassPF.setText("");
				}//else end
			} else {
				JOptionPane.showMessageDialog(null, "Please enter your email.", "Missing Information", JOptionPane.ERROR_MESSAGE);
			}//else end
		}//if end
		
		//reset button clicked
		if(event.equals("Reset")) {
			this.reset();
		}
	}

	//setting value for savedForAll depending on checkbox
	public void itemStateChanged(ItemEvent e) {
		if((e.getStateChange()== ItemEvent.SELECTED) ? (savedForAll = true) : (savedForAll = false));
	}
}
