package VokalNoApiOrg
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


object vokalnoapiorglib  {	
    val headersdepartment = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, sdch",
		"Accept-Language" -> "en-US,en;q=0.8",
        "cxtoken" -> "9:52",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36")

   //     val feeder = csv("account.csv").random
//GET method	              
    // /schoolowners
    //def GetAllSchoolOwners(baseUrl: String)
    //                = http("Get all school owners")
    //                .get(baseUrl + "/schoolowners/")
    //                .feed(feeder)
    //                .basicAuth("${username}","${password}")
    //                .headers(headersdepartment)
                    

    

    // /schoolowners/{schoolownerid}
    def GetSchoolOwnersById(baseUrl: String, userName: String, passWord: String, schoolOwner: String)
                    = http("Get all schools owners by id")
                    .get(baseUrl + "/schoolowners/" + schoolOwner)                    
                    .basicAuth(userName,passWord)
                    .headers(headersdepartment)
                    
    // /schoolowners/{schoolownerid}/schools
    def GetAllSchools(baseUrl: String, userName: String, passWord: String, schoolOwner: String)
                    = http("Get all schools")
                    .get(baseUrl + "/schoolowners/" + schoolOwner + "/schools/")
                    .basicAuth(userName,passWord)
                    .headers(headersdepartment)
                    
    // /schoolowners/{schoolownerid}/schools/{schoolid}                            
    def GetSchoolBySchoolId(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String)
	              = http("Get school info by id")
                      .get(baseUrl + "/schoolowners/" + schoolOwner + "/schools/" + school)					
                      .basicAuth(userName,passWord)
                      .headers(headersdepartment)

    
    // /schoolowners/{schoolownerid}/schools/{schoolid}/classes   
    def GetAllClasses(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String)
                    = http("Get all classes inside school")
                    .get(baseUrl + "/schoolowners/" + schoolOwner + "/schools/" + school + "/classes")
                    .basicAuth(userName,passWord)
                    .headers(headersdepartment)
                    
    // /schoolowners/{schoolownerid}/schools/{schoolid}/classes/{classid}  
    def GetClassByClassId(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String, classId: String)
                    = http("Get class by class id")
                    .get(baseUrl + "/schoolowners/" + schoolOwner + "/schools/"+ school + "/classes/" + classId)
                    .basicAuth("systemtestuser","systemtestpassword")
                    .headers(headersdepartment)     
                    
//POST method
    // /schoolowners/
    def PostSchoolOwners(baseUrl: String, userName: String, passWord: String)
                    = http("Post school owner")
                    .post(baseUrl + "/schoolowners/")
                    .basicAuth(userName,passWord)
                    .body(ELFileBody("request-bodies/post-school-owners.json")).asJSON
                    .headers(headersdepartment)
                    .check(status.in(200 to 210))                    
                    .check(jsonPath("$[0].Body.Id").saveAs("schoolOwnerId"))

    // /schoolowners/{schoolownerid}/schools 
    def PostSchools(baseUrl: String, userName: String, passWord: String, schoolOwner: String)
                    = http("Post schools")
                    .post(baseUrl + "/schoolowners/" + schoolOwner + "/schools/")
                    .basicAuth(userName,passWord)
                    .body(ELFileBody("request-bodies/post-schools.json")).asJSON                    
                    .headers(headersdepartment)
                    .check(status.in(200 to 210))                    
                    .check(jsonPath("$[0].Body.Id").saveAs("schoolId"))
    
    // /schoolowners/{schoolownerid}/schools/{schoolid}/classes   
    def PostClasses(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String)
                    = http("Post classes")
                    .post(baseUrl + "/schoolowners/" + schoolOwner + "/schools/" + school + "/classes")
                    .basicAuth(userName,passWord)
                    .body(ELFileBody("request-bodies/post-classes.json")).asJSON
                    .headers(headersdepartment)
                    .check(status.in(200 to 210))                    
                    .check(jsonPath("$[0].Body.Id").saveAs("classId"))

    // /schoolowners/{schoolownerid}/schools/{schoolid}/learners
    //schoolowner: 39852
    //school: 90141 - Persie-Testing
    def PostLearnersBySchoolId(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String)
                    = http("Post learners by school id")
                    .post(baseUrl + "/schoolowners/" + schoolOwner + "/schools/" + school + "/learners")
                    //.basicAuth("${username}","${password}")
                    .basicAuth(userName,passWord)
                    .body(ELFileBody("request-bodies/post-learner.json")).asJSON
                    .headers(headersdepartment)
                    .check(status.in(200 to 210))                    
                    .check(jsonPath("$[0].Body.Id").saveAs("learnerSId"))
                    

    //Insert Employee to school owner
    // /schoolowners/{schoolownerid}/employees
    def PostEmployeesBySchoolOwnerId(baseUrl: String, userName: String, passWord: String, schoolOwner: String)
                    = http("Add employee to School Owner")
                    .post(baseUrl + "/schoolowners/" + schoolOwner + "/employees")                    
                    .basicAuth(userName,passWord)
                    .body(ELFileBody("request-bodies/post-employee.json")).asJSON
                    .headers(headersdepartment)
                    .check(status.in(200 to 210))                    
                    .check(jsonPath("$[0].Body.Id").saveAs("employeeSOId"))

