package helpers;

import org.apache.commons.lang3.StringUtils;
import spark.Request;
import utils.DataFieldJSONBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by johankladder on 12-3-17 (19:03)
 */
public abstract class ParamsHandler {

    private Request request;

    public ParamsHandler(Request request) {
        this.request = request;
    }

    private HashMap<String, Boolean> createParamsKeyArray(ParamsHandler handler) throws CreationException {
        HashMap<String, Boolean> paramsKeys = new HashMap<>();
        for (Field field : handler.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ParamField.class)) {
                ParamField anotation = field.getAnnotation(ParamField.class);
                try {
                    paramsKeys.put((String) field.get(handler), anotation.required());
                } catch (IllegalAccessException e) {
                    throw new CreationException();
                }
            }
        }
        return paramsKeys;
    }

    private HashMap<String, Object> constructParamMap(HashMap<String, Boolean> keys) throws CreationException {
        HashMap<String, Object> paramsMap = new HashMap<>();

        keys.forEach((key, required) -> {
            String value = null;
            if(required && !request.queryParams().contains(key)) {
                try {
                    throw new CreationException();
                } catch (CreationException e) {
                    e.printStackTrace();
                }
            }
            String tempValue = request.queryParams(key);

            if(StringUtils.isNotEmpty(tempValue))
                value = tempValue;
            paramsMap.put(key, value);
        });

        return paramsMap;
    }

    public String createJsonFromParamsField(HashMap<String, Object> paramsMap) throws CreationException {
        String dataField = getDataFieldAnotationValue();
        DataFieldJSONBuilder jsonBuilder = new DataFieldJSONBuilder(dataField);
        paramsMap.forEach(jsonBuilder::appendDataField);
        return jsonBuilder.getJsonString();
    }

    private String getDataFieldAnotationValue() {
        for(Field field : this.getClass().getFields()) {
            DataField dataField = field.getAnnotation(DataField.class);
            if(dataField != null)
                return dataField.value();
        }
        return null;
    }

    private HashMap<String, Object> createParamsMap(ParamsHandler handler) throws CreationException {
        HashMap<String, Boolean> paramsKeyArray = createParamsKeyArray(handler);
        return constructParamMap(paramsKeyArray);
    }

    /**
     * Tries to publish the result of the implemented request of this class. The method will firstly create an array
     * containing all the keys prior to its child-class. After this, it will create an parameter map according to the
     * given keys. When every thing went well, this method calls the abstract method publish().
     *
     * @param handler The handler that needs to be user to perform a publish on.
     * @throws CreationException When given query keys are not given or when the custom validation failed.
     */
    protected void tryPublish(ParamsHandler handler) throws CreationException {
        publish(createParamsMap(handler));
    }

    /**
     * Abstract method that can be overridden and is called at the last point of the try publish method. Handler classes
     * can therefore implement their own validation for requests.
     *
     * @param paramsMap An HashMap containing query keys with their given value.
     * @throws CreationException When validation fails
     */
    protected abstract void publish(HashMap<String, Object> paramsMap) throws CreationException;

}
