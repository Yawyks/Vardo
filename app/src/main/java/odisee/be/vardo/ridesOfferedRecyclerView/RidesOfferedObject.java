package odisee.be.vardo.ridesOfferedRecyclerView;

public class RidesOfferedObject {

    private String rideDeparture;
    private String rideDestination;
    private String rideDateTime;

    public RidesOfferedObject(String rideDeparture, String rideDestination, String rideDateTime) {

        this.rideDeparture = rideDeparture;
        this.rideDestination = rideDestination;
        this.rideDateTime = rideDateTime;
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
