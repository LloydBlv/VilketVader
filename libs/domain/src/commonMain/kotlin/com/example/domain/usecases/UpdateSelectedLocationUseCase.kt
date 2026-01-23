package com.example.domain.usecases

import com.example.domain.repositories.LocationRepository
import com.example.domain.utils.Interactor

class UpdateSelectedLocationUseCase(
    private val locationRepository: LocationRepository
) : Interactor<UpdateSelectedLocationUseCase.Params, Unit>() {
    data class Params(val locationId: Int)

    override suspend fun doWork(params: Params) {
        locationRepository.updateSelectedLocation(params.locationId)
    }
}
