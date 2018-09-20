/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschorting;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonOpschortingGroep;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/** Unit test voor de {@link BRBY0901} bedrijfsregel. */
public class BRBY0901Test {
    private final BRBY0901 brby0901 = new BRBY0901();

    private Persoon nieuweSituatie;

    @Mock
    private Persoon oudeSituatie;

    @Before
    public void setupMocks() {
        initMocks(this);
        nieuweSituatie = new PersoonBericht();
    }

    @Test
    public void zouMeldingMoetenTerugGevenOmdatPersoonOpgeschortEnOverledenIs() {
        @SuppressWarnings("serial")
        final PersoonOpschortingGroep opschorting = new PersoonOpschortingGroep() {
            @Override
            public RedenOpschorting getRedenOpschortingBijhouding() {
                return RedenOpschorting.OVERLIJDEN;
            }
        };

        when(oudeSituatie.getOpschorting()).thenReturn(opschorting);

        List<Melding> meldings = brby0901.executeer(oudeSituatie, nieuweSituatie, null);

        assertThat(meldings.size(), is(1));
        Melding melding = meldings.get(0);
        assertThat(melding.getCode(), is(MeldingCode.BRBY0901));
    }

    @Test
    public void zouGeenMeldingenMoetenGevenOmdatNieuweSituatieNullIs() {
        List<Melding> meldings = brby0901.executeer(oudeSituatie, null, null);

        assertThat(meldings.size(), is(0));
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatPersoonNietOpgeschortIs() {
        when(oudeSituatie.getOpschorting()).thenReturn(null);

        List<Melding> meldings = brby0901.executeer(oudeSituatie, nieuweSituatie, null);
        assertThat(meldings.size(), is(0));

    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatPersoonWelOpgeschortMaarNietOverledenIs() {
        @SuppressWarnings("serial")
        final PersoonOpschortingGroep opschorting = new PersoonOpschortingGroep() {
            @Override
            public RedenOpschorting getRedenOpschortingBijhouding() {
                return RedenOpschorting.MINISTERIEEL_BESLUIT;
            }
        };

        when(oudeSituatie.getOpschorting()).thenReturn(opschorting);

        List<Melding> meldings = brby0901.executeer(oudeSituatie, nieuweSituatie, null);
        assertThat(meldings.size(), is(0));
    }
}
