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

import nl.bzk.brp.pocmotor.dal.operationeel.LandOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.PartijOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.PlaatsOGMRepository;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonAdresID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonAdresIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Gemeente;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonAdres;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_PersoonAdres;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;

/**
 * Standaard JPA implementatie van de {@link PersoonAdresLGMRepositoryCustom} repository voor het ophalen en persisteren van
 * {@link PersoonAdres} instanties. Deze implementatie houdt rekening met historie en de
 * verschillende lagen in de database, waarbij deze implementatie de juiste data in de verschillende lagen toevoegt,
 * wijzigt of verwijderd en dus niet uitgaat van triggers en/of views in de database. Deze implementatie gaat er dus
 * van uit dat bijvoorbeeld de A-laag geen view is, maar een tabel.
 */
public class PersoonAdresLGMRepositoryImpl implements PersoonAdresLGMRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private LandOGMRepository landOGMRepository;
    @Inject
    private PartijOGMRepository partijOGMRepository;
    @Inject
    private PlaatsOGMRepository plaatsOGMRepository;

    /**
     * {@inheritDoc}
     *
     * @param persoonId id van de persoon waarvoor het adres opgehaald dient te worden.
     * @return het huidige adres van de opgegeven persoon.
     */
    @Override
    public PersoonAdres ophalenPersoonAdresVoorPersoon(final Long persoonId) {
        TypedQuery<PersoonAdres> query =
                em.createQuery("SELECT pa FROM PersoonAdresOperationeel pa WHERE pa.persoon.id = :persoonId",
                        PersoonAdres.class);
        query.setParameter("persoonId", persoonId);
        return query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     * Hierbij wordt er rekening gehouden met de twee-dimensionele historie. Zo worden verouderde adresgegevens die
     * door het nieuwe adres worden overschreven naar de D-laag verplaatst en worden eventuele wijzigingen in de
     * C-laag aangebracht. Het nieuwe adres wordt uiteraard zowel in de A-laag als in de C-laag toegevoegd.
     *
     * @param persoonAdres het adres dat opgeslagen dient te worden.
     */
    @Override
    public void opslaanNieuwPersoonAdres(final PersoonAdres persoonAdres,
                                         final Datum datumAanvangGeldigheid,
                                         final Datum datumEindeGeldigheid,
                                         final DatumTijd registratieTijd)
    {
        // Selecteer records uit C-laag met overlap (in de tijd) met het bericht
        List<His_PersoonAdres> overlappendeRecords = selecteerOverlappendeRecords(
                persoonAdres, datumAanvangGeldigheid, datumEindeGeldigheid);
        // De niet geheel overlapte records zullen moeten worden aangepast (ingekort).
        kopieerGrensRecordsEnPasDezeAan(overlappendeRecords,
                                        datumAanvangGeldigheid,
                                        datumEindeGeldigheid,
                                        registratieTijd);
        // Alle overlapte en deels overlapte records zullen moeten worden verplaatst naar de D-laag
        verplaatsOverlapteRecordsNaarDLaag(overlappendeRecords, registratieTijd);
        // Het nieuwe record moet worden toegevoegd
        voegNieuwPersoonAdresToe(persoonAdres, datumAanvangGeldigheid, registratieTijd);
    }

    /**
     * Selecteert (en haalt op) alle {@link His_PersoonAdres} records uit de C-laag (dus nog niet vervallen) die
     * deels of geheel worden overlapt door het opgegeven adres en bijbehorende context en die horen bij de persoon
     * waarvoor het nieuwe adres geldt.
     *
     * @param persoonAdres het nieuwe adres.
     * @param datumAanvangGeldigheid Datum waarop het adres ingaat.
     * @param datumEindeGeldigheid Datum waarop het adres niet meer geldig is.
     * @return een lijst van voor de persoon overlapte adres records uit de C-laag.
     */
    private List<His_PersoonAdres> selecteerOverlappendeRecords(final PersoonAdres persoonAdres,
                                                                final Datum datumAanvangGeldigheid,
                                                                final Datum datumEindeGeldigheid)
    {
        TypedQuery<His_PersoonAdres> query;
        if (datumEindeGeldigheid == null) {
            query = em.createQuery("SELECT hpa FROM His_PersoonAdresOperationeel hpa WHERE (hpa.datumAanvangGeldigheid > "
                    + ":aanvangGeldigheid OR hpa.datumEindeGeldigheid IS NULL OR hpa.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) AND hpa.datumTijdVerval IS NULL AND hpa.persoonAdres.persoon.id = "
                    + ":persoonId", His_PersoonAdres.class);
        } else {
            query = em.createQuery("SELECT hpa FROM His_PersoonAdresOperationeel hpa WHERE ((hpa.datumAanvangGeldigheid > "
                    + ":aanvangGeldigheid AND hpa.datumAanvangGeldigheid < :eindeGeldigheid) OR "
                    + "(hpa.datumEindeGeldigheid < :eindeGeldigheid AND hpa.datumEindeGeldigheid > "
                    + ":aanvangGeldigheid) OR (hpa.datumEindeGeldigheid IS NULL AND hpa.datumAanvangGeldigheid < "
                    + ":eindeGeligheid)) AND hpa.datumTijdVerval IS NULL AND hpa.persoonAdres.persoon.id = "
                    + ":persoonId", His_PersoonAdres.class);
            query.setParameter("eindeGeldigheid", datumEindeGeldigheid);
        }
        query.setParameter("aanvangGeldigheid", datumAanvangGeldigheid);
        query.setParameter("persoonId", persoonAdres.getIdentiteit().getPersoon().getId());
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
    private void kopieerGrensRecordsEnPasDezeAan(final List<His_PersoonAdres> overlapteRecords,
                                                 final Datum datumIngangGeldigheid,
                                                 final Datum datumEindeGeldigheid,
                                                 final DatumTijd datumTijdRegistratie)
    {
        for (His_PersoonAdres adres : overlapteRecords) {
            if (adres.getDatumAanvangGeldigheid().getWaarde() < datumIngangGeldigheid.getWaarde()) {
                His_PersoonAdres nieuwAdresRecord = adres.clone();
                nieuwAdresRecord.setDatumEindeGeldigheid(datumIngangGeldigheid);
                nieuwAdresRecord.setDatumTijdRegistratie(datumTijdRegistratie);
                em.persist(nieuwAdresRecord);
            } else if (datumEindeGeldigheid != null && (adres.getDatumEindeGeldigheid() == null
                    || adres.getDatumEindeGeldigheid().getWaarde() > datumEindeGeldigheid.getWaarde()))
            {
                His_PersoonAdres nieuwAdresRecord = adres.clone();
                nieuwAdresRecord.setDatumAanvangGeldigheid(datumEindeGeldigheid);
                nieuwAdresRecord.setDatumTijdRegistratie(datumTijdRegistratie);
                em.persist(nieuwAdresRecord);
            }
        }
    }

    /**
     * Alle (deels) overlapte records dienen uit de C-laag te worden gehaald en naar de D-laag te worden verplaatst,
     * daar ze niet meer geldig zijn. Hiervoor wordt het record aangepast en wordt het tijdstipvervallen ingevuld.
     *
     * @param overlapteRecords alle overlapte records uit de C-laag.
     * @param tijdstipRegistratie Tijdstip van registratie.
     */
    private void verplaatsOverlapteRecordsNaarDLaag(final List<His_PersoonAdres> overlapteRecords,
                                                    final DatumTijd tijdstipRegistratie)
    {
        for (His_PersoonAdres adres : overlapteRecords) {
            adres.setDatumTijdVerval(tijdstipRegistratie);
            em.persist(adres);
        }
    }

    /**
     * Voor het toevoegen van een nieuw record dient het record uit de A-laag gewoon aangepast te worden naar de nieuwe
     * waardes en dient er een nieuw record aan de C-laag te worden toegevoegd.
     *
     * @param persoonAdres het nieuwe adres.
     * @param datumAanvangGeldigheid Datum wanneer het adres ingaat.
     * @param tijdstipRegistratie Tijdstip van registratie
     */
    private void voegNieuwPersoonAdresToe(final PersoonAdres persoonAdres,
                                          final Datum datumAanvangGeldigheid,
                                          final DatumTijd tijdstipRegistratie) {
        // Er wordt niet echt een adres toegevoegd in de A laag, maar huidige wordt gewijzigd.
        TypedQuery<PersoonAdresID> query =
                em.createQuery("SELECT pa.identiteit.iD FROM PersoonAdres pa WHERE pa.identiteit.persoon = :persoon",
                               PersoonAdresID.class);
        query.setParameter("persoon", persoonAdres.getIdentiteit().getPersoon());
        PersoonAdresID adresId = query.getSingleResult();

        PersoonAdresIdentiteit paid = new PersoonAdresIdentiteit();
        paid.setID(adresId);
        paid.setPersoon(persoonAdres.getIdentiteit().getPersoon());
        persoonAdres.setIdentiteit(paid);
        persoonAdres.getStandaard().setDatumAanvangAdreshouding(datumAanvangGeldigheid);
        em.merge(persoonAdres);

        His_PersoonAdres cLaagAdres = bouwNieuwHistoriePersoonAdres(persoonAdres, datumAanvangGeldigheid, tijdstipRegistratie);
        em.persist(cLaagAdres);
    }

    /**
     * Bouwt een nieuwe {@link His_PersoonAdres} instantie op, op basis van een {@link PersoonAdres}. Hierbij
     * worden alle waardes uit de {@link PersoonAdres} instantie in de {@link His_PersoonAdres} instantie gezet
     * en worden zaken uit de context, zoals het tijdstip van registratie en aanvang van de geldigheid, op het
     * historisch bewust instantie gezet.
     *
     * @param persoonAdres het nieuwe adres.
     * @param datumAanvangGeldigheid Datum waarop het adres ingaat.
     * @param tijdstipRegistratie Tijdstip van registratie.
     * @return de nieuw aangemaakte {@link His_PersoonAdres} instantie.
     */
    private His_PersoonAdres bouwNieuwHistoriePersoonAdres(final PersoonAdres persoonAdres,
                                                           final Datum datumAanvangGeldigheid,
                                                           final DatumTijd tijdstipRegistratie)
    {
        His_PersoonAdres hisAdres = new His_PersoonAdres();
        hisAdres.setPostcode(persoonAdres.getStandaard().getPostcode());
        hisAdres.setHuisnummer(persoonAdres.getStandaard().getHuisnummer());
        hisAdres.setSoort(persoonAdres.getStandaard().getSoort());
        hisAdres.setDatumAanvangAdreshouding(persoonAdres.getStandaard().getDatumAanvangAdreshouding());
        hisAdres.setAfgekorteNaamOpenbareRuimte(persoonAdres.getStandaard().getAfgekorteNaamOpenbareRuimte());
        hisAdres.setNaamOpenbareRuimte(persoonAdres.getStandaard().getNaamOpenbareRuimte());
        hisAdres.setGemeentedeel(persoonAdres.getStandaard().getGemeentedeel());
        hisAdres.setHuisletter(persoonAdres.getStandaard().getHuisletter());
        hisAdres.setHuisnummertoevoeging(persoonAdres.getStandaard().getHuisnummertoevoeging());
        hisAdres.setLocatietovAdres(persoonAdres.getStandaard().getLocatietovAdres());

        //@TODO De rest moet ook gekopieerd worden.....

        //Land is verplicht
        hisAdres.setLand(landOGMRepository
                .findByLandcode(persoonAdres.getStandaard().getLand().getIdentiteit().getLandcode()));
        if (persoonAdres.getStandaard().getGemeente() != null) {
            hisAdres.setGemeente(partijOGMRepository
                    .findByGemeentecode(
                            persoonAdres.getStandaard().getGemeente().getGemeenteStandaard().getGemeentecode()));
        }
        if (persoonAdres.getStandaard().getWoonplaats() != null) {
            hisAdres.setWoonplaats(plaatsOGMRepository.findOne(persoonAdres.getStandaard().getWoonplaats().getId()));
        }
        
        hisAdres.setDatumTijdRegistratie(tijdstipRegistratie);
        hisAdres.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        //Set de terug referentie naar PersoonAdres!
        hisAdres.setPersoonAdres(new nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonAdres());
        hisAdres.getPersoonAdres().setId(persoonAdres.getIdentiteit().getID().getWaarde());
        return hisAdres;
    }
    
}
