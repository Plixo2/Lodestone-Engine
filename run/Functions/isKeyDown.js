import net.plixo.paper.client.util.KeyboardUtil;
import net.plixo.paper.client.avs.newVersion.CustomFunction;


public class isKeyDown extends CustomFunction {

    public void out() {
        output(0, KeyboardUtil.isKeyDown(input(0,0).intValue()));
    }

}
