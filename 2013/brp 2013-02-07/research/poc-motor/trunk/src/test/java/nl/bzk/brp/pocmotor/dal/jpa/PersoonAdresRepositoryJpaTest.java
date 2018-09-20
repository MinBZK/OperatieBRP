/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.pocmotor.dal.AbstractRepositoryTest;
import nl.bzk.brp.pocmotor.dal.logisch.LandLGMRepository;
import nl.bzk.brp.pocmotor.dal.logisch.PersoonAdresLGMRepository;
import nl.bzk.brp.pocmotor.dal.logisch.PersoonLGMRepository;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Huisnummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Landcode;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonAdresIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonAdresStandaard;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonAdres;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_PersoonAdres;
import nl.bzk.brp.pocmotor.util.DatumEnTijdUtil;
import org.junit.Test;

public class PersoonAdresRepositoryJpaTest extends AbstractRepositoryTest {

    @Inject
    private PersoonAdresLGMRepository persoonAdresLGMRepository;

    @Inject
    private PersoonLGMRepository persoonLGMRepository;

    @Inject
    private LandLGMRepository landLGMRepo;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testSimpeleVerhuizing() {
        Persoon verhuizende = persoonLGMRepository.findOne(1L);

        PersoonAdres nieuwAdres = new PersoonAdres();
        nieuwAdres.setIdentiteit(new PersoonAdresIdentiteit());
        nieuwAdres.getIdentiteit().setPersoon(verhuizende);

        nieuwAdres.setStandaard(new PersoonAdresStandaard());
        Huisnummer huisnummer = new Huisnummer();
        huisnummer.setWaarde("33");
        nieuwAdres.getStandaard().setHuisnummer(huisnummer);

        Landcode lc = new Landcode();
        lc.setWaarde("6030");
        nieuwAdres.getStandaard().setLand(landLGMRepo.findByIdentiteitLandcode(lc));

        persoonAdresLGMRepository.opslaanNieuwPersoonAdres(nieuwAdres, DatumEnTijdUtil.vandaag(), null, DatumEnTijdUtil.nu());

        final List<His_PersoonAdres> CLaagRecords = em.createQuery("SELECT hisadres FROM His_PersoonAdresOperationeel hisadres " +
                "WHERE hisadres.persoonAdres.id = :waarde").setParameter("waarde", 1L).getResultList();

        Assert.assertTrue(CLaagRecords.size() == 5);
    }
}
