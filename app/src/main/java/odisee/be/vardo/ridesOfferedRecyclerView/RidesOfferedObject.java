package odisee.be.vardo.ridesOfferedRecyclerView;

public class RidesOfferedObject {

    private String rideId;
    private String rideDeparture;

    public RidesOfferedObject(String rideId, String rideDeparture) {

        this.rideId = rideId;
        this.rideDeparture = rideDeparture;
    }

    public String getRideId() {

        return rideId;
    }

    public String getDeparture() {
        return rideDeparture;
    }
}
