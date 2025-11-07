package com.smart.keuneunong.repository

import com.smart.keuneunong.database.AppDatabase
import com.smart.keuneunong.database.asDomainModel
import com.smart.keuneunong.domain.Details
import com.smart.keuneunong.network.DetailsApi
import com.smart.keuneunong.network.model.asDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val detailsApi: DetailsApi,
    private val appDatabase: AppDatabase
) {

    fun getUserDetails(user: String): Flow<Details?> =
        appDatabase.usersDao.getDetails(user).map { it?.asDomainModel() }

    suspend fun refreshDetails(user: String) {
        try {
            val userDetails = detailsApi.getDetails(user)
            appDatabase.usersDao.insertDetails(userDetails.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

}