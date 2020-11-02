import java.rmi.RemoteException;
import java.io.Serializable;

public class RoomInfo implements Serializable {
    private String owner;
    private String capacity;

    public RoomInfo(IRoom room) throws RemoteException {
        owner = room.getOwner();
        capacity = room.getNbPlayers() + "/" + room.getRoomSize();
    }

    public String getOwner() {
        return owner;
    }

    public String getCapacity() {
        return capacity;
    }
}