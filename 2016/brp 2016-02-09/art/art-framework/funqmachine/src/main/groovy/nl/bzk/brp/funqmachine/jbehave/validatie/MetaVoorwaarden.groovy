package nl.bzk.brp.funqmachine.jbehave.validatie

import nl.bzk.brp.funqmachine.datalezers.YamlDataLezer

/**
 * Abstractie voor alle meta regels/attributen die bij een scenario horen.
 */
class MetaVoorwaarden {
    @Delegate
    private Map<String, Object> regels = [:]

    private static MetaVoorwaarden instance

    private MetaVoorwaarden() {
        regels = new YamlDataLezer().lees('/nl/bzk/brp/funqmachine/jbehave/validatie/story-properties.yml')
    }

    static MetaVoorwaarden instance() {
        if (null == instance) {
            instance = new MetaVoorwaarden()
        }

        return instance
    }

    /**
     * Geeft de valide waardes voor de {@code @status} meta.
     * @return lijst van status waardes
     */
    List<String> valideStatussen() {
        regels.meta.find { it -> it.name == '@status' }.values
    }

    /**
     * Geeft de namen van meta tags die worden ondersteund.
     * @return llijst van meta namen
     */
    List<String> ondersteundeMetaTags() {
        regels.meta.collect { it.name }
    }

    /**
     * Geeft de voorwaardes voor een meta tag.
     *
     * @param metaNaam de naam van de metatag
     * @return de voorwaardes als map
     */
    Map getRegel(String metaNaam) {
        regels.meta.find { it.name == metaNaam } as Map
    }
}
