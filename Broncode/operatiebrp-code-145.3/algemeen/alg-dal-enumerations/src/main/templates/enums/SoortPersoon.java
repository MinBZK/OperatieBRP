/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Het _SoortPersoon van een BRP-persoon.
 */
public enum SoortPersoon implements Enumeratie {
    
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
    || '", '
    || coalesce(CAST(dataanvgel AS VARCHAR(8)), 'null')
    || ', '
    || coalesce(CAST(dateindegel AS VARCHAR(8)), 'null')
    || ')'
    FROM   kern.srtpers
    ORDER BY id
    */    

    private static final EnumParser<SoortPersoon> PARSER = new EnumParser<>(SoortPersoon.class);

    private final int id;
    private final String code;
    private final String naam;
    private final Integer datumAanvangGeldigheid;
    private final Integer datumEindeGeldigheid;

    /**
     * Maak een nieuwe soort persoon.
     *
     * @param id id
     * @param code code
     * @param naam naam
     * @param datumAanvangGeldigheid datumAanvangGeldigheid
     * @param datumEindeGeldigheid datumEindeGeldigheid
     */
    SoortPersoon(final int id, final String code, final String naam, final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid) {
        this.id = id;
        this.code = code;
        this.naam = naam;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id id
     * @return soort persoon
     */
    public static SoortPersoon parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code code
     * @return soort persoon
     */
    public static SoortPersoon parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see Enumeratie#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see Enumeratie#getCode()
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
     * Geef de waarde van datum aanvang geldigheid van SoortPersoon.
     *
     * @return de waarde van datum aanvang geldigheid
     */
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde va datum einde geldigheid van SoortPersoon.
     *
     * @return de waarde van datum einde geldigheid
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }
}
