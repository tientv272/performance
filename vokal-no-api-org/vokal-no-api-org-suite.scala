package VokalNoApiOrg
import vokalnoapiorglib._
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import java.util.UUID
import io.gatling.jsonpath._
import java.util.Calendar
import java.io

object UuidFeeder {
    val feederuuid = new Feeder[String] {
        override def hasNext = true
 
        override def next: Map[String, String] = {
            Map("uuid" -> UUID.randomUUID.toString());
        }
    }
}

//feed(UuidFeeder.feederuuid)

object vokalnoapiorgsuite {              
                val feeder = csv("account.csv").circular 
                val feederurl = csv("url.csv").circular
                val feederlearner = csv("learner.csv").circular
                
                val now = Calendar.getInstance()
                val currentHour = now.get(Calendar.DAY_OF_MONTH) +""+ now.get(Calendar.OCTOBER)+""+ now.get(Calendar.YEAR) + "-"+ now.get(Calendar.MILLISECOND)
                val glevelid = "3"
                
                val result = {
                            val fos = new java.io.FileOutputStream("result.csv",true) 
                            new java.io.PrintWriter( fos, true ) 
                                //pw.println(session.get("schoolId").asOption[String])          
                        }
                                
//POST scenarios

//POST full steps
                val scnPostSchoolOwners = scenario ("Full steps scenario - Active case")
                .repeat(1) {
                    feed(feeder)
                    .feed(feederurl)
                    .feed(UuidFeeder.feederuuid)
                    .exec(session => session.set("base-url",""))

                    //Post schoolowner
                    .exec(PostSchoolOwners("${base-url}","${username}","${password}"))                                        
                    .exec(session => session.set("schoolowner-id",session.get("schoolOwnerId").as[String].toInt))
                    //Get schoolowner Info
                    .exec(GetSchoolOwnersById("${base-url}","${username}","${password}","${schoolowner-id}"))
                    //Get all school inside school Owner
                    .exec(GetAllSchools("${base-url}","${username}","${password}","${schoolowner-id}"))                    

                    //Post school
                    .exec(PostSchools("${base-url}","${username}","${password}","${schoolowner-id}"))
                    .exec(session => session.set("school-id",session.get("schoolId").as[String].toInt)) 
                    //Get school Info
                    .exec(GetSchoolBySchoolId("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}"))                    
                                       

                    //Post level to school
                    .exec(session => session.set("member-id",session.get("schoolId").as[String].toInt)
                                            .set("archetype","school"))
                    .exec(PostLevel("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}",glevelid,"${member-id}","${archetype}"))

                    //Get school Info
                    .exec(GetSchoolBySchoolId("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}"))
                    
                    //Post class
                    .exec(PostClasses("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}"))
                    .exec(session => session.set("class-id",session.get("classId").as[String].toInt))
                    
                    //Get class Info
                    .exec(GetClassByClassId("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}","${class-id}"))
                    
                    //Get all classes inside school
                    .exec(GetAllClasses("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}")) 
                    
                    //Post level to class
                    .exec(session => session.set("member-id",session.get("classId").as[String].toInt)
                                            .set("archetype","class"))
                    .exec(PostLevel("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}",glevelid,"${member-id}","${archetype}"))

                    //Post employee
                    .exec(PostEmployeesBySchoolOwnerId("${base-url}","${username}","${password}","${schoolowner-id}"))
                    .exec(session => session.set("soemployee-id",session.get("employeeSOId").as[String].toInt))
                    .exec(PostEmployeesBySchoolId("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}"))
                    .exec(session => session.set("semployee-id",session.get("employeeSId").as[String].toInt))
                    
                    //Post learner
                    .exec(PostLearnersBySchoolId("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}"))
                    .exec(session => session.set("learner-id",session.get("learnerSId").as[String].toInt))

                    //Post teaching group to school
                    .exec(PostTeachingGroupBySchoolId("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}"))
                    .exec(session => session.set("teachinggroup-id",session.get("teachinggroupId").as[String].toInt))
                        
                    //Post level to teaching group
                    .exec(session => session.set("member-id",session.get("learnerSId").as[String].toInt)
                                            .set("archetype","learner"))
                    .exec(PostLevel("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}",glevelid,"${member-id}","${archetype}"))

                    //Post member to class
                    .exec(session => session.set("member-id",session.get("learnerSId").as[String].toInt)
                                            .set("archetype","learner")
                                            set("class-id",session.get("classId").as[String].toInt))
                    .exec(PostMemberToClass("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}","${class-id}","${member-id}","${archetype}"))

                    //Post member to teaching group
                    .exec(session => session.set("member-id",session.get("learnerSId").as[String].toInt)
                                            .set("archetype","learner")
                                            set("teachinggroup-id",session.get("teachinggroupId").as[String].toInt))
                    .exec(PostMemberToTeachingGroup("${base-url}","${username}","${password}","${schoolowner-id}","${school-id}","${teachinggroup-id}","${member-id}","${archetype}"))

                    //def PostMemberToTeachingGroup(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String, teachinggroup: String, memberid: String, archetype: String)


                    //def PostMemberToClass(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String, classId: String, memberid: String, archetype: String)
                    

                    .exec(session => {
                        val maybeId = session.get("schoolOwnerId").asOption[String]
                        println(maybeId.getOrElse("COULD NOT FIND ID"))   
                        
                        result.println("School Owner ID: " + session.get("schoolOwnerId").asOption[String])

                        //pw.println(currentHour)
                        println(currentHour)
                        println("School Owner ID: " + session.get("schoolOwnerId").asOption[String])
                        //guid_data.println("School Owner ID: " + session.get("schoolOwnerId").asOption[String])
                        println("School ID: " + session.get("schoolId").asOption[String])
                        println("Class ID: " + session.get("classId").asOption[String])
                        println("Employee of School Owner: " + session.get("employeeSOId").asOption[String])
                        println("Employee of School: " + session.get("employeeSId").asOption[String])
                        println("Learner ID: " + session.get("learnerSId").asOption[String])                                
                        println("Teaching group ID: " + session.get("teachinggroupId").asOption[String])
                        session
                        }
                        //session.set("deparment-id",maybeId)
                        )

                    
                }
//End POST schoolowners

//POST schools    
                val scnPostSchools = scenario ("Post Schools")
                .repeat(1) {                 
                    exec(session => 
                      session.set("base-url","http://cct-web01.westeurope.cloudapp.azure.com")                             
                          )
                    .exec(PostSchools("${base-url}","${username}","${password}","${password}"))
                }
//POST class                
              val scnPostClasses = scenario ("Post Classes")
                .repeat(1) {                 
                    exec(session => 
                      session.set("base-url","")                             
                          )
                    .exec(PostClasses("${base-url}","${base-url}","${base-url}","${base-url}","${base-url}"))
                }
//POST learner
//use http://jsonpath.com/? to check the jsonpath format
                val scnPostLearnersBySchoolId = scenario ("Post Learner By School Id")
                //.during(10 minutes) {
                .repeat(1) {                 
                    feed(feeder)
                    .feed(feederurl)
                    .feed(UuidFeeder.feederuuid)
                    .exec(session => 
                      session.set("base-url","")                       
                          )
                    .exec(PostLearnersBySchoolId("${base-url}","${username}","${password}","${password}","${password}"))
                    .exec(session => {
                        val maybeId = session.get("learnerId").asOption[String]
                        println(maybeId.getOrElse("COULD NOT FIND ID"))

                        //val maybeId = session.get("pList")                                               
                        //val Id = JsonPath.query("$.Id", maybeId)                                                    
                        //println(Id)
                        //println("Value:" + session.get("Body"))  
                        session
                        })
                    //.exec(println("${pList}"))
                    //.exec(session => {
                    //                    println(session.get("Id"))                                           
                    //                    session                                     
                     //                }
                     //    )
                }

//POST assessment grade
                val scnPostAssessmentGrade = scenario ("Post Assessment Grade for 1 survey, 1 learner")
                //.during(3 minutes) {
                .repeat(1) {                 
                    feed(feederlearner)
                    //.feed(UuidFeeder.feederuuid)
                    .exec(session => 
                      session.set("base-url","http://development-vokal-no-api-assessment.conexus.no")                             
                          )
                    .exec(PostAssessmentGrade("${base-url}"))
                }


//POST assessment absence
                val scnPostAssessmentAbsence = scenario ("Post Assessment Absence for 1 survey, 1 learner")
                //.during(3 minutes) {
                .repeat(2) {                 
                    feed(feederlearner)
                    //.feed(UuidFeeder.feederuuid)
                    .exec(session => 
                      session.set("base-url","http://development-vokal-no-api-assessment.conexus.no")                             
                          )
                    .exec(PostAssessmentAbsence("${base-url}"))
                }
                                        
 }