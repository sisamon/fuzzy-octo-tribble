package net.wiringbits.webapp.utils.ui.webTest

import io.github.nafg.simplefacade.Factory
import japgolly.scalajs.react.vdom.VdomNode
import net.wiringbits.webapp.utils.api.models.AdminGetTables
import net.wiringbits.webapp.utils.ui.webTest.components._
import net.wiringbits.webapp.utils.ui.webTest.facades.reactadmin
import net.wiringbits.webapp.utils.ui.webTest.facades.reactadmin.{Admin, Resource, simpleRestProvider}
import net.wiringbits.webapp.utils.ui.webTest.models.DataExplorerSettings
import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits.global

import scala.concurrent.Future

object AdminView {
  private def AdminTables(
      api: API,
      response: AdminGetTables.Response,
      dataExplorerSettings: DataExplorerSettings
  ): Factory[Admin.Props] = {
    val tablesUrl = s"${api.url}/admin/tables"

    def buildResources: List[VdomNode] = {
      response.data.map { table =>
        Resource(
          _.name := table.name,
          _.list := ListGuesser(table),
          _.edit := EditGuesser(table, dataExplorerSettings)
        )
      }
    }

    val resources = buildResources
    reactadmin.Admin(_.dataProvider := simpleRestProvider(tablesUrl))(resources: _*)
  }

  def apply(
      api: API,
      dataExplorerSettings: DataExplorerSettings = DataExplorerSettings()
  ): Future[Factory[reactadmin.Admin.Props]] = {
    api.client.getTables.map { AdminTables(api, _, dataExplorerSettings) }
  }
}
