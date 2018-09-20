/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import java.util.HashMap;
import java.util.Map;

/**
 * Gbav helper.
 */
public final class GbavHelper {

    private final Map<String, Integer> cyclusActiviteiten = new HashMap<>();
    private final Map<String, Integer> plIds = new HashMap<>();

    /**
     * Registreer cyclus activiteit obv referentie persoon bericht.
     *
     * @param berichtReferentie
     *            gbav persoon bericht
     * @param cyclusActiviteitId
     *            cyclus activiteit id
     */
    public void registeerCyclusActiviteitIdObvReferentiePersoon(final String berichtReferentie, final Integer cyclusActiviteitId) {
        cyclusActiviteiten.put(berichtReferentie, cyclusActiviteitId);
    }

    /**
     * Ophalen cyclus activiteit obv referentie persoon bericht.
     *
     * @param berichtReferentie
     *            gba persoon bericht
     * @return cyclus activiteit id
     */
    public Integer getCyclusActiviteitIdObvReferentiePersoon(final String berichtReferentie) {
        return cyclusActiviteiten.get(berichtReferentie);
    }

    /**
     * Opschonen.
     */
    public void opschonenCyclusActiviteitIds() {
        cyclusActiviteiten.clear();
        plIds.clear();
    }

    /**
     * Registreer pl id obv referentie persoon bericht.
     *
     * @param berichtReferentie
     *            gbav persoon bericht
     * @param plId
     *            pl id
     */
    public void registeerPlIdObvReferentiePersoon(final String berichtReferentie, final Integer plId) {
        plIds.put(berichtReferentie, plId);
    }

    /**
     * Ophalen pl id obv referentie persoon bericht.
     *
     * @param berichtReferentie
     *            gbav persoon bericht referentie
     * @return pl id
     */
    public Integer getPlIdObvReferentiePersoon(final String berichtReferentie) {
        return plIds.get(berichtReferentie);
    }

}
