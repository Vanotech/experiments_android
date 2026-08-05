package com.vanotech.experiments.data.tvguide.local.db

import com.vanotech.experiments.data.tvguide.local.db.model.RemoteKeyEntity
import org.koin.core.annotation.Singleton

@Singleton
internal class RemoteKeyDaoService(
    private val dao: RemoteKeyDao
) {
    suspend fun deleteAll() = dao.deleteAll()

    suspend fun getsFlow(id: String) = dao.get(id)

    suspend fun getCreationTime() = dao.getCreationTime()

    suspend fun insert(item: RemoteKeyEntity) = dao.insert(item)

    suspend fun insert(items: Collection<RemoteKeyEntity>) = dao.insert(items)
}