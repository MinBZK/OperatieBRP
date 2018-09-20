/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.entity;

import java.util.List;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegel;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObject;

import org.springframework.stereotype.Component;

/**
 * MultiRealiteitRegel converter.
 */
@Component
public final class MultirealiteitConverter extends EntityConverter {

    private static final String TYPE = "kern.multirealiteitregel";

    /**
     * Default constructor.
     */
    public MultirealiteitConverter() {
        super(TYPE);
    }

    @Override
    public void convert(final DataObject dataObject, final ConverterContext context) {
        converteer(dataObject, context);
        bepaalBidirectioneleKoppelingen(context);
    }

    private void converteer(final DataObject dataObject, final ConverterContext context) {
        if (!TYPE.equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException();
        }

        for (int dataIndex = 0; dataIndex < dataObject.getData().size(); dataIndex++) {
            final List<String> dataValues = dataObject.getData().get(dataIndex);

            MultiRealiteitRegel current = null;

            for (int columnIndex = 0; columnIndex < dataObject.getHeaders().size(); columnIndex++) {
                final String header = dataObject.getHeaders().get(columnIndex);
                final String dataValue = getValue(dataValues, columnIndex);

                if (isEmpty(dataValue)) {
                    continue;
                }

                if (TYPE.equals(header)) {
                    current = new MultiRealiteitRegel();
                    context.storeMRRegel(Integer.valueOf(dataValue), current);
                } else {
                    setJPAColumn(context, current, header, dataValue);
                }
            }
        }
    }

    private void bepaalBidirectioneleKoppelingen(final ConverterContext context) {
        // Koppelen
        for (final MultiRealiteitRegel mrRegel : context.getMRRegels()) {
            mrRegel.getGeldigVoorPersoon().addMultiRealiteitRegelGeldigVoorPersoon(mrRegel);

            if (mrRegel.getBetrokkenheid() != null) {
                mrRegel.getBetrokkenheid().addMultiRealiteitRegel(mrRegel);
            }
            if (mrRegel.getRelatie() != null) {
                mrRegel.getRelatie().addMultiRealiteitRegel(mrRegel);
            }
            if (mrRegel.getPersoon() != null) {
                mrRegel.getPersoon().addMultiRealiteitRegel(mrRegel);
            }
            if (mrRegel.getMultiRealiteitPersoon() != null) {
                mrRegel.getPersoon().addMultiRealiteitRegelMultiRealiteitPersoon(mrRegel);
            }
        }
    }
}
