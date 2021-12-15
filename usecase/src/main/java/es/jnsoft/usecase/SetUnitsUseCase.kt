package es.jnsoft.usecase

import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.repository.SettingsRepository
import es.jnsoft.domain.usecase.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetUnitsUseCase @Inject constructor(
    private val repository: SettingsRepository
) : BaseUseCase<Units, Unit> {

    override suspend fun invoke(params: Units) = withContext(Dispatchers.IO) {
        repository.setUnits(params)
    }
}