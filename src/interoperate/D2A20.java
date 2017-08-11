package interoperate;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class D2A20 extends EntityX {
    public D2A20() {
        super();
    }

    @BindField("type") public String type;
    @BindField("dir") public String direction;
    @BindField("work_code") public String work_code;

    @BindField("body") public D2A21Discovery body;
}
