/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project.analysis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author admin
 */
public class TweetsPerTopic {
    private Document doc;
    Element rootElement;
    
    public TweetsPerTopic() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("topics");
            doc.appendChild(rootElement);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Ajoute un topic.
     * Ne fait rien si un topic du même nom est déjà défini.
     * @param topicName
    */
    public void addTopic(String topicName) {
        NodeList topics = rootElement.getElementsByTagName("topic");
        Boolean isAlreadyCreated = false;
        
        for(int i=0; i<topics.getLength(); i++){
            isAlreadyCreated = isAlreadyCreated || topics.item(i).getAttributes().getNamedItem("name").getNodeValue().equals(topicName);
        }
        
        if (!isAlreadyCreated) {
            Element topic = doc.createElement("topic");
            topic.setAttribute("name", topicName);
            rootElement.appendChild(topic);
        }
    }
    
    /**
     * Ajoute un tweet à un topic qu'on a déjà ajouté au préalable.
     * @param topicName
     * @param tweetId 
     */
    public void addTweet(String topicName, String tweetId) {
        if (!tweetId.equals("")) {
            Element tweet = doc.createElement("tweet");
            NodeList topics = rootElement.getElementsByTagName("topic");

            for(int i=0; i<topics.getLength(); i++){
                if (topics.item(i).getAttributes().getNamedItem("name").getNodeValue().equals(topicName)) {
                    tweet.setAttribute("id", tweetId);
                    topics.item(i).appendChild(tweet);
                }
            }
        }
    }
    
    /**
     * Crée le fichier tweetsPerTopic.xml
     * construit à partir des appels de addTopic et addTweet précédent l'appel de cette méthode.
     */
    public void createFile() {
        try {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream("tweetsPerTopic.xml"));
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Exceptions.printStackTrace(ex);
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
}
