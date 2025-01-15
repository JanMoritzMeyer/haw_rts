case class Event(callback: () => Unit, endTime: Double) {

  private var done: Boolean = false

  def taskDone(): Unit = {
    done = true
  }

  def isDone: Boolean = done

  def triggerCallback(): Unit = {
    taskDone()
    callback()
  }
  
}
