/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project.analysis;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author skander
 */
public class Topic {
    private String name;
    private List<String> words;
    

    public Topic(String name) {
        this.name = name;
        words = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
    
    public void addWord(String word) {
        this.words.add(word);
    }
    
    public void clearWords() {
        this.words.clear();
    }
    
    
}
