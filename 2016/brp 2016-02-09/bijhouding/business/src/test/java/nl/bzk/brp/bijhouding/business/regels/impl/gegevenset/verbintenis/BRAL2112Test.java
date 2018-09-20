/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRAL2112Test {

    private Gemeente amsterdam;

    private Gemeente adorp;
    private Gemeente baflo;
    private Gemeente winsum;


    /**
     * Initialisatie met de volgende instellingen:
     * - Gemeente baflo is voortzettende gemeente van adorp
     * - Gemeente winsum is voortzettende gemeente van baflo
     */
    @Before
    public void init() {
        amsterdam = new Gemeente(null, new GemeenteCodeAttribuut((short) 1), null, null, null, null);

        winsum = new Gemeente(null, new GemeenteCodeAttribuut((short) 2), null, null, null, null);
        baflo = new Gemeente(null, new GemeenteCodeAttribuut((short) 1), null, winsum, null, null);
        adorp = new Gemeente(null, new GemeenteCodeAttribuut((short) 1), null, baflo, null, null);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRAL2112, new BRAL2112().getRegel());
    }

    @Test
    public void testGemeenteEindeNietIngevuld() {
        final List<BerichtEntiteit> berichtEntiteiten = new BRAL2112().voerRegelUit(
                maakHuidigeSituatie(amsterdam),
                maakNieuweSituatie(null,
                                   StatischeObjecttypeBuilder.bouwRedenEindeRelatie(
                                           RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_NIETIGVERKLARING_CODE_STRING, null)),
                null, null
        );

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testGemeenteAanvangNietInHuidigeSituatie() {
        final List<BerichtEntiteit> berichtEntiteiten = new BRAL2112().voerRegelUit(
                maakHuidigeSituatie(null),
                maakNieuweSituatie(null,
                                   StatischeObjecttypeBuilder.bouwRedenEindeRelatie(
                                           RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_NIETIGVERKLARING_CODE_STRING, null)),
                null, null
        );

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testNietGelijkeGemeentenMaarRedenBeEindigingRelatieIsOverlijden() {
        final List<BerichtEntiteit> berichtEntiteiten = new BRAL2112().voerRegelUit(
                maakHuidigeSituatie(amsterdam),
                maakNieuweSituatie(winsum,
                                   StatischeObjecttypeBuilder.bouwRedenEindeRelatie(
                                           RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OVERLIJDEN_CODE_STRING, null)),
                null, null
        );

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testNietGelijkeGemeentenMaarRedenBeEindigingRelatieIsRechtsVermoedenOverlijden() {
        final List<BerichtEntiteit> berichtEntiteiten = new BRAL2112().voerRegelUit(
                maakHuidigeSituatie(amsterdam),
                maakNieuweSituatie(winsum,
                                   StatischeObjecttypeBuilder.bouwRedenEindeRelatie(
                                 RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_RECHTSVERMOEDEN_OVERLIJDEN_CODE_STRING, null)),
                null, null
        );

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testGemeentenNietGelijk() {
        final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie =
                maakNieuweSituatie(winsum,
                                   StatischeObjecttypeBuilder.bouwRedenEindeRelatie(
                                           RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_NIETIGVERKLARING_CODE_STRING, null));
        final List<BerichtEntiteit> berichtEntiteiten = new BRAL2112().voerRegelUit(
                maakHuidigeSituatie(amsterdam),
                nieuweSituatie,
                null, null
        );

        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(nieuweSituatie, berichtEntiteiten.get(0));
    }

    @Test
    public void testGemeentenGelijk() {
        final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie =
                maakNieuweSituatie(amsterdam,
                                   StatischeObjecttypeBuilder.bouwRedenEindeRelatie(
                                           RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_NIETIGVERKLARING_CODE_STRING, null));
        final List<BerichtEntiteit> berichtEntiteiten = new BRAL2112().voerRegelUit(
                maakHuidigeSituatie(amsterdam),
                nieuweSituatie,
                null, null
        );

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testGemeentenNietGelijkMaarGemeenteEindeIsVoortzettendeVanGemeenteAanvang() {
        final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie =
                maakNieuweSituatie(winsum,
                                   StatischeObjecttypeBuilder.bouwRedenEindeRelatie(
                                           RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_NIETIGVERKLARING_CODE_STRING, null));
        final List<BerichtEntiteit> berichtEntiteiten = new BRAL2112().voerRegelUit(
                maakHuidigeSituatie(baflo),
                nieuweSituatie,
                null, null
        );

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    /**
     * 2 niveau's verder, adorp is voortgezet door baflo en baflo is voortgezet door winsum.
     */
    @Test
    public void testGemeentenNietGelijkMaarGemeenteEindeIsVoortzettendeVanGemeenteAanvang2() {
        final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie =
                maakNieuweSituatie(winsum,
                                   StatischeObjecttypeBuilder.bouwRedenEindeRelatie(
                                           RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_NIETIGVERKLARING_CODE_STRING, null));
        final List<BerichtEntiteit> berichtEntiteiten = new BRAL2112().voerRegelUit(
                maakHuidigeSituatie(adorp),
                nieuweSituatie,
                null, null
        );

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    private HuwelijkGeregistreerdPartnerschapView maakHuidigeSituatie(final Gemeente gemeenteAanvang) {
        final ActieModel actie = new ActieModel(null, null, null,
                                          new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()),
                                          null, DatumTijdAttribuut.nu(), null);
        final HuwelijkHisVolledigImpl huwelijk = new HuwelijkHisVolledigImplBuilder()
                .nieuwStandaardRecord(actie)
                .gemeenteAanvang(gemeenteAanvang)
                .eindeRecord().build();

        return new HuwelijkGeregistreerdPartnerschapView(huwelijk, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());
    }

    private HuwelijkGeregistreerdPartnerschapBericht maakNieuweSituatie(
            final Gemeente gemeenteEinde, final RedenEindeRelatieAttribuut redenBeeindigingRelatie)
    {
        final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie = new HuwelijkBericht();
        nieuweSituatie.setStandaard(new RelatieStandaardGroepBericht());
        if (gemeenteEinde != null) {
            nieuweSituatie.getStandaard().setGemeenteEinde(new GemeenteAttribuut(gemeenteEinde));
        }
        nieuweSituatie.getStandaard().setRedenEinde(redenBeeindigingRelatie);
        return nieuweSituatie;
    }
}
