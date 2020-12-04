package fr.univcotedazur.polytech.si4.fsm.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface WriteAndDecodeFile {
	
	static void addHashMapToFile(HashMap<String, List<Double>> hmap) {
		try
        {
           FileOutputStream fos = new FileOutputStream("CustomerDiscountInformation.txt");
           ObjectOutputStream oos = new ObjectOutputStream(fos);
           oos.writeObject(hmap);
           oos.close();
           fos.close();
        }
        catch(IOException ioe)
         {
               ioe.printStackTrace();
         }
	}
	
	static void createFile(String name, double finalBeveragePrice) {
		HashMap<String, List<Double>> hmap = new HashMap<String, List<Double>>();
        List<Double> newListInfoCard = new ArrayList<>();
        newListInfoCard.add(finalBeveragePrice);
		hmap.put(name, newListInfoCard);
		addHashMapToFile(hmap);
	}
	
	@SuppressWarnings("unchecked")
	static HashMap<String, List<Double>> retrieveHashInfoCard() {
		HashMap<String, List<Double>> hmap = new HashMap<String, List<Double>>();
		try {
			FileInputStream fis = new FileInputStream("CustomerDiscountInformation.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			hmap = (HashMap<String, List<Double>>) ois.readObject();
	        ois.close();
	        fis.close();
		} 
		catch(IOException ioe)
	      {
	         ioe.printStackTrace();
	      }
	      catch(ClassNotFoundException c)
	      {
	         c.printStackTrace();
	      }
		return hmap;
	}
	
	static void addHashInfoCard(String name, double finalBeveragePrice) {
		File newFile = new File("CustomerDiscountInformation.txt");
		if (newFile.length() == 0) {
			createFile(name, finalBeveragePrice);
			return;
		}
		HashMap<String, List<Double>> hmap = retrieveHashInfoCard();
		if (hmap == null) {
			return;
		}
		List<Double> newListHashInfo;
		if (hmap.get(name) == null) {
			newListHashInfo = new ArrayList<>(); 
			newListHashInfo.add(finalBeveragePrice);
			hmap.put(name,newListHashInfo);
		}
		else {
			newListHashInfo = hmap.get(name);
			newListHashInfo.add(finalBeveragePrice);
			hmap.put(name,newListHashInfo);
		}
		
		addHashMapToFile(hmap);
   }

}
