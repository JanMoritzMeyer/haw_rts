import scala.util.Random

case class Frame(stream: String, pcp: Int, minsize: Int, maxsize: Int) {
  def getSize: Int = Random.between(minsize, maxsize)
}
