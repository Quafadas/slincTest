import fr.hammons.slinc.annotations.*
import fr.hammons.slinc.runtime.given
import fr.hammons.slinc.types.*
import fr.hammons.slinc.*

case class div_t(quot: CInt, rem: CInt) derives Struct 

trait MyLib derives FSet:
  def div(numer: CInt, denom: CInt): div_t
  def rand(): CInt

val myLib = FSet.instance[MyLib]

@NeedsFile("""C:\temp\superJlibCompiled\bin\libjulia.dll""")
trait SuperJLib derives FSet:
  def jl_init():Unit  
  def jl_atexit_hook(code : CInt):Unit
  def jl_eval_string(code : Ptr[CChar]):Unit
  //def increment(num: CInt): CInt

val jLib = FSet.instance[SuperJLib]

@main def calc = 
  val a = myLib.div(5,2)
  println(s"Got a quotient of ${a.quot} and a remainder of ${a.rem}")
  println(myLib.rand())
  println(myLib.rand())

  //val format = Ptr.copy("%i hello: %s %i")
  // val t = Ptr.copy("print(sqrt(2.0))")

  // the above works, but now we would want to test Julia. 
  jLib.jl_init()
  // jLib.jl_eval_string( t );
  jLib.jl_atexit_hook(0)


