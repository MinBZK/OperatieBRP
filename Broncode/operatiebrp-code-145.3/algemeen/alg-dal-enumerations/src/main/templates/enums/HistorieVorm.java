/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie voor statische stamtabel ber.historievorm.
 */
public enum HistorieVorm implements Enumeratie {

    /** QUERY:
    SELECT naam 
           || '. ',
            regexp_replace(regexp_replace(naam, '([A-Z])', '_$1'), '^_', ''),
           '('
           || id
           || ', "'
           || naam
           || '", "'
           || oms
           || '")'
    FROM   kern.historievorm
    ORDER BY id
    */    
    
    private static final EnumParser<HistorieVorm> PARSER = new EnumParser<>(HistorieVorm.class);
    private final int id;
    private final String naam;
    private final String omschrijving;

    /**
     * Constructor voor de enumeratie.
     *
     * @param id           de ID van de enum waarde
     * @param naam         naam van de enum waarde
     * @param omschrijving omschrijving van de enum waarde
     */
    HistorieVorm(final int id, final String naam, final String omschrijving) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id id
     * @return historie vorm
     */
    public static HistorieVorm parseId(final Integer id) {
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
     *
     * @return de omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }



    /**
     * Geeft de HistorieVorm per naam.
     *
     * @param naam de naam
     * @return de HistorieVorm
     * @throws IllegalArgumentException als de naam niet als HistorieVorm bestaat
     */
    public static HistorieVorm getByNaam(final String naam) {
        if (naam != null) {
            for (final HistorieVorm historieVorm : HistorieVorm.values()) {
                if (historieVorm.getNaam().equals(naam)) {
                    return historieVorm;
                }
            }
        }
        throw new IllegalArgumentException(String.format("HistorieVorm met naam %s kan niet geparst worden.", naam));
    }
}
