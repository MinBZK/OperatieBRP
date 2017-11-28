/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.repositories.jpa;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.repositories.StamtabelRepository;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;


/**
 * JPA implementatie van stamtabel methodes.
 * @param <T> het type entiteit voor deze repository
 * @param <I> het type van de primary key van de entiteit van deze repository
 * @param <C> het type van de key die gebruikt wordt als unieke sleutel in de cache
 */
public class StamtabelRepositoryImpl<T, I extends Serializable, C> extends BasisRepositoryImpl<T, I> implements StamtabelRepository<T, I, C> {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String GET = "get";

    private final Method[] keyFieldGetter;

    private Map<C, T> cache;

    private StamtabelRepositoryImpl<T, I, ?> bronRepository;
    private Map<C, ?> keyMapOpBron;

    /**
     * Maakt een nieuw StamtabelRepositoryImpl object.
     * @param entityType entity type
     * @param keyField de naam van het veld van de entity dat overeenkomt de de unieke sleutel van het stamgegeven
     */
    public StamtabelRepositoryImpl(final Class<T> entityType, final String... keyField) {
        this(entityType, null, keyField);
    }

    /**
     * Maakt een nieuw StamtabelRepositoryImpl object.
     * @param entityType entity type
     * @param bronRepository de repository met dezelfde stamgegevens waarnaar deze repository moet verwijzen
     * @param keyField de naam van het veld van de entity dat overeenkomt de de unieke sleutel van het stamgegeven
     */
    public StamtabelRepositoryImpl(final Class<T> entityType, final StamtabelRepositoryImpl<T, I, ?> bronRepository, final String... keyField) {
        super(entityType);
        this.bronRepository = bronRepository;
        keyFieldGetter = findGetterMethodes(keyField);
    }

    @Override
    public T findByKey(final C key) {
        if (bronRepository != null) {
            return findByKeyInBronRepository(key);
        } else {
            return findByKeyInEigenRepository(key);
        }
    }

    @Override
    public List<T> findAll() {
        if (cache == null) {
            vulCache();
        }
        return new ArrayList<>(cache.values());
    }

    @Override
    public void clear() {
        cache = null;
        LOGGER.info("Stamtabel cache leeggemaakt voor type: " + getEntityType());
    }

    private T findByKeyInBronRepository(final C key) {
        if (bronRepository.cache == null) {
            bronRepository.vulCache();
            keyMapOpBron = null;
        }
        if (keyMapOpBron == null) {
            final Map<C, Object> newkeyMapOpBron = new HashMap<>();
            for (final Map.Entry<?, T> entry : bronRepository.cache.entrySet()) {
                final Object otherKey = entry.getKey();
                final C thisKey = getKeyForEntity(entry.getValue());
                newkeyMapOpBron.put(thisKey, otherKey);
            }
            keyMapOpBron = newkeyMapOpBron;
        }
        return bronRepository.cache.get(keyMapOpBron.get(key));
    }

    private T findByKeyInEigenRepository(final C key) {
        if (cache == null) {
            vulCache();
        }
        return cache.get(key);
    }

    private void vulCache() {
        final Map<C, T> newCache = new HashMap<>();
        for (final T entry : findAllUncached()) {
            newCache.put(getKeyForEntity(entry), entry);
        }
        cache = newCache;
    }

    private List<T> findAllUncached() {
        return getEntityManager().createNamedQuery(getEntityType().getSimpleName() + ".findAll", getEntityType()).getResultList();
    }

    private C getKeyForEntity(final T entity) {
        try {
            Object result = entity;
            for (final Method method : keyFieldGetter) {
                result = method.invoke(result);
            }
            return (C) result;
        } catch (
                IllegalAccessException
                        | InvocationTargetException e) {
            throw new IllegalArgumentException("De getter methode om de sleutel te bepalen kan niet worden uitgevoerd.", e);
        }
    }

    private Method[] findGetterMethodes(final String... keyFields) {
        final Method[] result = new Method[keyFields.length];
        Class<?> entityType = getEntityType();
        for (int i = 0; i < keyFields.length; i++) {
            final String keyField = keyFields[i];
            try {
                result[i] = entityType.getDeclaredMethod(getter(keyField));
                entityType = result[i].getReturnType();
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(String.format(
                        "Er kan geen getter methode gevonden worden voor veld '%s' in class '%s'",
                        keyField,
                        entityType), e);
            }
        }
        return result;
    }

    private static String getter(final String veldNaam) {
        final String eersteLetter = veldNaam.substring(0, 1);
        final String rest = veldNaam.substring(1);
        return GET + eersteLetter.toUpperCase() + rest;
    }
}
