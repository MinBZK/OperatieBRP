/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identificatienummers;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;

import java.util.List;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BRAL0004Test {

    @Mock
    private PersoonRepository persoonRepository;

    @InjectMocks
    private final BRAL0004 bral0004 = new BRAL0004();


    @Test
    public void zouCorrecteCodeMoetenGeven() {
        Assert.assertEquals(Regel.BRAL0004, bral0004.getRegel());
    }

    @Test
    public void zouGeenMeldingMoetenGevenWantIdentificatienummersGroepIsNull() {
        final PersoonBericht persoon = new PersoonBericht();
        final List<BerichtEntiteit> objecten = bral0004.voerRegelUit(null, persoon, null, null);
        assertEquals(0, objecten.size());
        Mockito.verify(persoonRepository, Mockito.never()).isBSNAlIngebruik(isA(BurgerservicenummerAttribuut.class));
    }

    @Test
    public void zouGeenMeldingMoetenGevenWantBurgerServicenummerIsNull() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        final List<BerichtEntiteit> objecten = bral0004.voerRegelUit(null, persoon, null, null);

        assertEquals(0, objecten.size());
        Mockito.verify(persoonRepository, Mockito.never()).isBSNAlIngebruik(isA(BurgerservicenummerAttribuut.class));
    }

    @Test
    public void zouGeenMeldingMoetenGevenWantNietInDatabase() {
        final PersoonBericht persoon = new PersoonBericht();
        final PersoonIdentificatienummersGroepBericht identificatienummers =
                new PersoonIdentificatienummersGroepBericht();
        final BurgerservicenummerAttribuut bsn = new BurgerservicenummerAttribuut(123456789);
        identificatienummers.setBurgerservicenummer(bsn);
        persoon.setIdentificatienummers(identificatienummers);
        Mockito.when(persoonRepository.isBSNAlIngebruik(bsn)).thenReturn(false);

        final List<BerichtEntiteit> objecten = bral0004.voerRegelUit(null, persoon, null, null);

        assertEquals(0, objecten.size());
    }

    @Test
    public void zouMeldingMoetenGevenWantInDatabase() {
        final PersoonBericht persoon = new PersoonBericht();
        final PersoonIdentificatienummersGroepBericht identificatienummers =
                new PersoonIdentificatienummersGroepBericht();
        final BurgerservicenummerAttribuut bsn = new BurgerservicenummerAttribuut(123456789);
        identificatienummers.setBurgerservicenummer(bsn);
        persoon.setIdentificatienummers(identificatienummers);
        Mockito.when(persoonRepository.isBSNAlIngebruik(bsn)).thenReturn(true);

        final List<BerichtEntiteit> objecten = bral0004.voerRegelUit(null, persoon, null, null);

        assertEquals(1, objecten.size());
        assertEquals(persoon, objecten.get(0));
    }
}
