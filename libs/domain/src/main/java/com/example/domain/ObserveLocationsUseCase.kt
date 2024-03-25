package com.example.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) : SubjectInteractor<ObserveLocationsUseCase.Params, Result<List<Location>>>() {
    data class Params(val forceFresh: Boolean = false)

    override fun createObservable(params: Params): Flow<Result<List<Location>>> {
        return locationRepository.observeLocations()
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}