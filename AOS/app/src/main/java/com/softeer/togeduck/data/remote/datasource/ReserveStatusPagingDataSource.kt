package com.softeer.togeduck.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.softeer.togeduck.data.mapper.toReserveStatusItemModel
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusItemModel
import com.softeer.togeduck.data.remote.service.ReserveStatusService
import javax.inject.Inject


class ReserveStatusPagingDataSource @Inject constructor(private val reserveStatusService: ReserveStatusService) :
    PagingSource<Int, ReserveStatusItemModel>() {

    companion object {
        private const val STARTING_KEY = 1
        const val ITEMS_PER_PAGE = 20
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReserveStatusItemModel> {
        val page = params.key ?: STARTING_KEY
        val data = reserveStatusService.getReserveStatusList(page, params.loadSize)

        val nextKey = if (data.last) {
            null
        } else {
            page + (params.loadSize / ITEMS_PER_PAGE)
        }
        return LoadResult.Page(
            data = data.content.map { item ->
                item.toReserveStatusItemModel()
            },
            prevKey = null,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, ReserveStatusItemModel>): Int? {
        return null
    }
}