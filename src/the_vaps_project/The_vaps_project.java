/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project;


import gephi.PreviewJFrame;
import me.jhenrique.manager.TweetManager;
import me.jhenrique.manager.TwitterCriteria;
import me.jhenrique.model.Tweet;
/*import twitter4j.TwitterFactory;
 import twitter4j.conf.ConfigurationBuilder;
 import twitter4j.*;*/

/**
 *
 * @author pierre
 */
public class The_vaps_project {

    /**
     * @param args the command line arguments
     */
    private static final String USERNAME = "Username: ";
    private static final String RETWEETS = "Retweets: ";
    private static final String TEXT = "Text: ";
    private static final String MENTIONS = "Mentions: ";
    private static final String HASHTAGS = "Hashtags: ";
    private static final String DATE = "Date: ";

    
    public static void main(String[] args)/* throws TwitterException */ {
        // TODO code application logic here
        /*ConfigurationBuilder cb = new ConfigurationBuilder();
        
         cb.setDebugEnabled(true)
         .setOAuthConsumerKey("gVaO166IGfSCun5Esj1sZ5bX3")
         .setOAuthConsumerSecret("KhN1L4pXCG5U6d6tee1IGUnIV1bqxieQJDxE2HOx3AGI5npQRE")
         .setOAuthAccessToken("822018210178469888-4Pr1KRE2rmFvof0O5IUdTlxtkGKamU4")
         .setOAuthAccessTokenSecret("D1thDl7k8v2s1SupU5ZGhK3U7Mphb69fpUW4JuDou5O65");
        
         TwitterFactory tf = new TwitterFactory(cb.build());
         twitter4j.Twitter twitter = tf.getInstance();
        
         List<Status> status = twitter.getHomeTimeline();
         for(Status st : status){
         System.out.println(st.getUser().getName()+"--------"+st.getText());
         System.out.println(st.getCreatedAt());
         }*/

        // The factory instance is re-useable and thread safe.
        /*Twitter twitter2 = tf.getSingleton();
         Query query = new Query("source:twitter4j yusukey");
         QueryResult result = twitter.search(query);
         for (Status statuss : result.getTweets()) {
         System.out.println("@" + statuss.getUser().getScreenName() + ":" + statuss.getText());
         }*/
        TwitterCriteria criteria = null;
        Tweet t = null;
        criteria = TwitterCriteria.create()
                .setMaxTweets(10)
                //.setUntil("2016-11-08")
                .setQuerySearch("#test123456789");
        /*Scribe s = new Scribe();
        s.detruireFichier("tweets.xml");
        s.ouvrir("tweets.xml");
        s.ecrire("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.ecrire("<tweets>\n");
        for (int i = 0; i < TweetManager.getTweets(criteria).size(); i++) {
            t = TweetManager.getTweets(criteria).get(i);
            s.ecrire("\t<tweet>\n");
            s.ecrire("\t\t<text>"+t.getText()+"</text>\n");
            s.ecrire("\t\t<hashtags>"+t.getHashtags()+"</hashtags>\n");
            s.ecrire("\t\t<date>"+t.getDate()+"</date>\n");
            s.ecrire("\t\t<username>"+t.getUsername()+"</username>\n");
            s.ecrire("\t\t<retweets>"+t.getRetweets()+"</retweets>\n");
            s.ecrire("\t\t<favorites>"+t.getFavorites()+"</favorites>\n");
            s.ecrire("\t\t<id>"+t.getId()+"</id>\n");
            s.ecrire("\t\t<geo>"+t.getGeo()+"</geo>\n");
            s.ecrire("\t\t<mentions>"+t.getMentions()+"</mentions>\n");
            s.ecrire("\t\t<permalink>"+t.getPermalink()+"</permalink>\n");
            s.ecrire("\t\t<smileys>"+t.getSmileys()+"</smileys>\n");
            s.ecrire("\t</tweet>\n");
        }
        s.ecrire("</tweets>");
        s.fermer();*/
        
        
        Scribe s = new Scribe();
        s.detruireFichier("tweets.gexf");
        s.ouvrir("tweets.gexf");
        s.ecrire("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.ecrire("<gexf xmlns=\"http://www.gexf.net/1.3\" version=\"1.3\" xmlns:viz=\"http://www.gexf.net/1.3/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.3 http://www.gexf.net/1.3/gexf.xsd\">\n");
        s.ecrire("\t<meta lastmodifieddate=\"2017-02-17\">\n");
        s.ecrire("\t\t<creator>Pirboss</creator>\n");
        s.ecrire("\t\t<description>Test v1</description>\n");
        s.ecrire("\t</meta>\n");
        s.ecrire("\t<graph defaultedgetype=\"directed\" mode=\"static\">\n");
        s.ecrire("\t\t<nodes>\n");
        for (int i = 0; i < TweetManager.getTweets(criteria).size(); i++) {
            t = TweetManager.getTweets(criteria).get(i);
            s.ecrire("\t\t\t<node id=\""+i+"\" label=\""+t.getUsername()+"\" mentions=\""+t.getMentions()+"\"/>\n");
        }
        s.ecrire("\t\t</nodes>\n");
        s.ecrire("\t</graph>\n");
        s.ecrire("</gexf>");
        s.fermer();
        
        PreviewJFrame p = new PreviewJFrame();
        p.script();
    }

    
}
