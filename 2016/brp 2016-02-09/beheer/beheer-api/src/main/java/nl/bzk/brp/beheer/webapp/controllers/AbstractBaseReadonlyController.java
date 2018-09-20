/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.beheer.webapp.configuratie.jpa.PageWarning;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.PredicateBuilder;
import nl.bzk.brp.beheer.webapp.controllers.query.PredicateBuilderSpecification;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Basis implementatie readonly controller.
 *
 * @param <V> view type
 * @param <T> model type
 * @param <I> id type
 */
public abstract class AbstractBaseReadonlyController<V, T, I extends Serializable> implements ReadonlyController<V, I> {

    private static final int DEFAULT_PAGE_SIZE = 50;

    private TransactionTemplate transactionTemplate;

    private final ReadonlyRepository<T, I> repository;
    private final List<Filter<?>> filters;
    private final List<String> caseInsensitiveSorts;
    private final List<String> allowedSorts;
    private final boolean queryShouldContainParameters;

    /**
     * Constructor.
     *
     * @param repository repository
     * @param filters filters
     */
    protected AbstractBaseReadonlyController(final ReadonlyRepository<T, I> repository, final List<Filter<?>> filters) {
        this(repository, filters, null, false);
    }

    /**
     * Constructor.
     *
     * @param repository repository
     * @param filters filters
     * @param allowedSorts toegestane sorteringen (eerste voorkomen is default)
     */
    protected AbstractBaseReadonlyController(final ReadonlyRepository<T, I> repository, final List<Filter<?>> filters, final List<String> allowedSorts) {
        this(repository, filters, allowedSorts, false);
    }

    /**
     * Constructor.
     *
     * @param repository repository
     * @param filters filters
     * @param allowedSorts toegestane sorteringen (eerste voorkomen is default)
     * @param queryShouldContainParameters geef aan of er geldige parameters moeten worden gebruik om resultaten te
     *            krijgen
     */
    protected AbstractBaseReadonlyController(
        final ReadonlyRepository<T, I> repository,
        final List<Filter<?>> filters,
        final List<String> allowedSorts,
        final boolean queryShouldContainParameters)
    {
        this.repository = repository;
        this.filters = filters;
        caseInsensitiveSorts = new ArrayList<>();
        for (final Filter<?> filter : this.filters) {
            if (filter.getPredicateBuilderFactory() instanceof LikePredicateBuilderFactory) {
                caseInsensitiveSorts.add(((LikePredicateBuilderFactory) filter.getPredicateBuilderFactory()).getAttribuutNaam());
            }
        }
        this.allowedSorts = allowedSorts;
        this.queryShouldContainParameters = queryShouldContainParameters;
    }

    /**
     * Zet de transaction manager.
     *
     * @param transactionManager transaction manager
     */
    @Autowired
    public final void setTransactionManagerReadonly(final PlatformTransactionManager transactionManager) {
        final DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setReadOnly(true);
        transactionTemplate = new TransactionTemplate(transactionManager, transactionDefinition);
    }

    /**
     * Haal een specifiek item op.
     *
     * @param id id
     * @return item
     * @throws NotFoundException wanneer het item niet gevonden kan worden
     */
    @Override
    public final V get(@PathVariable("id") final I id) throws NotFoundException {
        final V result = transactionTemplate.execute(new TransactionCallback<V>() {

            @Override
            public V doInTransaction(final TransactionStatus status) {
                final T item = repository.findOne(id);
                if (item == null) {
                    return null;
                }
                return converteerNaarView(item);
            }
        });

        if (result == null) {
            throw new NotFoundException();
        }

        return result;
    }

    /**
     * Converteer model naar view.
     *
     * @param item model
     * @return view
     */
    protected abstract V converteerNaarView(final T item);

