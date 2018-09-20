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

import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersoonAdres} class en standaard implementatie van de {@link PersoonAdresRepository}
 * class.
 */
@Repository
public class PersoonAdresJpaRepository implements PersoonAdresRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonAdresJpaRepository.class);

    @PersistenceContext
    private EntityManager em;

    @Inject
    private ReferentieDataRepository referentieDataRepository;
    @Inject
    private PersoonRepository        persoonRepository;

    /** {@inheritDoc} */
    @Override
    public void opslaanNieuwPersoonAdres(final PersoonAdres persoonAdres, final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid)
    {
        final Date registratieTijd = new Date();

        // Selecteer records uit C-laag met overlap (in de tijd) voor het opgegeven nieuwe adres en bijbehorende
        // geldigheid.
        List<HisPersoonAdres> overlappendeRecords =
            selecteerOverlappendeRecords(persoonAdres, datumAanvangGeldigheid, datumEindeGeldigheid);

        // De niet geheel overlapte records zullen moeten worden aangepast (ingekort).
        kopieerGrensRecordsEnPasDezeAan(overlappendeRecords, datumAanvangGeldigheid, datumEindeGeldigheid,
            registratieTijd);

        // Alle overlapte en deels overlapte records zullen moeten worden verplaatst naar de D-laag
        verplaatsOverlapteRecordsNaarDLaag(overlappendeRecords, registratieTijd);

        // Het nieuwe record moet worden toegevoegd
        voegNieuwPersoonAdresToe(persoonAdres, datumAanvangGeldigheid, datumEindeGeldigheid, registratieTijd);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isIemandIngeschrevenOpAdres(final PersoonAdres persoonAdres) {
        final StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT adres FROM PersistentPersoonAdres adres ")
                .append("WHERE adres.gemeente.gemeentecode = :gemCode ").append("AND adres.postcode = :postcode ")
                .append("AND adres.woonplaats.woonplaatscode = :wplCode ");

        if (persoonAdres.getHuisnummer() != null) {
            sqlQuery.append("AND adres.huisnummer = :huisnr ");
        }
        if (persoonAdres.getHuisletter() != null) {
            sqlQuery.append("AND adres.huisletter = :huisletter ");
        }
        if (persoonAdres.getHuisnummertoevoeging() != null) {
            sqlQuery.append("AND adres.huisnummertoevoeging = :huisnrToevoeging ");
        }

        TypedQuery<PersistentPersoonAdres> tQuery = em.createQuery(sqlQuery.toString(), PersistentPersoonAdres.class);
        tQuery.setParameter("gemCode", persoonAdres.getGemeente().getGemeentecode());
        tQuery.setParameter("postcode", persoonAdres.getPostcode());
        tQuery.setParameter("wplCode", persoonAdres.getWoonplaats().getWoonplaatscode());

        if (persoonAdres.getHuisnummer() != null) {
            tQuery.setParameter("huisnr", persoonAdres.getHuisnummer());
        }
        if (persoonAdres.getHuisletter() != null) {
            tQuery.setParameter("huisletter", persoonAdres.getHuisletter());
        }
        if (persoonAdres.getHuisnummertoevoeging() != null) {
            tQuery.setParameter("huisnrToevoeging", persoonAdres.getHuisnummertoevoeging());
        }
        // Optimaliseer
        tQuery.setMaxResults(1);

        return !tQuery.getResultList().isEmpty();
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
    private List<HisPersoonAdres> selecteerOverlappendeRecords(final PersoonAdres persoonAdres,
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
        query.setParameter("burgerservicenummer", persoonAdres.getPersoon().getIdentificatienummers()
                                                              .getBurgerservicenummer());
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
                } else if (datumEindeGeldigheid != null
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
     * Voor het toevoegen van een nieuw record dient het record uit de A-laag gewoon aangepast te worden naar de nieuwe
     * waardes (indien het een nieuw adres is) en dient er een nieuw record aan de C-laag te worden toegevoegd.
     *
     * @param persoonAdres het nieuwe adres.
     * @param datumAanvangGeldigheid Datum wanneer het adres ingaat.
     * @param datumEindeGeldigheid Datum wanneer het adres niet meer geldig is.
     * @param tijdstipRegistratie Tijdstip van registratie
     */
    private void voegNieuwPersoonAdresToe(final PersoonAdres persoonAdres, final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid, final Date tijdstipRegistratie)
    {
        // Er wordt niet echt een adres toegevoegd in de A laag, maar huidige wordt gewijzigd.
        PersistentPersoonAdres huidigAdres =
            haalHuidigAdresOp(persoonAdres.getPersoon().getIdentificatienummers().getBurgerservicenummer());

        if (datumEindeGeldigheid == null) {
            huidigAdres.setSoort(persoonAdres.getSoort());
            // TODO: BLiem: set de RedenWijzingAdres, new Entity moet nog gemaakt worden.
            // huidigAdres.setxxxxxx(persoonAdres.getRedenWijziging());

            // TODO: BLiem: set de AangeverAdreshouding, new Entity moet nog gemaakt worden.
            // huidigAdres.setxxxxx(persoonAdres.getAangeverAdreshouding());
            huidigAdres.setGemeente(referentieDataRepository.vindGemeenteOpCode(persoonAdres.getGemeente()
                                                                                            .getGemeentecode()));
            huidigAdres.setLand(referentieDataRepository.vindLandOpCode(persoonAdres.getLand().getLandcode()));

            huidigAdres.setDatumAanvangAdreshouding(persoonAdres.getDatumAanvangAdreshouding());
            huidigAdres.setNaamOpenbareRuimte(persoonAdres.getNaamOpenbareRuimte());
            huidigAdres.setAfgekorteNaamOpenbareRuimte(persoonAdres.getAfgekorteNaamOpenbareRuimte());
            huidigAdres.setHuisnummer(persoonAdres.getHuisnummer());
            huidigAdres.setHuisletter(persoonAdres.getHuisletter());
            huidigAdres.setHuisnummertoevoeging(persoonAdres.getHuisnummertoevoeging());
            huidigAdres.setWoonplaats(referentieDataRepository.findWoonplaatsOpCode(persoonAdres.getWoonplaats()
                                                                                                .getWoonplaatscode()));
            huidigAdres.setPostcode(persoonAdres.getPostcode());
            huidigAdres.setAdresseerbaarObject(persoonAdres.getAdresseerbaarObject());
            huidigAdres.setIdentificatiecodeNummeraanduiding(persoonAdres.getIdentificatiecodeNummeraanduiding());

            em.merge(huidigAdres);
        }

        HisPersoonAdres cLaagAdres =
            bouwNieuwHistoriePersoonAdres(huidigAdres, datumAanvangGeldigheid, datumEindeGeldigheid,
                tijdstipRegistratie);

        em.persist(cLaagAdres);
    }

    /**
     * Haalt het huidige adres op voor de opgegeven bsn. Indien er meerdere adressen worden gevonden, wordt een
     * exceptie gegooid. Het kan overigens ook zijn dat er geen huidig adres wordt gevonden, dan wordt er een nieuw
     * adres aangemaakt (conform operationeel model) en deze wordt dan geretourneerd.
     *
     * @param bsn het burgerservicenummer waarvoor het huidige adres moet worden opgehaald.
     * @return het huidige adres, of een leeg adres indien er geen adres aanwezig is.
     */
    private PersistentPersoonAdres haalHuidigAdresOp(final String bsn) {
        final String sql =
            "SELECT adres FROM PersistentPersoonAdres adres WHERE adres.persoon.burgerservicenummer = :bsn";

        List<PersistentPersoonAdres> adressen = em.createQuery(sql).setParameter("bsn", bsn).getResultList();

        PersistentPersoonAdres resultaat;
        if (adressen.size() == 0) {
            PersistentPersoon persoon = persoonRepository.findByBurgerservicenummer(bsn);

            PersistentPersoonAdres adres = new PersistentPersoonAdres();
            adres.setPersoon(persoon);
            em.persist(adres);

            resultaat = adres;
        } else {
            // TODO Er dient een exceptie gegooid te worden indien er meer dan 1 adres wordt gevonden.
            resultaat = adressen.get(0);
        }
        return resultaat;
    }

    /**
     * Bouwt een nieuwe {@link HisPersoonAdres} instantie op, op basis van een {@link PersoonAdres}. Hierbij
     * worden alle waardes uit de {@link PersoonAdres} instantie in de {@link HisPersoonAdres} instantie gezet
     * en worden zaken uit de context, zoals het tijdstip van registratie en aanvang van de geldigheid, op het
     * historisch bewust instantie gezet.
     *
     * @param persoonAdres het nieuwe adres.
     * @param datumAanvangGeldigheid Datum waarop het adres ingaat.
     * @param datumEindeGeldigheid Datum waarop het adres niet meer geldig is.
     * @param tijdstipRegistratie Tijdstip van registratie.
     * @return de nieuw aangemaakte {@link HisPersoonAdres} instantie.
     */
    private HisPersoonAdres bouwNieuwHistoriePersoonAdres(final PersistentPersoonAdres persoonAdres,
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
        hisPersoonAdres.setWoonplaats(referentieDataRepository.findWoonplaatsOpCode(persoonAdres.getWoonplaats()
                                                                                                .getWoonplaatscode()));
        hisPersoonAdres.setAdresseerbaarObject(persoonAdres.getAdresseerbaarObject());
        hisPersoonAdres.setIdentificatiecodeNummeraanduiding(persoonAdres.getIdentificatiecodeNummeraanduiding());

        hisPersoonAdres.setDatumTijdRegistratie(tijdstipRegistratie);
        hisPersoonAdres.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonAdres.setDatumEindeGeldigheid(datumEindeGeldigheid);

        return hisPersoonAdres;
    }
}
