package model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.sql.DriverManager.println

class MelonAlbumCommentParser//System Property SetUp

//Driver SetUp

//Driver SetUp
() {
	private var driver: WebDriver? = null

	//Properties
	val WEB_DRIVER_ID = "webdriver.chrome.driver"
	val WEB_DRIVER_PATH = "src/driver/chromedriver.exe"
	private var base_url: String? = null

	init {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
		driver = ChromeDriver()
		base_url = "https://www.melon.com/album/detail.htm?albumId=10479150"
	}

	fun crawl() {
		try {
			//get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
			driver!!.get(base_url)
			val html = driver!!.pageSource
			print(html)
			//System.out.println(driver.getPageSource());
		} catch (e: Exception) {
			e.printStackTrace()
		} finally {
			driver!!.close()
		}
	}
}