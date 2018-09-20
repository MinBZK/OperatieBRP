package nl.bzk.brp.funqmachine.datalezers

/**
 * Reads data from a source into a map.
 */
public interface DataLezer {

    /**
     * Read from a file.
     *
     * @param file file to lees from
     * @return data map
     */
    Map<String, Object> lees(File file)

    /**
     * Read from a file.
     *
     * @param bestandsNaam het pad naar het bestand
     * @return data map
     */
    Map<String, Object> lees(String bestandsNaam)
}
