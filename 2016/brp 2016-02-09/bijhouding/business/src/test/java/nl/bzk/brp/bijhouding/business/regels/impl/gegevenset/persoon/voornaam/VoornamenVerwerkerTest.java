/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.voornaam;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam.SamengesteldeNaamAfleiding;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

public class VoornamenVerwerkerTest {

    private static final Integer DATUM_AANVANG_GELDIGHEID_ACTIE = 20120101;
    private static final Integer[] DATUM_AANVANG_GELDIGHEID_VOORNAMEN = new Integer[] {20110101, 20120101, 20130101};
    private static final String OUDE_NAAM = "Test oude naam";
    private static final String NIEUWE_NAAM = "Test nieuwe naam";

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00001, new VoornamenVerwerker(null, null, null).getRegel());
    }

    @Test
    public void testVerwerking1VoornamenBericht0VoornamenHisVolledig() {
        PersoonHisVolledigImpl persoonHisVolledig = bouwPersoonHisVolledig(0);
        VoornamenVerwerker verwerker = new VoornamenVerwerker(bouwPersoon(1), persoonHisVolledig, bouwActie());
        verwerker.neemBerichtDataOverInModel();

        Assert.assertEquals(1, persoonHisVolledig.getVoornamen().size());
        MaterieleHistorieSet<HisPersoonVoornaamModel> voornaamHistorie = persoonHisVolledig.getVoornamen().iterator().next().getPersoonVoornaamHistorie();
        Assert.assertEquals(1, voornaamHistorie.getAantal());
        Assert.assertEquals(NIEUWE_NAAM, voornaamHistorie.getActueleRecord().getNaam().getWaarde());
    }

    @Test
    public void testVerwerking1VoornamenBericht1VoornamenHisVolledig() {
        PersoonHisVolledigImpl persoonHisVolledig = bouwPersoonHisVolledig(1);
        VoornamenVerwerker verwerker = new VoornamenVerwerker(bouwPersoon(1), persoonHisVolledig, bouwActie());
        verwerker.neemBerichtDataOverInModel();

        Assert.assertEquals(1, persoonHisVolledig.getVoornamen().size());
        MaterieleHistorieSet<HisPersoonVoornaamModel> voornaamHistorie = persoonHisVolledig.getVoornamen().iterator().next().getPersoonVoornaamHistorie();
        Assert.assertEquals(3, voornaamHistorie.getAantal());
        Assert.assertEquals(NIEUWE_NAAM, voornaamHistorie.getActueleRecord().getNaam().getWaarde());
    }

    @Test
    public void testVerwerking1VoornamenBericht2VoornamenHisVolledig() {
        PersoonHisVolledigImpl persoonHisVolledig = bouwPersoonHisVolledig(3);
        VoornamenVerwerker verwerker = new VoornamenVerwerker(bouwPersoon(1), persoonHisVolledig, bouwActie());
        verwerker.neemBerichtDataOverInModel();

        Assert.assertEquals(3, persoonHisVolledig.getVoornamen().size());
        for (PersoonVoornaamHisVolledig voornaamHisVolledig : persoonHisVolledig.getVoornamen()) {
            MaterieleHistorieSet<HisPersoonVoornaamModel> voornaamHistorie = voornaamHisVolledig.getPersoonVoornaamHistorie();
            if (voornaamHisVolledig.getVolgnummer().getWaarde() == 1) {
                // Voornaam 1 heeft extra historie en is nu NIEUWE_NAAM geworden.
                Assert.assertEquals(3, voornaamHistorie.getAantal());
                Assert.assertEquals(NIEUWE_NAAM, voornaamHistorie.getActueleRecord().getNaam().getWaarde());
            } else if (voornaamHisVolledig.getVolgnummer().getWaarde() == 2) {
                // Voornaam 2 is vervallen.
                Assert.assertEquals(1, voornaamHistorie.getAantal());
                Assert.assertNotNull(voornaamHistorie.iterator().next().getDatumTijdVerval());
            }
        }
    }

    @Test
    public void testVerwerking2VoornamenBericht1VoornamenHisVolledig() {
        PersoonHisVolledigImpl persoonHisVolledig = bouwPersoonHisVolledig(1);
        VoornamenVerwerker verwerker = new VoornamenVerwerker(bouwPersoon(2), persoonHisVolledig, bouwActie());
        verwerker.neemBerichtDataOverInModel();

        Assert.assertEquals(2, persoonHisVolledig.getVoornamen().size());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        VoornamenVerwerker verwerker = new VoornamenVerwerker(null, null, null);
        verwerker.verzamelAfleidingsregels();
        Assert.assertEquals(1, verwerker.getAfleidingsregels().size());
        Assert.assertTrue(verwerker.getAfleidingsregels().get(0) instanceof SamengesteldeNaamAfleiding);
    }

    private PersoonBericht bouwPersoon(final int aantalVoornamen) {
        PersoonBericht persoon = new PersoonBericht();
        final List<PersoonVoornaamBericht> voornamen = new ArrayList<>();
        for (int i = 1; i <= aantalVoornamen; i++) {
            PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
            voornaam.setVolgnummer(new VolgnummerAttribuut(i));
            voornaam.setStandaard(bouwVoornaamStandaardGroep());
            voornamen.add(voornaam);
        }
        persoon.setVoornamen(voornamen);
        return persoon;
    }

    private PersoonVoornaamStandaardGroepBericht bouwVoornaamStandaardGroep() {
        PersoonVoornaamStandaardGroepBericht voornaam = new PersoonVoornaamStandaardGroepBericht();
        voornaam.setNaam(new VoornaamAttribuut(NIEUWE_NAAM));
        voornaam.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DATUM_AANVANG_GELDIGHEID_ACTIE));
        return voornaam;
    }

    private PersoonHisVolledigImpl bouwPersoonHisVolledig(final int aantalVoornamen) {
        PersoonHisVolledigImpl persoonHisVolledigImpl = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        for (int i = 1; i <= aantalVoornamen; i++) {
            persoonHisVolledigImpl.getVoornamen().add(new PersoonVoornaamHisVolledigImplBuilder(persoonHisVolledigImpl, new VolgnummerAttribuut(i))
                    .nieuwStandaardRecord(DATUM_AANVANG_GELDIGHEID_VOORNAMEN[i - 1], null, DATUM_AANVANG_GELDIGHEID_VOORNAMEN[i - 1])
                        .naam(OUDE_NAAM)
                    .eindeRecord().build());
        }
        return persoonHisVolledigImpl;
    }

    /**
     * Creeer een standaard actie.
     *
     * @return het actie model
     */
    private ActieModel bouwActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_AFSTAMMING),
                              new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                      SoortAdministratieveHandeling.CORRECTIE_AFSTAMMING), null,
                                      null, null), null, new DatumEvtDeelsOnbekendAttribuut(DATUM_AANVANG_GELDIGHEID_ACTIE), null, DatumTijdAttribuut.nu(), null);
    }
}
