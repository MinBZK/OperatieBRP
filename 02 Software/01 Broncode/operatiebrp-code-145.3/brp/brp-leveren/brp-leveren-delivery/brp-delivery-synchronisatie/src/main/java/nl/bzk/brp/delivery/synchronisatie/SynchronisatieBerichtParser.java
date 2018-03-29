/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser;
import nl.bzk.brp.delivery.algemeen.VerzoekParser;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.w3c.dom.Node;

/**
 * De parser die van een synchronisatieverzoek een SynchronisatieVerzoek maakt.
 */
public final class SynchronisatieBerichtParser extends AbstractGeneriekeBerichtParser<SynchronisatieVerzoek> {

    private static final Map<String, VerzoekParser<SynchronisatieVerzoek>> DIENST_BERICHT_PARSER_MAP = new HashMap<>();

    static {
        DIENST_BERICHT_PARSER_MAP.put(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON.getIdentifier(), new SynchroniseerPersoonVerzoekParser());
        DIENST_BERICHT_PARSER_MAP.put(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN.getIdentifier(), new SynchroniseerStamgegevenVerzoekParser());
    }

    @Override
    protected VerzoekParser<SynchronisatieVerzoek> geefDienstSpecifiekeParser(final Node node) {
        return DIENST_BERICHT_PARSER_MAP.get(node.getLocalName());
    }

    @Override
    protected SynchronisatieVerzoek maakVerzoek() {
        return new SynchronisatieVerzoek();
    }
}
