package es.jnsoft.domain.usecase

import es.jnsoft.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetSelectedIdUseCase @Inject constructor(
    private val repository: SettingsRepository
) : BaseUseCase<Long, Unit> {

    override suspend fun invoke(params: Long) = withContext(Dispatchers.IO) {
        repository.setSelectedId(params)
    }
}