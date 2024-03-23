package com.example.domain

data class ForceFresh private constructor(
    private val requestTimeMs: Long,
    private val forceFresh: Boolean
) {

    val shouldRefresh: Boolean
        get() = forceFresh && System.currentTimeMillis() - requestTimeMs < 500
    companion object {
        fun refresh() = ForceFresh(System.currentTimeMillis(), true)
        fun idle() = ForceFresh(-1, false)
    }
}