    //Insert Employee to school
    // /schoolowners/{schoolownerid}/schools/{schoolid}/employees
    def PostEmployeesBySchoolId(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String)
                    = http("Add employee to School Owner")
                    .post(baseUrl + "/schoolowners/" + schoolOwner + "/schools/" + school + "/employees")                    
                    .basicAuth(userName,passWord)
                    .body(ELFileBody("request-bodies/post-employee-school.json")).asJSON
                    .headers(headersdepartment)
                    .check(status.in(200 to 210))                    
                    .check(jsonPath("$[0].Body.Id").saveAs("employeeSId"))

     //Insert Teaching group to school
    // /schoolowners/{schoolownerid}/schools/{schoolid}/teachinggroups
    def PostTeachingGroupBySchoolId(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String)
                    = http("Post teaching group to School")
                    .post(baseUrl + "/schoolowners/" + schoolOwner + "/schools/" + school + "/teachinggroups")                    
                    .basicAuth(userName,passWord)
                    .body(ELFileBody("request-bodies/post-teaching-group.json")).asJSON
                    .headers(headersdepartment)
                    .check(status.in(200 to 210))                    
                    .check(jsonPath("$.Identity.Id").saveAs("teachinggroupId"))
    
    //POST Level to member
    // /schoolowners/{schoolownerid}/schools/{schoolid}/classes/{classid}/members
    def PostLevel(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String, levelid: String, memberid: String, archetype: String)
                    = http("Post level to member")
                    .post(baseUrl + "/schoolowners/" + schoolOwner + "/schools/" + school + "/levels/" + levelid + "/members")     
                    .basicAuth(userName,passWord)
                    .body(ELFileBody("request-bodies/post-level.json")).asJSON
                    .headers(headersdepartment)
                    .check(status.in(200 to 210))                                        

    //POST member to class
    // /schoolowners/{schoolownerid}/schools/{schoolid}/classes/{classid}/members
    def PostMemberToClass(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String, classId: String, memberid: String, archetype: String)
                    = http("Post member to class")
                    .post(baseUrl + "/schoolowners/" + schoolOwner + "/schools/" + school + "/classes/" + classId + "/members")     
                    .basicAuth(userName,passWord)
                    .body(ELFileBody("request-bodies/post-member-to-class.json")).asJSON
                    .headers(headersdepartment)
                    .check(status.in(200 to 210))         

    //POST member to teaching group
    // /schoolowners/{schoolownerid}/schools/{schoolid}/teachinggroups/{teachinggroupid}/members
    def PostMemberToTeachingGroup(baseUrl: String, userName: String, passWord: String, schoolOwner: String, school: String, teachinggroup: String, memberid: String, archetype: String)
                    = http("Post member to teaching group")
                    .post(baseUrl + "/schoolowners/" + schoolOwner + "/schools/" + school + "/teachinggroups/" + teachinggroup + "/members")     
                    .basicAuth(userName,passWord)
                    .body(ELFileBody("request-bodies/post-member-to-teaching-group.json")).asJSON
                    .headers(headersdepartment)
                    .check(status.in(200 to 210))

//PUT method 
     // /schoolowners/{schoolownerid}/schools/{schoolid}
     def PutSchools(baseUrl: String)
                    = http("Put schools")
                    .post(baseUrl + "/schoolowners/18917/schools/89629")
                    .basicAuth("systemtestuser","systemtestpassword")
                    .body(ELFileBody("request-bodies/put-schools.json")).asJSON
                    .headers(headersdepartment)
                                   
     // /schoolowners/{schoolowersid }/schools/{schoolid}/classes/{classid}
     def Putclasses(baseUrl: String)
                    = http("Put classes")
                    .post(baseUrl + "/schoolowners/18917/schools/89635/classes/89636")
                    .basicAuth("systemtestuser","systemtestpassword")
                    .body(ELFileBody("request-bodies/put-classes.json")).asJSON
                    .headers(headersdepartment)       


    // /assessments/{activityid}/assessmentsurveys/{surveyid}
     def PostAssessmentGrade(baseUrl: String)
                    = http("Post grade assessment")
                    .post(baseUrl + "/assessments/600/assessmentsurveys/2929")
                    .basicAuth("developmentuser","developmentpassword")
                    .body(ELFileBody("request-bodies/post-assessment-grade.json")).asJSON
                    .headers(headersdepartment)          

    // /assessments/{activityid}/assessmentsurveys/{surveyid}
     def PostAssessmentAbsence(baseUrl: String)
                    = http("Post absence assessment")
                    .post(baseUrl + "/assessments/600/assessmentsurveys/2929")
                    .basicAuth("developmentuser","developmentpassword")
                    .body(ELFileBody("request-bodies/post-assessment-absence.json")).asJSON
                    .headers(headersdepartment)                 
                    
}