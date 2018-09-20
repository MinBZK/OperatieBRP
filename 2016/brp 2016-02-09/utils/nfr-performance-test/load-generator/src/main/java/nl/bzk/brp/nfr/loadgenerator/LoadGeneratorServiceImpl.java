/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.nfr.loadgenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.brp.blobifier.service.BlobifierService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.transaction.annotation.Transactional;

/**
 * Load generator service.
 */
public class LoadGeneratorServiceImpl implements LoadGeneratorService {

    @Inject
    private DataSource dataSource;

    @Inject
    private BlobifierService blobifierSerivice;

    @Override
    @Transactional
    public void maakBlob(final int persoonId) {
        blobifierSerivice.blobify(persoonId, false);
    }

    @Override
    public List<Handeling> geefTeVerwerkenHandelingen() {
        final List<Handeling> handelingen = new LinkedList<>();
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        template.query("select pers, admhnd from kern.his_persafgeleidadministrati where admhnd in (select id from kern.admhnd where tslev is null)",
            new RowCallbackHandler() {
                @Override
                public void processRow(final ResultSet resultSet) throws SQLException {
                    handelingen.add(new Handeling(resultSet.getInt(1), resultSet.getLong(2)));
                }
            });
        return handelingen;
    }

    //http://stackoverflow.com/questions/7872720/convert-date-from-long-time-postgres
    @Override
    public void markeerStart(final long admhnd) {
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update("update kern.admhnd set toelichtingontlening = ? where id = ? ", String.valueOf(System.currentTimeMillis()), admhnd);
    }



}
