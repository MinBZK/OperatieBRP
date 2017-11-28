/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatieHistorie;
import nl.bzk.brp.service.dalapi.AfnemerindicatieRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * AfnemerindicatieJpaRepositoryImpl.
 */
@Repository
public final class AfnemerindicatieJpaRepositoryImpl implements AfnemerindicatieRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    @Override
    public List<PersoonAfnemerindicatie> haalAfnemerindicatiesOp(final long persoonId) {
        final String query = "SELECT pc FROM PersoonAfnemerindicatie pc WHERE pc.persoon.id = :persoonIdIndicatie";
        final TypedQuery<PersoonAfnemerindicatie> typedQuery =
                entityManager.createQuery(query, PersoonAfnemerindicatie.class);
        typedQuery.setParameter("persoonIdIndicatie", persoonId);
        return typedQuery.getResultList();
    }

    @Override
    public void plaatsAfnemerindicatie(final long persoonId, final short partijId, final int leveringsautorisatieId,
                                       final int dienstId, final Integer datumEindeVolgen,
                                       final Integer datumAanvangMaterielePeriode, final ZonedDateTime tijdstipRegistratie) {
        PersoonAfnemerindicatie persoonAfnemerindicatieVoorOpslag = zoekAfnemerindicatie(persoonId, partijId, leveringsautorisatieId);
        if (persoonAfnemerindicatieVoorOpslag != null && persoonAfnemerindicatieVoorOpslag.isActueelEnGeldig()) {
            final String foutBericht = String
                    .format("Er is al een afnemerindicatie voor persoon %d, partij %d en leveringsautorisatie %d.", persoonId,
                            partijId, leveringsautorisatieId);
            throw new OptimisticLockException(foutBericht);
        }
        if (persoonAfnemerindicatieVoorOpslag == null) {
            final Persoon persoon = entityManager.getReference(Persoon.class, persoonId);
            final Partij partij = entityManager.getReference(Partij.class, partijId);
            final Leveringsautorisatie leveringsautorisatie = entityManager.getReference(Leveringsautorisatie.class, leveringsautorisatieId);
            persoonAfnemerindicatieVoorOpslag = new PersoonAfnemerindicatie(persoon, partij, leveringsautorisatie);
        }
        persoonAfnemerindicatieVoorOpslag.setActueelEnGeldig(true);

        final PersoonAfnemerindicatieHistorie hisAfnemerindicatieEntity =
                new PersoonAfnemerindicatieHistorie(persoonAfnemerindicatieVoorOpslag);
        if (datumAanvangMaterielePeriode != null) {
            hisAfnemerindicatieEntity.setDatumAanvangMaterielePeriode(datumAanvangMaterielePeriode);
        }
        if (datumEindeVolgen != null) {
            hisAfnemerindicatieEntity.setDatumEindeVolgen(datumEindeVolgen);
        }
        hisAfnemerindicatieEntity.setDienstInhoud(entityManager.getReference(Dienst.class, dienstId));
        hisAfnemerindicatieEntity.setDatumTijdRegistratie(new Timestamp(tijdstipRegistratie.toInstant().toEpochMilli()));

        persoonAfnemerindicatieVoorOpslag.setDatumEindeVolgen(hisAfnemerindicatieEntity.getDatumEindeVolgen());
        persoonAfnemerindicatieVoorOpslag.setDatumAanvangMaterielePeriode(hisAfnemerindicatieEntity.getDatumAanvangMaterielePeriode());
        persoonAfnemerindicatieVoorOpslag.getPersoonAfnemerindicatieHistorieSet().add(hisAfnemerindicatieEntity);
        entityManager.persist(persoonAfnemerindicatieVoorOpslag);
    }

    @Override
    public void verwijderAfnemerindicatie(final long persoonId, final short partijId, final int leveringsautorisatieId, final int dienstIdVerval) {
        final PersoonAfnemerindicatie persoonAfnemerindicatieVoorOpslag = zoekAfnemerindicatie(persoonId, partijId, leveringsautorisatieId);
        if (persoonAfnemerindicatieVoorOpslag == null || !persoonAfnemerindicatieVoorOpslag.isActueelEnGeldig()) {
            final String foutMelding = String.format("Het verwijderen van de afnemerindicatie is mislukt, er is geen afnemerindicatie"
                            + " aangetroffen met deze gegevens: persoonId %d, leveringsautorisatieId %s, partijCode %d", persoonId,
                    leveringsautorisatieId, partijId);
            throw new OptimisticLockException(foutMelding);
        }
        persoonAfnemerindicatieVoorOpslag.setActueelEnGeldig(false);
        //zoek his record dat moet vervallen.
        PersoonAfnemerindicatieHistorie his = null;
        for (PersoonAfnemerindicatieHistorie persoonAfnemerindicatieHistorie : persoonAfnemerindicatieVoorOpslag.getPersoonAfnemerindicatieHistorieSet()) {
            if (!persoonAfnemerindicatieHistorie.isVervallen()) {
                his = persoonAfnemerindicatieHistorie;
                his.setDienstVerval(entityManager.getReference(Dienst.class, dienstIdVerval));
                his.setDatumTijdVerval(new Timestamp(System.currentTimeMillis()));
                break;
            }
        }
        Assert.notNull(his, "Niet vervallen Afnemerindicatie hisrecord niet gevonden");
        entityManager.persist(persoonAfnemerindicatieVoorOpslag);
    }

    private PersoonAfnemerindicatie zoekAfnemerindicatie(final long persoonId, final short partijId, final int leveringsautorisatieId) {
        final String query = "select pc from PersoonAfnemerindicatie pc "
                + "left join fetch pc.persoonAfnemerindicatieHistorieSet "
                + "where pc.persoon.id = :persoonId "
                + "and pc.partij.id = :partijId and pc.leveringsautorisatie.id = :leveringsautorisatieId";
        final TypedQuery<PersoonAfnemerindicatie> typedQuery =
                entityManager.createQuery(query, PersoonAfnemerindicatie.class);
        typedQuery.setParameter("persoonId", persoonId);
        typedQuery.setParameter("partijId", partijId);
        typedQuery.setParameter("leveringsautorisatieId", leveringsautorisatieId);
        final List<PersoonAfnemerindicatie> resultList = typedQuery.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }
}
