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
        devices = new ArrayList<C2D12Device>();
        devices.add(new C2D12Device());
        resource = new ArrayList<C2D13Resource>();
        resource.add(new C2D13Resource());
    }

    @BindField("response") public String response;
    @BindField("source") public String source;
    @BindField("size") public String size;

    @BindField("dev") public ArrayList<C2D12Device> devices;
    @BindField("res") public ArrayList<C2D13Resource> resource;
}
