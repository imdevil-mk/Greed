package com.hold.rich.okex

import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object HmacSHA256Base64Utils {
    /**
     * Signing a Message.<br></br>
     *
     *
     * using: Hmac SHA256 + base64
     *
     * @param timestamp   the number of seconds since Unix Epoch in UTC. Decimal values are allowed.
     * eg: 2018-03-08T10:59:25.789Z
     * @param method      eg: POST
     * @param requestPath eg: /orders
     * @param queryString eg: before=2&limit=30
     * @param body        json string, eg: {"product_id":"BTC-USD-0309","order_id":"377454671037440"}
     * @param secretKey   user's secret key eg: E65791902180E9EF4510DB6A77F6EBAE
     * @return signed string   eg: TO6uwdqz+31SIPkd4I+9NiZGmVH74dXi+Fd5X0EzzSQ=
     * @throws CloneNotSupportedException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    @Throws(
        CloneNotSupportedException::class,
        InvalidKeyException::class,
        UnsupportedEncodingException::class
    )
    fun sign(
        timestamp: String,
        method: String,
        requestPath: String,
        queryString: String?,
        body: String?,
        secretKey: String
    ): String {
        val preHash = preHash(timestamp, method, requestPath, queryString, body)
        val secretKeyBytes: ByteArray = secretKey.toByteArray(StandardCharsets.UTF_8)
        val secretKeySpec = SecretKeySpec(secretKeyBytes, "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(secretKeySpec)
        return Base64.getEncoder()
            .encodeToString(mac.doFinal(preHash.toByteArray(StandardCharsets.UTF_8)))
    }

    /**
     * the prehash string = timestamp + method + requestPath + body .<br></br>
     *
     * @param timestamp   the number of seconds since Unix Epoch in UTC. Decimal values are allowed.
     * eg: 2018-03-08T10:59:25.789Z
     * @param method      eg: POST
     * @param requestPath eg: /orders
     * @param queryString eg: before=2&limit=30
     * @param body        json string, eg: {"product_id":"BTC-USD-0309","order_id":"377454671037440"}
     * @return prehash string eg: 2018-03-08T10:59:25.789ZPOST/orders?before=2&limit=30{"product_id":"BTC-USD-0309",
     * "order_id":"377454671037440"}
     */
    fun preHash(
        timestamp: String, method: String, requestPath: String,
        queryString: String?, body: String?
    ): String {
        val preHash = StringBuilder()
        preHash.append(timestamp)
        preHash.append(method.toUpperCase())
        preHash.append(requestPath)
        //get方法
        if (!queryString.isNullOrEmpty()) {
            //在queryString前面拼接上？
            preHash.append("?").append(queryString)
            //改动了
            //preHash.append(queryString);
        }
        //post方法
        if (!body.isNullOrEmpty()) {
            preHash.append(body)
        }
        return preHash.toString()
    }

    var MAC: Mac? = null

    init {
        try {
            MAC = Mac.getInstance("HmacSHA256")
        } catch (e: NoSuchAlgorithmException) {
            throw e
        }
    }
}