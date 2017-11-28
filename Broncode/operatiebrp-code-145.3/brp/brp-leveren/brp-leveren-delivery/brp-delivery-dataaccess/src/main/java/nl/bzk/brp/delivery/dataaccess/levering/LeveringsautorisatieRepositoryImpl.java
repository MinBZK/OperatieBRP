/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.util.Calendar;
import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.service.dalapi.LeveringsautorisatieRepository;
import org.springframework.stereotype.Repository;


/**
 * Toegang- leveringsautorisatie repository.
 */
@Repository
@Bedrijfsregel(Regel.R2258)
public final class LeveringsautorisatieRepositoryImpl implements LeveringsautorisatieRepository {

    private static final int DATUM_EINDE_TERUG_MAAND = 3;

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    @Override
    public List<ToegangLeveringsAutorisatie> haalAlleToegangLeveringsautorisatiesOpZonderAssociaties() {
        final TypedQuery<ToegangLeveringsAutorisatie> typedQuery =
                entityManager.createQuery("SELECT tla FROM "
                                + "ToegangLeveringsAutorisatie tla "
                                + "WHERE (tla.datumEinde IS NULL OR tla.datumEinde > :dateinde) AND tla.isActueelEnGeldig = true "
                                + "AND tla.geautoriseerde.isActueelEnGeldig = true AND tla.geautoriseerde.partij.isActueelEnGeldig = true",
                        ToegangLeveringsAutorisatie.class);
        zetDefaults(typedQuery);
        final List<ToegangLeveringsAutorisatie> resultList = typedQuery.getResultList();
        entityManager.clear();
        return resultList;
    }

    @Override
    public List<Leveringsautorisatie> haalAlleLeveringsautorisatiesOpZonderAssocaties() {
        final TypedQuery<Leveringsautorisatie> typedQuery =
                entityManager.createQuery("SELECT la FROM Leveringsautorisatie la " +
                                "WHERE (la.datumEinde IS NULL OR la.datumEinde > :dateinde) AND la.isActueelEnGeldig = true",
                        Leveringsautorisatie.class);
        zetDefaults(typedQuery);
        final List<Leveringsautorisatie> resultList = typedQuery.getResultList();
        entityManager.clear();
        return resultList;
    }

    @Override
    public List<Dienstbundel> haalAlleDienstbundelsOpZonderAssocaties() {
        final TypedQuery<Dienstbundel> typedQuery =
                entityManager.createQuery("SELECT db FROM Dienstbundel db WHERE "
                        + "db.indicatieNaderePopulatiebeperkingVolledigGeconverteerd IS NULL "
                        + "AND (db.datumEinde is NULL OR db.datumEinde > :dateinde) AND db.isActueelEnGeldig = true", Dienstbundel.class);
        zetDefaults(typedQuery);
        final List<Dienstbundel> resultList = typedQuery.getResultList();
        entityManager.clear();
        return resultList;
    }

    @Override
    public List<Dienst> haalAlleDienstenOpZonderAssocaties() {
        final TypedQuery<Dienst> typedQuery =
                entityManager.createQuery("SELECT d FROM Dienst d  "
                        + "WHERE (d.datumEinde IS NULL OR d.datumEinde > :dateinde) AND d.isActueelEnGeldig = true", Dienst.class);
        zetDefaults(typedQuery);
        final List<Dienst> resultList = typedQuery.getResultList();
        entityManager.clear();
        return resultList;
    }

    @Override
    public List<DienstbundelGroep> haalAlleDienstbundelGroepenOpZonderAssocaties() {
        final TypedQuery<DienstbundelGroep> query = entityManager.createQuery("SELECT dbg FROM DienstbundelGroep dbg, Dienstbundel db "
                + "WHERE dbg.dienstbundel = db.id "
                + "AND (db.datumEinde IS NULL OR db.datumEinde > :dateinde) AND db.isActueelEnGeldig = true", DienstbundelGroep.class);
        zetDefaults(query);
        final List<DienstbundelGroep> resultList = query.getResultList();
        entityManager.clear();
        return resultList;
    }

    @Override
    public List<DienstbundelLo3Rubriek> haalAlleDienstbundelLo3Rubrieken() {
        final TypedQuery<DienstbundelLo3Rubriek> query = entityManager.createQuery("SELECT dbr FROM DienstbundelLo3Rubriek dbr, Dienstbundel db "
                + "JOIN FETCH dbr.lo3Rubriek "
                + "WHERE dbr.dienstbundel = db.id "
                + "AND (db.datumEinde IS NULL OR db.datumEinde > :dateinde) AND db.isActueelEnGeldig = true", DienstbundelLo3Rubriek.class);
        zetDefaults(query);
        final List<DienstbundelLo3Rubriek> resultList = query.getResultList();
        entityManager.clear();
        return resultList;
    }

    @Override
    public List<DienstbundelGroepAttribuut> haalAlleDienstbundelGroepAttributenOpZonderAssocaties() {
        final TypedQuery<DienstbundelGroepAttribuut> query = entityManager.createQuery("SELECT dba FROM DienstbundelGroepAttribuut dba, "
                + "DienstbundelGroep dbg, Dienstbundel db "
                + "WHERE dba.dienstbundelGroep = dbg.id AND dbg.dienstbundel = db.id "
                + "AND (db.datumEinde IS NULL OR db.datumEinde > :dateinde) "
                + "AND db.isActueelEnGeldig = true", DienstbundelGroepAttribuut.class);
        zetDefaults(query);
        final List<DienstbundelGroepAttribuut> resultList = query.getResultList();
        entityManager.clear();
        return resultList;
    }

    private void zetDefaults(final Query query) {
        final Calendar cal = Calendar.getInstance();
        final int maandenTerug = -DATUM_EINDE_TERUG_MAAND;
        cal.add(Calendar.MONTH, maandenTerug);
        final Integer waarde = DatumUtil.vanDatumNaarInteger(cal.getTime());
        query.setParameter("dateinde", waarde);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
    }
}
