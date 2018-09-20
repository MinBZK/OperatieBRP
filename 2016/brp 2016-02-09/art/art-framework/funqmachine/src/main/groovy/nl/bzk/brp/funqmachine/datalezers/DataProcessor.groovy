package nl.bzk.brp.funqmachine.datalezers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Processor die weet hoe data wordt ingelezen voor een type bestand,
 * en hoe deze data in een een {@link Map} wordt geplaatst.
 */
@Component
class DataProcessor {
    static final Logger logger = LoggerFactory.getLogger(DataProcessor.class)

    /**
     * Leest data uit een bestand en voegt deze in de opgegeven map.
     *
     * @param map Map met bestaande waardes (of lege map) waarin de data wordt geplaatst
     * @param file Het bestand dat wordt gelezen
     * @param kolom (optioneel) de kolom van een excelbestand dat moet worden gelezen
     * @return map met ingevoegde waardes
     */
    Map<String, Object> process(Map map, File file, String kolom) {
        logger.debug "Lezen van data file $file.canonicalPath"

        DataLezer reader
        def extensie = file.name.substring(file.name.lastIndexOf('.'), file.name.size())
        switch (extensie) {
            case '.csv':
                reader = new CsvDataLezer()
                break
            case '.yml':
                reader = new YamlDataLezer()
                break
            case '.xls':
                reader = new ExcelDataLezer(kolom)
                break
            default:
                throw new IllegalArgumentException("Kan bestand $file.name niet lezen, extensie $extensie wordt niet ondersteund.")

        }
        def nieuweMap = reader.lees(file)
        samenvoegenMaps(map, nieuweMap)
    }

    /**
     * Leest data uit een bestand en voegt deze in de opgegeven map.
     *
     * @param map Map met bestaande waardes (of lege map) waarin de data wordt geplaatst
     * @param file Het bestand dat wordt gelezen
     * @return map met ingevoegde waardes
     */
    Map<String, Object> process(Map map, File file) {
        process(map, file, null)
    }

    /*
     * Voeg twee maps samen door middel van het overschrijven van de originele
     * map door een andere map met overschrijvende waarden.
     *
     * @param origineleMap map met default waarden
     * @param nieuweMap map met overschrijvende waarden
     */
    private Map samenvoegenMaps(Map origineleMap, Map nieuweMap) {
        // Bij laden van eerste file: de lege template
        if (origineleMap.isEmpty()) {
            origineleMap.putAll(nieuweMap);
        }

        for (Object key : nieuweMap.keySet()) {
            if (nieuweMap.get(key) instanceof Map && origineleMap.get(key) instanceof Map) {
                Map origineelKind = (Map) origineleMap.get(key);
                Map nieuwKind = (Map) nieuweMap.get(key);
                origineleMap.put(key, samenvoegenMaps(origineelKind, nieuwKind));
            } else {
                origineleMap.put(key, nieuweMap.get(key));
            }
        }
        return origineleMap;
    }
}
