package com.tensor.domain

import tsec.authentication.*
import tsec.mac.jca.HMACSHA256

object auth {

  type JwtToken = AugmentedJWT[HMACSHA256, String]

  final case class Tokens(
      authToken: JwtToken,
      refreshToken: JwtToken,
  ) {}

}
