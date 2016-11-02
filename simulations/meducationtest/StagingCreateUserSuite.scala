package meducation
import StagingCreateUserTestLib._
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

object StagingCreateUserSuite {                      
          var stagingCreateUserScenario = scenario("Create User") 
            .repeat(1) {                
                exec(session => 
                    session.set("org-api-username", "staging-vokal-bd-api-excelsoft")
                            .set("org-api-password", "hZP5Er9o3mXFRqAF")
                            .set("org-base-url","https://staging-vokal-bd-api-org.azurewebsites.net")
                            .set("meducation-version","v1")
                            .set("user-password","apassword")
                            .set("user-level", "9")
                    )                
                .exec(StagingCreateUser())                     
            }
             
 
 }
