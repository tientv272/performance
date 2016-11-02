package meducation 
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


class StagingCreateUserSimulation extends Simulation {
  
	val httpConf = http
			.baseURL("") 
			.acceptHeader("text/html, */*; q=0.01")
			.acceptEncodingHeader("gzip,deflate,sdch")
			.acceptLanguageHeader("en-GB,en;q=0.8,en-US;q=0.6,nb;q=0.4,da;q=0.2")
			.connection("keep-alive")
			.userAgentHeader("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36")
	
	setUp(StagingCreateUserSuite.stagingCreateUserScenario.inject(rampUsers(1) over (1 minutes))).protocols(httpConf)
}