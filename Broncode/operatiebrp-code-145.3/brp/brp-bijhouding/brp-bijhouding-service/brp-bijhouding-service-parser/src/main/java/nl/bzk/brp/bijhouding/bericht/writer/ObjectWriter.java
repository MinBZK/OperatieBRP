/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.writer;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.BmrAttribuut;
import nl.bzk.brp.bijhouding.bericht.model.BmrFieldMetaInfo;
import nl.bzk.brp.bijhouding.bericht.model.BmrGroep;
import nl.bzk.brp.bijhouding.bericht.model.BmrGroepMetaInfo;

/**
 * Deze generieke writer kan objecten uit het bijhoudingsmodel schrijven naar XML mits deze geannoteerd zijn.
 */
public final class ObjectWriter extends AbstractWriter<Object> {

    private final BmrGroepMetaInfo bmrGroepMetaInfo;

    /**
     * Maakt een nieuw ObjectWriter object.
     *
     * @param elementClass the element class
     */
    public ObjectWriter(final Class<?> elementClass) {
        bmrGroepMetaInfo = BmrGroepMetaInfo.getInstance(elementClass);
    }

    @Override
    protected boolean heeftChildElementen(final Object element) {
        boolean result = false;
        for (final BmrFieldMetaInfo bmrFieldMetaInfo : bmrGroepMetaInfo.getChildElementMetaInfoLijst()) {
            final Object childElement = bmrFieldMetaInfo.invokeGetterMethod(element);
            if (childElement != null && (!(childElement instanceof List) || !((List) childElement).isEmpty())) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    protected void writeElementInhoud(final Object element, final WriterContext context) throws WriteException {
        for (final BmrFieldMetaInfo bmrFieldMetaInfo : bmrGroepMetaInfo.getChildElementMetaInfoLijst()) {
            final Object childElement = bmrFieldMetaInfo.invokeGetterMethod(element);
            final String childElementNaam;
            if (bmrFieldMetaInfo.getMogelijkeElementNamen().size() == 1) {
                childElementNaam = bmrFieldMetaInfo.getMogelijkeElementNamen().iterator().next();
            } else {
                childElementNaam = BmrGroepMetaInfo.bepaalElementNaam(childElement);
            }
            writeChildElementInhoud(childElementNaam, childElement, context);
        }
    }

    private void writeChildElementInhoud(final String elementNaam, final Object childElement, final WriterContext context) throws WriteException {
        if (childElement != null) {
            if (childElement instanceof BmrAttribuut) {
                writeBmrAttribuut(elementNaam, childElement, context);
            } else if (childElement instanceof List) {
                writeList(elementNaam, childElement, context);
            } else if (childElement instanceof BmrGroep) {
                writeBmrGroep(childElement, context);
            } else {
                throw new IllegalArgumentException(
                    String.format(
                        "Het child element '%s' van type '%s' moet een BmrAttribuut, BmrGroep of List van deze types zijn. "
                            + "Gebruik @xmlTransient als dit child element genegeerd moet worden.",
                        elementNaam,
                        childElement.getClass()));
            }
        }
    }

    private void writeBmrAttribuut(final String elementNaam, final Object attribuut, final WriterContext context) throws WriteException {
        context.writeBmrAttribuut(elementNaam, (BmrAttribuut) attribuut);
    }

    private void writeBmrGroep(final Object attribuut, final WriterContext context) throws WriteException {
        new ObjectWriter(attribuut.getClass()).write(attribuut, context);
    }

    private void writeList(final String elementNaam, final Object attribuut, final WriterContext context) throws WriteException {
        final List<Object> lijstElement = convertToObjectList((List) attribuut);
        if (!lijstElement.isEmpty() && lijstElement.get(0) instanceof BmrGroep) {
            new ListWriter<>(elementNaam).write(lijstElement, context);
        }
    }

    private List<Object> convertToObjectList(final List list) {
        final List<Object> result = new ArrayList<>();
        result.addAll(list);
        return result;
    }

    @Override
    protected String getElementNaam(final Object element) {
        return BmrGroepMetaInfo.bepaalElementNaam(element);
    }
}
