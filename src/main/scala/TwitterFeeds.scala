import com.typesafe.config.{Config, ConfigFactory}
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Query, Status, TwitterFactory}
import scala.collection.JavaConverters._

class TwitterFeeds {

  val conf: Config = ConfigFactory.load("Properties")

  /*gets Status object of twitter*/
  def getTweets(tweetQuery: String): List[Status] ={
    try {
      val consumerKey = conf.getString("Consumer Key (API Key)")
      val consumerSecretKey = conf.getString("Consumer Secret (API Secret)")
      val accessToken = conf.getString("Access Token")
      val accessTokenSecret = conf.getString("Access Token Secret")

      val configurationBuilder = new ConfigurationBuilder()
      configurationBuilder.setDebugEnabled(true)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecretKey)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret)

      val twitter = new TwitterFactory(configurationBuilder.build()).getInstance()
      val query = new Query(tweetQuery)
      twitter.search(query).getTweets.asScala.toList

    } catch {
      case ex: Exception => List[Status]()
    }
  }

}
