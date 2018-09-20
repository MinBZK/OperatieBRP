/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.jpa;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.PluralAttribute;
import nl.bzk.brp.model.basis.Attribuut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.util.Assert;

/**
 * Aangepaste query utils.
 */
public final class CustomQueryUtils {

    private static final Map<PersistentAttributeType, Class<? extends Annotation>> ASSOCIATION_TYPES;

    static {
        final Map<PersistentAttributeType, Class<? extends Annotation>> persistentAttributeTypes
                = new HashMap<PersistentAttributeType, Class<? extends Annotation>>();
        persistentAttributeTypes.put(PersistentAttributeType.ONE_TO_ONE, OneToOne.class);
        persistentAttributeTypes.put(PersistentAttributeType.ONE_TO_MANY, null);
        persistentAttributeTypes.put(PersistentAttributeType.MANY_TO_ONE, ManyToOne.class);
        persistentAttributeTypes.put(PersistentAttributeType.MANY_TO_MANY, null);
        persistentAttributeTypes.put(PersistentAttributeType.ELEMENT_COLLECTION, null);

        ASSOCIATION_TYPES = Collections.unmodifiableMap(persistentAttributeTypes);
    }

    private CustomQueryUtils() {
        // Niet instantieerbaar
    }

    /**
     * Turns the given {@link Sort} into {@link javax.persistence.criteria.Order}s.
     *
     * @param sort the {@link Sort} instance to be transformed into JPA {@link javax.persistence.criteria.Order}s.
     * @param root must not be {@literal null}.
     * @param cb must not be {@literal null}.
     * @return jpa orders
     */
    public static List<javax.persistence.criteria.Order> toOrders(final Sort sort, final Root<?> root, final CriteriaBuilder cb) {

        final List<javax.persistence.criteria.Order> orders = new ArrayList<javax.persistence.criteria.Order>();

        if (sort == null) {
            return orders;
        }

        Assert.notNull(root);
        Assert.notNull(cb);

        for (final org.springframework.data.domain.Sort.Order order : sort) {
            orders.add(toJpaOrder(order, root, cb));
        }

        return orders;
    }

    /**
     * Creates a criteria API {@link javax.persistence.criteria.Order} from the given {@link Order}.
     *
     * @param order the order to transform into a JPA {@link javax.persistence.criteria.Order}
     * @param root the {@link Root} the {@link Order} expression is based on
     * @param cb the {@link CriteriaBuilder} to build the {@link javax.persistence.criteria.Order} with
     * @return
     */
    @SuppressWarnings("unchecked")
    private static javax.persistence.criteria.Order toJpaOrder(final Order order, final Root<?> root, final CriteriaBuilder cb) {

        final PropertyPath property = PropertyPath.from(order.getProperty(), root.getJavaType());
        final Expression<?> expression = toExpressionRecursively(root, property);

        if (order.isIgnoreCase() && isSupportedType(expression)) {
            final Expression<String> lower = cb.lower((Expression<String>) expression);
            return order.isAscending() ? cb.asc(lower) : cb.desc(lower);
        } else {
            return order.isAscending() ? cb.asc(expression) : cb.desc(expression);
        }
    }

    private static boolean isSupportedType(final Expression<?> expression) {
        return String.class.equals(expression.getJavaType()) || Attribuut.class.isAssignableFrom(expression.getJavaType());
    }

    @SuppressWarnings("unchecked")
    private static <T> Expression<T> toExpressionRecursively(final From<?, ?> from, final PropertyPath property) {

        Bindable<?> propertyPathModel;
        final Bindable<?> model = from.getModel();
        final String segment = property.getSegment();

        if (model instanceof ManagedType) {
            propertyPathModel = (Bindable<?>) ((ManagedType<?>) model).getAttribute(segment);
        } else {
            propertyPathModel = from.get(segment).getModel();
        }

        if (requiresJoin(propertyPathModel, model instanceof PluralAttribute)) {
            final Join<?, ?> join = getOrCreateJoin(from, segment);
            return (Expression<T>) (property.hasNext() ? toExpressionRecursively(join, property.next()) : join);
        } else {
            final Path<Object> path = from.get(segment);
            return (Expression<T>) (property.hasNext() ? toExpressionRecursively(path, property.next()) : path);
        }
    }

    /**
     * Returns whether the given {@code propertyPathModel} requires the creation of a join. This is the case if we find a non-optional association.
     *
     * @param propertyPathModel must not be {@literal null}.
     * @param forPluralAttribute
     * @return
     */
    private static boolean requiresJoin(final Bindable<?> propertyPathModel, final boolean forPluralAttribute) {
        boolean result = false;
        if (propertyPathModel == null && forPluralAttribute) {
            result = true;
        } else if (propertyPathModel instanceof Attribute) {
            final Attribute<?, ?> attribute = (Attribute<?, ?>) propertyPathModel;

            if (ASSOCIATION_TYPES.containsKey(attribute.getPersistentAttributeType())) {
                final Class<? extends Annotation> associationAnnotation = ASSOCIATION_TYPES.get(attribute.getPersistentAttributeType());

                if (associationAnnotation == null || !(attribute.getJavaMember() instanceof AnnotatedElement)) {
                    result = true;
                } else {
                    final Annotation annotation = AnnotationUtils.getAnnotation((AnnotatedElement) attribute.getJavaMember(), associationAnnotation);
                    result = annotation == null ? true : (Boolean) AnnotationUtils.getValue(annotation, "optional");
                }
            }
        }
        return result;
    }

    private static Expression<Object> toExpressionRecursively(final Path<Object> path, final PropertyPath property) {
        final Path<Object> result = path.get(property.getSegment());
        return property.hasNext() ? toExpressionRecursively(result, property.next()) : result;
    }

    /**
     * Returns an existing join for the given attribute if one already exists or creates a new one if not.
     *
     * @param from the {@link From} to get the current joins from.
     * @param attribute the {@link Attribute} to look for in the current joins.
     * @return will never be {@literal null}.
     */
    private static Join<?, ?> getOrCreateJoin(final From<?, ?> from, final String attribute) {

        for (final Join<?, ?> join : from.getJoins()) {

            final boolean sameName = join.getAttribute().getName().equals(attribute);

            if (sameName && join.getJoinType().equals(JoinType.LEFT)) {
                return join;
            }
        }

        return from.join(attribute, JoinType.LEFT);
    }
}
