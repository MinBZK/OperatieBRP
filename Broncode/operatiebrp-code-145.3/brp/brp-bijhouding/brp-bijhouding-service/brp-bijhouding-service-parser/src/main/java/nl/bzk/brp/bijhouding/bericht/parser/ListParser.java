/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Deze parser kan lijsten van elementen parsen.
 *
 * @param <T> het type element waarvoor een lijst wordt geparsed
 */
public final class ListParser<T> extends AbstractParser<List<T>> {

    private final String elementNaam;
    private final Map<String, Parser<T>> childParsers;

    private List<T> childElementen;

    private ListParser(final String elementNaam) {
        ValidatieHelper.controleerOpNullWaarde(elementNaam, "elementNaam");
        this.elementNaam = elementNaam;
        childParsers = new HashMap<>();
    }

    /**
     * Maakt een nieuw ListParser object.
     *
     * @param elementNaam de naam van het element dat deze parser kan verwerken, mag niet null zijn
     * @param childParser een parser die child elementen kan parsen, mag niet null zijn
     */
    public ListParser(final String elementNaam, final Parser<T> childParser) {
        this(elementNaam);
        ValidatieHelper.controleerOpNullWaarde(childParser, "childParser");
        final List<Parser<T>> childParserLijst = new ArrayList<>();
        childParserLijst.add(childParser);
        childParsers.putAll(vulMap(childParserLijst));
    }

    /**
     * Maakt een nieuw ListParser object.
     * 
     * @param elementNaam de naam van het element dat deze parser kan verwerken, mag niet null zijn
     * @param childParserLijst een lijst van parsers die child elementen kunnen parsen, mag niet null zijn
     */
    public ListParser(final String elementNaam, final List<Parser<T>> childParserLijst) {
        this(elementNaam);
        ValidatieHelper.controleerOpNullWaarde(childParserLijst, "childParserLijst");
        childParsers.putAll(vulMap(childParserLijst));
    }

    @Override
    protected void verwerkStartElement(final ParserContext context) throws ParseException {
        childElementen = new ArrayList<>();
    }

    @Override
    protected List<T> verwerkEindeElement(final ParserContext context) throws ParseException {
        return childElementen;
    }

    @Override
    protected void verwerkAttributen(final Map<String, String> attributenMap) throws ParseException {
        //No attributes
    }

    @Override
    protected void verwerkChildElement(final ParserContext context) throws ParseException {
        final String childElementNaam = context.getEventNaam();
        if (childParsers.containsKey(childElementNaam)) {
            childElementen.add(childParsers.get(childElementNaam).parse(context));
        } else {
            onverwachtElement(elementNaam, childElementNaam);
        }
    }

    @Override
    public List<String> getElementNamen() {
        return Collections.singletonList(elementNaam);
    }

    private Map<String, Parser<T>> vulMap(final List<Parser<T>> childParserLijst) {
        final Map<String, Parser<T>> result = new HashMap<>();
        for (final Parser<T> childParser : childParserLijst) {
            for (final String childElementNaam : childParser.getElementNamen()) {
                result.put(childElementNaam, childParser);
            }
        }
        return result;
    }
}
