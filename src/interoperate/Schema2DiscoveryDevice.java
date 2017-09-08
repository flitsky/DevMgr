package interoperate;

import java.util.ArrayList;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class Schema2DiscoveryDevice extends EntityX {
    public Schema2DiscoveryDevice()
    {
        super();
        dev_type = new ArrayList<String>();
    }

    @BindField("dev_id") public String dev_id;
    @BindField("dev_name") public String dev_name;
    @BindField("dev_type") public ArrayList<String> dev_type;
    @BindField("spec_ver") public String spec_ver;
    @BindField("host_addr") public String host_addr;

    //@BindField("server_ver") public String server_ver;
    //@BindField("plat_id") public String plat_id;
    //@BindField("plat_name") public String plat_name;
}

