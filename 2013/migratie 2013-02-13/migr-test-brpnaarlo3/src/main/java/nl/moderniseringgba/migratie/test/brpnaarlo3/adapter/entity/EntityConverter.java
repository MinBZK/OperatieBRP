/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.entity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BRPActie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.FormeleHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MaterieleHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.ConverterContext;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.DataObjectConverter;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.PropertyConverterFactory;

/**
 * Basis converter.
 */
public abstract class EntityConverter implements DataObjectConverter {

    private static final List<String> IGNORE_FIELDS_VOOR_ACTUEEL = Arrays.asList(new String[] {
            "datumAanvangGeldigheid", "datumEindeGeldigheid", "datumTijdRegistratie", "datumTijdVerval",
            "actieInhoud", "actieVerval", "actieAanpassingGeldigheid", "persoon", "persoonAdres",
            "persoonGeslachtsnaamcomponent", "persoonIndicatie", "persoonNationaliteit", "persoonReisdocument",
            "persoonVoornaam", "relatie", "betrokkenheid", });

    private final String type;

    @Inject
    private PropertyConverterFactory propertyConverter;

    /**
     * Constructor.
     * 
     * @param type
     *            type die deze converter kan converteren
     */
    protected EntityConverter(final String type) {
        this.type = type;
    }

    @Override
    public final String getType() {
        return type;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Bepaal of een string een waarde heeft (dus niet null en niet leeg is).
     * 
     * @param value
     *            string
     * @return true als de string een waar heeft
     */
    protected final boolean isEmpty(final String value) {
        return value == null || "".equals(value);
    }

    /**
     * Null-safe get.
     * 
     * @param values
     *            list
     * @param index
     *            index
     * @return waarde of index (of null)
     */
    protected final String getValue(final List<String> values, final int index) {
        return index < values.size() ? values.get(index) : null;

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Zet een waarde van een JPA veld. Waarden worden opgezocht (Persoon, Betrokkenheid, Relatie) in de context of
     * geconverteerd met een PropertyConverter naar het juiste formaat.
     * 
     * @param context
     *            converter context
     * @param object
     *            object waarop de waarde te zetten is
     * @param header
     *            JPA-veld naam van het te zetten veld
     * @param value
     *            string waarde
     * @throws IllegalArgumentException
     *             als het veld niet geconden kan worden
     */
    protected final void setJPAColumn(
            final ConverterContext context,
            final Object object,
            final String header,
            final String value) {
        if (object == null) {
            throw new IllegalArgumentException("Object mag niet null zijn (header=" + header + ").");
        }

        try {
            final Field field = findJPAField(object, header);
            field.setAccessible(true);
            final Class<?> expectedType = field.getType();
            final Object objectValue;
            if (isEmpty(value)) {
                objectValue = null;
            } else if (BRPActie.class.equals(expectedType)) {
                objectValue = context.getActie(Integer.valueOf(value));
            } else if (Persoon.class.equals(expectedType)) {
                objectValue = context.getPersoon(Integer.valueOf(value));
            } else if (Relatie.class.equals(expectedType)) {
                objectValue = context.getRelatie(Integer.valueOf(value));
            } else if (Betrokkenheid.class.equals(expectedType)) {
                objectValue = context.getBetrokkenheid(Integer.valueOf(value));
            } else {
                objectValue = propertyConverter.convert(expectedType, value);
            }

            field.set(object, objectValue);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException("Header '" + header + "' not accessible on class '"
                    + object.getClass().getName() + "'.");
        }
    }

    private Field findJPAField(final Object object, final String name) {

        final Field[] fields = object.getClass().getDeclaredFields();

        for (final Field field : fields) {
            final String jpaName = getJPAName(field);

            if (name.equalsIgnoreCase(jpaName)) {
                return field;
            }

        }

        throw new IllegalArgumentException("JPA field '" + name + "' not found on class '"
                + object.getClass().getName() + "'. (converter=" + this.getClass().getSimpleName() + ")");
    }

    private String getJPAName(final Field field) {
        final String result;

        if (field.isAnnotationPresent(Column.class)) {
            final Column ann = field.getAnnotation(Column.class);
            if (isEmpty(ann.name())) {
                result = field.getName();
            } else {
                result = ann.name();
            }

        } else if (field.isAnnotationPresent(JoinColumn.class)) {
            final JoinColumn ann = field.getAnnotation(JoinColumn.class);
            if (isEmpty(ann.name())) {
                result = field.getName();
            } else {
                result = ann.name();
            }

        } else {
            result = field.getName();
        }

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Bepaal het actuele historie record (datumtijd verval is leeg en in geval van MaterieleHistorie is ook datum einde
     * geldigheid leeg).
     * 
     * @param set
     *            set
     * @return actuele historie record
     * @throws IllegalArgumentException
     *             als er geen actueel record is
     */
    protected final Object bepaalActueleHistorieRecord(final Set<? extends FormeleHistorie> set) {
        for (final FormeleHistorie formHis : set) {
            if (formHis instanceof MaterieleHistorie) {
                final MaterieleHistorie matHis = (MaterieleHistorie) formHis;
                if (matHis.getDatumEindeGeldigheid() == null && matHis.getDatumTijdVerval() == null) {
                    return matHis;
                }
            } else {
                if (formHis.getDatumTijdVerval() == null) {
                    return formHis;
                }
            }
        }

        throw new IllegalArgumentException("Geen actueel record");
    }

    /**
     * Vul de JPA waarden vanuit een record naar een ander record. Velden worden gecorreleerd aan de hand van de JPA
     * veld namen.
     * 
     * @param target
     *            te vullen record
     * @param record
     *            record om waardne uit te lezen
     */
    protected final void vulActueelVanuit(final Object target, final Object record) {
        // Zoek de velden uit record (die de annotatie @Column hebben) en vul die in persoon
        for (final Field field : record.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                continue;
            }

            if (!(field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(JoinColumn.class))) {
                continue;
            }

            if (IGNORE_FIELDS_VOOR_ACTUEEL.contains(field.getName())) {
                continue;
            }

            final String jpaName = getJPAName(field);

            try {
                field.setAccessible(true);
                final Object fieldValue = field.get(record);

                final Field targetField = findJPAField(target, jpaName);
                targetField.setAccessible(true);
                targetField.set(target, fieldValue);
            } catch (final IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }

        }
    }

}
