/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.brp.service.dalapi.SelectieRepository;
import org.junit.Assert;
import org.junit.Test;

@Data(resources = {"classpath:/data/testdata-autaut.xml"})
public class SelectieRepositoryImplTest extends AbstractDataAccessTest {

    @Inject
    private SelectieRepository selectieRepository;

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager em;

    @Test
    public void getTakenGeplandVoorVandaagLeeg() {
        final List<Selectietaak> takenGeplandVoorVandaag = selectieRepository.getTakenGeplandVoorVandaag();
        Assert.assertTrue(takenGeplandVoorVandaag.isEmpty());
    }

    @Test
    public void getTakenGeplandVoorVandaag() {
        final Dienst dienst = em.getReference(Dienst.class, 4);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = em.getReference(ToegangLeveringsAutorisatie.class, 1);
        final Selectietaak selectietaak = new Selectietaak(dienst, toegangLeveringsAutorisatie, 1);
        selectietaak.setStatus((short) SelectietaakStatus.UITVOERBAAR.getId());
        selectietaak.setActueelEnGeldig(true);
        selectietaak.setDatumPlanning(DatumUtil.vandaag());
        em.persist(selectietaak);

        final List<Selectietaak> takenGeplandVoorVandaag = selectieRepository.getTakenGeplandVoorVandaag();
        Assert.assertEquals(1, takenGeplandVoorVandaag.size());
    }

    @Test
    public void getTakenGeplandVoorVandaagMeerdere() {
        final Dienst dienst = em.getReference(Dienst.class, 4);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = em.getReference(ToegangLeveringsAutorisatie.class, 1);
        final Selectietaak vandaag1 = new Selectietaak(dienst, toegangLeveringsAutorisatie, 1);
        vandaag1.setStatus((short) SelectietaakStatus.UITVOERBAAR.getId());
        vandaag1.setDatumPlanning(DatumUtil.vandaag());
        vandaag1.setActueelEnGeldig(true);
        em.persist(vandaag1);

        final Selectietaak vandaag2 = new Selectietaak(dienst, toegangLeveringsAutorisatie, 2);
        vandaag2.setStatus((short) SelectietaakStatus.UITVOERBAAR.getId());
        vandaag2.setDatumPlanning(DatumUtil.vandaag());
        vandaag2.setActueelEnGeldig(true);
        em.persist(vandaag2);

        final Selectietaak gisteren1 = new Selectietaak(dienst, toegangLeveringsAutorisatie, 3);
        gisteren1.setStatus((short) SelectietaakStatus.CONTROLEREN.getId());
        gisteren1.setDatumPlanning(DatumUtil.gisteren());
        gisteren1.setActueelEnGeldig(true);
        em.persist(gisteren1);

        final Selectietaak morgen1 = new Selectietaak(dienst, toegangLeveringsAutorisatie, 4);
        morgen1.setStatus((short) SelectietaakStatus.CONTROLEREN.getId());
        morgen1.setDatumPlanning(DatumUtil.morgen());
        morgen1.setActueelEnGeldig(true);
        em.persist(morgen1);

        final List<Selectietaak> takenGeplandVoorVandaag = selectieRepository.getTakenGeplandVoorVandaag();
        Assert.assertEquals(2, takenGeplandVoorVandaag.size());
        Assert.assertEquals(2, takenGeplandVoorVandaag.stream().filter(selectietaak
                -> selectietaak.getStatus() == SelectietaakStatus.UITVOERBAAR.getId()).count());

    }

    /**
     * foutscenario
     */
    @Test
    public void getTakenGeplandVoorVandaagDatumPlanningMorgen() {
        final Dienst dienst = em.getReference(Dienst.class, 4);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = em.getReference(ToegangLeveringsAutorisatie.class, 1);
        final Selectietaak selectietaak = new Selectietaak(dienst, toegangLeveringsAutorisatie, 1);
        selectietaak.setStatus((short) SelectietaakStatus.UITVOERBAAR.getId());
        selectietaak.setDatumPlanning(DatumUtil.morgen());
        em.persist(selectietaak);

        final List<Selectietaak> takenGeplandVoorVandaag = selectieRepository.getTakenGeplandVoorVandaag();
        Assert.assertTrue(takenGeplandVoorVandaag.isEmpty());
    }

    /**
     * foutscenario
     */
    @Test
    public void getTakenGeplandVoorVandaagStatusControleren() {
        final Dienst dienst = em.getReference(Dienst.class, 4);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = em.getReference(ToegangLeveringsAutorisatie.class, 1);
        final Selectietaak selectietaak = new Selectietaak(dienst, toegangLeveringsAutorisatie, 1);
        selectietaak.setStatus((short) SelectietaakStatus.CONTROLEREN.getId());
        selectietaak.setDatumPlanning(DatumUtil.vandaag());
        em.persist(selectietaak);

        final List<Selectietaak> takenGeplandVoorVandaag = selectieRepository.getTakenGeplandVoorVandaag();
        Assert.assertTrue(takenGeplandVoorVandaag.isEmpty());
    }

    /**
     * foutscenario
     */
    @Test
    public void getTakenGeplandVoorVandaagStatusWordUitgevoerd() {
        final Dienst dienst = em.getReference(Dienst.class, 4);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = em.getReference(ToegangLeveringsAutorisatie.class, 1);
        final Selectietaak selectietaak = new Selectietaak(dienst, toegangLeveringsAutorisatie, 1);
        selectietaak.setStatus((short) SelectietaakStatus.IN_UITVOERING.getId());
        selectietaak.setDatumPlanning(DatumUtil.vandaag());
        em.persist(selectietaak);

        final List<Selectietaak> takenGeplandVoorVandaag = selectieRepository.getTakenGeplandVoorVandaag();
        Assert.assertTrue(takenGeplandVoorVandaag.isEmpty());
    }


    @Test
    public void persistStartSelectie() {
        final Dienst dienst = em.getReference(Dienst.class, 4);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = em.getReference(ToegangLeveringsAutorisatie.class, 1);
        final Selectierun selectierun =
                new Selectierun(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime()));
        final Selectietaak selectietaak = new Selectietaak(dienst, toegangLeveringsAutorisatie, 1);
        selectietaak.setStatus((short) SelectietaakStatus.IN_UITVOERING.getId());
        selectierun.getSelectieTaken().add(selectietaak);
        selectieRepository.slaSelectieOp(selectierun);
    }
}
