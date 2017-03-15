/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project;

/**
 *
 * @author pierre
 */
public class DuoKey {

    private final String x;
    private final String y;

    public DuoKey(String x, String y) {
        this.x = x;
        this.y = y;
    }
    
    public String getX(){
        return this.x;
    }
    
    public String getY(){
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DuoKey)) return false;
        DuoKey key = (DuoKey) o;
        return x.equals(key.x) && y.equals(key.y);
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }
    
    public String toString(){
        return "("+x+";"+y+")";
    }

}
