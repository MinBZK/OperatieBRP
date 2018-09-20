/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.archivering;

import javax.inject.Inject;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.ber.BerichtPersoonModel;
import org.junit.Assert;
import org.junit.Test;


public class BerichtPersoonRepositoryTest extends AbstractRepositoryTestCase {

    private static final Integer PERSOON_ID = 1;
    private static final Long BERICHT_ID_1 = 2001L;
    private static final Long BERICHT_ID_2 = 2002L;
    private static final Long BERICHT_PERSOON_ID_1 = 3001L;

    @Inject
    private BerichtPersoonRepository berichtPersoonRepository;

    @Inject
    private BerichtRepository berichtRepository;

    @Test
    public void testFindOne() {
        final BerichtPersoonModel berichtPersoon = berichtPersoonRepository.findOne(BERICHT_PERSOON_ID_1);
        Assert.assertNotNull(berichtPersoon);
        Assert.assertEquals(BERICHT_PERSOON_ID_1, berichtPersoon.getID());
        Assert.assertEquals(BERICHT_ID_1, berichtPersoon.getBericht().getID());
    }

    @Test
    public void testSave() {
        final BerichtModel bericht = berichtRepository.findOne(BERICHT_ID_2);
        final BerichtPersoonModel berichtPersoon = new BerichtPersoonModel(bericht, PERSOON_ID);

        berichtPersoonRepository.save(berichtPersoon);
        Assert.assertNotNull(berichtPersoon.getID());
        Assert.assertEquals(BERICHT_ID_2, berichtPersoon.getBericht().getID());
    }

}
