/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.model.VerwerkBijhoudingsplanBericht;
import nl.bzk.brp.bijhouding.bericht.model.VerwerkBijhoudingsplanBerichtImpl;

/**
 * Deze parser kan XML berichten van het type <code>Systeemhandeling_VerwerkBijhoudingsplan</code> parsen naar een
 * VerwerkBijhoudingsplanBericht.
 */
public final class VerwerkBijhoudingsplanBerichtParser extends AbstractParser<VerwerkBijhoudingsplanBericht> {

    private final ObjectParser objectParser;

    /**
     * Maakt een nieuw VerwerkBijhoudingsplanBerichtParser object.
     */
    public VerwerkBijhoudingsplanBerichtParser() {
        objectParser = new ObjectParser(VerwerkBijhoudingsplanBerichtImpl.class);
    }

    @Override
    protected void verwerkStartElement(final ParserContext context) throws ParseException {
        objectParser.verwerkStartElement(context);
    }

    @Override
    protected VerwerkBijhoudingsplanBericht verwerkEindeElement(final ParserContext context) throws ParseException {
        return (VerwerkBijhoudingsplanBericht) objectParser.verwerkEindeElement(context);
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
