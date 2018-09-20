/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMultiRealiteitRegel;
import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.domain.kern.HisMultirealiteitregel;
import nl.bzk.brp.testdatageneratie.domain.kern.Multirealiteitregel;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;


/**
 * Multirealiteit generator.
 */
public class MultirealiteitGenerator extends AbstractBasicGenerator {

    private static Logger    log = Logger.getLogger(MultirealiteitGenerator.class);
    private final Ids persoonIds;
    private final Ids relatieIds;
    private final Ids betrokkenheidIds;

    /**
     * Instantieert Multirealiteit generator.
     *
     * @param numberOfRecordsToProcess number of records to process
     * @param batchBlockSize batch block size
     * @param threadIndex thread index
     * @param persoonIds persoon ids
     * @param relatieIds relatie ids
     * @param betrokkenheidIds betrokkenheid ids
     */
    public MultirealiteitGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final int threadIndex,
            final Ids persoonIds, final Ids relatieIds, final Ids betrokkenheidIds)
    {
        super(numberOfRecordsToProcess, batchBlockSize, threadIndex);
        this.persoonIds = persoonIds;
        this.relatieIds = relatieIds;
        this.betrokkenheidIds = betrokkenheidIds;
    }

    @Override
    public GeneratorAdminData call() {

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@ Multirealiteit @@@@@@@@@@@@@@@@");

            for (int i = Constanten.EEN; i <= getNumberOfRecordsToProcess(); i++) {

                // TODO splitsen tussen art-testdata generator en random generator.
                Multirealiteitregel regel = getMultirealiteit();
                regel.setId(SequenceUtil.getMax(Multirealiteitregel.class.getSimpleName()).intValue());
                kernSession.save(regel);

                HisMultirealiteitregel hisRegel = new HisMultirealiteitregel();
                hisRegel.setMultirealiteitregel(regel.getId());
                hisRegel.setId(SequenceUtil.getMax(Multirealiteitregel.class.getSimpleName()).intValue());
                kernSession.save(hisRegel);

                updateCurrentCount(kernSession, i);
            }

            log.info("########## Multirealiteit ##############");

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

    /**
     * Geeft multirealiteit.
     *
     * @return multirealiteit
     */
    protected Multirealiteitregel getMultirealiteit() {
        Multirealiteitregel regel = new Multirealiteitregel();
        Long persoonId = persoonIds.selecteerId();

        if (!RandomUtil.isFractie(Constanten.TIEN)) {
            regel.setSrt((short) SoortMultiRealiteitRegel.PERSOON.ordinal());
            Long persId = persoonIds.selecteerIdBehalve(persoonId);
            regel.setPers(persId.intValue());
            regel.setMultirealiteitpers(((Long) persoonIds.selecteerIdBehalve(persoonId, persId)).intValue());
        } else if (RandomUtil.isFractie(2)) {
            regel.setSrt((short) SoortMultiRealiteitRegel.RELATIE.ordinal());
            regel.setRelatie(((Long) relatieIds.selecteerId()).intValue());
        } else {
            regel.setSrt((short) SoortMultiRealiteitRegel.BETROKKENHEID.ordinal());
            regel.setBetr(((Long) betrokkenheidIds.selecteerId()).intValue());
        }

        regel.setGeldigvoor(persoonId.intValue());
        return regel;
    }

}
