package com.vanotech.experiments.data.tvguide.internal.db

import javax.inject.Inject


internal class RemoteKeyDaoService @Inject constructor(
    private val dao: RemoteKeyDao
) {
    suspend fun deleteAll() = dao.deleteAll()

    suspend fun getsFlow(id: String) = dao.get(id)

    suspend fun getCreationTime() = dao.getCreationTime()

    suspend fun insert(item: RemoteKeyEntity) = dao.insert(item)

    suspend fun insert(items: Collection<RemoteKeyEntity>) = dao.insert(items)
}