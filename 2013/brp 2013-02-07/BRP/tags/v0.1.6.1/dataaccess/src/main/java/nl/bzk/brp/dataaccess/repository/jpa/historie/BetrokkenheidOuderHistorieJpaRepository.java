/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.repository.historie.BetrokkenheidOuderHistorieRepository;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidOuder;
import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.operationeel.kern.PersistentBetrokkenheid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Repository voor de {@link nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidOuder} class en standaard implementatie
 * van de {@link nl.bzk.brp.dataaccess.repository.historie.BetrokkenheidOuderHistorieRepository} class.
 */
@Repository
public class BetrokkenheidOuderHistorieJpaRepository implements BetrokkenheidOuderHistorieRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BetrokkenheidOuderHistorieJpaRepository.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void opslaanHistorie(final PersistentBetrokkenheid ouderBetrokkenheid, final PersistentActie actie,
                                final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid)
    {
        // Selecteer records uit C-laag met overlap (in de tijd) voor de opgegeven nieuwe ouder betrokkenheid en
        // bijbehorende geldigheid.
        List<HisBetrokkenheidOuder> overlappendeRecords =
            selecteerOverlappendeRecords(ouderBetrokkenheid, datumAanvangGeldigheid, datumEindeGeldigheid);

        // De niet geheel overlapte records zullen moeten worden aangepast (ingekort).
        kopieerGrensRecordsEnPasDezeAan(overlappendeRecords, actie, datumAanvangGeldigheid, datumEindeGeldigheid);

        // Alle overlapte en deels overlapte records zullen moeten worden verplaatst naar de D-laag
        verplaatsOverlapteRecordsNaarDLaag(overlappendeRecords, actie);

        // Nieuwe huidige historie moet opgeslagen worden.
        slaOpNieuwHistorie(ouderBetrokkenheid, actie, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

    /**
     * Selecteert (en haalt op) alle {@link HisBetrokkenheidOuder} records uit de C-laag (dus nog niet vervallen) die
     * deels of geheel worden overlapt door de opgegeven betrokkenheid en bijbehorende context en die horen bij de
     * relatie en betrokkene waarvoor de nieuwe betrokkenheid geldt. Indien er geen dergelijke records aanwezig zijn,
     * zal een lege lijst worden geretourneerd.
     *
     * @param betrokkenheid de nieuwe betrokkenheid.
     * @param datumAanvangGeldigheid Datum waarop de betrokkenheid ingaat.
     * @param datumEindeGeldigheid Datum waarop de betrokkenheid niet meer geldig is.
     * @return een lijst van voor de relatie en betrokkene overlapte betrokkenheid records uit de C-laag.
     */
    private List<HisBetrokkenheidOuder> selecteerOverlappendeRecords(final PersistentBetrokkenheid betrokkenheid,
        final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid)
    {
        TypedQuery<HisBetrokkenheidOuder> query;
        if (datumEindeGeldigheid == null) {
            query =
                em.createQuery("SELECT hbo FROM HisBetrokkenheidOuder hbo WHERE (hbo.datumAanvangGeldigheid > "
                    + ":aanvangGeldigheid OR hbo.datumEindeGeldigheid IS NULL OR hbo.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) AND hbo.datumTijdVerval IS NULL AND "
                    + "hbo.betrokkenheid.id = :betrokkenheidId", HisBetrokkenheidOuder.class);
        } else {
            query =
                em.createQuery("SELECT hbo FROM HisBetrokkenheidOuder hbo WHERE ((hbo.datumAanvangGeldigheid >= "
                    + ":aanvangGeldigheid AND hbo.datumAanvangGeldigheid < :eindeGeldigheid) OR "
                    + "(hbo.datumEindeGeldigheid <= :eindeGeldigheid AND hbo.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) OR (hbo.datumEindeGeldigheid IS NULL AND hbo.datumAanvangGeldigheid < "
                    + ":eindeGeldigheid) OR (hbo.datumAanvangGeldigheid <= :aanvangGeldigheid AND "
                    + "hbo.datumEindeGeldigheid >= :eindeGeldigheid)) AND hbo.datumTijdVerval IS NULL AND "
                    + "hbo.betrokkenheid.id = :betrokkenheidId", HisBetrokkenheidOuder.class);
            query.setParameter("eindeGeldigheid", datumEindeGeldigheid);
        }
        query.setParameter("aanvangGeldigheid", datumAanvangGeldigheid);
        query.setParameter("betrokkenheidId", betrokkenheid.getId());
        return query.getResultList();
    }

    /**
     * Records die slechts deels worden overlapt, dus die aanvangen reeds voor de aanvang uit de context of nog
     * doorlopen tot na het einde uit de context, zullen moeten worden ingekort. Hiervoor worden nieuwe records aan de
     * C-laag toegevoegd, welke een kopie zijn van de deels overlapte records, maar waarvan de aanvangdatum of
     * einddatum is aangepast, conform de context.
     *
     * @param overlapteRecords alle overlapte records uit de C-laag.
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     * @param datumIngangGeldigheid Datum waarop de betrokkenheid ingaat
     * @param datumEindeGeldigheid Datum waarop de huidige betrokkenheid vervalt.
     */
    private void kopieerGrensRecordsEnPasDezeAan(final List<HisBetrokkenheidOuder> overlapteRecords,
                                                 final PersistentActie actie,
                                                 final Integer datumIngangGeldigheid,
                                                 final Integer datumEindeGeldigheid)
    {
        try {
            for (HisBetrokkenheidOuder betrokkenheidOuder : overlapteRecords) {
                if (betrokkenheidOuder.getDatumAanvangGeldigheid() < datumIngangGeldigheid) {
                    // Controlleer huidige betrokkenheid AanvangGeldigheid of die valt voor de nieuwe betrokkenheid
                    // IngangGeldigheid en kort in de huidige betrokkenheid eindeGeldigheid

                    HisBetrokkenheidOuder nieuwOuderBetrokkenheid = betrokkenheidOuder.clone();
                    nieuwOuderBetrokkenheid.setDatumEindeGeldigheid(datumIngangGeldigheid);
                    nieuwOuderBetrokkenheid.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
                    nieuwOuderBetrokkenheid.setActieAanpassingGeldigheid(actie);
                    em.persist(nieuwOuderBetrokkenheid);
                }
                if (datumEindeGeldigheid != null
                    && (betrokkenheidOuder.getDatumEindeGeldigheid() == null
                    || betrokkenheidOuder.getDatumEindeGeldigheid() > datumEindeGeldigheid))
                {
                    HisBetrokkenheidOuder nieuwOuderBetrokkenheid = betrokkenheidOuder.clone();
                    nieuwOuderBetrokkenheid.setDatumAanvangGeldigheid(datumEindeGeldigheid);
                    nieuwOuderBetrokkenheid.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
                    nieuwOuderBetrokkenheid.setActieAanpassingGeldigheid(actie);
                    em.persist(nieuwOuderBetrokkenheid);
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
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     */
    private void verplaatsOverlapteRecordsNaarDLaag(final List<HisBetrokkenheidOuder> overlapteRecords,
                                                    final PersistentActie actie)
    {
        for (HisBetrokkenheidOuder betrokkenheidOuder : overlapteRecords) {
            betrokkenheidOuder.setDatumTijdVerval(actie.getTijdstipRegistratie());
            betrokkenheidOuder.setActieVerval(actie);
            em.persist(betrokkenheidOuder);
        }
    }

    /**
     * Bouwt een nieuwe {@link HisBetrokkenheidOuder} instantie op, op basis van een {@link PersistentBetrokkenheid}.
     * Hierbij worden alle waardes uit de {@link PersistentBetrokkenheid} instantie in de {@link HisBetrokkenheidOuder}
     * instantie gezet en worden zaken uit de context, zoals het tijdstip van registratie en aanvang van de geldigheid,
     * op het historisch bewust instantie gezet.
     *
     * @param betrokkenheid de nieuwe betrokkenheid.
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid Datum waarop de betrokkenheid ingaat.
     * @param datumEindeGeldigheid Datum waarop de betrokkenheid niet meer geldig is.
     */
    private void slaOpNieuwHistorie(final PersistentBetrokkenheid betrokkenheid, final PersistentActie actie,
        final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid)
    {
        HisBetrokkenheidOuder hisBetrokkenheidOuder = new HisBetrokkenheidOuder();
        hisBetrokkenheidOuder.setBetrokkenheid(betrokkenheid);
        hisBetrokkenheidOuder.setIndicatieOuder(betrokkenheid.isIndOuder());

        hisBetrokkenheidOuder.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
        hisBetrokkenheidOuder.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisBetrokkenheidOuder.setDatumEindeGeldigheid(datumEindeGeldigheid);
        hisBetrokkenheidOuder.setActieInhoud(actie);

        em.persist(hisBetrokkenheidOuder);
    }
}
