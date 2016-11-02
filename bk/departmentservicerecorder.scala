package departmentservice

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class departmentservicerecorder extends Simulation {

	val httpProtocol = http
		.baseURL("http://cct-web01.westeurope.cloudapp.azure.com")
		.inferHtmlResources()
	
	val headers_35 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, sdch",
		"Accept-Language" -> "en-US,en;q=0.8",
		//"Postman-Token" -> "9925216d-8422-af79-8ae1-1a1385f8e15b",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36")

    
    val uri7 = "http://cct-web01.westeurope.cloudapp.azure.com"

	val scn = scenario("departmentservicerecorder")							
		.exec(http("request_35")
			.get(uri7 + "/schoolowners/18917/schools/89629")
			.headers(headers_35)
			.basicAuth("systemtestuser","systemtestpassword"))
		
		
	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}