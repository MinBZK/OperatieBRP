/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie voor XSD type Zoekbereik-s.
 */
public enum Zoekbereik implements Enumeratie {
    /**
     * Peilmoment.
     */
    PEILMOMENT(1, "Peilmoment", "Zoekcriteria toepassen op één moment in de tijd"),
    /**
     * Materiele periode.
     */
    MATERIELE_PERIODE(2, "Materiele periode", "Zoekcriteria toepassen op materiële periode");

    private static final EnumParser<Zoekbereik> PARSER = new EnumParser<>(Zoekbereik.class);
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
    Zoekbereik(final int id, final String naam, final String omschrijving) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id id
     * @return Zoekbereik
     **/
    public static Zoekbereik parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Geeft de Zoekbereik per naam.
     *
     * @param naam de naam
     * @return de Zoekbereik
     * @throws IllegalArgumentException als de naam niet als Zoekbereik bestaat
     */
    public static Zoekbereik getByNaam(final String naam) {
        if (naam != null) {
            for (final Zoekbereik zoekbereik : Zoekbereik.values()) {
                if (zoekbereik.getNaam().equals(naam)) {
                    return zoekbereik;
                }
            }
        }
        throw new IllegalArgumentException(String.format("Zoekbereik met naam %s kan niet geparst worden.", naam));
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
}
