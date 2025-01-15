import scala.util.Random

case class Frame(stream: String, pcp: Int, minsize: Double, maxsize: Double) {
  def getSize(): Double = {
    if (minsize.equals(maxsize)) {
      minsize
    }
    else {
      Random.between(minsize, maxsize)
    }
  }
}