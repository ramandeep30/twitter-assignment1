import java.util.Date

import twitter4j.Query

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


case class MyTweets(tweetText: String, userName: String, date: Date)

class TwitterOperation {

  val twitterFeeds = new TwitterFeeds

  /*gets list of tweets for a hashtag*/
  def getFeeds(hashTag: String): Future[List[MyTweets]] = {
    Future {
      try {
        val tweets = twitterFeeds.getTweets(hashTag)
        val allTweets = tweets.map {
          tweet =>
            MyTweets(tweet.getText, tweet.getUser.getScreenName, tweet.getCreatedAt)
        }
        allTweets.sortBy(_.date)

      } catch {
        case ex: Exception => List[MyTweets]()
      }
    }
  }


  /*number of tweets for a hashtag search*/
  def getNumberOfTweets(tweetQuery: String): Future[Int] = {
    Future{
      twitterFeeds.getTweets(tweetQuery).size
    }
  }

  /*returns average likes and retweets per tweet*/
  def averageLikesAndRetweets(tweetQuery: String): Future[(Int, Int)] = {
    Future {
      try{
        val listOfTweets = twitterFeeds.getTweets(tweetQuery)
        val listOfRetweets = listOfTweets.map(x => x.getRetweetCount).sum
        val listOfLikes = listOfTweets.map(x => x.getFavoriteCount).sum
        val totalTweets = listOfTweets.size
        (listOfLikes / totalTweets, listOfRetweets / totalTweets)
      }
      catch {
        case ex: Exception => (0,0)
      }
    }
  }
}
