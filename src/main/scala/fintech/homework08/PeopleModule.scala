package fintech.homework08

import java.sql.ResultSet
import java.time.LocalDate

trait PeopleModule {

  case class Person(name: String, birthday: LocalDate)

  def storePerson(p: Person): DBRes[Unit] = {
    println("store person " + p)
    DBRes.update("INSERT INTO people(name, birthday) VALUES (?, ?)", List(p.name, p.birthday))
  }

  val readPerson: ResultSet => Person = { rs =>
    val name = rs.getString("name")
    val bd = rs.getDate("birthday").toLocalDate
    Person(name, bd)
  }

  def setup(): DBRes[Unit] =
    for {
      _ <- DBRes.update("DROP TABLE people", List.empty)
      _ <- DBRes.update("CREATE TABLE people(name VARCHAR(256), birthday DATE)", List.empty)
      _ <- storePerson(Person("Alice", LocalDate.of(1970, 1, 1)))
      _ <- storePerson(Person("Bob", LocalDate.of(1981, 5, 12)))
      _ <- storePerson(Person("Charlie", LocalDate.of(1979, 2, 20)))
    } yield ()

}