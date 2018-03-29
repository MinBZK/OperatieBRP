/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EnumParser;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Enumeration based repository.
 * @param <T> enum type
 */
public class EnumReadonlyRepository<T extends Enumeratie> implements ReadonlyRepository<T, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final Class<T> enumClazz;
    private final EnumParser<T> enumParser;
    private final List<T> values = new ArrayList<>();
    private final List<String> filters = new ArrayList<>();

    /**
     * Constructor.
     * @param enumClazz enum class
     */
    protected EnumReadonlyRepository(final Class<T> enumClazz) {
        this.enumClazz = enumClazz;
        enumParser = new EnumParser<>(enumClazz);

        for (final Field declaredField : enumClazz.getDeclaredFields()) {
            filters.add(declaredField.getName());
        }

        for (final T enumValue : enumClazz.getEnumConstants()) {
            values.add(enumValue);
        }
    }

    @Override
    public final T findOne(final Integer id) {
        try {
            return enumParser.parseId(id);
        } catch (final IllegalArgumentException e) {
            LOG.error("Enumwaarde met id {} niet gevonden. {}", id, e);
            return null;
        }
    }

    @Override
    public final T getOne(final Integer id) {
        return findOne(id);
    }

    @Override
    public final Page<T> findAll(final Specification<T> spec, final Pageable pageable) {
        final int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
        if (fromIndex >= values.size()) {
            return new PageImpl<>(Collections.<T>emptyList(), pageable, values.size());
        }
        int toIndex = pageable.getPageNumber() * pageable.getPageSize() + pageable.getPageSize();
        if (toIndex > values.size()) {
            toIndex = values.size();
        }

        return new PageImpl<>(values.subList(fromIndex, toIndex), pageable, values.size());
    }

    /**
     * Geef de enumeratie klasse.
     * @return De enumeratie klasse
     */
    public final Class<T> getEnumClazz() {
        return enumClazz;
    }

    /**
     * Geef de lijst met waarden van de enumeratie.
     * @return De lijst met waarden
     */
    public final List<T> getValues() {
        return values;
    }

}
