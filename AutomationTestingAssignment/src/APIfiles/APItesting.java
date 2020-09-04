package APIfiles;

import static io.restassured.RestAssured.given;

import java.util.Scanner;

import io.restassured.RestAssured;

public class APItesting {

	public static String WeatherReport() {

		RestAssured.baseURI = "https://api.openweathermap.org";
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter the backend city for which you want weather report");
			String city = sc.nextLine();
			return

			given().queryParam("q", city).queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea")
					.header("Content-Type", "application/x-www-form-urlencoded").when().post("/data/2.5/weather").then()
					.assertThat().statusCode(200).header("Connection", "keep-alive").extract().response().asString();

		}
	}
}
