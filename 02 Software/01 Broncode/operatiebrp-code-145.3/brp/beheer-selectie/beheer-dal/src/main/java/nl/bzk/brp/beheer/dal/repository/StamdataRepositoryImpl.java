/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.dal.repository;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.brp.beheer.service.dal.StamdataRepository;
import nl.bzk.brp.beheer.service.stamdata.StatischeStamdataDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Implementatie van {@link StamdataRepository}.
 */
@Component
class StamdataRepositoryImpl implements StamdataRepository {

    private final JdbcTemplate jdbcTemplate;

    @Inject
    StamdataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<StatischeStamdataDTO> getStatischeStamdata(final String tabel) {
        return jdbcTemplate.query(String.format("select id, naam from %s", tabel), new BeanPropertyRowMapper<>(StatischeStamdataDTO.class));
    }
}
