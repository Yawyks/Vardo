package odisee.be.vardo.ridesOfferedRecyclerView;

public class RidesOfferedObject {

    private String rideId;
    private String rideDeparture;
    private String rideDestination;

    public RidesOfferedObject(String rideId, String rideDeparture, String rideDestination) {

        this.rideId = rideId;
        this.rideDeparture = rideDeparture;
        this.rideDestination = rideDestination;
    }

    public String getRideId() {

        return rideId;
    }

    public String getDeparture() {
        return rideDeparture;
    }

    public String getRideDestination() {
        return rideDestination;
    }
}
