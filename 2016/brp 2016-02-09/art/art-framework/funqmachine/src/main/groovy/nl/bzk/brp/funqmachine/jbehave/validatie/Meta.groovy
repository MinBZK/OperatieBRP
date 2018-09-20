package nl.bzk.brp.funqmachine.jbehave.validatie

/**
 * Abstractie over een meta annotatie in een .story file.
 */
class Meta {
    String name
    List<String> values

    /**
     * Constructor, splitst de regel in de waardes voor een meta in jbehave.
     *
     * @param regel
     */
    Meta(String regel) {
        if (regel.contains(' ')) {
            this.name = regel.substring(0, regel.indexOf(' '))
            this.values = regel.substring(regel.indexOf(' ') + 1).split(',').collect { it.trim() }
        } else {
            this.name = regel
            this.values = []
        }
    }
}
