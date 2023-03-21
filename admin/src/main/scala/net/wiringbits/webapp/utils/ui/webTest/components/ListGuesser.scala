package net.wiringbits.webapp.utils.ui.webTest.components

import io.github.nafg.simplefacade.Factory
import japgolly.scalajs.react.vdom.all.div
import japgolly.scalajs.react.vdom.html_<^.{VdomNode, _}
import net.wiringbits.webapp.utils.api.models.AdminGetTables
import net.wiringbits.webapp.utils.ui.webTest.facades.reactadmin
import net.wiringbits.webapp.utils.ui.webTest.facades.reactadmin._
import net.wiringbits.webapp.utils.ui.webTest.facades.reactadmin.fields._
import net.wiringbits.webapp.utils.ui.webTest.facades.reactadmin.inputs._
import net.wiringbits.webapp.utils.ui.webTest.models.ColumnType
import net.wiringbits.webapp.utils.ui.webTest.utils.ResponseGuesser

object ListGuesser {
  def apply(response: AdminGetTables.Response.DatabaseTable): Factory[reactadmin.ComponentList.Props] = {
    val fields = ResponseGuesser.getTypesFromResponse(response)

    val widgetFields: List[VdomNode] = fields.map { field =>
      field.`type` match {
        case ColumnType.Date => DateField(_.source := field.name, _.showTime := true)
        case ColumnType.Text => TextField(_.source := field.name)
        case ColumnType.Email => EmailField(_.source := field.name)
        case ColumnType.Reference(reference, source) =>
          ReferenceField(_.reference := reference, _.source := field.name)(
            TextField(_.source := source)
          )
      }
    }

    // TODO: implement the same example from: https://marmelab.com/react-admin/doc/4.1/List.html

    val filterList: Seq[VdomNode] = Seq(
      TextInput(_.source := "name", _.label := "Name", _.alwaysOn := true),
      TextInput(_.source := "last_name", _.label := "Last Name", _.alwaysOn := true)
    )

    val listToolbar: VdomNode = TopToolbar()(
      // TODO: FilterButton is not working
      FilterButton(
        _.filters := filterList
      ),
      ExportButton()
    )

    reactadmin.ComponentList(_.actions := listToolbar)(
      reactadmin.Datagrid(_.rowClick := "edit", _.bulkActionButtons := response.canBeDeleted)(widgetFields: _*)
    )
  }
}
