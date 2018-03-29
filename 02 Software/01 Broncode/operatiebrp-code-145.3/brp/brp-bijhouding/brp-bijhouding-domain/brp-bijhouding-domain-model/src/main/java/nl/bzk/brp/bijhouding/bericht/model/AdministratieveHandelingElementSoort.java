/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;

/**
 * De verschillende soorten administratievehandeling elementen.
 */
public enum AdministratieveHandelingElementSoort {

    /**
     * voltrekkingHuwelijkInNederland.
     */
    VOLTREKKING_HUWELIJK_IN_NEDERLAND("voltrekkingHuwelijkInNederland", false, false, false),
    /**
     * ontbindingHuwelijkInNederland.
     */
    ONTBINDING_HUWELIJK_IN_NEDERLAND("ontbindingHuwelijkInNederland", false, false, false),
    /**
     * voltrekkingHuwelijkInBuitenland.
     */
    VOLTREKKING_HUWELIJK_IN_BUITENLAND("voltrekkingHuwelijkInBuitenland", false, false, false),
    /**
     * ontbindingHuwelijkInBuitenland.
     */
    ONTBINDING_HUWELIJK_IN_BUITENLAND("ontbindingHuwelijkInBuitenland", false, false, false),
    /**
     * aangaanGeregistreerdPartnerschapInNederland.
     */
    AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND("aangaanGeregistreerdPartnerschapInNederland", false, false, false),
    /**
     * beeindigingGeregistreerdPartnerschapInNederland.
     */
    BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND("beeindigingGeregistreerdPartnerschapInNederland", false, false, false),
    /**
     * aangaanGeregistreerdPartnerschapInBuitenland.
     */
    AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND("aangaanGeregistreerdPartnerschapInBuitenland", false, false, false),
    /**
     * beeindigingGeregistreerdPartnerschapInBuitenland.
     */
    BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND("beeindigingGeregistreerdPartnerschapInBuitenland", false, false, false),
    /**
     * omzettingGeregistreerdPartnerschapInHuwelijk.
     */
    OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK("omzettingGeregistreerdPartnerschapInHuwelijk", false, false, false),
    /**
     * nietigverklaringHuwelijkInNederland.
     */
    NIETIGVERKLARING_HUWELIJK_IN_NEDERLAND("nietigverklaringHuwelijkInNederland", false, false, false),
    /**
     * nietigverklaringGeregistreerdPartnerschapInNederland.
     */
    NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND("nietigverklaringGeregistreerdPartnerschapInNederland", false, false, false),
    /**
     * verhuizingBinnengemeentelijk.
     */
    VERHUIZING_BINNENGEMEENTELIJK("verhuizingBinnengemeentelijk", false, false, false),
    /**
     * verhuizingIntergemeentelijk.
     */
    VERHUIZING_INTERGEMEENTELIJK("verhuizingIntergemeentelijk", false, false, false),
    /**
     * verhuizingIntergemeentelijk.
     */
    VERHUIZING_NAAR_BUITENLAND("verhuizingNaarBuitenland", false, true, false),
    /**
     * gBAVerhuizingIntergemeentelijkGBANaarBRP.
     */
    GBA_VERHUIZING_INTERGEMEENTELIJK_GBA_NAAR_BRP("gBAVerhuizingIntergemeentelijkGBANaarBRP", true, false, false),
    /**
     * gBAVoltrekkingHuwelijkInNederland.
     */
    GBA_VOLTREKKING_HUWELIJK_IN_NEDERLAND("gBAVoltrekkingHuwelijkInNederland", true, false, false),
    /**
     * gBAOntbindingHuwelijkInNederland.
     */
    GBA_ONTBINDING_HUWELIJK_IN_NEDERLAND("gBAOntbindingHuwelijkInNederland", true, false, false),
    /**
     * gBAAangaanGeregistreerdPartnerschapInNederland.
     */
    GBA_AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND("gBAAangaanGeregistreerdPartnerschapInNederland", true, false, false),
    /**
     * gBABeeindigingGeregistreerdPartnerschapInNederland.
     */
    GBA_BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND("gBABeeindigingGeregistreerdPartnerschapInNederland", true, false, false),
    /**
     * gBAOmzettingGeregistreerdPartnerschapInHuwelijk.
     */
    GBA_OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK("gBAOmzettingGeregistreerdPartnerschapInHuwelijk", true, false, false),
    /**
     * wijzigingBijzondereVerblijfsrechtelijkePositie.
     */
    WIJZIGING_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE("wijzigingBijzondereVerblijfsrechtelijkePositie", false, true, false),
    /**
     * wijzigingVerblijfsrecht.
     */
    WIJZIGING_VERBLIJFSRECHT("wijzigingVerblijfsrecht", false, true, false),
    /**
     * wijzigingAdresInfrastructureel.
     */
    WIJZIGING_ADRES_INFRASTRUCTUREEL("wijzigingAdresInfrastructureel", false, false, false),
    /**
     * wijzigingGemeenteInfrastructureel.
     */
    WIJZIGING_GEMEENTE_INFRASTRUCTUREEL("wijzigingGemeenteInfrastructureel", false, false, false),
    /**
     * geboorteInNederland.
     */
    GEBOORTE_IN_NEDERLAND("geboorteInNederland", false, false, false),
    /**
     * geboorteInNederlandMetErkenningOpGeboortedatum.
     */
    GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM("geboorteInNederlandMetErkenningOpGeboortedatum", false, false, false),
    /**
     * geboorteInNederlandMetErkenningNaGeboortedatum.
     */
    GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM("geboorteInNederlandMetErkenningNaGeboortedatum", false, false, false),
    /**
     * erkenning.
     */
    ERKENNING("erkenning", false, false, false),
    /**
     * gBAErkenning.
     */
    GBA_ERKENNING("gBAErkenning", true, false, false),
    /**
     * Adoptie.
     */
    ADOPTIE("adoptie", false, false, false),
    /**
     * gBAGeboorteInNederland.
     */
    GBA_GEBOORTE_IN_NEDERLAND("gBAGeboorteInNederland", true, false, false),
    /**
     * gBAGeboorteInNederlandMetErkenningOpGeboortedatum.
     */
    GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM("gBAGeboorteInNederlandMetErkenningOpGeboortedatum", true, false, false),
    /**
     * gBAGeboorteInNederlandMetErkenningNaGeboortedatum.
     */
    GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM("gBAGeboorteInNederlandMetErkenningNaGeboortedatum", true, false, false),
    /**
     * vestigingNietIngeschrevene.
     */
    VESTIGING_NIET_INGESCHREVENE("vestigingNietIngeschrevene", false, false, false),
    /**
     * correctieHuwelijk.
     */
    CORRECTIE_HUWELIJK("correctieHuwelijk", false, false, true),
    /**
     * correctieGeregistreerdPartnerschap.
     */
    CORRECTIE_GEREGISTREERD_PARTNERSCHAP("correctieGeregistreerdPartnerschap", false, false, true),
    /**
     * correctiePartnergegevensGeregistreerdPartnerschap.
     */
    CORRECTIE_PARTNERGEGEVENS_GEREGISTREERD_PARTNERSCHAP("correctiePartnergegevensGeregistreerdPartnerschap", false, false, true),
    /**
     * correctiePartnergegevensHuwelijk.
     */
    CORRECTIE_PARTNERGEGEVENS_HUWELIJK("correctiePartnergegevensHuwelijk", false, false, true),
    /**
     * ongedaanmakingHuwelijk.
     */
    ONGEDAANMAKING_HUWELIJK("ongedaanmakingHuwelijk", false, false, true),
    /**
     * ongedaanmakingGeregistreerdPartnerschap.
     */
    ONGEDAANMAKING_GEREGISTREERD_PARTNERSCHAP("ongedaanmakingGeregistreerdPartnerschap", false, false, true),
    /**
     * wijzigingPartnergegevensHuwelijk.
     */
    WIJZIGING_PARTNERGEGEVENS_HUWELIJK("wijzigingPartnergegevensHuwelijk", false, false, false),
    /**
     * wijzigingPartnergegevensGeregistreerdPartnerschap.
     */
    WIJZIGING_PARTNERGEGEVENS_GEREGISTREERD_PARTNERSCHAP("wijzigingPartnergegevensGeregistreerdPartnerschap", false, false, false),
    /**
     * aanvangOnderzoek.
     */
    AANVANG_ONDERZOEK("aanvangOnderzoek", false, true, false),
    /**
     * wijzigingOnderzoek.
     */
    WIJZIGING_ONDERZOEK("wijzigingOnderzoek", false, true, false),
    /**
     * registratieNietAangetroffenOpAdres.
     */
    REGISTRATIE_NIET_AANGETROFFEN_OP_ADRES("registratieNietAangetroffenOpAdres", false, true, false),
    /**
     * beeindigingOnderzoek.
     */
    BEEINDIGING_ONDERZOEK("beeindigingOnderzoek", false, true, false),
    /**
     * overlijden.
     */
    OVERLIJDEN("overlijden", false, false, false),
    /**
     * wijzigingGemeenteInfrastructureelOverlijden.
     */
    WIJZIGING_GEMEENTE_INFRASTRUCTUREEL_OVERLIJDEN("wijzigingGemeenteInfrastructureelOverlijden", false, false, false),
    /**
     * vestigingNietIngezetene.
     */
    REGISTRATIE_VESTIGING_NIET_INGEZETENE("vestigingNietIngezetene", false, false, false),
    ;

