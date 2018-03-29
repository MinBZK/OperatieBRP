/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie voor statische stamtabel kern.bijhsituatie.
 */
public enum BijhoudingSituatie implements Enumeratie {

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
     || CASE WHEN id IN (1,2,8) THEN 'true' ELSE 'false' END
     || ', '
     || CASE WHEN id IN (6,7) THEN 'true' ELSE 'false' END
     || ')'
     FROM   kern.bijhsituatie
     ORDER BY id
     */

    private static final EnumParser<BijhoudingSituatie> PARSER = new EnumParser<>(BijhoudingSituatie.class);
    private final int id;
    private final String naam;
    private final String omschrijving;
    private final boolean isVerwerkbaar;
    private final boolean isTeNegerenVoorBijhoudingResultaat;

    /**
     * Constructor voor de enumeratie.
     * @param id de ID van de enum waarde
     * @param naam naam van de enum waarde
     * @param omschrijving omschrijving van de enum waarde
     * @param isVerwerkbaar true als deze bijhoudingsituatie betekent dat een persoon verwerkt mag worden, anders false
     * @param isTeNegerenVoorBijhoudingResultaat true als deze bijhoudingsituatie geen invloed heeft op het {@link BijhoudingResultaat}
     */
    BijhoudingSituatie(final int id, final String naam, final String omschrijving, final boolean isVerwerkbaar,
                       final boolean isTeNegerenVoorBijhoudingResultaat) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.isVerwerkbaar = isVerwerkbaar;
        this.isTeNegerenVoorBijhoudingResultaat = isTeNegerenVoorBijhoudingResultaat;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     * @param id id
     * @return bijhouding situatie
     */
    public static BijhoudingSituatie parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Geef de waarde van id van Enumeratie.
     * @return de waarde van id van Enumeratie
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Geef de waarde van code van Enumeratie.
     * @return de waarde van code van Enumeratie
     * @throws UnsupportedOperationException als de Enumeratie geen code bevat.
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen naam", this.name()));
    }

    /**
     * @return true als deze Enumeratie een code heeft, anders false.
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geeft de naam terug die gebruikt wordt voor de Enumeratie.
     */
    @Override
    public String getNaam() {
        return naam;
    }

    /**
     * Geeft de omschrijving terug.
     * @return de omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van isVerwerkbaar.
     * @return true als deze bijhoudingsituatie betekent dat een persoon verwerkt mag worden, anders false
     */
    public boolean isVerwerkbaar() {
        return isVerwerkbaar;
    }

    /**
     * Geef de waarde van isTeNegerenVoorBijhoudingResultaat.
     * @return true als deze bijhoudingsituatie betekent dat dit geen invloed heeft op het {@link BijhoudingResultaat}, anders false
     */
    public boolean isTeNegerenVoorBijhoudingResultaat() {
        return isTeNegerenVoorBijhoudingResultaat;
    }
}
