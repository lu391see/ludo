package de.htwg.se.ludo.util

import java.security.KeyStore.TrustedCertificateEntry

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ObservableSpec extends AnyWordSpec with Matchers {
  "An Observable" should {
    val observable = new Observable
    val observer = new Observer {
      var updated: Boolean = false
      override def update(): Boolean = {updated = true; updated}
      def isUpdated: Boolean = updated
    }
    "add an Observer" in {
      observable.add(observer)
      observable.subscribers should contain (observer)
    }
    "notify an Observer" in {
      observer.isUpdated should be(false)
      observable.notifyObservers
      observer.isUpdated should be(true)
    }
    "remove an Observer" in {
      observable.remove(observer)
      observable.subscribers should not contain observer
    }

  }

}