    private final String elementNaam;
    private final boolean gbaAdministratieveHandeling;
    private final boolean controleerZendendePartijIsBijhoudendePartij;
    private final boolean isCorrectie;

    /**
     * Maakt een AdministratieveHandelingElementSoort object.
     * @param elementNaam de naam van het element in het bericht
     * @param gbaAdministratieveHandeling true als  het een gba Administratieve handeling betreft.
     * @param controleerZendendePartijIsBijhoudendePartij true als er gecontroleerd moet worden of de zendende partij de bijhoudende partij is.
     * @param isCorrectie is deze handeling een correctie handeling
     */
    AdministratieveHandelingElementSoort(final String elementNaam, final boolean gbaAdministratieveHandeling,
                                         final boolean controleerZendendePartijIsBijhoudendePartij, boolean isCorrectie) {
        this.elementNaam = elementNaam;
        this.gbaAdministratieveHandeling = gbaAdministratieveHandeling;
        this.controleerZendendePartijIsBijhoudendePartij = controleerZendendePartijIsBijhoudendePartij;
        this.isCorrectie = isCorrectie;
    }

    /**
     * De element naam.
     * @return element naam.
     */
    public String getElementNaam() {
        return elementNaam;
    }

    /**
     * geeft aan of de Administratievehandeling een GBA handeling is.
     * @return the boolean
     */
    public boolean gbaAdministratieveHandeling() {
        return gbaAdministratieveHandeling;
    }

