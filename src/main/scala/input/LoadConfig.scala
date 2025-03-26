package input

import pureconfig.ConfigSource
import pureconfig.generic.auto._

object LoadConfig {

  // function that either loads the game config from application.conf or returns an exception
  def apply: Either[Exception, Config] = ConfigSource.default.load[Config] match {
    case Left(e) => Left(new Exception(e.toString()))
    case Right(config) => Right(config)
  }

}
