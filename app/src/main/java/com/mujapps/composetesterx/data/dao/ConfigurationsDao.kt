package com.mujapps.composetesterx.data.dao

import com.mujapps.composetesterx.models.Configuration

data class ConfigurationsDao(
    val poolId: String?, val clientId: String?, val clientSecret: String?, val region: String?
) {
    fun getConfigurations() = Configuration(
        mPoolId = poolId ?: "",
        mClientId = clientId ?: "",
        mClientSecret = clientSecret ?: "",
        mRegion = region ?: ""
    )
}
