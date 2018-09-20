/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersistentPersoonAdres} class en standaard implementatie van de
 * {@link PersoonAdresHistorieRepository} class.
 */
@Repository
public class PersoonAdresHistorieJpaRepository implements PersoonAdresHistorieRepository {

    private static final Logger      LOGGER = LoggerFactory.getLogger(PersoonAdresHistorieJpaRepository.class);

    @PersistenceContext
    private EntityManager            em;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Override
    public void opslaanHistorie(final PersistentPersoonAdres persoonAdres, final Integer datumAanvangGeldigheid,
            final Integer datumEindeGeldigheid, final Date registratieTijd)
    {
        // Selecteer records uit C-laag met overlap (in de tijd) voor het opgegeven nieuwe adres en bijbehorende
        // geldigheid.
        List<HisPersoonAdres> overlappendeRecords =
            selecteerOverlappendeRecords(persoonAdres, datumAanvangGeldigheid, datumEindeGeldigheid);

        // De niet geheel overlapte records zullen moeten worden aangepast (ingekort).
        kopieerGrensRecordsEnPasDezeAan(overlappendeRecords, datumAanvangGeldigheid, datumEindeGeldigheid,
                registratieTijd);

        // Alle overlapte en deels overlapte records zullen moeten worden verplaatst naar de D-laag
        verplaatsOverlapteRecordsNaarDLaag(overlappendeRecords, registratieTijd);

        // Nieuwe huidige historie moet opgeslagen worden.
        slaOpNieuwHistoriePersoonAdres(persoonAdres, datumAanvangGeldigheid, datumEindeGeldigheid, registratieTijd);
    }

