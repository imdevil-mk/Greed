package com.hold.rich.api.bean

/*
给定币种列表的总体余额
 */
data class BalanceSummary(
    // 美金层面有效保证金 适用于跨币种保证金模式和组合保证金模式
    val adjEq: String,
    // 美金层面占用保证金 适用于跨币种保证金模式和组合保证金模式
    val imr: String,
    // 美金层面逐仓仓位权益 适用于单币种保证金模式和跨币种保证金模式和组合保证金模式
    val isoEq: String,
    // 美金层面保证金率 适用于跨币种保证金模式 和组合保证金模式
    val mgnRatio: String,
    // 美金层面维持保证金 适用于跨币种保证金模式和组合保证金模式
    val mmr: String,
    // 以美金价值为单位的持仓数量，即仓位美金价值 适用于跨币种保证金模式和组合保证金模式
    val notionalUsd: String,
    // 美金层面全仓挂单占用保证金 适用于跨币种保证金模式和组合保证金模式
    val ordFroz: String,
    // 美金层面权益
    val totalEq: String,
    // 账户信息的更新时间，Unix时间戳的毫秒数格式，如 1597026383085
    val uTime: String,
    // 各币种资产详细信息l
    val coinBalanceSummaries: List<CoinBalanceSummary>,
)

/*/
给定币种列表中某一币种的持仓信息
 */
data class CoinBalanceSummary(
    // 可用余额 适用于简单交易模式
    val availBal: String,
    // 可用保证金 适用于单币种保证金模式和跨币种保证金模式和组合保证金模式
    val availEq: String,
    // 币种余额
    val cashBal: String,
    // 币种
    val ccy: String,
    // 币种全仓负债额 适用于跨币种保证金模式和组合保证金模式
    val crossLiab: String,
    // 美金层面币种折算权益
    val disEq: String,
    // 币种总权益
    val eq: String,
    // 币种权益美金价值
    val eqUsd: String,
    // 币种占用金额
    val frozenBal: String,
    // 计息 适用于跨币种保证金模式和组合保证金模式
    val interest: String,
    // 币种逐仓仓位权益 适用于单币种保证金模式和跨币种保证金模式和组合保证金模式
    val isoEq: String,
    //币种逐仓负债额 适用于跨币种保证金模式和组合保证金模式
    val isoLiab: String,
    // 逐仓未实现盈亏 适用于单币种保证金模式和跨币种保证金模式和组合保证金模式
    val isoUpl: String,
    // 币种负债额 适用于跨币种保证金模式和组合保证金模式
    val liab: String,
    // 币种最大可借 适用于跨币种保证金模式和组合保证金模式
    val maxLoan: String,
    // 保证金率 适用于单币种保证金模式
    val mgnRatio: String,
    // 币种杠杆倍数 适用于单币种保证金模式
    val notionalLever: String,
    // 挂单冻结数量
    val ordFrozen: String,
    // 策略权益
    val stgyEq: String,
    // 当前负债币种触发系统自动换币的风险 0、1、2、3、4、5其中之一，数字越大代表您的负债币种触发自动换币概率越高 适用于跨币种保证金模式
    val twap: String,
    // 币种余额信息的更新时间，Unix时间戳的毫秒数格式，如 1597026383085
    val uTime: String,
    // 未实现盈亏 适用于单币种保证金模式和跨币种保证金模式和组合保证金模式
    val upl: String,
    // 由于仓位未实现亏损导致的负债 适用于跨币种保证金模式和组合保证金模式
    val uplLiab: String
)