package helpers.database.parsers;

/**
 * Created by johankladder on 27-3-17 (18:06)
 */
public interface SQLParser {

    enum TYPE {
        INSERT
    }

    String parse(Object data, TYPE type);
}
