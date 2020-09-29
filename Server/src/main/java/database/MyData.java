package database;

import java.util.*;

public class MyData {


    Map<String,List<String>> data = new HashMap<String, List<String>>();



    public MyData(){
        List<String> wordsAnimal = new ArrayList<String>();
        wordsAnimal.add("vache");
        wordsAnimal.add("cochon");
        wordsAnimal.add("poule");
        wordsAnimal.add("cheval");
        wordsAnimal.add("chat");
        wordsAnimal.add("chien");
        data.put("Animal",wordsAnimal);

        List<String> wordsLieu = new ArrayList<String>();
        wordsLieu.add("Plage");
        wordsLieu.add("Piscine");
        wordsLieu.add("Espace");

        List<String> wordsMobilier = new ArrayList<String>();
        wordsMobilier.add("Lave-vaisselle");
        wordsMobilier.add("Frigo");
        wordsMobilier.add("Télé");
        wordsMobilier.add("Canapé");
    }

    public Map<String, List<String>> getData() {
        return data;
    }
}
