package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.data.database.AppDatabase
import com.smart.keuneunong.data.database.asDomainModel
import com.smart.keuneunong.domain.UserDetails
import com.smart.keuneunong.data.network.DetailsApi
import com.smart.keuneunong.data.network.model.asDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val detailsApi: DetailsApi,
    private val appDatabase: AppDatabase
) {

    fun getUserDetails(user: String): Flow<UserDetails?> =
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