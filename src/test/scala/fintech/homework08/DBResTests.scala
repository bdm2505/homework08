package fintech.homework08

import org.scalatest.{FlatSpec, Matchers}

class DBResTests extends FlatSpec with Matchers {

  trait DBTest {
    val uri = "jdbc:h2:~/test"
    var call = 0
    def emptyDB = DBRes(_ => ())
  }

  "def map" should "call a function once" in new DBTest {
    emptyDB.map(_ => call += 1).execute(uri)

    call shouldBe 1
  }

  "def flatMap" should "call a function once" in new DBTest {

    emptyDB.flatMap(_ => {
      call += 1
      emptyDB
    }).execute(uri)

    call shouldBe 1
  }
}
