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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.repository.PersoonMdlRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummerAanduiding;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersoonMdl} class en standaard implementatie van de {@link PersoonMdlRepository} class.
 */
@Repository
public class PersoonMdlJpaRepository implements PersoonMdlRepository {

    private static final Logger LOGGER            = LoggerFactory.getLogger(PersoonMdlJpaRepository.class);

    private static final String SELECT_PERSOON    = " SELECT distinct persoon FROM PersoonMdl persoon ";
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
    public PersoonMdl findByBurgerservicenummer(final Burgerservicenummer bsn) {
        TypedQuery<PersoonMdl> tQuery =
            em.createQuery(
                    "SELECT persoon FROM PersoonMdl persoon"
                    + " WHERE identificatieNummers.burgerServiceNummer = :burgerservicenummer",
                    PersoonMdl.class);
        tQuery.setParameter("burgerservicenummer", bsn);

        PersoonMdl persoon;
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
    private PersoonMdl haalPersoonOpViaQuery(final String query, final QueryParameter... parameters) {
        TypedQuery<PersoonMdl> tQuery = em.createQuery(query.toString(), PersoonMdl.class);
        if (parameters != null) {
            for (QueryParameter param : parameters) {
                tQuery.setParameter(param.paramNaam, param.waarde);
            }
        }
        PersoonMdl persoon;
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
    private List<PersoonMdl> haalPersonenOpViaQuery(final String query, final QueryParameter... parameters) {
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

        TypedQuery<PersoonMdl> tQuery = em.createQuery(uitTeVoerenQuery.toString(), PersoonMdl.class);

        if (teGebruikenParameters.size() > 0) {
            for (QueryParameter param : teGebruikenParameters) {
                tQuery.setParameter(param.paramNaam, param.waarde);
            }
        }
        List<PersoonMdl> personen;
        try {
            personen = tQuery.getResultList();
        } catch (NoResultException e) {
            personen = new ArrayList<PersoonMdl>();
        }
        return personen;

    }

    @Override
    public PersoonMdl haalPersoonMetAdres(final Long persId) {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES);
        query.append("WHERE persoon.id = :persId ");
        PersoonMdl persistentPersoon = haalPersoonOpViaQuery(query.toString(),
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
    public List<PersoonMdl> haalPersonenMetWoonAdresOpViaBurgerservicenummer(final Burgerservicenummer bsn) {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES);
        query.append("WHERE persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer ");
        query.append("AND adres.gegevens.soort = 1");

        List<PersoonMdl> persistentPersonen =
            haalPersonenOpViaQuery(query.toString(),
                    new QueryParameter("persoon.identificatieNummers.burgerServiceNummer.waarde",
                            "burgerservicenummer", bsn));

        return persistentPersonen;
    }

    @Override
    public List<PersoonMdl> haalPersonenMetWoonAdresOpViaVolledigAdres(final NaamOpenbareRuimte naamOpenbareRuimte,
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

        List<PersoonMdl> persistentPersonen = haalPersonenOpViaQuery(query.toString(),
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
    public List<PersoonMdl> haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
            final IdentificatiecodeNummerAanduiding identificatiecodeNummeraanduiding)
    {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES);
        query.append("WHERE adres.gegevens.identificatiecodeNummeraanduiding = :idCodeNumaanduiding ");
        query.append("AND adres.gegevens.soort = 1");


        List<PersoonMdl> persistentPersonen =
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
    public List<PersoonMdl> haalPersonenOpMetAdresViaPostcodeHuisnummer(final Postcode postcode,
            final Huisnummer huisnummer, final Huisletter huisletter,
            final Huisnummertoevoeging huisnummertoevoeging)
    {
        StringBuilder query = new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES);
        query.append("WHERE (upper(adres.gegevens.postcode) = :postcode ) ");
        query.append("AND (upper(adres.gegevens.huisnummer) = :huisnummer ) ");
        query.append("AND (upper(adres.gegevens.huisletter) = :huisletter ) ");
        query.append("AND (upper(adres.gegevens.huisnummertoevoeging) = :huisnummertoevoeging ) ");
        query.append("AND adres.gegevens.soort = 1");

        List<PersoonMdl> persistentPersonen =
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
    public PersoonMdl haalPersoonOpMetBurgerservicenummer(final Burgerservicenummer bsn) {
        StringBuilder query = new StringBuilder(SELECT_PERSOON);
        query.append("WHERE persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer");

        PersoonMdl persoon = haalPersoonOpViaQuery(query.toString(),
                new QueryParameter("persoon.identificatieNummers.burgerServiceNummer",
                        "burgerservicenummer", bsn));
        return persoon;
    }

}
