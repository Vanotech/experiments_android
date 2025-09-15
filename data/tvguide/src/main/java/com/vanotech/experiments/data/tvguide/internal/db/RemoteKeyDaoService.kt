package com.vanotech.experiments.data.tvguide.internal.db

import com.vanotech.experiments.data.tvguide.internal.db.schema.RemoteKey
import javax.inject.Inject


internal class RemoteKeyDaoService @Inject constructor(
    private val dao: RemoteKeyDao
) {
    suspend fun deleteAll() = dao.deleteAll()

    suspend fun getsFlow(id: String) = dao.get(id)

    suspend fun getCreationTime() = dao.getCreationTime()

    suspend fun insert(item: RemoteKey) = dao.insert(item)

    suspend fun insert(items: Collection<RemoteKey>) = dao.insert(items)
}