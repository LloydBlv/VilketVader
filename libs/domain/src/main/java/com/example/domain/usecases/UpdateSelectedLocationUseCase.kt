package com.example.domain.usecases

import com.example.domain.utils.Interactor
import com.example.domain.repositories.LocationRepository
import javax.inject.Inject

class UpdateSelectedLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) : Interactor<UpdateSelectedLocationUseCase.Params, Unit>() {
    data class Params(val locationId: Int)

    override suspend fun doWork(params: Params) {
        locationRepository.updateSelectedLocation(params.locationId)
    }
}
