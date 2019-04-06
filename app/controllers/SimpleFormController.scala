
package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport

@Singleton
class SimpleFormController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {
    val form = Form("name"->text)
    def showForm() = Action { implicit request: Request[AnyContent] =>
      Ok(views.html.simpleform(form))
    }
    def process() = Action {
        implicit request: Request[AnyContent] =>
        val sentData = form.bindFromRequest.get
        Ok(views.html.simpleform_result(sentData))
    }
}