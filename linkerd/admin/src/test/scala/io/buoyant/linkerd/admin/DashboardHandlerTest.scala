package io.buoyant.linkerd.admin

import com.twitter.finagle.http.{Request, Status}
import io.buoyant.linkerd.{Build, Linker, TestProtocol}
import io.buoyant.test.Awaits
import org.scalatest.FunSuite

class DashboardHandlerTest extends FunSuite with Awaits {
  val handler = new DashboardHandler()

  test("serves ok on /") {
    val rsp = await(handler(Request("/")))
    assert(rsp.status == Status.Ok)
  }

  test("serves 404 other routes") {
    val rsp = await(handler(Request("/foo")))
    assert(rsp.status == Status.NotFound)
  }

  test("serves linkerd admin and version") {
    val rsp = await(handler(Request("/")))
    assert(rsp.contentString.contains("linkerd admin"))
    assert(rsp.contentString.contains(Build.load().version))
  }
}
