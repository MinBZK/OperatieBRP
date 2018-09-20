/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Test;

/** Unit test ten behoeve van de functionaliteit zoals geboden in de regel {@link BRAL2110}. */
public class BRAL2110Test {

    private static final PartijAttribuut   PARTIJ_AMSTERDAM   = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM;
    private static final PartijAttribuut   PARTIJ_BREDA       = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA;
    private static final GemeenteAttribuut GEMEENTE_AMSTERDAM = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM;

    private final BRAL2110 bral2110 = new BRAL2110();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRAL2110, bral2110.getRegel());
    }

    @Test
    public void testActieGemeenteOngelijkAanBronGemeente() {
        final HuwelijkGeregistreerdPartnerschapBericht nieuwSituatie = maakNieuwSituatie(GEMEENTE_AMSTERDAM);
        final ActieBericht actieBericht = maakActieBericht(PARTIJ_AMSTERDAM, PARTIJ_BREDA, PARTIJ_AMSTERDAM);

        final List<BerichtEntiteit> berichtEntiteits = bral2110.voerRegelUit(null, nieuwSituatie, actieBericht, null);

        Assert.assertEquals(1, berichtEntiteits.size());
    }

    @Test
    public void testActieGemeenteLeeg() {
        final HuwelijkGeregistreerdPartnerschapBericht nieuwSituatie = maakNieuwSituatie(GEMEENTE_AMSTERDAM);
        final ActieBericht actieBericht = maakActieBericht();
        final ActieBronBericht actieBronBericht = maakActieBronBericht(null);
        actieBericht.getBronnen().add(actieBronBericht);

        final List<BerichtEntiteit> berichtEntiteits = bral2110.voerRegelUit(null, nieuwSituatie, actieBericht, null);

        Assert.assertEquals(0, berichtEntiteits.size());
    }

    @Test
    public void testActieGemeenteGelijkAanBronGemeente() {
        final HuwelijkGeregistreerdPartnerschapBericht nieuwSituatie = maakNieuwSituatie(GEMEENTE_AMSTERDAM);
        final ActieBericht actieBericht = maakActieBericht(PARTIJ_AMSTERDAM, PARTIJ_AMSTERDAM);

        final List<BerichtEntiteit> berichtEntiteits = bral2110.voerRegelUit(null, nieuwSituatie, actieBericht, null);

        Assert.assertEquals(0, berichtEntiteits.size());
    }

    @Test
    public void testGeenGemeenteOpgegevenInRelatie() {
        final HuwelijkGeregistreerdPartnerschapBericht nieuwSituatie = maakNieuwSituatie(null);
        final ActieBericht actieBericht = maakActieBericht(PARTIJ_AMSTERDAM);

        final List<BerichtEntiteit> berichtEntiteits = bral2110.voerRegelUit(null, nieuwSituatie, actieBericht, null);

        Assert.assertEquals(0, berichtEntiteits.size());
    }

    @Test
    public void testGeenBronnen() {
        final HuwelijkGeregistreerdPartnerschapBericht nieuwSituatie = maakNieuwSituatie(GEMEENTE_AMSTERDAM);
        final ActieBericht actieBericht = maakActieBericht();

        final List<BerichtEntiteit> berichtEntiteits = bral2110.voerRegelUit(null, nieuwSituatie, actieBericht, null);

        Assert.assertEquals(0, berichtEntiteits.size());
    }

    @Test
    public void testGeenDocumentInBron() {
        final HuwelijkGeregistreerdPartnerschapBericht nieuwSituatie = maakNieuwSituatie(GEMEENTE_AMSTERDAM);
        final ActieBericht actieBericht = maakActieBericht();
        actieBericht.getBronnen().add(new ActieBronBericht());

        final List<BerichtEntiteit> berichtEntiteits = bral2110.voerRegelUit(null, nieuwSituatie, actieBericht, null);

        Assert.assertEquals(0, berichtEntiteits.size());
    }

    private HuwelijkGeregistreerdPartnerschapBericht maakNieuwSituatie(final GemeenteAttribuut gemeente) {
        final HuwelijkGeregistreerdPartnerschapBericht nieuwSituatie = new HuwelijkBericht();
        nieuwSituatie.setStandaard(new RelatieStandaardGroepBericht());
        nieuwSituatie.getStandaard().setGemeenteAanvang(gemeente);

        return nieuwSituatie;
    }

    private ActieBronBericht maakActieBronBericht(final PartijAttribuut partij) {
        final ActieBronBericht actieBronBericht = new ActieBronBericht();
        actieBronBericht.setDocument(new DocumentBericht());
        actieBronBericht.getDocument().setStandaard(new DocumentStandaardGroepBericht());
        actieBronBericht.getDocument().getStandaard()
                        .setPartij(partij);

        return actieBronBericht;
    }

    private ActieBericht maakActieBericht(final PartijAttribuut ... partijen) {
        final ActieBericht actieBericht = new ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht();
        actieBericht.setBronnen(new ArrayList<ActieBronBericht>());

        for (final PartijAttribuut partij : partijen) {
            if (partij != null) {
                actieBericht.getBronnen().add(maakActieBronBericht(partij));
            }
        }

        return actieBericht;
    }
}
