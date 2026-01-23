package com.example.domain.usecases

import com.example.domain.models.Location
import com.example.domain.models.NoLocationSelectedException
import com.example.domain.repositories.LocationRepository
import com.example.domain.utils.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ObserveSelectedLocationUseCase(
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
