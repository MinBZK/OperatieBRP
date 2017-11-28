/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;

/**
 * De verschillende soorten bijhoudingsberichten.
 */
public enum BijhoudingBerichtSoort {
    /* ====================== Bijhouding berichten ==================== */
    /** bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap. */
    REGISTREER_HUWELIJK_GP("bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap"),
    /** bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R. */
    REGISTREER_HUWELIJK_GP_ANTWOORD("bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R"),
    /** bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap. */
    CORRIGEER_HUWELIJK_GP("bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap"),
    /** bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R. */
    CORRIGEER_HUWELIJK_GP_ANTWOORD("bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R"),
    /** Registreer Verhuizing. */
    REGISTREER_VERHUIZING("bhg_vbaRegistreerVerhuizing"),
    /** Registreer Verhuizing antwoord. */
    REGISTREER_VERHUIZING_ANTWOORD("bhg_vbaRegistreerVerhuizing_R"),
    /** Registreer verblijfsrecht. */
    REGISTREER_VERBLIJFSRECHT("bhg_vbaRegistreerVerblijfsrecht"),
    /** Registreer verblijfsrecht antwoord. */
    REGISTREER_VERBLIJFSRECHT_ANTWOORD("bhg_vbaRegistreerVerblijfsrecht_R"),
    /** Actualiseer infrastructurele wijziging. */
    ACTUALISEER_INFRASTRUCTURELE_WIJZIGING("bhg_vbaActualiseerInfrastructureleWijziging"),
    /** Actualiseer infrastructurele wijziging antwoord. */
    ACTUALISEER_INFRASTRUCTURELE_WIJZIGING_ANTWOORD("bhg_vbaActualiseerInfrastructureleWijziging_R"),
    /** Registreer geboorte. */
    REGISTREER_GEBOORTE("bhg_afsRegistreerGeboorte"),
    /** Registreer geboorte antwoord. */
    REGISTREER_GEBOORTE_ANTWOORD("bhg_afsRegistreerGeboorte_R"),
    /** Actualiseer HuwelijkGeregistreerdPartnerschap.  */
    BHG_HGP_ACTUALISEER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP("bhg_hgpActualiseerHuwelijkGeregistreerdPartnerschap"),
    /** Actualiseer HuwelijkGeregistreerdPartnerschap antwoord.  */
    BHG_HGP_ACTUALISEER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP_ANTWOORD("bhg_hgpActualiseerHuwelijkGeregistreerdPartnerschap_R"),
    /** Registreer aanvang onderzoek. */
    REGISTREER_ONDERZOEK("bhg_ondRegistreerOnderzoek"),
    /** Registreer aanvang onderzoek antwoord. */
    REGISTREER_ONDERZOEK_ANTWOORD("bhg_ondRegistreerOnderzoek_R"),
    /** bhg_afsRegistreerErkenning. */
    BHG_AFS_REGISTREER_ERKENNING("bhg_afsRegistreerErkenning"),
    /** bhg_afsRegistreerErkenning. */
    BHG_AFS_REGISTREER_ERKENNING_ANTWOORD("bhg_afsRegistreerErkenning_R"),
    /* =============================== ISC berichten ========================================*/
    /** isc_migRegistreerHuwelijkGeregistreerdPartnerschap. */
    ISC_REGISTREER_HUWELIJK_GP("isc_migRegistreerHuwelijkGeregistreerdPartnerschap"),
    /** isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R. */
    ISC_REGISTREER_HUWELIJK_GP_ANTWOORD("isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R"),
    /** isc_migRegistreerVerhuizing. */
    ISC_REGISTREER_VERHUIZING("isc_migRegistreerVerhuizing"),
    /** isc_migRegistreerVerhuizing_R */
    ISC_REGISTREER_VERHUIZING_ANTWOORD("isc_migRegistreerVerhuizing_R"),
    /** isc_migRegistreerGeboorte */
    ISC_REGISTREER_GEBOORTE("isc_migRegistreerGeboorte"),
    /** isc_migRegistreerGeboorte_R */
    ISC_REGISTREER_GEBOORTE_ANTWOORD("isc_migRegistreerGeboorte_R"),
    /** isc_migRegistreerGeboorte */
    ISC_REGISTREER_ERKENNING("isc_migRegistreerErkenning"),
    /** isc_migRegistreerGeboorte_R */
    ISC_REGISTREER_ERKENNING_ANTWOORD("isc_migRegistreerErkenning_R");

    private static final String ANTWOORD_SUFFIX = "_ANTWOORD";

    private final String elementNaam;

    /**
     * Maakt een BijhoudingBerichtSoort object.
     *
     * @param elementNaam de naam van het element in het bericht
     */
    BijhoudingBerichtSoort(final String elementNaam) {
        this.elementNaam = elementNaam;
    }

    /**
     * De element naam.
     *
     * @return element naam.
     */
    public String getElementNaam() {
        return elementNaam;
    }

    /**
     * Geeft het bijbehorende antwoord bericht soort voor dit bericht soort.
     *
     * @return antwoord bericht soort
     */
    public BijhoudingBerichtSoort getAntwoordBijhoudingBerichtSoort() {
        return BijhoudingBerichtSoort.valueOf(name() + ANTWOORD_SUFFIX);
    }

    /**
     * De lijst van element namen.
     *
     * @return element namen
     */
    public static List<String> getElementNamen() {
        final List<String> result = new ArrayList<>();
        for (BijhoudingBerichtSoort soort : values()) {
            result.add(soort.getElementNaam());
        }
        return result;
    }

    /**
     * Geeft het soort dat correspondeert met de gegeven element naam.
     *
     * @param elementNaam de element naam
     * @return het soort
     * @throws OngeldigeWaardeException wanneer er geen bijhoudingsberichtsoort correspondeert met de gegeven
     *             elementnaam.
     */
    public static BijhoudingBerichtSoort parseElementNaam(final String elementNaam) throws OngeldigeWaardeException {
        for (final BijhoudingBerichtSoort soort : BijhoudingBerichtSoort.values()) {
            if (soort.getElementNaam().equals(elementNaam)) {
                return soort;
            }
        }
        throw new OngeldigeWaardeException(String.format("Het element '%s' correspondeert niet met een soort bijhoudingsbericht.", elementNaam));
    }
}
