/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de VerhuizingUitvoerder.
 */
@RunWith(MockitoJUnitRunner.class)
public class VerhuizingUitvoerderTest {

    private VerhuizingUitvoerder   verhuizingUitvoerder;

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    @Before
    public void init() {
        verhuizingUitvoerder = new VerhuizingUitvoerder();
        ReflectionTestUtils.setField(verhuizingUitvoerder, "persoonAdresRepository", persoonAdresRepository);
    }

    @Test
    public void testVoerUitZonderFouten() {
        PersoonAdres persoonAdres = new PersoonAdres();
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        adressen.add(persoonAdres);

        Persoon persoon = new Persoon();
        persoon.setAdressen(adressen);

        BRPActie actie = new BRPActie();
        Integer datumAanvangGeldigheid = new Integer(1);
        actie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        verhuizingUitvoerder.voerUit(actie);

        Mockito.verify(persoonAdresRepository, Mockito.times(1)).opslaanNieuwPersoonAdres(persoonAdres, datumAanvangGeldigheid, null);
    }

}
