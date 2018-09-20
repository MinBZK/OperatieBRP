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
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonReisdocumentHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObject;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.PropertyConverterFactory;

import org.springframework.stereotype.Component;

/**
 * Reisdocument converter.
 */
@Component
public final class ReisdocumentConverter extends EntityConverter {

    private static final String TYPE = "kern.his_persreisdoc";

    @Inject
    private PropertyConverterFactory propertyConverter;

    /**
     * Default constructor.
     */
    public ReisdocumentConverter() {
        super(TYPE);
    }

    @Override
    public void convert(final DataObject dataObject, final ConverterContext context) {
        if (!TYPE.equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException();
        }

        for (int dataIndex = 0; dataIndex < dataObject.getData().size(); dataIndex++) {
            final List<String> dataValues = dataObject.getData().get(dataIndex);

            PersoonReisdocumentHistorie current = null;
            Integer persoonId = null;

            for (int columnIndex = 0; columnIndex < dataObject.getHeaders().size(); columnIndex++) {
                final String header = dataObject.getHeaders().get(columnIndex);
                final String dataValue = getValue(dataValues, columnIndex);

                if (isEmpty(dataValue)) {
                    continue;
                }

                if (TYPE.equals(header)) {
                    current = new PersoonReisdocumentHistorie();
                    persoonId = Integer.valueOf(dataValue);
                } else if ("srt".equals(header)) {
                    final Persoon persoon = context.getPersoon(persoonId);

                    final PersoonReisdocument component = getOrCreateReisdocument(persoon, dataValue);
                    component.addPersoonReisdocumentHistorieSet(current);
                } else {
                    setJPAColumn(context, current, header, dataValue);
                }
            }
        }

        // A-laag
        for (final Persoon persoon : context.getPersonen()) {
            for (final PersoonReisdocument record : persoon.getPersoonReisdocumentSet()) {
                final Set<PersoonReisdocumentHistorie> historieSet = record.getPersoonReisdocumentHistorieSet();
                final HistorieStatus historieStatus = HistorieStatus.bepaalHistorieStatusVoorBrp(historieSet);
                record.setPersoonReisdocumentStatusHistorie(historieStatus);

                if (historieStatus == HistorieStatus.A) {
                    vulActueelVanuit(record, bepaalActueleHistorieRecord(historieSet));
                }
            }
        }
    }

    private PersoonReisdocument getOrCreateReisdocument(final Persoon persoon, final String dataValue) {
        final SoortNederlandsReisdocument soort =
                propertyConverter.convert(SoortNederlandsReisdocument.class, dataValue);

        if (persoon.getPersoonReisdocumentSet() != null) {
            for (final PersoonReisdocument reisdoc : persoon.getPersoonReisdocumentSet()) {
                if (reisdoc.getSoortNederlandsReisdocument().equals(soort)) {
                    return reisdoc;
                }
            }
        }

        final PersoonReisdocument reisdoc = new PersoonReisdocument();
        reisdoc.setSoortNederlandsReisdocument(soort);
        persoon.addPersoonReisdocument(reisdoc);

        return reisdoc;
    }
}
