/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands.selecties;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Optional;
import nl.bzk.brp.bevraging.commands.BevraagInfo;
import nl.bzk.brp.bevraging.dataaccess.PersoonLeveringService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 * <code>ResultSetExtractor</code> implementatie die een blob omzet naar <code>PersoonHisVolledig</code>
 * en deze valideert tegen een <code>Expressie</code>.
 */
@Component
public class PersoonExpressieResultSetExtractor implements ResultSetExtractor<List<BevraagInfo>> {

    @Inject
    private PersoonLeveringService persoonLeveringService;

    @Override
    public List<BevraagInfo> extractData(final ResultSet rs) throws SQLException, DataAccessException {
        List<BevraagInfo> resultaten = new ArrayList<BevraagInfo>();

        while (rs.next()) {
            long startTimeMillis = System.currentTimeMillis();

            byte[] persoonData = rs.getBytes(1);
            Optional<Boolean> geleverd = persoonLeveringService.leverPersoon(persoonData);

            long duration = System.currentTimeMillis() - startTimeMillis;

            if (geleverd.isPresent()) {
                resultaten.add(new BevraagInfo("", ("OK"), duration));
            } else {
                resultaten.add(new BevraagInfo("", ("FAIL"), duration));
            }
        }

        return resultaten;
    }
}
