/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingLeveringGroepModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

import org.springframework.stereotype.Repository;


/**
 * De Class AdministratieveHandelingVerwerkerRepositoryImpl, de implementatie voor de interface
 * AdministratieveHandelingVerwerkerRepository. Deze klasse haalt Administratieve Handelingen op uit de data-laag.
 */
@Repository
public class AdministratieveHandelingVerwerkerRepositoryImpl implements AdministratieveHandelingVerwerkerRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager em;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager emMaster;

    @Override
    public final List<Integer> haalAdministratieveHandelingPersoonIds(final Long administratieveHandelingId) {
        final TypedQuery<Integer> tQuery = em.createQuery("SELECT afadmo.persoon.id FROM HisPersoonAfgeleidAdministratiefModel afadmo"
                                                              + " WHERE afadmo.administratieveHandeling.id = :id", Integer.class);

        tQuery.setParameter("id", administratieveHandelingId);

        return tQuery.getResultList();
    }

    @Override
    @Regels(Regel.R1988)
    public final void markeerAdministratieveHandelingAlsVerwerkt(final Long administratieveHandelingId) {
        final AdministratieveHandelingModel administratieveHandelingModelMaster =
                emMaster.find(AdministratieveHandelingModel.class, administratieveHandelingId);

        final DatumTijdAttribuut huidigeDatumTijd = DatumTijdAttribuut.nu();
        final AdministratieveHandelingLeveringGroepModel levering = new AdministratieveHandelingLeveringGroepModel(huidigeDatumTijd);
        administratieveHandelingModelMaster.setLevering(levering);
    }

    @Override
    public final boolean magAdministratieveHandelingVerwerktWorden(
            final AdministratieveHandelingModel administratieveHandeling,
            final List<Integer> persoonIds,
            final List<SoortAdministratieveHandeling> overslaanSoortAdministratieveHandelingen)
    {
        /**
         * zie {@link AdministratieveHandelingVerwerkerRepository}.
         *
         * select NOT exists(
         select a.id from kern.his_persafgeleidadministrati afad
         join kern.admhnd a on a.id = afad.admhnd
         where a.tslev is null
         and a.tsreg < (select tsreg from kern.admhnd where id = 1)
         and afad.pers in (2, 4, 1)
         )
         */

        final StringBuilder querySb = new StringBuilder("SELECT CASE WHEN (count(afad) > 0) THEN false ELSE true END"
                + " FROM HisPersoonAfgeleidAdministratiefModel afad"
                + " WHERE afad.administratieveHandeling.levering.tijdstipLevering IS NULL"
                + " AND afad.administratieveHandeling.tijdstipRegistratie < :huidigeTsreg"
                + " AND afad.persoon.id IN :persIds");

        final boolean heeftTeNegerenSoortAdministratieveHandelingen =
                overslaanSoortAdministratieveHandelingen != null && !overslaanSoortAdministratieveHandelingen.isEmpty();

        if (heeftTeNegerenSoortAdministratieveHandelingen) {
            querySb.append(
                    " AND afad.administratieveHandeling.soort.waarde NOT IN :overslaanSoortAdministratieveHandelingen");
        }

        final TypedQuery<Boolean> tQuery = em.createQuery(querySb.toString(), Boolean.class);
        tQuery.setParameter("huidigeTsreg", administratieveHandeling.getTijdstipRegistratie());
        tQuery.setParameter("persIds", persoonIds);

        if (heeftTeNegerenSoortAdministratieveHandelingen) {
            tQuery.setParameter("overslaanSoortAdministratieveHandelingen", overslaanSoortAdministratieveHandelingen);
        }
        return tQuery.getSingleResult();
    }
}
