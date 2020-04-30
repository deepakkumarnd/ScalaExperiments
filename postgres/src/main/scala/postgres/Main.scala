package postgres

import doobie._
import doobie.implicits._
import cats.effect.IO
import scala.concurrent.ExecutionContext

case class Country(id: Int, name: String, code: String)

// Prerequisites, run the following in the postgres console
// create database world;
// \c world
// create table countries(id SERIAL PRIMARY KEY, name VARCHAR(100), code VARCHAR(4));
// insert into countries(name, code) values('India', 'IN');
// insert into countries(name, code) values('United States Of America', 'US');

object Main extends App {
  implicit val cs = IO.contextShift(ExecutionContext.global)

  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql:world",
    "postgres",
    ""
  )

  // insert and return fields
  def insertNew(country: Country): ConnectionIO[Country] =
    sql"insert into countries (name, code) values (${country.name}, ${country.code})".update
      .withUniqueGeneratedKeys("id", "name", "code")

  def findByCode(code: String): ConnectionIO[Option[Country]] =
    sql"select id, code, name from countries where code = $code"
      .query[Country]
      .option

  def count(table: String) =
    (fr"select count(*) from" ++ Fragment.const(table)).query[Int].unique

  // select
  val result = findByCode("IN").transact(xa).unsafeRunSync()

  result match {
    case Some(country) => println(country)
    case None          => "County not found"
  }

  // insert
  val myCountry = Country(3, "France", "FR")
  println(insertNew(myCountry).transact(xa).unsafeRunSync())
  // count
  println(count("countries").transact(xa).unsafeRunSync())
}
