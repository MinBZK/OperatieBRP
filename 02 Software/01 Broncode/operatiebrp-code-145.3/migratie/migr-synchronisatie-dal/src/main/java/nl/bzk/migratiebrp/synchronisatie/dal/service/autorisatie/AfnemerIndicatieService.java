/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie;

import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;

/**
 * Interface class voor alle operaties die nodig zijn voor afnemerindicaties voor de DAL.
 */
public interface AfnemerIndicatieService {
    /**
     * Slaat afnemersindicaties voor een persoon op in de BRP database, de persoon moet bestaan anders treedt er een
     * fout op.
     * @param brpAfnemersindicaties de afnemers indicaties
     * @param persoon de persoon waarvoor de afnemersindicaties opgeslagen moet worden
     */
    void persisteerAfnemersindicaties(List<BrpAfnemersindicatie> brpAfnemersindicaties, Persoon persoon);

    /**
     * Lees afnemersindicaties voor een persoon uit de BRP database, de afnemersindicatie moet bestaan anders treedt er
     * en fout op.
     * @param persoon de persoon waarvoor de afnemersindicaties gelezen moet worden
     * @return brpAfnemersindicaties de afnemers indicaties
     */
    BrpAfnemersindicaties bevraagAfnemersindicaties(Persoon persoon);
}
