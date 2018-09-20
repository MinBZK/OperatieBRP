/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl;

import java.util.HashSet;

import junit.framework.Assert;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class MR0502Test {

    private Persoon nieuweSituatie;

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    private MR0502 mr0502;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mr0502 = new MR0502();
        ReflectionTestUtils.setField(mr0502, "persoonAdresRepository", persoonAdresRepository);
        nieuweSituatie = new Persoon();
        nieuweSituatie.setAdressen(new HashSet<PersoonAdres>());
        PersoonAdres adres = new PersoonAdres();
        nieuweSituatie.getAdressen().add(adres);
    }

    @Test
    public void testErWoontNiemandOpHetAdres() {
        Mockito.when(persoonAdresRepository.isIemandIngeschrevenOpAdres(Mockito.any(PersoonAdres.class)))
                .thenReturn(false);
        Melding melding = mr0502.executeer(null, nieuweSituatie);
        Assert.assertNull(melding);
    }

    @Test
    public void testErWoontAlIemandOpHetAdres() {
        Mockito.when(persoonAdresRepository.isIemandIngeschrevenOpAdres(Mockito.any(PersoonAdres.class)))
                .thenReturn(true);
        Melding melding = mr0502.executeer(null, nieuweSituatie);
        Assert.assertNotNull(melding);
        Assert.assertEquals(MeldingCode.MR0502, melding.getCode());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, melding.getSoort());
        Assert.assertEquals("Reeds persoon ingeschreven op het adres", melding.getOmschrijving());
    }
}
