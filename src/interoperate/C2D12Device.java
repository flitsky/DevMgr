package interoperate;

import java.util.ArrayList;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class C2D12Device extends EntityX {
    public C2D12Device()
    {
        super();
        dev_type = new ArrayList<String>();
    }

    @BindField("dev_id") public String dev_id;
    @BindField("dev_name") public String dev_name;
    @BindField("dev_type") public ArrayList<String> dev_type;
    @BindField("spec_ver") public String spec_ver;
    @BindField("hst_addr") public String host_addr;

    //@BindField("server_ver") public String server_ver;
    //@BindField("plat_id") public String plat_id;
    //@BindField("plat_name") public String plat_name;
}

