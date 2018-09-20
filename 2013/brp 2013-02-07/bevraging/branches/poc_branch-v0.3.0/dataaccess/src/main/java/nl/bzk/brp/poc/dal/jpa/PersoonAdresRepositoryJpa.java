/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.dal.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.poc.dal.PersoonAdresRepository;
import nl.bzk.brp.poc.domein.BijhoudingContext;
import nl.bzk.brp.poc.domein.HistoriePersoonAdres;
import nl.bzk.brp.poc.domein.PocPersoonAdres;
import org.springframework.stereotype.Repository;

/**
 * Standaard JPA implementatie van de {@link PersoonAdresRepository} repository voor het ophalen en persisteren van
 * {@link nl.bzk.brp.poc.domein.PocPersoonAdres} instanties. Deze implementatie houdt rekening met historie en de
 * verschillende lagen in de database, waarbij deze implementatie de juiste data in de verschillende lagen toevoegt,
 * wijzigt of verwijderd en dus niet uitgaat van triggers en/of views in de database. Deze implementatie gaat er dus
 * van uit dat bijvoorbeeld de A-laag geen view is, maar een tabel.
 */
@Repository
public class PersoonAdresRepositoryJpa implements PersoonAdresRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     *
     * @param persoonId id van de persoon waarvoor het adres opgehaald dient te worden.
     * @return het huidige adres van de opgegeven persoon.
     */
    @Override
    public PocPersoonAdres ophalenPersoonAdresVoorPersoon(final Long persoonId) {
        TypedQuery<PocPersoonAdres> query =
                em.createQuery("SELECT pa FROM PocPersoonAdres pa WHERE pa.persoon.id = :persoonId",
                        PocPersoonAdres.class);
        query.setParameter("persoonId", persoonId);
        return query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     * Hierbij wordt er rekening gehouden met de twee-dimensionele historie. Zo worden verouderde adresgegevens die
     * door het nieuwe adres worden overschreven naar de D-laag verplaatst en worden eventuele wijzigingen in de
     * C-laag aangebracht. Het nieuwe adres wordt uiteraard zowel in de A-laag als in de C-laag toegevoegd.
     *
     * @param context de context van uitvoering.
     * @param pocPersoonAdres het adres dat opgeslagen dient te worden.
     */
    @Override
    public void opslaanNieuwPersoonAdres(final BijhoudingContext context, final PocPersoonAdres pocPersoonAdres) {
        // Selecteer records uit C-laag met overlap (in de tijd) met het bericht
        List<HistoriePersoonAdres> overlappendeRecords = selecteerOverlappendeRecords(context, pocPersoonAdres);
        // De niet geheel overlapte records zullen moeten worden aangepast (ingekort).
        kopieerGrensRecordsEnPasDezeAan(context, overlappendeRecords);
        // Alle overlapte en deels overlapte records zullen moeten worden verplaatst naar de D-laag
        verplaatsOverlapteRecordsNaarDLaag(context, overlappendeRecords);
        // Het nieuwe record moet worden toegevoegd
        voegNieuwPersoonAdresToe(context, pocPersoonAdres);
    }

    /**
     * Selecteert (en haalt op) alle {@link HistoriePersoonAdres} records uit de C-laag (dus nog niet vervallen) die
     * deels of geheel worden overlapt door het opgegeven adres en bijbehorende context en die horen bij de persoon
     * waarvoor het nieuwe adres geldt.
     *
     * @param context de context bepaald de start en eventuele einddatum van het nieuwe adres.
     * @param pocPersoonAdres het nieuwe adres.
     * @return een lijst van voor de persoon overlapte adres records uit de C-laag.
     */
    private List<HistoriePersoonAdres> selecteerOverlappendeRecords(final BijhoudingContext context,
            final PocPersoonAdres pocPersoonAdres)
    {
        TypedQuery<HistoriePersoonAdres> query;
        if (context.getDatumEindeGeldigheid() == null) {
            query = em.createQuery("SELECT hpa FROM HistoriePersoonAdres hpa WHERE (hpa.datumAanvangGeldigheid > "
                    + ":aanvangGeldigheid OR hpa.datumEindeGeldigheid IS NULL OR hpa.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) AND hpa.tijdstipVervallen IS NULL AND hpa.persoonAdres.persoon.id = "
                    + ":persoonId", HistoriePersoonAdres.class);
        } else {
            query = em.createQuery("SELECT hpa FROM HistoriePersoonAdres hpa WHERE ((hpa.datumAanvangGeldigheid > "
                    + ":aanvangGeldigheid AND hpa.datumAanvangGeldigheid < :eindeGeldigheid) OR "
                    + "(hpa.datumEindeGeldigheid < :eindeGeldigheid AND hpa.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) OR (hpa.datumEindeGeldigheid IS NULL AND hpa.datumAanvangGeldigheid < "
                    + ":eindeGeligheid)) AND hpa.tijdstipVervallen IS NULL AND hpa.persoonAdres.persoon.id = "
                    + ":persoonId", HistoriePersoonAdres.class);
            query.setParameter("eindeGeldigheid", context.getDatumEindeGeldigheid());
        }
        query.setParameter("aanvangGeldigheid", context.getDatumIngangGeldigheid());
        query.setParameter("persoonId", pocPersoonAdres.getPersoon().getId());
        return query.getResultList();
    }

    /**
     * Records die slechts deels worden overlapt, dus die aanvangen reeds voor de aanvang uit de context of nog
     * doorlopen tot na het einde uit de context, zullen moeten worden ingekort. Hiervoor worden nieuwe records aan de
     * C-laag toegevoegd, welke een kopie zijn van de deels overlapte records, maar waarvan de aanvangdatum of
     * einddatum is aangepast, conform de context.
     *
     * @param context de context bepaald de start en eventuele einddatum van het nieuwe adres.
     * @param overlapteRecords alle overlapte records uit de C-laag.
     */
    private void kopieerGrensRecordsEnPasDezeAan(final BijhoudingContext context,
            final List<HistoriePersoonAdres> overlapteRecords)
    {
        for (HistoriePersoonAdres adres : overlapteRecords) {
            if (adres.getDatumAanvangGeldigheid() < context.getDatumIngangGeldigheid()) {
                HistoriePersoonAdres nieuwAdresRecord = adres.clone();
                nieuwAdresRecord.setDatumEindeGeldigheid(context.getDatumIngangGeldigheid());
                nieuwAdresRecord.setTijdstipRegistratie(context.getTijdstipRegistratie());
                em.persist(nieuwAdresRecord);
            } else if (context.getDatumEindeGeldigheid() != null && (adres.getDatumEindeGeldigheid() == null
                    || adres.getDatumEindeGeldigheid() > context.getDatumEindeGeldigheid()))
            {
                HistoriePersoonAdres nieuwAdresRecord = adres.clone();
                nieuwAdresRecord.setDatumAanvangGeldigheid(context.getDatumEindeGeldigheid());
                nieuwAdresRecord.setTijdstipRegistratie(context.getTijdstipRegistratie());
                em.persist(nieuwAdresRecord);
            }
        }
    }

    /**
     * Alle (deels) overlapte records dienen uit de C-laag te worden gehaald en naar de D-laag te worden verplaatst,
     * daar ze niet meer geldig zijn. Hiervoor wordt het record aangepast en wordt het tijdstipvervallen ingevuld.
     *
     * @param context de context bepaald de start en eventuele einddatum van het nieuwe adres.
     * @param overlapteRecords alle overlapte records uit de C-laag.
     */
    private void verplaatsOverlapteRecordsNaarDLaag(final BijhoudingContext context,
            final List<HistoriePersoonAdres> overlapteRecords)
    {
        for (HistoriePersoonAdres adres : overlapteRecords) {
            adres.setTijdstipVervallen(context.getTijdstipRegistratie());
            em.persist(adres);
        }
    }

    /**
     * Voor het toevoegen van een nieuw record dient het record uit de A-laag gewoon aangepast te worden naar de nieuwe
     * waardes en dient er een nieuw record aan de C-laag te worden toegevoegd.
     *
     * @param context de context bepaald de start en eventuele einddatum van het nieuwe adres.
     * @param pocPersoonAdres het nieuwe adres.
     */
    private void voegNieuwPersoonAdresToe(final BijhoudingContext context, final PocPersoonAdres pocPersoonAdres) {
        // Er wordt niet echt een adres toegevoegd in de A laag, maar huidige wordt gewijzigd.
        TypedQuery<Long> query =
                em.createQuery("SELECT pa.id FROM PocPersoonAdres pa WHERE pa.persoon.id = :persoonId", Long.class);
        query.setParameter("persoonId", pocPersoonAdres.getPersoon().getId());
        Long id = query.getSingleResult();

        pocPersoonAdres.setId(id);
        pocPersoonAdres.setDatumAanvangAdresHouding(context.getDatumIngangGeldigheid());
        em.merge(pocPersoonAdres);

        HistoriePersoonAdres cLaagAdres = bouwNieuwHistoriePersoonAdres(context, pocPersoonAdres);
        em.persist(cLaagAdres);
    }

    /**
     * Bouwt een nieuwe {@link HistoriePersoonAdres} instantie op, op basis van een {@link PocPersoonAdres}. Hierbij
     * worden alle waardes uit de {@link PocPersoonAdres} instantie in de {@link HistoriePersoonAdres} instantie gezet
     * en worden zaken uit de context, zoals het tijdstip van registratie en aanvang van de geldigheid, op het
     * historisch bewust instantie gezet.
     *
     * @param context de context bepaald de start en eventuele einddatum van het nieuwe adres.
     * @param pocPersoonAdres het nieuwe adres.
     * @return de nieuw aangemaakte {@link HistoriePersoonAdres} instantie.
     */
    private HistoriePersoonAdres bouwNieuwHistoriePersoonAdres(final BijhoudingContext context,
            final PocPersoonAdres pocPersoonAdres)
    {
        HistoriePersoonAdres historiePersoonAdres = new HistoriePersoonAdres(pocPersoonAdres);

        historiePersoonAdres.setTijdstipRegistratie(context.getTijdstipRegistratie());
        historiePersoonAdres.setDatumAanvangGeldigheid(context.getDatumIngangGeldigheid());
        historiePersoonAdres.setPostcode(pocPersoonAdres.getPostcode());
        return historiePersoonAdres;
    }

}
