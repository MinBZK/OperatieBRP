/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import java.util.ArrayList;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.bevraging.BevragingAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenBericht;
import nl.bzk.brp.model.bevraging.bijhouding.ZoekPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.ZoekPersoonBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;


/**
 * Unit tests voor de {@link AntwoordBerichtFactoryImpl} klasse.
 */
public class AntwoordBerichtFactoryImplTest {

    private final AntwoordBerichtFactoryImpl antwoordBerichtFactory = new AntwoordBerichtFactoryImpl();

    @Test
    public final void testMaakInitieelAntwoordBerichtVoorInkomendBerichtZoekPersoon() {
        final Bericht ingaandBericht = new ZoekPersoonBericht();

        final BevragingAntwoordBericht antwoordBericht =
            antwoordBerichtFactory.maakInitieelAntwoordBerichtVoorInkomendBericht(ingaandBericht);

        assertThat(antwoordBericht, instanceOf(ZoekPersoonAntwoordBericht.class));
    }

    @Test
    public final void testMaakInitieelAntwoordBerichtVoorInkomendBerichtGeefDetailsPersoon() {
        final Bericht ingaandBericht = new GeefDetailsPersoonBericht();

        final BevragingAntwoordBericht antwoordBericht =
            antwoordBerichtFactory.maakInitieelAntwoordBerichtVoorInkomendBericht(ingaandBericht);

        assertThat(antwoordBericht, instanceOf(GeefDetailsPersoonAntwoordBericht.class));
    }

    @Test
    public final void testMaakInitieelAntwoordBerichtVoorInkomendBerichtBepaaldKandidaatVader() {
        final Bericht ingaandBericht = new BepaalKandidaatVaderBericht();

        final BevragingAntwoordBericht antwoordBericht =
            antwoordBerichtFactory.maakInitieelAntwoordBerichtVoorInkomendBericht(ingaandBericht);

        assertThat(antwoordBericht, instanceOf(BepaalKandidaatVaderAntwoordBericht.class));
    }

    @Test
    public final void testMaakInitieelAntwoordBerichtVoorInkomendBerichtGeefBewonersAdresInclBetrokkenheden() {
        final Bericht ingaandBericht = new GeefPersonenOpAdresMetBetrokkenhedenBericht();

        final BevragingAntwoordBericht antwoordBericht =
            antwoordBerichtFactory.maakInitieelAntwoordBerichtVoorInkomendBericht(ingaandBericht);

        assertThat(antwoordBericht, instanceOf(GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht.class));
    }

    @Test(expected = IllegalStateException.class)
    public final void testMaakInitieelAntwoordBerichtVoorInkomendBerichtDummyVoorCoverage() {
        final ZoekPersoonBericht ingaandBericht = new ZoekPersoonBericht();
        ingaandBericht.setSoort(new SoortBerichtAttribuut(SoortBericht.DUMMY));

        antwoordBerichtFactory.maakInitieelAntwoordBerichtVoorInkomendBericht(ingaandBericht);
    }

    @Test
    public final void testVulAntwoordBerichtAanGevondenPersonenNullOfLeeg() {
        final Bericht ingaandBericht = new ZoekPersoonBericht();

        final BevragingAntwoordBericht antwoordBericht =
            antwoordBerichtFactory.maakInitieelAntwoordBerichtVoorInkomendBericht(ingaandBericht);

        final BevragingResultaat opvragenPersoonResultaat =
            new BevragingResultaat(new ArrayList<Melding>());

        antwoordBerichtFactory.vulAntwoordBerichtAan(opvragenPersoonResultaat, new ZoekPersoonAntwoordBericht(), antwoordBericht);
        assertThat(antwoordBericht, instanceOf(ZoekPersoonAntwoordBericht.class));

        antwoordBerichtFactory.vulAntwoordBerichtAan(opvragenPersoonResultaat, new ZoekPersoonAntwoordBericht(), antwoordBericht);
        assertThat(antwoordBericht, instanceOf(ZoekPersoonAntwoordBericht.class));
    }

}
