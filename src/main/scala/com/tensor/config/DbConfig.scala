package com.tensor.config

import pureconfig.generic.derivation.default.*
import pureconfig.ConfigReader
import pureconfig.error.CannotConvert
import com.comcast.ip4s.Host
import com.comcast.ip4s.Port

final case class DbConfig(
    host: String,
    port: Int,
    username: String,
    password: String,
    database: String,
) derives ConfigReader {}
