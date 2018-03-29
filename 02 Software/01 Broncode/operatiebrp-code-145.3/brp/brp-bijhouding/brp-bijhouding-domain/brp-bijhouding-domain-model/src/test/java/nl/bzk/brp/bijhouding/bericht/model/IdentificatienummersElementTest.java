/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Test;

/**
 */
public class IdentificatienummersElementTest extends AbstractElementTest {
    private static final String[] GELDIGE_A_NUMMERS_2013 = {"5473260818",
            "6089873170",
            "5673795346",
            "7092472594",
            "4173726482",
            "5637350162",
            "8675047698",
            "9430380818",
            "7638729490",
            "1828427282",
            "7015636754",
            "3757971730"};
    private static final String[] GELDIGE_A_NUMMERS = {"4038058529",
            "6957831457",
            "8372010273",
            "5864602913",
            "9232342305",
            "5424197921",
            "8535896353",
            "9406523681",
            "1397515841",
            "4516432161",
            "2737647521",
            "8302838561"};
    private static final String[] ONGELDIGE_A_NUMMERS = {"4884033825",
            "2754086689",
            "9587329825",
            "2534322081",
            "8543053089",
            "7679693089",
            "8292644641",
            "5192589857",
            "1978249953",
            "8621895457",
            "7513737505",
            "2506116001"};
    private static final String[] GELDIGE_BSN = {"111222333", "123456782", "882134061", "089444917"};
    private static final String[] ONGELDIGE_BSN = {"762614409", "903513865", "141048537", "141"};

    @Test
    public void valideerBSNnummerGeldig() throws Exception {
        test("591940681", "8675047698", AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
        int i = 0;
        for (String bsn : GELDIGE_BSN) {
            test(bsn, "8675047698", AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
            i++;
        }
        assertEquals(i, GELDIGE_BSN.length);
    }

    @Test
    public void valideerBSNnummerOngeldig() throws Exception {
        test("591940681", "8675047698", AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
        int i = 0;
        for (final String bsn : ONGELDIGE_BSN) {
            test(bsn, "8675047698", AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND, Regel.R1587);
            i++;
        }
        assertEquals(i, ONGELDIGE_BSN.length);
    }

    @Test
    public void valideerAnummer() throws Exception {
        // correct
        test("591940681", "8675047698", AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
        // begint met 0
        test("591940681", "08675047698", AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND, Regel.R1585);
        // twee dezelfde cijfers
        test("591940681", "7734628529", AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND, Regel.R1585);
        test("591940681", "7934628599", AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND, Regel.R1585);
        // alles opgeteld en delen door 11 geeft rest 5 of 0 (2)
        test("591940681", "6734628523", AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND, Regel.R1585);
        // (1*a[0])+(2*a[1])+(4*a[2])+.+(512*a[9]) is deelbaar door 11
        test("591940681", "4935964135", AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND, Regel.R1585);

    }

    @Test
    public void valideerAnummerBulkGoed() {
        int i = 0;
        for (String anummer : GELDIGE_A_NUMMERS) {
            test("591940681", anummer, AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
            i++;
        }
        assertEquals(i, GELDIGE_A_NUMMERS.length);
    }

    @Test
    public void valideerAnummerBulkGoed_GBA_2013() {
        int i = 0;
        for (String anummer : GELDIGE_A_NUMMERS_2013) {
            test("591940681", anummer, AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
            i++;
        }
        assertEquals(i, GELDIGE_A_NUMMERS.length);
    }

    @Test
    public void valideerAnummerBulkNietGoed() {
        int i = 0;
        for (String anummer : ONGELDIGE_A_NUMMERS) {
            test("591940681", anummer, AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND, Regel.R1585);
            i++;
        }
        assertEquals(i, ONGELDIGE_A_NUMMERS.length);
    }

    @Test
    public void testMaakPersoonIDHistorieEntiteit() {
        final String bsn = "111222333";
        final String anummer = "4038058529";

        final IdentificatienummersElement element = test(bsn, anummer, AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);

        final int datumAanvangGeldigheid = 20010101;
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.voegPersoonIDHistorieToe(element, getActie(), datumAanvangGeldigheid);
        final PersoonIDHistorie entiteit = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonIDHistorieSet());
        assertEquals(bsn, entiteit.getBurgerservicenummer());
        assertEquals(anummer, entiteit.getAdministratienummer());
        assertEquals(1, persoon.getPersoonIDHistorieSet().size());
        assertEquals(entiteit, persoon.getPersoonIDHistorieSet().iterator().next());
        assertEntiteitMetMaterieleHistorie(entiteit, datumAanvangGeldigheid);
    }

    @Test
    public void valideerAnummerEnBsnNull() {
        test(null, null, AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND, Regel.R2458);
    }

    @Test
    public void valideerAnummerEnBsnNull_Correctie() {
        test(null, null, AdministratieveHandelingElementSoort.CORRECTIE_PARTNERGEGEVENS_HUWELIJK);
    }

    @Test
    public void valideerAnummerNullEnBsnNotNull() {
        test("111222333", null, AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
    }

    @Test
    public void valideerAnummerNotNullEnBsnNull() {
        test(null, "4038058529", AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
    }

    @Test
    public void testGetInstance() {
        //setup
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(new Persoon(SoortPersoon.INGESCHREVENE));
        final BijhoudingVerzoekBericht verzoekBericht = mock(BijhoudingVerzoekBericht.class);
        //execute
        IdentificatienummersElement identificatienummersElement = IdentificatienummersElement.getInstance(idHistorie, verzoekBericht);
        assertNull(identificatienummersElement.getAdministratienummer());
        assertNull(identificatienummersElement.getBurgerservicenummer());
        //setup anummer bsn
        idHistorie.setAdministratienummer("9876543210");
        idHistorie.setBurgerservicenummer("123456789");
        //execute
        identificatienummersElement = IdentificatienummersElement.getInstance(idHistorie, verzoekBericht);
        //verify
        assertEquals(String.valueOf(idHistorie.getAdministratienummer()), BmrAttribuut.getWaardeOfNull(identificatienummersElement.getAdministratienummer()));
        assertEquals(String.valueOf(idHistorie.getBurgerservicenummer()), BmrAttribuut.getWaardeOfNull(identificatienummersElement.getBurgerservicenummer()));
        //verify null voorkomen
        assertNull(IdentificatienummersElement.getInstance(null, verzoekBericht));
    }

    private IdentificatienummersElement test(final String bsn, final String aNummer, final AdministratieveHandelingElementSoort soortAH, final Regel... regel) {
        final ElementBuilder builder = new ElementBuilder();
        final IdentificatienummersElement element = builder.maakIdentificatienummersElement("ci_test", bsn, aNummer);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        final ActieElement actie = mock(ActieElement.class);
        ahParams.acties(Collections.singletonList(actie));
        ahParams.soort(soortAH);
        ahParams.partijCode("052901");
        final AdministratieveHandelingElement ah = builder.maakAdministratieveHandelingElement("ahElement", ahParams);
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        when(bericht.getAdministratieveHandeling()).thenReturn(ah);
        element.setVerzoekBericht(bericht);

        controleerRegels(element.valideerInhoud(), regel);
        return element;
    }
}
