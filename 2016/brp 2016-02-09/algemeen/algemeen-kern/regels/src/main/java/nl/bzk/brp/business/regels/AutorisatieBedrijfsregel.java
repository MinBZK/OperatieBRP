/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;


/**
 * Interface voor bedrijfsregels die te maken hebben met autorisatie. Deze bedrijfsregels voeren een controle uit en
 * retourneren uiteindelijk of de bedrijfsregel is overtreden door middel van een boolean waarde.
 */
public interface AutorisatieBedrijfsregel extends Bedrijfsregel<AutorisatieRegelContext> {

    @Override boolean valideer(AutorisatieRegelContext regelContext);
}
