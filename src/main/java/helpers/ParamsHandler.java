package helpers;

import spark.Request;

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

    private ArrayList<String> createParamsKeyArray(ParamsHandler handler) throws CreationException {
        ArrayList<String> paramsKeys = new ArrayList<>();
        for (Field field : handler.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ParamField.class)) {
                try {
                    paramsKeys.add((String) field.get(handler));
                } catch (IllegalAccessException e) {
                    throw new CreationException();
                }
            }
        }
        return paramsKeys;
    }

    private HashMap<String, String> constructParamMap(ArrayList<String> keys) throws CreationException {
        HashMap<String, String> paramsMap = new HashMap<>();
        for (String param : keys) {
            if (request.queryParams().contains(param)) {
                String value = request.queryParams(param);
                paramsMap.put(param, value);
            } else {
                throw new CreationException();
            }
        }
        return paramsMap;
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
        ArrayList<String> paramsKeyArray = createParamsKeyArray(handler);
        HashMap<String, String> paramMap = constructParamMap(paramsKeyArray);
        publish(paramMap);
    }

    /**
     * Abstract method that can be overridden and is called at the last point of the try publish method. Handler classes
     * can therefore implement their own validation for requests.
     *
     * @param paramsMap An HashMap containing query keys with their given value.
     * @throws CreationException When validation fails
     */
    protected abstract void publish(HashMap<String, String> paramsMap) throws CreationException;

}
