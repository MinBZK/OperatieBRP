/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeration voor {@link AanduidingOuder}.
 */
public enum AanduidingOuder implements Enumeratie {
    
    /** QUERY:
    SELECT naam 
           || '. ',
           naam,
           '('
           || id
           || ')'
    FROM   verconv.lo3srtaandouder
    ORDER BY id
    */    

    private static final EnumParser<AanduidingOuder> PARSER = new EnumParser<>(AanduidingOuder.class);

    private final int id;

    /**
     * constructor.
     *
     * @param ouderNr 1 or 2
     **/
    AanduidingOuder(final int ouderNr) {
        id = ouderNr;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id id
     * @return lo3 berichten bron
     */
    public static AanduidingOuder parseId(final Integer id) {
        return PARSER.parseId(id);
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
        throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen code", this.name()));
    }

    @Override
    public boolean heeftCode() {
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see Enumeratie#getNaam()
     */
    @Override
    public String getNaam() {
        throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen naam", this.name()));
    }
}
