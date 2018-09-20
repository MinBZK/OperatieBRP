/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Bevat alle mogelijke typen van expressies.
 */
public enum ExpressieType {
    /**
     * Onbekend type.
     */
    ONBEKEND_TYPE("OnbekendType"),
    /**
     * Null type.
     */
    NULL("Null"),
    /**
     * Error type.
     */
    FOUT("Fout"),
    /**
     * Numeriek type (integer).
     */
    GETAL("Getal", true),
    /**
     * Numeriek type (long).
     */
    GROOT_GETAL("GrootGetal", true),
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
    LIJST("Lijst"),
    /**
     * Type van attribuutverwijzing.
     */
    ATTRIBUUT("Attribuut"),
    /**
     * Type van groep.
     */
    GROEP("Groep"),
    /**
     * Type van een attribuutcode.
     */
    ATTRIBUUTCODE("Attribuutcode"),
    /**
     * Persoontype.
     */
    PERSOON("Persoon"),
    /**
     * Adrestype.
     */
    ADRES("Adres"),
    /**
     * Voornaamtype.
     */
    VOORNAAM("Voornaam"),
    /**
     * Geslachtsnaamcomponenttype.
     */
    GESLACHTSNAAMCOMPONENT("Geslachtsnaamcomponent"),
    /**
     * Nationaliteittype.
     */
    NATIONALITEIT("Nationaliteit"),
    /**
     * Indicatietype.
     */
    INDICATIE("Indicatie"),
    /**
     * Verificatietype.
     */
    VERIFICATIE("Verificatie"),
    /**
     * Onderzoektype.
     */
    ONDERZOEK("Onderzoek"),
    /**
     * GegevenInOnderzoektype.
     */
    GEGEVEN_IN_ONDERZOEK("GegevenInOnderzoek"),
    /**
     * PartijOnderzoektype.
     */
    PARTIJ_ONDERZOEK("Partijonderzoek"),
    /**
     * PartijOnderzoektype.
     */
    PERSOON_ONDERZOEK("PersoonOnderzoek"),
    /**
     * Reisdocumenttype.
     */
    REISDOCUMENT("Reisdocument"),
    /**
     * Afnemerindicatietype.
     */
    AFNEMERINDICATIE("Afnemerindicatie"),
    /**
     * Verstrekkingsbeperkingtype.
     */
    VERSTREKKINGSBEPERKING("Verstrekkingsbeperking"),
    /**
     * Betrokkenheidtype.
     */
    BETROKKENHEID("Betrokkenheid"),
    /**
     * Huwelijktype.
     */
    HUWELIJK("Huwelijk"),
    /**
     * Geregistreerd-partnerschaptype.
     */
    GEREGISTREERDPARTNERSCHAP("GeregistreerdPartnerschap"),

    /**
     * FamilierechtelijkeBetrekking type.
     */
    FAMILIERECHTELIJKEBETREKKING("FamilierechtelijkeBetrekking"),
    /**
     * Multirealiteitregeltype.
     */
    MULTIREALITEITREGEL("Multirealiteitregel");

    private static final ExpressieType[]    BRP_OBJECT_ARRAY = new ExpressieType[]{
        ExpressieType.PERSOON, ExpressieType.ADRES, ExpressieType.VOORNAAM, ExpressieType.GESLACHTSNAAMCOMPONENT,
        ExpressieType.NATIONALITEIT, ExpressieType.INDICATIE, ExpressieType.VERIFICATIE, ExpressieType.ONDERZOEK,
        ExpressieType.REISDOCUMENT, ExpressieType.AFNEMERINDICATIE, ExpressieType.VERSTREKKINGSBEPERKING,
        ExpressieType.BETROKKENHEID, ExpressieType.HUWELIJK, ExpressieType.GEREGISTREERDPARTNERSCHAP,
        ExpressieType.FAMILIERECHTELIJKEBETREKKING, ExpressieType.GEGEVEN_IN_ONDERZOEK, ExpressieType.PARTIJ_ONDERZOEK,
        ExpressieType.PERSOON_ONDERZOEK,
    };
    private static final Set<ExpressieType> BRP_OBJECT_TYPES = new HashSet<>(Arrays.asList(BRP_OBJECT_ARRAY));

    private final String  expressieTypeNaam;
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
        final boolean getalCompatibel = (this == ExpressieType.GROOT_GETAL || this == ExpressieType.GETAL)
            && (ExpressieType.GROOT_GETAL.equals(type) || ExpressieType.GETAL.equals(type));
        final boolean datumCompatibel = (this == ExpressieType.DATUM || this == ExpressieType.DATUMTIJD)
            && (ExpressieType.DATUM.equals(type) || ExpressieType.DATUMTIJD.equals(type));
        return getalCompatibel || datumCompatibel;
    }

    @Override
    public String toString() {
        return getNaam();
    }

    /**
     * Geeft TRUE als het type overeenstemt met een BRP-objecttype (zoals PERSOON en NATIONALITEIT).
     *
     * @param type Te analyseren type.
     * @return TRUE als het type overeenstemt met een BRP-objecttype.
     */
    public static boolean isBRPObjectType(final ExpressieType type) {
        return BRP_OBJECT_TYPES.contains(type);
    }

}
