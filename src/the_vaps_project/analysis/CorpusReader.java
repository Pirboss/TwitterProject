/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project.analysis;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author skander
 */
public class CorpusReader {
    
    public static Reader getCorpus(String source) {
        Reader corpus = null;
        try {
            corpus = new InputStreamReader(new FileInputStream(new File(source)), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CorpusReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CorpusReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return corpus;     
    }
    
    public static Reader getCorpusLemmatized(String source) {
        String cmd = "python src/lemmatizer/preprocessing.py lemmatized";
        
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (IOException ex) {
            Logger.getLogger(CorpusReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(CorpusReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getCorpus("src/resources/corpusLem.txt");
    }
    
    /*
    Nettoie le corpus pour préparer l'analyse.
    Pas la peine de le lancer plus d'une fois par corpus.
    */
    public static void cleanCorpus(String source) {
        Path path = Paths.get(source);
        Charset charset = StandardCharsets.UTF_8;

        try {
            String texte = new String(Files.readAllBytes(path), charset);

            texte = texte.replaceAll("https?://\\S+\\s?", "");
            texte = texte.replaceAll("http?://\\S+\\s?", "");
            texte = texte.replaceAll("pic.twitter\\S+\\s?", "");
            texte = texte.replaceAll("@", " @");
            texte = texte.replaceAll("#", " #");
            texte = texte.replaceAll("'s", "");
            texte = texte.replaceAll("can't", "can not");
            texte = texte.replaceAll("n't", " not");
            texte = texte.replaceAll("makeamericagreatagain", "makeamericagreatagain ");
            texte = texte.replaceAll("MakeAmericaGreatAgain", "MakeAmericaGreatAgain ");
            texte = texte.replaceAll("  ", " ");

            Files.write(path, texte.getBytes(charset));
        } catch (IOException ex) {
            Logger.getLogger(CorpusReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main (String args[]) {
        cleanCorpus("src/resources/corpus.txt");
    }
}
