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
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObject;

import org.springframework.stereotype.Component;

/**
 * GeslachtsnaamComponent converter.
 */
@Component
public final class GeslachtsnaamComponentConverter extends EntityConverter {

    private static final String TYPE = "kern.his_persgeslnaamcomp";

    /**
     * Default constructor.
     */
    public GeslachtsnaamComponentConverter() {
        super(TYPE);
    }

    @Override
    public void convert(final DataObject dataObject, final ConverterContext context) {
        if (!TYPE.equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException();
        }

        for (int dataIndex = 0; dataIndex < dataObject.getData().size(); dataIndex++) {
            final List<String> dataValues = dataObject.getData().get(dataIndex);

            PersoonGeslachtsnaamcomponentHistorie current = null;
            Integer persoonId = null;

            for (int columnIndex = 0; columnIndex < dataObject.getHeaders().size(); columnIndex++) {
                final String header = dataObject.getHeaders().get(columnIndex);
                final String dataValue = getValue(dataValues, columnIndex);

                if (isEmpty(dataValue)) {
                    continue;
                }

                if (TYPE.equals(header)) {
                    current = new PersoonGeslachtsnaamcomponentHistorie();
                    persoonId = Integer.valueOf(dataValue);
                } else if ("volgnr".equals(header)) {
                    final Persoon persoon = context.getPersoon(persoonId);

                    final PersoonGeslachtsnaamcomponent component =
                            getOrCreateGeslachtsnaamComponent(persoon, Integer.valueOf(dataValue));
                    component.addPersoonGeslachtsnaamcomponentHistorie(current);
                } else {
                    setJPAColumn(context, current, header, dataValue);
                }
            }
        }

        // A-laag
        for (final Persoon persoon : context.getPersonen()) {
            for (final PersoonGeslachtsnaamcomponent record : persoon.getPersoonGeslachtsnaamcomponentSet()) {
                final Set<PersoonGeslachtsnaamcomponentHistorie> historieSet =
                        record.getPersoonGeslachtsnaamcomponentHistorieSet();
                final HistorieStatus historieStatus = HistorieStatus.bepaalHistorieStatusVoorBrp(historieSet);
                record.setPersoonGeslachtsnaamcomponentStatusHistorie(historieStatus);

                if (historieStatus == HistorieStatus.A) {
                    vulActueelVanuit(record, bepaalActueleHistorieRecord(historieSet));
                }
            }
        }
    }

    private PersoonGeslachtsnaamcomponent getOrCreateGeslachtsnaamComponent(
            final Persoon persoon,
            final Integer volgnummer) {
        for (final PersoonGeslachtsnaamcomponent component : persoon.getPersoonGeslachtsnaamcomponentSet()) {
            if (component.getVolgnummer().equals(volgnummer)) {
                return component;
            }
        }

        final PersoonGeslachtsnaamcomponent component = new PersoonGeslachtsnaamcomponent();
        component.setVolgnummer(volgnummer);
        persoon.addPersoonGeslachtsnaamcomponent(component);

        return component;
    }
}
