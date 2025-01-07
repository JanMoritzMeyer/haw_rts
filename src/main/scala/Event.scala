case class Event(callback: () => Unit, endTime: Long) {
  
  def triggerCallback(): Unit = {
    callback()
  }
  
}
