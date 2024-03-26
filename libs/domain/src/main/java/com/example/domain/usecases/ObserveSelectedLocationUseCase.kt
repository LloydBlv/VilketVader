package com.example.domain.usecases

import com.example.domain.repositories.LocationRepository
import com.example.domain.models.NoLocationSelectedException
import com.example.domain.utils.SubjectInteractor
import com.example.domain.models.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveSelectedLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) : SubjectInteractor<ObserveSelectedLocationUseCase.Params, Result<Location>>() {
    data class Params(val forceFresh: Boolean)

    override fun createObservable(params: Params): Flow<Result<Location>> {
        return locationRepository.observeSelectedLocation()
            .map {
                if (it == null) {
                    Result.failure(NoLocationSelectedException())
                } else {
                    Result.success(it)
                }
            }
            .catch { emit(Result.failure(it)) }
    }
}
