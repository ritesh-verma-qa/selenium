package UITest;

import java.io.IOException;

import java.util.List;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import APIfiles.APItesting;
import io.restassured.path.json.JsonPath;

public class FrontEnd {
	int apiHumidity = 0;
	int fTempUI = 0;
	int cTempUI = 0;
	int humidityUI = 0;
	String Humidity = "";

	public void UITestValidation() throws InterruptedException, IOException

	{
		System.setProperty("webdriver.chrome.driver", "F:\\Selenium\\chromeDriver\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.get("https://www.ndtv.com/");
		Thread.sleep(9000);
		driver.manage().window().maximize();
		Thread.sleep(20000);
		driver.switchTo().activeElement();

		driver.findElement(By.xpath("//a[text()='No Thanks']")).click();
		Dimension n = new Dimension(360, 592);
		driver.manage().window().setSize(n);
		WebElement username = driver.findElement(By.id("h_sub_menu"));
		username.click();
		driver.manage().window().maximize();

		driver.findElement(By.linkText("WEATHER")).click();

		Dimension n2 = new Dimension(360, 592);
		driver.manage().window().setSize(n2);
		Thread.sleep(9000);

		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter the UI city for which you want weather report");
			String citty = sc.nextLine();
			driver.findElement(By.className("searchBox")).sendKeys(citty);

			driver.manage().window().maximize();
			WebElement checkbox = driver.findElement(By.id(citty));

			checkbox.click();

			WebElement cityName = driver.findElement(By.id(citty));
			cityName.click();
			if (cityName.isSelected()) { // to check if checkbox is selected

				driver.findElement(By.xpath("//div[text()='" + citty + "']")).click();
				String Humidity = driver.findElement(By.xpath("//span[3]/b")).getText(); //to extract String Humidity from UI

				String celciusT = driver.findElement(By.xpath("//span[4]/b")).getText(); //to extract temp from UI

				String fahranT = driver.findElement(By.xpath("//span[5]/b")).getText(); //to extract temp from UI

				List<WebElement> h = driver
						.findElements(By.xpath("/html/body/div[6]/div[1]/div[6]/div/div[1]/div/div/span[3]/b"));
				for (WebElement e : h) {
					System.out.println("Dummy" + e.getText().replace('%', ' '));
					String oldStr = e.getText().replace('%', ' ');
					String delStr = "Humidity:";
					String newStr;

					newStr = oldStr.replace(delStr, "");
					humidityUI = Integer.parseInt(newStr.trim());
					System.out.println("Humidity from UI:" + humidityUI);
					List<WebElement> f = driver
							.findElements(By.xpath("/html/body/div[6]/div[1]/div[6]/div/div[1]/div/div/span[5]/b"));
					for (WebElement c : f) {

						String oldStr1 = c.getText();
						String delStr1 = "Temp in Fahrenheit:";
						String newStr1;

						newStr1 = oldStr1.replace(delStr1, "");
						fTempUI = Integer.parseInt(newStr1.trim());
						System.out.println("The Temp in F from UI:" + fTempUI);
					}

					List<WebElement> t = driver
							.findElements(By.xpath("/html/body/div[6]/div[1]/div[6]/div/div[1]/div/div/span[4]/b"));
					for (WebElement d : t) {

						String oldStr2 = d.getText();
						String delStr2 = "Temp in Degrees:";
						String newStr2;

						newStr2 = oldStr2.replace(delStr2, "");
						cTempUI = Integer.parseInt(newStr2.trim());
						System.out.println("The Temp in C from UI is" + cTempUI);

					}

				}
			} else {
				System.out.println("Checkbox is Toggled Off");
				checkbox.click();
				System.out.println("now the check box is checked");
				driver.findElement(By.xpath("//div[text()='" + citty + "']")).click();
				String Humidity = driver.findElement(By.xpath("//span[3]/b")).getText(); //Extracting Humidity details in String from UI

				String celciusT = driver.findElement(By.xpath("//span[4]/b")).getText();//Extracting celcius details in String from UI

				String fahranT = driver.findElement(By.xpath("//span[5]/b")).getText(); //Extracting temperature details in String from UI

				//convert Humidity and temp string to relevant integer
				
				List<WebElement> h = driver
						.findElements(By.xpath("/html/body/div[6]/div[1]/div[6]/div/div[1]/div/div/span[3]/b"));
				for (WebElement e : h) {
					System.out.println("Dummy" + e.getText().replace('%', ' ')); // Humidity:54%
					String oldStr = e.getText().replace('%', ' ');
					String delStr = "Humidity:";
					String newStr;

					newStr = oldStr.replace(delStr, "");
					humidityUI = Integer.parseInt(newStr.trim());
					System.out.println("Humidity from UI:" + humidityUI);

					List<WebElement> f = driver
							.findElements(By.xpath("/html/body/div[6]/div[1]/div[6]/div/div[1]/div/div/span[5]/b"));
					for (WebElement c : f) {

						String oldStr1 = c.getText();
						String delStr1 = "Temp in Fahrenheit:";
						String newStr1;

						newStr1 = oldStr1.replace(delStr1, "");
						fTempUI = Integer.parseInt(newStr1.trim());
						System.out.println("The Temp in F from UI is" + fTempUI);

						List<WebElement> t = driver
								.findElements(By.xpath("/html/body/div[6]/div[1]/div[6]/div/div[1]/div/div/span[4]/b"));
						for (WebElement d : t) {

							String oldStr2 = d.getText();
							String delStr2 = "Temp in Degrees:";
							String newStr2;

							newStr2 = oldStr2.replace(delStr2, "");
							cTempUI = Integer.parseInt(newStr2.trim());
							System.out.println("The Temp in C from UI is" + cTempUI);

						}
					}
				}
			}

			String response = APItesting.WeatherReport();

			//parsing the response
			JsonPath Js = new JsonPath(response); 
			Float apiTemperature = Js.getFloat("main.temp"); //extracting temp(kelvin) 
			apiHumidity = Js.getInt("main.humidity"); //extracting humidity value
			Double Celcius = apiTemperature - 273.15; //converting into celcius 
			Double apiFahrenheit = (9 * (apiTemperature - 273.15) / 5) + 32; //converting into Fahrenheit

			int cTempAPI = (int) Math.round(Celcius);
			int fTempAPI = (int) Math.round(apiFahrenheit);
			System.out.println("The celcius temp from backend is" + cTempAPI);
			System.out.println("The farheniet temp from backend is" + fTempAPI);
			System.out.println("HUMIDITY from Backend is" + apiHumidity);
			int humidityDifference = apiHumidity - humidityUI;
			System.out.println(Math.abs(humidityDifference));
			int i = Math.abs(humidityDifference);

			int celciusDifference = cTempUI - cTempAPI;
			int farDifference = fTempUI - fTempAPI;
			
			int j = Math.abs(farDifference);
			int k = Math.abs(celciusDifference);
			System.out.println("the difference in Humidity is:" + i + " the difference in celcius is:" + k
					+ " the difference in F temp is:" + j);
			//created variance in Humidity (i) for 10% and Temperature Celcius(k) and Temperature Fahrenheit(j) for 2 degrees  
			if (i < 10 && j <= 2 && k <= 2) {
				System.out.println("The test is passed");
			} else {
				System.out.println("The test is failed");

			}

		}
	}
}
