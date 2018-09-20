/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeAdresseerbaarObjectAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Aangever;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonAdresRepositoryTest extends AbstractRepositoryTestCase {

    private static final String ALMERE = "Almere";
    private static final String KIJKDUIN = "Kijkduin";
    private static final String A = "a";
    private static final String B = "b";
    private static final String I = "I";
    private static final String II = "II";
    private static final String C = "c";
    private static final String III = "III";
    private static final String WAARDE_1492 = "1492";
    private static final String WAARDE_1581 = "1581";
    private static final String WAARDE_0034 = "0034";
    private static final String STRAATWEG = "Straatweg";
    private static final String HUISLETTER = "huisletter";
    private static final String WOONPLAATSNAAM = "woonplaatsnaam";
    private static final String AMSTERDAM = "Amsterdam";
    private static final String AANGIFTE_DOOR_PERSOON = "Aangifte door persoon";

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Test
    public void testIsIemandIngeschrevenOpAdresNietIngeschreven() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(maakNieuwAdres()));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetAlleenHuisnummer() {
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(1), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
                                   .isIemandIngeschrevenOpAdres(
                                           creerTestAdresVoorTestReedsBewoners(
                                                   new HuisnummerAttribuut(2), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(3), null, null, null)));
        // True ondanks dat db naast dit adres ook een gemdeel heeft ingevuld, maar daar wordt niet op gecontroleerd
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(4), null, null, null)));
        // True ondanks dat db naast dit adres ook een locatietovadres heeft ingevuld, maar daar wordt niet op
        // gecontroleerd
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(5), null, null, null)));
        // Indien woonplaats niet wordt meegegeven, maar wel bekend is in de db, wordt dat niet als verschillend gezien
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(6), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
                                   .isIemandIngeschrevenOpAdres(
                                           creerTestAdresVoorTestReedsBewoners(
                                                   new HuisnummerAttribuut(7), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(8), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(9), null, null, null)));
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(10), null, null, null)));

        // En test met woonplaats
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(
            creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(1), null, null, ALMERE)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(
            creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(6), null, null, ALMERE)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(6), null, null, KIJKDUIN)));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetHuisnummerEnHuisletter() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(1), new HuisletterAttribuut(A), null, null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
                new HuisnummerAttribuut(2), new HuisletterAttribuut(A), null, null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(3), new HuisletterAttribuut(A), null, null)));
        // Zonder woonplaats moet adres ook gevonden worden, ook al is woonplaats in db wel bekend
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(7), new HuisletterAttribuut(B), null, null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(7), new HuisletterAttribuut(B), null, ALMERE)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
                new HuisnummerAttribuut(7), new HuisletterAttribuut(B), null, KIJKDUIN)));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetHuisnummerEnHuisnummerToevoeging() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(1), null, new HuisnummertoevoegingAttribuut(I), null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(2), null, new HuisnummertoevoegingAttribuut(I), null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(3), null, new HuisnummertoevoegingAttribuut(I), null)));
        // Zonder woonplaats moet adres ook gevonden worden, ook al is woonplaats in db wel bekend
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(8), null, new HuisnummertoevoegingAttribuut(II), null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(8), null, new HuisnummertoevoegingAttribuut(II), ALMERE)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
                new HuisnummerAttribuut(8), null, new HuisnummertoevoegingAttribuut(II), KIJKDUIN)));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetHuisnummerHuisLetterEnHuisnummerToevoeging() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(1), new HuisletterAttribuut(A), new HuisnummertoevoegingAttribuut(I), null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(2), new HuisletterAttribuut(A), new HuisnummertoevoegingAttribuut(I), null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(3), new HuisletterAttribuut(A), new HuisnummertoevoegingAttribuut(I), null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
                new HuisnummerAttribuut(9), new HuisletterAttribuut(C), new HuisnummertoevoegingAttribuut(III), null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new HuisnummerAttribuut(9), new HuisletterAttribuut(C), new HuisnummertoevoegingAttribuut(III), ALMERE)));
    }

    private PersoonAdresModel creerTestAdres() {
        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(paGegevens);

        paGegevens.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        paGegevens.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(20120229));
        paGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("Damstraat"));
        paGegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimteAttribuut("Damstr"));
        paGegevens.setHuisnummer(new HuisnummerAttribuut(1));
        paGegevens.setHuisletter(new HuisletterAttribuut(A));
        paGegevens.setHuisnummertoevoeging(new HuisnummertoevoegingAttribuut(II));
        paGegevens.setPostcode(new PostcodeAttribuut("3984NX"));
        paGegevens.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(ALMERE));
        paGegevens.setIdentificatiecodeAdresseerbaarObject(new IdentificatiecodeAdresseerbaarObjectAttribuut(WAARDE_1492));
        paGegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduidingAttribuut(WAARDE_1581));
        paGegevens.setLandGebied(new LandGebiedAttribuut(referentieDataRepository.vindLandOpCode(LandGebiedCodeAttribuut.NEDERLAND)));
        paGegevens.setGemeente(new GemeenteAttribuut(referentieDataRepository.vindGemeenteOpCode(
            new GemeenteCodeAttribuut(Short.parseShort(WAARDE_0034)))));
        return new PersoonAdresModel(adres, null);
    }

    private PersoonAdresModel creerTestAdresVoorTestReedsBewoners(final HuisnummerAttribuut huisnummer,
        final HuisletterAttribuut huisletter,
        final HuisnummertoevoegingAttribuut huisnummertoevoeging,
        final String woonplaatsNaam)
    {
        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(paGegevens);

        paGegevens.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        paGegevens.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(20120229));
        paGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut(STRAATWEG));
        paGegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimteAttribuut(STRAATWEG));
        paGegevens.setHuisnummer(huisnummer);
        paGegevens.setHuisletter(huisletter);
        paGegevens.setHuisnummertoevoeging(huisnummertoevoeging);
        paGegevens.setPostcode(new PostcodeAttribuut("1555BB"));
        if (woonplaatsNaam != null) {
            paGegevens.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(woonplaatsNaam));
        }
        paGegevens.setLandGebied(new LandGebiedAttribuut(referentieDataRepository.vindLandOpCode(LandGebiedCodeAttribuut.NEDERLAND)));
        paGegevens.setGemeente(new GemeenteAttribuut(referentieDataRepository.vindGemeenteOpCode(
                new GemeenteCodeAttribuut(Short.parseShort(WAARDE_0034)))));
        return new PersoonAdresModel(adres, null);
    }


    @Test
    public void testIsIemandIngeschrevenOpAdres() {
        final PersoonAdresModel adres = creerTestAdres();
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisnummer ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisnummer() {
        final PersoonAdresModel adres = creerTestAdres();
        // Geen huisnummer
        ReflectionTestUtils.setField(adres.getStandaard(), "huisnummer", null);

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisletter ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisletter() {
        final PersoonAdresModel adres = creerTestAdres();
        // Geen huisletter
        ReflectionTestUtils.setField(adres.getStandaard(), HUISLETTER, null);

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisnummertoevoeging ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisnummerToevoeging() {
        final PersoonAdresModel adres = creerTestAdres();
        // zonder huisnummertoevoeging
        ReflectionTestUtils.setField(adres.getStandaard(), "huisnummertoevoeging", null);

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresHuisletterVerschillend() {
        final PersoonAdresModel adres = creerTestAdres();
        // Andere huisletter
        ReflectionTestUtils.setField(adres.getStandaard(), HUISLETTER, new HuisletterAttribuut(B));

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresWoonplaatsVerschillend() {
        PersoonAdresModel adres = creerTestAdres();

        // Met zelfde woonplaats en de rest hetzelfde, zou uiteraard als zelfde adres moeten worden gemarkeerd
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));

        // Zonder woonplaats, maar de rest hetzelfde, zou als zelfde adres moeten worden gemarkeerd
        ReflectionTestUtils.setField(adres.getStandaard(), WOONPLAATSNAAM, null);
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));

        // Met andere woonplaats, maar de rest hetzelfde, zou NIET als zelfde adres moeten worden gemarkeerd
        ReflectionTestUtils.setField(adres.getStandaard(), WOONPLAATSNAAM, new NaamEnumeratiewaardeAttribuut(AMSTERDAM));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));

        // Met woonplaats, maar de rest hetzelfde, alleen in de db geen woonplaats; zou ook als zelfde moeten
        // worden gemarkeerd
        adres = creerTestAdresVoorTestReedsBewoners(new HuisnummerAttribuut(1), null, null, null);
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
        ReflectionTestUtils.setField(adres.getStandaard(), WOONPLAATSNAAM, new NaamEnumeratiewaardeAttribuut(AMSTERDAM));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresZonderGemeente() {
        final PersoonAdresModel adres = creerTestAdres();
        ReflectionTestUtils.setField(adres.getStandaard(), "gemeente", null);
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    private PersoonAdresModel maakNieuwAdres() {
        final PersoonModel persoon = em.find(PersoonModel.class, 1001);

        final PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(20120229));
        gegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("Damweg"));
        gegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimteAttribuut("Damwg"));
        gegevens.setHuisnummer(new HuisnummerAttribuut(2));
        gegevens.setHuisletter(new HuisletterAttribuut(B));
        gegevens.setHuisnummertoevoeging(new HuisnummertoevoegingAttribuut(III));
        gegevens.setPostcode(new PostcodeAttribuut("3063NB"));
        gegevens.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(KIJKDUIN));
        gegevens.setIdentificatiecodeAdresseerbaarObject(new IdentificatiecodeAdresseerbaarObjectAttribuut("1753"));
        gegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduidingAttribuut("1815"));
        gegevens.setLandGebied(new LandGebiedAttribuut(em.find(LandGebied.class, 2)));
        gegevens.setGemeente(new GemeenteAttribuut(em.find(Gemeente.class, (short) 3)));
        gegevens.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(em.find(RedenWijzigingVerblijf.class, (short) 2)));
        gegevens.setAangeverAdreshouding(
                new AangeverAttribuut(em.find(Aangever.class, (short) 3)));

        final PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        nieuwAdres.setStandaard(gegevens);

        return new PersoonAdresModel(nieuwAdres, persoon);
    }

    @Test
    public void haalPersoonAdresOp() {
        final PersoonAdresModel pa = em.find(PersoonAdresModel.class, 10001);

        Assert.assertEquals(new BurgerservicenummerAttribuut(135867277), pa.getPersoon().getIdentificatienummers().getBurgerservicenummer());
        Assert.assertEquals("Hoofd instelling", pa.getStandaard().getAangeverAdreshouding().getWaarde().getNaam().getWaarde());
        Assert.assertEquals(WAARDE_1492, pa.getStandaard().getIdentificatiecodeAdresseerbaarObject().getWaarde());
        Assert.assertEquals("Dorpstr", pa.getStandaard().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Regel 1", pa.getStandaard().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("Regel 2", pa.getStandaard().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("Regel 3", pa.getStandaard().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("Regel 4", pa.getStandaard().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("Regel 5", pa.getStandaard().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("Regel 6", pa.getStandaard().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(Integer.valueOf(20120101), pa.getStandaard().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals(ALMERE, pa.getStandaard().getGemeente().getWaarde().getNaam().getWaarde());
        Assert.assertEquals("GemDeel", pa.getStandaard().getGemeentedeel().getWaarde());
        Assert.assertEquals(A, pa.getStandaard().getHuisletter().getWaarde());
        Assert.assertEquals(Integer.valueOf(512), pa.getStandaard().getHuisnummer().getWaarde());
        Assert.assertEquals("IV", pa.getStandaard().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("Nederland", pa.getStandaard().getLandGebied().getWaarde().getNaam().getWaarde());
        Assert.assertEquals(WAARDE_1581, pa.getStandaard().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Omschrijving", pa.getStandaard().getLocatieomschrijving().getWaarde());
        Assert.assertEquals(LocatieTenOpzichteVanAdres.BY, pa.getStandaard().getLocatieTenOpzichteVanAdres().getWaarde());
        Assert.assertEquals("Dorpstraat", pa.getStandaard().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("7812PK", pa.getStandaard().getPostcode().getWaarde());
        Assert.assertEquals(AANGIFTE_DOOR_PERSOON, pa.getStandaard().getRedenWijziging().getWaarde().getNaam().getWaarde());
        Assert.assertEquals("Woonadres", pa.getStandaard().getSoort().getWaarde().getNaam());
        Assert.assertEquals(ALMERE, pa.getStandaard().getWoonplaatsnaam().getWaarde());
    }

    @Test
    public void haalStatischeObjectenOp() {
        final LandGebied land = em.find(LandGebied.class, 4);
        Assert.assertEquals("Frankrijk", land.getNaam().getWaarde());
        final AdellijkeTitel adellijkeTitel = em.find(AdellijkeTitel.class, (short) 1);
        Assert.assertEquals("B", adellijkeTitel.getCode().getWaarde());
        final Aangever aangever = em.find(Aangever.class, (short) 1);
        Assert.assertEquals("Gezaghouder", aangever.getNaam().getWaarde());
        final Nationaliteit nationaliteit = em.find(Nationaliteit.class, 1);
        Assert.assertEquals("Onbekend", nationaliteit.getNaam().getWaarde());
        final Partij party = em.find(Partij.class, (short) 1);
        Assert.assertEquals("Onbekend", party.getNaam().getWaarde());
        final Plaats plaats = em.find(Plaats.class, 1);
        Assert.assertEquals(ALMERE, plaats.getNaam().getWaarde());
        final Predicaat predikaat = em.find(Predicaat.class, (short) 1);
        Assert.assertEquals("K", predikaat.getCode().getWaarde());
        final RedenWijzigingVerblijf redenWijzigingVerblijf = em.find(RedenWijzigingVerblijf.class, (short) 1);
        Assert.assertEquals(AANGIFTE_DOOR_PERSOON, redenWijzigingVerblijf.getNaam().getWaarde());
    }
}
