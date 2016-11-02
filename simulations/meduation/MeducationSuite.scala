package meducation
import MeducationTestLib._
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import java.util.UUID


object UuidFeeder {
    val feeder = new Feeder[String] {
        override def hasNext = true
 
        override def next: Map[String, String] = {
            Map("uuid" -> UUID.randomUUID.toString());
        }
    }
}


object MeducationSuite {

          val subjectsFeed = csv("lesson-subjects.csv").random
          val usersFeed = csv("lesson-users-9th.csv").circular
          val topicsFeed = csv("lesson-topics-english-9th.csv").random
		
          val random = new util.Random 
           val userFeeder = Iterator.continually(Map("user-username" -> random.nextString(20)))
            
            var createUserScenario = scenario("Create User") 
            .repeat(1) {
                feed(UuidFeeder.feeder)
                .feed(userFeeder)
                .exec(session => 
                    session.set("org-api-username", "staging-vokal-bd-api-excelsoft")
                            .set("org-api-password", "hZP5Er9o3mXFRqAF")
                            .set("org-base-url","https://staging-vokal-bd-api-org.azurewebsites.net")
                            .set("meducation-version","v1")
                            .set("user-password","apassword")
                            .set("user-level", "9")
                    )
                .exec(CreateUser("${user-username}"))
            }
            
            
        val getRecommendationScenario = scenario ("Get Recommendation")
             .repeat(1) {
                 feed(usersFeed)
                    .exec(session => 
                      session.set("adaptive-api-username", "staging-vokal-bd-api-excelsoft")
                            .set("adaptive-api-password", "hZP5Er9o3mXFRqAF")
                            .set("adaptive-base-url","https://staging-vokal-bd-api-adaptive.azurewebsites.net")
                            .set("meducation-version","v1")
                    )
                 .exec(GetRecommendation("${adaptive-base-url}"))
        }
              
		val postSessionDataSmallScenario = scenario("Post Lesson") 
            .repeat(1) {
                feed(usersFeed)
                .exec(session => 
                    session.set("activity-api-username", "staging-vokal-bd-api-excelsoft")
                            .set("activity-api-password", "hZP5Er9o3mXFRqAF")
                            .set("activty-base-url","https://staging-vokal-bd-api-activity.azurewebsites.net")
                            .set("meducation-version","v1")
                    )
                
                    .group("LessonData") {
                        repeat(10) {
                            feed(topicsFeed)
                            .exec(PostLessonSmall("id"))
                    
                        }
                    }
            }
            
            
            val postChapterTestScenario = scenario("Post Assessment") 
            .repeat(1) {
                feed(usersFeed)
                .exec(session => 
                    session.set("activity-api-username",  "staging-vokal-bd-api-excelsoft")
                            .set("activity-api-password", "hZP5Er9o3mXFRqAF")
                            .set("activty-base-url","https://staging-vokal-bd-api-activity.azurewebsites.net")
                            .set("meducation-version","v1")
                    )
                
                    .group("ChapterTest") {
                        repeat(10) {
                            feed(topicsFeed)
                            .exec(PostChapterTest("${activty-base-url}"))
                
                        }
                    }
            }
         
         
 }
