package scrapping

import play.api.libs.json.{Format, Json, Writes}

case class Candidate(
    name: String,
    front: String,
    image: String,
    lead: Int,
    constituency: String,
    party: String,
    votes: Int,
    age: Int,
    gender: String
)

object Candidate {
  implicit val writes: Format[Candidate] = Json.format[Candidate]
}
