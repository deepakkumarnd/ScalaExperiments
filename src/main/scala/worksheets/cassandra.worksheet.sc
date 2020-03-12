 import com.datastax.oss.driver.api.core.CqlSession
 import com.datastax.oss.driver.api.core.cql.{ResultSet, Row, SimpleStatement}
 import com.datastax.oss.driver.api.querybuilder.QueryBuilder._
 import com.datastax.oss.driver.api.querybuilder.select.Select

// https://mvnrepository.com/artifact/com.datastax.oss/java-driver-core/4.5.0

val session: CqlSession = CqlSession.builder.build

val query: Select = selectFrom("system", "local").column("release_version")
val statement: SimpleStatement = query.build()

val rs: ResultSet = session.execute(statement)

val row: Row = rs.one()

row.getString("release_version")

