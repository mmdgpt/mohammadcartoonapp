package com.example.ui

import kotlinx.serialization.Serializable

@Serializable
data object CoverRoute

@Serializable
data object MenuRoute

@Serializable
data object GroupsRoute

@Serializable
data class GroupDetailRoute(
    val groupId: String,
    val pageNum: Int = 1,
)

@Serializable
data class ProductDetailRoute(
    val productId: String,
)

@Serializable
data object AboutRoute

@Serializable
data object ContactRoute

@Serializable
data object AdminPanelRoute
