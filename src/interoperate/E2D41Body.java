package interoperate;

import java.util.ArrayList;

import interoperate.EntityX.BindField;
import interoperate.EntityX.EntityX;

/**
 * Created by ryan on 17. 7. 26.
 */

public class E2D41Body extends EntityX {
    public E2D41Body() {
        super();
        trigger_rsrc = new ArrayList<E2D42TriggerResource>();
    }

    @BindField("trigger_rsrc") public ArrayList<E2D42TriggerResource> trigger_rsrc;
}
