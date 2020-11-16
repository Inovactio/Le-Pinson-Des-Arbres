import java.rmi.RemoteException;
import java.sql.SQLOutput;
import java.util.*;

import jsonparser.JsonParser;
import jsonparser.Tuple;

public class GameMonitor {

    private Room room;
    private List<IClient> clients;
    private List<String> usernames;
    private int turnTime;
    private int nbRounds;
    private int nbImpostors;
    private int mrWhiteIndex;
    private Set<Integer> impostersIndex;
    private String mrWhiteGuess;
    private int imposteurIndex;
    private Tuple<String> wordsImposterCitizens;
    private String words[][];
    private Vote votes[];
    private int nbVoted;
    private int points[];
    private String buffer;
    private boolean bufferIsEmpty;
    private int nbVoteAgainstImposteur;

    public GameMonitor(Room room, List<IClient> clients, List<String> usernames, int nbRounds, int nbImpostors, int turnTime) {
        this.room = room;
        this.clients = clients;
        this.usernames = usernames;
        this.turnTime = turnTime;
        this.nbRounds = nbRounds;
        this.nbImpostors = nbImpostors;
        this.mrWhiteGuess="";
        this.imposteurIndex=-1;
        this.nbVoteAgainstImposteur=0;
        words = new String[6][nbRounds];
        votes = new Vote[6];
        nbVoted = 0;
        points = new int[6];
        for (int i = 0; i < points.length ; i++) {
            points[i] = 0;
        }
        bufferIsEmpty = true;
    }

    public void launchGame() throws RemoteException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = classLoader.getResource("words.json").getPath();
        JsonParser jsonParser = new JsonParser(path);
        wordsImposterCitizens = jsonParser.getRandomWords();
        System.out.println("Words : " + wordsImposterCitizens.getFirst() + " / " + wordsImposterCitizens.getSecond());

        randomizeRoomOrder();

        mrWhiteIndex = new Random().ints(1,clients.size()-1).findFirst().getAsInt();
        impostersIndex = new HashSet<>();

        for(int i=0;i<nbImpostors;i++){
            int randomNumber = new Random().ints(0,clients.size()-1-impostersIndex.size()-1).findFirst().getAsInt();
            if(randomNumber>=mrWhiteIndex) randomNumber++;
            for (Integer imposterIndex: impostersIndex
                 ) {
                if(randomNumber>=imposterIndex)randomNumber++;
            }
            imposteurIndex=randomNumber;
            impostersIndex.add(randomNumber);
        }
        
        try {
            for(int i=0;i<clients.size();i++){
                if(i == mrWhiteIndex){
                    clients.get(i).init("Vous êtes MrWhite",true,usernames);
                    System.out.println(usernames.get(i)+" est initialisé MrWhite");
                }else if (impostersIndex.contains(i)){
                    clients.get(i).init(wordsImposterCitizens.getSecond(), false, usernames);
                    System.out.println(usernames.get(i)+" est initialisé Imposter");
                }else{
                    clients.get(i).init(wordsImposterCitizens.getFirst(), false, usernames);
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

    private synchronized void playGame() throws RemoteException{
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
        System.out.println("fin de la phase de jeu");
        for (IClient client:clients) {
            try {
                    client.switchToVoteScene();
                    client.requestVote();
            } catch (RemoteException e) {
                    e.printStackTrace();
            }
        }
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        verifyVote();
        updateEndOfGameResult();
        room.close();
        
    }

    private void verifyVote() {
        for (int i = 0; i < votes.length; i++) {
            for (int j = 0; j < usernames.size(); j++) {
                String user = usernames.get(j);
                if(user.equals(votes[i].getImposteur())){
                    if(impostersIndex.contains(j)){
                        givePoint(i);
                        nbVoteAgainstImposteur++;
                        try {
                            clients.get(i).setFoundImposteur(true);
                        }
                        catch(RemoteException e){
                            e.printStackTrace();
                        }
                    }
                }else if(user.equals(votes[i].getMrWhite()) && i!=mrWhiteIndex){
                    if(mrWhiteIndex == j )givePoint(i);
                }else if(j == mrWhiteIndex){
                    if(wordsImposterCitizens.getFirst().equals(mrWhiteGuess)) givePoint(j);
                }
            }
        }
        System.out.println("votes vérifies");
    }

    private void givePoint(int userIndex){
        points[userIndex]++;
    }

    private void givePoint(int userIndex,int nbPoints){
        points[userIndex]+=nbPoints;
    }

    public synchronized void sendWord(String word) {
        buffer = word;
        bufferIsEmpty = false;
        notify();
    }

    public synchronized void sendVote(String username,String imposteur, String mrWhite, boolean isMrWhite) {
        Vote vote;
        if(isMrWhite){
            vote = new Vote(imposteur);
            mrWhiteGuess=mrWhite;
        }
        else {
            vote = new Vote(imposteur,mrWhite);
        }
        for(String user:usernames) {
            if(user.equals(username)){
                votes[usernames.indexOf(user)] = vote ;
                nbVoted++;
            }
        }
        if(nbVoted>=clients.size())notify();

    }

    public synchronized void sendGuess(IClient client, String word) throws RemoteException {
        
    }

    public void updateEndOfGameResult() throws RemoteException{
        System.out.println("Debut de l'envoie des resultats de fin de partie");

        for (IClient client:clients) {
            String stringpoint = " Vous avez "+points[clients.indexOf(client)]+" points.";
            String result;
            if(client.getIsMrWhite()){
                if(wordsImposterCitizens.getFirst().equals(mrWhiteGuess)){
                    result = " devine le mot ! Victoire !";
                }
                else {
                    result = " perdu ! ce n'etait pas le bon mot.";
                }
            }
            else if(getImpostersUsername().contains(client.getUsername())){
                if(nbVoteAgainstImposteur>2){
                    result = "perdu ! Plus de la moitie des joueurs vous ont trouve.";
                }
                else {
                    result = "gagne ! moins de la moitie des joueurs vous ont trouve.";
                }
            }
            else{
                if(client.getFoundImposteur()){
                    result = "gagne ! vous avez trouve l'imposteur !";
                }
                else {
                    result = "perdu ! vous n'avez pas trouve l'imposteur !";
                }
            }
            result+=stringpoint;
            String imposters = getImpostersString();
            client.updateEndOfGameInfo(imposters,wordsImposterCitizens.getSecond(),usernames.get(mrWhiteIndex),wordsImposterCitizens.getFirst(),result);
        }
        System.out.println("Fin de l'envoie des resultats de fin de partie.");

    }

    private Set<String> getImpostersUsername(){
        Set<String> result = new HashSet<String>();
        for (int i:impostersIndex
             ) {
            result.add(usernames.get(i));
        }
        return result;
    }

    private String getImpostersString() {
        String imposters="";
        for (int i:impostersIndex
             ) {
            imposters+=usernames.get(i)+" ";
        }
        if(nbImpostors>1){
            imposters+="est l'imposteur.";
        }else{
            imposters+="sont les imposteurs.";
        }
        return imposters;
    }


    private class Vote{
        private String imposteur;
        private String mrWhite;
        private boolean mrWhiteVote;

        public Vote(String imposteur, String mrWhite){
            this.imposteur = imposteur;
            this.mrWhite = mrWhite;
            mrWhiteVote = true;
        }

        public Vote(String imposteur){
            this.imposteur = imposteur;
            mrWhiteVote = false;
        }

        public String getImposteur() {
            return imposteur;
        }

        public String getMrWhite() {
            return mrWhite;
        }

        public boolean isMrWhiteVote() {
            return mrWhiteVote;
        }
    }
}