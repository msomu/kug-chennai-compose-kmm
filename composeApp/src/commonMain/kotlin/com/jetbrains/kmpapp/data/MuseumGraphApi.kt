package com.jetbrains.kmpapp.data

import com.apollographql.apollo.ApolloClient
import com.example.rocketreserver.GetAllSessionsQuery
import io.ktor.utils.io.CancellationException

interface MuseumGraphApi {
    suspend fun getData(): GetAllSessionsQuery.Data?
}

class KtorMuseumGraphApi(private val client: ApolloClient) : MuseumGraphApi {

    override suspend fun getData(): GetAllSessionsQuery.Data? {
        return try {
            val response = client.query(GetAllSessionsQuery())
            return response.execute().dataOrThrow()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            null
        }
    }
}
