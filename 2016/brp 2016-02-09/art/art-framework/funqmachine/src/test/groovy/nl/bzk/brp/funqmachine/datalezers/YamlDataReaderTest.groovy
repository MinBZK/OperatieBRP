package nl.bzk.brp.funqmachine.datalezers

import org.junit.Test

class YamlDataReaderTest {
    DataLezer yamlReader = new YamlDataLezer()

    @Test
    void kanYamlLezen() {
        File f = new File(getClass().getResource('test.yml').toURI())

        def map = yamlReader.lees(f)
        assert map.size() == 2
        assert map.get('stuurgegevens') != null
    }

    @Test
    void kanLegeYamlLezen() {
        File f = new File(getClass().getResource('test-leeg.yml').toURI())

        def map = yamlReader.lees(f)
        assert map.isEmpty()
    }
}
