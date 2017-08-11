package interoperate;
import java.util.ArrayList;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class D2A21Discovery extends EntityX {
    public D2A21Discovery() {
        super();
        devices = new ArrayList<D2A22Device>();
        //공백 노드 발생//devices.add(new D2A22Device()); //하위 디바이스 노드를 추가
    }

    @BindField("status") public String status;
    @BindField("devices") public ArrayList<D2A22Device> devices;
}
