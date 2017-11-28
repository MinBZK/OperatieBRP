/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.alaag.ALaagAfleidingsUtil;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Afleidbaar;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;

/**
 * Basis historie verwerker.
 * @param <T> entiteit type
 * @param <H> historie type
 */
public abstract class AbstractHistorieVerwerker<T extends Afleidbaar, H extends FormeleHistorieZonderVerantwoording> implements HistorieVerwerker<T> {

    private final List<Field> historieVelden;
    private final Field historieEntiteitVeld;
    private final Field entiteitHistorieVeld;

    /**
     * Constructor.
     */
    public AbstractHistorieVerwerker() {
        historieVelden = FormeleHistorieZonderVerantwoording.historieVelden(historieClass(), entiteitClass());
        historieEntiteitVeld =
                Arrays.stream(historieClass().getDeclaredFields())
                        .filter(field -> field.getType().isAssignableFrom(entiteitClass()))
                        .findFirst()
                        .orElseThrow(
                                () -> new IllegalStateException(String.format("Geen entiteit referentie veld gevonden in historie class %s", historieClass())));
        entiteitHistorieVeld =
                Arrays.stream(entiteitClass().getDeclaredFields())
                        .filter(field -> ParameterizedType.class.isAssignableFrom(field.getGenericType().getClass()))
                        .filter(field -> ((ParameterizedType) field.getGenericType()).getActualTypeArguments().length > 0)
                        .filter(field -> ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].equals(historieClass()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException(String.format("Geen historie veld gevonden in class %s", entiteitClass())));
    }

    @Override
    public final void accept(final T inputItem, final T managedItem) {
        final T managedPojo = Entiteit.convertToPojo(managedItem);
        final Set<H> historieSet = geefHistorie(managedPojo);
        final H actueel = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(historieSet);

        ALaagAfleidingsUtil.leegALaag(managedPojo);
        final H nieuweHistorie = maakHistorie(inputItem);
        if ((actueel == null && !isLeeg(inputItem)) || (actueel != null && !isHistorieInhoudelijkGelijk(nieuweHistorie, actueel))) {
            FormeleHistorieZonderVerantwoording.voegToe(nieuweHistorie, historieSet);
        }
        ALaagAfleidingsUtil.vulALaag(managedPojo);
    }

    /**
     * Definiëert de klasse van de historie.
     * @return klasse van de historie.
     */
    public abstract Class<H> historieClass();

    /**
     * Definiëert de klasse van de entiteit.
     * @return klasse van de entiteit.
     */
    public abstract Class<T> entiteitClass();

    /**
     * Vergelijk historie records inhoudelijk.
     * @param nieuweHistorie nieuwe historie
     * @param actueleRecord actuele historie
     * @return true, als de records inhoudelijk gelijk zijn
     */
    final boolean isHistorieInhoudelijkGelijk(final H nieuweHistorie, final H actueleRecord) {
        if (nieuweHistorie == null || actueleRecord == null) {
            return false;
        }

        return historieVelden.stream().map(veld -> {
            try {
                return VergelijkingUtil.isEqual(veld.get(nieuweHistorie), veld.get(actueleRecord));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(String.format("Veld: %s is niet toegankelijk via reflectie", veld.getName()), e);
            }
        }).reduce(true, (a, b) -> a && b);
    }

    /**
     * Maak een historie record (met inhoud).
     * @param item item (entiteit)
     * @return historie record, null als de historie inhoudelijk leeg zou zijn
     */
    public final H maakHistorie(final T item) {
        if (isLeeg(item)) {
            return null;
        }

        try {
            final Constructor<H> constructor = historieClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            final H instance = constructor.newInstance();
            instance.setDatumTijdRegistratie(Timestamp.valueOf(LocalDateTime.now()));

            historieEntiteitVeld.setAccessible(true);
            historieEntiteitVeld.set(instance, item);

            historieVelden.forEach(veld -> repliceerVeld(item, instance, veld));
            return instance;
        } catch (final NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new IllegalStateException(String.format("Kan geen instantie maken van class %s met default constructor", historieClass()), e);
        }
    }

    /**
     * Geeft aan of het inputItem inhoud bevat.
     * @param inputItem item (entiteit)
     * @return of de entiteit inhoud heeft of niet
     */
    public boolean isLeeg(final T inputItem) {
        return historieVelden.stream().allMatch(veld -> {
            try {
                final Field inputVeld = entiteitClass().getDeclaredField(veld.getName());
                inputVeld.setAccessible(true);
                return inputVeld.get(inputItem) == null;
            } catch (final IllegalAccessException | NoSuchFieldException e) {
                throw new IllegalStateException(String.format("Fout tijdens opvragen waarde van veld %s voor object %s", veld.getName(), inputItem), e);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private Set<H> geefHistorie(final T item) {
        try {
            entiteitHistorieVeld.setAccessible(true);
            return (Set<H>) entiteitHistorieVeld.get(item);
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(String.format("Historie veld %s is niet opvraagbaar in object %s", entiteitHistorieVeld.getName(), item), e);
        }
    }

    private void repliceerVeld(final T source, final H target, Field field) {
        try {
            final Field sourceField = entiteitClass().getDeclaredField(field.getName());
            sourceField.setAccessible(true);
            final Object value = sourceField.get(source);
            if (value != null) {
                field.set(target, value);
            }
        } catch (final ReflectiveOperationException e) {
            throw new IllegalStateException(String.format("Kan veld %s niet vinden in object %s", field.getName(), source), e);
        }
    }
}
