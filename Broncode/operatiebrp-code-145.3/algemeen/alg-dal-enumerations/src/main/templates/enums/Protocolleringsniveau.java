/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie van protocolleringsniveaus.
 */
public enum Protocolleringsniveau implements Enumeratie {

    /** QUERY:
     SELECT naam
     || '. ',
     naam,
     '('
     || id
     || ', "'
     || code
     || '", "'
     || naam
     || '", "'
     || oms
     || '", '
     || coalesce(CAST(dataanvgel AS VARCHAR(8)), 'null')
     || ', '
     || coalesce(CAST(dateindegel AS VARCHAR(8)), 'null')
     || ')'
     FROM   autaut.protocolleringsniveau
     ORDER BY id
     */

    private static final EnumParser<Protocolleringsniveau> PARSER = new EnumParser<>(Protocolleringsniveau.class);

    private final int id;
    private final String code;
    private final String naam;
    private final String omschrijving;
    private final Integer datumAanvangGeldigheid;
    private final Integer datumEindeGeldigheid;

    /**
     * Maak een nieuw Protocolleringsniveau object.
     * @param id id
     * @param code code
     * @param naam naam
     * @param omschrijving omschrijving
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     */
    Protocolleringsniveau(final int id, final String code, final String naam, final String omschrijving, final Integer datumAanvangGeldigheid,
                          final Integer datumEindeGeldigheid) {
        this.id = id;
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
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
     * @param id id
     * @return functie adres
     */
    public static Protocolleringsniveau parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     * @param code code
     * @return functie adres
     */
    public static Protocolleringsniveau parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.enums.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van omschrijving van Protocolleringsniveau.
     * @return de waarde van omschrijving van Protocolleringsniveau
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van Protocolleringsniveau.
     * @return de waarde van datum aanvang geldigheid van Protocolleringsniveau
     */
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van Protocolleringsniveau.
     * @return de waarde van datum einde geldigheid van Protocolleringsniveau
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }
}
