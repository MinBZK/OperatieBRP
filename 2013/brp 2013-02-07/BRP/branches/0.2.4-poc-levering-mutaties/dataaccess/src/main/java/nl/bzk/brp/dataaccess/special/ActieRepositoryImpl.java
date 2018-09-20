/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.special;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.RelatieStandaardHisModel;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActieRepositoryImpl implements ActieRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActieRepositoryImpl.class);

    @PersistenceContext(unitName = "nl.bzk.brp.special")
    private EntityManager em;

    @Override
    public List<ActieModel> findByTijdstipVerwerkingMutatieIsNull() {
        TypedQuery<ActieModel> tQuery = em.createQuery(
                "SELECT actie FROM ActieModel actie WHERE actie.tijdstipVerwerkingMutatie IS NULL ORDER BY actie.tijdstipRegistratie ASC",
                ActieModel.class);

        List<ActieModel> actieList;
        try {
            actieList = tQuery.getResultList();
        } catch (NoResultException e) {
            actieList = null;
        }

        return actieList;
    }

    @Override
    public List<String> bepaalBetrokkenBsnsVanActie(final Long actieId) {
        ActieModel actieModel = em.find(ActieModel.class, actieId);

        SoortActie soortActie = actieModel.getSoort();
        TypedQuery<?> tQuery = null;

        List<String> bsns = new ArrayList<String>();

        final String adresHistorieSql =
                "SELECT adres FROM PersoonAdresHisModel adres " +
                "WHERE adres.historie.actieInhoud = :actie " +
                "OR adres.historie.actieVerval = :actie " +
                "OR adres.historie.actieAanpassingGeldigheid = :actie";

        switch (soortActie) {
            case CORRECTIE_ADRES_NL:
            case VERHUIZING:
                // PersoonBijhoudingsgemeenteHisModel
//                List<PersoonAdresHisModel> adresHistorie = em.createQuery(
//                        "SELECT adresHis FROM PersoonAdresHisModel adresHis " +
//                                "JOIN FETCH adresHis.historie.actieInhoud AS actieInhoud " +
//                                "JOIN FETCH adresHis.historie.actieVerval as actieVerval " +
//                                "JOIN FETCH adresHis.historie.actieAanpassingGeldigheid AS actieAanp " +
//                                "WHERE actieInhoud.id = :id OR actieAanp.id = :id OR actieVerval.id = :id",
//                        PersoonAdresHisModel.class).setParameter("id", actieId).getResultList();

                List<PersoonAdresHisModel> adresHistorie = em.createQuery(adresHistorieSql).setParameter("actie", actieModel).getResultList();
                for (PersoonAdresHisModel persoonAdresHisModel : adresHistorie) {
                	String bsn = persoonAdresHisModel.getPersoonAdres().getPersoon().getIdentificatienummers().getBurgerservicenummer().getWaarde();
                	if (!bsns.contains(bsn)) {
                		bsns.add(bsn);
                	}
                }
                break;
            case HUWELIJK:
            case AANGIFTE_GEBOORTE:
                List<RelatieStandaardHisModel> relatieHis = em.createQuery(
                        "SELECT relatieHis FROM RelatieStandaardHisModel relatieHis " +
                                "JOIN FETCH relatieHis.historie.actieInhoud AS actieInhoud " +
                                "JOIN FETCH relatieHis.historie.actieVerval AS actieVerval " +
                                "WHERE actieInhoud.id = :id OR actieVerval.id = :id",
                        RelatieStandaardHisModel.class).setParameter("id", actieId).getResultList();
                break;
            default:
                break;
        }

        return bsns;
    }

    @Override
    public List<String> bepaalBetrokkenBsnsVanEerdereOnverwerkteActies(final DatumTijd datumTijd) {

    	// Is nu specifiek voor adreshistorie (verhuizing/correctieadres)
    	final String bsnOnverwerktSql =
        "SELECT hisPersAdres.persoonAdres.persoon " +
        "FROM PersoonAdresHisModel hisPersAdres LEFT JOIN hisPersAdres.historie.actieInhoud as actieinh LEFT JOIN hisPersAdres.historie.actieVerval as actieverval LEFT JOIN hisPersAdres.historie.actieAanpassingGeldigheid as actieaanpgel " +
        "WHERE (actieinh.tijdstipVerwerkingMutatie IS NULL " +
        "AND actieinh.tijdstipRegistratie < :tijdstipRegistratie) " +
        "OR (actieverval.tijdstipVerwerkingMutatie IS NULL " +
        "AND actieverval.tijdstipRegistratie < :tijdstipRegistratie) " +
        "OR (actieaanpgel.tijdstipVerwerkingMutatie IS NULL " +
        "AND actieaanpgel.tijdstipRegistratie < :tijdstipRegistratie)";
    	List<PersoonModel> persoonLijst = em.createQuery(bsnOnverwerktSql).setParameter("tijdstipRegistratie", datumTijd).getResultList();

    	List<String> bsns = new ArrayList<String>();
    	for (PersoonModel persoon : persoonLijst) {
    		bsns.add(persoon.getIdentificatienummers().getBurgerservicenummer().getWaarde());
    	}
        return bsns;
    }

    /**
     * Er komt een exception als het lock niet verkregen kan worden.
     * org.postgresql.util.PSQLException: ERROR: could not obtain lock on row in relation "actie"
     */

    @Override
    public boolean lockTijdstipVerwerkingMutatie(final long id) {
        try {
            em.createNativeQuery("SELECT 42 FROM Kern.Actie a WHERE a.id=:id FOR UPDATE NOWAIT").setParameter("id", id)
                    .getSingleResult();
            return true;
        } catch (Exception e) {
            LOGGER.error("", e);
            return false;
        }
    }
}
