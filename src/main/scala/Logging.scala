import java.io.{FileOutputStream, PrintWriter}

object Logging {

  def writePackageLog(node: Node, packet: Packet): Unit = {
    new PrintWriter(new FileOutputStream(s"${node.name}_${packet.stream}.txt", true)) {
      append(s"arrival_time ${GlobalTime.time}.0 ns : Frame(UID:${packet.uid}, stream: ${packet.stream}, pcp: ${packet.pcp}, size: ${packet.size}, realuuid: ${packet.uuid}\r\n")
      close()
    }

  }

}
