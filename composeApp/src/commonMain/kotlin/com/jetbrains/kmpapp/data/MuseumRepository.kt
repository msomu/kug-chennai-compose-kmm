package com.jetbrains.kmpapp.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MuseumRepository(
    private val museumApi: MuseumGraphApi,
    private val museumStorage: MuseumStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        museumStorage.saveObjects(museumApi.getData()?.sessions?.mapNotNull {
            MuseumObject(
                it.time,
                it.session,
                it.rooms.first().roomName
            )
        } ?: emptyList())
    }

    fun getObjects(): Flow<List<MuseumObject>> = museumStorage.getObjects()
}
