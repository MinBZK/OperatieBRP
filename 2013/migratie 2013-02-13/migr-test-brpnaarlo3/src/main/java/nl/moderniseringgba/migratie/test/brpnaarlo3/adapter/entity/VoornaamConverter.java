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
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVoornaam;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVoornaamHistorie;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObject;

import org.springframework.stereotype.Component;

/**
 * Voornaam converter.
 */
@Component
public final class VoornaamConverter extends EntityConverter {

    private static final String TYPE = "kern.his_persvoornaam";

    /**
     * Default constructor.
     */
    public VoornaamConverter() {
        super(TYPE);
    }

    @Override
    public void convert(final DataObject dataObject, final ConverterContext context) {
        if (!TYPE.equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException();
        }

        for (int dataIndex = 0; dataIndex < dataObject.getData().size(); dataIndex++) {
            final List<String> dataValues = dataObject.getData().get(dataIndex);

            PersoonVoornaamHistorie current = null;
            Integer persoonId = null;

            for (int columnIndex = 0; columnIndex < dataObject.getHeaders().size(); columnIndex++) {
                final String header = dataObject.getHeaders().get(columnIndex);
                final String dataValue = getValue(dataValues, columnIndex);

                if (isEmpty(dataValue)) {
                    continue;
                }

                if (TYPE.equals(header)) {
                    current = new PersoonVoornaamHistorie();
                    persoonId = Integer.valueOf(dataValue);
                } else if ("volgnr".equals(header)) {
                    final Persoon persoon = context.getPersoon(persoonId);

                    final PersoonVoornaam component = getOrCreatePersoonVoornaam(persoon, Integer.valueOf(dataValue));
                    component.addPersoonVoornaamHistorie(current);
                } else {
                    setJPAColumn(context, current, header, dataValue);
                }
            }
        }

        // A-laag
        for (final Persoon persoon : context.getPersonen()) {
            for (final PersoonVoornaam record : persoon.getPersoonVoornaamSet()) {
                final Set<PersoonVoornaamHistorie> historieSet = record.getPersoonVoornaamHistorieSet();
                final HistorieStatus historieStatus = HistorieStatus.bepaalHistorieStatusVoorBrp(historieSet);
                record.setPersoonVoornaamStatusHistorie(historieStatus);

                if (historieStatus == HistorieStatus.A) {
                    vulActueelVanuit(record, bepaalActueleHistorieRecord(historieSet));
                }
            }
        }
    }

    private PersoonVoornaam getOrCreatePersoonVoornaam(final Persoon persoon, final Integer volgnummer) {
        for (final PersoonVoornaam component : persoon.getPersoonVoornaamSet()) {
            if (component.getVolgnummer().equals(volgnummer)) {
                return component;
            }
        }

        final PersoonVoornaam component = new PersoonVoornaam();
        component.setVolgnummer(volgnummer);
        persoon.addPersoonVoornaam(component);

        return component;
    }
}
