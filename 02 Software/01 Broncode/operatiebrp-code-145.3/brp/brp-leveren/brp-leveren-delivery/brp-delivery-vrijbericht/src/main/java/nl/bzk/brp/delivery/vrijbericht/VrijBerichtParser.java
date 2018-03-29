/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht;

import nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser;
import nl.bzk.brp.delivery.algemeen.VerzoekParser;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerzoek;
import org.w3c.dom.Node;

/**
 * VrijBerichtParser.
 */
final class VrijBerichtParser extends AbstractGeneriekeBerichtParser<VrijBerichtVerzoek> {

    @Override
    protected VerzoekParser<VrijBerichtVerzoek> geefDienstSpecifiekeParser(Node node) {
        return new StuurVrijBerichtParser();
    }

    @Override
    protected VrijBerichtVerzoek maakVerzoek() {
        return new VrijBerichtVerzoek();
    }
}
