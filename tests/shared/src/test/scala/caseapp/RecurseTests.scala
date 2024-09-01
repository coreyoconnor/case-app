package caseapp

import caseapp.core.Arg
import caseapp.core.help.{Help, HelpFormat, RuntimeCommandHelp, RuntimeCommandsHelp}
import caseapp.core.Scala3Helpers._
import utest._

object RecurseTests extends TestSuite {
  val format = HelpFormat.default(false)

  case class RecurseArg3(
    other3: String
  )

  object RecurseArg3 {
    implicit lazy val parser: Parser[RecurseArg3] = Parser.derive
    implicit lazy val help: Help[RecurseArg3]     = Help.derive
  }

  case class RecurseArg2(
    other2: String
  )

  object RecurseArg2 {
    implicit lazy val parser: Parser[RecurseArg2] = Parser.derive
    implicit lazy val help: Help[RecurseArg2]     = Help.derive
  }

  case class RecurseArg1(
    @Recurse recurseArg2: RecurseArg2 = RecurseArg2("default"),
    @Recurse recurseArg3: RecurseArg3 = RecurseArg3("default"),
    other1: String = "other1"
  )

  object RecurseArg1 {
    implicit lazy val parser: Parser[RecurseArg1] = Parser.derive
    implicit lazy val help: Help[RecurseArg1]     = Help.derive
  }

  case class RecurseArg0(
    @Recurse recurseArg1: RecurseArg1 = RecurseArg1(),
    other0: String = "other0"
  )

  object RecurseArg0 {
    implicit lazy val parser: Parser[RecurseArg0] = Parser.derive
    implicit lazy val help: Help[RecurseArg0]     = Help.derive
  }

  @HelpMessage("help")
  case class Args(
    @Recurse recurseArg0: RecurseArg0 = RecurseArg0(),
    other: String = "other"
  )

  object Args {
    implicit lazy val parser: Parser[Args] = Parser.derive
    implicit lazy val help: Help[Args] = Help.derive
  }

  object Issue566App extends CaseApp[Args] {
    def run(options: Args, remainingArgs: RemainingArgs): Unit = ???
  }

  val tests = Tests {
    test("reproduce issue 566") {
      assert(Issue566App.help != null)
    }
  }
}


