/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Het antwoord bericht voor registratie huwelijk en geregistreerd partnerschap.
 */
public final class RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht extends BijhoudingAntwoordBericht {

    /**
     * Standaard constructor (die direct de soort van het bericht initialiseert/zet).
     */
    public RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht() {
        super(SoortBericht.BHG_HGP_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP_R);
    }

    /**
     * Check of actie van de goede type (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isVoltrekkingHuwelijkInNederland() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND);
    }

    /**
     * Check of actie van de goede type (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isOntbindingHuwelijkInNederland() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_NEDERLAND);
    }

    /**
     * Check of actie van de goede type (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isVoltrekkingHuwelijkInBuitenland() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_BUITENLAND);
    }

    /**
     * Check of actie van de goede type (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isOntbindingHuwelijkInBuitenland() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_BUITENLAND);
    }

    /**
     * Check of actie van de goede type (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isAangaanGeregistreerdPartnerschapInNederland() {
        return isAdministratieveHandelingVanType(
            SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND);
    }

    /**
     * Check of actie van de goede type (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isBeeindigingGeregistreerdPartnerschapInNederland() {
        return isAdministratieveHandelingVanType(
            SoortAdministratieveHandeling.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND);
    }

    /**
     * Check of actie van het goede type is (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isAangaanGeregistreerdPartnerschapInBuitenland() {
        return isAdministratieveHandelingVanType(
            SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND);
    }

    /**
     * Check of actie van het goede type is (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isBeeindigingGeregistreerdPartnerschapInBuitenland() {
        return isAdministratieveHandelingVanType(
            SoortAdministratieveHandeling.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND);
    }

    /**
     * Check of actie van het goede type is (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isOmzettingGeregistreerdPartnerschapInHuwelijk() {
        return isAdministratieveHandelingVanType(
            SoortAdministratieveHandeling.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK);
    }

    /**
     * Check of actie van het goede type is (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isNietigverklaringHuwelijk() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.NIETIGVERKLARING_HUWELIJK);
    }

    /**
     * Check of actie van het goede type is (wordt gebruikt in Jibx).
     *
     * @return true als goede type, false anders.
     */
    public boolean isNietigverklaringGeregistreerdPartnerschap() {
        return isAdministratieveHandelingVanType(
            SoortAdministratieveHandeling.NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP);
    }

}
