package interoperate;

import java.util.ArrayList;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class Schema2Device extends EntityX {
    public Schema2Device()
    {
        super();
        dev_type = new ArrayList<String>();
        //resources = new ArrayList<D2A23Resource>();
        //공백 노드 발생//resourceURIs.add(new D2A23Resource()); //하위 리소스 노드를 추가
    }

    @BindField("dev_id") public String dev_id;
    @BindField("dev_name") public String dev_name;
    @BindField("dev_type") public ArrayList<String> dev_type;
    @BindField("spec_ver") public String spec_ver;
    @BindField("host_addr") public String host_addr;

    //@BindField("server_ver") public String server_ver;
    //@BindField("plat_id") public String plat_id;
    //@BindField("plat_name") public String plat_name;
    @BindField("deviceID") public String deviceID;
    @BindField("deviceName") public String deviceName;
    @BindField("resources") public ArrayList<D2A23Resource> resources;
}

