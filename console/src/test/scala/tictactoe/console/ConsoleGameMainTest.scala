package tictactoe.console

import org.scalatest._

class ConsoleGameMainTest extends FunSpec {
	describe("ClientMain.newEcho") {
		it("should create a new echo") {
			assert(ClientMain.newEcho.echo("hi") === "client:hi")
		}
	}
}