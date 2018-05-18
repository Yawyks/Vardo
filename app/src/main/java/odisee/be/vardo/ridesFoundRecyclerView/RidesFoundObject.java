package odisee.be.vardo.ridesFoundRecyclerView;

public class RidesFoundObject {

    private String rideId;
    private String rideDeparture;
    private String rideDestination;
    private String rideDateTime;
    private String rideOwnerId;
    private String userId;

    public RidesFoundObject(String rideId, String rideDeparture, String rideDestination, String rideDateTime, String rideOwnerId, String userId) {

        this.rideId = rideId;
        this.rideDeparture = rideDeparture;
        this.rideDestination = rideDestination;
        this.rideDateTime = rideDateTime;
        this.rideOwnerId = rideOwnerId;
        this.userId = userId;
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

    public String getRideOwnerId() {
        return rideOwnerId;
    }

    public String getUserId() {
        return userId;
    }

}
