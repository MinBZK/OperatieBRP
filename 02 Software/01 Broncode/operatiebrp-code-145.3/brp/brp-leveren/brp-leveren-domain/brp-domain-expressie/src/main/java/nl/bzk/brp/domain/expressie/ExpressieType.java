/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Bevat alle mogelijke typen van expressies.
 */
public enum ExpressieType {
    /**
     * Onbekend type.
     */
    ONBEKEND_TYPE("OnbekendType", true),
    /**
     * Null type.
     */
    NULL("Null", true),
    /**
     * Numeriek type (integer).
     */
    GETAL("Getal", true),
    /**
     * Stringtype.
     */
    STRING("String"),
    /**
     * Boolean type.
     */
    BOOLEAN("Boolean"),
    /**
     * Datumtype.
     */
    DATUM("Datum", true),
    /**
     * Datumtijd type.
     */
    DATUMTIJD("Datumtijd"),
    /**
     * Periode.
     */
    PERIODE("Periode", true),
    /**
     * Lijsttype.
     */
    LIJST("Lijst", true),
    /**
     * Naam van een Element.
     */
    ELEMENT("Element"),
    /**
     * ExpressieType voor {@link MetaAttribuut} waarden.
     */
    BRP_METAATTRIBUUT("BrpMetaAttribuut"),
    /**
     * ExpressieType voor {@link MetaRecord} waarden.
     */
    BRP_METARECORD("BrpMetaRecord"),
    /**
     * ExpressieType voor {@link MetaGroep} waarden.
     */
    BRP_METAGROEP("BrpMetaGroep"),
    /**
     * ExpressieType voor {@link MetaObject} waarden.
     */
    BRP_METAOBJECT("BrpMetaObject"),
    /**
     * ExpressieType voor {@link Actie} waarden.
     */
    BRP_ACTIE("BrpActie");


    private final String expressieTypeNaam;
    private final boolean isOrdinal;


    /**
     * Constructor.
     *
     * @param aExpressieTypeNaam Naam van het type.
     */
    ExpressieType(final String aExpressieTypeNaam) {
        this.expressieTypeNaam = aExpressieTypeNaam;
        this.isOrdinal = false;
    }

    /**
     * Constructor.
     *
     * @param aExpressieTypeNaam Naam van het type.
     * @param aIsOrdinal         TRUE als het een ordinal type is; anders FALSE.
     */
    ExpressieType(final String aExpressieTypeNaam, final boolean aIsOrdinal) {
        this.expressieTypeNaam = aExpressieTypeNaam;
        this.isOrdinal = aIsOrdinal;
    }

    public String getNaam() {
        return expressieTypeNaam;
    }

    public boolean isOrdinal() {
        return isOrdinal;
    }

    /**
     * Geeft TRUE als het type onbekend of NULL is; anders FALSE.
     *
     * @return TRUE als het type onbekend of NULL is; anders FALSE.
     */
    public boolean isOnbekendOfNull() {
        return this.equals(ExpressieType.ONBEKEND_TYPE) || this.equals(ExpressieType.NULL);
    }

    /**
     * Geeft TRUE als het opgegeven type compatibel is met dit type; anders FALSE.
     *
     * @param type Te analyseren type.
     * @return TRUE als het opgegeven type compatibel is met dit type; anders FALSE.
     */
    public boolean isCompatibel(final ExpressieType type) {
        if (this == type) {
            return true;
        }
        return (this == ExpressieType.DATUM || this == ExpressieType.DATUMTIJD)
                && (ExpressieType.DATUM.equals(type) || ExpressieType.DATUMTIJD.equals(type));
    }

    @Override
    public String toString() {
        return getNaam();
    }
}
