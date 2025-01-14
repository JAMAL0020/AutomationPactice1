import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import net.bytebuddy.implementation.bytecode.ByteCodeAppender.Size;

public class MyTestCases {

	WebDriver driver = new ChromeDriver();

	String Website = "https://codenboxautomationlab.com/practice/";

	Random rand = new Random();

	JavascriptExecutor Js = (JavascriptExecutor) driver;
	
	// this is related to test #11
	Actions action = new Actions (driver);

	@BeforeTest
	public void mySetup() {

		driver.manage().window().maximize();
		driver.get(Website);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
	}

	@Test(priority = 1, description = "Radio Button", invocationCount = 1, enabled = false)
	public void Radio_Button_Example() throws InterruptedException {

		List<WebElement> AllRadioButtons = driver.findElements(By.className("radioButton"));

		// to click on the first item
		// AllRadioButtons.get(0).click();

		int RadnomIndex = rand.nextInt(AllRadioButtons.size()); // size is 3 so it will generate 0,1,2
		AllRadioButtons.get(RadnomIndex).click();

		// this is new code for test 1
		boolean expecteddddddddResult = true;
		boolean ActualResult = AllRadioButtons.get(RadnomIndex).isSelected();

		Assert.assertEquals(ActualResult, expecteddddddddResult);

	}

	@Test(priority = 2, description = "dropdown Dynamic", enabled = false)
	public void Dynamic_Dropdown_Example() throws InterruptedException {

		// generate random String in a static way because i dont need my test to include
		// any other data
		String[] countryCodes = { "US", "CA", "OM", "BR", "AR", "FR", "DE", "IT", "ES" };

		// random index based on the length ofn the above array form 0,1,2,3,....8
		int randomIndex = rand.nextInt(countryCodes.length);

		// webelement to the input filed (country)
		WebElement DynamicListInput = driver.findElement(By.id("autocomplete"));

		// send a random item from my arry to the webelement (inputfiled)
		DynamicListInput.sendKeys(countryCodes[randomIndex]);

		Thread.sleep(1000);

		// it will press an arrow down + enter to select the first item from the list
		DynamicListInput.sendKeys(Keys.chord(Keys.ARROW_DOWN, Keys.ENTER));

		// i need to capture or take the country name that selenium already selected
		String DataInsideMyInput = (String) Js.executeScript("return arguments[0].value", DynamicListInput);

		// the country name for example United Arab Emirates contains capital letters
		// and small ones
		// so what i did i make all the letters in small (for example France , and South
		// Africa look at the 'fr and Fr')
		String updateDataInMyInput = DataInsideMyInput.toLowerCase();

		boolean ActualValue = updateDataInMyInput.contains(countryCodes[randomIndex].toLowerCase());

		boolean ExpectedResult = true;

		Assert.assertEquals(ActualValue, ExpectedResult);

	}

	@Test(priority = 3, description = "static dropdown list", enabled = false)
	public void Static_Dropdown_Example() {

		WebElement SelectElement = driver.findElement(By.id("dropdown-class-example"));

		Select sel = new Select(SelectElement);

		// sel.selectByIndex(3);
		// sel.selectByValue("option2");
		sel.selectByVisibleText("API");

	}

	@Test(priority = 4, description = "check box example", invocationCount = 1, enabled = false)
	public void Checkbox_Example() throws InterruptedException {

		List<WebElement> CheckBoxes = driver.findElements(By.xpath("//input[@type= 'checkbox']"));
		int randomIndex = rand.nextInt(CheckBoxes.size());
		System.out.println(CheckBoxes.size());

		Thread.sleep(1000);

		// CheckBoxes.getFirst().click();
		// CheckBoxes.getLast().click();
		// CheckBoxes.getLast(96).click();

		// CheckBoxes.get(randomIndex).click();

		// to select them all
		for (int i = 0; i < CheckBoxes.size(); i++) {
			CheckBoxes.get(i).click();
			boolean ActualResult = CheckBoxes.get(i).isSelected();
			boolean expectedReslut = true;

			Assert.assertEquals(ActualResult, expectedReslut);

		}

	}

	@Test(priority = 5, description = "this is to move from window to another one", enabled = false)
	public void Switch_Window_Example() throws InterruptedException {

		WebElement openWidnowButton = driver.findElement(By.id("openwindow"));
		openWidnowButton.click();
		Thread.sleep(2000);

		List<String> windowsHandels = new ArrayList<String>(driver.getWindowHandles());

		// number of open windows
		System.out.println(windowsHandels.size());

		// switch to the other window
		driver.switchTo().window(windowsHandels.get(1));
		// in the second window
		WebElement ContactButton = driver.findElement(By.id("menu-item-9680"));
		ContactButton.click();

		System.out.println(driver.getTitle() + " hello from the second window ");

		driver.close();
		// switch to the first window
		driver.switchTo().window(windowsHandels.get(0));

	}

	@Test(priority = 6, description = "check moving to another tab", enabled = false)
	public void Switch_Tab_Example() throws InterruptedException {

		WebElement OpenTabButton = driver.findElement(By.id("opentab"));
		OpenTabButton.click();

		List<String> windowsHandels = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(windowsHandels.get(1));
		Thread.sleep(2000);
		System.out.println(driver.getTitle());

	}

	@Test(priority = 7, description = "Alert and Confirm", enabled = false)
	public void Switch_To_Alert_Example() throws InterruptedException {
		WebElement nameBox = driver.findElement(By.id("name"));
		nameBox.sendKeys("Obada");
		// WebElement AlretBox = driver.findElement(By.id("alertbtn"));
		// AlretBox.click();

		Thread.sleep(2000);
		// driver.switchTo().alert().accept();
		// driver.switchTo().alert().dismiss();

		WebElement ConfirmBox = driver.findElement(By.id("confirmbtn"));
		ConfirmBox.click();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();

	}

