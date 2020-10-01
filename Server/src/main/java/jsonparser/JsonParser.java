package jsonparser;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonParser {


    private final static String FILEPATH = "Server/src/main/resources/words.json";

    public Tuple<String,String> getWords() {
        int MIN = 0;
        String firstword = "",secondword = "";




        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader(FILEPATH)){

            //READ Json
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray words = (JSONArray) jsonObject.get("words");

            //SET max for random generation
            int max =words.size();

            //GENERATE a random number
            int randomNumber = MIN + (int)(Math.random() * ((max - MIN)));

            //PICK the tuple of random words
            JSONObject randomwords = (JSONObject) words.get(randomNumber);
            firstword =randomwords.get("word1").toString();
            secondword =randomwords.get("word2").toString();




        }catch (FileNotFoundException e){
            System.out.println("Error the Json file was not found with the following path : "+FILEPATH);
            e.getStackTrace();
        } catch (IOException e) {
            System.out.println("The file cannot be opened");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Json File cannot be parsed");
            e.printStackTrace();
        }
        return new Tuple<String, String>(firstword,secondword);

    }

}
