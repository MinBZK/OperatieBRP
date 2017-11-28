/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBerichtImpl;
import nl.bzk.brp.bijhouding.bericht.model.BmrGroepReferentie;
import nl.bzk.brp.bijhouding.bericht.model.Element;

/**
 * Deze parser kan XML berichten van het type <code>Objecttype_Bericht_Bijhouding</code> parsen naar een
 * BijhoudingVerzoekBericht.
 */
public final class BijhoudingVerzoekBerichtParser extends AbstractParser<BijhoudingVerzoekBericht> {

    private final ObjectParser objectParser;

    /**
     * Maakt een nieuw BijhoudingVerzoekBerichtParser object.
     */
    public BijhoudingVerzoekBerichtParser() {
        objectParser = new ObjectParser(BijhoudingVerzoekBerichtImpl.class);
    }

    @Override
    protected void verwerkStartElement(final ParserContext context) throws ParseException {
        objectParser.verwerkStartElement(context);
    }

    @Override
    protected BijhoudingVerzoekBericht verwerkEindeElement(final ParserContext context) throws ParseException {
        final BijhoudingVerzoekBericht result = (BijhoudingVerzoekBericht) objectParser.verwerkEindeElement(context);
        initialiseerReferenties(result, context);
        result.setObjectSleutelIndex(context.getObjectSleutelIndex());
        return result;
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

    private void initialiseerReferenties(final BijhoudingVerzoekBericht result, final ParserContext context) {
        for (final BmrGroepReferentie groepReferentie : context.getReferenties()) {
            groepReferentie.initialiseer(context.getGroepMap());
        }
        result.setReferenties(context.getReferenties());
        for (final Element element : context.getElementen()) {
            element.setVerzoekBericht(result);
            result.registreerPostConstructAanroep(element);
        }
    }
}
