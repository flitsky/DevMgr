package interoperate;

import java.util.ArrayList;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class D2A22Device extends EntityX {
    public D2A22Device() {
        super();
        resources = new ArrayList<D2A23Resource>();
        //공백 노드 발생//resourceURIs.add(new D2A23Resource()); //하위 리소스 노드를 추가
    }

    @BindField("deviceID") public String deviceID;
    @BindField("deviceName") public String deviceName;
    @BindField("resources") public ArrayList<D2A23Resource> resources;
}

