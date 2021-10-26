package es.jnsoft.data.source

import es.jnsoft.data.createHourly
import es.jnsoft.data.model.HourlyData
import es.jnsoft.data.remote.HourlyRemoteDataSource
import es.jnsoft.domain.model.Result

class FakeHourlyRemoteDataSource : HourlyRemoteDataSource {

    override suspend fun findHourly(lat: Double, lon: Double): Result<List<HourlyData>> {
        return if (lat > 1.0 && lon > 1.0) {
            Result.Success(listOf(createHourly(), createHourly(), createHourly()))
        } else {
            Result.Failure("Location not found")
        }
    }
}