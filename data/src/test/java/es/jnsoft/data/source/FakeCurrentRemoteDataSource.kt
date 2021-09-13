package es.jnsoft.data.source

import es.jnsoft.data.createFirstCurrent
import es.jnsoft.data.model.CurrentData
import es.jnsoft.data.remote.CurrentRemoteDataSource
import es.jnsoft.domain.model.Result

class FakeCurrentRemoteDataSource : CurrentRemoteDataSource {

    override suspend fun findCurrentByLatLon(lat: Double, lon: Double): Result<CurrentData> {
        return if (lat > 0.0 && lon > 0.0) {
            Result.Success(createFirstCurrent())
        } else {
            Result.Failure("Location not found")
        }
    }

    override suspend fun findCurrentByName(name: String): Result<CurrentData> {
        return if (name.isNotBlank()) {
            Result.Success(createFirstCurrent())
        } else {
            Result.Failure("Location not found")
        }
    }
}