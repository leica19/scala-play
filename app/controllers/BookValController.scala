package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import models.Book

@Singleton
class BookValController @Inject()(cc: ControllerComponents)
extends AbstractController(cc) with play.api.i18n.I18nSupport {
  val bookform = Form(
      mapping(
          "title" -> nonEmptyText,
          "author" -> text,
          "year" -> number(max=2018)
      )(Book.apply)(Book.unapply)
  )

  def showForm()= Action{ implicit request: Request[AnyContent]  =>
     Ok(views.html.bookval(bookform))
  }

  def process() =Action{
     implicit request: Request[AnyContent] =>
        bookform.bindFromRequest.fold(
          wrongData=>{
              BadRequest(views.html.bookval(wrongData))
          },
          rightObj=>{
              Ok(views.html.bookresult(rightObj))
          }
        )
  }
}