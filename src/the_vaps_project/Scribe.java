/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author pierre
 */
public class Scribe {

    private FileWriter fw;
    private BufferedWriter output;

    public void ouvrir(String nomFic) {
        String adressedufichier = System.getProperty("user.dir") + "/" + nomFic;
        try {
            /*true signifie qu on ajoute dans le fichier (append), on ne marque pas par dessus*/
            fw = new FileWriter(adressedufichier, true);
            output = new BufferedWriter(fw);

        } catch (IOException ioe) {
            System.out.print("Erreur : ");
            ioe.printStackTrace();
        }
    }
    
    public void detruireFichier(String fileName){
        String adressedufichier = System.getProperty("user.dir") + "/" + fileName;
        try{
            File file = new File(adressedufichier);
            if(file.delete()){
                System.out.println(file.getName() + " supprimé");
            }else{
                System.out.println("Echec de la suppression de "+file.getName());
            }
    	}catch(Exception e){
            e.printStackTrace();
    	}
    }

    public void fermer() {
        try {
            output.close();
            //et on le ferme
            System.out.println("fichier créé");
        } catch (IOException ioe) {
            System.out.print("Erreur : ");
            ioe.printStackTrace();
        }
    }

    public void ecrire(String texte) {
        try {
            //on marque dans le fichier ou plutot dans le BufferedWriter qui sert comme un tampon(stream)
            output.write(texte);
            //on peut utiliser plusieurs fois methode write
            output.flush();
            //ensuite flush envoie dans le fichier, ne pas oublier cette methode pour le BufferedWriter
        } catch (IOException ioe) {
            System.out.print("Erreur : ");
            ioe.printStackTrace();
        }
    }
}
