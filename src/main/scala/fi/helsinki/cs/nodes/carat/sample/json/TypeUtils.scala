package fi.helsinki.cs.nodes.carat.sample.json

import scala.collection.immutable.ListMap

/**
 * Type mangling utility class able to produce pretty-print toStrings for complex case classes.
 * @author Eemil Lagerspetz, University of Helsinki
 */

object TypeUtils {
  import scala.reflect.runtime.universe._

  /**
   * Returns a map from formal parameter names to types, containing one
   * mapping for each constructor argument.  The resulting map (a ListMap)
   * preserves the order of the primary constructor's parameter list.
   */
  def caseClassParamsOf[T: TypeTag]: ListMap[String, Type] = {
    val tpe = typeOf[T]
    val constructorSymbol = tpe.decl(termNames.CONSTRUCTOR)
    val defaultConstructor =
      if (constructorSymbol.isMethod) constructorSymbol.asMethod
      else {
        val ctors = constructorSymbol.asTerm.alternatives
        ctors.map { _.asMethod }.find { _.isPrimaryConstructor }.get
      }

    ListMap[String, Type]() ++ defaultConstructor.paramLists.reduceLeft(_ ++ _).map {
      sym => sym.name.toString -> tpe.member(sym.name).asMethod.returnType
    }
  }

  def arrayToString(arr: Any) = {
    if (arr == null)
      "null"
    else
      arr.asInstanceOf[Array[Any]].map(_.toString).mkString("[", ";", "]")
  }

  def arrayAwareToString[T <: Product: TypeTag](p: T) = {
    val params = TypeUtils.caseClassParamsOf[T]
    p.productIterator.zip(params.iterator).map {
      case (f, (name, typ)) =>
        if (typ.toString.startsWith("scala.Array"))
          s"${name}=${TypeUtils.arrayToString(f)}"
        else
          s"${name}=$f"
    }.mkString(p.productPrefix + "(", ", ", ")")
  }
}