package VokalNoApiOrg 
import vokalnoapiorgsuite._
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class vokalnoapiorgsimulations extends Simulation {
  
	//val httpConf = http
	//		.baseURL("http://cct-web01.westeurope.cloudapp.azure.com") 
	//		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
	//		.acceptEncodingHeader("gzip,deflate,sdch")
	//		.acceptLanguageHeader("en-GB,en;q=0.8,en-US;q=0.6,nb;q=0.4,da;q=0.2")
	//		.connection("keep-alive")
	//		.userAgentHeader("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0")            
	//		.inferHtmlResources()
           
	val httpConf = http
		.baseURL("http://systemtest-vokal-no-api-organization.conexus.no")		
		.inferHtmlResources()
        			
	//setUp(VokalSuite.scnLogin.inject(rampUsers(100) over (15 minutes))).protocols(httpConf)
	
	//setUp(	scnPostAssessmentAbsence
    //    	.inject(atOnceUsers(5))
	//		,scnPostAssessmentGrade
    //    	.inject(atOnceUsers(3))
    //     ).protocols(httpConf)
	
	setUp(	//scnPostLearnersBySchoolId
			scnPostSchoolOwners
        	.inject(atOnceUsers(1))			
         ).protocols(httpConf)

	scnPostLearnersBySchoolId
    //setUp(vokalnoapiorgsuite.scnLogin
	//								.inject(
									//nothingFor(4 seconds)
									//atOnceUsers(20)
	//								rampUsers(100) over(2 minutes)
									//constantUsersPerSec(100) over (2 minutes)
									//rampUsersPerSec(2) to(2) during(2 minutes)
									//splitUsers(100) into(rampUsers(50) over(5 seconds)) separatedBy(3 minutes)
									//splitUsers(100) into(5) separatedBy(5 seconds)
									//,heavisideUsers(100) over(2 minutes)
	//								)
									
									//.throttle(
  										//reachRps(100) in (3 minutes),
  										//holdFor(3 minutes),
  										//jumpToRps(50),
 										//holdFor(3 minutes))
	//		).protocols(httpConf)
			//.maxDuration(3 minutes)
			//setUp(scn.inject(rampUsers(1000) over(20 minutes))).maxDuration(10 minutes)
}	