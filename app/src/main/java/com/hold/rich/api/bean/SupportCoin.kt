package com.hold.rich.api.bean

import com.google.gson.annotations.SerializedName

data class SupportCoin(
    @SerializedName("canDep")
    val canDep: Boolean,
    @SerializedName("canInternal")
    val canInternal: Boolean,
    @SerializedName("canWd")
    val canWd: Boolean,
    @SerializedName("ccy")
    val ccy: String,
    @SerializedName("chain")
    val chain: String,
    @SerializedName("mainNet")
    val mainNet: Boolean,
    @SerializedName("maxFee")
    val maxFee: String,
    @SerializedName("minFee")
    val minFee: String,
    @SerializedName("minWd")
    val minWd: String,
    @SerializedName("name")
    val name: String
)
