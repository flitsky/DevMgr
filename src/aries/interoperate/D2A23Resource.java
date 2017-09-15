package aries.interoperate;

import java.util.ArrayList;

import aries.interoperate.EntityX.BindField;
import aries.interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class D2A23Resource extends EntityX {
    public D2A23Resource() {
        super();
        properties = new ArrayList<Property>();
        //공백 노드 발생//properties.add(new Property()); //하위 프로퍼티 노드를 추가
    }

    @BindField("rsrcID") public String rsrcID;
    @BindField("rsrcUri") public String uri;
    @BindField("properties") public ArrayList<Property> properties;
}
