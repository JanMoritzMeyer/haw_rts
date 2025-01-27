object UID {
  var count = -1
  
  def getUnique: Int = {
    count += 1
    count
  }
}