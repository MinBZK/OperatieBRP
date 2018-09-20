package nl.bzk.brp.soapui.utils

import nl.bzk.brp.soapui.handlers.FileHandler

import java.util.regex.Pattern

/**
 * Vervangt placeholders in een tekst, met inhoud van een bestand.
 */
class FileImportUtil {

    private static def supportedRegExpFiles = [
            ~/\[.*\.sql\]/,
            ~/\[.*\.xml\]/
    ]

    /**
     * Importeert bestanden waar nodig in een tekst.
     *
     * @param path het pad waar bestanden gezocht kunnen worden
     * @param text de tekst waarin placeholders vervangen worden
     * @return de tekst met vervangen placeholders
     */
    public static String importFiles(FileHandler fileHandler, String text) {
        def result = text
        supportedRegExpFiles.each { Pattern p ->
            result = result.replaceAll(p) { Object[] it ->
                leesBestand(fileHandler, it[0])
            }
        }

        result
    }

    /**
     * Zoekt een bestand in gegeven pad, en leest de inhoud ervan.
     * Als het geen ondersteund type is, wordt de bestandsnaam teruggegeven.
     *
     * @param fileHandler pad waarin gezocht moet worden
     * @param filename bestandsnaam om te zoeken
     * @return de inhoud van het bestand als het bestaat, of de bestandsnaam als het geen ondersteund type is
     * @throws FileNotFoundException als het bestand wel ondersteund is, maar niet bestaat
     */
    private static String leesBestand(FileHandler fileHandler, String filename) throws Exception {
        if (filename.contains('.sql') || filename.contains('.xml')) {
            filename = filename.replaceAll(~/\[/, '').replaceAll(~/\]/, '')

            File importFile = fileHandler.geefVerwachtBestand(filename)
            if (importFile?.exists()) {
                return importFile.text
            } else {
                throw new FileNotFoundException("[$filename] niet gevonden")
            }
        }

        return filename
    }
}