    /**
     * Haal een lijst van items op.
     *
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @Override
    public final Page<V> list(@RequestParam final Map<String, String> parameters, @PageableDefault(size = 10) final Pageable pageable) {
        return transactionTemplate.execute(new TransactionCallback<Page<V>>() {
            @Override
            public Page<V> doInTransaction(final TransactionStatus status) {
                final PredicateBuilderSpecification<T> specification = new PredicateBuilderSpecification<T>();
                for (final Filter<?> filter : filters) {
                    if (parameters.containsKey(filter.getParameterName())) {
                        final String parameterValue = parameters.get(filter.getParameterName());
                        if (parameterValue == null || "".equals(parameterValue)) {
                            continue;
                        }
                        specification.addPredicateBuilder(verwerkFilter(filter, parameterValue));
                    }
                }
                if (queryShouldContainParameters && specification.isPredicateListEmpty()) {
                    return new PageImpl<>(new ArrayList<V>());
                }
                return converteerNaarView(repository.findAll(specification, adaptPageble(pageable)));
            }
        });
    }

    private <F> PredicateBuilder verwerkFilter(final Filter<F> filter, final String parameterValue) {
        final F queryValue = filter.getValueAdapter().adaptValue(parameterValue);
        return filter.getPredicateBuilderFactory().getPredicateBuilder(queryValue);
    }

    private Page<V> converteerNaarView(final Page<T> page) {
        return new PageAdapter<V>(converteerNaarView(page.getContent()), page);
    }

    /**
     * Converteer een lijst van model objecten naar een lijst van view objecten.
     *
     * @param content lijst van model objecten
     * @return lijst van view objecten
     */
    protected abstract List<V> converteerNaarView(final List<T> content);

    /**
     * Adapt the pageable to handle defaults.
     *
     * @param pageable pageable
     * @return adapted pageable
     */
    private Pageable adaptPageble(final Pageable pageable) {
        final int page = pageable == null ? 0 : pageable.getPageNumber();
        final int size = pageable == null ? DEFAULT_PAGE_SIZE : pageable.getPageSize();

        return new PageRequest(page, size, adaptSort(pageable == null ? null : pageable.getSort()));
    }

    /**
     * Adapt the sort to handle defaults.
     *
     * @param sort sort
     * @return adapted sort
     */
    private Sort adaptSort(final Sort sort) {
        final List<Order> orders = new ArrayList<>();
        if (sort != null) {
            for (final Order order : sort) {
                if (!allowedSorts.contains(order.getProperty())) {
                    throw new IllegalArgumentException("Sortering '" + order.getProperty() + "' niet toegestaan.");
                }

                if (caseInsensitiveSorts.contains(order.getProperty())) {
                    orders.add(order.ignoreCase());
                } else {
                    orders.add(order);
                }
            }
        }

        if (orders.isEmpty() && allowedSorts != null && !allowedSorts.isEmpty()) {
            final Order order = new Order(allowedSorts.iterator().next());
            if (caseInsensitiveSorts.contains(order.getProperty())) {
                orders.add(order.ignoreCase());
            } else {
                orders.add(order);
            }
        }

        return orders.isEmpty() ? null : new Sort(orders);
    }

    /**
     * Page adapter.
     *
     * @param <T> content type.
     */
    public static final class PageAdapter<T> implements Page<T>, PageWarning {

        private final Page<?> delegate;
        private final List<T> content;

        /**
         * Constructor.
         *
         * @param content nieuwe content
         * @param page oude page
         */
        public PageAdapter(final List<T> content, final Page<?> page) {
            this.content = content;
            this.delegate = page;
        }

        @Override
        public int getNumber() {
            return delegate.getNumber();
        }

        @Override
        public int getSize() {
            return delegate.getSize();
        }

        @Override
        public int getNumberOfElements() {
            return delegate.getNumberOfElements();
        }

        @Override
        public List<T> getContent() {
            return content;
        }

        @Override
        public boolean hasContent() {
            return delegate.hasContent();
        }

        @Override
        public Sort getSort() {
            return delegate.getSort();
        }

        @Override
        public boolean isFirst() {
            return delegate.isFirst();
        }

        @Override
        public boolean isLast() {
            return delegate.isLast();
        }

        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return delegate.hasPrevious();
        }

        @Override
        public Pageable nextPageable() {
            return null;
        }

        @Override
        public Pageable previousPageable() {
            return null;
        }

        @Override
        public Iterator<T> iterator() {
            return content.iterator();
        }

        @Override
        public int getTotalPages() {
            return delegate.getTotalPages();

        }

        @Override
        public long getTotalElements() {
            return delegate.getTotalElements();
        }

        @Override
        @JsonProperty
        public String getWarning() {
            return delegate instanceof PageWarning ? ((PageWarning) delegate).getWarning() : null;
        }
    }
}
