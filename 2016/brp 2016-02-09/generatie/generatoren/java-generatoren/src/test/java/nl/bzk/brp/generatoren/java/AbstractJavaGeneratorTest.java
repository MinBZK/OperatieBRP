/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import nl.bzk.brp.generatoren.algemeen.AbstractGeneratorTest;
import nl.bzk.brp.generatoren.algemeen.rapportage.RapportageUitvoerder;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Abstracte test klasse voor java generatoren testen.
 */
public abstract class AbstractJavaGeneratorTest extends AbstractGeneratorTest {

    @Before
    public void initialiseerRapportageUivoerder() {
        final RapportageUitvoerder mock = Mockito.mock(RapportageUitvoerder.class);
        ReflectionTestUtils.setField(getGenerator(), "rapportageUitvoerder", mock);
    }

    protected abstract AbstractJavaGenerator getGenerator();

}
