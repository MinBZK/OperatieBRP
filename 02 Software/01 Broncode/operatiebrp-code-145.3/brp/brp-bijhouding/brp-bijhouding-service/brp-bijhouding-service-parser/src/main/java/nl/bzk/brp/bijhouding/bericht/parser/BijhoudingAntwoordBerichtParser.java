/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;

/**
 * Deze parser kan XML berichten van het type <code>Objecttype_Bericht_Bijhouding</code> parsen naar een
 * BijhoudingAntwoordBericht.
 */
public final class BijhoudingAntwoordBerichtParser extends AbstractParser<BijhoudingAntwoordBericht> {

    private final ObjectParser objectParser;

    /**
     * Maakt een nieuw BijhoudingAntwoordBerichtParser object.
     */
    public BijhoudingAntwoordBerichtParser() {
        objectParser = new ObjectParser(BijhoudingAntwoordBericht.class);
    }

    @Override
    protected void verwerkStartElement(final ParserContext context) throws ParseException {
        objectParser.verwerkStartElement(context);
    }

    @Override
    protected BijhoudingAntwoordBericht verwerkEindeElement(final ParserContext context) throws ParseException {
        return (BijhoudingAntwoordBericht) objectParser.verwerkEindeElement(context);
    }

    @Override
    protected void verwerkAttributen(final Map<String, String> attributenMap) throws ParseException {
        objectParser.verwerkAttributen(attributenMap);
    }

    @Override
    protected void verwerkChildElement(final ParserContext context) throws ParseException {
        objectParser.verwerkChildElement(context);
    }

    @Override
    public List<String> getElementNamen() {
        return objectParser.getElementNamen();
    }
}
