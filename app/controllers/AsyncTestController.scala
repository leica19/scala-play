package controllers

import javax.inject._
import play.api.mvc._

import akka.actor.ActorSystem
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeUnit
import scala.concurrent.{ExecutionContext, Future, Promise}

@Singleton
class AsyncTestController @Inject()(cc: 
    ControllerComponents, actorSystem: ActorSystem)
    (implicit exec: ExecutionContext) extends AbstractController(cc) {
    def howManySeconds(futuretime:Long, now:Long):String={
        var sec = (futuretime-now)
        return "約"+sec+"ミリ秒間の御無沙汰でした"
    }
    def getYourFuture(sec:Int): Future[Long] = {
        val promise: Promise[Long] = Promise[Long]()
        val duration = new FiniteDuration(sec, TimeUnit.SECONDS)
        actorSystem.scheduler.scheduleOnce(duration) {
            promise.success(System.currentTimeMillis())
        }(actorSystem.dispatcher)
        promise.future
    }
    def goAsync(sec:Int) = Action.async {
        implicit request: Request[AnyContent] =>
        val now = System.currentTimeMillis()
        getYourFuture(sec).map { msg => Ok(views.html.goasync(howManySeconds(msg, now))) }
    }
}