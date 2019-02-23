package Web.UpdateResume;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;

public class MainGUI extends JFrame implements ActionListener, ItemListener{

private static final long serialVersionUID = 1L;
private ArrayList<JCheckBox> list = new ArrayList<JCheckBox>();
private JTextField fileTF;
private String filePath = "";
private JComboBox<String> driverCbox;
private File chromeTempFile = null;
//private File ieTempFile = null;

	public MainGUI() {
		
		setChromeWebDriver();
		//setIEWebDriver();
		
		//instantiate panels
		JPanel mainPanel = new JPanel();
		JPanel driverPanel = new JPanel();
		JPanel contentPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel filePanel = new JPanel();
		
		//instantiate checkboxes
		JCheckBox indeedCB = new JCheckBox("Indeed");
		JCheckBox glassdoorCB = new JCheckBox("Glassdoor");
		JCheckBox monsterCB = new JCheckBox("Monster");
		JCheckBox zipCB = new JCheckBox("ZipRecruiter");
		JCheckBox allCB = new JCheckBox("All");
			
		//instantiate buttons
		JButton updateB = new JButton("Update");
		JButton cancelB = new JButton("Cancel");
		JButton fileB = new JButton("File");
		
		//instantiate TextArea
		fileTF = new JTextField("Please select your resume",25);
		
		//instantiate comboBox
		driverCbox = new JComboBox<String>(new String[]{"Internet Explorer", "Google Chrome", "Mozilla Firefox"});
		driverCbox.setSelectedIndex(1);
		driverCbox.setEnabled(false);
		
		//configurations
		setSize(500,220);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Resume Updates");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));	
		fileTF.setEditable(false);
		
		//add CheckBox to ArrayList
		list.add(indeedCB);
		list.add(glassdoorCB);
		list.add(monsterCB);
		list.add(zipCB);
		
		//add listeners to component
		updateB.addActionListener(this);
		cancelB.addActionListener(this);
		fileB.addActionListener(this);
		allCB.addItemListener(this);
		
		//add components to panels
		contentPanel.add(indeedCB);
		contentPanel.add(glassdoorCB);
		contentPanel.add(monsterCB);
		contentPanel.add(zipCB);
		contentPanel.add(allCB);
		buttonPanel.add(updateB);
		buttonPanel.add(cancelB);
		filePanel.add(fileTF);
		filePanel.add(fileB);
		driverPanel.add(driverCbox);
		
		//add panels to mainPanel
		mainPanel.add(driverPanel);
		mainPanel.add(contentPanel);
		mainPanel.add(filePanel);
		mainPanel.add(buttonPanel);

		//set mainPanel to Frame
		setContentPane(mainPanel);
		
		//setVisible need to place at the end so components would display when application launches
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {	
		//instantiate variables
		String event = e.getActionCommand();
		UserInfo user;
		Script script;
		WebDriver driver = null;
		
		//update button is clicked
		if(event.equals("Update")) {
			//check if user input filePath
			if(!filePath.isEmpty())
			{
				//check if a site has been selected
				if(list.get(0).isSelected() || list.get(1).isSelected() || list.get(2).isSelected() || list.get(3).isSelected()) {
					user = new UserInfo();
					
					//check each checkbox to see if they've been selected
					for(int i=0; i<list.size(); i++) {
						if(list.get(i).isSelected()) {
								
								//check if user has saved email and password
								if(!user.isSavedForAll()) {
									
									//instructions information
									switch(i) {
										case 0: JOptionPane.showMessageDialog(null, "Please enter user information for Indeed", "Information", JOptionPane.INFORMATION_MESSAGE); break;
										case 1: JOptionPane.showMessageDialog(null, "Please enter user information for Glassdoor", "Information", JOptionPane.INFORMATION_MESSAGE); break;
										case 2: JOptionPane.showMessageDialog(null, "Please enter user information for Monster", "Information", JOptionPane.INFORMATION_MESSAGE); break;
										case 3: JOptionPane.showMessageDialog(null, "Please enter user information for ZipRecruiter", "Information", JOptionPane.INFORMATION_MESSAGE); break;
										default: JOptionPane.showMessageDialog(null, "An error has occured", "Error", JOptionPane.ERROR_MESSAGE); break; }//switch end
									
									//user jdialog opens up requiring user input
									user.setUserInfo();
								}//if end
								
								//check if user has inputed information
								if(user.getStatus()) {
									/*
									if(driverCbox.getSelectedItem().toString().equals("Internet Explorer"))
										driver = new InternetExplorerDriver();
									else if(driverCbox.getSelectedItem().toString().equals("Google Chrome"))
										driver = new ChromeDriver();
									else
										driver = new FirefoxDriver();*/
									
									//setting driver
									driver = new ChromeDriver();
									
									//instantiate script
									script = new Script(driver);
									
									switch(i) {
										case 0: script.indeed(user.getEmail(), user.getPassword(), this.getFilePath()); break;
										case 1: script.glassdoor(user.getEmail(), user.getPassword(), this.getFilePath()); break;
										case 2: script.monster(user.getEmail(), user.getPassword(), this.getFilePath()); break;
										case 3: script.zip(user.getEmail(), user.getPassword(), this.getFilePath()); break;
										default: JOptionPane.showMessageDialog(null, "An error has occured", "Error", JOptionPane.ERROR_MESSAGE); break; }//switch end
									
									driver.quit();
								}//if end 
								else {
									switch(i) {
									case 0: JOptionPane.showMessageDialog(null, "Failed to enter user information for Indeed. Exiting script.", "Information", JOptionPane.INFORMATION_MESSAGE); break;
									case 1: JOptionPane.showMessageDialog(null, "Failed to enter user information for Glassdoor. Exiting script.", "Information", JOptionPane.INFORMATION_MESSAGE); break;
									case 2: JOptionPane.showMessageDialog(null, "Failed to enter user information for Monster. Exiting script.", "Information", JOptionPane.INFORMATION_MESSAGE); break;
									case 3: JOptionPane.showMessageDialog(null, "Failed to enter user information for ZipRecruiter. Exiting script.", "Information", JOptionPane.INFORMATION_MESSAGE); break;
									default: JOptionPane.showMessageDialog(null, "An error has occured", "Error", JOptionPane.ERROR_MESSAGE); break; }//switch end
								
								}//else end							
						}//if end
					}//for loop end
				}//if end
				else {
					JOptionPane.showMessageDialog(null, "Please select a site", "Missing Information", JOptionPane.ERROR_MESSAGE);
				}//else end
			}//if end
			else {
				JOptionPane.showMessageDialog(null, "Please select a resume", "Missing Information", JOptionPane.ERROR_MESSAGE);
			}//else end
		}//if end
		
		//file button is clicked
		if(event.equals("File")) {
			JFileChooser file = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			
			int returnValue = file.showOpenDialog(null);
			
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				fileTF.setText(file.getSelectedFile().getAbsolutePath());
				filePath = file.getSelectedFile().getAbsolutePath();
			}
		}
		
		//cancel button is clicked
		if(event.equals("Cancel")) {
			//reset each checkbox 
			for(JCheckBox cb : list) {
				cb.setSelected(false);
			}
			
			//reset textfield
			fileTF.setText("Please select your resume");
		}
	}
	
