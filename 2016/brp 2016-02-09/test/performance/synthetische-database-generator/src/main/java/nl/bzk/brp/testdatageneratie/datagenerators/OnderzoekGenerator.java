/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.domain.kern.Gegeveninonderzoek;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOnderzoek;
import nl.bzk.brp.testdatageneratie.domain.kern.Onderzoek;
import nl.bzk.brp.testdatageneratie.domain.kern.Personderzoek;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.HisUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;


/**
 * Onderzoek generator.
 */
public class OnderzoekGenerator extends AbstractBasicGenerator {

    private static Logger    log = Logger.getLogger(OnderzoekGenerator.class);
    private final Ids persoonIds;

    /**
     * Instantieert Onderzoek generator.
     *
     * @param numberOfRecordsToProcess number of records to process
     * @param batchBlockSize batch block size
     * @param threadIndex thread index
     * @param persoonIds persoon ids
     */
    public OnderzoekGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final int threadIndex, final Ids persoonIds)
    {
        super(numberOfRecordsToProcess, batchBlockSize, threadIndex);
        this.persoonIds = persoonIds;
    }

    @Override
    public GeneratorAdminData call() {

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@ Onderzoeken / Gegevens in onderzoek @@@@@@@@@@@@@@@@");

            for (int i = Constanten.EEN; i <= getNumberOfRecordsToProcess(); i++) {

                nl.bzk.brp.testdatageneratie.domain.bronnen.Onderzoek oz =
                        BronnenRepo.getBron(nl.bzk.brp.testdatageneratie.domain.bronnen.Onderzoek.class);
                nl.bzk.brp.testdatageneratie.domain.bronnen.OnderzoekId onderzoekBron =
                    null == oz ? null : oz.getId();

                Onderzoek onderzoek = new Onderzoek();
                onderzoek.setId(SequenceUtil.getMax(Onderzoek.class.getSimpleName()).intValue());
                onderzoek.setDataanv(null == onderzoekBron ? null : onderzoekBron.getStart());
                onderzoek.setDateinde(null == onderzoekBron ? null : onderzoekBron.getEind());

                if (isFractie(2)) {
                    onderzoek.setOms(RandomStringUtils.randomAlphabetic(Constanten.VIJFTIG));
                }

                onderzoek.setStatus((short) StatusOnderzoek.IN_UITVOERING.ordinal());

                kernSession.save(onderzoek);
                HisOnderzoek hisOnderzoekOpen = HisUtil.creeerHis(onderzoek);
                hisOnderzoekOpen.setId(SequenceUtil.getMax(HisOnderzoek.class.getSimpleName()).intValue());

                kernSession.save(hisOnderzoekOpen);
                if (onderzoek.getDateinde() != null) {
                    HisOnderzoek hisOnderzoekSluiten = HisUtil.creeerHis(onderzoek);
                    hisOnderzoekSluiten.setId(SequenceUtil.getMax(HisOnderzoek.class.getSimpleName()).intValue());
                    if (isFractie(5)) {
                        // Gebruik maken van model?
                        hisOnderzoekOpen.setStatus((short) StatusOnderzoek.GESTAAKT.ordinal());
                    } else {
                        // Gebruik maken van model?
                        hisOnderzoekOpen.setStatus((short) StatusOnderzoek.AFGESLOTEN.ordinal());
                    }

                    kernSession.save(hisOnderzoekSluiten);
                }

                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    final HisOnderzoek hisOnderzoekCorrectie =
                            (HisOnderzoek) HisUtil.creeerHisCorrectie(HisUtil.creeerHis(onderzoek), hisOnderzoekOpen);
                    hisOnderzoekCorrectie.setId(SequenceUtil.getMax(HisOnderzoek.class.getSimpleName()).intValue());
                    kernSession.save(hisOnderzoekCorrectie);
                }

                Gegeveninonderzoek gegeveninonderzoek = new Gegeveninonderzoek();
                gegeveninonderzoek.setId(SequenceUtil.getMax(Gegeveninonderzoek.class.getSimpleName()).intValue());
                gegeveninonderzoek.setOnderzoek(onderzoek.getId());
                gegeveninonderzoek.setSrtgegeven(onderzoekBron.getDbobject().getId());
                gegeveninonderzoek.setIdentgegeven(RandomUtil.random.nextLong());

                kernSession.save(gegeveninonderzoek);

                int personderzoekAantal = isFractie(Constanten.VIJF) ? Constanten.TWEE : Constanten.EEN;

                Integer vorigePersoonId = null;
                for (int j = Constanten.EEN; j <= personderzoekAantal; j++) {
                    Integer persoonId = new Integer((int) persoonIds.selecteerId());
                    if (!persoonId.equals(vorigePersoonId)) {
                        Personderzoek personderzoek = new Personderzoek();
                        personderzoek.setId(SequenceUtil.getMax(Personderzoek.class.getSimpleName()).intValue());
                        personderzoek.setOnderzoek(onderzoek.getId());
                        personderzoek.setPers(persoonId);

                        vorigePersoonId = new Integer(persoonId);

                        kernSession.save(personderzoek);
                    }
                }

                updateCurrentCount(kernSession, i);
            }

            log.info("########## Onderzoeken / Gegevens in onderzoek ##############");

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

}
