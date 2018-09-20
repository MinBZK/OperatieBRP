package nl.bzk.brp.funqmachine.datalezers

import java.nio.charset.Charset
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord

/**
 * Reads data from a CSV file.
 */
class CsvDataLezer implements DataLezer {
    final CSVFormat format = CSVFormat.RFC4180.withHeader(null)

    @Override
    Map<String, Object> lees(final File file) {
        return read(file)
    }

    @Override
    Map<String, Object> lees(String bestandsNaam) {
        throw new UnsupportedOperationException()
    }

    private Map<String, Object> read(final File file) {

        try {
            CSVParser parser = CSVParser.parse(file, Charset.forName('UTF-8'), format)

            return parser.collectEntries([:]) { CSVRecord r ->
                [r.get(0), r.get(1)]
            }
        } catch (IOException e) {
            throw new DataNietValideException("Fout bij lezen van CSV file [$file.canonicalPath]", e)
        }
    }
}
