/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie van soorten diensten.
 *
 * Om de enumeratie te re-gegereren aan de hand van de database tabel, gebruik het volgende postgreSQL statement: <code>
 select format(E'/** %s. *\/\n%s (%s, "%s"),',
 s.naam, regexp_replace(regexp_replace(upper(s.naam), ' |-', '_', 'g'), '\(|\)', '', 'g'), s.id, s.naam)
 from autaut.srtdienst s
 </code>
 *
 * Gebruik in psql het commando '\a' om de formatting van de output te beperken.
 *
 * Gebruik in psql het commando '\o <bestandsnaam>' om de uitvoer in een bestand op te slaan.
 *
 * Voer daarna bovenstaand SQL statement uit.
 */
public enum SoortDienst implements Enumeratie {

    /** QUERY:
    SELECT naam 
    || '. ',
    naam,
    '('
    || id
    || ', "'
    || naam
    || '")'
    FROM   autaut.srtdienst
    ORDER BY id
    */        

    private static final EnumParser<SoortDienst> PARSER = new EnumParser<>(SoortDienst.class);

    private final int id;
    private final String naam;

    /**
     * Maak een nieuwe soort relatie.
     *
     * @param id id
     * @param naam code
     */
    SoortDienst(final int id, final String naam) {
        this.id = id;
        this.naam = naam;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.enums.Enumeratie#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id id
     * @return soort relatie
     */
    public static SoortDienst parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.enums.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortDienst heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef de waarde van naam van SoortDienst.
     *
     * @return de waarde van naam van SoortDienst
     */
    public String getNaam() {
        return naam;
    }
}
