/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project.analysis;

import java.io.*;
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
}
