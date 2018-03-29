/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie voor XSD type Zoekoptie-s.
 */
public enum Zoekoptie implements Enumeratie {
    /**
     * Exact.
     */
    EXACT(1, "Exact", "Exact zoeken"),
    /**
     * Klein.
     */
    KLEIN(2, "Klein", "Zoeken na omzetting in kleine letters zonder diakritische tekens"),
    /**
     * Leeg.
     */
    LEEG(3, "Leeg", "Zoeken met lege waarde"),
    /**
     * Vanaf klein.
     */
    VANAF_KLEIN(4, "Vanaf klein", "Zoeken na omz. in kl. letters zonder diakr. tekens vanaf opg. waarde (wildcard)"),
    /**
     * Vanaf exact.
     */
    VANAF_EXACT(5, "Vanaf exact", "Zoeken vanaf opgegeven waarde (wildcard)");

    private static final EnumParser<Zoekoptie> PARSER = new EnumParser<>(Zoekoptie.class);
    private final int id;
    private final String naam;
    private final String omschrijving;

    /**
     * Constructor voor de enumeratie.
     *
     * @param id de ID van de enum waarde
     * @param naam naam van de enum waarde
     * @param omschrijving omschrijving van de enum waarde
     */
    Zoekoptie(final int id, final String naam, final String omschrijving) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id id
     * @return zoekoptie
     **/
    public static Zoekoptie parseId(final Integer id) {
        return PARSER.parseId(id);
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
     * @throws UnsupportedOperationException als de Enumeratie geen code bevat.
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen naam", name()));
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
     *
     * @return de omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geeft de Zoekoptie per naam.
     *
     * @param naam de naam
     * @return de Zoekoptie
     * @throws IllegalArgumentException als de naam niet als Zoekoptie bestaat
     */
    public static Zoekoptie getByNaam(final String naam) {
        if (naam != null) {
            for (final Zoekoptie zoekoptie : Zoekoptie.values()) {
                if (zoekoptie.getNaam().equals(naam)) {
                    return zoekoptie;
                }
            }
        }
        throw new IllegalArgumentException(String.format("Zoekoptie met naam %s kan niet geparst worden.", naam));
    }
}
