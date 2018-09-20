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
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Nationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObject;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.PropertyConverterFactory;

import org.springframework.stereotype.Component;

/**
 * Nationaliteit converter.
 */
@Component("persoonNationaliteitConverter")
public final class NationaliteitConverter extends EntityConverter {

    private static final String TYPE = "kern.his_persnation";

    @Inject
    private PropertyConverterFactory propertyConverter;

    /**
     * Default constructor.
     */
    public NationaliteitConverter() {
        super(TYPE);
    }

    @Override
    public void convert(final DataObject dataObject, final ConverterContext context) {
        if (!TYPE.equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException();
        }

        for (int dataIndex = 0; dataIndex < dataObject.getData().size(); dataIndex++) {
            final List<String> dataValues = dataObject.getData().get(dataIndex);

            PersoonNationaliteitHistorie current = null;
            Integer persoonId = null;

            for (int columnIndex = 0; columnIndex < dataObject.getHeaders().size(); columnIndex++) {
                final String header = dataObject.getHeaders().get(columnIndex);
                final String dataValue = getValue(dataValues, columnIndex);

                if (isEmpty(dataValue)) {
                    continue;
                }

                if (TYPE.equals(header)) {
                    current = new PersoonNationaliteitHistorie();
                    persoonId = Integer.valueOf(dataValue);
                } else if ("nation".equals(header)) {
                    final Persoon persoon = context.getPersoon(persoonId);

                    final PersoonNationaliteit component = getOrCreateNationaliteit(persoon, dataValue);
                    component.addPersoonNationaliteitHistorie(current);
                } else {
                    setJPAColumn(context, current, header, dataValue);
                }
            }
        }

        // A-laag
        for (final Persoon persoon : context.getPersonen()) {
            for (final PersoonNationaliteit record : persoon.getPersoonNationaliteitSet()) {
                final Set<PersoonNationaliteitHistorie> historieSet = record.getPersoonNationaliteitHistorieSet();
                final HistorieStatus historieStatus = HistorieStatus.bepaalHistorieStatusVoorBrp(historieSet);
                record.setPersoonNationaliteitStatusHistorie(historieStatus);

                if (historieStatus == HistorieStatus.A) {
                    vulActueelVanuit(record, bepaalActueleHistorieRecord(historieSet));
                }
            }
        }
    }

    private PersoonNationaliteit getOrCreateNationaliteit(final Persoon persoon, final String dataValue) {
        final Nationaliteit nat = propertyConverter.convert(Nationaliteit.class, dataValue);

        if (persoon.getPersoonNationaliteitSet() != null) {
            for (final PersoonNationaliteit nationaliteit : persoon.getPersoonNationaliteitSet()) {
                if (nationaliteit.getNationaliteit().equals(nat)) {
                    return nationaliteit;
                }
            }
        }

        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit();
        nationaliteit.setNationaliteit(nat);
        persoon.addPersoonNationaliteit(nationaliteit);

        return nationaliteit;
    }
}
