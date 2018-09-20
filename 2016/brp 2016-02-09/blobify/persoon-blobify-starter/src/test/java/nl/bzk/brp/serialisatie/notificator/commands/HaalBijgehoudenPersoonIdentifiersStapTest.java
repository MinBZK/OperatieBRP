/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.commands;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.serialisatie.notificator.app.ContextParameterNames;
import nl.bzk.brp.serialisatie.notificator.exceptions.CommandException;
import nl.bzk.brp.serialisatie.notificator.repository.PersoonIdRepository;
import org.apache.commons.chain.Context;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HaalBijgehoudenPersoonIdentifiersStapTest {

    @InjectMocks
    private HaalBijgehoudenPersoonIdentifiersStap haalBijgehoudenPersoonIdentifiersStap = new HaalBijgehoudenPersoonIdentifiersStap();

    @Mock
    private PersoonIdRepository persoonIdRepository;

    @Mock
    private Context context;

    private String aantalUren = "24";

    private List<Integer> persoonIdLijst = new ArrayList<Integer>();

    @Test
    public void testExecute() throws Exception {
        when(context.get(ContextParameterNames.AANTAL_UREN_BIJGEHOUDEN_PERSONEN)).thenReturn(aantalUren);
        when(persoonIdRepository.vindBijgehoudenPersoonIds(anyInt())).thenReturn(persoonIdLijst);

        final boolean resultaat = haalBijgehoudenPersoonIdentifiersStap.execute(context);

        Mockito.verify(persoonIdRepository).vindBijgehoudenPersoonIds(anyInt());
        Mockito.verify(context).put(ContextParameterNames.PERSOON_ID_LIJST, persoonIdLijst);
        Assert.assertFalse(resultaat);
    }

    @Test(expected = CommandException.class)
    public void testExecuteMetFoutInRepository() throws Exception {
        when(persoonIdRepository.vindBijgehoudenPersoonIds(anyInt())).thenThrow(CommandException.class);

        haalBijgehoudenPersoonIdentifiersStap.execute(context);
    }

}
