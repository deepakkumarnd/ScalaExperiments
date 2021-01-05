import com.aerospike.client.{AerospikeClient, Bin, Key, Record}
import com.aerospike.client.policy.{WritePolicy}

import scala.jdk.CollectionConverters._

object AerospikeConnect extends App {
  val HOST = "localhost"
  val PORT = 3000
  val NAMESPACE = "test"

  implicit val client = new AerospikeClient(HOST, PORT)

  val writePolicy = new WritePolicy()
  writePolicy.sendKey = true
  writePolicy.totalTimeout = 50

  // Write bin
//  val key = new Key(NAMESPACE, "people", "deepak")
//  val bin1 = new Bin("age", 32)
//  client.put(writePolicy, key, bin1)
//  val bin2 = new Bin("gender", "Male")
//  client.put(writePolicy, key, bin2)

  def writeBins(
      namespace: String,
      setName: String
  )(keyName: String, bins: (String, Any)*)(
      implicit client: AerospikeClient,
      policy: WritePolicy = new WritePolicy()
  ): Unit = {
    val key = new Key(namespace, setName, keyName)
    val valueBins = bins.map {
      case (name, value) => new Bin(name, value)
    }
    client.put(policy, key, valueBins: _*)
  }

  val writerF = writeBins(NAMESPACE, "people") _

  writerF(
    "deepak",
    Seq("age" -> 32, "gender" -> "male", "job" -> "programming")
  )

  def deleteBin(
      namespace: String,
      setName: String
  )(keyName: String, binName: String)(
      implicit client: AerospikeClient,
      policy: WritePolicy = new WritePolicy()
  ): Unit = {
    val key = new Key(namespace, setName, keyName)
    client.put(policy, key, Bin.asNull(binName))
  }

  // Delete bin
//  val bin3 = new Bin("job", "Jobless")
//  client.put(writePolicy, key, bin3)
//  val bin4 = Bin.asNull(bin3.name)
//  client.put(writePolicy, key, bin4)

  val deleteF = deleteBin(NAMESPACE, "people") _

  deleteF("deepak", "job")

  // TTL
  val writePolicy2 = new WritePolicy()
  writePolicy2.expiration = 10 // 10 seconds
  writeBins(NAMESPACE, "people")(
    "deepak",
    ("online", "yes")
  )(
    client,
    writePolicy2
  )
//  val bin5 = new Bin("online", "typing")
//  client.put(writePolicy2, key, bin5)
//
//  // Reading records

  def readRecord(
      namespace: String,
      setName: String,
      keyName: String,
      bins: String*
  )(
      implicit client: AerospikeClient,
      policy: WritePolicy = new WritePolicy()
  ): Option[Record] = {
    val key = new Key(namespace, setName, keyName)
    Option(client.get(policy, key))
  }

  def readAllBins(namespace: String, setName: String, keyName: String)(
      implicit client: AerospikeClient,
      policy: WritePolicy = new WritePolicy()
  ): Map[String, AnyRef] = {
    readRecord(namespace, setName, keyName) match {
      case Some(record) => record.bins.asScala.toMap
      case None         => Map.empty[String, AnyRef]
    }
  }

  def readBins(
      namespace: String,
      setName: String,
      keyName: String,
      bins: String*
  )(
      implicit client: AerospikeClient,
      policy: WritePolicy = new WritePolicy()
  ): Map[String, AnyRef] = {
    readRecord(namespace, setName, keyName, bins: _*) match {
      case Some(record) => record.bins.asScala.toMap
      case None         => Map.empty[String, AnyRef]
    }
  }

  val bins = readAllBins(NAMESPACE, "people", "deepak")
//  val record = client.get(writePolicy, key)
//
//  val bins = record.bins.asScala
//
  bins.foreach { bin =>
    val value = bins.get(bin._1)
    println(s"Bin ${bin._1} => $value")
  }
//
//  // Read specific bins
//  val record1 = client.get(writePolicy, key, "age")
//  println("Reading specific bin")
//  println(record1.bins.asScala.get("age"))

  val bins2 = readBins(NAMESPACE, "people", "deepak", "age", "job")

  bins2.foreach { bin =>
    val value = bins.get(bin._1)
    println(s"Bin ${bin._1} => $value")
  }
//
//  // Batch reading records
//  val batchPolicy = new BatchPolicy()
//  val key2 = new Key(NAMESPACE, "people", "amrutha")
//  client.put(writePolicy, key2, new Bin("age", 26))
//
//  val keys: Array[Key] = Seq(key, key2).toArray
//  val records = client.get(batchPolicy, keys).toSeq
//
//  println("Reading multiple records using BatchPolicy")
//  records.foreach { rec =>
//    println("Bins \n" + rec.bins.keySet().asScala.mkString(", "))
//  }
//
//  // Doing multiple operations eg: increment counter
//  client.put(writePolicy, key2, new Bin("score", 100))
//  val incBin = new Bin("score", 1)
//  // Doing another operation eg: Write gender
//  val genderBin = new Bin("gender", "Female")
//  client.operate(
//    writePolicy,
//    key2,
//    Operation.add(incBin),
//    Operation.put(genderBin)
//  )
//
//  val bins1 = client.get(writePolicy, key2, "score", "gender").bins
//  println(bins1.get("score"))
//  println(bins1.get("gender"))
  client.close()
}
