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

    @BindField("org") public String org;
    @BindField("dst") public String dst;
    @BindField("date") public String date;
    @BindField("msgid") public String msgid;
    @BindField("msgtype") public String msgtype;
    @BindField("work_code") public String work_code;

    @BindField("body") public Schema1Body body;
    //@BindField("body") public ArrayList<Schema1Body> body;
}
