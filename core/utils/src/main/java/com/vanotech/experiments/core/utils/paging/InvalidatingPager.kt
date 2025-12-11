package com.vanotech.experiments.core.utils.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator

class InvalidatingPager<Key : Any, Value : Any> @OptIn(ExperimentalPagingApi::class) constructor(
    config: PagingConfig,
    initialKey: Key? = null,
    remoteMediator: RemoteMediator<Key, Value>?,
    pagingSourceFactory: () -> PagingSource<Key, Value>
) {
    private val invalidatingPagingSourceFactory = InvalidatingPagingSourceFactory(
        pagingSourceFactory = pagingSourceFactory
    )

    @OptIn(ExperimentalPagingApi::class)
    private val pager = Pager(
        config = config,
        initialKey = initialKey,
        remoteMediator = remoteMediator,
        pagingSourceFactory = invalidatingPagingSourceFactory
    )

    val flow = pager.flow

    fun invalidate() = invalidatingPagingSourceFactory.invalidate()
}