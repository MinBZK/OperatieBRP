/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie;

import javax.xml.xpath.XPath;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.service.afnemerindicatie.AfnemerindicatieVerzoek;
import org.w3c.dom.Node;

/**
 * De parser voor het verwijderen afnemerindicatie berichten.
 */
final class VerwijderAfnemerindicatieVerzoekParser extends AbstractAfnemerindicatieVerzoekParser {

    @Override
    protected String getDienstSpecifiekePrefix() {
        return getPrefix() + "/brp:verwijderingAfnemerindicatie";
    }

    @Override
    protected String getActieSpecifiekePrefix() {
        return getDienstSpecifiekePrefix() + "/brp:acties/brp:vervalAfnemerindicatie";
    }

    @Override
    public void vulDienstSpecifiekeGegevens(final AfnemerindicatieVerzoek verzoek, final Node node, final XPath xPath) {
        super.vulDienstSpecifiekeGegevens(verzoek, node, xPath);
        verzoek.setSoortDienst(SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
    }

}
