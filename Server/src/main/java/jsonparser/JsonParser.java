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

    private String filepath;

    public JsonParser(String fp){
        filepath = fp;
    }

    /**
     * get random words from the Json file
     * @return a random couple of word
     */
    public Tuple<String> getRandomWords() {

        String firstword = "",secondword = "";


        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader(filepath)){

            //READ Json
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray words = (JSONArray) jsonObject.get("words");
            int randomNumber = getRandomNumber(words);

            //PICK the tuple of random words
            JSONObject randomwords = (JSONObject) words.get(randomNumber);
            firstword =randomwords.get("word1").toString();
            secondword =randomwords.get("word2").toString();


        }catch (FileNotFoundException e){
            System.out.println("Error the Json file was not found with the following path : "+filepath);
            e.getStackTrace();
        } catch (IOException e) {
            System.out.println("The file cannot be opened");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Json File cannot be parsed");
            e.printStackTrace();
        }
        return new Tuple<String>(firstword,secondword);

    }

    private int getRandomNumber(JSONArray words) {
        int MIN = 0;
        int max =words.size();

        return MIN + (int)(Math.random() * ((max - MIN)));
    }

}
