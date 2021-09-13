package es.jnsoft.domain.usecase

interface BaseFlowUseCase<in Parameter, out Result> {

    operator fun invoke(params: Parameter): Result
}