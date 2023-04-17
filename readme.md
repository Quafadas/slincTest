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

## Useful 

```
nm -D --defined-only libname.so 
```

## Scala leg

Uses scala-cli for ease, in codespaces setup via apit install. 

```
curl -s --compressed "https://virtuslab.github.io/scala-cli-packages/KEY.gpg" | sudo apt-key add -
sudo curl -s --compressed -o /etc/apt/sources.list.d/scala_cli_packages.list "https://virtuslab.github.io/scala-cli-packages/debian/scala_cli_packages.list"
sudo apt update
sudo apt install scala-cli

scala-cli setup-ide .
scala-cli run .

```

## Current problems

Needed to copy "C:\Users\partens\AppData\Local\Programs\Julia-1.8.3\lib\julia\sys.dll"

To : "C:\temp\superJlibCompiled\lib\julia"

```
WARNING: Using incubator modules: jdk.incubator.foreign
Got a quotient of 2 and a remainder of 1
41
18467
WARNING: failed to initialize stack walk info
Code supplied to Julia directly
1.4142135623730951
 Now let's call a pre-compiled library

Please submit a bug report with steps to reproduce this fault, and any error messages that follow (in their entirety). Thanks.
Exception: EXCEPTION_ACCESS_VIOLATION at 0x0 -- unknown function (ip: 0000000000000000)
in expression starting at none:0
unknown function (ip: 0000000000000000)
Allocations: 2907 (Pool: 2896; Big: 11); GC: 0
```

## Let's experiment with the static compiler
Install julia 
https://julialang.org/downloads/platform/

export PATH="$PATH:/workspaces/slincTest/julia/julia-1.8.5/bin"

```julia
using Pkg
Pkg.add("StaticCompiler")
using StaticTools, StaticCompiler
```

Is suggestive that there is some content to the file we're trying to call. However, it doesn't seem to "work"... 
```
@Quafadas ➜ /workspaces/slincTest (master) $ nm --demangle --defined-only --dynamic /workspaces/slincTest/myJlib/libmul.so
0000000000003ec0 T julia_mul_inplace

```
