package interoperate;

import java.util.ArrayList;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class DMGwithCC_Get extends EntityX {
    public DMGwithCC_Get()
    {
        super();
        properties = new ArrayList<Property>();
        properties.add(new Property());
    }

    @BindField("response") public String response;
    @BindField("res_id") public String res_id;
    @BindField("properties") public ArrayList<Property> properties;
}
