/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Unit test voor de ActieFactory. */
public class ActieFactoryTest {

    private ActieFactory actieFactory;

    @Before
    public void init() {
        actieFactory = new ActieFactory();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetActieUitvoerderVoorDummy() {
        actieFactory.getActieUitvoerder(null, SoortActie.DUMMY);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetActieUitvoerderVoorFouteAdmHandBijGeboorteActie() {
        actieFactory.getActieUitvoerder(SoortAdministratieveHandeling.DUMMY, SoortActie.REGISTRATIE_GEBOORTE);
    }

    @Test
    public void testGetActieUitvoerder() {
        ActieUitvoerder<?> actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_ADRES);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieAdresUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.CORRECTIE_ADRES);
        Assert.assertTrue(actieUitvoerder instanceof CorrectieAdresUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND,
            SoortActie.REGISTRATIE_GEBOORTE);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieGeboorteUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(
            SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND_MET_ERKENNING, SoortActie.REGISTRATIE_GEBOORTE);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieGeboorteUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(
            SoortAdministratieveHandeling.TOEVOEGING_GEBOORTEAKTE, SoortActie.REGISTRATIE_GEBOORTE);
        Assert.assertTrue(actieUitvoerder instanceof ActualiseringAfstammingUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(
            SoortAdministratieveHandeling.VERBETERING_GEBOORTEAKTE, SoortActie.REGISTRATIE_GEBOORTE);
        Assert.assertTrue(actieUitvoerder instanceof ActualiseringAfstammingUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_BEHANDELD_ALS_NEDERLANDER);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieBehandeldAlsNederlanderUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_STAATLOOS);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieStaatloosUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_OUDER);
        Assert.assertTrue(actieUitvoerder instanceof ActualiseringAfstammingUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_OUDER);
        Assert.assertTrue(actieUitvoerder instanceof ActualiseringAfstammingUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_VOORNAAM);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieNaamGeslachtUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_GESLACHTSNAAM);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieNaamGeslachtUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_NATIONALITEIT);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieNationaliteitUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_AANVANG_HUWELIJK_GEREGISTREERD_PARTNERSCHAP);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_NAAMGEBRUIK);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieNaamgebruikUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_OVERLIJDEN);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieOverlijdenUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_BIJHOUDING);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieBijhoudingUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_NATIONALITEIT_NAAM);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieNationaliteitNaamUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null, SoortActie.REGISTRATIE_VASTGESTELD_NIET_NEDERLANDER);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieVastgesteldNietNederlanderUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null,
                                                          SoortActie.REGISTRATIE_SIGNALERING_REISDOCUMENT);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieSignaleringReisdocument);

        actieUitvoerder = actieFactory.getActieUitvoerder(null,
                                                          SoortActie.REGISTRATIE_CURATELE);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieCurateleUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null,
                SoortActie.REGISTRATIE_GEZAG);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieGezagUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null,
                                                          SoortActie.REGISTRATIE_REISDOCUMENT);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieReisdocumentUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null,
                SoortActie.REGISTRATIE_UITSLUITING_KIESRECHT);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieUitsluitingKiesrechtUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null,
                SoortActie.REGISTRATIE_DEELNAME_E_U_VERKIEZINGEN);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieDeelnameEUVerkiezingenUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null,
                SoortActie.REGISTRATIE_MIGRATIE);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieMigratieUitvoerder);

        actieUitvoerder = actieFactory.getActieUitvoerder(null,
                SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS);
        Assert.assertTrue(actieUitvoerder instanceof RegistratieIdentificatienummersUitvoerder);
    }

    @Test
    public void testNogNietOndersteundeActies() {
        final List<SoortActie> nietOndersteundeActies = Arrays.asList(SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER,
            SoortActie.BEEINDIGING_NATIONALITEIT, SoortActie.BEEINDIGING_OUDERSCHAP,
            SoortActie.BEEINDIGING_VERSTREKKINGSBEPERKING, SoortActie.BEEINDIGING_VOORNAAM,
            SoortActie.CONVERSIE_G_B_A, SoortActie.CORRECTIE_AFSTAMMING,
            SoortActie.CORRECTIE_GEREGISTREERD_PARTNERSCHAP, SoortActie.CORRECTIE_HUWELIJK,
            SoortActie.CORRECTIE_OVERLIJDEN, SoortActie.REGISTRATIE_AFSTAMMING,
            SoortActie.REGISTRATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE,
            SoortActie.VERVAL_HUWELIJK_GEREGISTREERD_PARTNERSCHAP, SoortActie.VERVAL_OVERLIJDEN,
            SoortActie.REGISTRATIE_ONDERZOEK);

        for (SoortActie soort : nietOndersteundeActies) {
            try {
                actieFactory.getActieUitvoerder(null, soort);
                Assert.fail(String.format("De ActieSoort '%s' wordt ondersteunt terwijl dat niet was verwacht.",
                    soort));
            } catch (IllegalArgumentException e) {
                Assert.assertNotNull(e.getMessage());
            }
        }
    }
}
