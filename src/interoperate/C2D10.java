package interoperate;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class C2D10 extends EntityX {
    public C2D10() {
        super();
    }

    @BindField("type") public String type;
    @BindField("dir") public String direction;
    @BindField("work_code") public String work_code;

    @BindField("body") public C2D11Discovery body;
}
