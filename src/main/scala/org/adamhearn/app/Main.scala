package org.adamhearn.app

import kyo.*
import sttp.tapir.*
import sttp.tapir.server.netty.*

object Main extends KyoApp:
  def app = defer:
    val port = await(System.property[Int]("PORT", 80))
    val options = NettyKyoServerOptions
      .default(enableLogging = false)
      .forkExecution(false)
    val config =
      NettyConfig.default.withSocketKeepAlive
        .copy(lingerTimeout = None)

    val server =
      NettyKyoServer(options, config)
        .host("0.0.0.0")
        .port(port)
    await(Console.println(s"Starting... 0.0.0.0:$port"))
    await(Routes.run(server):
      Routes.add(
        _.get
          .in("echo" / path[String])
          .out(stringBody)
      )(input => input)
    )

  run(app)
