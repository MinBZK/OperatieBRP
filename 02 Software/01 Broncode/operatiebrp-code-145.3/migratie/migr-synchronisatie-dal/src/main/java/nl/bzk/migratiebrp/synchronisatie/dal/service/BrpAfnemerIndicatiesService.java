/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service;

import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;

/**
 * Service voor BrpAfnemerIndicaties.
 */
public interface BrpAfnemerIndicatiesService {
    /**
     * Slaat afnemersindicaties voor een persoon op in de BRP database, de persoon moet bestaan anders treedt er een
     * fout op.
     * @param brpAfnemersindicaties de afnemers indicaties
     * @throws IllegalStateException wanneer de persoon van deze indicatie niet gevonden kan worden in de database
     */
    void persisteerAfnemersindicaties(BrpAfnemersindicaties brpAfnemersindicaties);

    /**
     * Lees afnemersindicaties voor een persoon uit de BRP database, de afnemersindicatie moet bestaan anders treedt er
     * en fout op.
     * @param aNummer administratienummer van de persoon waarvoor de afnemersindicaties gelezen moeten worden
     * @return brpAfnemersindicaties de afnemers indicaties
     * @throws IllegalStateException wanneer de afnemersindicatie niet gevonden kan worden voor het gegeven administratienummer
     */
    BrpAfnemersindicaties bevraagAfnemersindicaties(String aNummer);
}
