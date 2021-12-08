package com.hold.rich.okex

data class AccountBalanceResponse(
    val code: String,
    val data: List<Data>,
    val msg: String
)

data class Data(
    val adjEq: String,
    val details: List<Detail>,
    val imr: String,
    val isoEq: String,
    val mgnRatio: String,
    val mmr: String,
    val notionalUsd: String,
    val ordFroz: String,
    val totalEq: String,
    val uTime: String
)

data class Detail(
    val availBal: String,
    val availEq: String,
    val cashBal: String,
    val ccy: String,
    val crossLiab: String,
    val disEq: String,
    val eq: String,
    val eqUsd: String,
    val frozenBal: String,
    val interest: String,
    val isoEq: String,
    val isoLiab: String,
    val isoUpl: String,
    val liab: String,
    val maxLoan: String,
    val mgnRatio: String,
    val notionalLever: String,
    val ordFrozen: String,
    val stgyEq: String,
    val twap: String,
    val uTime: String,
    val upl: String,
    val uplLiab: String
)