package interoperate;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class E2D42TriggerResource extends EntityX {
    public E2D42TriggerResource()
    {
        super();
    }

    @BindField("service_id") public String service_id;
    @BindField("shortened_resource_name") public String shortened_resource_name;
    @BindField("trigger_status") public Integer trigger_status;
}