    /**
     * Selecteert (en haalt op) alle {@link HisPersoonAdres} records uit de C-laag (dus nog niet vervallen) die
     * deels of geheel worden overlapt door het opgegeven adres en bijbehorende context en die horen bij de persoon
     * waarvoor het nieuwe adres geldt. Indien er geen dergelijke records aanwezig zijn, zal een lege lijst worden
     * geretourneerd.
     *
     * @param persoonAdres het nieuwe adres.
     * @param datumAanvangGeldigheid Datum waarop het adres ingaat.
     * @param datumEindeGeldigheid Datum waarop het adres niet meer geldig is.
     * @return een lijst van voor de persoon overlapte adres records uit de C-laag.
     */
    private List<HisPersoonAdres> selecteerOverlappendeRecords(final PersistentPersoonAdres persoonAdres,
            final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid)
    {
        TypedQuery<HisPersoonAdres> query;
        if (datumEindeGeldigheid == null) {
            query =
                em.createQuery("SELECT hpa FROM HisPersoonAdres hpa WHERE (hpa.datumAanvangGeldigheid > "
                    + ":aanvangGeldigheid OR hpa.datumEindeGeldigheid IS NULL OR hpa.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) AND hpa.datumTijdVerval IS NULL AND "
                    + "hpa.persoonAdres.persoon.burgerservicenummer = "
                    + ":burgerservicenummer", HisPersoonAdres.class);
        } else {
            query =
                em.createQuery("SELECT hpa FROM HisPersoonAdres hpa WHERE ((hpa.datumAanvangGeldigheid >= "
                    + ":aanvangGeldigheid AND hpa.datumAanvangGeldigheid < :eindeGeldigheid) OR "
                    + "(hpa.datumEindeGeldigheid <= :eindeGeldigheid AND hpa.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) OR (hpa.datumEindeGeldigheid IS NULL AND hpa.datumAanvangGeldigheid < "
                    + ":eindeGeldigheid) OR (hpa.datumAanvangGeldigheid <= :aanvangGeldigheid AND "
                    + "hpa.datumEindeGeldigheid >= :eindeGeldigheid)) AND hpa.datumTijdVerval IS NULL AND "
                    + "hpa.persoonAdres.persoon.burgerservicenummer = :burgerservicenummer", HisPersoonAdres.class);
            query.setParameter("eindeGeldigheid", datumEindeGeldigheid);
        }
        query.setParameter("aanvangGeldigheid", datumAanvangGeldigheid);
        query.setParameter("burgerservicenummer", persoonAdres.getPersoon().getBurgerservicenummer());
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
     * @param datumEindeGeldigheid Datum waarop het huidige adres vervalt.
     * @param datumTijdRegistratie Tijdstip van registratie.
     */
    private void kopieerGrensRecordsEnPasDezeAan(final List<HisPersoonAdres> overlapteRecords,
            final Integer datumIngangGeldigheid, final Integer datumEindeGeldigheid, final Date datumTijdRegistratie)
    {
        try {
            for (HisPersoonAdres adres : overlapteRecords) {
                if (adres.getDatumAanvangGeldigheid() < datumIngangGeldigheid) {
                    // Controlleer huidige adres AanvangGeldigheid of die valt voor de nieuwe adres IngangGeldigheid en
                    // kort
                    // in de huidige adres eindeGeldigheid

                    HisPersoonAdres nieuwAdresRecord = adres.clone();
                    nieuwAdresRecord.setDatumEindeGeldigheid(datumIngangGeldigheid);
                    nieuwAdresRecord.setDatumTijdRegistratie(datumTijdRegistratie);
                    em.persist(nieuwAdresRecord);
                }
                if (datumEindeGeldigheid != null
                    && (adres.getDatumEindeGeldigheid() == null
                    || adres.getDatumEindeGeldigheid() > datumEindeGeldigheid))
                {
                    HisPersoonAdres nieuwAdresRecord = adres.clone();
                    nieuwAdresRecord.setDatumAanvangGeldigheid(datumEindeGeldigheid);
                    nieuwAdresRecord.setDatumTijdRegistratie(datumTijdRegistratie);
                    em.persist(nieuwAdresRecord);
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
    private void verplaatsOverlapteRecordsNaarDLaag(final List<HisPersoonAdres> overlapteRecords,
            final Date tijdstipRegistratie)
    {
        for (HisPersoonAdres adres : overlapteRecords) {
            adres.setDatumTijdVerval(tijdstipRegistratie);
            em.persist(adres);
        }
    }

    /**
     * Bouwt een nieuwe {@link HisPersoonAdres} instantie op, op basis van een {@link PersistentPersoonAdres}. Hierbij
     * worden alle waardes uit de {@link PersistentPersoonAdres} instantie in de {@link HisPersoonAdres} instantie gezet
     * en worden zaken uit de context, zoals het tijdstip van registratie en aanvang van de geldigheid, op het
     * historisch bewust instantie gezet.
     *
     * @param persoonAdres het nieuwe adres.
     * @param datumAanvangGeldigheid Datum waarop het adres ingaat.
     * @param datumEindeGeldigheid Datum waarop het adres niet meer geldig is.
     * @param tijdstipRegistratie Tijdstip van registratie.
     */
    private void slaOpNieuwHistoriePersoonAdres(final PersistentPersoonAdres persoonAdres,
            final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid, final Date tijdstipRegistratie)
    {
        HisPersoonAdres hisPersoonAdres = new HisPersoonAdres();
        hisPersoonAdres.setSoort(persoonAdres.getSoort());
        // hisPersoonAdres.setRedenWijziging();
        // hisPersoonAdres.setAangeverAdresHouding();
        hisPersoonAdres.setPersoonAdres(persoonAdres);
        hisPersoonAdres.setLand(persoonAdres.getLand());
        hisPersoonAdres.setGemeente(persoonAdres.getGemeente());
        hisPersoonAdres.setDatumAanvangAdreshouding(persoonAdres.getDatumAanvangAdreshouding());
        hisPersoonAdres.setNaamOpenbareRuimte(persoonAdres.getNaamOpenbareRuimte());
        hisPersoonAdres.setAfgekorteNaamOpenbareRuimte(persoonAdres.getAfgekorteNaamOpenbareRuimte());
        hisPersoonAdres.setHuisnummer(persoonAdres.getHuisnummer());
        hisPersoonAdres.setHuisletter(persoonAdres.getHuisletter());
        hisPersoonAdres.setHuisnummertoevoeging(persoonAdres.getHuisnummertoevoeging());
        hisPersoonAdres.setPostcode(persoonAdres.getPostcode());
        if (persoonAdres.getWoonplaats() != null) {
            hisPersoonAdres.setWoonplaats(referentieDataRepository.findWoonplaatsOpCode(persoonAdres.getWoonplaats()
                    .getWoonplaatscode()));
        }
        hisPersoonAdres.setAdresseerbaarObject(persoonAdres.getAdresseerbaarObject());
        hisPersoonAdres.setIdentificatiecodeNummeraanduiding(persoonAdres.getIdentificatiecodeNummeraanduiding());

        hisPersoonAdres.setDatumTijdRegistratie(tijdstipRegistratie);
        hisPersoonAdres.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonAdres.setDatumEindeGeldigheid(datumEindeGeldigheid);

        em.persist(hisPersoonAdres);
    }
}
