package com.hold.rich.api.bean


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("accFillSz")
    val accFillSz: String,
    @SerializedName("avgPx")
    val avgPx: String,
    @SerializedName("cTime")
    val cTime: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("ccy")
    val ccy: String,
    @SerializedName("clOrdId")
    val clOrdId: String,
    @SerializedName("fee")
    val fee: String,
    @SerializedName("feeCcy")
    val feeCcy: String,
    @SerializedName("fillPx")
    val fillPx: String,
    @SerializedName("fillSz")
    val fillSz: String,
    @SerializedName("fillTime")
    val fillTime: String,
    @SerializedName("instId")
    val instId: String,
    @SerializedName("instType")
    val instType: String,
    @SerializedName("lever")
    val lever: String,
    @SerializedName("ordId")
    val ordId: String,
    @SerializedName("ordType")
    val ordType: String,
    @SerializedName("pnl")
    val pnl: String,
    @SerializedName("posSide")
    val posSide: String,
    @SerializedName("px")
    val px: String,
    @SerializedName("rebate")
    val rebate: String,
    @SerializedName("rebateCcy")
    val rebateCcy: String,
    @SerializedName("side")
    val side: String,
    @SerializedName("slOrdPx")
    val slOrdPx: String,
    @SerializedName("slTriggerPx")
    val slTriggerPx: String,
    @SerializedName("slTriggerPxType")
    val slTriggerPxType: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("sz")
    val sz: String,
    @SerializedName("tag")
    val tag: String,
    @SerializedName("tdMode")
    val tdMode: String,
    @SerializedName("tgtCcy")
    val tgtCcy: String,
    @SerializedName("tpOrdPx")
    val tpOrdPx: String,
    @SerializedName("tpTriggerPx")
    val tpTriggerPx: String,
    @SerializedName("tpTriggerPxType")
    val tpTriggerPxType: String,
    @SerializedName("tradeId")
    val tradeId: String,
    @SerializedName("uTime")
    val uTime: String
)