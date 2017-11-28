/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.BRP_NAMESPACE_PREFIX;
import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.SLASH;

import javax.xml.xpath.XPath;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.service.bevraging.zoekpersoonopadres.ZoekPersoonOpAdresVerzoek;
import org.w3c.dom.Node;

/**
 * De parser voor ZoekPersoon berichten.
 */
final class ZoekPersoonOpAdresVerzoekParser extends AbstractZoekPersoonGeneriekVerzoekParser<ZoekPersoonOpAdresVerzoek> {

    @Override
    public String getPrefix() {
        return SLASH + BRP_NAMESPACE_PREFIX + SoortBericht.LVG_BVG_ZOEK_PERSOON_OP_ADRES.getIdentifier();
    }

    @Override
    public void vulDienstSpecifiekeGegevens(final ZoekPersoonOpAdresVerzoek verzoek, final Node node, final XPath xPath) {
        verzoek.setSoortDienst(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS);
        parseZoekCriteria(verzoek, node, xPath);
    }

}
