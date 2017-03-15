/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project.Learning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.openide.util.Exceptions;

/**
 *
 * @author 21102934
 */
public class TweetAnalyzer {

    String mots[];
    int score, nbMotsPositif, nbMotsNegatifs;
    boolean ponctuation, negation;

    public int getScore() {
        return score;
    }

    public int getNbMotsPositif() {
        return nbMotsPositif;
    }

    public int getNbMotsNegatifs() {
        return nbMotsNegatifs;
    }

    public String isPonctuation() {
        if (ponctuation)
            return "TRUE";
        else 
            return "FALSE";
    }

    public String isNegation() {
        if (negation)
            return "TRUE";
        else 
            return "FALSE";
    }

    public TweetAnalyzer(String tweet) {
        mots = tweet.split(" ");
        nbMotsNegatifs = 0;
        nbMotsPositif = 0;
        ponctuation = false;
        negation = false;
        score = calcScore();
        ponctuation(tweet);
    }
    
    private void ponctuation(String tweet) {
        if (tweet.contains("?") || tweet.contains("!") || tweet.contains(",") || tweet.contains("...")) {
            ponctuation = true;
        }
        
    }
   
    private int calcScore() {
        BufferedReader negation_word, negative_word, positive_word = null;
        String ligne;
        int score = 0;

        try {
            negation_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\negation-word.txt"));
            negative_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\negative-words.txt"));
            positive_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\positive-words.txt"));

            int i = 0;
            for (i = 0; i < mots.length; ++i) {
                while ((ligne = negative_word.readLine()) != null) {
                    if (mots[i].equalsIgnoreCase(ligne)) {
                        ++nbMotsNegatifs;
                        --score;
                    }
                }
                while ((ligne = positive_word.readLine()) != null) {
                    if (mots[i].equalsIgnoreCase(ligne)) {
                        ++nbMotsPositif;
                        ++score;
                    }
                }
                while ((ligne = negation_word.readLine()) != null) {
                    
                    if (mots.length > i+1 && mots[i].equalsIgnoreCase(ligne)) {
                        negation = true;
                        positive_word.close();
                        negative_word.close();
                        negative_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\negative-words.txt"));
                        positive_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\positive-words.txt"));
                        while ((ligne = negative_word.readLine()) != null) {
                            if (mots.length > i+1 && mots[i + 1].equalsIgnoreCase(ligne)) {
                                ++nbMotsPositif;
                                ++score;
                            }
                        }
                        while ((ligne = positive_word.readLine()) != null) {
                            if (mots.length > i+1 && mots[i + 1].equalsIgnoreCase(ligne)) {
                                ++nbMotsNegatifs;
                                --score;
                            }
                        }
                        ++i;
                    }
                }
                positive_word.close();
                negation_word.close();
                negative_word.close();
                negation_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\negation-word.txt"));
                negative_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\negative-words.txt"));
                positive_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\positive-words.txt"));
            }
            negation_word.close();
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return score;
    }
}
