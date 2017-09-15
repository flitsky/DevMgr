package Aries.interoperate;

import java.util.ArrayList;

import Aries.interoperate.EntityX.BindField;
import Aries.interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class Schema2Resource extends EntityX {
    public Schema2Resource()
    {
        super();
        res_type = new ArrayList<String>();
        properties = new ArrayList<Property>();
    }

    @BindField("res_id") public String res_id;
    @BindField("res_type") public ArrayList<String> res_type;
    @BindField("res_uri") public String res_uri;
    @BindField("res_isobserve") public Boolean resIsObservable;
    @BindField("res_dev_id") public String res_dev_id;
    @BindField("properties") public ArrayList<Property> properties;
}
