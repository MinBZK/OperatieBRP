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
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.PersistentActie;
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

    @Inject
    private PartijRepository partijRepository;

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
        PersistentActie actie = maakActie();
        persoonNationaliteitHistorieRepository.persisteerHistorie(persoonNationaliteit, persoonNationaliteit.getPers(),
                actie, 20120101, null);
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
            Assert.assertEquals(hisPersoonNationaliteit.getActieInhoud().getId(), actie.getId());
            Assert.assertNull(hisPersoonNationaliteit.getActieVerval());
            Assert.assertNull(hisPersoonNationaliteit.getActieAanpassingGeldigheid());
        }
    }

    @Test
    public void testWijzigingNationaliteit() {
        PersistentPersoonNationaliteit persoonNation = maakHuidigRecordMetHistorie(maakPersoonNationaliteit(), 20110101);
        PersistentActie actie = maakActie();
        //1 Historie record aanwezig:
        List<HisPersoonNationaliteit> huidigeHistorie = em.createQuery(
                "Select x from HisPersoonNationaliteit x", HisPersoonNationaliteit.class
        ).getResultList();
        Assert.assertFalse(huidigeHistorie.isEmpty());
        Assert.assertTrue(huidigeHistorie.size() == 1);
        //Wijzig huidige nationaliteit en persisteer huidigeHistorie
        persoonNation.setNationaliteit(
                referentieDataRepository.vindNationaliteitOpCode("0027")
        );
        PersistentPersoonNationaliteit nieuwPersoonNation = persoonNationaliteitRepository.save(persoonNation);
        persoonNationaliteitHistorieRepository.persisteerHistorie(nieuwPersoonNation, nieuwPersoonNation.getPers(),
            actie, 20120101, null);
        //Check C/D laag
        List<HisPersoonNationaliteit> nieuweHistorie = em.createQuery(
                "Select x from HisPersoonNationaliteit x", HisPersoonNationaliteit.class
        ).getResultList();
        Assert.assertFalse(nieuweHistorie.isEmpty());
        //1 oude record, en 1 nieuwe record
        Assert.assertTrue(nieuweHistorie.size() == 3);
        boolean eindeGeldigHeidIsGezet = false;
        boolean recordIsVervallen = false;
        boolean nieuwRecordIsGemaakt = false;
        for (HisPersoonNationaliteit hisPersoonNationaliteit : nieuweHistorie) {
            if (hisPersoonNationaliteit.getDatumEindeGeldigheid() != null
                && hisPersoonNationaliteit.getDatumEindeGeldigheid() == 20120101)
            {
                eindeGeldigHeidIsGezet = true;
                Assert.assertEquals(hisPersoonNationaliteit.getActieAanpassingGeldigheid().getId(), actie.getId());
            }

            if (hisPersoonNationaliteit.getDatumTijdVerval() != null) {
                recordIsVervallen = true;
                Assert.assertEquals(hisPersoonNationaliteit.getActieVerval().getId(), actie.getId());
            }

            if (hisPersoonNationaliteit.getDatumEindeGeldigheid() == null
                && hisPersoonNationaliteit.getDatumTijdVerval() == null)
            {
                nieuwRecordIsGemaakt = true;
                Assert.assertEquals(hisPersoonNationaliteit.getActieInhoud().getId(), actie.getId());
            }
        }
        Assert.assertTrue(eindeGeldigHeidIsGezet);
        Assert.assertTrue(recordIsVervallen);
        Assert.assertTrue(nieuwRecordIsGemaakt);
    }

    @Test
    public void testTerugWerkendeKrachtMutatie() {
        PersistentActie actie1 = maakActie();
        PersistentPersoonNationaliteit persoonNation = maakHuidigRecordMetHistorie(maakPersoonNationaliteit(),
            20110101);
        //Wijzig huidige nationaliteit en persisteer historie
        persoonNation.setNationaliteit(
                referentieDataRepository.vindNationaliteitOpCode("0027")
        );
        PersistentPersoonNationaliteit nieuwPersoonNation = persoonNationaliteitRepository.save(persoonNation);
        persoonNationaliteitHistorieRepository.persisteerHistorie(nieuwPersoonNation, nieuwPersoonNation.getPers(),
            actie1, 20120101, null);

        //Check C/D laag
        List<HisPersoonNationaliteit> nieuweHistorie1 = em.createQuery(
                "Select x from HisPersoonNationaliteit x", HisPersoonNationaliteit.class
        ).getResultList();
        Assert.assertFalse(nieuweHistorie1.isEmpty());
        Assert.assertEquals(3, nieuweHistorie1.size());
        int aantalDLaagRecords1 = 0;
        int aantalCLaagRecords1 = 0;
        for (HisPersoonNationaliteit hisPersoonNationaliteit : nieuweHistorie1) {
            if (hisPersoonNationaliteit.getDatumTijdVerval() != null) {
                aantalDLaagRecords1++;
                Assert.assertEquals(hisPersoonNationaliteit.getActieVerval().getId(), actie1.getId());
            } else {
                aantalCLaagRecords1++;
                if (hisPersoonNationaliteit.getDatumEindeGeldigheid() == null
                        && hisPersoonNationaliteit.getDatumTijdVerval() == null)
                {
                    Assert.assertEquals(hisPersoonNationaliteit.getActieInhoud().getId(), actie1.getId());
                } else {
                    Assert.assertEquals(hisPersoonNationaliteit.getActieAanpassingGeldigheid().getId(), actie1.getId());
                }
            }
        }
        Assert.assertEquals(1, aantalDLaagRecords1);
        Assert.assertEquals(2, aantalCLaagRecords1);


        //Doe een terugwerkende kracht mutatie
        PersistentPersoonNationaliteit persoonNationaliteit = maakPersoonNationaliteit();
        persoonNationaliteit.setNationaliteit(
                referentieDataRepository.vindNationaliteitOpCode("0339")
        );
        persoonNationaliteit = persoonNationaliteitRepository.save(persoonNationaliteit);

        PersistentActie actie2 = maakActie();
        persoonNationaliteitHistorieRepository.persisteerHistorie(persoonNationaliteit, persoonNationaliteit.getPers(),
            actie2, 20110201, 20110301);


        //Check C/D laag
        List<HisPersoonNationaliteit> nieuweHistorie2 = em.createQuery(
                "Select x from HisPersoonNationaliteit x", HisPersoonNationaliteit.class
        ).getResultList();
        Assert.assertFalse(nieuweHistorie2.isEmpty());
        Assert.assertEquals(6, nieuweHistorie2.size());
        int aantalDLaagRecords2 = 0;
        int aantalCLaagRecords2 = 0;
        for (HisPersoonNationaliteit hisPersoonNationaliteit : nieuweHistorie2) {
            if (hisPersoonNationaliteit.getDatumTijdVerval() != null) {
                aantalDLaagRecords2++;
                if (!hisPersoonNationaliteit.getActieVerval().getId().equals(actie1.getId())) {
                    Assert.assertEquals(hisPersoonNationaliteit.getActieVerval().getId(), actie2.getId());
                }
            } else {
                aantalCLaagRecords2++;
                if (hisPersoonNationaliteit.getDatumEindeGeldigheid() == null
                        && hisPersoonNationaliteit.getDatumTijdVerval() == null)
                {
                    if (!hisPersoonNationaliteit.getActieInhoud().getId().equals(actie1.getId())) {
                        Assert.assertEquals(hisPersoonNationaliteit.getActieInhoud().getId(), actie2.getId());
                    }
                } else {
                    if (hisPersoonNationaliteit.getActieAanpassingGeldigheid() != null) {
                        if (!hisPersoonNationaliteit.getActieAanpassingGeldigheid().getId().equals(actie1.getId())) {
                            Assert.assertEquals(hisPersoonNationaliteit.getActieAanpassingGeldigheid().getId(), actie2.getId());
                        }
                    } else {
                        //Dit is dus het C laag record wat overeenkomt met de terugwerkende kwacht mutatie.
                        Assert.assertEquals(hisPersoonNationaliteit.getActieInhoud().getId(), actie2.getId());
                    }
                }
            }
        }
        Assert.assertEquals(2, aantalDLaagRecords2);
        Assert.assertEquals(4, aantalCLaagRecords2);

    }

    private PersistentPersoonNationaliteit maakHuidigRecordMetHistorie(
        final PersistentPersoonNationaliteit persoonNationaliteit, final Integer datumAanvangGeldigheid)
    {
        PersistentPersoonNationaliteit persoonNation = persoonNationaliteitRepository.save(persoonNationaliteit);
        PersistentActie actie = maakActie();
        persoonNationaliteitHistorieRepository.persisteerHistorie(persoonNation, persoonNation.getPers(),
            actie, datumAanvangGeldigheid, null);
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

    private PersistentActie maakActie() {
        PersistentActie actie = new PersistentActie();
        actie.setTijdstipRegistratie(new Date(System.currentTimeMillis() - 1));
        actie.setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
        actie.setPartij(
                partijRepository.findOne(4)
        );
        em.persist(actie);
        return actie;
    }

}
