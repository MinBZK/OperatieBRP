/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.robuustheid;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * stap1: vul database met personen en autorisaties
 * mvn test -Dtest=AfnemerindicatiebulkTest#test -Pdataaccess-postgres
 *
 * stap2:
 * start tomcat via deployment_jenkins
 *
 * stap3:
 * voer send() testje uit
 *
 * herhalen;
 * delete from autaut.his_persafnemerindicatie;
 * delete from autaut.persafnemerindicatie;
 * update kern.perscache set afnemerindicatiegegevens = null;
 * truncate afnemerindicatie tabellen
 */
@Ignore
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ContextConfiguration(locations = {"/robuustheid/afnemerindicatie-bulk-test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AfnemerindicatiebulkTest {

    private static final Timestamp TS_19810101 = Timestamp.valueOf("1981-01-01 00:00:00.0");
    private static final int BSN_START = 1234567;
    private static final int BSN_AANTAL = 25000;

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager em;
    private BRPActie brpActie;

    @Test
    @Transactional(transactionManager = "masterTransactionManager", propagation = Propagation.REQUIRES_NEW)
    @Rollback(value = false)
    public void test() {
        maakVerantwoording();
        vulDatabaseMetPersonen();
        maakAutorisatie();
    }

    @Test
    public void send() throws IOException, InterruptedException {

        final ExecutorService executorService = Executors.newFixedThreadPool(21);

        final String verzoekTemplate = IOUtils.toString(new ClassPathResource("config/plaats_afnemerindicatie.xml").getInputStream());
        Lists.newArrayList("200001", "200901", "201301", "250002", "250101")
                .forEach(partijCode -> {

                    for (int i = 0; i < BSN_AANTAL; i++) {

                        final int bsnIncrement = i;
                        executorService.submit(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    runWithException();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            private void runWithException() throws IOException {
                                String verzoek = verzoekTemplate.replaceAll("#zendendePartijCode", partijCode);
                                verzoek = verzoek.replaceAll("#leveringsautorisatieIdentificatie", "1");
                                verzoek = verzoek.replaceAll("#burgerservicenummer", "00" + (BSN_START + bsnIncrement));

                                URL url = new URL("http://localhost:8080/afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties");
                                URLConnection connection = url.openConnection();
                                connection.setDoOutput(true);

                                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                                out.write(verzoek);
                                out.close();

                                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String decodedString;
                                while ((decodedString = in.readLine()) != null) {
                                    //System.out.println(decodedString);
                                }
                                in.close();
                            }
                        });
                    }
                });

        executorService.shutdown();
        while (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("waiting");
        }
    }

    @SuppressWarnings("unchecked")
    private void maakAutorisatie() {

        final Leveringsautorisatie leveringsautorisatie = em.merge(new Leveringsautorisatie(Stelsel.BRP, false));
        leveringsautorisatie.setPopulatiebeperking("WAAR");
        leveringsautorisatie.setActueelEnGeldig(true);
        leveringsautorisatie.setDatumIngang(20000101);

        final Dienstbundel dienstbundel = em.merge(new Dienstbundel(leveringsautorisatie));
        dienstbundel.setNaam("db");
        dienstbundel.setActueelEnGeldig(true);
        dienstbundel.setDatumIngang(20000101);

        Dienst dienstPlaatsAfnemerindicatie = new Dienst(dienstbundel, SoortDienst.PLAATSING_AFNEMERINDICATIE);
        dienstPlaatsAfnemerindicatie.setDatumIngang(20000101);
        dienstPlaatsAfnemerindicatie.setActueelEnGeldig(true);
        em.merge(dienstPlaatsAfnemerindicatie);

        final DienstbundelGroep groedIdNummer = em.merge(new DienstbundelGroep(dienstbundel, Element.PERSOON_IDENTIFICATIENUMMERS, true, true, true));
        groedIdNummer.getDienstbundelGroepAttribuutSet()
                .add(em.merge(new DienstbundelGroepAttribuut(groedIdNummer, Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER)));
        dienstbundel.getDienstbundelGroepSet().add(groedIdNummer);

        final DienstbundelGroep idGroep = em.merge(new DienstbundelGroep(dienstbundel, Element.PERSOON_IDENTITEIT, true, true, true));
        idGroep.getDienstbundelGroepAttribuutSet().add(em.merge(new DienstbundelGroepAttribuut(idGroep, Element.PERSOON_SOORTCODE)));
        dienstbundel.getDienstbundelGroepSet().add(idGroep);

        leveringsautorisatie.getDienstbundelSet().add(dienstbundel);

        final List<Object> resultList = em.createNativeQuery("select id from kern.partijrol where dateinde is null").getResultList();
        int count = 1;
        for (Object aResultList : resultList) {
            Integer partijrolId = (Integer) aResultList;
            final PartijRol partijRol = em.find(PartijRol.class, partijrolId);
            final Partij partij = partijRol.getPartij();
            partij.setOin("oin" + count);
            em.merge(partij);

            ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
            toegangLeveringsAutorisatie.setActueelEnGeldig(true);
            toegangLeveringsAutorisatie.setDatumIngang(20000101);
            toegangLeveringsAutorisatie.setAfleverpunt("http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService/VerwerkPersoon");
            em.persist(toegangLeveringsAutorisatie);

            count++;
        }
    }

    private void maakVerantwoording() {

        final Partij partij101 = em.getReference(Partij.class, (short) 101);

        //adm. handelingen
        AdministratieveHandeling admhnd1 = new AdministratieveHandeling(
                partij101, SoortAdministratieveHandeling.parseId(1), TS_19810101);
        admhnd1.setId(1L);

        //acties
        BRPActie brpActie = new BRPActie(SoortActie.parseId(1), admhnd1, partij101, TS_19810101);
        brpActie.setId(1L);

        em.merge(admhnd1);
        this.brpActie = em.merge(brpActie);
    }

    private void vulDatabaseMetPersonen() {

        for (int i = 0; i < BSN_AANTAL; i++) {
            Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
            persoon.setId((long) (i + 1));
            persoon.setBurgerservicenummer(String.valueOf(BSN_START + i));
            maakHisIdentificatie(persoon, i);
            em.merge(persoon);
            em.flush();
            em.clear();
        }
    }

    private void maakHisIdentificatie(Persoon persoon, int id) {
        final PersoonIDHistorie idHis = new PersoonIDHistorie(persoon);
        idHis.setAdministratienummer(String.valueOf(BSN_START + id));
        idHis.setBurgerservicenummer(String.valueOf(BSN_START + id));
        idHis.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));
        idHis.setActieInhoud(brpActie);
        idHis.setDatumAanvangGeldigheid(19671223);
        em.merge(idHis);
    }

}
