package interoperate;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

import java.util.ArrayList;

/**
 * Created by ryan on 17. 7. 26.
 */

public class C2D11Discovery extends EntityX {
    public C2D11Discovery() {
        super();
        devices = new ArrayList<Schema2DiscoveryDevice>();
        devices.add(new Schema2DiscoveryDevice());
        resource = new ArrayList<Schema2DiscoveryResource>();
        resource.add(new Schema2DiscoveryResource());
    }

    @BindField("response") public String response;
    @BindField("source") public String source;
    @BindField("size") public String size;

    @BindField("dev") public ArrayList<Schema2DiscoveryDevice> devices;
    @BindField("res") public ArrayList<Schema2DiscoveryResource> resource;
}
