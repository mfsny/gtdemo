package tutorial

import akka.actor._


class gtdemoActor (val staticPath: String) extends gtdemoService with Actor {

  def actorRefFactory = context

  def receive = runRoute(gtdemoRoute)
}