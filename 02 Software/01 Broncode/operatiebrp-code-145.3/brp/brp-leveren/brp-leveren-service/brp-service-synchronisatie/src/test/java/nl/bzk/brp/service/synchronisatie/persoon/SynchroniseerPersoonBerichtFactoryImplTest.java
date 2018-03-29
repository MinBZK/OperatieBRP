/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerPersoonAntwoordBericht;
import org.junit.Assert;
import org.junit.Test;

public class SynchroniseerPersoonBerichtFactoryImplTest {

    private static final ZonedDateTime TS_VERZENDING = ZonedDateTime.now();
    private static final String REF_NR_ATTR_ANTW = "referentieNummerAttribuutAntwoord";
    private static final Melding MELDING_ALG0001 = new Melding(Regel.ALG0001);
    private static final Partij PARTIJ = TestPartijBuilder.maakBuilder().metId(1).metCode("000000").metNaam("Testpartij").build();
    private static final String REF_NR = "referentieNummer";
    private static final SoortMelding HOOGSTE_MELDING_NIVEAU = SoortMelding.FOUT;

    @Test
    public void maakBericht() throws Exception {
        final MaakAntwoordBerichtResultaat maakAntwoordBerichtResultaat =
                new MaakAntwoordBerichtResultaat(TS_VERZENDING, REF_NR_ATTR_ANTW, Lists.newArrayList(MELDING_ALG0001), PARTIJ, REF_NR, HOOGSTE_MELDING_NIVEAU);
        final SynchroniseerPersoonBerichtFactoryImpl berichtFactory = new SynchroniseerPersoonBerichtFactoryImpl();
        final SynchroniseerPersoonAntwoordBericht bericht = berichtFactory.apply(maakAntwoordBerichtResultaat);

        Assert.assertEquals(REF_NR_ATTR_ANTW, bericht.getBasisBerichtGegevens().getStuurgegevens().getReferentienummer());
        Assert.assertEquals(REF_NR, bericht.getBasisBerichtGegevens().getStuurgegevens().getCrossReferentienummer());
        Assert.assertEquals(PARTIJ, bericht.getBasisBerichtGegevens().getStuurgegevens().getZendendePartij());
        Assert.assertEquals(BasisBerichtGegevens.BRP_SYSTEEM, bericht.getBasisBerichtGegevens().getStuurgegevens().getZendendeSysteem());
        Assert.assertEquals(TS_VERZENDING, bericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending());
        Assert.assertEquals(MELDING_ALG0001, bericht.getBasisBerichtGegevens().getMeldingen().get(0));
        Assert.assertEquals(1, bericht.getBasisBerichtGegevens().getMeldingen().size());
        Assert.assertEquals(TS_VERZENDING, bericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending());
        Assert.assertEquals("Foutief", bericht.getBasisBerichtGegevens().getResultaat().getVerwerking());
        Assert.assertEquals(HOOGSTE_MELDING_NIVEAU.getNaam(), bericht.getBasisBerichtGegevens().getResultaat().getHoogsteMeldingsniveau());
    }

}
