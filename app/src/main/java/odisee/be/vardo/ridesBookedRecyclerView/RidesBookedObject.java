package odisee.be.vardo.ridesBookedRecyclerView;

public class RidesBookedObject {

    private String rideId;
    private String rideDeparture;
    private String rideDestination;
    private String rideDateTime;

    public RidesBookedObject(String rideId, String rideDeparture, String rideDestination, String rideDateTime) {

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
