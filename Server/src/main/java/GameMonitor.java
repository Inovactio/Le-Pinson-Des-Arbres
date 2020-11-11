import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import jsonparser.JsonParser;
import jsonparser.Tuple;

public class GameMonitor {

    private Room room;
    private List<IClient> clients;
    private List<String> usernames;
    private int turnTime;
    private int nbWords;
    private int nbRounds;
    private int nbImpostors;
    private int mrWhiteIndex;

    private String words[][];
    private String votes[];
    private String buffer;
    private boolean bufferIsEmpty;

    public GameMonitor(Room room, List<IClient> clients, List<String> usernames, int nbWords, int nbRounds, int nbImpostors, int turnTime) {
        this.room = room;
        this.clients = clients;
        this.usernames = usernames;
        this.turnTime = turnTime;
        this.nbWords = nbWords;
        this.nbRounds = nbRounds;
        this.nbImpostors = nbImpostors;
        words = new String[6][nbRounds];
        votes = new String[6];
        bufferIsEmpty = true;
    }

    public void launchGame() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = classLoader.getResource("words.json").getPath();
        JsonParser jsonParser = new JsonParser(path);
        Tuple<String> words = jsonParser.getRandomWords();
        System.out.println("Words : " + words.getFirst() + " / " + words.getSecond());

        randomizeRoomOrder();

        mrWhiteIndex = new Random().ints(1,clients.size()-1).findFirst().getAsInt();
        Set<Integer> impostersIndex = new HashSet<>();

        for(int i=0;i<nbImpostors;i++){
            int randomNumber = new Random().ints(0,clients.size()-1-impostersIndex.size()-1).findFirst().getAsInt();
            if(randomNumber>=mrWhiteIndex) randomNumber++;
            for (Integer imposterIndex: impostersIndex
                 ) {
                if(randomNumber>=imposterIndex)randomNumber++;
            }
            impostersIndex.add(randomNumber);
        }
        
        try {
            for(int i=0;i<clients.size();i++){
                if(i == mrWhiteIndex){
                    clients.get(i).init("Vous êtes MrWhite",true,usernames);
                    System.out.println(usernames.get(i)+" est initialisé MrWhite");
                }else if (impostersIndex.contains(i)){
                    clients.get(i).init(words.getSecond(), false, usernames);
                    System.out.println(usernames.get(i)+" est initialisé Imposter");
                }else{
                    clients.get(i).init(words.getFirst(), false, usernames);
                    System.out.println(usernames.get(i)+" est initialisé Citoyen");
                }
            }
        } catch(RemoteException e) {
            System.out.println("Init failed.");
            e.printStackTrace();
        }
        
        playGame();
    }

    private void randomizeRoomOrder() {
        long seed = System.nanoTime();
        Collections.shuffle(clients, new Random(seed));
        Collections.shuffle(usernames, new Random(seed));
    }

    private synchronized void playGame() {
        int playerIndex = 0;

        for (int i = 0; i < nbRounds ; i++) {
            for (IClient client:clients) {
                try {
                    client.requestWord();
                    wait(turnTime*1000);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String currentWord;
                if (bufferIsEmpty) {
                    currentWord = "TIMED OUT";
                } else {
                    currentWord = buffer;
                    bufferIsEmpty = true;
                }
                words[playerIndex][i] = currentWord;
                
                for (IClient c : clients) {
                    try {
                        c.giveGameUpdate(currentWord, playerIndex);
                    } catch (RemoteException e) {
                        System.out.println("Game update failed.");
                        e.printStackTrace();
                    }
                }

                playerIndex = (playerIndex+1)%6;
            }
        }

        for (IClient client:clients) {
            try {
                client.requestVote();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        // TODO Récupérer les votes des joueurs et donner des points en fonction de la réussite
        

        // TODO Récupérer le mot que MrWhite pense avoir deviné et le comparer au mot des Citoyen

        room.close();
        
    }

    public synchronized void sendWord(String word) {
        buffer = word;
        bufferIsEmpty = false;
        notify();
    }

    public synchronized void sendVote(String player) {

    }

    public synchronized void sendGuess(IClient client, String word) throws RemoteException {
        
    }
}