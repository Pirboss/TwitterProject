/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project.analysis;

import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author admin
 */
public class TweetsReader {
    
    private static TweetsPerTopic tpt = new TweetsPerTopic();
    
    /**
     * Détermine si un tweet correspond à un thème.
     * @param text le texte du tweet
     * @param topicWords la liste des mots du topic
     * @return True si le texte match le topic
     */
    public static Boolean topicMatch(String text, List<String> topicWords) {
        Boolean res = false;
        for (String word : topicWords) {
            res = res || (text.contains(word));
        }
        return res;
    }
    
    public static void main (String args[]) {
        
        try {            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();            
            DefaultHandler handler = new DefaultHandler() {
                boolean bTweet = false;
                boolean bId = false;
                boolean bText = false;
                String id = "";
                String text = "";
                List<Topic> topics = new TopicsGenerator().calculTopicsIdeal();
                        
                public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
                    if(qName.equalsIgnoreCase("tweet")) {
                        bTweet = true;
                    }
                    if(qName.equalsIgnoreCase("id")) {
                        bId = true;
                        id = "";
                    }
                    if(qName.equalsIgnoreCase("text")) {
                        bText = true;
                        text = "";
                    }
                }
                
                public void characters(char ch[], int start, int length) throws SAXException {
                    if (bId) {
                        id = new String(ch, start, length);
                    }
                    if (bText) {
                        text = new String(ch, start, length);
                    }
                }
                
                public void endElement(String uri, String localName,String qName) throws SAXException {
                    if (bTweet) {
                        bTweet = false;
                        
                        // comparaison avec les thèmes trouvés
                        for(Topic t : topics) {
                            if (topicMatch(text, t.getWords())) {
                                //associer l'id au thème
                                tpt.addTopic(t.getName());
                                tpt.addTweet(t.getName(), id);
                            }
                        }
                    }
                    if (bId) {
                        bId = false;
                    }
                    if (bText) {
                        bText = false;
                    }
                }

            };
            
            saxParser.parse("tweets.xml", handler);
            tpt.createFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
