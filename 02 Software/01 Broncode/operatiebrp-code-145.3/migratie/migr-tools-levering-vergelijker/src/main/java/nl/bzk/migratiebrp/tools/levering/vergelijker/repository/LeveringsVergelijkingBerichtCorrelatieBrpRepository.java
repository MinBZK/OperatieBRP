/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.repository;

import java.util.List;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingBerichtCorrelatieBrp;

/**
 * DAO interface voor de berichten voor de leveringvergelijking.
 */
public interface LeveringsVergelijkingBerichtCorrelatieBrpRepository {

    /**
     * Haal de berichten voor de leveringvergelijkingen uit BRP op.
     * @param bijhoudingBerichtMessageId Het message_id van het bijhoudingsbericht dat geleid heeft tot de leveringsberichten.
     * @param afnemerCode De code van de afnemer.
     * @return Lijst met de berichten voor de leveringvergelijkingen.
     */
    List<LeveringsVergelijkingBerichtCorrelatieBrp> haalLeveringsVergelijkingBerichtCorrelatiesBrpOp(
            final Long bijhoudingBerichtMessageId,
            final String afnemerCode);

}
