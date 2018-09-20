/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.entity;

import java.util.List;
import java.util.Set;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegelHistorie;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObject;

import org.springframework.stereotype.Component;

/**
 * MultiRealiteitRegel converter.
 */
@Component
public final class MultiRealiteitHistorieConverter extends EntityConverter {

    private static final String TYPE = "kern.his_multirealiteitregel";

    /**
     * Default constructor.
     */
    public MultiRealiteitHistorieConverter() {
        super(TYPE);
    }

    @Override
    public void convert(final DataObject dataObject, final ConverterContext context) {
        if (!TYPE.equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException();
        }

        for (int dataIndex = 0; dataIndex < dataObject.getData().size(); dataIndex++) {
            final List<String> dataValues = dataObject.getData().get(dataIndex);

            MultiRealiteitRegelHistorie current = null;

            for (int columnIndex = 0; columnIndex < dataObject.getHeaders().size(); columnIndex++) {
                final String header = dataObject.getHeaders().get(columnIndex);
                final String dataValue = getValue(dataValues, columnIndex);

                if (TYPE.equals(header) && !isEmpty(dataValue)) {
                    current = new MultiRealiteitRegelHistorie();
                    final MultiRealiteitRegel mrRegel = context.getMRRegel(Integer.valueOf(dataValue));
                    mrRegel.addHisMultirealiteitregel(current);
                } else {
                    setJPAColumn(context, current, header, dataValue);
                }
            }
        }

        // A-laag
        for (final MultiRealiteitRegel mrRegel : context.getMRRegels()) {
            final Set<MultiRealiteitRegelHistorie> historieSet = mrRegel.getHisMultirealiteitregels();
            final HistorieStatus historieStatus = HistorieStatus.bepaalHistorieStatusVoorBrp(historieSet);
            mrRegel.setMultiRealiteitRegelStatusHistorie(historieStatus);

            // Geen data om over te nemen voor MR-regel
            // if (historieStatus == HistorieStatus.A) {
            // vulActueelVanuit(mrRegel, bepaalActueleHistorieRecord(historieSet));
            // }

        }
    }
}
