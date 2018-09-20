/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.kern.Actiebron;
import nl.bzk.brp.testdatageneratie.domain.kern.Doc;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisDoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtdoc;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.HisUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;


/**
 * Doc generator.
 */
public class DocGenerator extends AbstractBasicGenerator {

    private static Logger log = Logger.getLogger(DocGenerator.class);
    private final Ids actieIds;

    /**
     * Instantieert Doc generator.
     *
     * @param numberOfRecordsToProcess number of records to process
     * @param batchBlockSize batch block size
     * @param threadIndex thread index
     * @param actieIds actie ids
     */
    public DocGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final int threadIndex,
                        final Ids actieIds)
    {
        super(numberOfRecordsToProcess, batchBlockSize, threadIndex);
        this.actieIds = actieIds;
    }

    @Override
    public GeneratorAdminData call() {

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();
            long numberOfRecordsToProcess = getNumberOfRecordsToProcess();

            log.info("@@@@@@@@@ Docs / Bronnen @@@@@@@@@@@@@@@@");

            for (int i = Constanten.EEN; i <= numberOfRecordsToProcess; i++) {

                Doc doc = new Doc();
                Srtdoc srt = MetaRepo.get(Srtdoc.class);
                doc.setSrt(null == srt ? null : srt.getId());
                Short partij = RandomUtil.getPartijByBijhgem();
                doc.setPartij(null == partij ? null : partij);
                if (isFractie(Constanten.TWEE)) {
                    doc.setIdent(RandomStringUtils.randomAlphanumeric(Constanten.TIEN));
                }
                if (isFractie(Constanten.TWEE)) {
                    doc.setAktenr(RandomStringUtils.randomNumeric(Constanten.VIER));
                }

                // Omschrijving: "Alleen indien er geen andere vorm van identificatie mogelijk is." Bron:
                // Gegevenswoordenboek
                if (doc.getIdent() == null && doc.getAktenr() == null) {
                    doc.setOms(RandomStringUtils.randomAlphabetic(Constanten.VEERTIG));
                }

                doc.setId(SequenceUtil.getMax(Doc.class.getSimpleName()));
                kernSession.save(doc);

                HisDoc hisDoc = HisUtil.creeerHis(doc);
                hisDoc.setId(SequenceUtil.getMax(HisDoc.class.getSimpleName()).intValue());
                kernSession.save(hisDoc);

                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    HisDoc hisDocCorrectie = (HisDoc) HisUtil.creeerHisCorrectie(HisUtil.creeerHis(doc), hisDoc);
                    hisDocCorrectie.setId(SequenceUtil.getMax(HisDoc.class.getSimpleName()).intValue());
                    kernSession.save(hisDocCorrectie);
                }

                int bronAantal = !isFractie(Constanten.TIEN) ? Constanten.EEN :
                        isFractie(Constanten.TWEE) ? Constanten.NUL : Constanten.TWEE;

                Long vorigeActieId = null;
                for (int j = 1; j <= bronAantal; j++) {
                    Long actieId = actieIds.selecteerId();
                    if (!actieId.equals(vorigeActieId)) {
                        Actiebron actiebron = new Actiebron();
                        actiebron.setActie(actieId);
                        actiebron.setDoc(doc.getId());
                        //TODO: actiebron.setRechtsgrond();
                        vorigeActieId = actieId;

                        actiebron.setId(SequenceUtil.getMax(Actiebron.class.getSimpleName()));
                        kernSession.save(actiebron);
                    }
                }
                updateCurrentCount(kernSession, i);
            }
            log.info("########## Docs / Bronnen ##############");

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
