/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.AdellijkeTitelPredikaat;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.RNIDeelnemer;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit.InsertBefore;

import org.junit.Test;

public class ConversietabelRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private ConversietabelRepository conversietabelRepository;

    @InsertBefore("ConversietabelTestData.xml")
    @Test
    public void testFindAllAdellijkeTitelPredikaat() {
        final List<AdellijkeTitelPredikaat> titels = conversietabelRepository.findAllAdellijkeTitelPredikaat();
        assertNotNull(titels);
        assertEquals(2, titels.size());
    }

    @InsertBefore("ConversietabelTestData.xml")
    @Test
    public void testFindAllRNIDeelnemer() {
        final List<RNIDeelnemer> deelnemers = conversietabelRepository.findAllRNIDeelnemer();
        assertNotNull(deelnemers);
        assertEquals(3, deelnemers.size());
    }
}
