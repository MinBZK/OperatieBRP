/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.repository.jpa;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.brp.bmr.metamodel.AbstractRepositoryTest;
import nl.bzk.brp.bmr.metamodel.Laag;
import nl.bzk.brp.bmr.metamodel.SoortExport;
import nl.bzk.brp.bmr.metamodel.repository.ExportRegelRepository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class ExportRegelJpaRepositoryTest extends AbstractRepositoryTest {

    @Inject
    private ExportRegelRepository repository;

    @Before
    public void setUp() {
        Laag.setHuidigeLaag(Laag.LOGISCH_MODEL);
        SoortExport.setHuidigeSoort(SoortExport.LM);
    }

    @Test
    @Ignore("Niet iedereen, ook Jenkins niet,  heeft een Firebird installatie met geldige BMR inhoud")
    public void testGetExportIdentifier() {
        String id = repository.getExportIdentifier("Schema", "Kern", 3337);
        Assert.assertEquals("EAPK_2746EF89_896E_4416_99EA_9A37B99739F7", id);
    }

}
