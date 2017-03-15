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
import the_vaps_project.Scribe;

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
            DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
            DocumentBuilder constructeur = fabrique.newDocumentBuilder();
            File xml = new File(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\tweets.xml");
            Document document = constructeur.parse(xml);
            Element racine = document.getDocumentElement();
            NodeList maliste = racine.getChildNodes();
            Scribe s = new Scribe();
            s.detruireFichier("src\\the_vaps_project\\Learning\\tweet.arff");
            s.ouvrir("src\\the_vaps_project\\Learning\\tweet.arff");
            s.ecrire("@RELATION tweet\n\n"
                    + "@ATTRIBUTE motPositif Numeric\n"
                    + "@ATTRIBUTE motNegatif Numeric\n"
                    + "@ATTRIBUTE Negation { TRUE, FALSE }   \n"
                    + "@ATTRIBUTE Ponctuation { TRUE, FALSE }  \n"
                    + "@ATTRIBUTE class {Positif, Negatif, Neutre}\n"
                    + "\n"
                    + "@DATA\n\n");
            printListe(s, maliste);
            s.fermer();
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

    public static void printListe(Scribe s, NodeList liste) {

        for (int i = 0; i < liste.getLength(); i++) {
            if (liste.item(i).getNodeType() == Node.ELEMENT_NODE) {

                if (liste.item(i).getNodeName().equals("text")) {
                    TweetAnalyzer ta = new TweetAnalyzer(liste.item(i).getTextContent());
                    String classe;
                    if (ta.getScore() == 0) {
                        classe = "Neutre";
                    } else if (ta.getScore() > 0) {
                        classe = "Positif";
                    } else {
                        classe = "Negatif";
                    }
                    s.ecrire(ta.getNbMotsPositif() + "," + ta.getNbMotsNegatifs() + "," + ta.isNegation() + "," + ta.isPonctuation() + "," + classe + "\n");
                }
            }
            printListe(s, liste.item(i).getChildNodes());
        }
    }
}
