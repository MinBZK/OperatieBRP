/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.historie.GroepFormeleHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.GroepHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonOpschortingHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.basis.AttribuutType;
import nl.bzk.brp.model.groep.logisch.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOpschortingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonBijhoudingsverantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonIdentificatienummersGroep;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAanschrijvingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonBijhoudingsgemeenteHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonBijhoudingsverantwoordelijkheidHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsaanduidingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsnaamcomponentHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonIdentificatienummersHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonNationaliteitHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonSamengesteldeNaamHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonVoornaamHisModel;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link nl.bzk.brp.model.objecttype.operationeel.PersoonModel} class en standaard implementatie
 * van de {@link nl.bzk.brp.dataaccess.repository.PersoonRepository} class.
 */
@Repository
public class PersoonJpaRepository implements PersoonRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonJpaRepository.class);

    private static final String SELECT_PERSOON      = " SELECT distinct persoon FROM PersoonModel persoon ";
    private static final String JOIN_ADRES          = " LEFT JOIN FETCH persoon.adressen as adres ";
    private static final String JOIN_INDICATIES     = " LEFT JOIN FETCH persoon.indicaties as indicatie ";
    private static final String JOIN_BETROKKENHEDEN = " LEFT JOIN FETCH persoon.betrokkenheden as betrokkenheid ";
    private static final String JOIN_ALLES          = " LEFT JOIN FETCH persoon.adressen as adres "
        + "LEFT JOIN FETCH persoon.betrokkenheden as betrokkenheid "
        + "LEFT JOIN FETCH persoon.persoonVoornaam as persoonvoornaam "
        + "LEFT JOIN FETCH persoon.geslachtsnaamcomponenten as geslachtsnaamcomponent "
        + "LEFT JOIN FETCH persoon.nationaliteiten as nationaliteit "
        + "LEFT JOIN FETCH persoon.indicaties as indicatie ";

    /** Een pure shortcut om 2 objecten bij elkaar te houden. */
    private static final class QueryParameter {

        private final String dbVeldNaam;
        private final String paramNaam;
        private final Object waarde;

        /**
         * Een pure shortcut om 2 objecten bij elkaar te houden.
         *
         * @param dbVeldNaam Database veld naam
         * @param paramNaam de naam
         * @param waarde de waarde
         */
        private QueryParameter(final String dbVeldNaam, final String paramNaam, final Object waarde) {
            this.dbVeldNaam = dbVeldNaam;
            this.paramNaam = paramNaam;
            this.waarde = waarde;
        }
    }

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    // TODO: de nieuwe history repository moeten nog aangemaakt worden.
    @Inject
    private GroepHistorieRepository<PersoonModel, AbstractPersoonSamengesteldeNaamGroep,
            PersoonSamengesteldeNaamHisModel> persoonSamengesteldeNaamHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel, AbstractPersoonGeslachtsaanduidingGroep,
            PersoonGeslachtsaanduidingHisModel> persoonGeslachtsaanduidingHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel, AbstractPersoonBijhoudingsverantwoordelijkheidGroep,
            PersoonBijhoudingsverantwoordelijkheidHisModel> persoonBijhoudingsverantwoordelijkheidHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel, AbstractPersoonIdentificatienummersGroep,
            PersoonIdentificatienummersHisModel> persoonIdentificatienummersHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel, AbstractPersoonAanschrijvingGroep,
            PersoonAanschrijvingHisModel> persoonAanschrijvingHistorieRepository;

    @Inject
    private GroepFormeleHistorieRepository<PersoonModel> persoonGeboorteHistorieRepository;

    @Inject
    private GroepFormeleHistorieRepository<PersoonModel> persoonOverlijdenHistorieRepository;

    @Inject
    private GroepFormeleHistorieRepository<PersoonModel> persoonInschrijvingHistorieRepository;

    @Inject
    private PersoonOpschortingHistorieRepository persoonOpschortingHistorieRepository;

    @Inject
    private GroepFormeleHistorieRepository<PersoonModel> persoonEUVerkiezingenHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonGeslachtsnaamcomponentModel,
            AbstractPersoonGeslachtsnaamcomponentStandaardGroep,
            PersoonGeslachtsnaamcomponentHisModel> persoonGeslachtsnaamcomponentHistorieRepository;

    @Inject
    private PersoonAdresHistorieRepository persoonAdresHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonNationaliteitModel, AbstractPersoonNationaliteitStandaardGroep,
            PersoonNationaliteitHisModel> persoonNationaliteitStandaardHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonVoornaamModel, AbstractPersoonVoornaamStandaardGroep,
            PersoonVoornaamHisModel> persoonVoornaamHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel, AbstractPersoonBijhoudingsgemeenteGroep,
            PersoonBijhoudingsgemeenteHisModel> bijhoudingsgemeenteHistorieRepository;

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    /**
     * Creert een nieuw instantie van AttribuutType<String>, waar de waarde wordt geuppercased
     * of op null gezet indien leeg.
     *
     * @param origineel de originele attribuut
     * @return de nieuwe attribuut.
     */
    private Object creerZelfdeTypeUppercased(final AttribuutType<String> origineel) {
        if (origineel != null && origineel.getWaarde() != null) {
            try {
                Constructor cons = origineel.getClass().getConstructor(java.lang.String.class);
                String newWaarde = StringUtils.trim(origineel.getWaarde());
                if (StringUtils.isNotEmpty(newWaarde)) {
                    newWaarde = newWaarde.toUpperCase();
                    return cons.newInstance(newWaarde);
                }
            } catch (NoSuchMethodException e) {
                LOGGER.error(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                LOGGER.error(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                LOGGER.error(e.getMessage(), e);
            } catch (InstantiationException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * Methode voor het kopieren van een huisnummer.
     *
     * @param origineel het huisnummer dat gekopieerd dient te worden.
     * @return een nieuw huisnummer met dezelfde waarde als het origineel.
     */
    private Huisnummer creerZelfdeHuisnummer(final Huisnummer origineel) {
        Huisnummer huisnummer = null;
        if (origineel != null && origineel.getWaarde() != null) {
            huisnummer = new Huisnummer(origineel.getWaarde());
        }
        return huisnummer;
    }

    @Override
    public boolean isBSNAlIngebruik(final Burgerservicenummer bsn) {
        return findByBurgerservicenummer(bsn) != null;
    }

    @Override
    public PersoonModel findByBurgerservicenummer(final Burgerservicenummer bsn) {
        TypedQuery<PersoonModel> tQuery =
            em.createQuery("SELECT persoon FROM PersoonModel persoon"
                + " WHERE identificatienummers.burgerservicenummer = :burgerservicenummer", PersoonModel.class);
        tQuery.setParameter("burgerservicenummer", bsn);

        PersoonModel persoon;
        try {
            persoon = tQuery.getSingleResult();
        } catch (NoResultException e) {
            persoon = null;
        }

        return persoon;
    }

    /**
     * Voer een query uit en return de gevonden persoon. Deze query geeft een null als er meerdere gevonden
     * worden.
     *
     * @param query de uit te voeren query als text.
     * @param parameters variabele pameters (combinatie van naam, waarde)
     * @return de persistent persoon of null als het niet (of MEERDERE) gevonden wordt.
     */

    private PersoonModel haalPersoonOpViaQuery(final String query, final QueryParameter... parameters) {
        TypedQuery<PersoonModel> tQuery = em.createQuery(query, PersoonModel.class);
        if (parameters != null) {
            for (QueryParameter param : parameters) {
                tQuery.setParameter(param.paramNaam, param.waarde);
            }
        }
        PersoonModel persoon;
        try {
            persoon = tQuery.getSingleResult();
        } catch (NoResultException e) {
            persoon = null;
        }
        return persoon;
    }

    /**
     * Voer een query uit en return de gevonden personen.
     *
     * @param query de uit te voeren query als text.
     * @param parameters variabele pameters (combinatie van naam, waarde)
     * @return de persistent persoon of null als het niet gevonden wordt.
     */
    private List<PersoonModel> haalPersonenOpViaQuery(final String query, final QueryParameter... parameters) {
        // Vertaal "= null" naar "is null"
        String uitTeVoerenQuery = query;
        List<QueryParameter> teGebruikenParameters = new ArrayList<QueryParameter>();
        for (QueryParameter parameter : parameters) {
            if (parameter.waarde == null) {
                String paramNaam = parameter.paramNaam;
                uitTeVoerenQuery =
                    uitTeVoerenQuery.replace("= :" + paramNaam, "is null or " + parameter.dbVeldNaam + " = '' ");
            } else {
                teGebruikenParameters.add(parameter);
            }
        }

        TypedQuery<PersoonModel> tQuery = em.createQuery(uitTeVoerenQuery, PersoonModel.class);

        if (teGebruikenParameters.size() > 0) {
            for (QueryParameter param : teGebruikenParameters) {
                tQuery.setParameter(param.paramNaam, param.waarde);
            }
        }
        List<PersoonModel> personen;
        try {
            personen = tQuery.getResultList();
        } catch (NoResultException e) {
            personen = new ArrayList<PersoonModel>();
        }
        return personen;

    }

    @Override
    public PersoonModel haalPersoonMetAdres(final Integer persId) {
        StringBuilder query =
            new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES).append(JOIN_INDICATIES).append(JOIN_BETROKKENHEDEN);
        query.append("WHERE persoon.id = :persId ");
        PersoonModel persistentPersoon =
            haalPersoonOpViaQuery(query.toString(), new QueryParameter("persoon.id", "persId", persId));
        return persistentPersoon;
    }

    @Override
    public PersoonModel haalPersoonOpMetAdresViaBetrokkenheid(final BetrokkenheidModel betrokkenheid) {
        StringBuilder query =
                new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES).append(JOIN_INDICATIES).append(JOIN_BETROKKENHEDEN);
            query.append("WHERE betrokkenheid = :betrokkenheid ");
            PersoonModel persistentPersoon =
                haalPersoonOpViaQuery(query.toString(), new QueryParameter("betrokkenheid", "betrokkenheid", betrokkenheid));
            return persistentPersoon;
    }
    
    @Override
    public PersoonModel haalPersoonSimpel(final Integer persId) {
        StringBuilder query =
            new StringBuilder(SELECT_PERSOON);
        query.append("WHERE persoon.id = :persId ");
        PersoonModel persistentPersoon =
            haalPersoonOpViaQuery(query.toString(), new QueryParameter("persoon.id", "persId", persId));
        return persistentPersoon;
    }

    /**
     * Zoek een (of meerdere) personen met zijn adres adv. zijn bsn nummer. We verwachten een als resultaat,
     * maar kan ook meerdere in exceptionele gevallen.
     *
     * @param bsn de bsn
     * @return een lijst met logische personen (of lege lijst)
     */
    @Override
    public List<PersoonModel> haalPersonenMetWoonAdresOpViaBurgerservicenummer(final Burgerservicenummer bsn) {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES).append(JOIN_INDICATIES);
        query.append("WHERE persoon.identificatienummers.burgerservicenummer = :burgerservicenummer ");
        query.append("AND adres.gegevens.soort = 1");

        List<PersoonModel> persistentPersonen =
            haalPersonenOpViaQuery(query.toString(), new QueryParameter(
                "persoon.identificatienummers.burgerservicenummer.waarde", "burgerservicenummer", bsn));

        return persistentPersonen;
    }

    @Override
    public List<PersoonModel> haalPersonenMetWoonAdresOpViaVolledigAdres(final NaamOpenbareRuimte naamOpenbareRuimte,
        final Huisnummer huisnummer, final Huisletter huisletter, final Huisnummertoevoeging huisnummertoevoeging,
        final Plaats woonplaats, final Partij gemeente)
    {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ALLES);
        query.append("WHERE (upper(adres.gegevens.naamOpenbareRuimte) = :naamOpenbareRuimte ) ")
             .append("AND (adres.gegevens.huisnummer = :huisnummer ) ")
             .append("AND (upper(adres.gegevens.huisletter) = :huisletter ) ")
             .append("AND (upper(adres.gegevens.huisnummertoevoeging) = :huisnummertoevoeging ) ")
             .append("AND ((adres.gegevens.woonplaats = :woonplaats) OR (adres.gegevens.woonplaats IS NULL)) ")
             .append("AND (adres.gegevens.gemeente) = :gemeente ) ")
             .append("AND (adres.gegevens.soort = 1) ");

        List<PersoonModel> persistentPersonen =
            haalPersonenOpViaQuery(
                query.toString(),
                new QueryParameter("adres.gegevens.naamOpenbareRuimte", "naamOpenbareRuimte",
                    creerZelfdeTypeUppercased(naamOpenbareRuimte)),
                new QueryParameter("adres.gegevens.huisnummer", "huisnummer", creerZelfdeHuisnummer(huisnummer)),
                new QueryParameter("adres.gegevens.huisletter", "huisletter", creerZelfdeTypeUppercased(huisletter)),
                new QueryParameter("adres.gegevens.huisnummertoevoeging", "huisnummertoevoeging",
                    creerZelfdeTypeUppercased(huisnummertoevoeging)),
                new QueryParameter("adres.gegevens.woonplaats", "woonplaats", woonplaats),
                new QueryParameter("adres.gegevens.gemeente", "gemeente", gemeente));

        return persistentPersonen;
    }

    @Override
    public List<PersoonModel> haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
        final IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding)
    {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ALLES);
        query.append("WHERE adres.gegevens.identificatiecodeNummeraanduiding = :idCodeNumaanduiding ");
        query.append("AND adres.gegevens.soort = 1");

        List<PersoonModel> persistentPersonen =
            haalPersonenOpViaQuery(query.toString(), new QueryParameter(
                "adres.gegevens.identificatiecodeNummeraanduiding", "idCodeNumaanduiding",
                identificatiecodeNummeraanduiding));

        return persistentPersonen;
    }

    /**
     * Zoek een persoon wonende op een adres.
     *
     * @param postcode postcode Postcode van het adres.
     * @param huisnummer huisnummer Huisnummer van het adres.
     * @param huisletter huisletter Huisletter van het aders.
     * @param huisnummertoevoeging huisnummertoevoeging Huisnummertoevoeging van het adres.
     * @return Lijst van gevonden personen op het adres.
     */
    @Override
    public List<PersoonModel> haalPersonenOpMetAdresViaPostcodeHuisnummer(final Postcode postcode,
        final Huisnummer huisnummer, final Huisletter huisletter, final Huisnummertoevoeging huisnummertoevoeging)
    {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ALLES);
        query.append("WHERE (upper(adres.gegevens.postcode) = :postcode ) ");
        query.append("AND (adres.gegevens.huisnummer = :huisnummer ) ");
        query.append("AND (upper(adres.gegevens.huisletter) = :huisletter ) ");
        query.append("AND (upper(adres.gegevens.huisnummertoevoeging) = :huisnummertoevoeging ) ");
        query.append("AND adres.gegevens.soort = 1");

        List<PersoonModel> persistentPersonen = haalPersonenOpViaQuery(query.toString(),
            new QueryParameter("adres.gegevens.postcode", "postcode", creerZelfdeTypeUppercased(postcode)),
            new QueryParameter("adres.gegevens.huisnummer", "huisnummer", creerZelfdeHuisnummer(huisnummer)),
            new QueryParameter("adres.gegevens.huisletter", "huisletter", creerZelfdeTypeUppercased(huisletter)),
            new QueryParameter("adres.gegevens.huisnummertoevoeging", "huisnummertoevoeging",
                creerZelfdeTypeUppercased(huisnummertoevoeging)));
        return persistentPersonen;
    }

    @Override
    public PersoonModel haalPersoonOpMetBurgerservicenummer(final Burgerservicenummer bsn) {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ALLES);
        query.append("WHERE persoon.identificatienummers.burgerservicenummer = :burgerservicenummer");

        PersoonModel persoon =
            haalPersoonOpViaQuery(query.toString(), new QueryParameter(
                "persoon.identificatienummers.burgerservicenummer", "burgerservicenummer", bsn));
        return persoon;
    }

    /**
     * Gebruik deze merge ipv. de em.merge direct, omdat deze de tijdstipLaatsteWijziging bijhoudt met de
     * actie.tijsdtipRegistratie.
     * @param persoon de persoon
     * @param actie de actie die er bij hoort.
     */
    private void mergePersoon(final PersoonModel persoon, final ActieModel actie) {
        persoon.getAfgeleidAdministratief().setTijdstipLaatsteWijziging(actie.getTijdstipRegistratie());
        em.merge(persoon);
    }

    @Override
    public PersoonModel opslaanNieuwPersoon(final PersoonModel persoon, final ActieModel actie,
        final Datum datumAanvangGeldigheid)
    {
        // Controlleer of de persoon niet al bestaat op basis van de BSN nummer
        if (!isBSNAlIngebruik(persoon.getIdentificatienummers().getBurgerservicenummer())) {

            // Persisteer de persoon eerst in de A-laag, persisteer de C/D lagen en update dan de A-loaag
            // Persisteer samen gestelde naam indien deze afgeleid en aanwezig is.
            em.persist(persoon);
            werkHistorieBij(persoon, actie, datumAanvangGeldigheid);

            mergePersoon(persoon, actie);
            return persoon;
        } else {
            // Persoon bestaat al
            final String bsn = persoon.getIdentificatienummers().getBurgerservicenummer().getWaarde();
            LOGGER.error("Persoon bestaat al met de bsn: {}", bsn);
            throw new ObjectReedsBestaandExceptie(ObjectReedsBestaandExceptie.ReferentieVeld.BSN, bsn, null);
        }
    }

    /**
     * Werk de C-laag bij voor PersistentPersoon. Let op, deze functie is geschikt alleen bij het aanmaken van een
     * nieuw (!) persoon. In andere gevallen, moeten we eigenlijk nog uitzoeken (als het groep NIET is gewijzigd, of er
     * een nieuwe historie record aangemaakt moet worden !!).
     *
     * @param persoon De zojuist toegevoegde persoon.
     * @param actie De actie die leidt tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid datum van aanvang geldigheid
     */
    private void werkHistorieBij(final PersoonModel persoon, final ActieModel actie,
        final Datum datumAanvangGeldigheid)
    {
        // TODO: alle historische greoepen moet nog geimplementeerd worden.
        if (persoon.getAanschrijving() != null) {
            persoonAanschrijvingHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }

        // Note: persoon.getAfgeleidAdministratief() heeft nooit een historie.

        if (persoon.getBijhoudingsgemeente() != null) {
            bijhoudingsgemeenteHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }
        if (persoon.getBijhoudingsverantwoordelijkheid() != null) {
            persoonBijhoudingsverantwoordelijkheidHistorieRepository.persisteerHistorie(persoon, actie,
                datumAanvangGeldigheid, null);
        }
        if (persoon.getGeboorte() != null) {
            persoonGeboorteHistorieRepository.persisteerHistorie(persoon, actie);
        }
        if (persoon.getGeslachtsaanduiding() != null) {
            persoonGeslachtsaanduidingHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid,
                null);
        }
        if (persoon.getIdentificatienummers() != null) {
            persoonIdentificatienummersHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid,
                null);
        }
        if (persoon.getInschrijving() != null) {
            persoonInschrijvingHistorieRepository.persisteerHistorie(persoon, actie);
        }
        if (persoon.getOpschorting() != null) {
            persoonOpschortingHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
            // TODO: hoe komen we erachter dat een opschorting is beeindigd?
            // In dat geval moet de historiestatus op 'M' gezet worden.
        }
        if (persoon.getOverlijden() != null) {
            persoonOverlijdenHistorieRepository.persisteerHistorie(persoon, actie);
            // TODO: hoe komen we erachter overlijden is gecorrigeerd?
            // In dat geval moet de historiestatus op 'F' gezet worden.

        }
        if (persoon.getSamengesteldeNaam() != null) {
            persoonSamengesteldeNaamHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }
        if (persoon.getEUVerkiezingen() != null) {
            persoonEUVerkiezingenHistorieRepository.persisteerHistorie(persoon, actie);
        }

        //
        // persoon.getImmigratie();
        // persoon.getPersoonsKaart();
        // persoon.getUitsluitingNLKiesrecht();
        // persoon.getVerblijfsrecht();

        // Sla voornamen historie op in de C-laag
        for (PersoonVoornaamModel voornaam : persoon.getPersoonVoornaam()) {
            persoonVoornaamHistorieRepository.persisteerHistorie(voornaam, actie, datumAanvangGeldigheid, null);
        }

        // Sla geslachtsnaamcomponenten historie op in de C-laag
        for (PersoonGeslachtsnaamcomponentModel geslachtsnaamcomponent : persoon.getGeslachtsnaamcomponenten()) {
            persoonGeslachtsnaamcomponentHistorieRepository.persisteerHistorie(geslachtsnaamcomponent, actie,
                datumAanvangGeldigheid, null);
        }

        // Sla adressen op in de C-laag
        for (PersoonAdresModel persoonAdres : persoon.getAdressen()) {
            persoonAdresHistorieRepository.persisteerHistorie(persoonAdres, actie, datumAanvangGeldigheid, null);
        }

        // Sla Nationaliteit historie op in de C-laag
        // Hoeft niet meer hier te gebeuren. wordt al afgehandeld in de Persoon Nationaliteit Repository.
        // Let op: de PersoonGeslachtsnaamcomponenten, en Persoonvoornamen worden WEL bij deze persoon opgeslagen.
        // maar voor als deze toch meegeleverd is, dan moeten we wel de historie bijhouden, omdat deze ook in de
        // persoonModel.getNationaliteiten() inzit.
        for (PersoonNationaliteitModel nationaliteit : persoon.getNationaliteiten()) {
            persoonNationaliteitStandaardHistorieRepository.persisteerHistorie(nationaliteit, actie,
                datumAanvangGeldigheid, null);
        }

    }

    @Override
    public void werkbijBijhoudingsgemeente(final Burgerservicenummer bsn,
        final PersoonBijhoudingsgemeenteGroep bijhoudingsgemeente, final ActieModel actie,
        final Datum datumAanvangGeldigheid)
    {
        // haal eerst de persoon op, we krijgen hier een bsn nummer.
        // Dan schrijf de bijhoudingsgemeente op in de C-laag, dan update de A-laag
        // maar de history repository support nog geen 'aparte' groepen om te updaten.
        // ==> eerst de A, dan de C
        PersoonModel bestaandePersoon = findByBurgerservicenummer(bsn);
        if (null == bestaandePersoon) {
            LOGGER.error("Kan BSN niet vinden " + bsn.getWaarde());
            throw new IllegalArgumentException("Persoon niet bestaand");
        }
        bestaandePersoon.vervangGroepen(bijhoudingsgemeente);
        mergePersoon(bestaandePersoon, actie);
        bijhoudingsgemeenteHistorieRepository.persisteerHistorie(bestaandePersoon, actie, datumAanvangGeldigheid, null);
    }

    @Override
    public void werkbijNaamGebruik(final PersoonModel persoon, final PersoonAanschrijvingGroep aanschrijving,
        final ActieModel actie, final Datum datumAanvangGeldigheid)
    {
        persoon.vervangGroepen(aanschrijving);
        mergePersoon(persoon, actie);
        persoonAanschrijvingHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
    }


    @Override
    public void vulaanAdresMetHistorie(final PersoonModel persoon, final boolean inclFormeleHistorie) {
        if (persoon.getAdressen() != null) {
            for (PersoonAdresModel adres : persoon.getAdressen()) {
                persoonAdresRepository.vulaanAdresMetHistorie(adres, inclFormeleHistorie);
            }
        }
    }

    @Override
    public void werkbijOverlijden(final PersoonModel persoon,
            final PersoonOverlijdenGroep overlijden,
            final PersoonOpschortingGroep opschorting, final ActieModel actie,
            final Datum datumAanvangGeldigheid)
    {
        if (null == persoon) {
            LOGGER.error("Parameter persoon is null ");
            throw new IllegalArgumentException("Persoon niet bestaand");
        }
        if (null == overlijden) {
            LOGGER.error("Parameter overlijden is null ");
            throw new IllegalArgumentException("Overlijdengroep niet bestaand");
        }
        persoon.vervangGroepen(overlijden);
        if (opschorting != null) {
            persoon.vervangGroepen(opschorting);
        }
        mergePersoon(persoon, actie);
        persoonOverlijdenHistorieRepository.persisteerHistorie(persoon, actie /*, datumAanvangGeldigheid, null*/);
        if (opschorting != null) {
            persoonOpschortingHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }

    }

    @Override
    public void werkbijTijsptipLaastWijziging(final PersoonModel persoon, final DatumTijd tijdstip) {
        persoon.getAfgeleidAdministratief().setTijdstipLaatsteWijziging(tijdstip);
        em.merge(persoon);

    }

}
