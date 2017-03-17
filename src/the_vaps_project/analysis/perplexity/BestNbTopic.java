/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project.analysis.perplexity;

import the_vaps_project.analysis.TopicsGenerator;

/**
 *
 * @author skander
 */
public class BestNbTopic {
    /**
     * Affiche le meilleur nombre de topic pour le corpus.
     */
    public static void main (String args[]) {
        TopicsGenerator tg = new TopicsGenerator();
        System.out.println("NbTopic ideal = " + tg.calculNbTopicIdeal());
    }
}
