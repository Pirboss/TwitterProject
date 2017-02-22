/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project.emojis;

import java.util.Objects;

/**
 *
 * @author VinkHiker
 */
public class Emoji {
    private String code;
    private String description;
    private int polarity;

    public Emoji(String code, String description, int polarity) {
        this.code = code;
        this.description = description;
        this.polarity = polarity;
    }
        public Emoji(String code) {
        this.code = code;
        this.description = "";
        this.polarity = 0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPolarity() {
        return polarity;
    }

    public void setPolarity(int polarity) {
        this.polarity = polarity;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Emoji other = (Emoji) obj;
        return (this.code.equals(other.code));
    }
}
