import net.plixo.paper.client.ui.elements.UISpinner;
import net.plixo.paper.client.ui.GUI.GUICanvas;
import net.plixo.paper.client.avs.newVersion.CustomFunction;

public class GUI extends CustomFunction {

        int speed = 20;
        public void link() {
            GUICanvas canvas = new GUICanvas() {
                @Override 
                public void init() {
                    super.init();
                    UISpinner spinner = new UISpinner() {
                            @Override
                            public void onTick() {
                                speed = getNumber();
                            }        
                    };
                    spinner.setDimensions(10,10,80,20);
                    spinner.setNumber(speed);
                    canvas.add(spinner);
                }
            };
            mc.displayGuiScreen(canvas);
        }

        public void out() {
            output(0,speed);
        }

}
