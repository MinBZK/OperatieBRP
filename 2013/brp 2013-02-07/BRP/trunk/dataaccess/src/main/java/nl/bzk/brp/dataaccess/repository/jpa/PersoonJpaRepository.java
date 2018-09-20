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
import nl.bzk.brp.dataaccess.repository.historie.GroepMaterieleHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonOpschortingRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisletter;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummertoevoeging;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.basis.AttribuutType;
import nl.bzk.brp.model.logisch.kern.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOpschortingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsaardModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsgemeenteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAfgeleidAdministratiefGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} class en standaard implementatie
 * van de {@link nl.bzk.brp.dataaccess.repository.PersoonRepository} class.
 */
@Repository
public class PersoonJpaRepository implements PersoonRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonJpaRepository.class);

    private static final String SELECT_PERSOON = " SELECT distinct persoon FROM PersoonModel persoon ";
    private static final String JOIN_ADRES = " LEFT JOIN FETCH persoon.adressen as adres ";
    private static final String JOIN_INDICATIES = " LEFT JOIN FETCH persoon.indicaties as indicatie ";
    private static final String JOIN_BETROKKENHEDEN = " LEFT JOIN FETCH persoon.betrokkenheden as betrokkenheid ";
    private static final String JOIN_ALLES = " LEFT JOIN FETCH persoon.adressen as adres "
            + "LEFT JOIN FETCH persoon.voornamen as voornamen "
            + "LEFT JOIN FETCH persoon.geslachtsnaamcomponenten as geslachtsnaamcomponent "
            + "LEFT JOIN FETCH persoon.nationaliteiten as nationaliteit "
            + "LEFT JOIN FETCH persoon.indicaties as indicatie "
            + "LEFT JOIN FETCH persoon.betrokkenheden as betrokkenheid ";

    /**
     * Een pure shortcut om 2 objecten bij elkaar te houden.
     */
    private static final class QueryParameter {

        private final String dbVeldNaam;
        private final String paramNaam;
        private final Object waarde;

        /**
         * Een pure shortcut om 2 objecten bij elkaar te houden.
         *
         * @param dbVeldNaam Database veld naam
         * @param paramNaam  de naam
         * @param waarde     de waarde
         */
        private QueryParameter(final String dbVeldNaam, final String paramNaam, final Object waarde) {
            this.dbVeldNaam = dbVeldNaam;
            this.paramNaam = paramNaam;
            this.waarde = waarde;
        }
    }

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    // TODO: HisPersoonSamengesteldeNaamModel implementeerd Materiele Geschiedenis nog niet
    @Inject
    private GroepMaterieleHistorieRepository<PersoonModel, PersoonSamengesteldeNaamGroep,
            HisPersoonSamengesteldeNaamModel> historiePersoonSamengesteldeNaamRepository;

    @Inject
    private GroepMaterieleHistorieRepository<PersoonModel, PersoonGeslachtsaanduidingGroep,
            HisPersoonGeslachtsaanduidingModel> historiePersoonGeslachtsaanduidingRepository;

    @Inject
    private GroepMaterieleHistorieRepository<PersoonModel, PersoonBijhoudingsaardGroep,
            HisPersoonBijhoudingsaardModel> historiePersoonBijhoudingsaardRepository;

    @Inject
    private GroepMaterieleHistorieRepository<PersoonModel, PersoonIdentificatienummersGroep,
            HisPersoonIdentificatienummersModel> historiePersoonIdentificatienummersRepository;

    /**
     * Voorzichtig, moet dit echt formele historie zijn?
     */
    @Inject
    private GroepFormeleHistorieRepository<PersoonModel>
            historiePersoonAanschrijvingRepository;

    @Inject
    private GroepFormeleHistorieRepository<PersoonModel> historiePersoonGeboorteRepository;

    @Inject
    private GroepFormeleHistorieRepository<PersoonModel> historiePersoonOverlijdenRepository;

    @Inject
    private GroepFormeleHistorieRepository<PersoonModel> historiePersoonInschrijvingRepository;

    @Inject
    private HistoriePersoonOpschortingRepository historiePersoonOpschortingRepository;

    @Inject
    private GroepFormeleHistorieRepository<PersoonModel> historiePersoonEUVerkiezingenRepository;

    @Inject
    private
    GroepMaterieleHistorieRepository<PersoonGeslachtsnaamcomponentModel, PersoonGeslachtsnaamcomponentStandaardGroep,
            HisPersoonGeslachtsnaamcomponentModel> historiePersoonGeslachtsnaamcomponentRepository;

    @Inject
    private HistoriePersoonAdresRepository historiePersoonAdresRepository;

    @Inject
    private GroepMaterieleHistorieRepository<PersoonNationaliteitModel, PersoonNationaliteitStandaardGroep,
            HisPersoonNationaliteitModel> historiePersoonNationaliteitStandaardRepository;

    @Inject
    private GroepMaterieleHistorieRepository<PersoonVoornaamModel, PersoonVoornaamStandaardGroep,
            HisPersoonVoornaamModel> historiePersoonVoornaamRepository;

    @Inject
    private GroepMaterieleHistorieRepository<PersoonModel, PersoonBijhoudingsgemeenteGroep,
            HisPersoonBijhoudingsgemeenteModel> historiePersoonBijhoudingsgemeenteRepository;

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Override
    public PersoonModel findPersoonMetId(Integer iD) {
        return em.find(PersoonModel.class, iD);
    }

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
        TypedQuery<Long> tQuery = em.createQuery("SELECT COUNT(*) FROM PersoonModel persoon"
                                                         +
                                                         " WHERE identificatienummers.burgerservicenummer = :burgerservicenummer",
                                                 Long.class);
        tQuery.setParameter("burgerservicenummer", bsn);

        return tQuery.getResultList().get(0).longValue() > 0L;
    }

    @Override
    public PersoonModel findByBurgerservicenummer(final Burgerservicenummer bsn) {
        TypedQuery<PersoonModel> tQuery =
                em.createQuery("SELECT persoon FROM PersoonModel persoon"
                                       + " WHERE identificatienummers.burgerservicenummer = :burgerservicenummer",
                               PersoonModel.class);
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
     * @param query      de uit te voeren query als text.
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
     * @param query      de uit te voeren query als text.
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
                new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES).append(JOIN_INDICATIES)
                        .append(JOIN_BETROKKENHEDEN);
        query.append("WHERE persoon.id = :persId ");
        return haalPersoonOpViaQuery(query.toString(), new QueryParameter("persoon.id", "persId", persId));
    }

    @Override
    public PersoonModel haalPersoonOpMetAdresViaBetrokkenheid(final BetrokkenheidModel betrokkenheid) {
        StringBuilder query =
                new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES).append(JOIN_INDICATIES)
                        .append(JOIN_BETROKKENHEDEN);
        query.append("WHERE betrokkenheid = :betrokkenheid ");
        return haalPersoonOpViaQuery(query.toString(),
                                     new QueryParameter("betrokkenheid", "betrokkenheid", betrokkenheid));
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
        query.append("AND adres.standaard.soort = 1");

        List<PersoonModel> persistentPersonen =
                haalPersonenOpViaQuery(query.toString(), new QueryParameter(
                        "persoon.identificatienummers.burgerservicenummer.waarde", "burgerservicenummer", bsn));

        return persistentPersonen;
    }

    @Override
    public List<PersoonModel> haalPersonenMetWoonAdresOpViaVolledigAdres(final NaamOpenbareRuimte naamOpenbareRuimte,
                                                                         final Huisnummer huisnummer,
                                                                         final Huisletter huisletter,
                                                                         final Huisnummertoevoeging huisnummertoevoeging,
                                                                         final Plaats woonplaats, final Partij gemeente)
    {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ALLES);
        query.append("WHERE (upper(adres.standaard.naamOpenbareRuimte) = :naamOpenbareRuimte ) ")
                .append("AND (adres.standaard.huisnummer = :huisnummer ) ")
                .append("AND (upper(adres.standaard.huisletter) = :huisletter ) ")
                .append("AND (upper(adres.standaard.huisnummertoevoeging) = :huisnummertoevoeging ) ")
                .append("AND ((adres.standaard.woonplaats = :woonplaats) OR (adres.standaard.woonplaats IS NULL)) ")
                .append("AND (adres.standaard.gemeente) = :gemeente ) ")
                .append("AND (adres.standaard.soort = 1) ");

        List<PersoonModel> persistentPersonen =
                haalPersonenOpViaQuery(
                        query.toString(),
                        new QueryParameter("adres.standaard.naamOpenbareRuimte", "naamOpenbareRuimte",
                                           creerZelfdeTypeUppercased(naamOpenbareRuimte)),
                        new QueryParameter("adres.standaard.huisnummer", "huisnummer",
                                           creerZelfdeHuisnummer(huisnummer)),
                        new QueryParameter("adres.standaard.huisletter", "huisletter",
                                           creerZelfdeTypeUppercased(huisletter)),
                        new QueryParameter("adres.standaard.huisnummertoevoeging", "huisnummertoevoeging",
                                           creerZelfdeTypeUppercased(huisnummertoevoeging)),
                        new QueryParameter("adres.standaard.woonplaats", "woonplaats", woonplaats),
                        new QueryParameter("adres.standaard.gemeente", "gemeente", gemeente));

        return persistentPersonen;
    }

    @Override
    public List<PersoonModel> haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
            final IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding)
    {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ALLES);
        query.append("WHERE adres.standaard.identificatiecodeNummeraanduiding = :idCodeNumaanduiding ");
        query.append("AND adres.standaard.soort = 1");

        List<PersoonModel> persistentPersonen =
                haalPersonenOpViaQuery(query.toString(), new QueryParameter(
                        "adres.standaard.identificatiecodeNummeraanduiding", "idCodeNumaanduiding",
                        identificatiecodeNummeraanduiding));

        return persistentPersonen;
    }

    /**
     * Zoek een persoon wonende op een adres.
     *
     * @param postcode             postcode Postcode van het adres.
     * @param huisnummer           huisnummer Huisnummer van het adres.
     * @param huisletter           huisletter Huisletter van het aders.
     * @param huisnummertoevoeging huisnummertoevoeging Huisnummertoevoeging van het adres.
     * @return Lijst van gevonden personen op het adres.
     */
    @Override
    public List<PersoonModel> haalPersonenOpMetAdresViaPostcodeHuisnummer(final Postcode postcode,
                                                                          final Huisnummer huisnummer,
                                                                          final Huisletter huisletter,
                                                                          final Huisnummertoevoeging huisnummertoevoeging)
    {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ALLES);
        query.append("WHERE (upper(adres.standaard.postcode) = :postcode ) ");
        query.append("AND (adres.standaard.huisnummer = :huisnummer ) ");
        query.append("AND (upper(adres.standaard.huisletter) = :huisletter ) ");
        query.append("AND (upper(adres.standaard.huisnummertoevoeging) = :huisnummertoevoeging ) ");
        query.append("AND adres.standaard.soort = 1");

        List<PersoonModel> persistentPersonen = haalPersonenOpViaQuery(query.toString(),
                                                                       new QueryParameter("adres.standaard.postcode",
                                                                                          "postcode",
                                                                                          creerZelfdeTypeUppercased(
                                                                                                  postcode)),
                                                                       new QueryParameter("adres.standaard.huisnummer",
                                                                                          "huisnummer",
                                                                                          creerZelfdeHuisnummer(
                                                                                                  huisnummer)),
                                                                       new QueryParameter("adres.standaard.huisletter",
                                                                                          "huisletter",
                                                                                          creerZelfdeTypeUppercased(
                                                                                                  huisletter)),
                                                                       new QueryParameter(
                                                                               "adres.standaard.huisnummertoevoeging",
                                                                               "huisnummertoevoeging",
                                                                               creerZelfdeTypeUppercased(
                                                                                       huisnummertoevoeging)));
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
     *
     * @param persoon de persoon
     * @param actie   de actie die er bij hoort.
     */
    private void mergePersoon(final PersoonModel persoon, final ActieModel actie) {

        PersoonAfgeleidAdministratiefGroepModel oudeGroep = persoon.getAfgeleidAdministratief();
        PersoonAfgeleidAdministratiefGroepModel nieuweGroep =
                new PersoonAfgeleidAdministratiefGroepModel(actie.getTijdstipRegistratie(),
                                                            oudeGroep.getIndicatieGegevensInOnderzoek());
        persoon.setAfgeleidAdministratief(nieuweGroep);
        em.merge(persoon);
    }

    @Override
    public PersoonModel opslaanNieuwPersoon(final PersoonModel persoon, final ActieModel actie,
                                            final Datum datumAanvangGeldigheid)
    {
        // Controlleer of de persoon niet al bestaat op basis van de BSN nummer
        if (!isBSNAlIngebruik(persoon.getIdentificatienummers().getBurgerservicenummer())) {

            // Persisteer de persoon eerst in de A-laag, persisteer de C/D lagen en update dan de A-laag
            // Persisteer samen gestelde naam indien deze afgeleid en aanwezig is.
            em.persist(persoon);
            werkHistorieBij(persoon, actie, datumAanvangGeldigheid);

            mergePersoon(persoon, actie);
            return persoon;
        } else {
            // Persoon bestaat al
            final String bsn = persoon.getIdentificatienummers().getBurgerservicenummer().toString();
            LOGGER.error("Persoon bestaat al met de bsn: {}", bsn);
            throw new ObjectReedsBestaandExceptie(ObjectReedsBestaandExceptie.ReferentieVeld.BSN, bsn, null);
        }
    }

    /**
     * Werk de C-laag bij voor PersistentPersoon. Let op, deze functie is geschikt alleen bij het aanmaken van een
     * nieuw (!) persoon. In andere gevallen, moeten we eigenlijk nog uitzoeken (als het groep NIET is gewijzigd, of er
     * een nieuwe historie record aangemaakt moet worden !!).
     *
     * @param persoon                De zojuist toegevoegde persoon.
     * @param actie                  De actie die leidt tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid datum van aanvang geldigheid
     */
    private void werkHistorieBij(final PersoonModel persoon, final ActieModel actie,
                                 final Datum datumAanvangGeldigheid)
    {
        if (persoon.getAanschrijving() != null) {
            historiePersoonAanschrijvingRepository
                    .persisteerHistorie(persoon, actie);  // aangepast van materiële naar formele historie
        }

        // Note: persoon.getAfgeleidAdministratief() heeft nooit een historie.

        if (persoon.getBijhoudingsgemeente() != null) {
            historiePersoonBijhoudingsgemeenteRepository
                    .persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }
        if (persoon.getBijhoudingsaard() != null) {
            historiePersoonBijhoudingsaardRepository.persisteerHistorie(persoon, actie,
                                                                        datumAanvangGeldigheid, null);
        }
        if (persoon.getGeboorte() != null) {
            historiePersoonGeboorteRepository.persisteerHistorie(persoon, actie);
        }
        if (persoon.getGeslachtsaanduiding() != null) {
            historiePersoonGeslachtsaanduidingRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid,
                                                                            null);
        }
        if (persoon.getIdentificatienummers() != null) {
            historiePersoonIdentificatienummersRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid,
                                                                             null);
        }
        if (persoon.getInschrijving() != null) {
            historiePersoonInschrijvingRepository.persisteerHistorie(persoon, actie);
        }
        if (persoon.getOpschorting() != null) {
            historiePersoonOpschortingRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
            // TODO: hoe komen we erachter dat een opschorting is beeindigd?
            // In dat geval moet de historiestatus op 'M' gezet worden.
        }
        if (persoon.getOverlijden() != null) {
            historiePersoonOverlijdenRepository.persisteerHistorie(persoon, actie);
            // TODO: hoe komen we erachter overlijden is gecorrigeerd?
            // In dat geval moet de historiestatus op 'F' gezet worden.

        }
        if (persoon.getSamengesteldeNaam() != null) {
            historiePersoonSamengesteldeNaamRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }
        if (persoon.getEUVerkiezingen() != null) {
            historiePersoonEUVerkiezingenRepository.persisteerHistorie(persoon, actie);
        }

        //
        // persoon.getImmigratie();
        // persoon.getPersoonsKaart();
        // persoon.getUitsluitingNLKiesrecht();
        // persoon.getVerblijfsrecht();

        // Sla voornamen historie op in de C-laag
        for (PersoonVoornaamModel voornaam : persoon.getVoornamen()) {
            historiePersoonVoornaamRepository.persisteerHistorie(voornaam, actie, datumAanvangGeldigheid, null);
        }

        // Sla geslachtsnaamcomponenten historie op in de C-laag
        for (PersoonGeslachtsnaamcomponentModel geslachtsnaamcomponent : persoon.getGeslachtsnaamcomponenten()) {
            historiePersoonGeslachtsnaamcomponentRepository.persisteerHistorie(geslachtsnaamcomponent, actie,
                                                                               datumAanvangGeldigheid, null);
        }

        // Sla adressen op in de C-laag
        for (PersoonAdresModel persoonAdres : persoon.getAdressen()) {
            historiePersoonAdresRepository.persisteerHistorie(persoonAdres, actie, datumAanvangGeldigheid, null);
        }

        // Sla Nationaliteit historie op in de C-laag
        // Hoeft niet meer hier te gebeuren. wordt al afgehandeld in de Persoon Nationaliteit Repository.
        // Let op: de PersoonGeslachtsnaamcomponenten, en Persoonvoornamen worden WEL bij deze persoon opgeslagen.
        // maar voor als deze toch meegeleverd is, dan moeten we wel de historie bijhouden, omdat deze ook in de
        // persoonModel.getNationaliteiten() inzit.
        for (PersoonNationaliteitModel nationaliteit : persoon.getNationaliteiten()) {
            historiePersoonNationaliteitStandaardRepository.persisteerHistorie(nationaliteit, actie,
                                                                               datumAanvangGeldigheid, null);
        }

    }

    @Override
    public void werkbijBijhoudingsgemeente(final Burgerservicenummer bsn,
                                           final PersoonBijhoudingsgemeenteGroep bijhoudingsgemeente,
                                           final ActieModel actie,
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
        historiePersoonBijhoudingsgemeenteRepository
                .persisteerHistorie(bestaandePersoon, actie, datumAanvangGeldigheid, null);
    }

    @Override
    public void werkbijNaamGebruik(final PersoonModel persoon, final PersoonAanschrijvingGroep aanschrijving,
                                   final ActieModel actie, final Datum datumAanvangGeldigheid)
    {
        persoon.vervangGroepen(aanschrijving);
        mergePersoon(persoon, actie);
        historiePersoonAanschrijvingRepository.persisteerHistorie(persoon, actie);  // verschoven naar formele historie
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
        historiePersoonOverlijdenRepository.persisteerHistorie(persoon, actie /*, datumAanvangGeldigheid, null*/);
        if (opschorting != null) {
            historiePersoonOpschortingRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }

    }

}
