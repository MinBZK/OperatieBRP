/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.dataaccess.repository.alleenlezen.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.OngeleverdeAdministratieveHandelingRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.stereotype.Repository;


/**
 * De Class AdministratieveHandelingRepositoryImpl, de implementatie voor de interface
 * AdministratieveHandelingRepository. Deze klasse haalt Administratieve Handelingen op uit de data-laag.
 */
@Repository
public class OngeleverdeAdministratieveHandelingRepositoryImpl implements OngeleverdeAdministratieveHandelingRepository {
    private static final Logger LOGGER     = LoggerFactory.getLogger();
    private static final String HIBERNATE_CONNECTION_DATASOURCE = "javax.persistence.nonJtaDataSource";
    private static final int AANTAL_MINUTEN = 5;

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public final List<BigInteger> haalOnverwerkteAdministratieveHandelingenOp(
        final List<SoortAdministratieveHandeling> overslaanSoortAdministratieveHandelingen, final Integer aantal)
    {
        final List<Integer> overslaanSoortAdministratieveHandelingenIds = new ArrayList<>();

        // Query heeft vulling nodig voor zijn uitvoer (IN-operator vereist lijst met minimaal 1 waarde),
        // DUMMY waarde toegevoegd.
        overslaanSoortAdministratieveHandelingenIds.add(0);
        if (overslaanSoortAdministratieveHandelingen != null) {
            for (final SoortAdministratieveHandeling soortAdministratieveHandeling : overslaanSoortAdministratieveHandelingen) {
                // Gebruik van id (ordinal) tbv performance.
                overslaanSoortAdministratieveHandelingenIds.add(soortAdministratieveHandeling.ordinal());
            }
        }

        final String maxTijdstipVoorBezemwagen = bepaalMaxMomentVoorBezemwagen();

        final String driverClassName =
            ((BasicDataSource) em.getEntityManagerFactory().getProperties().get(HIBERNATE_CONNECTION_DATASOURCE)).getDriverClassName().toLowerCase();
        final String voorTijdstip;
        if (driverClassName.contains("postgres")) {
            voorTijdstip = String.format("AND ah.tsReg < '%s' ", maxTijdstipVoorBezemwagen);
        } else if (driverClassName.contains("hsql")) {
            voorTijdstip = String.format("AND ah.tsReg < TIMESTAMP '%s' ", maxTijdstipVoorBezemwagen);
        } else {
            LOGGER.error("Databasedriver wordt niet ondersteund: " + driverClassName);
            voorTijdstip = "";
        }

        //DELTA-699: Default sortering is oudste administratieve handeling eerst.
        //DELTA-911: Alleen administratieve handelingen op de queue plaatsen waarvoor bijgehouden personen zijn
        // vastgelegd.
        final StringBuilder queryBuilder =
            new StringBuilder()
                .append("SELECT ah.id ")
                .append("FROM kern.admhnd ah ")
                .append("WHERE EXISTS (select id ")
                .append("FROM kern.his_persafgeleidadministrati ")
                .append("WHERE admhnd = ah.id) ")
                .append("AND NOT (ah.srt IN (:overslaanSoortAdministratieveHandelingenIds)) ")
                .append("AND ah.tsLev IS NULL ")
                .append(voorTijdstip)
                .append("ORDER BY ah.tsReg ASC, ah.id ASC ");
        if (aantal != null) {
            queryBuilder.append("LIMIT ").append(aantal);
        }
        queryBuilder.append(";");

        return em.createNativeQuery(queryBuilder.toString())
            .setParameter("overslaanSoortAdministratieveHandelingenIds", overslaanSoortAdministratieveHandelingenIds).getResultList();
    }

    private String bepaalMaxMomentVoorBezemwagen() {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, -AANTAL_MINUTEN);
        final Date maxTijdstipVoorBezemwagen = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(maxTijdstipVoorBezemwagen);
    }
}
