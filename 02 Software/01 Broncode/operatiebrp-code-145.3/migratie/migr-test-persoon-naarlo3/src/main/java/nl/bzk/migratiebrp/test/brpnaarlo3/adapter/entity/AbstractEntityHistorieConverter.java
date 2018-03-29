/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;

/**
 * Converter voor Historische Entities.
 * @param <T> extends FormeleHistorie
 */
public abstract class AbstractEntityHistorieConverter<T extends FormeleHistorie> extends EntityConverter {

    private static final List<String> IGNORE_FIELDS_VOOR_ACTUEEL = Arrays.asList(
            "datumAanvangGeldigheid",
            "datumEindeGeldigheid",
            "datumTijdRegistratie",
            "datumTijdVerval",
            "actieInhoud",
            "actieVerval",
            "actieAanpassingGeldigheid",
            "persoon",
            "persoonAdres",
            "persoonGeslachtsnaamcomponent",
            "persoonIndicatie",
            "persoonNationaliteit",
            "persoonReisdocument",
            "persoonVoornaam",
            "relatie",
            "betrokkenheid",
            "nadereAanduidingVerval");

    /**
     * Default constructor.
     * @param type type van de converter
     */
    public AbstractEntityHistorieConverter(final String type) {
        super(type);
    }

    /**
     * Bepaalt de actuele BRP rij en vul de A-laag met de methode vulActeelVanuit.
     */
    protected abstract void vulActueelLaag();

    @Override
    protected final void maakEntity(final ConverterContext context) {
        maakHistorieEntity(context);
        vulActueelLaag();
    }

    /**
     * Maakt de historische entities aan.
     * @param context context waar de herbruikebare entities zitten zoals Persoon en Relatie
     */
    protected abstract void maakHistorieEntity(final ConverterContext context);

    private Field findJPAField(final Object object, final String name) {

        final Field[] fields = object.getClass().getDeclaredFields();

        for (final Field field : fields) {
            final String jpaName = getJPAName(field);

            if (name.equalsIgnoreCase(jpaName)) {
                return field;
            }

        }

        throw new IllegalArgumentException("JPA field '"
                + name
                + "' not found on class '"
                + object.getClass().getName()
                + "'. (converter="
                + this.getClass().getSimpleName()
                + ")");
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

    /**
     * Bepaal het actuele historie record (datumtijd verval is leeg en in geval van MaterieleHistorie is ook datum einde
     * geldigheid leeg).
     * @param set set
     * @return actuele historie record
     * @throws IllegalArgumentException als er geen actueel record is
     */
    protected final T getActueel(final Set<T> set) {
        T actueel = null;
        for (final T formHis : set) {
            final boolean isVervallen = formHis.getDatumTijdVerval() != null;

            final boolean heeftEindeGeldigheid =
                    formHis instanceof MaterieleHistorie && ((MaterieleHistorie) formHis).getDatumEindeGeldigheid() != null;

            if (!isVervallen && !heeftEindeGeldigheid) {
                if (actueel != null) {
                    throw new IllegalStateException("Er zijn meerdere actuele groepen gevonden!");
                }
                actueel = formHis;
            }
        }
        return actueel;
    }

    /**
     * Vul de JPA waarden vanuit een record naar een ander record. Velden worden gecorreleerd aan de hand van de JPA
     * veld namen.
     * @param target te vullen record
     * @param record record om waardne uit te lezen
     */
    protected final void vulActueelVanuit(final Object target, final T record) {
        if (record == null) {
            return;
        }

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
