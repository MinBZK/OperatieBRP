/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestNationaliteitBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link BRBY0151} bedrijfsregel.
 */
public class BRBY0151Test {

    private final BRBY0151 brby0151 = new BRBY0151();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0151, brby0151.getRegel());
    }

    @Test
    public void testNationaliteitenNull() {
        final List<BerichtEntiteit> overtreders = brby0151.voerRegelUit(null, bouwNieuweSituatie(null), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNationaliteitenLeeg() {
        final PersoonBericht persoon = bouwNieuweSituatie(null);
        persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        final List<BerichtEntiteit> overtreders = brby0151.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testVreemdeNationaliteit() {
        final List<BerichtEntiteit> overtreders = brby0151.voerRegelUit(null, bouwNieuweSituatie(new NationaliteitcodeAttribuut((short) 1234)), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitHuidigeSituatieNull() {
        final List<BerichtEntiteit> overtreders = brby0151.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitSamengesteldeNaamNull() {
        final List<BerichtEntiteit> overtreders = brby0151.voerRegelUit(bouwHuidigeSituatie(true, 20100101), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitGeenNamenreeks() {
        final List<BerichtEntiteit> overtreders = brby0151.voerRegelUit(bouwHuidigeSituatie(false), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitWelNamenreeksNamenreeksNeeInBericht() {
        final List<BerichtEntiteit> overtreders = brby0151.voerRegelUit(bouwHuidigeSituatie(true), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE, false), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitWelNamenreeksNamenreeksJaInBericht() {
        final List<BerichtEntiteit> overtreders = brby0151.voerRegelUit(bouwHuidigeSituatie(true), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE, true), null, null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitWelNamenreeks() {
        final List<BerichtEntiteit> overtreders = brby0151.voerRegelUit(bouwHuidigeSituatie(true), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), null, null);
        Assert.assertEquals(1, overtreders.size());
    }

    private PersoonBericht bouwNieuweSituatie(final NationaliteitcodeAttribuut nationaliteit) {
        return bouwNieuweSituatie(nationaliteit, null);
    }

    private PersoonBericht bouwNieuweSituatie(final NationaliteitcodeAttribuut nationaliteit, final Boolean indicatieNamenreeksInBericht) {
        final PersoonBericht persoon = new PersoonBericht();
        if (indicatieNamenreeksInBericht != null) {
            final PersoonSamengesteldeNaamGroepBericht samengesteldeNaam = new PersoonSamengesteldeNaamGroepBericht();
            samengesteldeNaam.setIndicatieNamenreeks(new JaNeeAttribuut(indicatieNamenreeksInBericht));
            persoon.setSamengesteldeNaam(samengesteldeNaam);
        }
        if (nationaliteit != null) {
            persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
            final PersoonNationaliteitBericht persoonNationaliteit = new PersoonNationaliteitBericht();
            persoonNationaliteit.setNationaliteit(new NationaliteitAttribuut(TestNationaliteitBuilder.maker().metCode(nationaliteit).maak()));
            persoon.getNationaliteiten().add(persoonNationaliteit);
        }
        return persoon;
    }

    private PersoonView bouwHuidigeSituatie(final boolean indicatieNamenreeks) {
        return bouwHuidigeSituatie(indicatieNamenreeks, null);
    }

    private PersoonView bouwHuidigeSituatie(final boolean indicatieNamenreeks, final Integer datumEindeGeldigheid) {
        return new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(20100101, datumEindeGeldigheid, 20100101)
                    .indicatieNamenreeks(indicatieNamenreeks)
                .eindeRecord()
                .build());
    }
}
