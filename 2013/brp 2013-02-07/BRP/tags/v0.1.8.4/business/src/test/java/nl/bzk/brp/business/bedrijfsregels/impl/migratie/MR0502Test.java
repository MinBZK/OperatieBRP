/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class MR0502Test {

    private PersoonBericht nieuweSituatie;

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    private MR0502                 mr0502;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mr0502 = new MR0502();
        ReflectionTestUtils.setField(mr0502, "persoonAdresRepository", persoonAdresRepository);
        nieuweSituatie = new PersoonBericht();
        nieuweSituatie.setAdressen(new ArrayList<PersoonAdresBericht>());
        PersoonAdresBericht adres = new PersoonAdresBericht();
        nieuweSituatie.getAdressen().add(adres);
    }

    @Test
    public void testErWoontNiemandOpHetAdres() {
        Mockito.when(persoonAdresRepository.isIemandIngeschrevenOpAdres(Matchers.any(PersoonAdresModel.class))).thenReturn(
                false);
        List<Melding> melding = mr0502.executeer(null, nieuweSituatie, null);
        Assert.assertTrue(melding.size() == 0);
    }

    @Test
    public void testErWoontAlIemandOpHetAdres() {
        Mockito.when(persoonAdresRepository.isIemandIngeschrevenOpAdres(Matchers.any(PersoonAdresModel.class))).thenReturn(
                true);
        List<Melding> melding = mr0502.executeer(null, nieuweSituatie, null);
        Assert.assertTrue(melding.size() == 1);
        Assert.assertEquals(MeldingCode.MR0502, melding.get(0).getCode());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, melding.get(0).getSoort());
        Assert.assertEquals("Er is reeds een persoon ingeschreven op dit adres.", melding.get(0).getOmschrijving());
    }

    @Test
    public void testErZijnGeenAdressen() {
        // Test met null voor adressen
        nieuweSituatie.setAdressen(null);
        Assert.assertTrue(mr0502.executeer(null, nieuweSituatie, null).size() == 0);

        // Test met lege set aan adressen
        nieuweSituatie.setAdressen(new ArrayList<PersoonAdresBericht>());
        Assert.assertTrue(mr0502.executeer(null, nieuweSituatie, null).size() == 0);
    }
}
