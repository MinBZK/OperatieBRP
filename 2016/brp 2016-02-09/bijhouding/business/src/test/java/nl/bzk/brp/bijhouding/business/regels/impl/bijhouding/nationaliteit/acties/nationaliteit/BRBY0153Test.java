/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestNationaliteitBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link BRBY0153} bedrijfsregel.
 */
public class BRBY0153Test {

    private final BRBY0153 brby0153 = new BRBY0153();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0153, brby0153.getRegel());
    }

    @Test
    public void testNationaliteitenNull() {
        final List<BerichtEntiteit> overtreders = brby0153.voerRegelUit(null, bouwNieuweSituatie(null), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNationaliteitenLeeg() {
        final PersoonBericht persoon = bouwNieuweSituatie(null);
        persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        final List<BerichtEntiteit> overtreders = brby0153.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testVreemdeNationaliteit() {
        final List<BerichtEntiteit> overtreders = brby0153.voerRegelUit(null, bouwNieuweSituatie(new NationaliteitcodeAttribuut((short) 1234)), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitHuidigeSituatieNull() {
        final List<BerichtEntiteit> overtreders = brby0153.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitWelVoornaam() {
        final List<BerichtEntiteit> overtreders = brby0153.voerRegelUit(bouwHuidigeSituatie("Pietje"), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitGeenVoornaamWelVoornaamInBericht() {
        final List<BerichtEntiteit> overtreders = brby0153.voerRegelUit(bouwHuidigeSituatie(null), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE, "Jan"), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitGeenVoornaamLegeVoornamenInBericht() {
        final PersoonBericht persoon = bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE, "Jan");
        // maak voornamen weer leeg, voor code coverage
        persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
        final List<BerichtEntiteit> overtreders = brby0153.voerRegelUit(bouwHuidigeSituatie(null), persoon, null, null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitGeenVoornaam() {
        final List<BerichtEntiteit> overtreders = brby0153.voerRegelUit(bouwHuidigeSituatie(null), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), null, null);
        Assert.assertEquals(1, overtreders.size());
    }

    private PersoonBericht bouwNieuweSituatie(final NationaliteitcodeAttribuut nationaliteit) {
        return bouwNieuweSituatie(nationaliteit, null);
    }

    private PersoonBericht bouwNieuweSituatie(final NationaliteitcodeAttribuut nationaliteit, final String voornaam) {
        final PersoonBericht persoon = new PersoonBericht();
        if (voornaam != null) {
            final PersoonVoornaamBericht voornaamBericht = new PersoonVoornaamBericht();
            final PersoonVoornaamStandaardGroepBericht voornaamGroep = new PersoonVoornaamStandaardGroepBericht();
            voornaamGroep.setNaam(new VoornaamAttribuut(voornaam));
            voornaamBericht.setStandaard(voornaamGroep);
            persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
            persoon.getVoornamen().add(voornaamBericht);
        }
        if (nationaliteit != null) {
            persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
            final PersoonNationaliteitBericht persoonNationaliteit = new PersoonNationaliteitBericht();
            persoonNationaliteit.setNationaliteit(new NationaliteitAttribuut(
                TestNationaliteitBuilder.maker().metCode(nationaliteit).maak()));
            persoon.getNationaliteiten().add(persoonNationaliteit);
        }
        return persoon;
    }

    private PersoonView bouwHuidigeSituatie(final String voornaam) {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        if (voornaam != null) {
            persoon.getVoornamen().add(new PersoonVoornaamHisVolledigImplBuilder(persoon, new VolgnummerAttribuut(1))
                    .nieuwStandaardRecord(20010101, null, 20010101)
                        .naam(voornaam)
                    .eindeRecord()
                    .build());
        }
        return new PersoonView(persoon);
    }
}
