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

import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresMdlHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersoonAdresModel} class en standaard implementatie van de
 * {@link PersoonAdresMdlHistorieRepository} class.
 */
@Repository
public class PersoonAdresHistorieMdlJpaRepository implements PersoonAdresMdlHistorieRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persisteerHistorie(final PersoonAdresModel persoonAdres, final ActieModel actie,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        // Selecteer records uit C-laag met overlap (in de tijd) voor het opgegeven nieuwe adres en bijbehorende
        // geldigheid.
        List<PersoonAdresHisModel> overlappendeRecords =
            selecteerOverlappendeRecords(persoonAdres, datumAanvangGeldigheid, datumEindeGeldigheid);

        // De niet geheel overlapte records zullen moeten worden aangepast (ingekort).
        kopieerGrensRecordsEnPasDezeAan(actie, overlappendeRecords, datumAanvangGeldigheid, datumEindeGeldigheid);

        // Alle overlapte en deels overlapte records zullen moeten worden verplaatst naar de D-laag
        verplaatsOverlapteRecordsNaarDLaag(actie, overlappendeRecords);

        // Nieuwe huidige historie moet opgeslagen worden.
        slaOpNieuwHistoriePersoonAdres(actie, persoonAdres, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

    /**
     * Selecteert (en haalt op) alle {@link PersoonAdresHisModel} records uit de C-laag (dus nog niet vervallen) die
     * deels of geheel worden overlapt door het opgegeven adres en bijbehorende context en die horen bij de persoon
     * waarvoor het nieuwe adres geldt. Indien er geen dergelijke records aanwezig zijn, zal een lege lijst worden
     * geretourneerd.
     *
     * @param persoonAdres het nieuwe adres.
     * @param datumAanvangGeldigheid Datum waarop het adres ingaat.
     * @param datumEindeGeldigheid Datum waarop het adres niet meer geldig is.
     * @return een lijst van voor de persoon overlapte adres records uit de C-laag.
     */
    private List<PersoonAdresHisModel> selecteerOverlappendeRecords(final PersoonAdresModel persoonAdres,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        TypedQuery<PersoonAdresHisModel> query;
        if (datumEindeGeldigheid == null) {
            query = em.createQuery("SELECT hpa FROM PersoonAdresHisModel hpa "
                + "WHERE (hpa.historie.datumAanvangGeldigheid.waarde > :aanvangGeldigheid "
                + "       OR hpa.historie.datumEindeGeldigheid.waarde IS NULL "
                + "       OR hpa.historie.datumEindeGeldigheid.waarde > :aanvangGeldigheid) "
                + "  AND hpa.historie.datumTijdVerval.waarde IS NULL "
                + "  AND hpa.persoonAdres.persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer",
                PersoonAdresHisModel.class);
        } else {
            query = em.createQuery("SELECT hpa FROM PersoonAdresHisModel hpa "
                + "WHERE (   (hpa.historie.datumAanvangGeldigheid.waarde >= :aanvangGeldigheid "
                + "           AND hpa.historie.datumAanvangGeldigheid.waarde < :eindeGeldigheid) "
                + "       OR (hpa.historie.datumEindeGeldigheid.waarde <= :eindeGeldigheid "
                + "           AND hpa.historie.datumEindeGeldigheid.waarde > :aanvangGeldigheid) "
                + "       OR (hpa.historie.datumEindeGeldigheid.waarde IS NULL "
                + "           AND hpa.historie.datumAanvangGeldigheid.waarde < :eindeGeldigheid) "
                + "       OR (hpa.historie.datumAanvangGeldigheid.waarde <= :aanvangGeldigheid "
                + "           AND hpa.historie.datumEindeGeldigheid.waarde >= :eindeGeldigheid)) "
                + "  AND hpa.historie.datumTijdVerval IS NULL "
                + "  AND hpa.persoonAdres.persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer",
                PersoonAdresHisModel.class);
            query.setParameter("eindeGeldigheid", datumEindeGeldigheid.getWaarde());
        }
        query.setParameter("aanvangGeldigheid", datumAanvangGeldigheid.getWaarde());
        query.setParameter("burgerservicenummer", persoonAdres.getPersoon().getIdentificatieNummers()
                .getBurgerServiceNummer());
        return query.getResultList();
    }

    /**
     * Records die slechts deels worden overlapt, dus die aanvangen reeds voor de aanvang uit de context of nog
     * doorlopen tot na het einde uit de context, zullen moeten worden ingekort. Hiervoor worden nieuwe records aan de
     * C-laag toegevoegd, welke een kopie zijn van de deels overlapte records, maar waarvan de aanvangdatum of
     * einddatum is aangepast, conform de context.
     *
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     * @param overlapteRecords alle overlapte records uit de C-laag.
     * @param datumIngangGeldigheid Datum waarop het adres ingaat
     * @param datumEindeGeldigheid Datum waarop het huidige adres vervalt.
     */
    private void kopieerGrensRecordsEnPasDezeAan(final ActieModel actie,
            final List<PersoonAdresHisModel> overlapteRecords, final Datum datumIngangGeldigheid,
            final Datum datumEindeGeldigheid)
    {
        for (PersoonAdresHisModel adres : overlapteRecords) {
            if (adres.getDatumAanvangGeldigheid().getWaarde() < datumIngangGeldigheid.getWaarde()) {
                // Controlleer huidige adres AanvangGeldigheid of die valt voor de nieuwe adres IngangGeldigheid en
                // kort
                // in de huidige adres eindeGeldigheid
                PersoonAdresHisModel nieuwAdresRecord = new PersoonAdresHisModel(adres, adres.getPersoonAdres());

                nieuwAdresRecord.setDatumEindeGeldigheid(datumIngangGeldigheid);
                nieuwAdresRecord.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
                nieuwAdresRecord.setActieAanpassingGeldigheid(actie);

                em.persist(nieuwAdresRecord);
            }
            if (datumEindeGeldigheid != null && (adres.getDatumEindeGeldigheid() == null
                || adres.getDatumEindeGeldigheid().getWaarde() > datumEindeGeldigheid.getWaarde()))
            {
                PersoonAdresHisModel nieuwAdresRecord = new PersoonAdresHisModel(adres, adres.getPersoonAdres());

                nieuwAdresRecord.setDatumAanvangGeldigheid(datumEindeGeldigheid);
                nieuwAdresRecord.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
                nieuwAdresRecord.setActieAanpassingGeldigheid(actie);
                em.persist(nieuwAdresRecord);
            }
        }
    }

    /**
     * Alle (deels) overlapte records dienen uit de C-laag te worden gehaald en naar de D-laag te worden verplaatst,
     * daar ze niet meer geldig zijn. Hiervoor wordt het record aangepast en wordt het tijdstipvervallen ingevuld.
     *
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     * @param overlapteRecords alle overlapte records uit de C-laag.
     */
    private void verplaatsOverlapteRecordsNaarDLaag(final ActieModel actie,
            final List<PersoonAdresHisModel> overlapteRecords)
    {
        for (PersoonAdresHisModel adres : overlapteRecords) {
            adres.setDatumTijdVerval(actie.getTijdstipRegistratie());
            adres.setActieVerval(actie);
            em.persist(adres);
        }
    }

    /**
     * Bouwt een nieuwe {@link PersoonAdresHisModel} instantie op, op basis van een {@link PersoonAdresModel}. Hierbij
     * worden alle waardes uit de {@link PersoonAdresModel} instantie in de {@link PersoonAdresHisModel} instantie
     * gezet en worden zaken uit de context, zoals het tijdstip van registratie en aanvang van de geldigheid, op het
     * historisch bewust instantie gezet.
     *
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     * @param persoonAdres het nieuwe adres.
     * @param datumAanvangGeldigheid Datum waarop het adres ingaat.
     * @param datumEindeGeldigheid Datum waarop het adres niet meer geldig is.
     */
    private void slaOpNieuwHistoriePersoonAdres(final ActieModel actie, final PersoonAdresModel persoonAdres,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        PersoonAdresHisModel hisPersoonAdres = new PersoonAdresHisModel(persoonAdres.getGegevens(), persoonAdres);
        hisPersoonAdres.setDatumTijdRegistratie(actie.getTijdstipRegistratie());
        hisPersoonAdres.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonAdres.setDatumEindeGeldigheid(datumEindeGeldigheid);
        hisPersoonAdres.setActieInhoud(actie);

        em.persist(hisPersoonAdres);
    }
}
