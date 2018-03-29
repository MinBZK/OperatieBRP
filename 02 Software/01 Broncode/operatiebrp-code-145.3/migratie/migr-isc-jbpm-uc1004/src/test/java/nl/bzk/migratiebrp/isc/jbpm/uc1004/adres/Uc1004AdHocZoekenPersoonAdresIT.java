/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.adres;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xa01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractUcTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * IntegratieTest voor uc1004 ad hoc zoeken persoon.
 */
@ContextConfiguration("classpath:/uc1004-test-beans.xml")
public class Uc1004AdHocZoekenPersoonAdresIT extends AbstractUcTest {

    private static final String ACHTERNAAM = "Jansen";
    private static final int BSN = 123456789;
    private static final long A_NUMMER = 1234567890L;
    private static final String AFNEMER = "059901";
    private static final String ONTVANGER = "199902";
    private static final String POSTCODE = "2994HA";
    private static final List<String> GEVRAAGDE_GEGEVENS = Arrays.asList("010110", "010120", "010240", "081160");
    private static final String LO3_PL = "00069010460110010123456789001200091234567890240006Jansen0801311600062994HA";
    private static final String LO3_BERICHT = "00000000Xa01" + LO3_PL;

    public Uc1004AdHocZoekenPersoonAdresIT() {
        super("/uc1004-adres/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Before
    public void setupPartijRegister() {
        // Partij register
        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("059901", "0599", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("042901", "0429", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE)));
        partijen.add(new Partij("051801", "0518", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("199902", "1999", intToDate(19900101), Collections.emptyList()));
        partijen.add(new Partij("071701", "0717", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        setPartijRegister(new PartijRegisterImpl(partijen));
    }

    @After
    public void endProces() {
        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void persoonGevonden() throws BerichtInhoudException {
        startProcess(maakXq01Bericht(AFNEMER, ONTVANGER, A_NUMMER, BSN, ACHTERNAAM, POSTCODE, GEVRAAGDE_GEGEVENS));
        controleerBerichten(0, 0, 1);
        final AdHocZoekPersonenOpAdresVerzoekBericht verzoek = getBericht(AdHocZoekPersonenOpAdresVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De gevraagde rubrieken", GEVRAAGDE_GEGEVENS, verzoek.getGevraagdeRubrieken());
        Assert.assertEquals("Identificerende gegevens", "00069010460110010123456789001200091234567890240006Jansen0801311600062994HA",
                verzoek.getIdentificerendeGegevens());

        final AdHocZoekPersonenOpAdresAntwoordBericht antwoord = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoord.setCorrelationId(verzoek.getMessageId());
        antwoord.setInhoud(LO3_BERICHT);
        signalSync(antwoord);

        controleerBerichten(0, 1, 0);
        final Xa01Bericht xa01 = getBericht(Xa01Bericht.class);
        Assert.assertEquals("Categorieen moeten correct zijn", LO3_PL, Lo3Inhoud.formatInhoud(xa01.getCategorieen()));
    }

    @Test
    public void afnemerIsGeenAfnemer() throws BerichtInhoudException {
        startProcess(maakXq01Bericht("042901", ONTVANGER, A_NUMMER, BSN, ACHTERNAAM, POSTCODE, GEVRAAGDE_GEGEVENS));
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);
    }

    @Test
    public void ontvangerIsNietCentrale() throws BerichtInhoudException {
        startProcess(maakXq01Bericht(AFNEMER, "071701", A_NUMMER, BSN, ACHTERNAAM, POSTCODE, GEVRAAGDE_GEGEVENS));
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);
    }

    @Test
    public void afbrekenNaZoekVraag() throws BerichtInhoudException {
        startProcess(maakXq01Bericht(AFNEMER, ONTVANGER, A_NUMMER, BSN, ACHTERNAAM, POSTCODE, GEVRAAGDE_GEGEVENS));
        controleerBerichten(0, 0, 1);
        final AdHocZoekPersonenOpAdresVerzoekBericht verzoek = getBericht(AdHocZoekPersonenOpAdresVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De gevraagde rubrieken", GEVRAAGDE_GEGEVENS, verzoek.getGevraagdeRubrieken());
        Assert.assertEquals("Identificerende gegevens", "00069010460110010123456789001200091234567890240006Jansen0801311600062994HA",
                verzoek.getIdentificerendeGegevens());
        signalProcess("3c. afbreken");
        signalHumanTask("end");
        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);
    }

    @Test
    public void onverwachtAntwoordOpZoekVraag() throws BerichtInhoudException {
        startProcess(maakXq01Bericht(AFNEMER, ONTVANGER, A_NUMMER, BSN, ACHTERNAAM, POSTCODE, GEVRAAGDE_GEGEVENS));
        controleerBerichten(0, 0, 1);
        final AdHocZoekPersonenOpAdresVerzoekBericht verzoek = getBericht(AdHocZoekPersonenOpAdresVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De gevraagde rubrieken", GEVRAAGDE_GEGEVENS, verzoek.getGevraagdeRubrieken());
        Assert.assertEquals("Identificerende gegevens", "00069010460110010123456789001200091234567890240006Jansen0801311600062994HA",
                verzoek.getIdentificerendeGegevens());

        signalSync(maakOngeldigBericht(verzoek));
        signalHumanTask("endWithoutPf03");
    }

    @Test
    public void onverwachtAntwoordOpZoekVraagOpnieuwAanbieden() throws BerichtInhoudException {
        startProcess(maakXq01Bericht(AFNEMER, ONTVANGER, A_NUMMER, BSN, ACHTERNAAM, POSTCODE, GEVRAAGDE_GEGEVENS));
        controleerBerichten(0, 0, 1);
        final AdHocZoekPersonenOpAdresVerzoekBericht verzoek = getBericht(AdHocZoekPersonenOpAdresVerzoekBericht.class);

        Assert.assertEquals("De gevraagde rubrieken", GEVRAAGDE_GEGEVENS, verzoek.getGevraagdeRubrieken());
        Assert.assertEquals("Identificerende gegevens", "00069010460110010123456789001200091234567890240006Jansen0801311600062994HA",
                verzoek.getIdentificerendeGegevens());

        signalSync(maakOngeldigBericht(verzoek));
        signalHumanTask("restartAtZoekenPersoonAdres");

        controleerBerichten(0, 0, 1);
        final AdHocZoekPersonenOpAdresVerzoekBericht tweedeVerzoek = getBericht(AdHocZoekPersonenOpAdresVerzoekBericht.class);

        Assert.assertEquals("De gevraagde rubrieken", GEVRAAGDE_GEGEVENS, tweedeVerzoek.getGevraagdeRubrieken());
        Assert.assertEquals("Identificerende gegevens", "00069010460110010123456789001200091234567890240006Jansen0801311600062994HA",
                tweedeVerzoek.getIdentificerendeGegevens());

        final AdHocZoekPersonenOpAdresAntwoordBericht antwoord = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoord.setCorrelationId(verzoek.getMessageId());
        antwoord.setInhoud(LO3_BERICHT);
        signalSync(antwoord);

        controleerBerichten(0, 1, 0);
        final Xa01Bericht xa01 = getBericht(Xa01Bericht.class);
        Assert.assertEquals("Categorieen moeten correct zijn", LO3_PL, Lo3Inhoud.formatInhoud(xa01.getCategorieen()));

    }

    @Test
    public void persoonNietGevonden() throws BerichtInhoudException {
        startProcess(maakXq01Bericht(AFNEMER, ONTVANGER, A_NUMMER, BSN, ACHTERNAAM, POSTCODE, GEVRAAGDE_GEGEVENS));
        controleerBerichten(0, 0, 1);
        final AdHocZoekPersonenOpAdresVerzoekBericht verzoek = getBericht(AdHocZoekPersonenOpAdresVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De gevraagde rubrieken", GEVRAAGDE_GEGEVENS, verzoek.getGevraagdeRubrieken());
        Assert.assertEquals("Identificerende gegevens", "00069010460110010123456789001200091234567890240006Jansen0801311600062994HA",
                verzoek.getIdentificerendeGegevens());

        final AdHocZoekPersonenOpAdresAntwoordBericht antwoord = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoord.setCorrelationId(verzoek.getMessageId());
        antwoord.setFoutreden(AdHocZoekAntwoordFoutReden.G);
        signalSync(antwoord);

        controleerBerichten(0, 1, 0);
        final Xf01Bericht xf01 = getBericht(Xf01Bericht.class);
        Assert.assertEquals("Gevraagde rubrieken moeten overeenkomen", GEVRAAGDE_GEGEVENS, xf01.getHeaderWaarden(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN));
        Assert.assertEquals("Identificerende gegevens moeten overeenkomen", "00069010460110010123456789001200091234567890240006Jansen0801311600062994HA",
                Lo3Inhoud.formatInhoud(xf01.getCategorieen()));
        Assert.assertEquals("Foutreden moet G zijn", "G", xf01.getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN));
    }

    @Test
    public void zoekenNietGeautoriseerd() throws BerichtInhoudException {
        startProcess(maakXq01Bericht(AFNEMER, ONTVANGER, A_NUMMER, BSN, ACHTERNAAM, POSTCODE, GEVRAAGDE_GEGEVENS));
        controleerBerichten(0, 0, 1);
        final AdHocZoekPersonenOpAdresVerzoekBericht verzoek = getBericht(AdHocZoekPersonenOpAdresVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De gevraagde rubrieken", GEVRAAGDE_GEGEVENS, verzoek.getGevraagdeRubrieken());
        Assert.assertEquals("Identificerende gegevens", "00069010460110010123456789001200091234567890240006Jansen0801311600062994HA",
                verzoek.getIdentificerendeGegevens());

        final AdHocZoekPersonenOpAdresAntwoordBericht antwoord = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoord.setCorrelationId(verzoek.getMessageId());
        antwoord.setFoutreden(AdHocZoekAntwoordFoutReden.X);
        signalSync(antwoord);

        controleerBerichten(0, 1, 0);
        final Xf01Bericht xf01 = getBericht(Xf01Bericht.class);
        Assert.assertEquals("Gevraagde rubrieken moeten overeenkomen", GEVRAAGDE_GEGEVENS, xf01.getHeaderWaarden(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN));
        Assert.assertEquals("Identificerende gegevens moeten overeenkomen", "00069010460110010123456789001200091234567890240006Jansen0801311600062994HA",
                Lo3Inhoud.formatInhoud(xf01.getCategorieen()));
        Assert.assertEquals("Foutreden moet X zijn", "X", xf01.getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN));

        signalHumanTask("end");
    }

    @Test
    public void zoekenTechnischeFoutBackend() throws BerichtInhoudException {
        startProcess(maakXq01Bericht(AFNEMER, ONTVANGER, A_NUMMER, BSN, ACHTERNAAM, POSTCODE, GEVRAAGDE_GEGEVENS));
        controleerBerichten(0, 0, 1);
        final AdHocZoekPersonenOpAdresVerzoekBericht verzoek = getBericht(AdHocZoekPersonenOpAdresVerzoekBericht.class);
        // controle verzoek
        Assert.assertEquals("De gevraagde rubrieken", GEVRAAGDE_GEGEVENS, verzoek.getGevraagdeRubrieken());
        Assert.assertEquals("Identificerende gegevens", "00069010460110010123456789001200091234567890240006Jansen0801311600062994HA",
                verzoek.getIdentificerendeGegevens());

        final AdHocZoekPersonenOpAdresAntwoordBericht antwoord = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoord.setCorrelationId(verzoek.getMessageId());
        antwoord.setFoutreden(AdHocZoekAntwoordFoutReden.F);
        signalSync(antwoord);

        controleerBerichten(0, 1, 0);
        final Pf03Bericht pf03 = getBericht(Pf03Bericht.class);
        Assert.assertNotNull(pf03);
    }

    private Xq01Bericht maakXq01Bericht(final String afnemerCode, final String ontvangerCode, final Long aNummer, final Integer bsn, final String geslachtsnaam,
                                        final String postcode, final List<String> gevraagderubrieken) throws BerichtInhoudException {
        final Xq01Bericht xq01Bericht = new Xq01Bericht();
        xq01Bericht.setBronPartijCode(afnemerCode);
        xq01Bericht.setDoelPartijCode(ontvangerCode);
        xq01Bericht.setHeader(Lo3HeaderVeld.AANTAL, String.format("%03d", gevraagderubrieken.size()));
        xq01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, gevraagderubrieken.stream().collect(Collectors.joining()));
        xq01Bericht.setHeader(Lo3HeaderVeld.ADRESFUNCTIE, "A");
        xq01Bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, "P");

        if (aNummer != null || bsn != null || geslachtsnaam != null || postcode != null) {
            xq01Bericht.setCategorieen(maakCategorieen(aNummer, bsn, geslachtsnaam, postcode));
        }
        return xq01Bericht;
    }

    private List<Lo3CategorieWaarde> maakCategorieen(final Long aNummer, final Integer bsn, final String geslachtsnaam, final String postcode) {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        if (aNummer != null || bsn != null || geslachtsnaam != null) {
            final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
            if (aNummer != null) {
                categorieWaarde.addElement(Lo3ElementEnum.ANUMMER, String.valueOf(aNummer));
            }
            if (bsn != null) {
                categorieWaarde.addElement(Lo3ElementEnum.BURGERSERVICENUMMER, String.valueOf(bsn));
            }
            if (geslachtsnaam != null) {
                categorieWaarde.addElement(Lo3ElementEnum.GESLACHTSNAAM, geslachtsnaam);
            }
            categorieen.add(categorieWaarde);
        }

        if (postcode != null) {
            final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 0, 0);
            categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1160, postcode);
            categorieen.add(categorieWaarde);
        }

        return categorieen;
    }
}
