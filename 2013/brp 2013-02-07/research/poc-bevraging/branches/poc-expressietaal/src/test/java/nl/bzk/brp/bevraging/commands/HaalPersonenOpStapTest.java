/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
public class HaalPersonenOpStapTest {

    @Inject
    @Named("haalPersonenOpStap")
    private Command bevragingStap;

    @Test
    public void shouldReturnPersoonsLijst() throws Exception {
        // given
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_THREADS, 2);
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);
        List<Integer> bsnLijst = Arrays.asList(100000006, 100000002, 100000003, 100000004, 100000005, 135867277);
        context.put(ContextParameterNames.BSNLIJST,
                bsnLijst);

        // when
        bevragingStap.execute(context);

        // then
        List<PersoonModel> personenLijst = (List<PersoonModel>) context.get(ContextParameterNames.PERSONENLIJST);
        Assert.assertThat(personenLijst.size(), Matchers.is(6));

        for(PersoonModel persoon : personenLijst){
            assertTrue(bsnLijst.contains(persoon.getIdentificatienummers().getBurgerservicenummer().getWaarde()));
        }

    }
}
