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
import javax.persistence.TypedQuery;

import nl.bzk.brp.pocmotor.dal.operationeel.His_PersoonGeslachtsnaamcomponentRepository;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.pocmotor.util.DatumEnTijdUtil;

public class PersoonGeslachtsnaamComponentOGMRepositoryImpl implements PersoonGeslachtsnaamComponentOGMRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private His_PersoonGeslachtsnaamcomponentRepository his_persoonGeslachtsnaamcomponentRepository;

    @Override
    public void verwerkGeslachtsNaamWijzigingInHistorie(final PersoonGeslachtsnaamcomponent persGeslachtsNaamComp,
                                                        final BRPActie actie) {
        final Datum datumAanvangGeldigheid = actie.getDatumAanvangGeldigheid();
        final Datum datumEindeGeldigheid = null; //Nog niet gespecificeerd in berichten.
        final DatumTijd regTijd = DatumEnTijdUtil.nu();

        //Haal overlappende records op
        List<His_PersoonGeslachtsnaamcomponent> overlappendeRecords =
                selecteerOverlappendeRecords(persGeslachtsNaamComp, datumAanvangGeldigheid, datumEindeGeldigheid);

        //Maak C-Laag records en D-Laag records aan
        kopieerGrensRecordsEnPasDezeAan(overlappendeRecords,
                                        datumAanvangGeldigheid,
                                        datumEindeGeldigheid,
                                        regTijd);

        verplaatsOverlapteRecordsNaarDLaag(overlappendeRecords, regTijd);

        maakHistorieRecordAanVoorNieuweRecord(persGeslachtsNaamComp, datumAanvangGeldigheid, regTijd);
    }

    private void maakHistorieRecordAanVoorNieuweRecord(final PersoonGeslachtsnaamcomponent persGeslachtsNaamComp,
                                                       final Datum datumAanvangGeldigheid,
                                                       final DatumTijd regTijd) {

        final His_PersoonGeslachtsnaamcomponent cLaagRecord = new His_PersoonGeslachtsnaamcomponent();
        cLaagRecord.setPersoonGeslachtsnaamcomponent(persGeslachtsNaamComp);
        cLaagRecord.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        cLaagRecord.setDatumTijdRegistratie(regTijd);
        cLaagRecord.setVoorvoegsel(persGeslachtsNaamComp.getVoorvoegsel());
        cLaagRecord.setScheidingsteken(persGeslachtsNaamComp.getScheidingsteken());
        cLaagRecord.setNaam(persGeslachtsNaamComp.getNaam());
        cLaagRecord.setPredikaat(persGeslachtsNaamComp.getPredikaat());
        cLaagRecord.setAdellijkeTitel(persGeslachtsNaamComp.getAdellijkeTitel());
        em.persist(cLaagRecord);
    }

    private void verplaatsOverlapteRecordsNaarDLaag(final List<His_PersoonGeslachtsnaamcomponent> overlappendeRecords,
                                                    final DatumTijd regTijd) {
        for (His_PersoonGeslachtsnaamcomponent hisPersGeslNaam : overlappendeRecords) {
            hisPersGeslNaam.setDatumTijdVerval(regTijd);
            em.persist(hisPersGeslNaam);
        }
    }

    private void kopieerGrensRecordsEnPasDezeAan(final List<His_PersoonGeslachtsnaamcomponent> overlappendeRecords,
                                                 final Datum datumAanvangGeldigheid,
                                                 final Datum datumEindeGeldigheid,
                                                 final DatumTijd regTijd) {
        for (His_PersoonGeslachtsnaamcomponent overlappendeRecord : overlappendeRecords) {
            if (overlappendeRecord.getDatumAanvangGeldigheid().getWaarde() < datumAanvangGeldigheid.getWaarde()) {
                His_PersoonGeslachtsnaamcomponent nieuwHisRecord = overlappendeRecord.clone();
                nieuwHisRecord.setDatumEindeGeldigheid(datumAanvangGeldigheid);
                nieuwHisRecord.setDatumTijdRegistratie(regTijd);
                em.persist(nieuwHisRecord);
            } else if (datumEindeGeldigheid != null && (overlappendeRecord.getDatumEindeGeldigheid() == null
                    || overlappendeRecord.getDatumEindeGeldigheid().getWaarde() > datumEindeGeldigheid.getWaarde()))
            {
                His_PersoonGeslachtsnaamcomponent nieuwHisRecord = overlappendeRecord.clone();
                nieuwHisRecord.setDatumAanvangGeldigheid(datumEindeGeldigheid);
                nieuwHisRecord.setDatumTijdRegistratie(regTijd);
                em.persist(nieuwHisRecord);
            }
        }
    }



    private List<His_PersoonGeslachtsnaamcomponent> selecteerOverlappendeRecords(final PersoonGeslachtsnaamcomponent persGeslachtsNaamComp,
                                                                                 final Datum datumAanvangGeldigheid,
                                                                                 final Datum datumEindeGeldigheid) {
        TypedQuery<His_PersoonGeslachtsnaamcomponent> query;
        if (datumEindeGeldigheid == null) {
            query = em.createQuery("SELECT hpg FROM His_PersoonGeslachtsnaamcomponentOperationeel hpg WHERE (hpg.datumAanvangGeldigheid >" +
                    ":aanvangGeldigheid OR hpg.datumEindeGeldigheid IS NULL OR hpg.datumEindeGeldigheid >" +
                    ":aanvangGeldigheid) AND hpg.datumTijdVerval IS NULL AND hpg.persoonGeslachtsnaamcomponent.id = " +
                    ":geslNaamCompID",
                    His_PersoonGeslachtsnaamcomponent.class);
        } else {
            query = em.createQuery("SELECT hpg FROM His_PersoonGeslachtsnaamcomponentOperationeel hpg WHERE ((hpg.datumAanvangGeldigheid > "
                    + ":aanvangGeldigheid AND hpg.datumAanvangGeldigheid < :eindeGeldigheid) OR "
                    + "(hpg.datumEindeGeldigheid < :eindeGeldigheid AND hpg.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) OR (hpg.datumEindeGeldigheid IS NULL AND hpg.datumAanvangGeldigheid < "
                    + ":eindeGeligheid)) AND hpg.datumTijdVerval IS NULL AND hpg.persoonGeslachtsnaamcomponent.id = "
                    + ":geslNaamCompID",
                    His_PersoonGeslachtsnaamcomponent.class);
            query.setParameter("eindeGeldigheid", datumEindeGeldigheid);
        }
        query.setParameter("aanvangGeldigheid", datumAanvangGeldigheid);
        query.setParameter("geslNaamCompID", persGeslachtsNaamComp.getId());
        return query.getResultList();
    }
}
