/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-generatoren-context.xml" })
@TransactionConfiguration
@Transactional
public class OperationeelModelGeneratorTest extends AbstractJavaGeneratorTest {

    @Inject
    private OperationeelModelGenerator generator;

    @Test
    public void test() {
        generator.genereer(getGeneratorConfiguratie("operationeelmodel-genconfig.xml"));
    }

    @Override
    protected AbstractJavaGenerator getGenerator() {
        return generator;
    }
}
