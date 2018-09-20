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
import nl.bzk.brp.dataaccess.repository.PersoonMdlRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresMdlHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonOpschortingHistorieRepository;
import nl.bzk.brp.dataaccess.repository.jpa.historie.GroepHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummerAanduiding;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsGemeenteGroep;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamComponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link nl.bzk.brp.model.objecttype.operationeel.PersoonModel} class en standaard implementatie
 *  van de {@link PersoonMdlRepository} class.
 */
@Repository
public class PersoonMdlJpaRepository implements PersoonMdlRepository {

    private static final Logger LOGGER            = LoggerFactory.getLogger(PersoonMdlJpaRepository.class);

    private static final String SELECT_PERSOON    = " SELECT distinct persoon FROM PersoonModel persoon ";
    private static final String JOIN_ADRES        = " LEFT JOIN FETCH persoon.adressen as adres ";

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
         * @param paramNaam de naam
         * @param waarde de waarde
         */
        private QueryParameter(final String dbVeldNaam, final String paramNaam, final Object waarde) {
            this.dbVeldNaam = dbVeldNaam;
            this.paramNaam = paramNaam;
            this.waarde = waarde;
        }
    }

    @PersistenceContext
    private EntityManager                                   em;


// TODO: de nieuwe history repository moeten nog aangemaakt worden.
    @Inject
    private GroepHistorieRepository<PersoonModel>   persoonSamengesteldeNaamHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel>   persoonGeslachtsaanduidingHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel>   persoonBijhoudingsVerantwoordelijkheidHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel>   persoonIdentificatienummersHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel>   persoonAanschrijvingHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel>   persoonGeboorteHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel>   persoonOverlijdenHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel>   persoonInschrijvingHistorieRepository;

    @Inject
    private PersoonOpschortingHistorieRepository   persoonOpschortingHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel>   persoonEUVerkiezingenHistorieRepository;


    @Inject
    private GroepHistorieRepository<PersoonGeslachtsnaamComponentModel>
    persoonGeslachtsnaamcomponentHistorieRepository;

    @Inject
    private PersoonAdresMdlHistorieRepository               persoonAdresHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonNationaliteitModel>   persoonNationaliteitStandaardMdlHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonVoornaamModel>   persoonVoornaamHistorieRepository;

    @Inject
    private GroepHistorieRepository<PersoonModel> bijhoudingsGemeenteHistorieRepository;


    /**
     * Creert een nieuw instantie van AbstractGegevensAttribuutType<String>, waar de waarfe wordt geuppercased
     * of op null gezet indien leeg.
     * @param origine de originele attribuut
     * @return de nieuwe attribuut.
     */
    private Object creerZelfdeTypeUppercased(final AbstractGegevensAttribuutType<String> origine) {
        if (origine != null) {
            try {
                Constructor cons = origine.getClass().getConstructor(java.lang.String.class);
                String newWaarde = StringUtils.trim(origine.getWaarde());
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


    @Override
    public boolean isBSNAlIngebruik(final Burgerservicenummer bsn) {
        return findByBurgerservicenummer(bsn) != null;
    }

    @Override
    public PersoonModel findByBurgerservicenummer(final Burgerservicenummer bsn) {
        TypedQuery<PersoonModel> tQuery =
            em.createQuery(
                    "SELECT persoon FROM PersoonModel persoon"
                    + " WHERE identificatieNummers.burgerServiceNummer = :burgerservicenummer",
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
     * @param query de uit te voeren query als text.
     * @param parameters variabele pameters (combinatie van naam, waarde)
     * @return de persistent persoon of null als het niet (of MEERDERE) gevonden wordt.
     */
    private PersoonModel haalPersoonOpViaQuery(final String query, final QueryParameter... parameters) {
        TypedQuery<PersoonModel> tQuery = em.createQuery(query.toString(), PersoonModel.class);
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
                uitTeVoerenQuery = uitTeVoerenQuery.replace("= :" + paramNaam, "is null or "
                        + parameter.dbVeldNaam + " = '' ");
            } else {
                teGebruikenParameters.add(parameter);
            }
        }

        TypedQuery<PersoonModel> tQuery = em.createQuery(uitTeVoerenQuery.toString(), PersoonModel.class);

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
    public PersoonModel haalPersoonMetAdres(final Long persId) {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES);
        query.append("WHERE persoon.id = :persId ");
        PersoonModel persistentPersoon = haalPersoonOpViaQuery(query.toString(),
            new QueryParameter("persoon.id", "persId", persId));
        return persistentPersoon;
    }

    /**
     * Zoek een (of meerdere) personen met zijn adres adv.ijn bsn nummer. We verwachten een als resultaat,
     * maar kan ook mmerdere in exceptionele gevallen.
     *
     * @param bsn de bsn
     * @return een lijst met logische personen (of lege lijst)
     */
    @Override
    public List<PersoonModel> haalPersonenMetWoonAdresOpViaBurgerservicenummer(final Burgerservicenummer bsn) {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES);
        query.append("WHERE persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer ");
        query.append("AND adres.gegevens.soort = 1");

        List<PersoonModel> persistentPersonen =
            haalPersonenOpViaQuery(query.toString(),
                    new QueryParameter("persoon.identificatieNummers.burgerServiceNummer.waarde",
                            "burgerservicenummer", bsn));

        return persistentPersonen;
    }

    @Override
    public List<PersoonModel> haalPersonenMetWoonAdresOpViaVolledigAdres(final NaamOpenbareRuimte naamOpenbareRuimte,
            final Huisnummer huisnummer, final Huisletter huisletter, final Huisnummertoevoeging huisnummertoevoeging,
            final LocatieOmschrijving locatieOmschrijving, final LocatieTovAdres locatietovAdres,
            final Plaats woonplaats)
    {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES);
        query
            .append("WHERE (upper(adres.gegevens.naamOpenbareRuimte) = :naamOpenbareRuimte ) ")
            .append("AND (upper(adres.gegevens.huisnummer) = :huisnummer ) ")
            .append("AND (upper(adres.gegevens.huisletter) = :huisletter ) ")
            .append("AND (upper(adres.gegevens.huisnummertoevoeging) = :huisnummertoevoeging ) ")
            .append("AND (upper(adres.gegevens.locatieOmschrijving) = :locatieOmschrijving ) ")
            .append("AND (upper(adres.gegevens.locatietovAdres) = :locatietovAdres ) ")
            .append("AND (adres.gegevens.woonplaats = :woonplaats ) ")
            .append("AND (adres.gegevens.soort = 1) ");

        List<PersoonModel> persistentPersonen = haalPersonenOpViaQuery(query.toString(),
            new QueryParameter("adres.gegevens.naamOpenbareRuimte", "naamOpenbareRuimte",
                    creerZelfdeTypeUppercased(naamOpenbareRuimte))
            , new QueryParameter("adres.gegevens.huisnummer", "huisnummer",
                    creerZelfdeTypeUppercased(huisnummer))
            , new QueryParameter("adres.gegevens.huisletter", "huisletter",
                    creerZelfdeTypeUppercased(huisletter))
            , new QueryParameter("adres.gegevens.huisnummertoevoeging", "huisnummertoevoeging",
                    creerZelfdeTypeUppercased(huisnummertoevoeging))
            , new QueryParameter("adres.gegevens.locatieOmschrijving", "locatieOmschrijving",
                    creerZelfdeTypeUppercased(locatieOmschrijving))
            , new QueryParameter("adres.gegevens.locatietovAdres", "locatietovAdres",
                    creerZelfdeTypeUppercased(locatietovAdres))
            , new QueryParameter("adres.gegevens.woonplaats", "woonplaats",
                    woonplaats)
        );

        return persistentPersonen;
    }

    @Override
    public List<PersoonModel> haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
            final IdentificatiecodeNummerAanduiding identificatiecodeNummeraanduiding)
    {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES);
        query.append("WHERE adres.gegevens.identificatiecodeNummeraanduiding = :idCodeNumaanduiding ");
        query.append("AND adres.gegevens.soort = 1");


        List<PersoonModel> persistentPersonen =
            haalPersonenOpViaQuery(query.toString(),
                    new QueryParameter("adres.gegevens.identificatiecodeNummeraanduiding", "idCodeNumaanduiding",
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
            final Huisnummer huisnummer, final Huisletter huisletter,
            final Huisnummertoevoeging huisnummertoevoeging)
    {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES);
        query.append("WHERE (upper(adres.gegevens.postcode) = :postcode ) ");
        query.append("AND (upper(adres.gegevens.huisnummer) = :huisnummer ) ");
        query.append("AND (upper(adres.gegevens.huisletter) = :huisletter ) ");
        query.append("AND (upper(adres.gegevens.huisnummertoevoeging) = :huisnummertoevoeging ) ");
        query.append("AND adres.gegevens.soort = 1");

        List<PersoonModel> persistentPersonen =
            haalPersonenOpViaQuery(query.toString(),
                    new QueryParameter("adres.gegevens.postcode", "postcode",
                            creerZelfdeTypeUppercased(postcode)),
                    new QueryParameter("adres.gegevens.huisnummer", "huisnummer",
                            creerZelfdeTypeUppercased(huisnummer)),
                    new QueryParameter("adres.gegevens.huisletter", "huisletter",
                            creerZelfdeTypeUppercased(huisletter)),
                    new QueryParameter("adres.gegevens.huisnummertoevoeging", "huisnummertoevoeging",
                            creerZelfdeTypeUppercased(huisnummertoevoeging)));
        return persistentPersonen;
    }

    @Override
    public PersoonModel haalPersoonOpMetBurgerservicenummer(final Burgerservicenummer bsn) {
        StringBuilder query = new StringBuilder(SELECT_PERSOON);
        query.append("WHERE persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer");

        PersoonModel persoon = haalPersoonOpViaQuery(query.toString(),
                new QueryParameter("persoon.identificatieNummers.burgerServiceNummer",
                        "burgerservicenummer", bsn));
        return persoon;
    }

    @Override
    public PersoonModel opslaanNieuwPersoon(final PersoonModel persoon, final ActieModel actie,
            final Datum datumAanvangGeldigheid, final DatumTijd tijdstipRegistratie)
    {
        // Controlleer of de persoon niet al bestaat op basis van de BSN nummer
        if (!isBSNAlIngebruik(persoon.getIdentificatieNummers().getBurgerServiceNummer())) {
            // Persisteer de persoon eerst in de A-laag, persisteer de C/D lagen en update dan de A-loaag
                        // Persisteer samen gestelde naam indien deze afgeleid en aanwezig is.
            em.persist(persoon);
            em.flush();
            werkHistorieBij(persoon, actie, datumAanvangGeldigheid);

            em.merge(persoon);
            return persoon;
        } else {
            // Persoon bestaat al
            final String bsn = persoon.getIdentificatieNummers().getBurgerServiceNummer().getWaarde();
            LOGGER.error("Persoon bestaat al met de bsn: {}", bsn);
            throw new ObjectReedsBestaandExceptie(ObjectReedsBestaandExceptie.ReferentieVeld.BSN, bsn, null);
        }
    }

    /**
      * Werk de C-laag bij voor PersistentPersoon. Let op, deze functie is geschikt alleen bij het aanmaken van een
      * nieuw (!) persoon. In andere gevallen, moeten we eigenlijk nog uitzoeken (als het groep NIET is gewijzigd, of er
      * een nieuwe historie record aangemaakt moet worden !!).
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

        if (persoon.getBijhoudenGemeente() != null) {
            bijhoudingsGemeenteHistorieRepository.persisteerHistorie(persoon, actie,
                    datumAanvangGeldigheid, null);
        }
        if (persoon.getBijhoudingVerantwoordelijke() != null) {
            persoonBijhoudingsVerantwoordelijkheidHistorieRepository.persisteerHistorie(persoon, actie,
                    datumAanvangGeldigheid, null);
        }
        if (persoon.getGeboorte() != null) {
            persoonGeboorteHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }
        if (persoon.getGeslachtsAanduiding() != null) {
            persoonGeslachtsaanduidingHistorieRepository.persisteerHistorie(persoon, actie,
                    datumAanvangGeldigheid, null);
        }
        if (persoon.getIdentificatieNummers() != null) {
            persoonIdentificatienummersHistorieRepository.persisteerHistorie(persoon, actie,
                    datumAanvangGeldigheid, null);
        }
        if (persoon.getInschrijving() != null) {
            persoonInschrijvingHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }
        if (persoon.getOpschorting() != null) {
            persoonOpschortingHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }
        if (persoon.getOverlijden() != null) {
            persoonOverlijdenHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }
        if (persoon.getSamengesteldeNaam() != null) {
            persoonSamengesteldeNaamHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }
        if (persoon.getEUVerkiezingen() != null) {
            persoonEUVerkiezingenHistorieRepository.persisteerHistorie(persoon, actie, datumAanvangGeldigheid, null);
        }

//
//        persoon.getImmigratie();
//        persoon.getPersoonsKaart();
//        persoon.getUitsluitingNLKiesrecht();
//        persoon.getVerblijfsrecht();


        // Sla voornamen historie op in de C-laag
        for (PersoonVoornaamModel voornaam : persoon.getPersoonVoornaam()) {
            persoonVoornaamHistorieRepository.persisteerHistorie(voornaam, actie, datumAanvangGeldigheid, null);
        }

        // Sla geslachtsnaamcomponenten historie op in de C-laag
        for (PersoonGeslachtsnaamComponentModel geslachtsnaamcomponent : persoon.getGeslachtsnaamcomponenten()) {
            persoonGeslachtsnaamcomponentHistorieRepository.persisteerHistorie(geslachtsnaamcomponent,
                actie, datumAanvangGeldigheid, null);
        }

        // Sla adressen op in de C-laag
        for (PersoonAdresModel persoonAdres : persoon.getAdressen()) {
            persoonAdresHistorieRepository.persisteerHistorie(persoonAdres, actie, datumAanvangGeldigheid, null);
        }

        // Sla Nationaliteit historie op in de C-laag
        // Hoeft niet meer hier te gebeuren. wordt al afgehandeld in de Persoon Nationaliteit Repository.
        // Let op: de PersoonGeslachtsnaamComponenten, en Persoonvoornamen worden WEL bij deze persoon opgeslagen.
        // maar voor als deze toch meegeleverd is, dan moeten we wel de historie bijhouden, omdat deze ook in de
        // persoonModel.getNationaliteiten() inzit.
        for (PersoonNationaliteitModel nationaliteit : persoon.getNationaliteiten()) {
            persoonNationaliteitStandaardMdlHistorieRepository.persisteerHistorie(nationaliteit,  actie,
                datumAanvangGeldigheid, null);
        }

    }


    @Override
    public void werkbijBijhoudingsGemeente(final Burgerservicenummer bsn,
            final PersoonBijhoudingsGemeenteGroep bijhoudingsGemeente, final ActieModel actie,
            final Datum datumAanvangGeldigheid, final DatumTijd tijdstipRegistratie)
    {
        // haal eerst de persoon op, we krijgen hier een bsn nummer.
        // Dan schrijf de bijhoudingsgemeente op in de C-laag, dan update de A-laag
        // maar de history repository support nog geen 'aparte' groepen om te updaten.
        //  ==> eerst de A, dan de C
        PersoonModel bestaandePersoon = findByBurgerservicenummer(bsn);
        if (null == bestaandePersoon) {
            LOGGER.error("Kan BSN niet vinden "  + bsn.getWaarde());
            throw new IllegalArgumentException("Persoon niet bestaand");
        }
        bestaandePersoon.vervangGroepen(bijhoudingsGemeente);
        em.merge(bestaandePersoon);
        bijhoudingsGemeenteHistorieRepository.persisteerHistorie(bestaandePersoon, actie,
                datumAanvangGeldigheid, null);
    }


}
