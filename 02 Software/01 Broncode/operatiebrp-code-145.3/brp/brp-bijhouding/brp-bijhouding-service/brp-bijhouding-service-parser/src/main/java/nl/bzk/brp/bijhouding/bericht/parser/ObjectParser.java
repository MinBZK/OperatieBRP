/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.model.BmrGroep;
import nl.bzk.brp.bijhouding.bericht.model.BmrGroepMetaInfo;
import nl.bzk.brp.bijhouding.bericht.model.BooleanElement;
import nl.bzk.brp.bijhouding.bericht.model.CharacterElement;
import nl.bzk.brp.bijhouding.bericht.model.DatumElement;
import nl.bzk.brp.bijhouding.bericht.model.DatumTijdElement;
import nl.bzk.brp.bijhouding.bericht.model.IntegerElement;
import nl.bzk.brp.bijhouding.bericht.model.StringElement;

/**
 * Deze generieke parser kan XML omzetten naar objecten uit het bijhoudingsmodel mits deze geannoteerd zijn.
 */
public final class ObjectParser extends AbstractParser<Object> {

    private final Map<String, BmrGroepMetaInfo> bmrGroepMetaInfoMap;

    private String elementNaam;
    private BmrGroepMetaInfo bmrGroepMetaInfo;
    private Map<String, String> attributen;
    private Map<String, Object> childElementen;

    /**
     * Maakt een nieuw ObjectParser object.
     *
     * @param elementClass the element class
     */
    public ObjectParser(final Class<?> elementClass) {
        bmrGroepMetaInfoMap = BmrGroepMetaInfo.getInstanceMap(elementClass);
    }

    @Override
    protected void verwerkStartElement(final ParserContext context) throws ParseException {
        elementNaam = context.getEventNaam();
        bmrGroepMetaInfo = bmrGroepMetaInfoMap.get(elementNaam);
        childElementen = new LinkedHashMap<>();
        attributen = null;
    }

    @Override
    protected Object verwerkEindeElement(final ParserContext context) throws ParseException {
        return bmrGroepMetaInfo.maakObject(elementNaam, attributen, childElementen);
    }

    @Override
    protected void verwerkAttributen(final Map<String, String> attributenMap) throws ParseException {
        attributen = attributenMap;
    }

    @Override
    protected void verwerkChildElement(final ParserContext context) throws ParseException {
        final String childElementNaam = context.getEventNaam();
        if (bmrGroepMetaInfo.hasChildElement(childElementNaam)) {
            final Class<?> childElementType = bmrGroepMetaInfo.getChildElementType(childElementNaam);
            Object childElement = null;
            if (BooleanElement.class.isAssignableFrom(childElementType)) {
                childElement = context.getBooleanElement();
            } else if (CharacterElement.class.isAssignableFrom(childElementType)) {
                childElement = context.getCharacterElement();
            } else if (StringElement.class.isAssignableFrom(childElementType)) {
                childElement = context.getStringElement();
            } else if (DatumElement.class.isAssignableFrom(childElementType)) {
                childElement = context.getDatumElement();
            } else if (DatumTijdElement.class.isAssignableFrom(childElementType)) {
                childElement = context.getDatumTijdElement();
            } else if (IntegerElement.class.isAssignableFrom(childElementType)) {
                childElement = context.getIntegerElement();
            } else if (BmrGroep.class.isAssignableFrom(childElementType)) {
                childElement = new ObjectParser(childElementType).parse(context);
            } else if (List.class.isAssignableFrom(childElementType)) {
                childElement =
                        new ListParser<>(childElementNaam, new ObjectParser(bmrGroepMetaInfo.getChildListElementType(childElementNaam))).parse(context);
            }
            childElementen.put(childElementNaam, childElement);
        } else {
            onverwachtElement(elementNaam, childElementNaam);
        }
    }

    @Override
    public List<String> getElementNamen() {
        return new ArrayList<>(bmrGroepMetaInfoMap.keySet());
    }
}
