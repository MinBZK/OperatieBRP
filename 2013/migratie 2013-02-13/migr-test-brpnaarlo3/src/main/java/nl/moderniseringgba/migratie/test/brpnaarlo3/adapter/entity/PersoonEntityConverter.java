/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.entity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.FormeleHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObject;

/**
 * Basis entity om persoon entities te converteren.
 */
public abstract class PersoonEntityConverter extends EntityConverter {

    private final Class<? extends FormeleHistorie> historieClazz;
    private final String historieSetFieldName;
    private final String historieStatusFieldName;

    /**
     * Constructor.
     * 
     * @param type
     *            type zoals gehanteerd in de data objecten
     * @param historieClazz
     *            class van het historie record
     * @param historieSetFieldName
     *            property naam van de historie set
     * @param historieStatusFieldName
     *            property naam van de historie status
     */
    protected PersoonEntityConverter(
            final String type,
            final Class<? extends FormeleHistorie> historieClazz,
            final String historieSetFieldName,
            final String historieStatusFieldName) {
        super(type);
        this.historieClazz = historieClazz;
        this.historieSetFieldName = historieSetFieldName;
        this.historieStatusFieldName = historieStatusFieldName;
    }

    @Override
    public final void convert(final DataObject dataObject, final ConverterContext context) {
        converteer(dataObject, context);
        bepaalActueelInPersoon(context);
    }

    private void converteer(final DataObject dataObject, final ConverterContext context) {
        if (!getType().equals(dataObject.getHeaders().get(0))) {
            throw new IllegalArgumentException("Ongeldig type voor converter.");
        }

        FormeleHistorie current = null;

        for (int dataIndex = 0; dataIndex < dataObject.getData().size(); dataIndex++) {
            final List<String> dataValues = dataObject.getData().get(dataIndex);

            for (int columnIndex = 0; columnIndex < dataObject.getHeaders().size(); columnIndex++) {
                final String header = dataObject.getHeaders().get(columnIndex);
                final String dataValue = getValue(dataValues, columnIndex);

                if (getType().equals(header) && !isEmpty(dataValue)) {
                    final Persoon persoon = context.getPersoon(Integer.valueOf(dataValue));

                    current = nieuwInstance();
                    final Field persoonField = getField(current.getClass(), "persoon");
                    setFieldValue(current, persoonField, persoon);

                    final Field historieSetField = getField(Persoon.class, historieSetFieldName);
                    final Set<FormeleHistorie> historieSet = getFieldValue(persoon, historieSetField);
                    historieSet.add(current);

                } else {
                    setJPAColumn(context, current, header, dataValue);
                }
            }
        }
    }

    private FormeleHistorie nieuwInstance() {
        try {
            return historieClazz.newInstance();
        } catch (final InstantiationException e) {
            throw new IllegalArgumentException(e);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void bepaalActueelInPersoon(final ConverterContext context) {
        final Field historieStatusField = getField(Persoon.class, historieStatusFieldName);
        final Field historieSetField = getField(Persoon.class, historieSetFieldName);

        for (final Persoon persoon : context.getPersonen()) {
            final Set<? extends FormeleHistorie> historieSet = getFieldValue(persoon, historieSetField);

            final HistorieStatus historieStatus = HistorieStatus.bepaalHistorieStatusVoorBrp(historieSet);
            setFieldValue(persoon, historieStatusField, historieStatus);

            if (historieStatus == HistorieStatus.A) {
                vulActueelVanuit(persoon, bepaalActueleHistorieRecord(historieSet));
            }
        }
    }

    private Field getField(final Class<?> clazz, final String name) {
        Field field;
        try {
            field = clazz.getDeclaredField(name);
        } catch (final NoSuchFieldException e) {
            throw new IllegalArgumentException(e);
        }
        field.setAccessible(true);

        return field;
    }

    @SuppressWarnings("unchecked")
    private <T> T getFieldValue(final Object obj, final Field field) {
        try {
            return (T) field.get(obj);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void setFieldValue(final Object obj, final Field field, final Object value) {
        try {
            field.set(obj, value);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
