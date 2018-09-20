/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.NietUniekeAnummerExceptie;
import nl.bzk.brp.dataaccess.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamModel;
import org.junit.Assert;
import org.junit.Test;


public class PersoonRepositoryTest extends AbstractRepositoryTestCase {

    public static final String ALMERE = "Almere";
    public static final String WAARDE_0034 = "0034";
    public static final String DAMSTRAAT = "Damstraat";
    public static final String A = "a";
    public static final String II = "II";
    public static final String WAARDE_1334NA = "1334NA";
    public static final String A1 = "A";
    public static final String SOUS = "sous";
    public static final String WAARDE_1000AA = "1000AA";
    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Test
    public void testHaalPersoonIdsMetWoonAdresOpViaVolledigAdres() {
        final Plaats plaats = referentieDataRepository.vindWoonplaatsOpNaam(new NaamEnumeratiewaardeAttribuut(ALMERE));
        final Gemeente gemeente = referentieDataRepository.vindGemeenteOpCode(new GemeenteCodeAttribuut(Short.parseShort(WAARDE_0034)));
        final List<Integer> persoonIds =
                persoonRepository.haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
                        new NaamOpenbareRuimteAttribuut(DAMSTRAAT),
                        new HuisnummerAttribuut(1), new HuisletterAttribuut(A),
                        new HuisnummertoevoegingAttribuut(II),
                        plaats.getNaam(), gemeente);
        Assert.assertEquals(1, persoonIds.size());
        Assert.assertEquals(1001, persoonIds.get(0).intValue());
    }

    @Test
    public void testHaalPersoonIdsMetWoonAdresOpViaVolledigAdresMetLegeStringsIpvNulls() {
        final Plaats plaats = referentieDataRepository.vindWoonplaatsOpNaam(new NaamEnumeratiewaardeAttribuut(ALMERE));
        final Gemeente gemeente = referentieDataRepository.vindGemeenteOpCode(new GemeenteCodeAttribuut(Short.parseShort(WAARDE_0034)));

        final List<Integer> persoonIds =
                persoonRepository.haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
                        new NaamOpenbareRuimteAttribuut(DAMSTRAAT),
                        new HuisnummerAttribuut(1), new HuisletterAttribuut(A),
                        new HuisnummertoevoegingAttribuut(II),
                        plaats.getNaam(), gemeente);
        Assert.assertEquals(1, persoonIds.size());
        Assert.assertEquals(1001, persoonIds.get(0).intValue());
    }

    @Test
    public void testHaalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding() {
        final List<Integer> persoonIds =
                persoonRepository.haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        new IdentificatiecodeNummeraanduidingAttribuut("1581"));
        Assert.assertEquals(2, persoonIds.size());
        assertTrue(persoonIds.contains(1001));
        assertTrue(persoonIds.contains(8731137));
    }

    @Test
    public void testHaalPersoonIdsOpMetAdresViaPostcodeHuisnummer() {
        List<Integer> persoonIds =
                persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
                        new PostcodeAttribuut(WAARDE_1334NA),
                        new HuisnummerAttribuut(73), new HuisletterAttribuut(A1),
                        new HuisnummertoevoegingAttribuut(SOUS));
        Assert.assertEquals(1, persoonIds.size());

        Assert.assertEquals(1, persoonIds.get(0).intValue());

        persoonIds = persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
                new PostcodeAttribuut(WAARDE_1000AA), new HuisnummerAttribuut(1), null, null);
        Assert.assertEquals(2, persoonIds.size());
        // Volgorde niet gegarandeerd, gebruik contains.
        Assert.assertTrue(persoonIds.contains(2));
    }

    @Test
    public void testHaalPersoonIdsOpMetAdresViaPostcodeHuisnummerMetLegeStringsIpvNulls() {
        List<Integer> persoonIds =
                persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
                        new PostcodeAttribuut(WAARDE_1334NA),
                        new HuisnummerAttribuut(73), new HuisletterAttribuut(A1),
                        new HuisnummertoevoegingAttribuut(SOUS));
        Assert.assertEquals(1, persoonIds.size());
        Assert.assertEquals(1, persoonIds.get(0).intValue());

        persoonIds = persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
                new PostcodeAttribuut(WAARDE_1000AA),
                new HuisnummerAttribuut(1), new HuisletterAttribuut(""), new HuisnummertoevoegingAttribuut(""));
        Assert.assertEquals(2, persoonIds.size());

        // Volgorde niet gegarandeerd, gebruik contains.
        Assert.assertTrue(persoonIds.contains(2));
        Assert.assertTrue(persoonIds.contains(1002));
    }

    @Test
    public void testGesorteerdeSetsOpPersoon() {
        final PersoonModel persoon = em.find(PersoonModel.class, 8731137);
        Assert.assertNotNull(persoon);

        PersoonVoornaamModel vorigeVoornaam = null;
        for (final PersoonVoornaamModel voornaam : persoon.getVoornamen()) {
            if (vorigeVoornaam != null) {
                assertTrue("Vorige voornaam is groter dan huidige in set --> Volgorde is niet goed.",
                        voornaam.getVolgnummer().compareTo(vorigeVoornaam.getVolgnummer()) >= 0);
            }
            vorigeVoornaam = voornaam;
        }
    }

    @Test
    public void testIsBsnInGebruik() {
        Assert.assertTrue(persoonRepository.isBSNAlIngebruik(new BurgerservicenummerAttribuut(123456789)));
        Assert.assertFalse(persoonRepository.isBSNAlIngebruik(new BurgerservicenummerAttribuut(123456780)));
    }


    @Test
    public void testIsANummerInGebruik() {
        Assert.assertTrue(persoonRepository.isAdministratienummerAlInGebruik(
                new AdministratienummerAttribuut(1234567890L)));
        Assert.assertFalse(persoonRepository.isAdministratienummerAlInGebruik(
                new AdministratienummerAttribuut(1234567800L)));
    }

    @Test
    public void testHaalPersoonInformatieOpZonderSleutel() {
        Assert.assertNull(persoonRepository.haalPersoonInformatie(null));
        Assert.assertNull(persoonRepository.haalPersoonInformatie(""));
        Assert.assertNull(persoonRepository.haalPersoonInformatie(" "));
    }

    @Test
    public void testHaalPersoonInformatieOpViaBSN() {
        PersoonInformatieDto info;

        info = persoonRepository.haalPersoonInformatie("123456789");
        Assert.assertNotNull(info);
        Assert.assertEquals(Integer.valueOf(1), info.getiD());

        info = persoonRepository.haalPersoonInformatie("123456780");
        Assert.assertNull(info);
    }

    @Test
    public void testHaalPersoonInformatieOpViaId() {
        PersoonInformatieDto info;

        info = persoonRepository.haalPersoonInformatie("db1");
        Assert.assertNotNull(info);
        Assert.assertEquals(Integer.valueOf(1), info.getiD());

        info = persoonRepository.haalPersoonInformatie("db001");
        Assert.assertNotNull(info);
        Assert.assertEquals(Integer.valueOf(1), info.getiD());

        info = persoonRepository.haalPersoonInformatie("db4");
        Assert.assertNull(info);

        info = persoonRepository.haalPersoonInformatie("db");
        Assert.assertNull(info);
    }

    @Test
    public void testHaalPersoonInformatieOpMetFouteSleutel() {
        Assert.assertNull(persoonRepository.haalPersoonInformatie("X"));
        Assert.assertNull(persoonRepository.haalPersoonInformatie("dbx"));
        Assert.assertNull(persoonRepository.haalPersoonInformatie("999999999999999999999"));
    }

    @Test
    public void testZoekIdBijBSNGevonden() {
        final Integer verwachtPersoonId = 1;
        final Integer persoonId = persoonRepository.zoekIdBijBSN(new BurgerservicenummerAttribuut(123456789));
        assertEquals(verwachtPersoonId, persoonId);
    }

    @Test
    public void testZoekIdBijBSNNietsGevonden() {
        final Integer persoonId = persoonRepository.zoekIdBijBSN(new BurgerservicenummerAttribuut(12345));
        assertNull(persoonId);
    }

    @Test(expected = NietUniekeBsnExceptie.class)
    public void testZoekIdBijBSNMeerdereGevonden() {
        persoonRepository.zoekIdBijBSN(new BurgerservicenummerAttribuut(999999999));
    }

    @Test
    public void testZoekIdBijAnummerGevonden() {
        final Integer verwachtPersoonId = 1;
        final Integer persoonId = persoonRepository.zoekIdBijAnummer(new AdministratienummerAttribuut(1234567890L));
        assertEquals(verwachtPersoonId, persoonId);
    }

    @Test
    public void testZoekIdBijAnummerNietsGevonden() {
        final Integer persoonId = persoonRepository.zoekIdBijAnummer(new AdministratienummerAttribuut(12345L));
        assertNull(persoonId);
    }

    @Test(expected = NietUniekeAnummerExceptie.class)
    public void testZoekIdBijAnummerMeerdereGevonden() {
        final Integer persoonId = persoonRepository.zoekIdBijAnummer(new AdministratienummerAttribuut(999999999L));
        assertNull(persoonId);
    }
}
