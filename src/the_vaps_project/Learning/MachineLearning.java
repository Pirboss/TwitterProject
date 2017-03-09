/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project.Learning;

import java.util.List;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.io.*;

/**
 *
 * @author 21102934
 */
public class MachineLearning {

    List<Integer> tweet;

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            // cr�ation d'une fabrique de documents
            DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();

            // cr�ation d'un constructeur de documents
            DocumentBuilder constructeur = fabrique.newDocumentBuilder();

            // lecture du contenu d'un fichier XML avec DOM
            File xml = new File(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\tweets.xml");
            Document document = constructeur.parse(xml);

            //traitement du document
            Element racine = document.getDocumentElement();
            System.out.println(racine.getNodeName());
            NodeList maliste = racine.getChildNodes();
            printListe(maliste);

        } catch (ParserConfigurationException pce) {
            System.out.println("Erreur de configuration du parseur DOM");
            System.out.println("lors de l'appel � fabrique.newDocumentBuilder();");
        } catch (SAXException se) {
            System.out.println("Erreur lors du parsing du document");
            System.out.println("lors de l'appel � construteur.parse(xml)");
        } catch (IOException ioe) {
            System.out.println("Erreur d'entr�e/sortie");
            System.out.println("lors de l'appel � construteur.parse(xml)");
        }
    }

    public static void printListe(NodeList liste) {
        for (int i = 0; i < liste.getLength(); i++) {
            if (liste.item(i).getNodeType() == Node.ELEMENT_NODE) {

                if (liste.item(i).getNodeName().equals("text"))
                {
                    TweetAnalyzer ta = new TweetAnalyzer(liste.item(i).getTextContent());
                    
                }

            }
            printListe(liste.item(i).getChildNodes());
        }
    }

}
