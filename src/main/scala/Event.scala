case class Event(connection: Connection, packet: Packet, endTime: Long) {
  
  def end() = {
    Console.println("Package removed")
    connection.removePackage(packet)
  }
  
}
