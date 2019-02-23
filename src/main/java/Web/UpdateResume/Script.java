package Web.UpdateResume;

import java.util.List;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Script {

	final String COMPLETE = "Your resume has been updated. Please verify your information.";
	WebDriver driver;
	WebDriverWait wait;
	Actions action; 
	
	public Script() {
		
	}
	
	public Script(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 5);
		this.action = new Actions(driver);
	}
	
	public void indeed(String email, String password, String filePath) {
		
		try {
			//open indeed login page
			driver.get("https://secure.indeed.com/account/login?service=my&hl=en_US&co=US&continue=https%3A%2F%2Fwww.indeed.com%2F");
			
			//maximize window
			driver.manage().window().maximize();
			
			//login information
			wait.until(ExpectedConditions.elementToBeClickable(By.className("icl-GlobalFooter")));
			driver.findElement(By.name("__email")).sendKeys(email);
			driver.findElement(By.name("__password")).sendKeys(password);
			driver.findElement(By.name("login_tk")).submit();
			
			//open drop down list and click resume 
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='icl-DesktopGlobalHeader-toggleDropdown' and @role='navigation']")));
			driver.findElement(By.xpath("//span[@class='icl-DesktopGlobalHeader-toggleDropdown' and @role='navigation']")).click();
			driver.findElement(By.xpath("//a[@tabindex='0' and @class='icl-DesktopGlobalHeader-dropdownLink']")).click();
			
			//upload resume file path
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-tn-element='indeedHomepageLink']")));
			driver.findElement(By.xpath("//input[@type='file']")).sendKeys(filePath);
			
			//click next button
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-tn-element='nextBtn']")));
			driver.findElement(By.xpath("//button[@data-tn-element='nextBtn']")).click();
			
			/**
			//click continue button
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-tn-element='continueBtn']")));
			driver.findElement(By.xpath("//button[@data-tn-element='continueBtn']")).click();
			
			//click view resume link
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-tn-element='resume']")));
			driver.findElement(By.xpath("//a[@data-tn-element='resume']")).click();
			**/
			
			//Pop up to verify all information
			JOptionPane.showMessageDialog(null, this.COMPLETE, "Indeed Complete", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			driver.quit();
			JOptionPane.showMessageDialog(null, "An error has occured. Exiting script.", "Information", JOptionPane.INFORMATION_MESSAGE);
			System.out.println(e.toString());
		}
	}
	
	public void glassdoor(String email, String password, String filePath) {
		try {
			//open glassdoor homepage
			driver.get("https://www.glassdoor.com/index.htm");
			driver.manage().window().maximize();
			
			//click sign in button
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='susiLink sign-in']")));
			driver.findElement(By.xpath("//a[@class='susiLink sign-in']")).click();
			
			//login information
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='gd-btn gd-btn-1 fill']")));
			driver.findElement(By.name("username")).sendKeys(email);
			driver.findElement(By.name("password")).sendKeys(password);
			driver.findElement(By.xpath("//button[@class='gd-btn gd-btn-1 fill']")).submit();
			
			//get to resume page
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='About / Press']")));
			action.moveToElement(driver.findElement(By.xpath("//li[@class='signed-in']"))).perform();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-hook='manage-resumes' and text()='My Resumes']")));
			driver.findElement(By.xpath("//a[@data-hook='manage-resumes' and text()='My Resumes']")).click();
			
			//go to add resume page
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' + Add Resume']")));
			driver.findElement(By.xpath("//a[text()=' + Add Resume']")).click();
			
			//upload resume
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='link bailout padTop']")));
			driver.findElement(By.xpath("//input[@type='file' and @name='resume' and @id='FileUploadInput']")).sendKeys(filePath);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='ReplacePickedResume']")));
			driver.findElement(By.xpath("//span[text()='Upload']")).click();
			
			//go back to homepage
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='logo green']")));
			driver.findElement(By.xpath("//a[@class='logo green']")).click();
			
			//Pop up verifying resume is uploaded
			JOptionPane.showMessageDialog(null, this.COMPLETE, "Glassdoor Complete", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			driver.quit();
			JOptionPane.showMessageDialog(null, "An error has occured. Exiting script.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void monster(String email, String password, String filePath) {
		try {
			//Delete inputs from a text field
			String clear = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
			
			//open monster login page
			driver.get("https://login20.monster.com/Login/SignIn?ch=MONS&intcid=skr_navigation_www_signin");
			
			//maximize window
			driver.manage().window().maximize();
			
			//login information
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='m-logo' and @title='Go to Monster home']")));
			driver.findElement(By.id("EmailAddress")).sendKeys(email);
			driver.findElement(By.id("Password")).sendKeys(password);
			driver.findElement(By.id("btn-login")).submit();
			
			//Go to resume management page
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='m-logo' and @title='Go to Monster home']")));
			driver.findElement(By.xpath("//a[@class='dropdown-toggle navbar-icon-link loginLink2' and @data-toggle='dropdown']")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@gfltrack='1' and text()='Resumes/Cover Letters']")));
			driver.findElement(By.xpath("//a[@gfltrack='1' and text()='Resumes/Cover Letters']")).click();
			
			//Add resume
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='m-logo' and @title='Go to Monster home']")));
			driver.findElement(By.xpath("//a[@class='btn btn-md btn-block btn-purple-fill' and @rel='submitButton']")).click();
			
			//There are required input fields user needs to fill out
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='m-logo' and @title='Go to Monster home']")));
			
			//Obtain labels for the questions
			List<WebElement> labels = driver.findElements(By.xpath("//div[@class='form-group has-feedback']"));
			
			//Obtain the text fields for user inputs
			List<WebElement> inputs = driver.findElements(By.xpath("//input[contains(@class,'required')]"));
			
			//Ask user for input and update text field with input
			for(int i=0; i<labels.size(); i++)
			{
				inputs.get(i).sendKeys(clear + (JOptionPane.showInputDialog(labels.get(i).getText())));
			}
			
			//upload resume
			driver.findElement(By.xpath("//input[@id='fuUploadFromMyComputer' and @name='upload']")).sendKeys(filePath);
			
			//Pop up verifying resume is uploaded
			JOptionPane.showMessageDialog(null, this.COMPLETE, "Monster Complete", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			driver.quit();
			JOptionPane.showMessageDialog(null, "An error has occured. Exiting script.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void zip(String email, String password, String filePath) {
		try {
			//open zip recruiter login page
			driver.get("https://www.ziprecruiter.com/login?realm=candidates");
			
			//maximize window
			driver.manage().window().maximize();
			
			//login information
			wait.until(ExpectedConditions.elementToBeClickable(By.id("forgotPasswordLink")));
			driver.findElement(By.id("candidates_btn")).click();
			driver.findElement(By.name("email")).sendKeys(email);
			driver.findElement(By.name("password")).sendKeys(password);
			driver.findElement(By.name("submitted")).submit();
			
			//user select option
			if(JOptionPane.showConfirmDialog(null, "ZipRecruiter is protected by captcha. Please verify the images and click 'OK' to process.", "User Input Required", JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			
				//resume page
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit']")));
				action.moveToElement(driver.findElement(By.xpath("//span[@class='user_photo']"))).perform();
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='My Resume']")));
				driver.findElement(By.xpath("//a[text()='My Resume']")).click();
				
				//close pop up
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='close']")));
				driver.findElement(By.xpath("//button[@class='close']")).click();
				
				//upload resume
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='add_info']")));
				driver.findElement(By.xpath("//input[@type='file' and @name='ResumeUpload']")).sendKeys(filePath);
				
				//Pop up verifying resume is uploaded
				JOptionPane.showMessageDialog(null, this.COMPLETE, "ZipRecruiter Complete", JOptionPane.INFORMATION_MESSAGE);
			}//if end
			else {
				//Pop up script failed
				JOptionPane.showMessageDialog(null, "Exiting script.", "Information", JOptionPane.INFORMATION_MESSAGE);
			}//else end
		} catch (Exception e) {
			driver.quit();
			JOptionPane.showMessageDialog(null, "An error has occured. Exiting script.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
