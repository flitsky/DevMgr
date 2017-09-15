package Aries.interoperate;

import java.util.ArrayList;

import Aries.interoperate.EntityX.BindField;
import Aries.interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class Property extends EntityX {
    public Property() {
        super();
        propertyValue = new ArrayList<Object>();
    }

    @BindField("prop_name") public String propertyName;
    @BindField("prop_type") public String propertyValueType;
    @BindField("prop_value") public ArrayList<Object> propertyValue;
    @BindField("read_only") public Boolean readOnly;
}
