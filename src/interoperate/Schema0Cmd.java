package interoperate;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class Schema0Cmd extends EntityX {
    public Schema0Cmd() {
        super();
    }

    @BindField("type") public String type;
    @BindField("dir") public String direction;
    @BindField("work_code") public String work_code;

    //@BindField("body") public ArrayList<Schema1Body> body;
    @BindField("body") public Schema1Body body;
}
