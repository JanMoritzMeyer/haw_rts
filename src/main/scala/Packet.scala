import java.util.UUID

case class Packet(
                 size: Double,
                 nextHop: Node,
                 target: List[Node],
                 pcp: Int,
                 stream: String,
                 uid: Int,
                 uuid: UUID = UUID.randomUUID()
                 )
