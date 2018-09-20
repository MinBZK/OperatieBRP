/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.repository.historie.PersoonNationaliteitHistorieRepository;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonNationaliteit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom implementatie uitbreiding van de JPA PersoonNationaliteitRepository.
 */
public class PersoonNationaliteitRepositoryImpl implements PersoonNationaliteitRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonNationaliteitRepositoryImpl.class);

    @Inject
    private PersoonNationaliteitHistorieRepository persoonNationaliteitHistorieRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public void persisteerHistorie(final PersistentPersoonNationaliteit persoonNationaliteit,
                                   final Integer datumAanvangGeldigheid,
                                   final Integer datumEindeGeldigheid,
                                   final Date registratieTijd)
    {
         // Selecteer records uit C-laag met overlap (in de tijd) voor het opgegeven nieuwe nationaliteit en
        // bijbehorende geldigheid.
        List<HisPersoonNationaliteit> overlappendeRecords =
            selecteerOverlappendeRecords(persoonNationaliteit, datumAanvangGeldigheid, datumEindeGeldigheid);

        // De niet geheel overlapte records zullen moeten worden aangepast (ingekort).
        kopieerGrensRecordsEnPasDezeAan(overlappendeRecords, datumAanvangGeldigheid, datumEindeGeldigheid,
                registratieTijd);

        // Alle overlapte en deels overlapte records zullen moeten worden verplaatst naar de D-laag
        verplaatsOverlapteRecordsNaarDLaag(overlappendeRecords, registratieTijd);

        // Nieuwe huidige historie moet opgeslagen worden.
        slaOpNieuwHistorieRecord(persoonNationaliteit, datumAanvangGeldigheid, datumEindeGeldigheid, registratieTijd);
    }

    /**
     * Selecteert (en haalt op) alle {@link HisPersoonNationaliteit} records uit de C-laag (dus nog niet vervallen) die
     * deels of geheel worden overlapt door het opgegeven persoonNationaliteit en die horen bij de persoon
     * waarvoor de nieuwe nationaliteit geldt. Indien er geen dergelijke records aanwezig zijn, zal een lege lijst
     * worden geretourneerd.
     *
     * @param persoonNationaliteit Nationaliteit die geregistreerd wordt.
     * @param datumAanvangGeldigheid Datum waarop de nationaliteit ingaat.
     * @param datumEindeGeldigheid Datum waarop de nationaliteit niet meer geldig is.
     * @return een lijst van voor de persoon overlapte nationaliteit records uit de C-laag.
     */
    private List<HisPersoonNationaliteit> selecteerOverlappendeRecords(
            final PersistentPersoonNationaliteit persoonNationaliteit,
            final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid)
    {
        TypedQuery<HisPersoonNationaliteit> query;
        if (datumEindeGeldigheid == null) {
            query =
                em.createQuery("SELECT hpn FROM HisPersoonNationaliteit hpn WHERE (hpn.datumAanvangGeldigheid > "
                    + ":aanvangGeldigheid OR hpn.datumEindeGeldigheid IS NULL OR hpn.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) AND hpn.datumTijdVerval IS NULL AND "
                    + "hpn.persoonNationaliteit.pers = :persoon", HisPersoonNationaliteit.class);
        } else {
            query =
                em.createQuery("SELECT hpn FROM HisPersoonNationaliteit hpn WHERE ((hpn.datumAanvangGeldigheid >= "
                    + ":aanvangGeldigheid AND hpn.datumAanvangGeldigheid < :eindeGeldigheid) OR "
                    + "(hpn.datumEindeGeldigheid <= :eindeGeldigheid AND hpn.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) OR (hpn.datumEindeGeldigheid IS NULL AND hpn.datumAanvangGeldigheid < "
                    + ":eindeGeldigheid) OR (hpn.datumAanvangGeldigheid <= :aanvangGeldigheid AND "
                    + "hpn.datumEindeGeldigheid >= :eindeGeldigheid)) AND hpn.datumTijdVerval IS NULL AND "
                    + "hpn.persoonNationaliteit.pers = :persoon", HisPersoonNationaliteit.class);
            query.setParameter("eindeGeldigheid", datumEindeGeldigheid);
        }
        query.setParameter("aanvangGeldigheid", datumAanvangGeldigheid);
        query.setParameter("persoon", persoonNationaliteit.getPers());
        return query.getResultList();
    }

     /**
     * Records die slechts deels worden overlapt, dus die aanvangen reeds voor de aanvang uit de context of nog
     * doorlopen tot na het einde uit de context, zullen moeten worden ingekort. Hiervoor worden nieuwe records aan de
     * C-laag toegevoegd, welke een kopie zijn van de deels overlapte records, maar waarvan de aanvangdatum of
     * einddatum is aangepast, conform de context.
     *
     * @param overlapteRecords alle overlapte records uit de C-laag.
     * @param datumIngangGeldigheid Datum waarop het adres ingaat
     * @param datumEindeGeldigheid Datum waarop de huidige nationaliteit vervalt.
     * @param datumTijdRegistratie Tijdstip van registratie.
     */
    private void kopieerGrensRecordsEnPasDezeAan(final List<HisPersoonNationaliteit> overlapteRecords,
            final Integer datumIngangGeldigheid, final Integer datumEindeGeldigheid, final Date datumTijdRegistratie)
    {
        try {
            for (HisPersoonNationaliteit persoonNationaliteit : overlapteRecords) {
                if (persoonNationaliteit.getDatumAanvangGeldigheid() < datumIngangGeldigheid) {
                    // Controlleer huidige nationaliteit AanvangGeldigheid of die valt voor de nieuwe nationaliteit
                    // IngangGeldigheid en kort in de huidige nationaliteit eindeGeldigheid.

                    HisPersoonNationaliteit nieuwRecord = persoonNationaliteit.clone();
                    nieuwRecord.setDatumEindeGeldigheid(datumIngangGeldigheid);
                    nieuwRecord.setDatumTijdRegistratie(datumTijdRegistratie);
                    persoonNationaliteitHistorieRepository.save(nieuwRecord);
                }
                if (datumEindeGeldigheid != null
                    && (persoonNationaliteit.getDatumEindeGeldigheid() == null
                    || persoonNationaliteit.getDatumEindeGeldigheid() > datumEindeGeldigheid))
                {
                    HisPersoonNationaliteit nieuwRecord = persoonNationaliteit.clone();
                    nieuwRecord.setDatumAanvangGeldigheid(datumEindeGeldigheid);
                    nieuwRecord.setDatumTijdRegistratie(datumTijdRegistratie);
                    persoonNationaliteitHistorieRepository.save(nieuwRecord);
                }
            }
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Fout opgetreden vanwege niet bestaande clone methode", e);
            throw new RuntimeException("Technische fout vanwege incorrecte code", e);
        }
    }

    /**
     * Alle (deels) overlapte records dienen uit de C-laag te worden gehaald en naar de D-laag te worden verplaatst,
     * daar ze niet meer geldig zijn. Hiervoor wordt het record aangepast en wordt het tijdstipvervallen ingevuld.
     *
     * @param overlapteRecords alle overlapte records uit de C-laag.
     * @param tijdstipRegistratie Tijdstip van registratie.
     */
    private void verplaatsOverlapteRecordsNaarDLaag(final List<HisPersoonNationaliteit> overlapteRecords,
            final Date tijdstipRegistratie)
    {
        for (HisPersoonNationaliteit persoonNationaliteit : overlapteRecords) {
            persoonNationaliteit.setDatumTijdVerval(tijdstipRegistratie);
            em.persist(persoonNationaliteit);
        }
    }

    /**
     * Bouwt een nieuwe {@link nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteit} instantie op,
     * op basis van een {@link nl.bzk.brp.model.operationeel.kern.PersistentPersoonNationaliteit}. Hierbij
     * worden alle waardes uit de {@link nl.bzk.brp.model.operationeel.kern.PersistentPersoonNationaliteit} instantie in
     * de {@link nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteit} instantie gezet
     * en worden zaken uit de context, zoals het tijdstip van registratie en aanvang van de geldigheid, op het
     * historisch bewust instantie gezet.
     *
     * @param persoonNationaliteit de nieuwe nationaliteit.
     * @param datumAanvangGeldigheid Datum waarop de nationaliteit ingaat.
     * @param datumEindeGeldigheid Datum waarop de nationaliteit niet meer geldig is.
     * @param tijdstipRegistratie Tijdstip van registratie.
     */
    private void slaOpNieuwHistorieRecord(final PersistentPersoonNationaliteit persoonNationaliteit,
            final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid, final Date tijdstipRegistratie)
    {
        HisPersoonNationaliteit hisPersoonNationaliteit = new HisPersoonNationaliteit();
        hisPersoonNationaliteit.setPersoonNationaliteit(persoonNationaliteit);
        hisPersoonNationaliteit.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonNationaliteit.setDatumEindeGeldigheid(datumEindeGeldigheid);
        hisPersoonNationaliteit.setDatumTijdRegistratie(tijdstipRegistratie);

        persoonNationaliteitHistorieRepository.save(hisPersoonNationaliteit);
    }
}
