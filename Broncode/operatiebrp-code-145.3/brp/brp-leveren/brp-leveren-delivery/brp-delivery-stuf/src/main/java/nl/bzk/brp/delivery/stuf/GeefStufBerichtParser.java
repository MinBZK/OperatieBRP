/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.stuf;

import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.BRP_NAMESPACE_PREFIX;
import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.SLASH;

import javax.xml.xpath.XPath;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.delivery.algemeen.AbstractDienstVerzoekParser;
import nl.bzk.brp.service.stuf.StufBericht;
import nl.bzk.brp.service.stuf.StufBerichtVerzoek;
import org.w3c.dom.Node;

/**
 * De parser voor stuur stuf bericht verzoeken.
 */
class GeefStufBerichtParser extends AbstractDienstVerzoekParser<StufBerichtVerzoek> {


    @Override
    public final String getPrefix() {
        return SLASH + BRP_NAMESPACE_PREFIX + SoortBericht.STV_STV_GEEF_STUFBG_BERICHT.getIdentifier();
    }

    @Override
    public void vulDienstSpecifiekeGegevens(StufBerichtVerzoek verzoek, Node node, XPath xPath) {
        final String inhoud = getXmlFragment(getPrefix() + "/brp:berichtVertaling/brp:teVertalenBericht/brp:lvg_synVerwerkPersoon", xPath, node);
        final String
                soortSynchronisatie =
                getNodeTextContent(getPrefix() + "/brp:berichtVertaling/brp:teVertalenBericht/brp:lvg_synVerwerkPersoon/brp:parameters/brp:soortSynchronisatie",
                        xPath, node);
        verzoek.setStufBericht(new StufBericht(inhoud, soortSynchronisatie));
        verzoek.setSoortDienst(SoortDienst.GEEF_STUF_BG_BERICHT);
    }

    @Override
    public final void vulParameters(final StufBerichtVerzoek verzoek, final Node node, final XPath xPath) {
        verzoek.getParameters().setCommunicatieId(
                getNodeTextContent(getPrefix() + "/brp:parameters/@brp:communicatieID", xPath, node));
        verzoek.getParameters()
                .setVersieStufbg(
                        getNodeTextContent(getPrefix() + "/brp:parameters/brp:versieStufbg", xPath, node));
        verzoek.getParameters()
                .setVertalingBerichtsoortBRP(
                        getNodeTextContent(getPrefix() + "/brp:parameters/brp:vertalingBerichtsoortBRP", xPath, node));
        verzoek.getParameters()
                .setLeveringsAutorisatieId(getNodeTextContent(getPrefix() + "/brp:parameters/brp:leveringsautorisatieIdentificatie", xPath, node));
    }

}
