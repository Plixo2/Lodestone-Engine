package net.plixo.paper.client.engine.ecs;

import com.google.gson.JsonObject;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.*;
import net.plixo.paper.client.util.Util;

import java.io.File;

public class Resource {

    public Class clazz;
    public String name;
    public Object value;


    public Resource(String name, Class clazz, Object initValue) {
        this.clazz = clazz;
        this.name = name;
        this.value = initValue;
    }

    public void fromString(String str) {
        try {
            if (str.isEmpty()) {
                return;
            }
            if (isFile()) {
                setValue(new File(str));
            } else if (isBoolean()) {
                setValue(Boolean.valueOf(str));
            } else if (isFloat()) {
                setValue(Float.valueOf(str));
            } else if (isInteger()) {
                setValue(Integer.valueOf(str));
            } else if (isString()) {
                setValue(str);
            } else if (isVector()) {
                setValue(Util.getVecFromString(str));
            }
        } catch (Exception e) {
          Util.print(e.getMessage());
          e.printStackTrace();
        }
    }


    public boolean getAsBoolean() {
        if (!hasValue()) {
            return false;
        }
        return (boolean) value;
    }

    public File getAsFile() {
        if (!hasValue()) {
            return null;
        }
        return (File) value;
    }

    public float getAsFloat() {
        if (!hasValue()) {
            return 0;
        }
        return (float) value;
    }

    public int getAsInteger() {
        if (!hasValue()) {
            return 0;
        }
        return (int) value;
    }

    public String getAsString() {
        if (!hasValue()) {
            return "";
        }
        return (String) value;
    }

    public Vector3d getAsVector() {
        if (!hasValue()) {
            return new Vector3d(0, 0, 0);
        }
        return (Vector3d) value;
    }


    public boolean hasValue() {
        return value != null;
    }


    public boolean isBoolean() {
        return clazz == Boolean.class;
    }

    public boolean isFile() {
        return clazz == File.class;
    }

    public boolean isFloat() {
        return clazz == Float.class;
    }

    public boolean isInteger() {
        return clazz == Integer.class;
    }

    public boolean isString() {
        return clazz == String.class;
    }

    public boolean isVector() {
        return clazz == Vector3d.class;
    }


    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("Value", toTxt());
        return obj;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String toTxt() {
        if (!hasValue()) {
            return "";
        }
        if (isFile()) {
            return getAsFile().getPath();
        } else if (isBoolean()) {
            return getAsBoolean() + "";
        } else if (isFloat()) {
            return getAsFloat() + "";
        } else if (isInteger()) {
            return getAsInteger() + "";
        } else if (isString()) {
            return getAsString();
        } else if (isVector()) {

            return Util.getStringFromVector(getAsVector());
        }
        return "";
    }

    public static UIElement getUIElement(Resource res , float x, float y, float width, float height) {
        UIElement element = null;
        if (res.isFile()) {
            UIFileChooser chooser = new UIFileChooser() {
                @Override
                public void onTick() {
                    res.setValue(getFile());
                    super.onTick();
                }
            };
            element = chooser;
            element.setDimensions(x,y,width,height);
            chooser.setFile(res.getAsFile());

        } else if (res.isInteger()) {
            UISpinner spinner = new UISpinner() {
                @Override
                public void onTick() {
                    res.setValue(getNumber());
                    super.onTick();
                }
            };
            element = spinner;
            element.setDimensions(x,y,width,height);
            spinner.setNumber(res.getAsInteger());
        } else if (res.isBoolean()) {
            UIToggleButton toggleButton = new UIToggleButton() {
                @Override
                public void onTick() {
                    res.setValue(getState());
                    super.onTick();
                }
            };
            element = toggleButton;
            element.setDimensions(x,y,width,height);
            toggleButton.setYesNo("True", "False");
            toggleButton.setState(res.getAsBoolean());
        } else if (res.isString()) {
            UITextbox txt = new UITextbox() {
                @Override
                public void onTick() {
                    res.setValue(getText());
                    super.onTick();
                }
            };
            element = txt;
            element.setDimensions(x,y,width,height);
            txt.setText(res.getAsString());
        } else if (res.isFloat()) {
            UIPointNumber number = new UIPointNumber() {
                @Override
                public void onTick() {
                    res.setValue((float)getAsDouble());
                    super.onTick();
                }
            };
            element = number;
            element.setDimensions(x,y,width,height);
            number.setValue(res.getAsFloat());
        } else if (res.isVector()) {
            UIVector vec = new UIVector() {
                @Override
                public void onTick() {
                    res.setValue(getAsVector());
                    super.onTick();
                }
            };
            element = vec;
            element.setDimensions(x,y,width,height);
            vec.setVector(res.getAsVector());
        }
        return element;
    }

    @Override
    public String toString() {
        return "name="+name+ ", class=" + getClass() + ", data=" +toTxt();
    }
}
