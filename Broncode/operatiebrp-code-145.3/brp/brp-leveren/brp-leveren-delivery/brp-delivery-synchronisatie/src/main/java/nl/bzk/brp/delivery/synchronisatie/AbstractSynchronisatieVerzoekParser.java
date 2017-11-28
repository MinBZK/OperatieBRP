/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import javax.xml.xpath.XPath;
import nl.bzk.brp.delivery.algemeen.AbstractDienstVerzoekParser;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.w3c.dom.Node;

/**
 * Abstacte basis voor het parsen van inkomende synchronisatieverzoeken.
 */
abstract class AbstractSynchronisatieVerzoekParser extends AbstractDienstVerzoekParser<SynchronisatieVerzoek> {

    @Override
    public void vulParameters(final SynchronisatieVerzoek verzoek, final Node node, final XPath xPath) {
        verzoek.getParameters().setCommunicatieId(
                getNodeTextContent(getPrefix() + "/brp:parameters/@brp:communicatieID", xPath, node));
        verzoek.getParameters()
                .setLeveringsAutorisatieId(
                        getNodeTextContent(getPrefix() + "/brp:parameters/brp:leveringsautorisatieIdentificatie", xPath, node));
    }
}
