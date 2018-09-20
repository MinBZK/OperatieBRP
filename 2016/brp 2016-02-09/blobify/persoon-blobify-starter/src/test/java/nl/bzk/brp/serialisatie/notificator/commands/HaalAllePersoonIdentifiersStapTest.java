/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.commands;

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
public class HaalAllePersoonIdentifiersStapTest {

    @InjectMocks
    private HaalAllePersoonIdentifiersStap haalAllePersoonIdentifiersStap = new HaalAllePersoonIdentifiersStap();

    @Mock
    private PersoonIdRepository persoonIdRepository;

    @Mock
    private Context context;

    private List<Integer> persoonIdLijst = new ArrayList<Integer>();

    @Test
    public void testExecute() throws Exception {
        when(persoonIdRepository.vindAllePersoonIds()).thenReturn(persoonIdLijst);

        final boolean resultaat = haalAllePersoonIdentifiersStap.execute(context);

        Mockito.verify(persoonIdRepository).vindAllePersoonIds();
        Mockito.verify(context).put(ContextParameterNames.PERSOON_ID_LIJST, persoonIdLijst);
        Assert.assertFalse(resultaat);
    }

    @Test(expected = CommandException.class)
    public void testExecuteMetFoutInRepository() throws Exception {
        when(persoonIdRepository.vindAllePersoonIds()).thenThrow(CommandException.class);

        haalAllePersoonIdentifiersStap.execute(context);
    }

}
