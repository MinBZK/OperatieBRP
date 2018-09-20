/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsnaamcomponent.GeslachtsnaamcomponentVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.NationaliteitVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.voornaam.VoornamenVerwerker;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitNaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 */
public class RegistratieNationaliteitNaamUitvoerderTest {

    @Mock
    private BijhoudingBerichtContext context;
    @Mock
    private ActieModel               actie;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(actie.getDatumAanvangGeldigheid()).thenReturn(
            new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        Mockito.when(actie.getTijdstipRegistratie()).thenReturn(DatumTijdAttribuut.nu());
    }

    @Test
    public void testVerzamelVerwerkingsregels() throws Exception {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificerendeSleutel("123");
        persoonBericht.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());

        final PersoonNationaliteitBericht persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        persoonNationaliteitBericht.setStandaard(new PersoonNationaliteitStandaardGroepBericht());
        persoonNationaliteitBericht.getStandaard().setDatumAanvangGeldigheid(
            new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        persoonNationaliteitBericht.getStandaard().setDatumTijdRegistratie(DatumTijdAttribuut.nu());
        persoonBericht.getNationaliteiten().add(persoonNationaliteitBericht);

        persoonBericht.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
        final PersoonVoornaamBericht persoonVoornaamBericht = new PersoonVoornaamBericht();
        persoonVoornaamBericht.setVolgnummer(new VolgnummerAttribuut(1));
        persoonVoornaamBericht.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        persoonVoornaamBericht.getStandaard().setDatumAanvangGeldigheid(
            new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        persoonVoornaamBericht.getStandaard().setDatumTijdRegistratie(DatumTijdAttribuut.nu());
        persoonBericht.getVoornamen().add(persoonVoornaamBericht);
        persoonBericht.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());

        final PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponentBericht =
                new PersoonGeslachtsnaamcomponentBericht();
        geslachtsnaamcomponentBericht.setVolgnummer(new VolgnummerAttribuut(1));
        geslachtsnaamcomponentBericht.setStandaard(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        geslachtsnaamcomponentBericht.getStandaard().setDatumAanvangGeldigheid(
                new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        geslachtsnaamcomponentBericht.getStandaard().setDatumTijdRegistratie(DatumTijdAttribuut.nu());
        persoonBericht.getGeslachtsnaamcomponenten().add(geslachtsnaamcomponentBericht);

        ActieBericht actieBericht = new ActieRegistratieNationaliteitNaamBericht();
        actieBericht.setRootObject(persoonBericht);

        PersoonHisVolledigImpl bestaandPersoon =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        Mockito.when(context.zoekHisVolledigRootObject(persoonBericht)).thenReturn(bestaandPersoon);

        final RegistratieNationaliteitNaamUitvoerder uitvoerder =
                new RegistratieNationaliteitNaamUitvoerder();
        ReflectionTestUtils.setField(uitvoerder, "berichtRootObject", persoonBericht);
        ReflectionTestUtils.setField(uitvoerder, "modelRootObject", bestaandPersoon);
        ReflectionTestUtils.setField(uitvoerder, "context", context);
        ReflectionTestUtils.setField(uitvoerder, "actieModel", actie);
        uitvoerder.setActieBericht(actieBericht);
        uitvoerder.voerActieUit();

        @SuppressWarnings("unchecked")
        List<Verwerkingsregel> verwerkingsregels =
                (List<Verwerkingsregel>) ReflectionTestUtils.getField(uitvoerder, "verwerkingsregels");

        Assert.assertEquals(3, verwerkingsregels.size());

        Assert.assertTrue(verwerkingsregels.get(0) instanceof NationaliteitVerwerker);
        Assert.assertTrue(verwerkingsregels.get(1) instanceof VoornamenVerwerker);
        Assert.assertTrue(verwerkingsregels.get(2) instanceof GeslachtsnaamcomponentVerwerker);
    }
}
