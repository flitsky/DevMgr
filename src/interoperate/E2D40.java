package interoperate;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class E2D40 extends EntityX {
    public E2D40()
    {
        super();
    }

    @BindField("type") public String type;
    @BindField("dir") public String direction;
    @BindField("work_code") public String work_code;

    @BindField("body") public E2D41Body body;
}
