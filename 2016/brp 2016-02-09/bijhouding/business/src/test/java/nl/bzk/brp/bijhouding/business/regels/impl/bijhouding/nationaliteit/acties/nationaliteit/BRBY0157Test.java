/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestNationaliteitBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder.HuwelijkHisVolledigImplBuilderStandaard;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link BRBY0157} bedrijfsregel.
 */
public class BRBY0157Test {

    private final BRBY0157 brby0157 = new BRBY0157();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0157, brby0157.getRegel());
    }

    @Test
    public void testNationaliteitenNull() {
        final List<BerichtEntiteit> overtreders = brby0157.voerRegelUit(null, bouwNieuweSituatie(null), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNationaliteitenLeeg() {
        final PersoonBericht persoon = bouwNieuweSituatie(null);
        persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        final List<BerichtEntiteit> overtreders = brby0157.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testVreemdeNationaliteit() {
        final List<BerichtEntiteit> overtreders = brby0157.voerRegelUit(null, bouwNieuweSituatie(new NationaliteitcodeAttribuut((short) 1234)), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitHuidigeSituatieNull() {
        final List<BerichtEntiteit> overtreders = brby0157.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitGeenHgps() {
        final List<BerichtEntiteit> overtreders = brby0157.voerRegelUit(bouwHuidigeSituatie(), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitEenHgp() {
        final List<BerichtEntiteit> overtreders = brby0157.voerRegelUit(bouwHuidigeSituatie(20010101, null, 20010101, 20020202), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitTweeHgps() {
        final List<BerichtEntiteit> overtreders = brby0157.voerRegelUit(bouwHuidigeSituatie(20010101, null, 20020202, null), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), null, null);
        Assert.assertEquals(1, overtreders.size());
    }

    private PersoonBericht bouwNieuweSituatie(final NationaliteitcodeAttribuut nationaliteit) {
        final PersoonBericht persoon = new PersoonBericht();
        if (nationaliteit != null) {
            persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
            final PersoonNationaliteitBericht persoonNationaliteit = new PersoonNationaliteitBericht();
            persoonNationaliteit.setNationaliteit(new NationaliteitAttribuut(TestNationaliteitBuilder.maker().metCode(nationaliteit).maak()));
            persoon.getNationaliteiten().add(persoonNationaliteit);
        }
        return persoon;
    }

    // @param datumAanvangEnEinde een lijst met aanvang en einde van hgp's, dus altijd een even aantal in totaal.
    private PersoonView bouwHuidigeSituatie(final Integer ... datumAanvangEnEinde) {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        for (int i = 0; i < datumAanvangEnEinde.length; i += 2) {
            final Integer datumAanvang = datumAanvangEnEinde[i];
            final Integer datumEinde = datumAanvangEnEinde[i + 1];

            final HuwelijkHisVolledigImplBuilderStandaard builder = new HuwelijkHisVolledigImplBuilder()
                    .nieuwStandaardRecord(datumAanvang)
                        .datumAanvang(datumAanvang);
            if (datumEinde != null) {
                builder.datumEinde(datumEinde);
            }
            final HuwelijkHisVolledigImpl huwelijk = builder.eindeRecord().build();

            final PartnerHisVolledigImpl persoonPartner = new PartnerHisVolledigImpl(huwelijk, persoon);
            persoon.getBetrokkenheden().add(persoonPartner);
            huwelijk.getBetrokkenheden().add(persoonPartner);
        }
        return new PersoonView(persoon);
    }
}
