package nl.bzk.brp.funqmachine.datalezers

import org.junit.Test

class DataProcessorTest {
    final DataProcessor dataProcessor = new DataProcessor();

    @Test
    void testSamenvoegenMaps() {
        final File standaard = new File(getClass().getResource('standaard.yml').toURI())
        final File specifiek = new File(getClass()
            .getResource('specifiek.yml').toURI())

        Map<String, Object> standaardMap = [:]
        standaardMap = dataProcessor.process(standaardMap, standaard);
        standaardMap = dataProcessor.process(standaardMap, specifiek);

        assert standaardMap.toString() == '[stuurgegevens:[zendende_partij:[code:654321, naam:Testpartij], zendende_systeem:BRPOVERSCHREVEN, referentienummer:00000000-0000-0000-0000-000000000001]]'
    }
}
