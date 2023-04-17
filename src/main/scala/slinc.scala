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
  def julia_mul_inplace(c: MalloccMatrix, a: MalloccMatrix, b: MalloccMatrix): Unit
end myJlib

// val juliaLib = FSet.instance[JuliaLib]
val superJ = FSet.instance[myJlib]

case class MalloccMatrix(
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

  //val format = Ptr.copy("%i hello: %s %i")
  //

  // the above works, but now we would want to test Julia. 
  //juliaLib.jl_init()
  
  //Scope.confined {
    //println("Code supplied to Julia directly")    
    //val t = Ptr.copy("print(sqrt(2.0))")
//    juliaLib.jl_eval_string(t);
  //}
  
  //println("\n Now let's call a pre-compiled library")
  //println(superJ.mul_inplace(1))
  //juliaLib.jl_atexit_hook(0)



  Scope.confined {
    val size : Long = 1
    
    val a : Ptr[Double] = Ptr.copy[Double](1.0)
    val b : Ptr[Double] = Ptr.copy[Double](1.0)
    val c : Ptr[Double] = Ptr.copy[Double](0.0)

    val ap = MalloccMatrix(a, 1,1,1 )
    val bp = MalloccMatrix(b, 1,1,1 )
    val cp = MalloccMatrix(c, 1,1,1 )

    println("calling native julia function")
    superJ.julia_mul_inplace(cp, ap, bp)
    println("Exited native julia function")
    
    println(c)
    
  }


