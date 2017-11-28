/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie;

import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser;
import nl.bzk.brp.delivery.algemeen.VerzoekParser;
import nl.bzk.brp.service.afnemerindicatie.AfnemerindicatieVerzoek;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import org.w3c.dom.Node;

/**
 * De parser die van een onderhoud afnemerindicatieverzoek AfnemerindicatieVerzoek maakt.
 */
final class OnderhoudAfnemerindicatiesBerichtParser extends AbstractGeneriekeBerichtParser<AfnemerindicatieVerzoek> {
    /**
     * Het XML element voor Plaatsing Afnemerindicatie.
     */
    private static final String PLAATSING_AFNEMERINDICATIE = "plaatsingAfnemerindicatie";
    /**
     * Het XML element voor Verwijdering Afnemerindicatie.
     */
    private static final String VERWIJDERING_AFNEMERINDICATIE = "verwijderingAfnemerindicatie";
    private static final Map<String, VerzoekParser<AfnemerindicatieVerzoek>> DIENST_BERICHT_PARSER_MAP = new HashMap<>();

    static {
        DIENST_BERICHT_PARSER_MAP.put(PLAATSING_AFNEMERINDICATIE, new PlaatsAfnemerindicatieVerzoekParser());
        DIENST_BERICHT_PARSER_MAP.put(VERWIJDERING_AFNEMERINDICATIE, new VerwijderAfnemerindicatieVerzoekParser());

    }


    @Override
    protected VerzoekParser<AfnemerindicatieVerzoek> geefDienstSpecifiekeParser(final Node node) {
        try {
            final String plaatsenExpressie = SLASH + BRP_NAMESPACE_PREFIX + SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE.getIdentifier()
                    + SLASH + BRP_NAMESPACE_PREFIX + PLAATSING_AFNEMERINDICATIE;
            final String verwijderenExpressie = SLASH + BRP_NAMESPACE_PREFIX + SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE.getIdentifier()
                    + SLASH + BRP_NAMESPACE_PREFIX + VERWIJDERING_AFNEMERINDICATIE;
            final Node plaatsingNode = (Node) xpath.evaluate(plaatsenExpressie, node, XPathConstants.NODE);
            final Node verwijderingNode = (Node) xpath.evaluate(verwijderenExpressie, node, XPathConstants.NODE);
            for (Map.Entry<String, VerzoekParser<AfnemerindicatieVerzoek>> entry : DIENST_BERICHT_PARSER_MAP.entrySet()) {
                if ((plaatsingNode != null && entry.getKey().contains(plaatsingNode.getLocalName())) || (verwijderingNode != null && entry.getKey()
                        .contains(verwijderingNode.getLocalName()))) {
                    return entry.getValue();
                }
            }
        } catch (final XPathExpressionException e) {
            throw new UnsupportedOperationException("XPath kon niet worden geëvalueerd.", e);
        }
        throw new BrpServiceRuntimeException("Geen geschikte parser voor dit verzoek.");
    }

    @Override
    protected AfnemerindicatieVerzoek maakVerzoek() {
        return new AfnemerindicatieVerzoek();
    }

}
