/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identificatienummers.IdentificatienummersGroepVerwerker;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 */
public class RegistratieIdentificatienummersUitvoerderTest {

    @Test
    public void testVerzamelVerwerkingsregels() {
        ActieBericht actieBericht = Mockito.mock(ActieBericht.class);
        Mockito.when(actieBericht.getRootObject()).thenReturn(new PersoonBericht());
        final PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        RegistratieIdentificatienummersUitvoerder registratieIdnrsUitvoerder = new RegistratieIdentificatienummersUitvoerder() {
            @Override
            protected PersoonHisVolledigImpl bepaalRootObjectHisVolledig() {
                return persoonHisVolledig;
            }
        };

        registratieIdnrsUitvoerder.verzamelVerwerkingsregels();

        final List<Verwerkingsregel> verwerkingsregels =
                (List<Verwerkingsregel>) ReflectionTestUtils
                        .getField(registratieIdnrsUitvoerder, "verwerkingsregels");

        Assert.assertTrue(verwerkingsregels.size() == 1);
        Assert.assertTrue(verwerkingsregels.get(0) instanceof IdentificatienummersGroepVerwerker);
    }
}
