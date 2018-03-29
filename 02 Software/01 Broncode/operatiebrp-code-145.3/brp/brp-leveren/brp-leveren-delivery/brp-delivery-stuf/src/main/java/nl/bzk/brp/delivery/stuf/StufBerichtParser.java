/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.stuf;

import nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser;
import nl.bzk.brp.delivery.algemeen.VerzoekParser;
import nl.bzk.brp.service.stuf.StufBerichtVerzoek;
import org.w3c.dom.Node;

/**
 * StufBerichtParser.
 */
final class StufBerichtParser extends AbstractGeneriekeBerichtParser<StufBerichtVerzoek> {

    @Override
    protected VerzoekParser<StufBerichtVerzoek> geefDienstSpecifiekeParser(Node node) {
        return new GeefStufBerichtParser();
    }

    @Override
    protected StufBerichtVerzoek maakVerzoek() {
        return new StufBerichtVerzoek();
    }
}
