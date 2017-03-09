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
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.openide.util.Exceptions;

/**
 *
 * @author 21102934
 */
public class TweetAnalyzer {

    String mots[];
    int score;
    List vector;

    public TweetAnalyzer(String tweet) {
        mots = tweet.split(" ");
        score = calcScore();
        vector = new ArrayList();
        if (score == 0) {
            System.out.println(tweet + " -> neutre");
        }
        else if (score>0) {
            System.out.println(tweet + " -> positif");
        }
        else {
            System.out.println(tweet + " -> negatif");
        }
    }

//    private boolean contient (String mots[], String mot)
//    {
//        for (String mot1 : mots)
//            if (mot.equals(mot1))
//                return true;
//        return false;
//    }
//    
    private int calcScore() {
        BufferedReader negation_word, negative_word, positive_word = null;
        String ligne;
        int score = 0;

        Comparator comparator = String.CASE_INSENSITIVE_ORDER;
        int comparaison = comparator.compare("Hello World", "hello world");

        try {
            negation_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\negation-word.txt"));
            negative_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\negative-words.txt"));
            positive_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\positive-words.txt"));

            for (int i = 0; i < mots.length; ++i) {
//                System.out.println(mots[i] + " " + score);
                while ((ligne = negative_word.readLine()) != null) {
                    if (mots[i].equalsIgnoreCase(ligne)) {
                        --score;
                    }
                }
                while ((ligne = positive_word.readLine()) != null) {
                    if (mots[i].equalsIgnoreCase(ligne)) {
                        ++score;
                    }
                }
                while ((ligne = negation_word.readLine()) != null) {
                    if (mots[i].equalsIgnoreCase(ligne)) {
                        positive_word.close();
                        negative_word.close();
                        negative_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\negative-words.txt"));
                        positive_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\positive-words.txt"));
                        while ((ligne = negative_word.readLine()) != null) {
                            if (mots[i+1].equalsIgnoreCase(ligne)) {
                                ++score;
                            }
                        }
                        while ((ligne = positive_word.readLine()) != null) {
                            if (mots[i+1].equalsIgnoreCase(ligne)) {
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

//            while ((ligne = negation_word.readLine()) != null) {
//                
//                
//                
//                
//            }
            negation_word.close();
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        return score;
    }

}
