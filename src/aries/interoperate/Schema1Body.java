package aries.interoperate;

import java.util.ArrayList;

import aries.interoperate.EntityX.BindField;
import aries.interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class Schema1Body extends EntityX {
    public Schema1Body() {
        super();
    }

    @BindField("status") public Integer status;
    @BindField("provider") public String provider; //sign up req
    @BindField("auth_code") public String authcode; //sign up req
    @BindField("uid") public String uid; //sign up resp; sign in req 
    @BindField("accesstoken") public String accesstoken; //sign up resp; sign in req
    @BindField("expiresin") public Integer expiresin; //sign up resp; sign in req

    @BindField("dev_id") public String device_id; //discovery device resp; discovery resource req
    @BindField("res_id") public String resourcd_id; //discovery resource resp; get/post/observe req

    // between CC and DevMgr
    @BindField("devices") public ArrayList<Schema2Device> devices;
    @BindField("resources") public ArrayList<Schema2Resource> resources;
    @BindField("properties") public ArrayList<Property> properties;
    
    // between DevMgr and App

    // between DevMgr and SvcMgr
}
