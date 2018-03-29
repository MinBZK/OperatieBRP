/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * Insert een persoon in de GBA-v tabellen.
 */
public final class GbavPersoonKanaal extends LazyLoadingKanaal {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public GbavPersoonKanaal() {
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
            return "gbavPersoon";
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            LOG.info("Verwerk bericht: {}", bericht.getInhoud());
            final Lo3Bericht lo3Bericht = new Lo3BerichtFactory().getBericht(bericht.getInhoud());

            final List<Lo3CategorieWaarde> categorieen;
            if (lo3Bericht instanceof Lg01Bericht) {
                categorieen = ((Lg01Bericht) lo3Bericht).getCategorieen();
            } else if (lo3Bericht instanceof La01Bericht) {
                categorieen = ((La01Bericht) lo3Bericht).getCategorieen();
            } else {
                throw new KanaalException("Onherkend bericht type: " + lo3Bericht.getBerichtType());
            }

            final Integer bijhoudingOpschortDatum =
                    toInteger(Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_07, 0, 0, Lo3ElementEnum.ELEMENT_6710));
            final String bijhoudingOpschortReden =
                    Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_07, 0, 0, Lo3ElementEnum.ELEMENT_6720);
            final Long aNummer =
                    toLong(Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_01, 0, 0, Lo3ElementEnum.ELEMENT_0110));
            final Long bsn =
                    toLong(Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_01, 0, 0, Lo3ElementEnum.ELEMENT_0120));
            final String originatorOrRecipient =
                    Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_08, 0, 0, Lo3ElementEnum.ELEMENT_0910);

            LOG.info(
                    "Bijwerken persoon: anr: {}, bsn {}, originator: {}, opschortdatum: {}, opschortreden: {}",
                    new Object[]{aNummer, bsn, originatorOrRecipient, bijhoudingOpschortDatum, bijhoudingOpschortReden,});

            try {
                final Integer plId;
                if (bericht.getCorrelatieReferentie() != null) {
                    plId = gbavHelper.getPlIdObvReferentiePersoon(bericht.getCorrelatieReferentie());
                } else {
                    plId = gbavRepository.insertLo3Pl(bijhoudingOpschortDatum, bijhoudingOpschortReden, null, aNummer, bsn);
                }

                final Integer cyclusActiviteitId =
                        gbavRepository.insertActiviteit(
                                null,
                                GbavRepository.ACTIVITEIT_TYPE_LG01_CYCLUS,
                                GbavRepository.ACTIVITEIT_SUBTYPE_LG01_CYCLUS,
                                GbavRepository.TOESTAND_VERWERKT,
                                plId,
                                bericht.getOntvangendePartij(),
                                null);
                gbavHelper.registeerCyclusActiviteitIdObvReferentiePersoon(bericht.getBerichtReferentie(), cyclusActiviteitId);

                final Integer berichtActiviteitId =
                        gbavRepository.insertActiviteit(
                                cyclusActiviteitId,
                                GbavRepository.ACTIVITEIT_TYPE_LG01_BERICHT,
                                GbavRepository.ACTIVITEIT_SUBTYPE_LG01_BERICHT,
                                GbavRepository.TOESTAND_VERWERKT,
                                plId,
                                bericht.getOntvangendePartij(),
                                null);

                final Date tijdstipVerzendingOntvangst = toDate(bericht.getTestBericht().getTestBerichtProperty("tijdstipVerzendingOntvangst"));
                gbavRepository.insertBericht(bericht.getInhoud(), originatorOrRecipient, berichtActiviteitId, tijdstipVerzendingOntvangst);
                gbavRepository.updateLo3Pl(plId, bijhoudingOpschortDatum, bijhoudingOpschortReden, berichtActiviteitId, aNummer, bsn);
                gbavHelper.registeerPlIdObvReferentiePersoon(bericht.getBerichtReferentie(), plId);

            } catch (final
            SQLException
                    | ParseException e) {
                throw new KanaalException("Kan persoonslijst niet toevoegen aan GBA-v tabellen", e);
            }

        }

        @Override
        public List<Bericht> naTestcase(final TestCasusContext testcasus) {
            gbavHelper.opschonenCyclusActiviteitIds();
            return Collections.emptyList();
        }

        private Integer toInteger(final String elementWaarde) {
            if (elementWaarde == null || elementWaarde.isEmpty()) {
                return null;
            } else {
                return Integer.valueOf(elementWaarde);
            }
        }

        private Long toLong(final String elementWaarde) {
            if (elementWaarde == null || elementWaarde.isEmpty()) {
                return null;
            } else {
                return Long.valueOf(elementWaarde);
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
