/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGBAInitieleVullingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder.HuwelijkHisVolledigImplBuilderStandaard;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/** Unit test ten behoeve van de functionaliteit zoals geboden in de regel {@link BRAL2104}. */
public class BRAL2104Test {

    private static final PartijAttribuut   PARTIJ_AMSTERDAM   = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM;
    private static final PartijAttribuut   PARTIJ_BREDA       = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA;
    private static final GemeenteAttribuut GEMEENTE_BREDA     = StatischeObjecttypeBuilder.GEMEENTE_BREDA;

    private final BRAL2104 bral2104 = new BRAL2104();
    private List<BerichtEntiteit> overtreders;

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRAL2104, bral2104.getRegel());
    }

    @Test
    public void testPartijGerechtigdGemeenteAanvangHuidigeSituatie() {
        overtreders = bral2104.voerRegelUit(maakHuidigeSituatie(GEMEENTE_BREDA, null), null, maakActie(PARTIJ_BREDA), maakBestaandeBetrokkenen());
        Assert.assertEquals(0, overtreders.size());
        overtreders = bral2104.voerRegelUit(maakHuidigeSituatie(GEMEENTE_BREDA, null), null, maakActie(PARTIJ_AMSTERDAM), maakBestaandeBetrokkenen());
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testPartijGerechtigdGemeenteEindeHuidigeSituatie() {
        overtreders = bral2104.voerRegelUit(maakHuidigeSituatie(null, GEMEENTE_BREDA), null, maakActie(PARTIJ_BREDA), maakBestaandeBetrokkenen());
        Assert.assertEquals(0, overtreders.size());
        overtreders = bral2104.voerRegelUit(maakHuidigeSituatie(null, GEMEENTE_BREDA), null, maakActie(PARTIJ_AMSTERDAM), maakBestaandeBetrokkenen());
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testPartijGerechtigdGemeenteAanvangNieuweSituatie() {
        overtreders = bral2104.voerRegelUit(null, maakNieuweSituatie(GEMEENTE_BREDA, null), maakActie(PARTIJ_BREDA), maakBestaandeBetrokkenen());
        Assert.assertEquals(0, overtreders.size());
        overtreders = bral2104.voerRegelUit(null, maakNieuweSituatie(GEMEENTE_BREDA, null), maakActie(PARTIJ_AMSTERDAM), maakBestaandeBetrokkenen());
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testPartijGerechtigdGemeenteEindeNieuweSituatie() {
        overtreders = bral2104.voerRegelUit(null, maakNieuweSituatie(null, GEMEENTE_BREDA), maakActie(PARTIJ_BREDA), maakBestaandeBetrokkenen());
        Assert.assertEquals(0, overtreders.size());
        overtreders = bral2104.voerRegelUit(null, maakNieuweSituatie(null, GEMEENTE_BREDA), maakActie(PARTIJ_AMSTERDAM), maakBestaandeBetrokkenen());
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testPartijGerechtigdBijhoudingsGemeente() {
        overtreders = bral2104.voerRegelUit(maakHuidigeSituatie(null, null), null, maakActie(PARTIJ_BREDA), maakBestaandeBetrokkenen(PARTIJ_BREDA));
        Assert.assertEquals(0, overtreders.size());
        overtreders = bral2104.voerRegelUit(maakHuidigeSituatie(null, null), null, maakActie(PARTIJ_AMSTERDAM), maakBestaandeBetrokkenen(PARTIJ_BREDA));
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testPartijBijhoudingsGemeenteNull() {
        overtreders = bral2104.voerRegelUit(maakHuidigeSituatie(null, null), null, maakActie(PARTIJ_BREDA), maakBestaandeBetrokkenen(true, new PartijAttribuut[] {null}));
        Assert.assertEquals(1, overtreders.size());
    }

    private Actie maakActie(final PartijAttribuut partij) {
        final ActieBericht actie = new ActieConversieGBABericht();
        final AdministratieveHandelingBericht administratieveHandeling = new HandelingGBAInitieleVullingBericht();
        administratieveHandeling.setPartij(partij);
        actie.setAdministratieveHandeling(administratieveHandeling);
        return actie;
    }

    private HuwelijkGeregistreerdPartnerschapView maakHuidigeSituatie(
            final GemeenteAttribuut gemeenteAanvang, final GemeenteAttribuut gemeenteEinde)
    {
        HuwelijkHisVolledigImplBuilderStandaard builder = new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(20010101);
        if (gemeenteAanvang != null) {
            builder = builder.gemeenteAanvang(gemeenteAanvang.getWaarde());
        }
        if (gemeenteEinde != null) {
            builder = builder.gemeenteEinde(gemeenteEinde.getWaarde());
        }
        return new HuwelijkView(builder.eindeRecord().build());
    }

    private HuwelijkGeregistreerdPartnerschapBericht maakNieuweSituatie(
            final GemeenteAttribuut gemeenteAanvang, final GemeenteAttribuut gemeenteEinde)
    {
        final HuwelijkBericht huwelijk = new HuwelijkBericht();
        final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
        standaard.setGemeenteAanvang(gemeenteAanvang);
        standaard.setGemeenteEinde(gemeenteEinde);
        huwelijk.setStandaard(standaard);
        return huwelijk;
    }

    private Map<String, PersoonView> maakBestaandeBetrokkenen(final PartijAttribuut ... bijhoudingsgemeentePartners) {
        return maakBestaandeBetrokkenen(false, bijhoudingsgemeentePartners);
    }

    private Map<String, PersoonView> maakBestaandeBetrokkenen(final boolean nullen, final PartijAttribuut ... bijhoudingsgemeentePartners) {
        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        int i = 1;
        for (final PartijAttribuut bijhoudingsgemeentePartner : bijhoudingsgemeentePartners) {
            if (!nullen) {
                final PersoonHisVolledigImpl partner = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwBijhoudingRecord(20010101, null, 20010101)
                            .bijhoudingspartij(bijhoudingsgemeentePartner.getWaarde())
                        .eindeRecord()
                        .build();
                bestaandeBetrokkenen.put("" + i, new PersoonView(partner));
            } else {
                bestaandeBetrokkenen.put("" + i, new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build()));
            }
            i++;
        }
        return bestaandeBetrokkenen;
    }

}
