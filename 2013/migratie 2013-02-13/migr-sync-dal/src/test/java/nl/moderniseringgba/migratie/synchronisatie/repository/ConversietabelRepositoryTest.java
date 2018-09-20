/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.AdellijkeTitelPredikaat;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.Voorvoegsel;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(defaultRollback = false)
public class ConversietabelRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private ConversietabelRepository conversietabelRepository;

    @InsertBefore("ConversietabelTestData.xml")
    @Test
    public void testFindAllVoorvoegsel() {
        final List<Voorvoegsel> voorvoegsels = conversietabelRepository.findAllVoorvoegsels();
        assertNotNull(voorvoegsels);
        assertEquals(2, voorvoegsels.size());
    }

    @InsertBefore("ConversietabelTestData.xml")
    @Test
    public void testFindAllAdellijkeTitelPredikaat() {
        final List<AdellijkeTitelPredikaat> titels = conversietabelRepository.findAllAdellijkeTitelPredikaat();
        assertNotNull(titels);
        assertEquals(2, titels.size());
    }
}
