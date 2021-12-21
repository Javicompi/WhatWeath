package es.jnsoft.usecase

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.repository.CurrentRepository
import es.jnsoft.domain.repository.DailyRepository
import es.jnsoft.domain.repository.HourlyRepository
import es.jnsoft.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteEntryUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
    private val hourlyRepository: HourlyRepository,
    private val currentRepository: CurrentRepository,
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(current: Current) = withContext(Dispatchers.IO) {
        val lat = current.location.lat
        val lon = current.location.lon
        dailyRepository.deleteDailies(lat, lon)
        hourlyRepository.deleteHourlies(lat, lon)
        currentRepository.deleteCurrent(current)
        settingsRepository.setSelectedId(0)
    }
}