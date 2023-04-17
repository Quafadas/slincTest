using StaticCompiler
using StaticTools
using LoopVectorization
using Base: RefValue

@inline function mul!(C::MallocArray, A::MallocArray, B::MallocArray)
    @turbo for n ∈ indices((C,B), 2), m ∈ indices((C,A), 1)
        Cmn = zero(eltype(C))
        for k ∈ indices((A,B), (2,1))
            Cmn += A[m,k] * B[k,n]
        end
        C[m,n] = Cmn
    end
    return 0
end

tt = (RefValue{MallocMatrix{Float64}}, RefValue{MallocMatrix{Float64}}, RefValue{MallocMatrix{Float64}})
# this will let us accept pointers to MallocArrays
mul!(C::Ref,A::Ref,B::Ref) = mul!(C[], A[], B[])

#in a console, run this; 
# compile_shlib(mul!, tt, "./", "mul_inplace", filename="libmul")

ttAdd = (RefValue{Ptr{Float64}}, RefValue{Ptr{Float64}}, RefValue{Ptr{Float64}})
@inline function add!(c :: malloc,  a:: malloc , b :: malloc )
    c = zero(eltype(c))
    c = a + b
    return 0
end 

add!(C :: Ref, A:: Ref, B ::Ref) = add!(C, A, B)


ttAdd2 = (Float64, Float64)
@inline function add(a:: Float64 , b :: Float64 )
    return a+b
end 

add(A:: Float64, B ::Float64) = add(a, b)


