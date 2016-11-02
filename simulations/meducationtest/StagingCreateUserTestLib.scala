package meducation
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


object StagingCreateUserTestLib  {

	val meducation_header = Map(
			"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
			"Content-Type" -> "application/json; charset=UTF-8",
			"Origin" -> "https://staging-vokal-bd-api-org.azurewebsites.net",
			"Cache-Control" -> "max-age=0, s-maxage=0, no-cache"
	)
	
	//def CreateUser(baseUrl: String)		
	def StagingCreateUser()
	              = http("Post User")				    
					.post("${org-base-url}/${meducation-version}/students")					
					.basicAuth("${org-api-username}","${org-api-password}")
					.body(ELFileBody("request-bodies/staging-create-user.json")).asJSON
					.check(status.is(200))
					//.check(jsonPath("$.userId").saveAs("respondUserId"))
					//.exec {session => println(session) session}								
}