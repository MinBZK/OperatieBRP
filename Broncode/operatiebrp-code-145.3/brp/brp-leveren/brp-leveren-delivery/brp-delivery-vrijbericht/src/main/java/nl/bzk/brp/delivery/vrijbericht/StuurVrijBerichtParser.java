/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht;

import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.BRP_NAMESPACE_PREFIX;
import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.SLASH;

import javax.xml.xpath.XPath;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.delivery.algemeen.AbstractDienstVerzoekParser;
import nl.bzk.brp.service.vrijbericht.VrijBerichtBericht;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerzoek;
import org.w3c.dom.Node;

/**
 * De parser voor stuur vrij bericht verzoeken.
 */
class StuurVrijBerichtParser extends AbstractDienstVerzoekParser<VrijBerichtVerzoek> {


    @Override
    public final String getPrefix() {
        return SLASH + BRP_NAMESPACE_PREFIX + SoortBericht.VRB_VRB_STUUR_VRIJ_BERICHT.getIdentifier();
    }

    @Override
    public void vulDienstSpecifiekeGegevens(VrijBerichtVerzoek verzoek, Node node, XPath xPath) {
        final VrijBerichtBericht vrijBericht = new VrijBerichtBericht();
        vrijBericht
                .setSoortNaam(getNodeTextContent(getPrefix() + "/brp:vrijBericht/brp:soortNaam", xPath, node));
        vrijBericht.setInhoud(
                getNodeTextContent(getPrefix() + "/brp:vrijBericht/brp:inhoud", xPath, node));
        verzoek.setVrijBericht(vrijBericht);
    }

    @Override
    public final void vulParameters(final VrijBerichtVerzoek verzoek, final Node node, final XPath xPath) {
        verzoek.getParameters().setCommunicatieId(
                getNodeTextContent(getPrefix() + "/brp:parameters/@brp:communicatieID", xPath, node));
        verzoek.getParameters()
                .setOntvangerVrijBericht(
                        getNodeTextContent(getPrefix() + "/brp:parameters/brp:ontvangerVrijBericht", xPath, node));
        verzoek.getParameters()
                .setZenderVrijBericht(
                        getNodeTextContent(getPrefix() + "/brp:parameters/brp:zenderVrijBericht", xPath, node));
    }

}
