package net.wiringbits.webapp.utils.ui.webTest.facades.reactadmin

import io.github.nafg.simplefacade.Implicits._
import io.github.nafg.simplefacade._
import japgolly.scalajs.react.vdom.VdomNode
import net.wiringbits.webapp.utils.ui.webTest.facades

import scala.scalajs.js

object Resource extends FacadeModule.Simple {
  override def raw: js.Object = facades.reactadmin.ReactAdmin.Resource
  override def mkProps = new Props
  class Props extends PropTypes {
    val name: PropTypes.Prop[String] = of[String]
    val create: PropTypes.Prop[VdomNode] = of[VdomNode]
    val edit: PropTypes.Prop[VdomNode] = of[VdomNode]
    val list: PropTypes.Prop[VdomNode] = of[VdomNode]
  }
}