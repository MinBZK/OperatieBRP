/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;

import nl.bzk.brp.testdatageneratie.csv.dto.ArtHuishoudingDto;
import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.helper.Huwelijksboot;
import nl.bzk.brp.testdatageneratie.helper.PersoonHelper;
import nl.bzk.brp.testdatageneratie.helper.RandomBsnHelper;
import nl.bzk.brp.testdatageneratie.helper.brpbuilder.BrpPersoonBuilder;
import nl.bzk.brp.testdatageneratie.helper.synthbuilder.SynthPersoonBuilder;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Persoon generator.
 */
public class PersoonGenerator extends AbstractBasicGenerator {

    private static Logger log = Logger.getLogger(PersoonGenerator.class);

    public final RandomBsnHelper randomBSNService;
    private final Huwelijksboot huwelijksboot;
    private final PersoonHelper persHelper;
    private final ArtHuishoudingDto brpHuishouding;
    private final BrpPersoonBuilder brpBuilder;
    private final SynthPersoonBuilder synthBuilder;

    /**
     * Instantieert een Persoon generator.
     *
     * @param numberOfRecordsToProcess aantal records dat doorlopen wordt
     * @param batchBlockSize batch block size
     * @param threadIndex thread index
     * @param numberOfThreads aantal threads
     * @param rangeSize bereik grootte
     * @param huwelijksboot huwelijksboot
     * @param brpHuishouding brp huishouding
     * @param maxSesNr max ses nr
     * @param threadBsnBlockSize thread bsn block size
     */
    public PersoonGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final int threadIndex,
            final int numberOfThreads, final int rangeSize, final Huwelijksboot huwelijksboot,
            final ArtHuishoudingDto brpHuishouding, final int maxSesNr, final int threadBsnBlockSize)
    {
        super(numberOfRecordsToProcess, batchBlockSize, threadIndex);
        this.huwelijksboot = huwelijksboot;
        this.brpHuishouding = brpHuishouding;

        randomBSNService = new RandomBsnHelper(threadIndex, rangeSize, threadBsnBlockSize);
        persHelper = new PersoonHelper(randomBSNService);
        brpBuilder = new BrpPersoonBuilder(this, randomBSNService, brpHuishouding, threadIndex, numberOfThreads,
                                           maxSesNr);
        synthBuilder = new SynthPersoonBuilder(this, persHelper);
    }

    @Override
    public GeneratorAdminData call() {
        try {
            Thread.sleep(Constanten.VIJFTIG + RandomUtil.random.nextInt(Constanten.DUIZEND_VIERENTWINTIG));
        } catch (InterruptedException e) {
            log.info(e.getMessage());
            return getAdminData();
        }

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();
            long numberOfRecordsToProcess = getNumberOfRecordsToProcess();

            log.info("@@@@@@@@@@@@@@@@ Personen @@@@@@@@@@@@@@@@@@@@");

            for (int i = 1; i <= numberOfRecordsToProcess; i++) {
                Integer saved = null;
                if (isFractie(Constanten.TIEN)) {
                    saved = brpBuilder.voegPersoonToe(kernSession);
                }
                if (null == saved) {
                    synthBuilder.voegPersoonToe(kernSession);
                }

                updateCurrentCount(kernSession, i);
            }
            brpBuilder.voegRestOfHuidigSetTo(kernSession);
//            brpBuilder.dumpBrpPersDto(brpHuishouding);
            log.info("################ Personen ####################");

            kernSession.getTransaction().commit();
        } finally {
            if (kernSession != null) {
                try {
                    kernSession.close();
                } catch (RuntimeException e) {
                    log.error("", e);
                }
            }
        }
        finish();
        return getAdminData();
    }

    public Huwelijksboot getHuwelijksboot() {
        return huwelijksboot;
    }

}
