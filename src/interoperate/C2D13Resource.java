package interoperate;

import java.util.ArrayList;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class C2D13Resource extends EntityX {
    public C2D13Resource()
    {
        super();
        res_type = new ArrayList<String>();
    }

    @BindField("res_id") public String res_id;
    @BindField("res_type") public ArrayList<String> res_type;
    @BindField("res_uri") public String res_uri;
    @BindField("res_iso") public String res_iso;
    @BindField("res_dev") public String res_dev;
}
