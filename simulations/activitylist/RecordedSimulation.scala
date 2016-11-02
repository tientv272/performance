package activitylist

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://cxloadtest")
		.inferHtmlResources()

	val headers_0 = Map("X-Requested-With" -> "XMLHttpRequest")

	val headers_1 = Map(
		"X-Requested-With" -> "XMLHttpRequest",
		"__RequestVerificationToken" -> "9ozm_5ksVAZTofwwDtnrMc5M1pLSDR4GhQj_UYuPIX9KpUgoKX_BRMq5BfnDe4YVi-NrUCuVZEn8YUCnxK09tdPKEqMGTqGUh7buJ9jxLy41")

    val uri1 = "http://cxloadtest/vokal2/PortalPage"

	val scn = scenario("RecordedSimulation")
		.exec(http("request_0")
			.post("/vokal2/PortalPage/RenderPortalPageIncludeParameters")
			.headers(headers_0)
			.body(RawFileBody("RecordedSimulation_0000_request.txt"))
			.resources(http("request_1")
			.post(uri1 + "/PortalPartRender")
			.headers(headers_1)
			.body(RawFileBody("RecordedSimulation_0001_request.txt")),
            http("request_2")
			.post(uri1 + "/RenderPortalPageIncludeParameters")
			.headers(headers_0)
			.body(RawFileBody("RecordedSimulation_0002_request.txt")),
            http("request_3")
			.post(uri1 + "/RenderPortalPageIncludeParameters")
			.headers(headers_0)
			.body(RawFileBody("RecordedSimulation_0003_request.txt"))))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}