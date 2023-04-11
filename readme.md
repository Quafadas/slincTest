


https://docs.julialang.org/en/v1/manual/embedding/

https://github.com/matthijscox/embedjuliainc

```
using P
```

```
 create_library("superJlib", "superJlibCompiled";lib_name="superJlib",precompile_execution_file="superJlib/build/generate_precompile.jl",precompile_statements_file="superJlib/build/additional_precompile.jl",header_files=["superJlib/build/mylib.h"], force=true)
 ```