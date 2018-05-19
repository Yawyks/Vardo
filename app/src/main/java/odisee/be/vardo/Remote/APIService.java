package odisee.be.vardo.Remote;

import odisee.be.vardo.Model.MyResponse;
import odisee.be.vardo.Model.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AIzaSyDERNmB92h8FVCrxP_Rq1yRlz-qIbcBd2M"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
