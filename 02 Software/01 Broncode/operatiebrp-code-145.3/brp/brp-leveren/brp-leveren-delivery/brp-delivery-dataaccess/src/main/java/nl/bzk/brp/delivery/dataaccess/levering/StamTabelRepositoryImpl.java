/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.ElementWaarde;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.service.dalapi.StamTabelRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * De implementatie van de StamtabelRepository interface.
 */
@Repository
public final class StamTabelRepositoryImpl implements StamTabelRepository {

    @Inject
    private DataSource masterDataSource;

    private StamTabelRepositoryImpl() {
    }

    @Override
    public List<Map<String, Object>> vindAlleStamgegevensVoorTabel(final StamgegevenTabel stamgegevenTabel) {
        final StringBuilder sql = new StringBuilder();
        for (AttribuutElement stamgegevenAttribuut : stamgegevenTabel.getStamgegevenAttributen()) {
            final String select = String.format("%n %s ", stamgegevenAttribuut.getElement().getElementWaarde().getIdentdb());
            if (sql.length() == 0) {
                sql.append(String.format("select %s ", select));
            } else {
                sql.append(String.format(", %s", select));
            }
        }
        sql.append(String.format("from%n"));
        final ElementWaarde elementWaarde = stamgegevenTabel.getObjectElement().getElement().getElementWaarde();
        sql.append(String.format("%s.%s", elementWaarde.getIdentdbschema(), elementWaarde.getIdentdb()));
        if (Element.ELEMENT.getId() == stamgegevenTabel.getObjectElement().getId()) {
            sql.append(String.format("%n where inber = true order by volgnr asc"));
        }
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(masterDataSource);
        return jdbcTemplate.queryForList(sql.toString());
    }
}
