package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.Book
import play.api.libs.json._
import play.api.libs.functional.syntax._

@Singleton
class BookJsonController @Inject()(cc: ControllerComponents)
extends AbstractController(cc) {

    val bookTest = Book("家の犬", "ドイル", 1902)

    val jsonTest="""{
        "title": "走れエロス", "author": "太宰治", "year": 1939
    }"""

    def writeTest(book:Book):String={
        implicit val bookWrites= new Writes[Book]{
            def writes(book:Book) = Json.obj(
                "title"->book.title,
                "author"->book.author,
                "year"->book.year
        )}
        val jsonResult = Json.toJson(book)
        return Json.prettyPrint(jsonResult)
    }

    def readTest(jsonStr:String):String={  
        val jsonObj= Json.parse(jsonStr)
        implicit val bookReads: Reads[Book]=(
            (JsPath \ "title").read[String] and
            (JsPath \ "author").read[String] and
            (JsPath \ "year").read[Int] 
        )(Book.apply _)
        jsonObj.validate[Book] match{
            case s: JsSuccess[book]=>{
                val result:Book = s.get
                return result.year+"年発表の"+result.author+"作「"+result.title+"」ですね。たぶんあります"
            }
            case e: JsError =>{
                return "解析に失敗しました"
            }
        }
    }

    def bToJ()= Action{ implicit request: Request[AnyContent] =>
        Ok(views.html.jsontest("bookオブジェクトをjsonに", writeTest(bookTest)))
    }
    def jToB()= Action{ implicit request: Request[AnyContent] =>
        Ok(views.html.jsontest("jsonをbookオブジェクトに", readTest(jsonTest)))
    }
}