import java.net.InetSocketAddress
import java.util.UUID

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.{
  AsyncResultSet,
  BoundStatement,
  ResultSet,
  Row
}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.control.NonFatal
import scala.jdk.CollectionConverters._
import scala.compat.java8.FutureConverters.CompletionStageOps
import scala.concurrent.duration._

case class User(id: UUID, name: String, age: Int)

object CassandraDemo extends App {
  val keyspace = "mykeyspace"
  val table = "users"

  def getSession: CqlSession = {
    CqlSession
      .builder()
      .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
      .withAuthCredentials("cassandra", "cassandra")
      .withLocalDatacenter("Cassandra")
      .withKeyspace(keyspace)
      .build()
  }

  val session: CqlSession = getSession

  def getReleaseVersion: String = {
    val session = getSession

    try {
      val results = session.execute("select release_version from system.local")
      val row = results.one()
      row.getString("release_version")
    } catch {
      case NonFatal(e) =>
        e.getLocalizedMessage
    } finally session.close()
  }

  def runQuery(stmt: BoundStatement): Option[ResultSet] =
    try {
      Some(session.execute(stmt))
    } catch {
      case NonFatal(e) =>
        println(e.getLocalizedMessage)
        None
    }

  def runAsyncQuery(stmt: BoundStatement): Future[AsyncResultSet] =
    session.executeAsync(stmt).toScala

  def allUsers: Seq[User] = {
    val query = s"select * from $table limit 10"

    runQuery(session.prepare(query).bind())
      .map(_.asScala.map(buildUser(_)).toSeq)
      .getOrElse(Seq.empty[User])
  }

  def findUser(id: UUID): Option[User] = {
    val query = s"select * from $table where id = ?"

    runQuery(
      session.prepare(query).bind(id)
    ).map(_.one()).map(buildUser(_))
  }

  def asyncFindUser(id: UUID): Future[Option[User]] = {
    val query = s"select * from $table where id = ?"

    runAsyncQuery(
      session.prepare(query).bind(id)
    ).map(rs => Option(rs.one()).map(buildUser(_)))
  }

  def buildUser(row: Row): User =
    User(row.getUuid("id"), row.getString("name"), row.getInt("age"))

  val f: Future[Option[User]] = asyncFindUser(
    UUID.fromString("fe133ac6-aac5-4b66-84b0-ff052690ded5")
  )

  println("Results after evaluating future is")
  println(Await.result(f, 10.seconds))
  println("Done")

  println(allUsers)
  println(findUser(UUID.fromString("fe133ac6-aac5-4b66-84b0-ff052690ded5")))

  session.close()
}
