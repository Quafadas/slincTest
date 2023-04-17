import fr.hammons.slinc.annotations.*
import fr.hammons.slinc.runtime.given
import fr.hammons.slinc.types.*
import fr.hammons.slinc.*
import fr.hammons.slinc.DescriptorOf.given_DescriptorOf_Int
//import fr.hammons.slinc.Allocator

case class div_t(quot: CInt, rem: CInt) derives Struct 

trait MyLib derives FSet:
  def div(numer: CInt, denom: CInt): div_t
  def rand(): CInt

val myLib = FSet.instance[MyLib]

// @NeedsFile("""C:\temp\MyLibCompiled\bin\libjulia.dll""")
// trait JuliaLib derives FSet:
//   def jl_init():Unit  
//   def jl_atexit_hook(code : CInt):Unit
//   def jl_eval_string(code : Ptr[CChar]):Unit
  //def increment32(num: CInt): CInt


// TODO: Report bug to slinc / check with scala-cli resourtce location. Couldn't get this to work as part of a resource
@NeedsFile("""/workspaces/slincTest/myJlib/libmul.so""")  
trait myJlib derives FSet:
  // def init(): Unit
  def julia_mul_inplace(c: MalloccMatrix1, a: MalloccMatrix1, b: MalloccMatrix1): Unit
end myJlib

// val juliaLib = FSet.instance[JuliaLib]
val superJ = FSet.instance[myJlib]

// case class MalloccMatrix(
//   pointer: Ptr[Double],
//   length: CFloat,
//   size : (CFloat, CFloat)
// ) derives Struct

case class MalloccMatrix1(
  pointer: Ptr[Double],
  length: CFloat,
  s1 : CFloat,
  s2: CFloat
) derives Struct

@main def calc = 
  val a = myLib.div(5,2)
  println(s"Got a quotient of ${a.quot} and a remainder of ${a.rem}")
  println(myLib.rand())
  println(myLib.rand())

  Scope.confined {
    val size : Long = 1
    
    val a  = Ptr.copy[Double](Array(1.0))
    val b = Ptr.copy[Double](Array(1.0))
    val c = Ptr.copy[Double](Array(0.0))

    //val as = Ptr.copy[(CFloat, CFloat)]((1,1))

    val ap = MalloccMatrix1(a, 1,1,1)
    val bp = MalloccMatrix1(b, 1,1,1)
    val cp = MalloccMatrix1(c, 1,1,1)

    println("calling native julia function")
    superJ.julia_mul_inplace(cp, ap, bp)
    println("Exited native julia function")
    
    println(c)
    
  }