	//get filepath
	public String getFilePath() {
		return filePath;
	}

	//when all checkbox is clicked
	public void itemStateChanged(ItemEvent e) {
		//select all checkboxes and disable it
		if(e.getStateChange() == ItemEvent.SELECTED) {
			System.out.println("asdfasdfasfd");
			for(int i=0; i<list.size(); i++) {
				JCheckBox temp = list.get(i);
				temp.setSelected(true);
				temp.setEnabled(false);
			}
		} else { 
			for(int i=0; i<list.size(); i++) {
				JCheckBox temp = list.get(i);
				temp.setSelected(false);
				temp.setEnabled(true);
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		new MainGUI();
	}
	
	//Chrome Temp Exe
	public void setChromeWebDriver() {
		
		try {
			//locate the exe inside jar
			InputStream src = this.getClass().getClassLoader().getResourceAsStream("chromedriver.exe");
			
			//create temp file
			chromeTempFile = File.createTempFile("chromedriver", ".exe");
			chromeTempFile.deleteOnExit();
			
			//stream data to temp file
			FileOutputStream out = new FileOutputStream(chromeTempFile);
			byte[] temp = new byte[3048];
			int rc;
			
			while((rc = src.read(temp)) > 0)
			    out.write(temp, 0, rc);			
			
			//setting property
			System.setProperty("webdriver.chrome.driver", chromeTempFile.toString());
			
			//close streams 
			src.close();
			out.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/*//Internet Explorer Temp File
	public void setIEWebDriver() {
		
		try {
			InputStream src = this.getClass().getClassLoader().getResourceAsStream("IEDriverServer.exe");
			ieTempFile = File.createTempFile("IEDriverServer", ".exe");
			ieTempFile.deleteOnExit();
			FileOutputStream out = new FileOutputStream(ieTempFile);
			byte[] temp = new byte[3048];
			int rc;
			while((rc = src.read(temp)) > 0)
			    out.write(temp, 0, rc);
			
			System.setProperty("webdriver.ie.driver", ieTempFile.toString());

			src.close();
			out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}*/
	
}
