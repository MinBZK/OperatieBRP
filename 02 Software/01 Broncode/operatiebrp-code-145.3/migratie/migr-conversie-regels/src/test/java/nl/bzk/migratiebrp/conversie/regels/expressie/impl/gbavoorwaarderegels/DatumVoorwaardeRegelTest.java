/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test voor de DatumVoorwaardeRegel.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class DatumVoorwaardeRegelTest {

    @Inject
    private DatumVoorwaardeRegel instance;

    private VoorwaardeRegelTestUtil testUtil;

    @Before
    public void initialize() {
        testUtil = new VoorwaardeRegelTestUtil(instance);
    }

    @Test
    public void testOnbekendDatumGedeelten() throws Exception {
        testUtil.testVoorwaarde("01.03.10 GA1 00000000", "Persoon.Geboorte.Datum E= ?/?/0");
        testUtil.testVoorwaarde("01.03.10 GA1 19330000", "Persoon.Geboorte.Datum E= 1933/?/?");
        testUtil.testVoorwaarde("01.03.10 GA1 19330700", "Persoon.Geboorte.Datum E= 1933/07/?");
        testUtil.testVoorwaarde("01.03.10 GDOG1 19330700", "Persoon.Geboorte.Datum E>= 1933/07/?");
        testUtil.testVoorwaarde("01.03.10 GDOGA 19330700", "Persoon.Geboorte.Datum A>= 1933/07/?");
        testUtil.testVoorwaarde("08.10.30 GD1 19330700", "Persoon.Adres.DatumAanvangAdreshouding E> 1933/07/?");
    }

    @Test
    public void testGD1() throws Exception {
        testUtil.testVoorwaarde("01.03.10 GD1 19800301", "Persoon.Geboorte.Datum E> 1980/03/01");
        testUtil.testVoorwaarde("08.10.30 GD1 19800301", "Persoon.Adres.DatumAanvangAdreshouding E> 1980/03/01");
        testUtil.testVoorwaarde("01.03.10 GD1 19800301 - 00290000", "Persoon.Geboorte.Datum E> 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("01.03.10 GD1 19800301 - 00090000", "Persoon.Geboorte.Datum E> 1980/03/01 - ^9/0/0");
        testUtil.testVoorwaarde("08.10.30 GD1 19800301 - 00290507", "Persoon.Adres.DatumAanvangAdreshouding E> 1980/03/01 - ^29/5/7");
        testUtil.testVoorwaarde("01.03.10 GD1 19800301 - 0029", "Persoon.Geboorte.Datum E> 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("08.10.30 GD1 19800301 - 0029", "Persoon.Adres.DatumAanvangAdreshouding E> 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("01.03.10 GD1 19800301 - 002900", "Persoon.Geboorte.Datum E> 1980/03/01 - ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 GD1 19800301 - 002905", "Persoon.Adres.DatumAanvangAdreshouding E> 1980/03/01 - ^29/5/?");
    }

    @Test
    public void testKD1() throws Exception {
        testUtil.testVoorwaarde("01.03.10 KD1 19800301", "Persoon.Geboorte.Datum E< 1980/03/01");
        testUtil.testVoorwaarde("08.10.30 KD1 19800301", "Persoon.Adres.DatumAanvangAdreshouding E< 1980/03/01");
        testUtil.testVoorwaarde("01.03.10 KD1 19800301 - 00290000", "Persoon.Geboorte.Datum E< 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("08.10.30 KD1 19800301 - 00290507", "Persoon.Adres.DatumAanvangAdreshouding E< 1980/03/01 - ^29/5/7");
        testUtil.testVoorwaarde("01.03.10 KD1 19800301 - 0029", "Persoon.Geboorte.Datum E< 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("08.10.30 KD1 19800301 - 0029", "Persoon.Adres.DatumAanvangAdreshouding E< 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("01.03.10 KD1 19800301 - 002900", "Persoon.Geboorte.Datum E< 1980/03/01 - ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 KD1 19800301 - 002905", "Persoon.Adres.DatumAanvangAdreshouding E< 1980/03/01 - ^29/5/?");
    }

    @Test
    public void testGDOG1() throws Exception {
        testUtil.testVoorwaarde("01.03.10 GDOG1 19800301", "Persoon.Geboorte.Datum E>= 1980/03/01");
        testUtil.testVoorwaarde("08.10.30 GDOG1 19800301", "Persoon.Adres.DatumAanvangAdreshouding E>= 1980/03/01");
        testUtil.testVoorwaarde("01.03.10 GDOG1 19800301 - 00290000", "Persoon.Geboorte.Datum E>= 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("08.10.30 GDOG1 19800301 - 00290000", "Persoon.Adres.DatumAanvangAdreshouding E>= 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("01.03.10 GDOG1 19800301 - 0029", "Persoon.Geboorte.Datum E>= 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("08.10.30 GDOG1 19800301 - 0029", "Persoon.Adres.DatumAanvangAdreshouding E>= 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("01.03.10 GDOG1 19800301 - 002900", "Persoon.Geboorte.Datum E>= 1980/03/01 - ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 GDOG1 19800301 - 002905", "Persoon.Adres.DatumAanvangAdreshouding E>= 1980/03/01 - ^29/5/?");
        testUtil.testVoorwaarde("01.03.10 GDOG1 19800301 + 002900", "Persoon.Geboorte.Datum E>= 1980/03/01 + ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 GDOG1 19800301 + 002905", "Persoon.Adres.DatumAanvangAdreshouding E>= 1980/03/01 + ^29/5/?");
    }

    @Test
    public void testKDOG1() throws Exception {
        testUtil.testVoorwaarde("01.03.10 KDOG1 19800301", "Persoon.Geboorte.Datum E<= 1980/03/01");
        testUtil.testVoorwaarde("08.10.30 KDOG1 19800301", "Persoon.Adres.DatumAanvangAdreshouding E<= 1980/03/01");
        testUtil.testVoorwaarde("01.03.10 KDOG1 19800301 - 00290000", "Persoon.Geboorte.Datum E<= 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("08.10.30 KDOG1 19800301 - 00290000", "Persoon.Adres.DatumAanvangAdreshouding E<= 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("01.03.10 KDOG1 19800301 - 0029", "Persoon.Geboorte.Datum E<= 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("08.10.30 KDOG1 19800301 - 0029", "Persoon.Adres.DatumAanvangAdreshouding E<= 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("01.03.10 KDOG1 19800301 - 002900", "Persoon.Geboorte.Datum E<= 1980/03/01 - ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 KDOG1 19800301 - 002905", "Persoon.Adres.DatumAanvangAdreshouding E<= 1980/03/01 - ^29/5/?");
        testUtil.testVoorwaarde("01.03.10 KDOG1 19800301 + 002900", "Persoon.Geboorte.Datum E<= 1980/03/01 + ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 KDOG1 19800301 + 002905", "Persoon.Adres.DatumAanvangAdreshouding E<= 1980/03/01 + ^29/5/?");
    }

    @Test
    public void testGDA() throws Exception {
        testUtil.testVoorwaarde("01.03.10 GDA 19800301", "Persoon.Geboorte.Datum A> 1980/03/01");
        testUtil.testVoorwaarde("08.10.30 GDA 19800301", "Persoon.Adres.DatumAanvangAdreshouding A> 1980/03/01");
        testUtil.testVoorwaarde("01.03.10 GDA 19800301 - 00290000", "Persoon.Geboorte.Datum A> 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("08.10.30 GDA 19800301 - 00290000", "Persoon.Adres.DatumAanvangAdreshouding A> 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("01.03.10 GDA 19800301 - 0029", "Persoon.Geboorte.Datum A> 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("08.10.30 GDA 19800301 - 0029", "Persoon.Adres.DatumAanvangAdreshouding A> 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("01.03.10 GDA 19800301 - 002900", "Persoon.Geboorte.Datum A> 1980/03/01 - ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 GDA 19800301 - 002905", "Persoon.Adres.DatumAanvangAdreshouding A> 1980/03/01 - ^29/5/?");
        testUtil.testVoorwaarde("01.03.10 GDA 19800301 + 002900", "Persoon.Geboorte.Datum A> 1980/03/01 + ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 GDA 19800301 + 002905", "Persoon.Adres.DatumAanvangAdreshouding A> 1980/03/01 + ^29/5/?");
    }

    @Test
    public void testKDA() throws Exception {
        testUtil.testVoorwaarde("01.03.10 KDA 19800301", "Persoon.Geboorte.Datum A< 1980/03/01");
        testUtil.testVoorwaarde("08.10.30 KDA 19800301", "Persoon.Adres.DatumAanvangAdreshouding A< 1980/03/01");
        testUtil.testVoorwaarde("01.03.10 KDA 19800301 - 00290000", "Persoon.Geboorte.Datum A< 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("08.10.30 KDA 19800301 - 00290000", "Persoon.Adres.DatumAanvangAdreshouding A< 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("01.03.10 KDA 19800301 - 0029", "Persoon.Geboorte.Datum A< 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("08.10.30 KDA 19800301 - 0029", "Persoon.Adres.DatumAanvangAdreshouding A< 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("01.03.10 KDA 19800301 - 002900", "Persoon.Geboorte.Datum A< 1980/03/01 - ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 KDA 19800301 - 002905", "Persoon.Adres.DatumAanvangAdreshouding A< 1980/03/01 - ^29/5/?");
        testUtil.testVoorwaarde("01.03.10 KDA 19800301 + 002900", "Persoon.Geboorte.Datum A< 1980/03/01 + ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 KDA 19800301 + 002905", "Persoon.Adres.DatumAanvangAdreshouding A< 1980/03/01 + ^29/5/?");
    }

    @Test
    public void testGDOGA() throws Exception {
        testUtil.testVoorwaarde("01.03.10 GDOGA 19800301", "Persoon.Geboorte.Datum A>= 1980/03/01");
        testUtil.testVoorwaarde("08.10.30 GDOGA 19800301", "Persoon.Adres.DatumAanvangAdreshouding A>= 1980/03/01");
        testUtil.testVoorwaarde("01.03.10 GDOGA 19800301 - 00290000", "Persoon.Geboorte.Datum A>= 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("08.10.30 GDOGA 19800301 - 00290000", "Persoon.Adres.DatumAanvangAdreshouding A>= 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("01.03.10 GDOGA 19800301 - 0029", "Persoon.Geboorte.Datum A>= 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("08.10.30 GDOGA 19800301 - 0029", "Persoon.Adres.DatumAanvangAdreshouding A>= 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("01.03.10 GDOGA 19800301 - 002900", "Persoon.Geboorte.Datum A>= 1980/03/01 - ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 GDOGA 19800301 - 002905", "Persoon.Adres.DatumAanvangAdreshouding A>= 1980/03/01 - ^29/5/?");
        testUtil.testVoorwaarde("01.03.10 GDOGA 19800301 + 002900", "Persoon.Geboorte.Datum A>= 1980/03/01 + ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 GDOGA 19800301 + 002905", "Persoon.Adres.DatumAanvangAdreshouding A>= 1980/03/01 + ^29/5/?");
    }

    @Test
    public void testKDOGA() throws Exception {
        testUtil.testVoorwaarde("01.03.10 KDOGA 19800301", "Persoon.Geboorte.Datum A<= 1980/03/01");
        testUtil.testVoorwaarde("08.10.30 KDOGA 19800301", "Persoon.Adres.DatumAanvangAdreshouding A<= 1980/03/01");
        testUtil.testVoorwaarde("01.03.10 KDOGA 19800301 - 00290000", "Persoon.Geboorte.Datum A<= 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("08.10.30 KDOGA 19800301 - 00290000", "Persoon.Adres.DatumAanvangAdreshouding A<= 1980/03/01 - ^29/0/0");
        testUtil.testVoorwaarde("01.03.10 KDOGA 19800301 - 0029", "Persoon.Geboorte.Datum A<= 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("08.10.30 KDOGA 19800301 - 0029", "Persoon.Adres.DatumAanvangAdreshouding A<= 1980/03/01 - ^29/?/?");
        testUtil.testVoorwaarde("01.03.10 KDOGA 19800301 - 002900", "Persoon.Geboorte.Datum A<= 1980/03/01 - ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 KDOGA 19800301 - 002905", "Persoon.Adres.DatumAanvangAdreshouding A<= 1980/03/01 - ^29/5/?");
        testUtil.testVoorwaarde("01.03.10 KDOGA 19800301 + 002900", "Persoon.Geboorte.Datum A<= 1980/03/01 + ^29/0/?");
        testUtil.testVoorwaarde("08.10.30 KDOGA 19800301 + 002905", "Persoon.Adres.DatumAanvangAdreshouding A<= 1980/03/01 + ^29/5/?");
    }

    @Test
    public void testVandaagEnSelectieDatum() throws Exception {
        testUtil.testVoorwaarde("01.03.10 KDOG1 19.89.30 - 00290000", "Persoon.Geboorte.Datum E<= VANDAAG() - ^29/0/0");
        testUtil.testVoorwaarde("01.03.10 KDOG1 19.89.20 - 00290000", "Persoon.Geboorte.Datum E<= SELECTIE_DATUM() - ^29/0/0");
        testUtil.testVoorwaarde("01.03.10 KDOG1 19.89.30 - 0029", "Persoon.Geboorte.Datum E<= VANDAAG() - ^29/?/?");
        testUtil.testVoorwaarde("01.03.10 KDOG1 19.89.30 - 002905", "Persoon.Geboorte.Datum E<= VANDAAG() - ^29/5/?");
        testUtil.testVoorwaarde("01.03.10 KDOG1 19.89.20 - 00000000", "Persoon.Geboorte.Datum E<= SELECTIE_DATUM() - ^0/0/0");
    }

    @Test
    public void testVergelijkingMetRubriek() throws Exception {
        testUtil.testVoorwaarde("01.03.10 KDOG1 07.68.10", "Persoon.Geboorte.Datum E<= Persoon.Inschrijving.Datum");
        testUtil.testVoorwaarde("01.03.10 OGA1 08.10.30", "NIET(Persoon.Adres.DatumAanvangAdreshouding A= Persoon.Geboorte.Datum)");
        testUtil.testVoorwaarde("08.10.30 GDOG1 01.03.10", "Persoon.Adres.DatumAanvangAdreshouding E>= Persoon.Geboorte.Datum");
    }

    @Test
    public void testVergelijkingMetRubriekMetPeriode() throws Exception {
        testUtil.testVoorwaarde("01.03.10 KDOG1 07.68.10 - 00290500", "Persoon.Geboorte.Datum E<= Persoon.Inschrijving.Datum - ^29/5/0");
        testUtil.testVoorwaarde("08.10.30 GDOG1 01.03.10 - 00290500", "Persoon.Adres.DatumAanvangAdreshouding E>= Persoon.Geboorte.Datum - ^29/5/0");
    }

    @Test
    public void datumAanvangGeldigheidWaarbijOokNaarDatumEindeGeldigheidMoetWordenGekeken() throws Exception {
        testUtil.testVoorwaarde("07.68.10 KD1 04.85.10",
                "(Persoon.Nationaliteit.DatumAanvangGeldigheid E> Persoon.Inschrijving.Datum OF HISM(Persoon.Nationaliteit.DatumEindeGeldigheid) E>" +
                        " Persoon.Inschrijving.Datum OF Persoon.Inschrijving.Datum E< Persoon" +
                        ".Indicatie.BehandeldAlsNederlander.DatumAanvangGeldigheid OF Persoon.Inschrijving.Datum E< Persoon.Indicatie" +
                        ".VastgesteldNietNederlander.DatumAanvangGeldigheid OF Persoon.Inschrijving.Datum E< Persoon.Indicatie.Staatloos" +
                        ".DatumAanvangGeldigheid)");
    }

    @Test
    public void datumVergelijkingMetLijstElementEnPeriodeVergelijking() throws Exception {
        testUtil.testVoorwaarde("01.03.10 KDOG1 05.03.10 - 00000100",
                "(GerelateerdeHuwelijkspartner.Persoon.Geboorte.Datum - ^0/1/0 E>= Persoon.Geboorte.Datum OF "
                        + "GerelateerdeGeregistreerdePartner.Persoon.Geboorte.Datum - ^0/1/0 E>= Persoon.Geboorte.Datum)");
    }

    @Test
    public void slimZoekenMetWildcard() throws Exception {
        testUtil.testVoorwaarde("01.03.10 GA1 1995*", "Persoon.Geboorte.Datum E=% 1995*");
        testUtil.testVoorwaarde("01.03.10 GA1 199501*", "Persoon.Geboorte.Datum E=% 1995/01*");
        testUtil.testVoorwaarde("01.03.10 GA1 \\1995*", "Persoon.Geboorte.Datum E=% \\1995*");
        testUtil.testVoorwaarde("01.03.10 GA1 \\199501*", "Persoon.Geboorte.Datum E=% \\1995/01*");
    }
}
