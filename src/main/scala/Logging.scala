import java.io.{FileOutputStream, PrintWriter}

object Logging {

  def writePackageLog(node: Node, packet: Packet): Unit = {
    new PrintWriter(new FileOutputStream(s"${node.name}_${packet.stream}.txt", true)) {
      append(s"arrival time ${GlobalTime.time}.0 ns : Frame size ${packet.size} pcp ${packet.pcp} \r\n")
      close
    }

  }

}
