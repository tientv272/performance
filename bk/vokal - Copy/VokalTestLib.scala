package vokalrelease
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


object VokalTestLib  {

	//val meducation_header = Map(
	//		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
	//		"Content-Type" -> "application/json; charset=UTF-8",
	//		"Origin" -> "https://test-vokal-bd-api-activity.azurewebsites.net",
	//		"Cache-Control" -> "max-age=0, s-maxage=0, no-cache"
	//)
	
	def Login (baseUrl: String)
					= http("Login page")
						.get("/")
	
	def LoginSuccessful (baseUrl: String)
					= http("Login Successful")
						.post("/Account/Login")
						.formParam("LanguageId","0")
						.formParam("UserName","rektor1")
						.formParam("Password","EK")
	
	def Logout (baseUrl: String)	
					= http("Logout page")
						.get("/Account/Logoff")
	
	def ActivityList (baseUrl: String)
					= http("Activity list page")
						.get("/PortalPage/PortalPageRender?miid=1054")

}					
					

					
