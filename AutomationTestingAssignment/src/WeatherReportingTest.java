import UITest.FrontEnd;

import java.io.IOException;

public class WeatherReportingTest {

	public static void main(String[] args) throws InterruptedException, IOException {
		/* Author: Ritesh Verma
		 * This application validates the temperature and humidity for NDTV comparing from Front end and Back end.
		 */

		FrontEnd obj = new FrontEnd();
		obj.UITestValidation();

	}
}
