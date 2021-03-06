package org.akkajs

import org.scalatest._
import akka.actor._
import akka.testkit._
import scala.scalajs.js

class WebSocketChannelSpec extends TestKit(ActorSystem("WSCSpec"))
    with WordSpecLike
    with Matchers
    with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A WebSocketChannel" must {

    "send messages to channel" in {

      val probe = TestProbe()

      val mockWSChannel = js.Dynamic.literal(
        "send" -> {(x: Any) => probe.ref ! (x)}
      )

      val channelActor = system.actorOf(Props(
        new WSTwitterChannelActor(mockWSChannel) {
          override def preStart() = {}
      }))

      channelActor ! "foo"
      probe.expectMsg("foo")

      channelActor ! "bar"
      probe.expectMsg("bar")

    }
  }
}
