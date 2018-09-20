/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.jpa.historie.GroepHistorieRepository;
import nl.bzk.brp.dataaccess.repository.jpa.historie.ObjectTypeHistorieRepository;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonNationaliteit;
import org.junit.Test;

public class PersoonNationaliteitRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Inject
    private PersoonNationaliteitRepository persoonNationaliteitRepository;

    @Inject
    private ObjectTypeHistorieRepository<HisPersoonNationaliteit, PersistentPersoonNationaliteit> persoonNationaliteitHistorieRepository;

    @Inject
    private GroepHistorieRepository persoonSamengesteldeNaamHistorieRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testOpslaanNieuwPersoonNationaliteitRecord() {
        List<PersistentPersoonNationaliteit> persoonNationaliteitList = persoonNationaliteitRepository.findAll();
        int beginAantal = persoonNationaliteitList.size();
        persoonNationaliteitRepository.save(maakPersoonNationaliteit());
        //Check A laag:
        persoonNationaliteitList = persoonNationaliteitRepository.findAll();
        Assert.assertFalse(persoonNationaliteitList.isEmpty());
        Assert.assertEquals(beginAantal + 1, persoonNationaliteitList.size());
        for (PersistentPersoonNationaliteit persoonNationaliteit : persoonNationaliteitList) {
            Assert.assertEquals("0001", persoonNationaliteit.getNationaliteit().getCode());
            Assert.assertTrue(persoonNationaliteit.getPers().getBurgerservicenummer().equals("234567891")
                || persoonNationaliteit.getPers().getBurgerservicenummer().equals("123456789")
                || persoonNationaliteit.getPers().getBurgerservicenummer().equals("135867277")
            );
        }
    }

    @Test
    public void testOpslaanNieuwPersoonNationaliteitRecordInclusiefHistorie() {
        PersistentPersoonNationaliteit persoonNationaliteit =
            persoonNationaliteitRepository.save(maakPersoonNationaliteit());
        persoonNationaliteitHistorieRepository.persisteerHistorie(persoonNationaliteit, persoonNationaliteit.getPers(),
                20120101, null, new Date());
        //Check C laag:
        List<HisPersoonNationaliteit> historie = em.createQuery(
                "Select x from HisPersoonNationaliteit x", HisPersoonNationaliteit.class
        ).getResultList();
        Assert.assertFalse(historie.isEmpty());
        Assert.assertTrue(historie.size() == 1);
        for (HisPersoonNationaliteit hisPersoonNationaliteit : historie) {
            Assert.assertEquals(persoonNationaliteit.getId(),
                    hisPersoonNationaliteit.getPersoonNationaliteit().getId());
            Assert.assertEquals(20120101, hisPersoonNationaliteit.getDatumAanvangGeldigheid().intValue());
            Assert.assertEquals("0001", hisPersoonNationaliteit.getPersoonNationaliteit().getNationaliteit().getCode());
            Assert.assertEquals("123456789",
                    hisPersoonNationaliteit.getPersoonNationaliteit().getPers().getBurgerservicenummer());
        }
    }

    @Test
    public void testWijzigingNationaliteit() {
        PersistentPersoonNationaliteit persoonNation = maakHuidigRecordMetHistorie(maakPersoonNationaliteit(), 20110101);
        //1 Historie record aanwezig:
        List<HisPersoonNationaliteit> historie = em.createQuery(
                "Select x from HisPersoonNationaliteit x", HisPersoonNationaliteit.class
        ).getResultList();
        Assert.assertFalse(historie.isEmpty());
        Assert.assertTrue(historie.size() == 1);
        HisPersoonNationaliteit hisPersoonNationaliteitRecord1 = historie.get(0);
        //Wijzig huidige nationaliteit en persisteer historie
        persoonNation.setNationaliteit(
                referentieDataRepository.vindNationaliteitOpCode("0027")
        );
        PersistentPersoonNationaliteit nieuwPersoonNation = persoonNationaliteitRepository.save(persoonNation);
        persoonNationaliteitHistorieRepository.persisteerHistorie(nieuwPersoonNation, nieuwPersoonNation.getPers(),
            20120101, null, new Date());
        //Check C/D laag
        List<HisPersoonNationaliteit> nieuweHistorie = em.createQuery(
                "Select x from HisPersoonNationaliteit x", HisPersoonNationaliteit.class
        ).getResultList();
        Assert.assertFalse(historie.isEmpty());
        //1 oude record, en 1 nieuwe record
        Assert.assertTrue(nieuweHistorie.size() == 3);
        boolean eindeGeldigHeidIsGezet = false;
        for (HisPersoonNationaliteit hisPersoonNationaliteit : nieuweHistorie) {
            if (hisPersoonNationaliteit.getDatumEindeGeldigheid() != null
                && hisPersoonNationaliteit.getDatumEindeGeldigheid() == 20120101)
            {
                eindeGeldigHeidIsGezet = true;
            }
        }
        Assert.assertTrue(eindeGeldigHeidIsGezet);
    }

    @Test
    public void testTerugWerkendeKrachtMutatie() {
        PersistentPersoonNationaliteit persoonNation = maakHuidigRecordMetHistorie(maakPersoonNationaliteit(),
            20110101);
        //Wijzig huidige nationaliteit en persisteer historie
        persoonNation.setNationaliteit(
                referentieDataRepository.vindNationaliteitOpCode("0027")
        );
        PersistentPersoonNationaliteit nieuwPersoonNation = persoonNationaliteitRepository.save(persoonNation);
        persoonNationaliteitHistorieRepository.persisteerHistorie(nieuwPersoonNation, nieuwPersoonNation.getPers(),
            20120101, null, new Date());
        List<HisPersoonNationaliteit> oudeHistorie = em.createQuery(
                "Select x from HisPersoonNationaliteit x", HisPersoonNationaliteit.class
        ).getResultList();
        //Doe een terugwerkende kracht mutatie
        PersistentPersoonNationaliteit persoonNationaliteit = maakPersoonNationaliteit();
        persoonNationaliteit.setNationaliteit(
                referentieDataRepository.vindNationaliteitOpCode("0339")
        );
        persoonNationaliteit = persoonNationaliteitRepository.save(persoonNationaliteit);
        persoonNationaliteitHistorieRepository.persisteerHistorie(persoonNationaliteit, persoonNationaliteit.getPers(),
            20110201, 20110301, new Date());
        //Check C/D laag
        List<HisPersoonNationaliteit> nieuweHistorie = em.createQuery(
                "Select x from HisPersoonNationaliteit x", HisPersoonNationaliteit.class
        ).getResultList();
        Assert.assertFalse(nieuweHistorie.isEmpty());
        Assert.assertEquals(6, nieuweHistorie.size());
        int aantalDLaagRecords = 0;
        int aantalCLaagRecords = 0;
        for (HisPersoonNationaliteit hisPersoonNationaliteit : nieuweHistorie) {
            if (hisPersoonNationaliteit.getDatumTijdVerval() != null) {
                aantalDLaagRecords++;
            } else {
                aantalCLaagRecords++;
            }
        }
        Assert.assertEquals(2, aantalDLaagRecords);
        Assert.assertEquals(4, aantalCLaagRecords);

    }

    private PersistentPersoonNationaliteit maakHuidigRecordMetHistorie(
        final PersistentPersoonNationaliteit persoonNationaliteit, final Integer datumAanvangGeldigheid)
    {
        PersistentPersoonNationaliteit persoonNation = persoonNationaliteitRepository.save(persoonNationaliteit);
        persoonNationaliteitHistorieRepository.persisteerHistorie(persoonNation, persoonNation.getPers(),
            datumAanvangGeldigheid, null, new Date(System.currentTimeMillis() - 1));
        return persoonNation;
    }

    private PersistentPersoonNationaliteit maakPersoonNationaliteit() {
        final PersistentPersoonNationaliteit persoonNationaliteit = new PersistentPersoonNationaliteit();
        persoonNationaliteit.setNationaliteit(
                referentieDataRepository.vindNationaliteitOpCode("0001")
        );
        persoonNationaliteit.setPers(
                persoonRepository.findByBurgerservicenummer("123456789")
        );
        persoonNationaliteit.setNationaliteitStatusHistorie(StatusHistorie.A);
        return persoonNationaliteit;
    }

}
