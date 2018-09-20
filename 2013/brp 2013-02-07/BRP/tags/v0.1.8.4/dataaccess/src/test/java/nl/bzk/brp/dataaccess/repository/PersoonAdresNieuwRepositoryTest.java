/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.Adresseerbaarobject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonAdresNieuwRepositoryTest extends AbstractRepositoryTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonAdresNieuwRepositoryTest.class);

    @PersistenceContext
    private EntityManager em;

    @Test
    public void haalPersoonAdresOp() {
        PersoonAdresModel pa = em.find(PersoonAdresModel.class, 10001);

        Assert.assertEquals(new Burgerservicenummer("135867277"),
            pa.getPersoon().getIdentificatienummers().getBurgerservicenummer());
        Assert.assertEquals("Hoofd instelling", pa.getGegevens().getAangeverAdresHouding().getNaam());
        Assert.assertEquals("1492", pa.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("Dorpstr", pa.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Regel 1", pa.getGegevens().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("Regel 2", pa.getGegevens().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("Regel 3", pa.getGegevens().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("Regel 4", pa.getGegevens().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("Regel 5", pa.getGegevens().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("Regel 6", pa.getGegevens().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(new Integer(20120101), pa.getGegevens().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals(new Integer(12020508), pa.getGegevens().getDatumVertrekUitNederland().getWaarde());
        Assert.assertEquals("Almere", pa.getGegevens().getGemeente().getNaam().getWaarde());
        Assert.assertEquals("GemDeel", pa.getGegevens().getGemeentedeel().getWaarde());
        Assert.assertEquals("a", pa.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals(Integer.valueOf(512), pa.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("IV", pa.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("Nederland", pa.getGegevens().getLand().getNaam().getWaarde());
        Assert.assertEquals("1581", pa.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Omschrijving", pa.getGegevens().getLocatieOmschrijving().getWaarde());
        Assert.assertEquals("ta", pa.getGegevens().getLocatietovAdres().getWaarde());
        Assert.assertEquals("Dorpstraat", pa.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("7812PK", pa.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("Aangifte door persoon", pa.getGegevens().getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals("Woonadres", pa.getGegevens().getSoort().getNaam());
        Assert.assertEquals("0034", pa.getGegevens().getWoonplaats().getCode().toString());
        Assert.assertEquals("Almeres", pa.getGegevens().getWoonplaats().getNaam().getWaarde());

        Assert.assertEquals(StatusHistorie.A, pa.getStatusHistorie());
    }

    @Test
    public void slaPersoonAdresOp() {
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        RedenWijzigingAdres rdnWijzig = em.find(RedenWijzigingAdres.class, new Short((short) 1));
        Land land = em.find(Land.class, 2);
        Plaats woonplaats = em.find(Plaats.class, 1);
        Partij gemeente = em.find(Partij.class, (short) 3);

        PersoonAdresBericht pa = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        pa.setGegevens(paGegevens);

        paGegevens.setSoort(FunctieAdres.WOONADRES);
        paGegevens.setDatumAanvangAdreshouding(new Datum(20120802));
        paGegevens.setRedenwijziging(rdnWijzig);
        paGegevens.setGemeente(gemeente);
        paGegevens.setWoonplaats(woonplaats);
        paGegevens.setLand(land);
        paGegevens.setAangeverAdreshouding(AangeverAdreshoudingIdentiteit.HOOFDINSTELLING);
        paGegevens.setAdresseerbaarObject(new Adresseerbaarobject("AdreerbaarObject x"));
        paGegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("Afgekorte NOR"));
        paGegevens.setBuitenlandsAdresRegel1(new Adresregel("Regel 1"));
        paGegevens.setBuitenlandsAdresRegel2(new Adresregel("Regel 2"));
        paGegevens.setBuitenlandsAdresRegel3(new Adresregel("Regel 3"));
        paGegevens.setBuitenlandsAdresRegel4(new Adresregel("Regel 4"));
        paGegevens.setBuitenlandsAdresRegel5(new Adresregel("Regel 5"));
        paGegevens.setBuitenlandsAdresRegel6(new Adresregel("Regel 6"));
        paGegevens.setDatumVertrekUitNederland(new Datum(12020508));
        paGegevens.setGemeentedeel(new Gemeentedeel("GemDeel"));
        paGegevens.setHuisletter(new Huisletter("a"));
        paGegevens.setHuisnummer(new Huisnummer(512));
        paGegevens.setHuisnummertoevoeging(new Huisnummertoevoeging("IV"));
        paGegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("1581"));
        paGegevens.setLocatieOmschrijving(new LocatieOmschrijving("Omschrijving"));
        paGegevens.setLocatietovAdres(new LocatieTovAdres("ta"));
        paGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Dorpstraat"));
        paGegevens.setPostcode(new Postcode("7812PK"));

        PersoonAdresModel persoonAdres = new PersoonAdresModel(pa, persoon);
        ReflectionTestUtils.setField(persoonAdres, "statusHistorie", StatusHistorie.A);

        em.persist(persoonAdres);
        Assert.assertTrue("Fout gegaan met wegschrijven, null Id", (persoonAdres.getId() != null));
        persoonAdres = em.find(PersoonAdresModel.class, persoonAdres.getId());
        Assert.assertEquals("Hoofd instelling", persoonAdres.getGegevens().getAangeverAdresHouding().getNaam());
        Assert.assertEquals("AdreerbaarObject x", persoonAdres.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("Afgekorte NOR", persoonAdres.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Regel 1", persoonAdres.getGegevens().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("Regel 2", persoonAdres.getGegevens().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("Regel 3", persoonAdres.getGegevens().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("Regel 4", persoonAdres.getGegevens().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("Regel 5", persoonAdres.getGegevens().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("Regel 6", persoonAdres.getGegevens().getBuitenlandsAdresRegel6().getWaarde());
        Assert
            .assertEquals(new Integer(20120802), persoonAdres.getGegevens().getDatumAanvangAdreshouding().getWaarde());
        Assert
            .assertEquals(new Integer(12020508), persoonAdres.getGegevens().getDatumVertrekUitNederland().getWaarde());
        Assert.assertEquals("Almere", persoonAdres.getGegevens().getGemeente().getNaam().getWaarde());
        Assert.assertEquals("GemDeel", persoonAdres.getGegevens().getGemeentedeel().getWaarde());
        Assert.assertEquals("a", persoonAdres.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals(Integer.valueOf(512), persoonAdres.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("IV", persoonAdres.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("Nederland", persoonAdres.getGegevens().getLand().getNaam().getWaarde());
        Assert.assertEquals("1581", persoonAdres.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Omschrijving", persoonAdres.getGegevens().getLocatieOmschrijving().getWaarde());
        Assert.assertEquals("ta", persoonAdres.getGegevens().getLocatietovAdres().getWaarde());
        Assert.assertEquals("Dorpstraat", persoonAdres.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("7812PK", persoonAdres.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("Aangifte door persoon",
            persoonAdres.getGegevens().getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals("Woonadres", persoonAdres.getGegevens().getSoort().getNaam());
        Assert.assertEquals("0034", persoonAdres.getGegevens().getWoonplaats().getCode().toString());
        Assert.assertEquals("Almeres", persoonAdres.getGegevens().getWoonplaats().getNaam().getWaarde());
        Assert.assertEquals("Actueel voorkomend", persoonAdres.getStatusHistorie().getNaam());

    }

    private static final String SELECT_PERSOON    = " SELECT distinct persoon FROM PersoonModel persoon ";
    private static final String JOIN_NATIONALIEIT = " LEFT JOIN FETCH persoon.nationaliteiten ";
    private static final String JOIN_INDICATIE    = " LEFT JOIN FETCH persoon.persoonIndicaties ";
    private static final String JOIN_ADRES        = " LEFT JOIN FETCH persoon.adressen as adres ";
    private static final String JOIN_GESLACHTNAAM = " LEFT JOIN FETCH persoon.geslachtsnaamcomponenten ";
    private static final String JOIN_VOORNAAM     = " LEFT JOIN FETCH persoon.persoonVoornaam ";
    private static final String JOIN_BETROKKENE   = " LEFT JOIN FETCH persoon.betrokkenheden ";

    @Test
    public void hibernateLeftJoinsTest() {
        StringBuilder query = new StringBuilder(SELECT_PERSOON)
            .append(JOIN_ADRES)
            .append(JOIN_NATIONALIEIT)
            .append(JOIN_GESLACHTNAAM)
            .append(JOIN_VOORNAAM);

        TypedQuery<PersoonModel> tQuery = em.createQuery(query.toString(), PersoonModel.class);
        PersoonModel persoon;
        List<PersoonModel> personen = tQuery.getResultList();
    }


    @Test
    public void haalStatischeObjectenOp() {
        Land land = em.find(Land.class, 4);
        Assert.assertEquals("Frankrijk", land.getNaam().getWaarde());
        AdellijkeTitel adellijkeTitel = em.find(AdellijkeTitel.class, (short) 1);
        Assert.assertEquals("B", adellijkeTitel.getAdellijkeTitelCode().getWaarde());
        AangeverAdreshouding aangeverAdreshouding = em.find(AangeverAdreshouding.class, (short) 1);
        Assert.assertEquals("Gezaghouder", aangeverAdreshouding.getNaam().getWaarde());
        Nationaliteit nationaliteit = em.find(Nationaliteit.class, 1);
        Assert.assertEquals("Onbekend", nationaliteit.getNaam().getWaarde());
        Partij party = em.find(Partij.class, (short) 1);
        Assert.assertEquals("Regering en Staten-Generaal", party.getNaam().getWaarde());
        Plaats plaats = em.find(Plaats.class, 1);
        Assert.assertEquals("Almeres", plaats.getNaam().getWaarde());
        Predikaat predikaat = em.find(Predikaat.class, (short) 1);
        Assert.assertEquals("K", predikaat.getCode().getWaarde());
//
//        RedenBeeindigingRelatie redenBeeindigingRelatie = em.find(RedenBeeindigingRelatie.class, 3);
//        System.out.println(redenBeeindigingRelatie + ","  + redenBeeindigingRelatie.getCode());
        RedenWijzigingAdres redenWijzigingAdres = em.find(RedenWijzigingAdres.class, (short) 1);
        Assert.assertEquals("Aangifte door persoon", redenWijzigingAdres.getNaam().getWaarde());
    }


    @Test
    public void testLadenVanAlleStatischeObjectTypes() throws Exception {
        List<Class<? extends Object>> classes = getClassesInPackage("nl.bzk.brp.model.objecttype.statisch");

        // lijst van classes met @Entity annotaties.
        List<Class<? extends Object>> entities = filterClassesOpEntities(classes);
        for (Class<? extends Object> o : entities) {
            Object instantie = createInstantie(o);
            Assert.assertTrue(
                "class " + instantie.getClass().getName() + " is niet afgeleid van AbstractStatischObjectType",
                (instantie instanceof AbstractStatischObjectType));
        }
    }

    private Object createInstantie(final Class<? extends Object> clazz) throws Exception {
        Object obj = null;
        Constructor[] cons = clazz.getConstructors();
        if ((cons != null && cons.length > 0) && (!clazz.getSimpleName().startsWith("Abstract"))) {
            // abstract classes doesn't have constructors, skip these.
            try {
                obj = creeerEntityInstantie(clazz);
            } catch (InstantiationException e) {
                LOGGER.error("Failed to create instance ofTAbstr " + clazz.getName() + " error " + e.getMessage());
                throw e;
            }
        }
        return obj;
    }

    /**
     * Creeert een nieuwe instantie voor de (entity) class. Het doet dit door de verwachte parameterloze
     * constructor
     * op te halen voor de opgegeven class en deze aan te roepen. Daar JPA entities verplicht zijn een
     * parameterloze
     * constructor te hebben, zal dit geen probleem zijn, ook al is deze niet public.
     *
     * @param clazz de class waarvoor een instantie moet worden gecreeerd.
     * @param <T> het type van de class.
     * @return een nieuwe instantie van de class.
     * @throws Exception indien er een fout optreedt bij het instantieren van de nieuwe instantie en/of het vinden
     * van de juiste constructor.
     */
    private <T extends Object> T creeerEntityInstantie(final Class<T> clazz) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private static List<Class<? extends Object>> getClassesInPackage(final String packageNaam) throws
        ClassNotFoundException, IOException, URISyntaxException
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(packageNaam.replace('.', '/'));

        // Maak een lijst van alle folders/resources die overeen komen met het gezochte package. Dit zal de package
        // specifieke folder zijn in /src/main/java en de folder in /src/test/java.
        List<File> bestanden = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            URI resource = url.toURI();
            if (null != resource && null != resource.getPath()) {
                bestanden.add(new File(resource.getPath()));
            }
        }

        // Maak een lijst van Class bestanden die zitten in de lijst van gevonden folders behorende bij de package.
        List<Class<? extends Object>> classes = new ArrayList<Class<? extends Object>>();
        for (File folder : bestanden) {
            classes.addAll(vindClassesInPackageFolderEnSubpackages(folder, packageNaam));
        }
        return classes;
    }

    /**
     * Bouwt een nieuwe lijst op van classes, gebaseerd op de opgegeven lijst van classes, maar waar dan alleen nog
     * classes inzitten die een entity zijn (welke geannoteerd zijn met de annotatie {@link Entity}).
     *
     * @param classes de lijst van classes waaruit de lijst van entities wordt opgebouwd.
     * @return een lijst van entity classes.
     */
    private List<Class<? extends Object>> filterClassesOpEntities(final List<Class<? extends Object>> classes) {
        List<Class<? extends Object>> entities = new ArrayList<Class<? extends Object>>();
        for (Class<? extends Object> clazz : classes) {
            if (clazz.isAnnotationPresent(Entity.class)) {
                entities.add(clazz);
            }
        }

        return entities;
    }

    /**
     * Haalt alle classes op uit de opgegeven folder en diens subfolder en retourneert deze.
     *
     * @param folder de folder die classes bevat welke geretourneerd dienen te worden.
     * @param packageNaam de naam van het package waartoe de classes in de folder behoren.
     * @return de in de folder en diens subfolder gevonden classes.
     * @throws ClassNotFoundException indien een class niet gevonden kan worden.
     */
    private static List<Class<? extends Object>> vindClassesInPackageFolderEnSubpackages(final File folder,
        final String packageNaam) throws ClassNotFoundException
    {
        List<Class<? extends Object>> classes = new ArrayList<Class<? extends Object>>();

        if (!folder.exists()) {
            LOGGER.error("Folder bestaat niet: {}", folder.getAbsolutePath());
            return classes;
        }

        File[] bestanden = folder.listFiles();
        for (File bestand : bestanden) {
            if (bestand.isDirectory()) {
                classes.addAll(vindClassesInPackageFolderEnSubpackages(bestand,
                    bouwPackageNaamVoorFolder(bestand, packageNaam)));
            } else if (bestand.getName().endsWith(".class")) {
                classes.add(Class.forName(bouwClassNaamVoorBestand(bestand, packageNaam)));
            }
        }
        return classes;
    }

    /**
     * Bouwt de package naam op voor een folder.
     *
     * @param folder de folder waarvoor de package naam wordt opgebouwd.
     * @param parentPackageNaam de naam van het (parent) package waarin dit package zich bevindt.
     * @return de package naam voor de folder.
     */
    private static String bouwPackageNaamVoorFolder(final File folder, final String parentPackageNaam) {
        return String.format("%s.%s", parentPackageNaam, folder.getName());
    }

    /**
     * Retourneert de volledige naam van de java class file.
     *
     * @param bestand het java class bestand waarvoor de naam moet worden gegenereerd.
     * @param packageNaam de naam van het package waarin de java class file zich bevindt.
     * @return de volledige naam van de java class file.
     */
    private static String bouwClassNaamVoorBestand(final File bestand, final String packageNaam) {
        return String.format("%s.%s", packageNaam, bestand.getName().substring(0, bestand.getName().length() - 6));
    }

}
