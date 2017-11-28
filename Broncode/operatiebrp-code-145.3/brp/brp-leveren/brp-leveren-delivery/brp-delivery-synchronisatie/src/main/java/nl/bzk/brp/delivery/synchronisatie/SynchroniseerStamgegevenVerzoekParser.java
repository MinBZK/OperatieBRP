/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.BRP_NAMESPACE_PREFIX;
import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.SLASH;

import javax.xml.xpath.XPath;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.w3c.dom.Node;

/**
 * De parser voor synchroniseer stamgegeven verzoeken.
 */
final class SynchroniseerStamgegevenVerzoekParser extends AbstractSynchronisatieVerzoekParser {

    @Override
    public String getPrefix() {
        return SLASH + BRP_NAMESPACE_PREFIX + SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN.getIdentifier();
    }

    @Override
    public void vulDienstSpecifiekeGegevens(final SynchronisatieVerzoek verzoek, final Node node, final XPath xPath) {
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_STAMGEGEVEN);
    }

    @Override
    public void vulParameters(final SynchronisatieVerzoek verzoek, final Node node, final XPath xPath) {
        super.vulParameters(verzoek, node, xPath);
        verzoek.getParameters()
                .setStamgegeven(getNodeTextContent(getPrefix() + "/brp:parameters/brp:stamgegeven", xPath, node));
    }
}
