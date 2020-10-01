package database;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class DataBaseConnect {


    /* Connexion à la base de données */
    String url;
    String user;
    String password;
    Connection connexion;


    public DataBaseConnect(String url, String user,String password){
        this.url=url;
        this.user=user;
        this.password=password;


        try {
            connexion = DriverManager.getConnection( url, user, password );
            Statement statement = connexion.createStatement();
            /* Execution de la requête SQL */




            if(!(statement.executeQuery("").getFetchSize()>0)){

                initializeDataBase(statement);
            }

        } catch ( SQLException e ) {
            System.out.println(e.getErrorCode());
        }finally {
            if ( connexion != null )
                try {
                    /* Fermeture de la connexion */
                    connexion.close();
                } catch ( SQLException ignore ) {
                    /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
                }
        }

    }


    public void initializeDataBase(Statement statement){

        /* Création des tables */
        try{
            statement.executeQuery("CREATE TABLE categories (idcategory INT,category VARCHAR , PRIMARY KEY (idcategory)");
            statement.executeQuery("CREATE TABLE words (idword INT,word VARCHAR, idcategory, PRIMARY KEY(idword), FOREIGN KEY(idcategory) REFERENCES categories(idcategory))");
        }catch (SQLException e){
            e.getStackTrace();
        }



        /* Insertion des données*/
        Map<String,List<String>> data = new MyData().getData();
        int idCategory = 0;
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            int idWord =0;
            try {
                statement.executeQuery("INSERT INTO categories (idcategory, category) VALUES "+"("+idCategory+","+entry.getKey()+")"+";");
                for (String word:entry.getValue()
                     ) {
                    statement.executeQuery("INSERT INTO words (idword, word, idcategory) VALUES "+"("+idWord+","+word+","+idCategory+")"+";");
                }
            }catch (SQLException e){
                e.getStackTrace();
            }
            idCategory++;

        }
    }



}
