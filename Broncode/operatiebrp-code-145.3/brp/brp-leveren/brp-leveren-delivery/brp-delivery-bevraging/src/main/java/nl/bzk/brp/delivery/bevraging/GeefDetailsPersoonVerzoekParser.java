/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.BRP_NAMESPACE_PREFIX;
import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.SLASH;

import javax.xml.xpath.XPath;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * De parser voor GeefDetailsPersoon berichten.
 */
final class GeefDetailsPersoonVerzoekParser extends AbstractBevragingVerzoekParser<GeefDetailsPersoonVerzoek> {

    @Override
    public String getPrefix() {
        return SLASH + BRP_NAMESPACE_PREFIX + SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON.getIdentifier();
    }

    @Override
    public void vulDienstSpecifiekeGegevens(final GeefDetailsPersoonVerzoek bevragingVerzoek, final Node node, final XPath xPath) {
        parseScopingElementen(bevragingVerzoek, node, xPath);
        parseIdentificatiecriteria(bevragingVerzoek, node, xPath);
        bevragingVerzoek.setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
    }

    @Override
    public void vulParameters(final GeefDetailsPersoonVerzoek verzoek, final Node node, final XPath xPath) {
        super.vulParameters(verzoek, node, xPath);
        // Historiefilter
        final String historieVorm = getNodeTextContent(getPrefix() + "/brp:parameters/brp:historievorm", xPath, node);
        if (!StringUtils.isEmpty(historieVorm)) {
            verzoek.getParameters().setHistorieVorm(HistorieVorm.getByNaam(historieVorm));
        }
        //peilmoment formeel
        final String peilMomentFormeelResultaat = getNodeTextContent(getPrefix() + "/brp:parameters/brp:peilmomentFormeelResultaat", xPath, node);
        if (!StringUtils.isEmpty(peilMomentFormeelResultaat)) {
            verzoek.getParameters()
                    .setPeilMomentFormeelResultaat(peilMomentFormeelResultaat);
        }
        //peilmoment materieel resultaat
        final String peilMomentMaterieelResultaat = getNodeTextContent(getPrefix() + "/brp:parameters/brp:peilmomentMaterieelResultaat", xPath, node);
        if (!StringUtils.isEmpty(peilMomentMaterieelResultaat)) {
            verzoek.getParameters()
                    .setPeilMomentMaterieelResultaat(peilMomentMaterieelResultaat);
        }
        //verantwoording
        final String verantwoording = getNodeTextContent(getPrefix() + "/brp:parameters/brp:verantwoording", xPath, node);
        if (!StringUtils.isEmpty(verantwoording)) {
            verzoek.getParameters().setVerantwoording(verantwoording);
        }
    }

    private void parseScopingElementen(final GeefDetailsPersoonVerzoek bevragingVerzoek, final Node node, final XPath xPath) {
        final String scopingElementenXpath = getPrefix() + "/brp:scopeElementen/brp:elementNaam";
        final String communicatieId = getNodeTextContent(getPrefix() + "/brp:scopeElementen/@brp:communicatieID", xPath, node);
        bevragingVerzoek.getScopingElementen().setCommunicatieId(communicatieId);
        final NodeList scopingElementenXpathNodes = getNodeList(scopingElementenXpath, xPath, node);
        if (scopingElementenXpathNodes.getLength() > 0) {
            final int nodesCount = scopingElementenXpathNodes.getLength();
            for (int i = 0; i < nodesCount; i++) {
                final Node elementNaamNode = scopingElementenXpathNodes.item(i);
                bevragingVerzoek.getScopingElementen().getElementen().add(elementNaamNode.getTextContent());
            }
        }
    }

    private void parseIdentificatiecriteria(final GeefDetailsPersoonVerzoek bevragingVerzoek, final Node node, final XPath xPath) {
        final String identificatiecriteriaRoot = "/brp:identificatiecriteria/";
        final String communicatieId = getNodeTextContent(getPrefix() + identificatiecriteriaRoot + "@brp:communicatieID", xPath, node);
        final String bsn = getNodeTextContent(getPrefix() + identificatiecriteriaRoot + "brp:burgerservicenummer", xPath, node);
        final String anr = getNodeTextContent(getPrefix() + identificatiecriteriaRoot + "brp:administratienummer", xPath, node);
        final String objectSleutel = getNodeTextContent(getPrefix() + identificatiecriteriaRoot + "brp:objectSleutel", xPath, node);
        bevragingVerzoek.getIdentificatiecriteria().setCommunicatieId(communicatieId);
        bevragingVerzoek.getIdentificatiecriteria().setBsn(bsn);
        bevragingVerzoek.getIdentificatiecriteria().setAnr(anr);
        bevragingVerzoek.getIdentificatiecriteria().setObjectSleutel(objectSleutel);
    }
}
