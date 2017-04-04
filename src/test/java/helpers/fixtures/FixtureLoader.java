package helpers.fixtures;

import functional.register.RegisterApiTest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedHashMap;
import org.yaml.snakeyaml.Yaml;

public class FixtureLoader {

    public FixturesHolder loadFixture(String path) throws FileNotFoundException {
        LinkedHashMap fixturesMap = loadYaml(path);
        return new FixturesHolder(fixturesMap);
    }

    private LinkedHashMap loadYaml(String path) throws FileNotFoundException {
        URL resource = RegisterApiTest.class.getResource(path);
        Yaml yaml = new Yaml();
        return  (LinkedHashMap) yaml.load(new FileInputStream(resource.getFile()));

    }
}
