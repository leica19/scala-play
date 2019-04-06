package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import models.Book

@Singleton
class BookFormController @Inject()(cc: ControllerComponents)
extends AbstractController(cc) with play.api.i18n.I18nSupport {
  val bookform = Form(
      mapping(
         "title" -> text,
         "author" -> text,
         "year" -> number
      )(Book.apply)(Book.unapply)
  )
  def showForm()= Action{ implicit request: Request[AnyContent]  =>
     Ok(views.html.bookform(bookform))
  }
  def process() = Action{
     implicit request: Request[AnyContent] =>
        val book = bookform.bindFromRequest.get
        Ok(views.html.bookresult(book))
  }
}