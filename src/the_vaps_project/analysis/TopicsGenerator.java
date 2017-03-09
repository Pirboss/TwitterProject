/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

    left TODO : lemmatization
 */
package the_vaps_project.analysis;

import cc.mallet.util.*;
import cc.mallet.types.*;
import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.*;
import cc.mallet.topics.*;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import the_vaps_project.Scribe;
import the_vaps_project.analysis.perplexity.PerplexityMeasure;

/**
 *
 * @author skander
 */
public class TopicsGenerator {
    
    private static final String pathCorpus = "src/resources/corpus.txt";
    private static final String pathPhi = "src/resources/corpus.phi";
    private static final String pathTheta = "src/resources/corpus.theta";
    
    private double perplexite = 0;
    
    /**
     * 
     * @param corpus
     * @param nTopics number of topic generated
     * @param nWords number of words per topic (it keeps the most relevant ones).
     * @return 
     */
    public List<Topic> getTopics(Reader corpus, int nTopics, int nWords) {
        List<Topic> output = new ArrayList<>();
        
        Scribe sPhi = new Scribe();
        Scribe sTheta = new Scribe();
        sPhi.detruireFichier(pathPhi);
        sTheta.detruireFichier(pathTheta);
        sPhi.ouvrir(pathPhi);
        sTheta.ouvrir(pathTheta);
        
        // Begin by importing documents from text to feature sequences
        ArrayList<Pipe> pipeList = new ArrayList<>();

        // Pipes: lowercase, tokenize, remove stopwords, map to features
        pipeList.add( new CharSequenceLowercase() );
        pipeList.add( new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")) );
        pipeList.add( new TokenSequenceRemoveStopwords(new File("src/resources/stoplists/en.txt"), "UTF-8", false, false, false) );
        pipeList.add( new TokenSequence2FeatureSequence() );

        InstanceList instances = new InstanceList (new SerialPipes(pipeList));
        
        //Reader fileReader = new InputStreamReader(new FileInputStream(new File(args[0])), "UTF-8");
        //Reader fileReader = new StringReader(src);
        /*
        Reader fileReader;
        try {
            fileReader = new InputStreamReader(new FileInputStream(new File(filepath)), "UTF-8");
            instances.addThruPipe(new CsvIterator (fileReader, Pattern.compile("^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$"), 3, 2, 1)); // data, label, name fields
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TopicsGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TopicsGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        instances.addThruPipe(new CsvIterator (corpus, Pattern.compile("^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$"), 3, 2, 1)); // data, label, name fields
        
        // Create a model with 100 topics, alpha_t = 0.01, beta_w = 0.01
        //  Note that the first parameter is passed as the sum over topics, while
        //  the second is the parameter for a single dimension of the Dirichlet prior.
        int numTopics = nTopics;
        ParallelTopicModel model = new ParallelTopicModel(numTopics, 1.0, 0.01);

        model.addInstances(instances);

        // Use two parallel samplers, which each look at one half the corpus and combine
        //  statistics after every iteration.
        model.setNumThreads(2);

        // Run the model for 50 iterations and stop (this is for testing only, 
        //  for real applications, use 1000 to 2000 iterations)
        model.setNumIterations(50);
        try {
            model.estimate();
        } catch (IOException ex) {
            Logger.getLogger(TopicsGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        // Show the words and topics in the first instance

        // The data alphabet maps word IDs to strings
        Alphabet dataAlphabet = instances.getDataAlphabet();
        
        FeatureSequence tokens = (FeatureSequence) model.getData().get(0).instance.getData();
        LabelSequence topics = model.getData().get(0).topicSequence;
        
        Formatter out = new Formatter(new StringBuilder(), Locale.US);
        for (int position = 0; position < tokens.getLength(); position++) {
            out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), topics.getIndexAtPosition(position));
        }
        //System.out.println(out);
        
        // Estimate the topic distribution of the first instance, 
        // given the current Gibbs state.
        double[] topicDistribution = model.getTopicProbabilities(0);
        
        for (int i=0; i<topicDistribution.length; i++) {
            sTheta.ecrire(topicDistribution[i] + " ");
        }

        // Get an array of sorted sets of word ID/count pairs
        ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();
        
        // Show top nWords words in topics with proportions for the first document
        for (int topic = 0; topic < numTopics; topic++) {
            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();
            
            out = new Formatter(new StringBuilder(), Locale.US);
            out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
            int rank = 0;
            while (iterator.hasNext() && rank < nWords) {
                IDSorter idCountPair = iterator.next();
                out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
                rank++;
            }
        }
        
        /*
        //The same but sorted by topic distribution
        
        System.out.println("\n\n################################\n\n");
        java.util.Arrays.sort(topicDistribution);
        for (int topic = 0; topic < numTopics; topic++) {
            
            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();
            
            out = new Formatter(new StringBuilder(), Locale.US);
            out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
            int rank = 0;
            while (iterator.hasNext() && rank < nWords) {
                IDSorter idCountPair = iterator.next();
                out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
                rank++;
            }
            System.out.println(out);
        }
        */
        
        //generate topics list
        for (int topic = 0; topic < numTopics; topic++) {
            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();
            
            Topic t = new Topic("Topic " + String.valueOf(topic));
            
            double sum = 0;
            
            int rank = 0;
            //calcul de la somme du poid
            while (iterator.hasNext() && rank < nWords) {
                IDSorter idCountPair = iterator.next();
                sum += idCountPair.getWeight();
                rank++;
            }
            
            rank = 0;
            iterator = topicSortedWords.get(topic).iterator();
            while (iterator.hasNext() && rank < nWords) {
                IDSorter idCountPair = iterator.next();
                t.addWord((String) dataAlphabet.lookupObject(idCountPair.getID()));
                
                double wordTopicDistribution = idCountPair.getWeight() / sum;        
                sPhi.ecrire(wordTopicDistribution + " ");
                
                rank++;
            }
            
            if (topic < numTopics-1) {
                sPhi.ecrire("\n");
            }
            
            output.add(t);
        }
        
        sPhi.fermer();
        sTheta.fermer();
        
        perplexite = PerplexityMeasure.perplexity(pathPhi, pathTheta);
        
        
        System.out.println("\nLa perplexité pour "+nTopics+" topics est : " 
                + perplexite + "\n");
        
        return output;
    }
    
    public static void main(String args[]) throws IOException {
        /*
        for (Topic topic : tg.getTopics(CorpusReader.getCorpusLemmatized("src/resources/corpus.txt"), 20, 5)) {
            System.out.println(topic.getName() + "\t" + topic.getWords());
        }
        */
        
        TopicsGenerator tg = new TopicsGenerator();
        /*
        for (Topic topic : tg.getTopics(CorpusReader.getCorpus(pathCorpus), 76, 10)) {
            System.out.println(topic.getName() + "\t" + topic.getWords());
        }
        */
        System.out.println("NbTopic ideal = " + tg.calculNbTopicIdeal());
    }
    
    /**
     * Calcule le nombre idéal de topic par rapport au corpus.
     * Attention : Prend beaucoup de temps à s'exécuter !!!
     * N'utiliser cette méthode qu'une fois par corpus.
     * @return nombre idéal de topic par rapport au corpus.
     */
    public int calculNbTopicIdeal () {
        int nbTopic = 1;
        double currentBest = 0;
        
        boolean isOk;
        
        for (int i=1; i<=100; i++) {
            
            /* Parfois le résultat de Mallet est incompatible avec le code de PerplexityMeasure
            pour le moment j'ai rien de mieux que de relancer Mallet */
            isOk = false;
            while (!isOk) {
                try {
                    getTopics(CorpusReader.getCorpus(pathCorpus), i, 10);
                    isOk = true;
                }catch(Exception e) {}
            }
            
            if (currentBest < perplexite) {
                nbTopic = i;
                currentBest = perplexite;
            }
        }
        
        return nbTopic;
    }
    
}
