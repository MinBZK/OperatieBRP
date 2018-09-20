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
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.exceptie.NietUniekeAnummerExceptie;
import nl.bzk.brp.dataaccess.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonInformatieDto;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.Attribuut;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} class en standaard implementatie
 * van de {@link nl.bzk.brp.dataaccess.repository.PersoonRepository} class.
 */
@Repository
public final class PersoonJpaRepository implements PersoonRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String SELECT_PERSOON_ID = " SELECT distinct persoon.id FROM PersoonModel persoon ";
    private static final String JOIN_ADRES = " LEFT JOIN persoon.adressen as adres ";
    private static final int RADIX_DECIMAL = 10;
    private static final String PRIMARY_KEY_PREFIX = "db";
    private static final String BURGERSERVICENUMMER = "burgerservicenummer";
    private static final String ADRES_STANDAARD_HUISNUMMER = "adres.standaard.huisnummer";
    private static final String HUISNUMMER = "huisnummer";
    private static final String ADRES_STANDAARD_HUISLETTER = "adres.standaard.huisletter";
    private static final String HUISLETTER = "huisletter";
    private static final String ADRES_STANDAARD_HUISNUMMERTOEVOEGING = "adres.standaard.huisnummertoevoeging";
    private static final String HUISNUMMERTOEVOEGING = "huisnummertoevoeging";
    private static final String SELECT_ID_FROM_PERSOON_MODEL_PERSOON = "SELECT id FROM PersoonModel persoon";
    private static final String AND_ADRES_STANDAARD_HUISNUMMER_HUISNUMMER = "AND (adres.standaard.huisnummer = :huisnummer ) ";
    private static final String AND_UPPER_ADRES_STANDAARD_HUISLETTER_HUISLETTER = "AND (upper(adres.standaard.huisletter) = :huisletter ) ";
    private static final String AND_UPPER_ADRES_STANDAARD_HUISNUMMERTOEVOEGING_HUISNUMMERTOEVOEGING =
        "AND (upper(adres.standaard.huisnummertoevoeging) = :huisnummertoevoeging ) ";

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    /**
     * Creert een nieuw instantie van AbstractAttribuut<String>, waar de waarde wordt geuppercased
     * of op null gezet indien leeg.
     *
     * @param origineel de originele attribuut
     * @return de nieuwe attribuut.
     */
    protected Object creerZelfdeTypeUppercased(final Attribuut<String> origineel) {
        if (origineel != null && origineel.getWaarde() != null) {
            try {
                final Constructor<?> cons = origineel.getClass().getConstructor(String.class);
                String newWaarde = origineel.getWaarde();
                if (StringUtils.isNotEmpty(newWaarde)) {
                    newWaarde = newWaarde.toUpperCase();
                    return cons.newInstance(newWaarde);
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException
                    | InstantiationException e)
            {
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
    protected HuisnummerAttribuut creerZelfdeHuisnummer(final HuisnummerAttribuut origineel) {
        HuisnummerAttribuut huisnummer = null;
        if (origineel != null && origineel.getWaarde() != null) {
            huisnummer = new HuisnummerAttribuut(origineel.getWaarde());
        }
        return huisnummer;
    }

    @Override
    public boolean isBSNAlIngebruik(final BurgerservicenummerAttribuut bsn) {
        final TypedQuery<Long> tQuery = em.createQuery("SELECT COUNT(*) FROM PersoonModel persoon"
            + " WHERE identificatienummers.burgerservicenummer = :burgerservicenummer", Long.class);
        tQuery.setParameter(BURGERSERVICENUMMER, bsn);

        return tQuery.getResultList().get(0) > 0L;
    }

    @Override
    public boolean isAdministratienummerAlInGebruik(final AdministratienummerAttribuut aNummer) {
        final TypedQuery<Long> typedQuery = em.createQuery("SELECT COUNT(*) FROM PersoonModel persoon WHERE "
                                                             + "persoon.identificatienummers.administratienummer = "
                                                             + ":anummer", Long.class)
                .setParameter("anummer", aNummer);
        return typedQuery.getResultList().get(0) > 0L;
    }

    /**
     * Voer een query uit en return de gevonden persoon id's.
     *
     * @param query      de uit te voeren query als text.
     * @param parameters variabele pameters (combinatie van naam, waarde)
     * @return de persistent persoon id's of null als het niet gevonden wordt.
     */
    private List<Integer> haalPersoonIdsOpViaQuery(final String query, final QueryParameter... parameters) {
        String uitTeVoerenQuery = query;
        final List<QueryParameter> teGebruikenParameters = new ArrayList<>();
        for (final QueryParameter parameter : parameters) {
            if (parameter.waarde == null) {
                final String paramNaam = parameter.paramNaam;
                // Vertaal "= null" naar "is null"
                uitTeVoerenQuery =
                        uitTeVoerenQuery.replace("= :" + paramNaam, "is null or " + parameter.dbVeldNaam + " = '' ");
            } else {
                teGebruikenParameters.add(parameter);
            }
        }

        final TypedQuery<Integer> tQuery = em.createQuery(uitTeVoerenQuery, Integer.class);

        for (final QueryParameter param : teGebruikenParameters) {
            tQuery.setParameter(param.paramNaam, param.waarde);
        }
        return tQuery.getResultList();
    }

    @Override
    public List<Integer> haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            final NaamOpenbareRuimteAttribuut naamOpenbareRuimte, final HuisnummerAttribuut huisnummer,
            final HuisletterAttribuut huisletter,
            final HuisnummertoevoegingAttribuut huisnummertoevoeging,
            final NaamEnumeratiewaardeAttribuut woonplaatsnaam, final Gemeente gemeente)
    {
        //Om optimaal gebruik te maken van indexen wordt 'upper-case' gezocht. (Index is upper-ed).
        final String query = SELECT_PERSOON_ID
                + JOIN_ADRES
                + "WHERE (upper(adres.standaard.naamOpenbareRuimte) = :naamOpenbareRuimte ) "
                + AND_ADRES_STANDAARD_HUISNUMMER_HUISNUMMER
                + AND_UPPER_ADRES_STANDAARD_HUISLETTER_HUISLETTER
                + AND_UPPER_ADRES_STANDAARD_HUISNUMMERTOEVOEGING_HUISNUMMERTOEVOEGING
                + "AND ((adres.standaard.woonplaatsnaam = :woonplaatsnaam) OR (adres.standaard.woonplaatsnaam IS "
                + "NULL)) "
                + "AND (adres.standaard.gemeente) = :gemeente ) " + "AND (adres.standaard.soort = 1) ";

        return haalPersoonIdsOpViaQuery(
                query,
                new QueryParameter("adres.standaard.naamOpenbareRuimte", "naamOpenbareRuimte",
                                   creerZelfdeTypeUppercased(naamOpenbareRuimte)),
                new QueryParameter(ADRES_STANDAARD_HUISNUMMER, HUISNUMMER,
                                   creerZelfdeHuisnummer(huisnummer)),
                new QueryParameter(ADRES_STANDAARD_HUISLETTER, HUISLETTER,
                                   creerZelfdeTypeUppercased(huisletter)),
                new QueryParameter(ADRES_STANDAARD_HUISNUMMERTOEVOEGING, HUISNUMMERTOEVOEGING,
                                   creerZelfdeTypeUppercased(huisnummertoevoeging)),
                new QueryParameter("adres.standaard.woonplaatsnaam", "woonplaatsnaam", woonplaatsnaam),
                new QueryParameter("adres.standaard.gemeente", "gemeente", new GemeenteAttribuut(gemeente)));
    }

    @Override
    public List<Integer> haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
            final IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding)
    {
        final StringBuilder query = new StringBuilder(SELECT_PERSOON_ID).append(JOIN_ADRES);
        query.append("WHERE adres.standaard.identificatiecodeNummeraanduiding = :idCodeNumaanduiding ");
        query.append("AND adres.standaard.soort = 1");

        return haalPersoonIdsOpViaQuery(query.toString(), new QueryParameter(
                "adres.standaard.identificatiecodeNummeraanduiding", "idCodeNumaanduiding",
                identificatiecodeNummeraanduiding));
    }

    /**
     * Zoek een persoon wonende op een adres. Briefadres wordt op dezelfde manier behandeld
     * als woonadres
     *
     * @param postcode             postcode Postcode van het adres.
     * @param huisnummer           huisnummer Huisnummer van het adres.
     * @param huisletter           huisletter Huisletter van het aders.
     * @param huisnummertoevoeging huisnummertoevoeging Huisnummertoevoeging van het adres.
     * @return Lijst van gevonden personen op het adres.
     */
    @Override
    public List<Integer> haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
            final PostcodeAttribuut postcode, final HuisnummerAttribuut huisnummer,
            final HuisletterAttribuut huisletter,
            final HuisnummertoevoegingAttribuut huisnummertoevoeging)
    {
        final String query = SELECT_PERSOON_ID + JOIN_ADRES
                + "WHERE (upper(adres.standaard.postcode) = :postcode ) "
                + AND_ADRES_STANDAARD_HUISNUMMER_HUISNUMMER
                + AND_UPPER_ADRES_STANDAARD_HUISLETTER_HUISLETTER
                + AND_UPPER_ADRES_STANDAARD_HUISNUMMERTOEVOEGING_HUISNUMMERTOEVOEGING;

        return haalPersoonIdsOpViaQuery(query,
                                      new QueryParameter("adres.standaard.postcode",
                                                         "postcode",
                                                         creerZelfdeTypeUppercased(postcode)),
                                      new QueryParameter(ADRES_STANDAARD_HUISNUMMER,
                                                         HUISNUMMER,
                                                         creerZelfdeHuisnummer(huisnummer)),
                                      new QueryParameter(ADRES_STANDAARD_HUISLETTER,
                                                         HUISLETTER,
                                                         creerZelfdeTypeUppercased(huisletter)),
                                      new QueryParameter(
                                          ADRES_STANDAARD_HUISNUMMERTOEVOEGING,
                                          HUISNUMMERTOEVOEGING,
                                              creerZelfdeTypeUppercased(huisnummertoevoeging)));
    }

    @Override
    public PersoonInformatieDto haalPersoonInformatie(final String technischeSleutel) {
        PersoonInformatieDto dto = null;
        final List<Object> rows;
        if (StringUtils.isNotBlank(technischeSleutel)) {
            try {
                if (technischeSleutel.startsWith(PRIMARY_KEY_PREFIX)) {
                    // 10 == radix, string beginnend met '0' wordt geconverteerd naar octal !
                    final Integer id = Integer.parseInt(
                            technischeSleutel.substring(PRIMARY_KEY_PREFIX.length()), RADIX_DECIMAL);
                    rows = em.createNativeQuery("SELECT id, srt from kern.pers WHERE id=" + id + "").getResultList();
                } else {
                    final Integer bsn = Integer.parseInt(technischeSleutel, RADIX_DECIMAL);
                    rows = em.createNativeQuery("SELECT id, srt from kern.pers WHERE bsn=" + bsn + "")
                            .getResultList();
                }
                if (CollectionUtils.isNotEmpty(rows)) {
                    final Object[] row = (Object[]) rows.get(0);
                    dto = new PersoonInformatieDto(
                            (Integer) row[0],
                            SoortPersoon.values()[(Short) row[1]]);
                }
            } catch (final NumberFormatException e) {
                LOGGER.error("Geen correcte tecnische sleutel gegeven [" + technischeSleutel + "]");
            }
        }
        return dto;
    }

    @Override
    public Integer zoekIdBijBSN(final BurgerservicenummerAttribuut bsn) {
        final TypedQuery<Integer> tQuery = em.createQuery(SELECT_ID_FROM_PERSOON_MODEL_PERSOON
                + " WHERE persoon.identificatienummers.burgerservicenummer = :burgerservicenummer",
                Integer.class);
        tQuery.setParameter(BURGERSERVICENUMMER, bsn);

        try {
            return tQuery.getSingleResult();
        } catch (final NoResultException e) {
            return null;
        } catch (final NonUniqueResultException e) {
            final String foutMelding = "Meerdere personen gevonden met BSN: " + bsn;
            LOGGER.warn(foutMelding);
            throw new NietUniekeBsnExceptie(foutMelding, e);
        }
    }

    @Override
    public Integer zoekIdBijAnummer(final AdministratienummerAttribuut anr) {
        final TypedQuery<Integer> tQuery = em.createQuery(SELECT_ID_FROM_PERSOON_MODEL_PERSOON
                + " WHERE persoon.identificatienummers.administratienummer = :administratienummer",
                Integer.class);
        tQuery.setParameter("administratienummer", anr);

        try {
            return tQuery.getSingleResult();
        } catch (final NoResultException e) {
            return null;
        } catch (final NonUniqueResultException e) {
            final String foutMelding = "Meerdere personen gevonden met A-nummer: " + anr;
            LOGGER.warn(foutMelding);
            throw new NietUniekeAnummerExceptie(foutMelding, e);
        }
    }


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
}
