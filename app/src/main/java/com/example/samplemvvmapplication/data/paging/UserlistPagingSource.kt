package com.example.samplemvvmapplication.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.samplemvvmapplication.data.model.DashboardModel
import com.example.samplemvvmapplication.data.model.Data
import com.example.samplemvvmapplication.data.network.RetroApi
import retrofit2.HttpException
import java.io.IOException

class UserlistPagingSource(
    val api: RetroApi,
    val query: @JvmSuppressWildcards Int) :
    PagingSource<Int, Data>() {
    private var dashboardModel : DashboardModel ?= null
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let {anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {

                val pageNumber = params.key ?: 1

                val resp = api.getUserList(pageNumber)
                dashboardModel = resp.body()
                val page = dashboardModel?.page
                val list = dashboardModel?.data ?: arrayListOf()

                LoadResult.Page(
                    data = list,
                    prevKey = if (pageNumber == 1) null else pageNumber - 1,
                    nextKey = if (page == 0 )null else if (pageNumber == dashboardModel?.totalPages) null else pageNumber + 1
                )
        }catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }
}