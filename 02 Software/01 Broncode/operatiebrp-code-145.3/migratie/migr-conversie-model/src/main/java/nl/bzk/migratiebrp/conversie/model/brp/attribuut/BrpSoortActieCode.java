/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Deze class representeert een BRP soort actie code.
 */
public final class BrpSoortActieCode extends AbstractBrpAttribuutMetOnderzoek {

    /**
     * Conversie GBA.
     */
    public static final BrpSoortActieCode CONVERSIE_GBA = new BrpSoortActieCode("Conversie GBA");
    /**
     * Inschrijving Geboorte.
     */
    public static final BrpSoortActieCode INSCHR_GEBOORTE = new BrpSoortActieCode("Inschrijving Geboorte");
    /**
     * Verhuizing.
     */
    public static final BrpSoortActieCode VERHUIZING = new BrpSoortActieCode("Verhuizing");
    /**
     * Registratie Erkenning.
     */
    public static final BrpSoortActieCode REG_ERKENNING = new BrpSoortActieCode("Registratie Erkenning");
    /**
     * Registratie Huwelijk.
     */
    public static final BrpSoortActieCode REG_HUWELIJK = new BrpSoortActieCode("Registratie Huwelijk");
    /**
     * Wijziging Geslachtsnaamcomponent.
     */
    public static final BrpSoortActieCode WIJZ_GESLACHTSNAAM = new BrpSoortActieCode("Wijziging Geslachtsnaamcomponent");
    /**
     * Wijziging Naamgebruik.
     */
    public static final BrpSoortActieCode WIJZ_NAAMGEBRUIK = new BrpSoortActieCode("Wijziging Naamgebruik");
    /**
     * Correctie Adres Binnen NL.
     */
    public static final BrpSoortActieCode CORRECTIE_ADRES = new BrpSoortActieCode("Correctie Adres Binnen NL");
    /**
     * Conversie GBA Materiële historie.
     */
    public static final BrpSoortActieCode CONVERSIE_GBA_MATERIELE_HISTORIE = new BrpSoortActieCode("Conversie GBA Materiële historie");
    /**
     * Conversie GBA Materiële historie.
     */
    public static final BrpSoortActieCode CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST = new BrpSoortActieCode("Conversie GBA Lege onjuiste categorie");

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpSoortActieCode.
     * @param waarde BRP waarde
     */
    public BrpSoortActieCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpSoortActieCode met onderzoek.
     * @param waarde BRP waarde
     * @param onderzoek het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpSoortActieCode(
            @Element(name = "waarde", required = false) final String waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public String getWaarde() {
        return (String) unwrapImpl(this);
    }
}
