/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie voor statische stamtabel kern.srtmelding.
 */
public enum SoortMelding implements Enumeratie {
    
    /** QUERY:
    SELECT naam 
    || '. ',
    naam,
    '('
    || id
    || ', "'
    || naam
    || '", "'
    || oms
    || '", '
    || niveau
    || ')'
    FROM kern.srtmelding
    ORDER BY id
    */    

    private static final EnumMetNaamParser<SoortMelding> PARSER = new EnumMetNaamParser<>(SoortMelding.class);
    private final int id;
    private final String naam;
    private final String omschrijving;
    private int meldingNiveau;

    /**
     * Constructor voor de enumeratie.
     *
     * @param id
     *            de ID van de enum waarde
     * @param naam
     *            naam van de enum waarde
     * @param omschrijving
     *            omschrijving van de enum waarde
     * @param meldingNiveau
     *            het niveau van de melding
     */
    SoortMelding(final int id, final String naam, final String omschrijving, final int meldingNiveau) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.meldingNiveau = meldingNiveau;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return soort melding
     */
    public static SoortMelding parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van naam.
     *
     * @param naam
     *            naam
     * @return soort melding
     */
    public static SoortMelding parseNaam(final String naam) {
        return PARSER.parseNaam(naam);
    }

    /**
     * Geef de waarde van id van Enumeratie.
     *
     * @return de waarde van id van Enumeratie
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Geef de waarde van code van Enumeratie.
     *
     * @return de waarde van code van Enumeratie
     * @throws UnsupportedOperationException
     *             als de Enumeratie geen code bevat.
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen code", this.name()));
    }

    /**
     * @return true als deze Enumeratie een code heeft, anders false.
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * @return Geeft de naam terug die gebruikt wordt voor de Enumeratie.
     */
    @Override
    public String getNaam() {
        return naam;
    }

    /**
     * Geeft de omschrijving terug.
     *
     * @return de omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geeft het niveau van de melding terug.
     * @return het niveau
     */
    public int getMeldingNiveau() {
        return meldingNiveau;
    }
}
