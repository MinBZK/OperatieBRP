/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;

import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.kern.Bron;
import nl.bzk.brp.testdatageneratie.domain.kern.Doc;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisDoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtdoc;
import nl.bzk.brp.testdatageneratie.utils.HisUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;


public class DocGenerator implements Callable<Boolean> {

    private static Logger log = Logger.getLogger(DocGenerator.class);
    private final int     numberOfRecordsToProcess;
    private final int     batchBlockSize;
    private final Ids     actieIds;

    public DocGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final Ids actieIds) {
        this.numberOfRecordsToProcess = numberOfRecordsToProcess;
        this.batchBlockSize = batchBlockSize;
        this.actieIds = actieIds;
    }

    @Override
    public Boolean call() {

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@ Docs / Bronnen @@@@@@@@@@@@@@@@");

            for (long i = 1; i <= numberOfRecordsToProcess; i++) {

                Doc doc = new Doc();

                doc.setSrtdoc(MetaRepo.get(Srtdoc.class));
                doc.setPartij(RandomUtil.getPartijByBijhgem());
                if (isFractie(2)) doc.setIdent(RandomStringUtils.randomAlphanumeric(10));
                if (isFractie(2)) doc.setAktenr(RandomStringUtils.randomNumeric(4));

                // Omschrijving: "Alleen indien er geen andere vorm van identificatie mogelijk is." Bron:
                // Gegevenswoordenboek
                if (doc.getIdent() == null && doc.getAktenr() == null) {
                    doc.setOms(RandomStringUtils.randomAlphabetic(40));
                }

                kernSession.save(doc);

                HisDoc hisDoc = HisUtil.creeerHis(doc);
                kernSession.save(hisDoc);

                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    HisDoc hisDocCorrectie = (HisDoc) HisUtil.creeerHisCorrectie(HisUtil.creeerHis(doc), hisDoc);
                    kernSession.save(hisDocCorrectie);
                }

                int bronAantal = !isFractie(10)? 1: isFractie(2)? 0: 2;

                Long vorigeActieId = null;
                for (int j = 1; j <= bronAantal; j++) {
                    Long actieId = actieIds.selecteerId();
                    if (!actieId.equals(vorigeActieId)) {
                        Bron bron = new Bron();
                        bron.setActie(actieId);
                        bron.setDoc(doc);
                        vorigeActieId = actieId;

                        kernSession.save(bron);
                    }
                }

                if (i % batchBlockSize == 0) {
                    log.debug(i);
                    kernSession.getTransaction().commit();
                    kernSession.clear();
                    kernSession.getTransaction().begin();
                }
            }

            log.info("########## Docs / Bronnen ##############");

            kernSession.getTransaction().commit();
        } finally {
            if (kernSession != null)
                try {
                    kernSession.close();
                } catch (RuntimeException e) {
                    log.error("", e);
                }
        }
        return true;
    }

}
