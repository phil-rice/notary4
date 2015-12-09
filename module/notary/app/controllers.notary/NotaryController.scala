package controllers.notary

import play.api._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.http.LazyHttpErrorHandler
import org.validoc.notary.utilities.digest._
import org.validoc.notary.common.Css

trait NotaryController extends Controller {
  object NotaryForm {
    val inputForm = Form(single("notary.text" -> nonEmptyText))
  }

  implicit val notaryCss = Css(routes.NotaryAssets.versioned("stylesheets/notary.css"))

  implicit def messages(implicit request: Request[_]) = {
    import play.api.Play.current
    import play.api.i18n.Messages.Implicits._
    implicitly[Messages]
  }
  def index = Action { implicit request => Ok(views.html.page.notary.index(None, NotaryForm.inputForm)) }
  def postText = Action { implicit request =>
    NotaryForm.inputForm.bindFromRequest().fold(
      withErrors => Ok(views.html.page.notary.index(None, withErrors)),
      { value =>
        println(s"Got value $value")
        Ok(views.html.page.notary.index(Some(Digester(value)), NotaryForm.inputForm))
      })
  }
}

object NotaryController extends NotaryController {

}

object NotaryAssets extends controllers.AssetsBuilder(LazyHttpErrorHandler)