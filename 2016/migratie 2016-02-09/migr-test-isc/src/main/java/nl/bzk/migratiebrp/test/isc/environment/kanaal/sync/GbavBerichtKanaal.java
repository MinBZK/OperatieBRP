/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Kanaal om een GBAV leveringsbericht in de GBAV database op te nemen.
 */
public class GbavBerichtKanaal extends LazyLoadingKanaal {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public GbavBerichtKanaal() {
        super(new Worker(), new Configuration("classpath:configuratie.xml", "classpath:infra-db-gbav.xml", "classpath:infra-gbav.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        @Inject
        private GbavHelper gbavHelper;

        @Inject
        private GbavRepository gbavRepository;

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return "gbavBericht";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            LOG.info("Verwerk bericht: {}", bericht.getInhoud());
            final Integer cyclusActiviteitId = gbavHelper.getCyclusActiviteitIdObvReferentiePersoon(bericht.getCorrelatieReferentie());
            if (cyclusActiviteitId == null) {
                throw new KanaalException("Kan gecorreleerde persoon niet vinden om spontaan cyclus aan te koppelen");
            }

            try {
                Integer spontaanActiviteitId =
                        gbavRepository.findActiviteit(
                            cyclusActiviteitId,
                            GbavRepository.ACTIVITEIT_TYPE_SPONTAAN,
                            GbavRepository.ACTIVITEIT_SUBTYPE_SPONTAAN,
                            GbavRepository.TOESTAND_VERWERKT);

                if (spontaanActiviteitId == null) {
                    spontaanActiviteitId =
                            gbavRepository.insertActiviteit(
                                cyclusActiviteitId,
                                GbavRepository.ACTIVITEIT_TYPE_SPONTAAN,
                                GbavRepository.ACTIVITEIT_SUBTYPE_SPONTAAN,
                                GbavRepository.TOESTAND_VERWERKT);
                }

                final Integer berichtActiviteitId =
                        gbavRepository.insertActiviteit(
                            spontaanActiviteitId,
                            GbavRepository.ACTIVITEIT_TYPE_LEVERING_BERICHT,
                            GbavRepository.ACTIVITEIT_SUBTYPE_LEVERING_BERICHT,
                            GbavRepository.TOESTAND_VERWERKT);

                final Date tijdstipVerzendingOntvangst = toDate(bericht.getTestBericht().getTestBerichtProperty("tijdstipVerzendingOntvangst"));
                gbavRepository.insertBericht(bericht.getInhoud(), bericht.getOntvangendePartij(), berichtActiviteitId, tijdstipVerzendingOntvangst);
            } catch (final
                SQLException
                | ParseException e)
            {
                throw new KanaalException("Onverwacht probleem bij opvoeren bericht", e);
            }
        }

        private Date toDate(final String value) throws ParseException {
            if (value == null || "".equals(value)) {
                return null;
            } else {
                final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                return format.parse(value);
            }
        }
    }
}
