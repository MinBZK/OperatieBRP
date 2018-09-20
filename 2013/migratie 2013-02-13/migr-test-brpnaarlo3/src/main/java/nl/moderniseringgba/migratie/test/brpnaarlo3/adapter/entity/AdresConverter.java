/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.entity;

import java.util.List;
import java.util.Set;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAdresHistorie;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObject;

import org.springframework.stereotype.Component;

/**
 * Adres converter.
 */
@Component
public final class AdresConverter extends EntityConverter {

    private static final String TYPE = "kern.his_persadres";

    /**
     * Default constructor.
     */
    protected AdresConverter() {
        super(TYPE);
    }

    @Override
    public void convert(final DataObject dataObject, final ConverterContext context) {
        if (!TYPE.equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException();
        }

        PersoonAdresHistorie current = null;

        for (int dataIndex = 0; dataIndex < dataObject.getData().size(); dataIndex++) {
            final List<String> dataValues = dataObject.getData().get(dataIndex);

            for (int columnIndex = 0; columnIndex < dataObject.getHeaders().size(); columnIndex++) {
                final String header = dataObject.getHeaders().get(columnIndex);
                final String dataValue = getValue(dataValues, columnIndex);

                if (TYPE.equals(header) && !isEmpty(dataValue)) {
                    current = new PersoonAdresHistorie();
                    final PersoonAdres adres = new PersoonAdres();
                    adres.addPersoonAdresHistorie(current);

                    final Persoon persoon = context.getPersoon(Integer.valueOf(dataValue));
                    persoon.addPersoonAdres(adres);

                } else {
                    setJPAColumn(context, current, header, dataValue);
                }
            }
        }

        // A-laag
        for (final Persoon persoon : context.getPersonen()) {
            for (final PersoonAdres record : persoon.getPersoonAdresSet()) {
                final Set<PersoonAdresHistorie> historieSet = record.getPersoonAdresHistorieSet();
                final HistorieStatus historieStatus = HistorieStatus.bepaalHistorieStatusVoorBrp(historieSet);
                record.setPersoonAdresStatusHistorie(historieStatus);

                if (historieStatus == HistorieStatus.A) {
                    vulActueelVanuit(record, bepaalActueleHistorieRecord(historieSet));
                }
            }
        }
    }
}
