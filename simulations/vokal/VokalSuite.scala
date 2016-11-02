package vokalrelease
import VokalTestLib._
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

object VokalSuite {                                  
            val subjectsFeed = csv("search.csv").random
            val scnLogin = scenario("Login page - Login successful")
                .during(10 minutes){
                    exec(session => 
                     session.set("base-url","http://cxloadtest/vokalmain")
                    )
                    .exec(Login("1")).pause (1 second,3 seconds)
                    .exec(LoginSuccessful("1")).pause (1 second,3 seconds)
                    .exec(ActivityList("1")).pause (1 second,3 seconds)
                    .exec(Logout("1"))
                }
 }