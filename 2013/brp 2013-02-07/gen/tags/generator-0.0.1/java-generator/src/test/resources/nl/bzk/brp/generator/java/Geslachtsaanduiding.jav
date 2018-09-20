package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De mogelijke aanduiding van het geslacht van een Persoon..
 */
public enum Geslachtsaanduiding {

    /** DUMMY. */
    DUMMY("", "", ""),
    /** Man. */
    MAN("M", "Man", ""),
    /** Vrouw. */
    VROUW("V", "Vrouw", ""),
    /** Onbekend. */
    ONBEKEND("O", "Onbekend", "");

    /** code. */
    private String code;
    /** naam. */
    private String naam;
    /** omschrijving. */
    private String omschrijving;

    /**
     * Constructor.
     *
     * @param code de code
     * @param naam de naam
     * @param omschrijving de omschrijving
     *
     */
    private Geslachtsaanduiding(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * De (functionele) code waarmee het Geslacht kan worden aangeduid.
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * De naam waarmee het Geslacht kan worden aangeduid.
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De omschrijving waarmee het Geslacht kan worden omschreven.
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
