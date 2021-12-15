package es.jnsoft.usecase

interface BaseFlowUseCase<in Parameter, out Result> {

    operator fun invoke(params: Parameter): Result
}