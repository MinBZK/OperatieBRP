/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.stamgegeven;

import static java.util.Arrays.asList;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerStamgegevenBericht;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.junit.Assert;
import org.junit.Test;

public class SynchroniseerStamgegevenBerichtFactoryImplTest {

    private static final String REF_NR = "referentieNummer";
    private static final Melding MELDING_ALG0001 = new Melding(Regel.ALG0001);
    private static final Partij PARTIJ = TestPartijBuilder.maakBuilder().metId(1).metCode("000000").metNaam("Testpartij").build();
    //@formatter:off
    private static final StamtabelGegevens STAMTABEL_GEGEVENS =
        new StamtabelGegevens(new StamgegevenTabel(
            ElementHelper.getObjectElement(Element.PERSOON_ADRES),
            Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE)),
            Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE))
        ), asList(
            ImmutableMap.<String, Object>builder().put("gem", "Amsterdam").build()
        ));
    //@formatter:on

    @Test
    public void maakBericht() throws Exception {
        final SynchroniseerStamgegevenBerichtFactory synchroniseerStamgegevenBerichtFactory = new SynchroniseerStamgegevenBerichtFactoryImpl();
        final SynchroniseerStamgegevenBericht bericht = synchroniseerStamgegevenBerichtFactory.maakBericht
                (maakStamgegevenResultaat());

        Assert.assertNotNull(bericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending());
        Assert.assertNotNull(bericht.getBasisBerichtGegevens().getStuurgegevens().getReferentienummer());
        Assert.assertEquals(REF_NR, bericht.getBasisBerichtGegevens().getStuurgegevens().getCrossReferentienummer());
        Assert.assertEquals(PARTIJ, bericht.getBasisBerichtGegevens().getStuurgegevens().getZendendePartij());
        Assert.assertEquals(BasisBerichtGegevens.BRP_SYSTEEM, bericht.getBasisBerichtGegevens().getStuurgegevens().getZendendeSysteem());
        Assert.assertEquals(MELDING_ALG0001, bericht.getBasisBerichtGegevens().getMeldingen().get(0));
        Assert.assertEquals(1, bericht.getBasisBerichtGegevens().getMeldingen().size());
        Assert.assertEquals("Fout", bericht.getBasisBerichtGegevens().getResultaat().getHoogsteMeldingsniveau());
        Assert.assertEquals("Foutief", bericht.getBasisBerichtGegevens().getResultaat().getVerwerking());
        Assert.assertNotNull(bericht.getBerichtStamgegevens().getStamtabelGegevens());
    }

    private BepaalStamgegevenResultaat maakStamgegevenResultaat() {
        SynchronisatieVerzoek synchronisatieVerzoek = new SynchronisatieVerzoek();
        synchronisatieVerzoek.getStuurgegevens().setReferentieNummer(REF_NR);
        BepaalStamgegevenResultaat bepaalStamgegevenResultaat = new BepaalStamgegevenResultaat(synchronisatieVerzoek);
        bepaalStamgegevenResultaat.getMeldingList().add(MELDING_ALG0001);
        bepaalStamgegevenResultaat.setStamgegevens(STAMTABEL_GEGEVENS);
        bepaalStamgegevenResultaat.setBrpPartij(PARTIJ);
        return bepaalStamgegevenResultaat;
    }

}
