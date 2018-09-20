/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.entity;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIndicatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortIndicatie;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObject;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.PropertyConverterFactory;

import org.springframework.stereotype.Component;

/**
 * Indicatie converter.
 */
@Component
public final class IndicatieConverter extends EntityConverter {

    private static final String TYPE = "kern.his_persindicatie";

    @Inject
    private PropertyConverterFactory propertyConverter;

    /**
     * Default constructor.
     */
    protected IndicatieConverter() {
        super(TYPE);
    }

    @Override
    public void convert(final DataObject dataObject, final ConverterContext context) {
        if (!TYPE.equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException();
        }

        for (int dataIndex = 0; dataIndex < dataObject.getData().size(); dataIndex++) {
            final List<String> dataValues = dataObject.getData().get(dataIndex);

            PersoonIndicatieHistorie current = null;
            Integer persoonId = null;

            for (int columnIndex = 0; columnIndex < dataObject.getHeaders().size(); columnIndex++) {
                final String header = dataObject.getHeaders().get(columnIndex);
                final String dataValue = getValue(dataValues, columnIndex);

                if (isEmpty(dataValue)) {
                    continue;
                }

                if (TYPE.equals(header)) {
                    current = new PersoonIndicatieHistorie();
                    persoonId = Integer.valueOf(dataValue);
                } else if ("srt".equals(header)) {
                    final Persoon persoon = context.getPersoon(persoonId);

                    final PersoonIndicatie component = getOrCreateIndicatie(persoon, dataValue);
                    component.addPersoonIndicatieHistorie(current);
                } else {
                    setJPAColumn(context, current, header, dataValue);
                }
            }
        }

        // A-laag
        for (final Persoon persoon : context.getPersonen()) {
            for (final PersoonIndicatie record : persoon.getPersoonIndicatieSet()) {
                final Set<PersoonIndicatieHistorie> historieSet = record.getPersoonIndicatieHistorieSet();
                final HistorieStatus historieStatus = HistorieStatus.bepaalHistorieStatusVoorBrp(historieSet);
                record.setPersoonIndicatieStatusHistorie(historieStatus);

                if (historieStatus == HistorieStatus.A) {
                    vulActueelVanuit(record, bepaalActueleHistorieRecord(historieSet));
                }
            }
        }
    }

    private PersoonIndicatie getOrCreateIndicatie(final Persoon persoon, final String dataValue) {
        final SoortIndicatie soort = propertyConverter.convert(SoortIndicatie.class, dataValue);

        for (final PersoonIndicatie component : persoon.getPersoonIndicatieSet()) {
            if (component.getSoortIndicatie().equals(soort)) {
                return component;
            }
        }

        final PersoonIndicatie component = new PersoonIndicatie();
        component.setSoortIndicatie(soort);
        persoon.addPersoonIndicatie(component);

        return component;
    }
}
