/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import javax.xml.xpath.XPath;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * De generieke parser voor ZoekPersoon en ZoekPersoon op adres berichten.
 * @param <T> verzoek.
 */
abstract class AbstractZoekPersoonGeneriekVerzoekParser<T extends ZoekPersoonGeneriekVerzoek> extends AbstractBevragingVerzoekParser<T> {

    @Override
    public void vulParameters(final T verzoek, final Node node, final XPath xPath) {
        super.vulParameters(verzoek, node, xPath);
        //peilmoment materieel
        final String peilmomentMaterieel = getNodeTextContent(getPrefix() + "/brp:parameters/brp:peilmomentMaterieel", xPath, node);
        if (!StringUtils.isEmpty(peilmomentMaterieel)) {
            verzoek.getParameters().setPeilmomentMaterieel(peilmomentMaterieel);
        }
        //zoek bereik
        final String zoekbereik = getNodeTextContent(getPrefix() + "/brp:parameters/brp:zoekbereik", xPath, node);
        if (!StringUtils.isEmpty(zoekbereik)) {
            verzoek.getParameters().setZoekBereik(Zoekbereik.getByNaam(zoekbereik));
        }
    }

    /**
     * @param bevragingVerzoek bevragingVerzoek
     * @param node node
     * @param xPath xPath
     */
    final void parseZoekCriteria(final T bevragingVerzoek, final Node node, final XPath xPath) {
        final String zoekcriteriumXpath = getPrefix() + "/brp:zoekcriteria/brp:zoekcriterium";
        final NodeList zoekcriteriaNodes = getNodeList(zoekcriteriumXpath, xPath, node);
        final int nodesCount = zoekcriteriaNodes.getLength();
        for (int i = 0; i < nodesCount; i++) {
            final Node zoekCriteriumNode = zoekcriteriaNodes.item(i);
            final ZoekPersoonVerzoek.ZoekCriteria zoekCriterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
            zoekCriterium.setElementNaam(getNodeTextContent("brp:elementNaam", xPath, zoekCriteriumNode));
            zoekCriterium.setWaarde(getNodeTextContent("brp:waarde", xPath, zoekCriteriumNode));
            zoekCriterium.setZoekOptie(Zoekoptie.getByNaam(getNodeTextContent("brp:optie", xPath, zoekCriteriumNode)));
            zoekCriterium.setCommunicatieId(getNodeTextContent("@brp:communicatieID", xPath, zoekCriteriumNode));
            bevragingVerzoek.getZoekCriteriaPersoon().add(zoekCriterium);
        }
    }
}
