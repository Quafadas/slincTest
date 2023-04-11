# Test embedding Julia in Scala

## References

https://docs.julialang.org/en/v1/manual/embedding/

https://github.com/matthijscox/embedjuliainc

## Julia Leg

We assume

1. that the working directory is c:/temp, and that 
2. we're working with the Julia library [here](https://github.com/JuliaLang/PackageCompiler.jl/tree/master/examples/MyLib) which has been placed in a subdirectory "superJlib"
3. Package compiler is installed.


```
cd("c:/temp")
using PackageCompiler
create_library("superJlib", "superJlibCompiled";lib_name="superJlib",precompile_execution_file="superJlib/build/generate_precompile.jl",precompile_statements_file="superJlib/build/additional_precompile.jl",header_files=["superJlib/build/mylib.h"], force=true)
 
```

## Scala leg

Uses scala-cli for ease

```
scala-cli setup-ide .
scala-cli run .

```

## Current problems

Needed to copy "C:\Users\partens\AppData\Local\Programs\Julia-1.8.3\lib\julia\sys.dll"

To : "C:\temp\superJlibCompiled\lib\julia"