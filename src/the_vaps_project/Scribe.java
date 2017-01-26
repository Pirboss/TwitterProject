/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_vaps_project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author pierre
 */
public class Scribe {
    public void ecrire(String nomFic, String texte)
		{
			//on va chercher le chemin et le nom du fichier et on me tout ca dans un String
			String adressedufichier = System.getProperty("user.dir") + "/"+ nomFic;
		
			//on met try si jamais il y a une exception
			try
			{
				/**
				 * BufferedWriter a besoin d un FileWriter, 
				 * les 2 vont ensemble, on donne comme argument le nom du fichier
				 * true signifie qu on ajoute dans le fichier (append), on ne marque pas par dessus 
				 
				 */
				FileWriter fw = new FileWriter(adressedufichier, true);
				
				// le BufferedWriter output auquel on donne comme argument le FileWriter fw cree juste au dessus
				BufferedWriter output = new BufferedWriter(fw);
				
				//on marque dans le fichier ou plutot dans le BufferedWriter qui sert comme un tampon(stream)
				output.write(texte);
				//on peut utiliser plusieurs fois methode write
				
				output.flush();
				//ensuite flush envoie dans le fichier, ne pas oublier cette methode pour le BufferedWriter
				
				output.close();
				//et on le ferme
				System.out.println("fichier créé");
			}
			catch(IOException ioe){
				System.out.print("Erreur : ");
				ioe.printStackTrace();
				}

		}
}
