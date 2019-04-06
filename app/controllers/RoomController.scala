package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import models.Room

@Singleton
class RoomController @Inject()(cc: ControllerComponents)
extends AbstractController(cc) with play.api.i18n.I18nSupport {
  val roomform = Form(
      mapping(
         "name" -> text,
         "date" -> date("yyyy-MM-dd"),
         "use" -> text,
         "member" -> boolean
      )(Room.apply)(Room.unapply)
  )
  val kaigiform = roomform.fill(Room("", new java.util.Date(), "会議", false))
  def showForm()= Action{ implicit request: Request[AnyContent]  =>
     Ok(views.html.roomform(kaigiform))
  }
  def process() = Action{
     implicit request: Request[AnyContent] =>
        val room = roomform.bindFromRequest.get
        Ok(views.html.roomresult(room))
  }
}