	@Test(priority = 8, description = "play with the data of the colum", enabled = false)

	public void Web_Table_Example() {

		WebElement TheTabel = driver.findElement(By.id("product"));
		List<WebElement> theDataInsideTheTable = TheTabel.findElements(By.tagName("tr"));

		for (int i = 1; i < theDataInsideTheTable.size(); i++) {

			int totalTdInTheRow = theDataInsideTheTable.get(i).findElements(By.tagName("td")).size();

			System.out.println(
					theDataInsideTheTable.get(i).findElements(By.tagName("td")).get(totalTdInTheRow - 1).getText());
		}

	}

	@Test(priority = 9, description = "this is to test hide and show buttons", enabled = false)
	public void Element_Displayed_Example() throws InterruptedException {

		JavascriptExecutor Js = (JavascriptExecutor) driver;
		SoftAssert myAssertion = new SoftAssert();

		// softassert if one test it will continue to the rest of the code

		// hardassert once failed it will stop all the execution

		Js.executeScript("window.scrollTo(0,1500)");

		WebElement HideButton = driver.findElement(By.id("hide-textbox"));
		WebElement ShowButton = driver.findElement(By.id("show-textbox"));

		HideButton.click();

		WebElement theTEXXXXXTINPUT = driver.findElement(By.id("displayed-text"));

		// hard assert
		// Assert.assertEquals(theTEXXXXXTINPUT.isDisplayed(), true);

		myAssertion.assertEquals(theTEXXXXXTINPUT.isDisplayed(), false);

		Thread.sleep(4000);
		
		ShowButton.click();
		Assert.assertEquals(theTEXXXXXTINPUT.isDisplayed(), true);

		myAssertion.assertAll();

	}

	@Test(priority = 10, description = "check The Both Buttons disable , enable" , enabled = false)
	public void Enabled_Disabled_Example() throws InterruptedException {
		
		WebElement DisabledButton = driver.findElement(By.id("disabled-button"));
		WebElement EnabledButton = driver.findElement(By.id("enabled-button"));

		DisabledButton.click();

		WebElement enabled_example_input = driver.findElement(By.id("enabled-example-input"));

		boolean ActucalResult = enabled_example_input.isEnabled();

		boolean ExpectedResult = false;

		Assert.assertEquals(ActucalResult, ExpectedResult);
		
		Thread.sleep(1000);
		EnabledButton.click();
		boolean ActualResult2 = enabled_example_input.isEnabled();
		enabled_example_input.sendKeys("123");
		boolean ExpectedResult2 = true ; 
		
		Assert.assertEquals(ActualResult2, ExpectedResult2);	
	}
	
	@Test(priority = 11, description = "check the hover to certain element" , enabled = false)
	public void Mouse_Hover_Example() throws InterruptedException {
		
		Js.executeScript("window.scrollTo(0,1800)");
		Thread.sleep(2000);
		

		WebElement MouseHoverElement = driver.findElement(By.id("mousehover"));
		
		action.moveToElement(MouseHoverElement).perform();
				
		//driver.findElement(By.linkText("Top")).click();
		driver.findElement(By.partialLinkText("Reload")).click();

	}
	
	@Test(priority = 12 , description = "open calendar in a new tab" , enabled = false)
	public void Calendar_Example() throws InterruptedException {
		
		Js.executeScript("window.scrollTo(0,1900)");

		//WebElement Calednar2 = driver.findElement(By.linkText("Booking Calendar"));
		WebElement Calednar2 = driver.findElement(By.partialLinkText("Booking Calendar"));

		Calednar2.click();
		Thread.sleep(1000);
		
		 List<String> windowsHandels = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windowsHandels.get(1));
			
			System.out.println(driver.getTitle());
			
			int totalAvailbleDates = driver.findElements(By.className("date_available")).size();
			driver.findElements(By.className("date_available")).get(0).click();
			driver.findElements(By.className("date_available")).get(totalAvailbleDates -1).click();	
	
	}



	@Test(priority = 13,description = "switch to frame insdie the main page" , enabled = false)
	public void iFrame_Example() {
		
		WebElement theFrame = driver.findElement(By.id("courses-iframe"));
		//by index
		driver.switchTo().frame(0);
		//by id
		driver.switchTo().frame("courses-iframe");
		//by webelement
		driver.switchTo().frame(theFrame);

		
		String theText = driver.findElement(By.xpath("//*[@id=\"ct_text_editor-be8c5ad\"]/div/div/p")).getText();
		
		System.out.println(theText);
	}

	
	
	@Test(priority = 14,description = "download the file inside the main page" , enabled = false)
	public void Download_file_to_test() {
		
		//WebElement TheFile = driver.findElement(By.xpath("//a[@href='http://codenboxautomationlab.com/wp-content/uploads/2022/12/APKFiles-1.zip']"));
		//WebElement TheFile = driver.findElement(By.cssSelector(".wp-block-button__link.wp-element-button"));
		WebElement TheFile = driver.findElement(By.xpath("//a[@class='wp-block-button__link wp-element-button']"));
             
		TheFile.click();
	
	}
	

	@Test(priority = 15, enabled = false)
	public void CheckTheTitle() {

		String expected = "Automation Practice - CodenBox AutomationLab";

		String ActualTitle = driver.getTitle();

		Assert.assertEquals(ActualTitle, expected);

	}
	
	//erf3o git hub

}
