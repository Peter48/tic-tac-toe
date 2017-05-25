package filters

import akka.stream.Materializer
import javax.inject._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}

/**
  * Filter that adds 'CorrelationId' header to all requests.
  */
@Singleton
class CorrelationIdRequestFilter @Inject()(implicit override val mat: Materializer,
                                           exec: ExecutionContext) extends Filter {

  override def apply(nextFilter: RequestHeader => Future[Result])
                    (requestHeader: RequestHeader): Future[Result] = {

    nextFilter(requestHeader).map { result =>
      result.withHeaders("X-CORRELATION-ID" -> "TODO",
                         "X-CORRELATION-SOURCE-ID" -> "TODO",
                         "X-CORRELATION-JSON" -> "TODO"
      )
    }
  }
}
