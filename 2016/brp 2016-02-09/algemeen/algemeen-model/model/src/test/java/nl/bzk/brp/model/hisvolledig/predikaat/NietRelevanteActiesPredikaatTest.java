/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 */
public class NietRelevanteActiesPredikaatTest {

    @Test
    public void testFormeleGroep() {
        // Maak een test persoon
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        // Pas de geboorte groep aan
        final ActieModel actieGeboorteCorrectie = new ActieModel(
            null, null, null, new DatumEvtDeelsOnbekendAttribuut(20140101), null, DatumTijdAttribuut.nu(), null
        );
        zetActieID(actieGeboorteCorrectie, 1001L);
        final PersoonGeboorteGroepBericht geboorteGroepBericht = new PersoonGeboorteGroepBericht();
        final HisPersoonGeboorteModel hisPersoonGeboorteModel = new HisPersoonGeboorteModel(
            persoonHisVolledig, geboorteGroepBericht, actieGeboorteCorrectie
        );
        persoonHisVolledig.getPersoonGeboorteHistorie().voegToe(hisPersoonGeboorteModel);

        // Vraag de persoon op zonder de laatste actie
        final Set<Long> uitgeslotenActieIds = new HashSet<>();
        uitgeslotenActieIds.add(actieGeboorteCorrectie.getID());
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoonHisVolledig, new NietRelevanteActiesPredikaat(uitgeslotenActieIds));

        // HisVolledig heeft 2 records voor geboorte:
        Assert.assertEquals(2, persoonHisVolledig.getPersoonGeboorteHistorie().getAantal());
        // View retourneert 1 record
        Assert.assertEquals(1, view.getPersoonGeboorteHistorie().getAantal());
    }

    @Test
    public void testMaterieleGroep() {
        // Maak een test persoon
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        final PersoonAdresHisVolledigImpl adresHisVolledig = persoonHisVolledig.getAdressen().iterator().next();

        // Pas adres groep aan
        final ActieModel actieVerhuizing = new ActieModel(
            null, null, null, new DatumEvtDeelsOnbekendAttribuut(20140101), null, DatumTijdAttribuut.nu(), null
        );
        zetActieID(actieVerhuizing, 1001L);
        final PersoonAdresStandaardGroepBericht adresStandaardGroepBericht = new PersoonAdresStandaardGroepBericht();
        adresStandaardGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20140105));
        final HisPersoonAdresModel nieuwAdres = new HisPersoonAdresModel(adresHisVolledig, adresStandaardGroepBericht, adresStandaardGroepBericht,
            actieVerhuizing);
        adresHisVolledig.getPersoonAdresHistorie().voegToe(nieuwAdres);

        // Vraag de persoon op zonder de laatste actie
        final Set<Long> uitgeslotenActieIds = new HashSet<>();
        uitgeslotenActieIds.add(actieVerhuizing.getID());
        final PersoonHisVolledigView view = new PersoonHisVolledigView(persoonHisVolledig, new NietRelevanteActiesPredikaat(uitgeslotenActieIds));

        // HisVolledig heeft 3 records voor adres: 1 vervallen, 1 aangepaste geldigheid, 1 actueel
        Assert.assertEquals(3, adresHisVolledig.getPersoonAdresHistorie().getAantal());
        // View retourneert 1 record
        Assert.assertEquals(1, view.getAdressen().iterator().next().getPersoonAdresHistorie().getAantal());
    }

    private void zetActieID(final ActieModel actie, final Long id) {
        ReflectionTestUtils.setField(actie, "iD", id);
    }
}
