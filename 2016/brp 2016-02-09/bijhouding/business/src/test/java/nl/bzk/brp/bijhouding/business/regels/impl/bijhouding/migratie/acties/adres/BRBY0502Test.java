/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Unit test voor de {@link nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres.BRBY0502} klasse en bijbehorende bedrijfsregel.
 */
public class BRBY0502Test {

    @InjectMocks
    private final BRBY0502 brby0502 = new BRBY0502();

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    private final PersoonBericht nieuweSituatie = new PersoonBericht();

    private final PersoonAdresBericht adres = new PersoonAdresBericht();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        // Nieuwe situatie met een adres
        nieuweSituatie.setAdressen(new ArrayList<PersoonAdresBericht>());
        nieuweSituatie.getAdressen().add(adres);
    }

    @Test
    public void testErWoontNiemandOpHetAdres() {
        Mockito.when(persoonAdresRepository.isIemandIngeschrevenOpAdres(Matchers.any(PersoonAdresModel.class)))
                .thenReturn(false);

        final List<BerichtEntiteit> fouteObjecten = brby0502.voerRegelUit(null, nieuweSituatie, null, null);

        Assert.assertEquals(0, fouteObjecten.size());
    }

    @Test
    public void testErWoontAlIemandOpHetAdres() {
        Mockito.when(persoonAdresRepository.isIemandIngeschrevenOpAdres(Matchers.any(PersoonAdresModel.class)))
                .thenReturn(true);

        final List<BerichtEntiteit> fouteObjecten = brby0502.voerRegelUit(null, nieuweSituatie, null, null);

        Assert.assertEquals(1, fouteObjecten.size());
        Assert.assertEquals(adres, fouteObjecten.get(0));
    }

    @Test
    public void testErZijnGeenAdressen() {
        // Test met null voor adressen
        nieuweSituatie.setAdressen(null);
        List<BerichtEntiteit> fouteObjecten = brby0502.voerRegelUit(null, nieuweSituatie, null, null);
        Assert.assertEquals(0, fouteObjecten.size());

        // Test met lege set aan adressen
        nieuweSituatie.setAdressen(new ArrayList<PersoonAdresBericht>());
        fouteObjecten = brby0502.voerRegelUit(null, nieuweSituatie, null, null);
        Assert.assertEquals(0, fouteObjecten.size());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals("BRBY0502", brby0502.getRegel().getCode());
    }

}
