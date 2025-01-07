case class Frame(stream: String, pcp: Int, minsize: Int, maxsize: Int) {
  def getSize: Int = (minsize + maxsize) / 2
}
