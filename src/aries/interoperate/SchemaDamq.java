package aries.interoperate;

import aries.interoperate.EntityX.BindField;
import aries.interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class SchemaDamq extends EntityX {
    public SchemaDamq() {
        super();
    }

    @BindField("destination") public String destination;
    @BindField("uuid") public String uuid;
    @BindField("msgbody") public Schema0Header msgbody;
}
