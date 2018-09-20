/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.jpa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 * Wrapper om voor een aangepaste count query de voor spring data jpa benodigde TypedQuery te maken.
 */
public final class CustomCountQueryWrapper implements TypedQuery<Long> {

    private final Query delegate;

    /**
     * Constructor.
     *
     * @param query de aangepaste count query. Moet resulteren in een Long compatible resultaat.
     */
    public CustomCountQueryWrapper(final Query query) {
        delegate = query;
    }

    @Override
    public int executeUpdate() {
        return delegate.executeUpdate();
    }

    @Override
    public int getMaxResults() {
        return delegate.getMaxResults();
    }

    @Override
    public int getFirstResult() {
        return delegate.getFirstResult();
    }

    @Override
    public Map<String, Object> getHints() {
        return delegate.getHints();
    }

    @Override
    public Set<Parameter<?>> getParameters() {
        return delegate.getParameters();
    }

    @Override
    public Parameter<?> getParameter(final String name) {
        return delegate.getParameter(name);
    }

    @Override
    public <T> Parameter<T> getParameter(final String name, final Class<T> type) {
        return delegate.getParameter(name, type);
    }

    @Override
    public Parameter<?> getParameter(final int position) {
        return delegate.getParameter(position);
    }

    @Override
    public <T> Parameter<T> getParameter(final int position, final Class<T> type) {
        return delegate.getParameter(position, type);
    }

    @Override
    public boolean isBound(final Parameter<?> param) {
        return delegate.isBound(param);
    }

    @Override
    public <T> T getParameterValue(final Parameter<T> param) {
        return delegate.getParameterValue(param);
    }

    @Override
    public Object getParameterValue(final String name) {
        return delegate.getParameterValue(name);
    }

    @Override
    public Object getParameterValue(final int position) {
        return delegate.getParameterValue(position);
    }

    @Override
    public FlushModeType getFlushMode() {
        return delegate.getFlushMode();
    }

    @Override
    public LockModeType getLockMode() {
        return delegate.getLockMode();
    }

    @Override
    public <T> T unwrap(final Class<T> cls) {
        return delegate.unwrap(cls);
    }

    @Override
    public List<Long> getResultList() {
        final List<?> intermediateList = delegate.getResultList();
        final List<Long> result = new ArrayList<>();
        for (final Object intermediate : intermediateList) {
            result.add(convertToLong(intermediate));
        }
        return result;
    }

    @Override
    public Long getSingleResult() {
        return convertToLong(delegate.getSingleResult());
    }

    private Long convertToLong(final Object singleResult) {
        if (singleResult instanceof Number) {
            return ((Number) singleResult).longValue();
        }
        throw new IllegalArgumentException("Kan '" + singleResult + "' niet converteren naar Long");
    }

    @Override
    public TypedQuery<Long> setMaxResults(final int maxResult) {
        delegate.setMaxResults(maxResult);
        return this;
    }

    @Override
    public TypedQuery<Long> setFirstResult(final int startPosition) {
        delegate.setFirstResult(startPosition);
        return this;
    }

    @Override
    public TypedQuery<Long> setHint(final String hintName, final Object value) {
        delegate.setHint(hintName, value);
        return this;
    }

    @Override
    public <T> TypedQuery<Long> setParameter(final Parameter<T> param, final T value) {
        delegate.setParameter(param, value);
        return this;
    }

    @Override
    public TypedQuery<Long> setParameter(final Parameter<Calendar> param, final Calendar value, final TemporalType temporalType) {
        delegate.setParameter(param, value, temporalType);
        return this;
    }

    @Override
    public TypedQuery<Long> setParameter(final Parameter<Date> param, final Date value, final TemporalType temporalType) {
        delegate.setParameter(param, value, temporalType);
        return this;
    }

    @Override
    public TypedQuery<Long> setParameter(final String name, final Object value) {
        delegate.setParameter(name, value);
        return this;
    }

    @Override
    public TypedQuery<Long> setParameter(final String name, final Calendar value, final TemporalType temporalType) {
        delegate.setParameter(name, value, temporalType);
        return this;
    }

    @Override
    public TypedQuery<Long> setParameter(final String name, final Date value, final TemporalType temporalType) {
        delegate.setParameter(name, value, temporalType);
        return this;
    }

    @Override
    public TypedQuery<Long> setParameter(final int position, final Object value) {
        delegate.setParameter(position, value);
        return this;
    }

    @Override
    public TypedQuery<Long> setParameter(final int position, final Calendar value, final TemporalType temporalType) {
        delegate.setParameter(position, value, temporalType);
        return this;
    }

    @Override
    public TypedQuery<Long> setParameter(final int position, final Date value, final TemporalType temporalType) {
        delegate.setParameter(position, value, temporalType);
        return this;
    }

    @Override
    public TypedQuery<Long> setFlushMode(final FlushModeType flushMode) {
        delegate.setFlushMode(flushMode);
        return this;
    }

    @Override
    public TypedQuery<Long> setLockMode(final LockModeType lockMode) {
        delegate.setLockMode(lockMode);
        return this;
    }

}