    /**
     * Geeft aan of er gecontroleerd moet worden of de zendende partij de bijhoudende partij is (zie {@link Regel#R1610}).
     * @return true als de controle uitgevoerd moet worden
     */
    public boolean isControleerZendendePartijIsBijhoudendePartij() {
        return controleerZendendePartijIsBijhoudendePartij;
    }

    /**
     * Is dit een correctie handeling.
     * @return true als dit een correctie handeling is, anders false
     */
    public boolean isCorrectie() {
        return isCorrectie;
    }

    /**
     * De lijst van element namen.
     * @return element namen
     */
    public static List<String> getElementNamen() {
        final List<String> result = new ArrayList<>();
        for (final AdministratieveHandelingElementSoort soort : values()) {
            result.add(soort.getElementNaam());
        }
        return result;
    }

    /**
     * Geeft het soort dat correspondeert met de gegeven element naam.
     * @param elementNaam de element naam
     * @return het soort
     * @throws OngeldigeWaardeException wanneer er geen administratieve handeling correspondeert met de gegeven             elementnaam.
     */
    public static AdministratieveHandelingElementSoort parseElementNaam(final String elementNaam) throws OngeldigeWaardeException {
        for (final AdministratieveHandelingElementSoort soort : AdministratieveHandelingElementSoort.values()) {
            if (soort.getElementNaam().equals(elementNaam)) {
                return soort;
            }
        }
        throw new OngeldigeWaardeException(String.format("Het element '%s' correspondeert niet met een soort administratievehandeling.", elementNaam));
    }
}
