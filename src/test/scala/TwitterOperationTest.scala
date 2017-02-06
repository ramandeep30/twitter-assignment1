import org.scalatest.FlatSpec

import scala.concurrent.Await
import scala.concurrent.duration._

class TwitterOperationTest extends FlatSpec{

  val twitterOperation = new TwitterOperation

  it should "return empty list for getTweets for empty input" in{
    assert(Await.result(twitterOperation.getFeeds(""),10.second).isEmpty)
  }

  it should "return non empty list for getTweets for empty input" in{
    assert(Await.result(twitterOperation.getFeeds("#scala"),10.second).nonEmpty)
  }

  it should "return zero for number of tweets for empty input" in{
    assert(Await.result(twitterOperation.getNumberOfTweets(""),10.second) == 0)
  }

  it should "return non zero for number of tweets for non empty input" in{
    assert(Await.result(twitterOperation.getNumberOfTweets("#scala"),10.second) != 0)
  }

  it should "return zero for avg retweets and favourites" in{
    assert(Await.result(twitterOperation.averageLikesAndRetweets(""),10.second) == (0,0))
  }

  it should "return non zero for avg retweets and favourites" in{
    assert(Await.result(twitterOperation.averageLikesAndRetweets("#scala"),10.second) != (0,0))
  }

}
