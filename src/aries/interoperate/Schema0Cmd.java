package aries.interoperate;

import aries.interoperate.EntityX.BindField;
import aries.interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class Schema0Cmd extends EntityX {
    public Schema0Cmd() {
        super();
        //body = new ArrayList<Schema1Body>();
    }

    @BindField("type") public String type;
    @BindField("dir") public String direction;
    @BindField("work_code") public String work_code;

    @BindField("body") public Schema1Body body;
    //@BindField("body") public ArrayList<Schema1Body> body;
}
