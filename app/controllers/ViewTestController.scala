
package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.util.Random
import models.ViewTestData
import models.JankenCase

@Singleton
class ViewTestController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
    def index() = Action { implicit request: Request[AnyContent] =>
      Ok(views.html.viewtest_index("dadada")(423))
    }
    def twoArgs() = Action { implicit request: Request[AnyContent] =>
      Ok(views.html.viewtest_twoargs("twoargs")(new Random().nextInt(10)))
    }
    def forLoop() = Action { implicit request: Request[AnyContent] =>
      Ok(views.html.viewtest_forloop("loop")(new ViewTestData().getTopics()))
    }
    def janken(): JankenCase = {
      val gcp = Array("グー", "チョキ", "パー")
      return JankenCase(
        gcp(new Random().nextInt(3)), gcp(new Random().nextInt(3))
      )
    }
    def doJanken() = Action { implicit request: Request[AnyContent] =>
      Ok(views.html.viewtest_janken("じゃんけん")(janken()))
    }
}