/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Enumeration based repository.
 *
 * @param <T> enum type
 */
public class EnumReadonlyRepository<T extends Enum<?>> implements ReadonlyRepository<T, Integer> {

    private final List<T> valueByOrdinal = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param enumClazz enum class
     */
    protected EnumReadonlyRepository(final Class<T> enumClazz) {
        for (final T enumValue : enumClazz.getEnumConstants()) {
            valueByOrdinal.add(enumValue);
        }
    }

    @Override
    public final T findOne(final Integer id) {
        if (id < 1 || id >= valueByOrdinal.size()) {
            return null;
        } else {
            return valueByOrdinal.get(id);
        }
    }

    @Override
    public final T getOne(final Integer id) {
        return findOne(id);
    }

    @Override
    public final Page<T> findAll(final Specification<T> spec, final Pageable pageable) {
        final int fromIndex = pageable.getPageNumber() * pageable.getPageSize() + 1;
        if (fromIndex >= valueByOrdinal.size()) {
            return new PageImpl<T>(Collections.<T>emptyList(), pageable, valueByOrdinal.size() - 1);
        }
        int toIndex = pageable.getPageNumber() * pageable.getPageSize() + pageable.getPageSize() + 1;
        if (toIndex > valueByOrdinal.size()) {
            toIndex = valueByOrdinal.size();
        }
        return new PageImpl<T>(valueByOrdinal.subList(fromIndex, toIndex), pageable, valueByOrdinal.size() - 1);
    }

}
