/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class RegistratieNationaliteitUitvoerderTest {

    /**
     * Maak nieuw persoonNationaliteitHisVolledig aan als de persoon nog geen persoonNationaliteitHisVolledig heeft
     * en geef deze door aan de verwerker.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testNieuwNationaliteitUitvoeren() {
        PersoonBericht persoonBericht = maakPersoonBericht(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE);

        PersoonHisVolledigImpl bestaandPersoon =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        RegistratieNationaliteitUitvoerder uitvoerder = bereidUitvoerderVoor(persoonBericht, bestaandPersoon);

        uitvoerder.verzamelVerwerkingsregels();

        List<Verwerkingsregel> afleidingsregels =
                (List<Verwerkingsregel>) ReflectionTestUtils.getField(uitvoerder, "verwerkingsregels");

        Assert.assertEquals(1, afleidingsregels.size());
        PersoonNationaliteitHisVolledigImpl persoonNationaliteitHisVolledig =
                (PersoonNationaliteitHisVolledigImpl) ReflectionTestUtils.getField(afleidingsregels.get(0), "model");

        Assert.assertNull(persoonNationaliteitHisVolledig.getNationaliteit().getWaarde().getDatumAanvangGeldigheid());
    }

    /**
     * Als persoon al een overeenkomende nationaliteit heeft dan moet de bestaande persoonNationaliteitHisVolledig
     * meegegeven worden aan de verwerker.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testReedsBekendNationaliteitUitvoeren() {
        PersoonBericht persoonBericht = maakPersoonBericht(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE);

        PersoonHisVolledigImpl bestaandPersoon = maakPersoonHisVolledigImpl();

        RegistratieNationaliteitUitvoerder uitvoerder = bereidUitvoerderVoor(persoonBericht, bestaandPersoon);

        uitvoerder.verzamelVerwerkingsregels();

        List<Verwerkingsregel> afleidingsregels =
                (List<Verwerkingsregel>) ReflectionTestUtils.getField(uitvoerder, "verwerkingsregels");

        Assert.assertEquals(1, afleidingsregels.size());
        PersoonNationaliteitHisVolledigImpl persNation =
                (PersoonNationaliteitHisVolledigImpl) ReflectionTestUtils.getField(afleidingsregels.get(0), "model");

        Assert.assertEquals(bestaandPersoon.getNationaliteiten().iterator().next(), persNation);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testHeeftAlNationaliteitMaarNietOvereenkomendMetBericht() {
        PersoonBericht persoonBericht = maakPersoonBericht(new NationaliteitcodeAttribuut((short) 2));

        PersoonHisVolledigImpl bestaandPersoon = maakPersoonHisVolledigImpl();

        RegistratieNationaliteitUitvoerder uitvoerder = bereidUitvoerderVoor(persoonBericht, bestaandPersoon);

        uitvoerder.verzamelVerwerkingsregels();

        List<Verwerkingsregel> afleidingsregels =
                (List<Verwerkingsregel>) ReflectionTestUtils.getField(uitvoerder, "verwerkingsregels");

        Assert.assertEquals(1, afleidingsregels.size());
        PersoonNationaliteitHisVolledigImpl persNation =
                (PersoonNationaliteitHisVolledigImpl) ReflectionTestUtils.getField(afleidingsregels.get(0), "model");

        Assert.assertNull(persNation.getNationaliteit().getWaarde().getDatumAanvangGeldigheid());
    }

    private PersoonBericht maakPersoonBericht(final NationaliteitcodeAttribuut nationaliteitcode) {
        PersoonNationaliteitBericht persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        persoonNationaliteitBericht.setNationaliteit(new NationaliteitAttribuut(
                new Nationaliteit(nationaliteitcode, new NaamEnumeratiewaardeAttribuut("NL"), null, null)));

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        persoonBericht.getNationaliteiten().add(persoonNationaliteitBericht);

        return persoonBericht;
    }

    private PersoonHisVolledigImpl maakPersoonHisVolledigImpl() {
        PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        PersoonNationaliteitHisVolledigImpl persoonNationaliteitHisVolledig =
                new PersoonNationaliteitHisVolledigImplBuilder(persoonHisVolledig,
                        new Nationaliteit(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE, new NaamEnumeratiewaardeAttribuut("NL"),
                                new DatumEvtDeelsOnbekendAttribuut(20101010), null)).build();
        persoonHisVolledig.getNationaliteiten().add(persoonNationaliteitHisVolledig);

        return persoonHisVolledig;
    }

    private RegistratieNationaliteitUitvoerder bereidUitvoerderVoor(final PersoonBericht persoonBericht,
                                                                    final PersoonHisVolledigImpl persoonHisVolledig)
    {
        RegistratieNationaliteitUitvoerder uitvoerder = new RegistratieNationaliteitUitvoerder();
        ReflectionTestUtils.setField(uitvoerder, "berichtRootObject", persoonBericht);
        ReflectionTestUtils.setField(uitvoerder, "modelRootObject", persoonHisVolledig);

        return uitvoerder;
    }
}
