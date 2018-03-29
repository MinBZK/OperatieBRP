/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.opschoner.dao.ExtractieDao;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * DAO voor extractie.
 */
public final class ExtractieDaoImpl implements ExtractieDao {

    private final JdbcTemplate template;

    /**
     * Constructor.
     * @param template jdbc template
     */
    @Inject
    public ExtractieDaoImpl(final JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Long> haalProcesIdsOpBasisVanBeeindigdeProcesExtractiesOp(final Timestamp datumTot) {

        final String query =
                "select process_instance_id from mig_extractie_proces ep where ep.einddatum is not null "
                        + "AND ep.einddatum < ? AND ep.indicatie_beeindigd_geteld = true AND "
                        + "(ep.verwachte_verwijder_datum is null OR ep.verwachte_verwijder_datum < ?) limit 100";

        return template.queryForList(query, Long.class, datumTot, datumTot);
    }

    @Override
    public void updateVerwachteVerwijderDatum(final Long procesId, final Timestamp verwachteVerwijderDatum) {
        final String query = "update mig_extractie_proces set verwachte_verwijder_datum = ? where process_instance_id = ?";

        template.update(query, verwachteVerwijderDatum, procesId);
    }
}
