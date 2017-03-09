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

        try {
            negation_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\negation-word.txt"));
            negative_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\negation-word.txt"));
            positive_word = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\the_vaps_project\\Learning\\negation-word.txt"));

            for (String mot : this.mots) {
//                System.out.println(mot);
                while ((ligne = negative_word.readLine()) != null) 
                    if (mot.equals(ligne)) 
                        System.out.println(mot);
            };
            

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

        return 0;
    }

}
