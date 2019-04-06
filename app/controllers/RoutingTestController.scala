
package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.util.Random
import models.ViewTestData
import models.JankenCase

@Singleton
class RoutingTestController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
    def simple(something: String) = Action { implicit request: Request[AnyContent] =>
      Ok(something+"さん。こんにちは")
    }
    def showData(index: Int) = Action { implicit request: Request[AnyContent] =>
      Ok(views.html.routingtest_showdata
            ("選ばれたトピック")
            (new ViewTestData().getTopic(index))
        )
    }
    def realJanken(index: Int): JankenCase = {
        val gcp = Array("グー", "チョキ", "パー")
        return JankenCase(
            gcp(index), gcp(new Random().nextInt(3))
        )
    }
    def doRealJanken(index: Int) = Action { implicit request: Request[AnyContent] =>
      Ok(views.html.viewtest_janken
            ("真のじゃんけん")
            (realJanken(index))
        )
    }
}