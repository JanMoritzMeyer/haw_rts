object Converter {

  def ms_to_ns(ms: Double): Double = ms * 1_000

  def mbit_to_byte(mbit: Double): Double = mbit / (8 * 1_000_000)

  def s_to_ns(s: Double): Double = s * 1_000_000_000

  def mbits_to_bytens(mbits: Double): Double = mbits / 8000

}
