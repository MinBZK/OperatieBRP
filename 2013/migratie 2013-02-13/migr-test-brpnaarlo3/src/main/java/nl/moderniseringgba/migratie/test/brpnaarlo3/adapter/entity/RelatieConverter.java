/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.entity;

import java.util.List;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObject;

import org.springframework.stereotype.Component;

/**
 * Relatie converter.
 */
@Component
public final class RelatieConverter extends EntityConverter {

    private static final String TYPE = "kern.relatie";
    private static final String BETROKKENHEID_TYPE = "kern.betr";

    /**
     * Default constructor.
     */
    public RelatieConverter() {
        super(TYPE);
    }

    @Override
    public void convert(final DataObject dataObject, final ConverterContext context) {
        if (!TYPE.equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException();
        }

        Relatie currentRelatie = null;

        for (int dataIndex = 0; dataIndex < dataObject.getData().size(); dataIndex++) {
            final List<String> dataValues = dataObject.getData().get(dataIndex);

            Betrokkenheid currentBetr = null;

            for (int columnIndex = 0; columnIndex < dataObject.getHeaders().size(); columnIndex++) {
                final String header = dataObject.getHeaders().get(columnIndex);
                final String dataValue = getValue(dataValues, columnIndex);

                if (isEmpty(dataValue)) {
                    continue;
                }

                if (TYPE.equals(header)) {
                    currentRelatie = new Relatie();
                    context.storeRelatie(Integer.valueOf(dataValue), currentRelatie);
                } else if (BETROKKENHEID_TYPE.equals(header)) {
                    currentBetr = new Betrokkenheid();
                    context.storeBetrokkenheid(Integer.valueOf(dataValue), currentBetr);

                    currentRelatie.addBetrokkenheid(currentBetr);
                } else {
                    setJPAColumn(context, currentBetr == null ? currentRelatie : currentBetr, header, dataValue);
                }
            }
        }

        // Koppelingen
        for (final Relatie relatie : context.getRelaties()) {
            for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheidSet()) {
                betrokkenheid.getPersoon().addBetrokkenheid(betrokkenheid);
                betrokkenheid.setRelatie(relatie);
            }
        }
    }
}
