package meducation
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


object MeducationTestLib  {

	val meducation_header = Map(
			"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
			"Content-Type" -> "application/json; charset=UTF-8",
			"Origin" -> "https://test-vokal-bd-api-activity.azurewebsites.net",
			"Cache-Control" -> "max-age=0, s-maxage=0, no-cache"
	)

					
	def PostLessonSmall(baseUrl: String) 
	              = http("Post Lesson")
					.post("${activty-base-url}/${meducation-version}/lessons")
					.basicAuth("${activity-api-username}","${activity-api-password}")
					.body(ELFileBody("request-bodies/post-lesson-small.json")).asJSON
					.check(status.is(202))
					
	def PostLessonBig(baseUrl: String) 
	              = http("Post Lesson Big")
					.post("${activty-base-url}/${meducation-version}/lessons")
					.basicAuth("${activity-api-username}","${activity-api-password}")
					.body(ELFileBody("request-bodies/post-lesson-big.json")).asJSON
					.check(status.is(202))
					
	def PostChapterTest(baseUrl: String) 
	              = http("Post Chapter")
					.post("${activty-base-url}/${meducation-version}/assessments")
					.basicAuth("${activity-api-username}","${activity-api-password}")
					.body(ELFileBody("request-bodies/post-chaptertest.json")).asJSON
					.check(status.is(202))


    def CreateUser(baseUrl: String) 
	              = http("Post User")
					.post("${org-base-url}/${meducation-version}/students")
					.basicAuth("${org-api-username}","${org-api-password}")
					.body(ELFileBody("request-bodies/user-create.json")).asJSON
					.check(status.is(200))
					
					
	def GetRecommendation(baseUrl: String)
	              = http("Get recommendation")
				  	.get(baseUrl + "/v1/recommendations/subjects/445/userid/${lesson-userid}")
					.basicAuth("${adaptive-api-username}","${adaptive-api-password}")
	        	 	
					.check(status.is(200)	
					)	
	
}					
					

					
