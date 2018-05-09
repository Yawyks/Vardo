package odisee.be.vardo.ridesOfferedRecyclerView;

public class RidesOfferedObject {

    private String rideId;
    private String rideDeparture;
    private String rideDestination;
    private String rideDateTime;

    public RidesOfferedObject(String rideId, String rideDeparture, String rideDestination, String rideDateTime) {

        this.rideId = rideId;
        this.rideDeparture = rideDeparture;
        this.rideDestination = rideDestination;
        this.rideDateTime = rideDateTime;
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

    public String getRideDateTime() {

        return rideDateTime;
    }
}
