package project1

import java.net.InetAddress

import akka.actor._

/**
 * Created by chelsea on 9/6/15.
 */

// The Boss acts like a server. It keeps track of all the problems and performs the job assignment.
class MineCoins() extends Actor {
  def receive = {
    case DoWork(numOfZeros,start,end) => {
      //println("It works!! Start: " + start)
      val dm = new DataMining()
      sender ! dm.mine(numOfZeros,start,end)
      //println("End")
    }
    /*case msg: String => {
      println("It works!!")
    }*/
  }
}

class Boss(numOfZeros: Long) extends Actor {
  var start: Long = 0
  var end: Long = 0
  val workSize: Integer = 1000000
  var totalAmtOfCoins: Integer = 0
  val cycles: Integer = 10

  def receive = {
    case msgFromServer: String => {
      /*if (msgFromServer.equals("Worker needs work!")) {
        start = start + workSize
        end = start + workSize
        println("Start Worker: " + start + " to " + end)
        sender ! DoWork(numOfZeros,start,end) // By using sender, one can refer to the actor that sent the message that the current actor last received
      }*/
      /*else */if (msgFromServer.equals("Server needs work!")) {
        var i = 0
        while (i < cycles) {
          start = start + workSize
          end = start + workSize
          context.actorOf(Props(new MineCoins)) ! DoWork(numOfZeros,start,end)
          //context.actorOf(Props(new Test)) ! "Hi test"
          i += 1
        }
        //context.system.shutdown
      }
      else {
        println(s"Boss received message '$msgFromServer'")
      }
    }
    case CoinCount(count) => {
      totalAmtOfCoins = totalAmtOfCoins + count
      println("Total coin count!!!: " + totalAmtOfCoins)
      //context.system.shutdown
    }
  }
}