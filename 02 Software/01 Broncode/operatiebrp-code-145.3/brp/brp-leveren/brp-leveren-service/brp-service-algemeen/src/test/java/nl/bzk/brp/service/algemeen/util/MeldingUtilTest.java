/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.util;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.domain.algemeen.Melding;
import org.junit.Assert;
import org.junit.Test;

/**
 * SoortMeldingBepalerTest.
 */
public class MeldingUtilTest {

    @Test
    public void testHoogsteMelding() {

        List<Melding> meldingen = maakMeldingen(Lists.newArrayList(SoortMelding.values()));
        Assert.assertEquals(SoortMelding.FOUT, MeldingUtil.bepaalHoogsteMeldingNiveau(meldingen));
    }

    @Test
    public void testGeenMeldingen() {
        List<Melding> meldingen = new ArrayList<>();
        Assert.assertEquals(SoortMelding.GEEN, MeldingUtil.bepaalHoogsteMeldingNiveau(meldingen));
    }

    @Test
    public void testGelijkeMeldingen() {
        List<Melding> meldingen = maakMeldingen(
                Lists.newArrayList(SoortMelding.WAARSCHUWING, SoortMelding.WAARSCHUWING, SoortMelding.GEEN));
        Assert.assertEquals(SoortMelding.WAARSCHUWING, MeldingUtil.bepaalHoogsteMeldingNiveau(meldingen));
    }

    @Test
    public void bepaaltVerwerkingFoutiefBijSoortMeldingDeblokkeerbaar() {
        final Melding melding = new Melding(Regel.R1359);

        assertThat(MeldingUtil.bepaalVerwerking(Collections.singletonList(melding)), is(VerwerkingsResultaat.FOUTIEF));
    }

    @Test
    public void bepaaltVerwerkingFoutiefBijSoortMeldingFoutief() {
        final Melding melding = new Melding(Regel.R1362);

        assertThat(MeldingUtil.bepaalVerwerking(Collections.singletonList(melding)), is(VerwerkingsResultaat.FOUTIEF));
    }

    @Test
    public void bepaaltVerwerkingFoutiefBijAndereSoortMelding() {
        final Melding melding = new Melding(Regel.R1375);

        assertThat(MeldingUtil.bepaalVerwerking(Collections.singletonList(melding)), is(VerwerkingsResultaat.GESLAAGD));
    }

    private List<Melding> maakMeldingen(List<SoortMelding> srtMeldingLijst) {
        return Lists.transform(srtMeldingLijst, input -> new Melding(input, Regel.R1339));
    }

}
