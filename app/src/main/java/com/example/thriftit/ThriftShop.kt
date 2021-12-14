package com.example.thriftit

import java.util.*

class ThriftShop(var name: String, var street: String, var streetNumber:Int) {
    var id:String = UUID.randomUUID().toString().replace("-", "")

}