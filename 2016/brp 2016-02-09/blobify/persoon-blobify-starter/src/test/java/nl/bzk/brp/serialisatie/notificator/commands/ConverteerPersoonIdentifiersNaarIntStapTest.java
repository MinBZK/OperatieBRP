/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.commands;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.serialisatie.notificator.app.ContextParameterNames;
import nl.bzk.brp.serialisatie.notificator.exceptions.CommandException;
import nl.bzk.brp.serialisatie.notificator.repository.PersoonIdRepository;
import nl.bzk.brp.serialisatie.notificator.service.IdentifierService;
import org.apache.commons.chain.Context;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConverteerPersoonIdentifiersNaarIntStapTest {

    @InjectMocks
    private ConverteerPersoonIdentifiersNaarIntStap converteerPersoonIdentifiersNaarIntStap =
            new ConverteerPersoonIdentifiersNaarIntStap();

    @Mock
    private PersoonIdRepository persoonIdRepository;

    @Mock
    private IdentifierService identifierService;

    @Mock
    private Context context;

    private List<Integer> persoonIdLijst = new ArrayList<Integer>();

    private List<String> persoonIdLijstStrings = new ArrayList<String>();

    @Test
    public void testExecute() throws Exception {
        when(context.get(ContextParameterNames.PERSOON_ID_LIJST_STRINGS)).thenReturn(persoonIdLijstStrings);
        when(identifierService.converteerLijstString(anyList())).thenReturn(persoonIdLijst);

        final boolean resultaat = converteerPersoonIdentifiersNaarIntStap.execute(context);

        Mockito.verify(identifierService).converteerLijstString(persoonIdLijstStrings);
        Mockito.verify(context).put(ContextParameterNames.PERSOON_ID_LIJST, persoonIdLijstStrings);
        Assert.assertFalse(resultaat);
    }

    @Test(expected = CommandException.class)
    public void testExecuteMetFoutInRepository() throws Exception {
        when(identifierService.converteerLijstString(anyList())).thenThrow(Exception.class);

        converteerPersoonIdentifiersNaarIntStap.execute(context);
    }

}
