package com.example.domain

import javax.inject.Inject

class UpdateSelectedLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) : Interactor<UpdateSelectedLocationUseCase.Params, Unit>() {
    data class Params(val locationId: Int)

    override suspend fun doWork(params: Params) {
        locationRepository.updateSelectedLocation(params.locationId)
    }
}