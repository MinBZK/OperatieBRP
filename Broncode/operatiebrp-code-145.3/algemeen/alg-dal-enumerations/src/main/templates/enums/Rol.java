/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Stamtabel kern.rol.
 */
public enum Rol implements Enumeratie {
    

    /** QUERY:
     SELECT naam
     || '. ',
     naam,
     '('
     || id
     || ', "'
     || naam
     || '", '
     || coalesce(CAST(dataanvgel AS VARCHAR(8)), 'null')
     || ', '
     || coalesce(CAST(dateindegel AS VARCHAR(8)), 'null')
     || ')'
     FROM   kern.rol
     ORDER BY id
    */        

    private static final EnumParser<Rol> PARSER = new EnumParser<>(Rol.class);
    private final int id;
    private final String naam;
    private final Integer datumAanvangGeldigheid;
    private final Integer datumEindeGeldigheid;

    /**
     * Maak een nieuwe rol.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     * @param datumAanvangGeldigheid
     *            datum aanvang geldigheid
     * @param datumEindeGeldigheid
     *            datum einde geldigheid
     */
    Rol(final int id, final String naam, final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid) {
        this.id = id;
        this.naam = naam;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
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

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return rol
     */
    public static Rol parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Rol heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van Rol.
     *
     * @return de waarde van datum aanvang geldigheid van Rol
     */
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van Rol.
     *
     * @return de waarde van datum einde geldigheid van Rol
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }
}
