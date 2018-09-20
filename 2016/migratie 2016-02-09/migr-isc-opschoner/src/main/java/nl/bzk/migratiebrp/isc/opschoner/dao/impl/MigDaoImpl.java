/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.opschoner.dao.MigDao;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * DAO voor mig.
 * 
 */
public final class MigDaoImpl implements MigDao {

    @Inject
    private JdbcTemplate template;

    @Override
    public Timestamp bepaalDatumLaatsteBerichtOntvangenVoorProces(final Long procesId) {

        final String query = "select max(tijdstip) from mig_bericht b where b.process_instance_id = ?";

        return template.queryForObject(query, Timestamp.class, procesId);
    }

    @Override
    public List<Long> selecteerGerelateerdeProcessenVoorProces(final Long procesId) {

        final String query =
                "select process_instance_id_een from mig_proces_relatie pr1 "
                        + "where pr1.process_instance_id_twee = ? union "
                        + "select process_instance_id_twee from mig_proces_relatie pr1 "
                        + "where pr1.process_instance_id_een = ? ";

        return template.queryForList(query, Long.class, procesId, procesId);

    }

    @Override
    public void verwijderBerichtenVanProces(final Long procesId) {
        final String query = "delete from mig_bericht where process_instance_id = ?";
        template.update(query, procesId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.isc.opschoner.dao.MigDao#verwijderGerelateerdProcesVerwijzingVoorProces(java.lang .Long,
     * java.lang.Long)
     */
    @Override
    public void verwijderGerelateerdProcesVerwijzingVoorProces(final Long procesId, final Long gerelateerdProcesId) {
        final String query =
                "delete from mig_proces_relatie pr where "
                        + "(pr.process_instance_id_een = ? and pr.process_instance_id_twee = ?) "
                        + "or (pr.process_instance_id_twee = ? and pr.process_instance_id_een = ?)";

        template.update(query, procesId, gerelateerdProcesId, gerelateerdProcesId, procesId);
    }
}
