/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import javax.xml.xpath.XPath;
import nl.bzk.brp.delivery.algemeen.AbstractDienstVerzoekParser;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import org.w3c.dom.Node;

/**
 * De abstracte basis voor het parsen van bevragingsverzoeken.
 * @param <T> het specifieke type {@link BevragingVerzoek}
 */
abstract class AbstractBevragingVerzoekParser<T extends BevragingVerzoek> extends AbstractDienstVerzoekParser<T> {

    @Override
    public void vulParameters(final T verzoek, final Node node, final XPath xPath) {
        verzoek.getParameters().setCommunicatieId(getNodeTextContent(getPrefix() + "/brp:parameters/@brp:communicatieID", xPath, node));
        verzoek.getParameters().setRolNaam(getNodeTextContent(getPrefix() + "/brp:parameters/brp:rolNaam", xPath, node));
        verzoek.getParameters()
                .setLeveringsAutorisatieId(getNodeTextContent(getPrefix() + "/brp:parameters/brp:leveringsautorisatieIdentificatie", xPath, node));
        verzoek.getParameters()
                .setDienstIdentificatie(getNodeTextContent(getPrefix() + "/brp:parameters/brp:dienstIdentificatie", xPath, node));
    }
}
