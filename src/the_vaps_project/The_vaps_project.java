/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project;


import gephi.PreviewJFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import me.jhenrique.manager.TweetManager;
import me.jhenrique.manager.TwitterCriteria;
import me.jhenrique.model.Tweet;
import org.openide.util.Exceptions;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
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
        
        
         /*
        
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
         System.out.println("@" + statuss.getUser().getName() + ":" + statuss.getText());
         }*/
        TwitterCriteria criteria = null;
        Tweet t = null;
        int annee = 2016;
        int mois = 11;
        int jour = 8;
        int nbr = 200;
        Scribe ss = new Scribe();
        ss.detruireFichier("tweetsTXT.txt");
        ss.ouvrir("tweetsTXT.txt");

        Scribe s = new Scribe();
        s.detruireFichier("tweetsXML.xml");
        s.ouvrir("tweetsXML.xml");
        s.ecrire("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.ecrire("<tweets>\n");
        
        for(int k=0; k<400; k++){
            
            
            criteria = TwitterCriteria.create()
                    .setMaxTweets(25)
                    .setUntil(annee+"-"+mois+"-"+jour)
                    .setQuerySearch("#TrumpWins OR #TrumpLandslide OR #TrumpPresident OR #TrumpMovement OR #TrumpNation OR #TrumpTrain OR #MakeAmericaGreatAgain OR #NeverTrump OR #ImWithHer OR #trumpprotest OR #voteclinton OR #votehillary OR #dumptrump");

            
            for (int i = 0; i < TweetManager.getTweets(criteria).size(); i++) {
                System.out.println(k*25+i);
                t = TweetManager.getTweets(criteria).get(i);
                s.ecrire("\t<tweet>\n");
                s.ecrire("\t\t<text>"+t.getText().replace("&", "&amp;")+"</text>\n");
                /*s.ecrire("\t\t<hashtags>"+t.getHashtags()+"</hashtags>\n");
                s.ecrire("\t\t<date>"+t.getDate()+"</date>\n");
                s.ecrire("\t\t<username>"+t.getUsername()+"</username>\n");
                s.ecrire("\t\t<retweets>"+t.getRetweets()+"</retweets>\n");
                s.ecrire("\t\t<favorites>"+t.getFavorites()+"</favorites>\n");
                s.ecrire("\t\t<id>"+t.getId()+"</id>\n");
                s.ecrire("\t\t<geo>"+t.getGeo()+"</geo>\n");
                s.ecrire("\t\t<mentions>"+t.getMentions()+"</mentions>\n");
                s.ecrire("\t\t<permalink>"+t.getPermalink()+"</permalink>\n");
                s.ecrire("\t\t<smileys>"+t.getSmileys()+"</smileys>\n");
                s.ecrire("\t\t<polarite>"+t.getPolarite()+"</polarite>\n");*/
                s.ecrire("\t</tweet>\n");
                ss.ecrire(t.getText());
            }
            jour--;
            if(jour==0){
                mois--;
                if(mois==1){
                    annee--;
                    jour = 30;
                    mois = 12;
                }else if(mois==2){
                    jour=27;
                }else{
                    jour=30;
                }
            }
        }
        s.ecrire("</tweets>");
        s.fermer();
        ss.fermer();
            
        
        
        /* SUPER SITE D'EXEMPLES
        https://github.com/yusuke/twitter4j/tree/master/twitter4j-examples/src/main/java/twitter4j/examples*/
        
        /*PARTIE GRAPHE DES RETWEETS*/
        /*Set<String> nameOfUsers = new HashSet<>();
        for (int i = 0; i < TweetManager.getTweets(criteria).size(); i++) {
            t = TweetManager.getTweets(criteria).get(i);
            System.out.println(t.getUsername());
            nameOfUsers.add(t.getUsername());
        }
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
         cb.setDebugEnabled(true)
         .setOAuthConsumerKey("gVaO166IGfSCun5Esj1sZ5bX3")
         .setOAuthConsumerSecret("KhN1L4pXCG5U6d6tee1IGUnIV1bqxieQJDxE2HOx3AGI5npQRE")
         .setOAuthAccessToken("822018210178469888-4Pr1KRE2rmFvof0O5IUdTlxtkGKamU4")
         .setOAuthAccessTokenSecret("D1thDl7k8v2s1SupU5ZGhK3U7Mphb69fpUW4JuDou5O65");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = tf.getInstance();
        
        
        
        Set<DuoKey> utilisateurs = new HashSet<>();
        Set<DuoKey> liens = new HashSet<>();
        

        try{
            int nbpages = 0;
            long cursor = -1;
            PagableResponseList<User> listeFollowersTemp;
            ArrayList<User> listeFollowers = new ArrayList<User>();
            for(String s: nameOfUsers){
                if(!utilisateurs.contains(new DuoKey(s,"auteurTweet"))){
                    cursor = -1;
                
                    utilisateurs.add(new DuoKey(s,"auteurTweet"));

                    do {
                        TimeUnit.MINUTES.sleep(1);
                        listeFollowersTemp = twitter.getFollowersList(s, cursor);

                        for (User u : listeFollowersTemp) {
                            listeFollowers.add(u);
                        }
                        nbpages++;
                    } while (((cursor = listeFollowersTemp.getNextCursor()) != 0) && nbpages <= 9); //4=100 followers par auteur de tweet recherché
                }
                System.out.println(s+", mes followers sont :");
                for(User u: listeFollowers){
                    utilisateurs.add(new DuoKey(u.getName(), "auteurTweet"));
                    liens.add(new DuoKey(u.getName(), s));
                    System.out.println("\t"+u.getName());
                }
                listeFollowers.clear();
                nbpages = 0;
            }
        } catch (TwitterException | InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        
        Scribe sc = new Scribe();
        sc.detruireFichier("graphFollowers.gexf");
        sc.ouvrir("graphFollowers.gexf");
        sc.ecrire("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sc.ecrire("<gexf xmlns=\"http://www.gexf.net/1.2draft/gexf.xsd\" version=\"1.2\" xmlns:viz=\"http://www.gexf.net/1.1draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.2draft http://www.gexf.net/1.2draft/gexf.xsd\">\n");
        sc.ecrire("\t<meta lastmodifieddate=\"2017-02-17\">\n");
        sc.ecrire("\t\t<creator>Pirboss</creator>\n");
        sc.ecrire("\t\t<description>Test v2</description>\n");
        sc.ecrire("\t</meta>\n");
        sc.ecrire("\t<graph defaultedgetype=\"directed\" mode=\"static\">\n");
        sc.ecrire("\t\t<nodes>\n");
        ArrayList<DuoKey> distinctListOfUsers = new ArrayList(utilisateurs);
        System.out.println(distinctListOfUsers.size());
        for(int i=0; i<distinctListOfUsers.size(); i++){
            sc.ecrire("\t\t\t<node id=\""+distinctListOfUsers.get(i).getX().replace("&", "&amp;")+"\" label=\""+distinctListOfUsers.get(i).getX().replace("&", "&amp;")+"\"/>\n");
        }
        sc.ecrire("\t\t</nodes>\n");
        sc.ecrire("\t\t<edges>\n");
        ArrayList<DuoKey> distinctListOfLinks = new ArrayList(liens);
        System.out.println(distinctListOfLinks.size());
        for(int i=0; i<liens.size(); i++){
            sc.ecrire("\t\t\t<edge id=\""+i+"\" source=\""+distinctListOfLinks.get(i).getX().replace("&", "&amp;")+"\" target=\""+distinctListOfLinks.get(i).getY().replace("&", "&amp;")+"\"/>\n");
        }
        
        sc.ecrire("\t\t</edges>\n");
        sc.ecrire("\t</graph>\n");
        sc.ecrire("</gexf>");
        sc.fermer();
        
        
        PreviewJFrame p = new PreviewJFrame();
        p.script();*/
        
        
        
        
        
        
        
        
        
        /*PARTIE GRAPHE DES MENTIONS*/
        //NE PAS OUBLIE LE COUP DE REMPLACER LES .replace("&", "&amp;") POUR LE XML 
        /*Set<String> users = new HashSet<>();
        HashMap<DuoKey, Integer> map;
        map = new HashMap<DuoKey, Integer>();
        DuoKey duokey;
        for (int i = 0; i < TweetManager.getTweets(criteria).size(); i++) {
            System.out.println(i);
            t = TweetManager.getTweets(criteria).get(i);
            users.add(t.getUsername());
            if(!t.getMentions().isEmpty()){
                for(String target : t.getMentions().split(" ")){
                    target = target.replaceFirst("@", "");
                    users.add(target);
                    duokey = new DuoKey(t.getUsername(), target);
                    if(map.get(duokey) != null){
                        map.put(duokey, map.get(duokey)+1);
                    }else{
                        map.put(duokey, 1);
                    }
                }
            }
        }
        
        Scribe s = new Scribe();
        s.detruireFichier("tweets.gexf");
        s.ouvrir("tweets.gexf");
        s.ecrire("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        s.ecrire("<gexf xmlns=\"http://www.gexf.net/1.2draft/gexf.xsd\" version=\"1.2\" xmlns:viz=\"http://www.gexf.net/1.1draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.2draft http://www.gexf.net/1.2draft/gexf.xsd\">\n");
        s.ecrire("\t<meta lastmodifieddate=\"2017-02-17\">\n");
        s.ecrire("\t\t<creator>Pirboss</creator>\n");
        s.ecrire("\t\t<description>Test v1</description>\n");
        s.ecrire("\t</meta>\n");
        s.ecrire("\t<graph defaultedgetype=\"directed\" mode=\"static\">\n");
        s.ecrire("\t\t<nodes>\n");
        ArrayList distinctList = new ArrayList(users);
        for(int i=0; i<distinctList.size(); i++){
            s.ecrire("\t\t\t<node id=\""+i+"\" label=\""+distinctList.get(i)+"\"/>\n");
        }
        s.ecrire("\t\t</nodes>\n");
        s.ecrire("\t\t<edges>\n");
        Iterator ite = map.entrySet().iterator();
        int jj=0;
        while (ite.hasNext()) {
            Map.Entry pair = (Map.Entry)ite.next();
            s.ecrire("\t\t\t<edge id=\""+(jj++)+"\" source=\""+distinctList.indexOf(((DuoKey)pair.getKey()).getX())+"\" target=\""+distinctList.indexOf(((DuoKey)pair.getKey()).getY())+"\" weight=\""+pair.getValue()+"\"/>\n");
            ite.remove(); // avoids a ConcurrentModificationException
        }
        s.ecrire("\t\t</edges>\n");
        s.ecrire("\t</graph>\n");
        s.ecrire("</gexf>");
        s.fermer();
        
        PreviewJFrame pr = new PreviewJFrame();
        pr.script();*/
                
    }

    
}
