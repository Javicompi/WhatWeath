package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Daily
import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.repository.CurrentRepository
import es.jnsoft.domain.repository.DailyRepository
import es.jnsoft.domain.repository.HourlyRepository
import es.jnsoft.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveEntryUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
    private val hourlyRepository: HourlyRepository,
    private val currentRepository: CurrentRepository,
    private val settingsRepository: SettingsRepository
) {

    suspend fun invoke(
        current: Current,
        hourlies: List<Hourly>,
        dailies: List<Daily>
    ) = withContext(Dispatchers.IO) {
        dailyRepository.saveDailies(dailies)
        hourlyRepository.saveHourlies(hourlies)
        currentRepository.saveCurrent(current)
        settingsRepository.setSelectedId(current.id)
    }
}