package nl.bzk.brp.funqmachine.datalezers

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.error.YAMLException

/**
 * Reads data from a YAML file.
 */
class YamlDataLezer implements DataLezer {
    Yaml yaml = new Yaml()

    @Override
    Map<String, Object> lees(final File file) {
        try {
            return read(new FileReader(file))
        } catch (YAMLException e) {
            throw new DataNietValideException("Fout bij lezen van YAML file [$file.canonicalPath]", e)
        }
    }

    @Override
    Map<String, Object> lees(String bestandsNaam) {
        try {
            def stream = getClass().getResourceAsStream(bestandsNaam)
            return read(new InputStreamReader(stream))
        } catch (YAMLException e) {
            throw new DataNietValideException("Fout bij lezen van YAML file [$bestandsNaam]", e)
        }
    }

    private Map<String, Object> read(Reader reader) {
        def resultaat = [:]

        def ymlData = (Map<String, Object>) yaml.load(reader)
        if (ymlData != null) {
            resultaat.putAll(ymlData)
        }

        resultaat
    }
}
