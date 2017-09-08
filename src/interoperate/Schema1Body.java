package interoperate;

import java.util.ArrayList;

import org.json.JSONArray;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class Schema1Body extends EntityX {
    public Schema1Body() {
        super();
    }

    @BindField("status") public Integer status;
    @BindField("res_id") public String resourcd_id;
    @BindField("authcode") public String authcode;
    @BindField("uid") public String uid;
    @BindField("accesstoken") public String accesstoken;
    @BindField("refreshtoken") public String refreshtoken;
    @BindField("tokentype") public String tokentype;
    @BindField("expiresin") public String expiresin;

    @BindField("dis_dev") public ArrayList<Schema2DiscoveryDevice> dis_dev;
    @BindField("dis_res") public ArrayList<Schema2DiscoveryResource> dis_res;
    @BindField("properties") public ArrayList<Property> properties;
}
