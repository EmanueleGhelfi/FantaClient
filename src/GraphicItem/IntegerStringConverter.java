package GraphicItem;

import javafx.util.StringConverter;

/**
 * Created by Emanuele on 04/01/2016.
 */
public class IntegerStringConverter extends StringConverter<Number> {

    public IntegerStringConverter() {
    }

    @Override
    public String toString(Number object) {
        if(object.intValue()!=object.doubleValue())
            return "";
        return ""+(object.intValue());
    }

    @Override
    public Number fromString(String string) {
        Number val = Double.parseDouble(string);
        return val.intValue();
    }
}
