/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.converter.PersoonConverter;
import nl.bzk.brp.dataaccess.converter.PersoonConverterConfiguratie;
import nl.bzk.brp.dataaccess.converter.PersoonConverterGroep;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonGeslachtsnaamcomponentHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonVoornaamHistorieRepository;
import nl.bzk.brp.dataaccess.repository.jpa.historie.GroepHistorieRepository;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.Verantwoordelijke;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsGemeente;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeNaam;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsVerantwoordelijkheid;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorte;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduiding;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijving;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonVoornaam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersistentPersoon} class en standaard implementatie van de {@link PersoonRepository} class.
 */
@Repository
public class PersoonJpaRepository implements PersoonRepository {

    private static final Logger LOGGER            = LoggerFactory.getLogger(PersoonJpaRepository.class);

    private static final String SELECT_PERSOON    = " SELECT distinct persoon FROM PersistentPersoon persoon ";
    private static final String JOIN_NATIONALIEIT = " LEFT JOIN FETCH persoon.nationaliteiten ";
    private static final String JOIN_INDICATIE    = " LEFT JOIN FETCH persoon.persoonIndicaties ";
    private static final String JOIN_ADRES        = " LEFT JOIN FETCH persoon.adressen as adres ";
    private static final String JOIN_GESLACHTNAAM = " LEFT JOIN FETCH persoon.persoonGeslachtsnaamcomponenten ";
    private static final String JOIN_VOORNAAM     = " LEFT JOIN FETCH persoon.persoonVoornamen ";
    private static final String JOIN_BETROKKENE   = " LEFT JOIN FETCH persoon.betrokkenheden ";

    /**
     * Een pure shortcut om 2 objecten bij elkaar te houden.
     */
    private static final class NameValue {

        private final String naam;
        private final Object waarde;

        /**
         * Een pure shortcut om 2 objecten bij elkaar te houden.
         *
         * @param naam de naam
         * @param waarde de waarde
         */
        private NameValue(final String naam, final Object waarde) {
            this.naam = naam;
            this.waarde = waarde;
        }
    }

    @PersistenceContext
    private EntityManager                                   em;

    @Inject
    private ReferentieDataRepository                        referentieDataRepository;

    @Inject
    private PersoonVoornaamHistorieRepository               persoonVoornaamHistorieRepository;

    @Inject
    private GroepHistorieRepository                         persoonSamengesteldeNaamHistorieRepository;

    @Inject
    private GroepHistorieRepository                         bijhoudingsGemeenteHistorieRepository;

    @Inject
    private PersoonGeslachtsnaamcomponentHistorieRepository persoonGeslachtsnaamcomponentHistorieRepository;

    @Inject
    private PersoonAdresHistorieRepository                  persoonAdresHistorieRepository;

    @Override
    public PersistentPersoon findByBurgerservicenummer(final String bsn) {
        TypedQuery<PersistentPersoon> tQuery =
            em.createQuery(
                    "SELECT persoon FROM PersistentPersoon persoon WHERE  burgerservicenummer = :burgerservicenummer",
                    PersistentPersoon.class);
        tQuery.setParameter("burgerservicenummer", bsn);

        PersistentPersoon persoon;
        try {
            persoon = tQuery.getSingleResult();
        } catch (NoResultException e) {
            persoon = null;
        }

        return persoon;
    }

    /**
     * Bouw een query op om een simpel persoon met adres op te halen.
     * Deze persoon heeft adressen en indicaties.
     * Dit wordt in allerlei queries gebruikt oa. voor opzoeken ven personen op adres.
     *
     * @return de opgebouwde query
     */
    private String bouwQueryVoorBasisPersoonMetAdres() {
        return new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES).append(JOIN_INDICATIE).toString();
    }

    /**
     * Bouw een query op om een persoon (voor referentie) met betrokkenen + adres op te halen.
     * Deze persoon heeft betrokkenen, adressen en indicaties.
     * Dit wordt in allerlei queries gebruikt oa. voor opzoeken ven personen op adres.
     *
     * @return de opgebouwde query
     */
    private String bouwQueryVoorPersoonMetBetrokkenenEnAdres() {
        return new StringBuilder(SELECT_PERSOON).append(JOIN_ADRES).append(JOIN_INDICATIE).append(JOIN_BETROKKENE)
                .toString();
    }

    /**
     * Bouw een query op om een volledig persoon op te halen.
     * Deze persoon heeft ook geslachtnaam, voornamen, betrokkenen, nationaliteiten, indicaties, adressen.
     *
     * @return de opgebouwde text, letop alle where, order by, ... moeten nog toegevoegd worden.
     */
    private String bouwQueryVoorVolledigPersoon() {
        return new StringBuilder(SELECT_PERSOON).append(JOIN_GESLACHTNAAM).append(JOIN_VOORNAAM).append(JOIN_ADRES)
                .append(JOIN_INDICATIE).append(JOIN_NATIONALIEIT).append(JOIN_BETROKKENE).toString();
    }

    /**
     * Voer een query uit en return de gevonden persoon. Deze query geeft een null als er meerdere gevonden
     * worden.
     *
     * @param query de uit te voeren query als text.
     * @param parameters variabele pameters (combinatie van naam, waarde)
     * @return de persistent persoon of null als het niet (of MEERDERE) gevonden wordt.
     */
    private PersistentPersoon haalPersoonOpViaQuery(final String query, final NameValue... parameters) {
        TypedQuery<PersistentPersoon> tQuery = em.createQuery(query.toString(), PersistentPersoon.class);
        if (parameters != null) {
            for (NameValue param : parameters) {
                tQuery.setParameter(param.naam, param.waarde);
            }
        }
        PersistentPersoon persoon;
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
    private List<PersistentPersoon> haalPersonenOpViaQuery(final String query, final NameValue... parameters) {
        // Vertaal "= null" naar "is null"
        String uitTeVoerenQuery = query;
        List<NameValue> teGebruikenParameters = new ArrayList<NameValue>();
        for (NameValue parameter : parameters) {
            if (parameter.waarde == null) {
                String paramNaam = parameter.naam;
                uitTeVoerenQuery = uitTeVoerenQuery.replace("= :" + paramNaam, "is null");
            } else {
                teGebruikenParameters.add(parameter);
            }
        }

        TypedQuery<PersistentPersoon> tQuery = em.createQuery(uitTeVoerenQuery.toString(), PersistentPersoon.class);

        if (teGebruikenParameters.size() > 0) {
            for (NameValue param : teGebruikenParameters) {
                tQuery.setParameter(param.naam, param.waarde);
            }
        }
        List<PersistentPersoon> personen;
        try {
            personen = tQuery.getResultList();
        } catch (NoResultException e) {
            personen = new ArrayList<PersistentPersoon>();
        }
        return personen;

    }

    @Override
    public Persoon haalPersoonMetAdres(final Long persId) {
        StringBuilder query = new StringBuilder(bouwQueryVoorBasisPersoonMetAdres());
        query.append("WHERE persoon.id = :persId ");
        PersoonConverterConfiguratie configuratie =
            new PersoonConverterConfiguratie(PersoonConverterGroep.IDENTIFICATIE_NUMMERS,
                    PersoonConverterGroep.GESLACHTS_AANDUIDING, PersoonConverterGroep.SAMENGESTELDE_NAAM,
                    PersoonConverterGroep.GEBOORTE, PersoonConverterGroep.AFGELEIDE_ADMINISTRATIE,
                    PersoonConverterGroep.ADRESSEN, PersoonConverterGroep.INDICATIES);
        PersistentPersoon persistentPersoon = haalPersoonOpViaQuery(query.toString(), new NameValue("persId", persId));
        if (persistentPersoon != null) {
            return PersoonConverter.converteerOperationeelNaarLogisch(persistentPersoon, true, configuratie);
        } else {
            return null;
        }
    }

    /**
     * Zoek een (of meerdere) personen met zijn adres adv.ijn bsn nummer. We verwachten een als resultaat,
     * maar kan ook mmerdere in exceptionele gevallen.
     *
     * @param bsn de bsn
     * @return een lijst met logische personen (of lege lijst)
     */
    @Override
    public List<Persoon> haalPersonenMetWoonAdresOpViaBurgerservicenummer(final String bsn) {
        StringBuilder query = new StringBuilder(bouwQueryVoorPersoonMetBetrokkenenEnAdres());
        query.append("WHERE persoon.burgerservicenummer = :burgerservicenummer ");
        query.append("AND adres.soort = 1");
        List<Persoon> personen = new ArrayList<Persoon>();
        PersoonConverterConfiguratie configuratie =
            new PersoonConverterConfiguratie(PersoonConverterGroep.IDENTIFICATIE_NUMMERS,
                    PersoonConverterGroep.GESLACHTS_AANDUIDING, PersoonConverterGroep.SAMENGESTELDE_NAAM,
                    PersoonConverterGroep.GEBOORTE, PersoonConverterGroep.AFGELEIDE_ADMINISTRATIE,
                    PersoonConverterGroep.BETROKKENHEDEN, PersoonConverterGroep.ADRESSEN,
                    PersoonConverterGroep.INDICATIES);
        List<PersistentPersoon> persistentPersonen =
            haalPersonenOpViaQuery(query.toString(), new NameValue("burgerservicenummer", bsn));

        for (PersistentPersoon pp : persistentPersonen) {
            // Voor deze vraag moeten de groepen in de betrokkenheden worden gelimiteerd.
            personen.add(PersoonConverter.converteerOperationeelNaarLogisch(pp, true, configuratie, true));
        }
        return personen;
    }

    @Override
    public List<Persoon> haalPersonenMetWoonAdresOpViaVolledigAdres(final String naamOpenbareRuimte,
            final String huisnummer, final String huisletter, final String huisnummertoevoeging,
            final String locatieOmschrijving, final String locatietovAdres, final Plaats woonplaats)
    {
        StringBuilder query = new StringBuilder(bouwQueryVoorPersoonMetBetrokkenenEnAdres());
        query.append("WHERE upper(adres.naamOpenbareRuimte) = :naamOpenbareRuimte ");
        query.append("AND adres.huisnummer = :huisnummer ");
        query.append("AND upper(adres.huisletter) = :huisletter ");
        query.append("AND upper(adres.huisnummertoevoeging) = :huisnummertoevoeging ");
        query.append("AND upper(adres.locatieOmschrijving) = :locatieOmschrijving ");
        query.append("AND upper(adres.locatietovAdres) = :locatietovAdres ");
        query.append("AND adres.woonplaats = :woonplaats ");
        query.append("AND adres.soort = 1");
        List<Persoon> personen = new ArrayList<Persoon>();

        PersoonConverterConfiguratie configuratie =
            new PersoonConverterConfiguratie(PersoonConverterGroep.IDENTIFICATIE_NUMMERS,
                    PersoonConverterGroep.GESLACHTS_AANDUIDING, PersoonConverterGroep.SAMENGESTELDE_NAAM,
                    PersoonConverterGroep.GEBOORTE, PersoonConverterGroep.AFGELEIDE_ADMINISTRATIE,
                    PersoonConverterGroep.BETROKKENHEDEN, PersoonConverterGroep.ADRESSEN,
                    PersoonConverterGroep.INDICATIES);

        List<PersistentPersoon> persistentPersonen = haalPersonenOpViaQuery(query.toString(),
            new NameValue("naamOpenbareRuimte", StringUtils.upperCase(naamOpenbareRuimte)),
            new NameValue("huisnummer", huisnummer),
            new NameValue("huisletter", StringUtils.upperCase(huisletter)),
            new NameValue("huisnummertoevoeging", StringUtils.upperCase(huisnummertoevoeging)),
            new NameValue("locatieOmschrijving", StringUtils.upperCase(locatieOmschrijving)),
            new NameValue("locatietovAdres", StringUtils.upperCase(locatietovAdres)),
            new NameValue("woonplaats", woonplaats));

        for (PersistentPersoon pp : persistentPersonen) {
            // Voor deze vraag moeten de groepen in de betrokkenheden worden gelimiteerd.
            personen.add(PersoonConverter.converteerOperationeelNaarLogisch(pp, true, configuratie, true));
        }
        return personen;
    }

    @Override
    public List<Persoon> haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
            final String identificatiecodeNummeraanduiding)
    {
        StringBuilder query = new StringBuilder(bouwQueryVoorPersoonMetBetrokkenenEnAdres());
        query.append("WHERE adres.identificatiecodeNummeraanduiding = :idCodeNumaanduiding ");
        query.append("AND adres.soort = 1");

        List<Persoon> personen = new ArrayList<Persoon>();

        PersoonConverterConfiguratie configuratie =
            new PersoonConverterConfiguratie(PersoonConverterGroep.IDENTIFICATIE_NUMMERS,
                    PersoonConverterGroep.GESLACHTS_AANDUIDING, PersoonConverterGroep.SAMENGESTELDE_NAAM,
                    PersoonConverterGroep.GEBOORTE, PersoonConverterGroep.AFGELEIDE_ADMINISTRATIE,
                    PersoonConverterGroep.BETROKKENHEDEN, PersoonConverterGroep.ADRESSEN,
                    PersoonConverterGroep.INDICATIES);

        List<PersistentPersoon> persistentPersonen =
            haalPersonenOpViaQuery(query.toString(), new NameValue("idCodeNumaanduiding",
                    identificatiecodeNummeraanduiding));

        for (PersistentPersoon pp : persistentPersonen) {
            // Voor deze vraag moeten de groepen in de betrokkenheden worden gelimiteerd.
            personen.add(PersoonConverter.converteerOperationeelNaarLogisch(pp, true, configuratie, true));
        }

        return personen;
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
    public List<Persoon> haalPersonenOpMetAdresViaPostcodeHuisnummer(final String postcode, final String huisnummer,
            final String huisletter, final String huisnummertoevoeging)
    {
        StringBuilder query = new StringBuilder(bouwQueryVoorPersoonMetBetrokkenenEnAdres());
        query.append("WHERE upper(adres.postcode) = :postcode ");
        query.append("AND adres.huisnummer = :huisnummer ");
        query.append("AND upper(adres.huisletter) = :huisletter ");
        query.append("AND upper(adres.huisnummertoevoeging) = :huisnummertoevoeging ");
        query.append("AND adres.soort = 1");

        List<PersistentPersoon> persistentPersonen =
            haalPersonenOpViaQuery(query.toString(), new NameValue("postcode", StringUtils.upperCase(postcode)),
                    new NameValue("huisnummer", huisnummer),
                    new NameValue("huisletter", StringUtils.upperCase(huisletter)), new NameValue(
                            "huisnummertoevoeging", StringUtils.upperCase(huisnummertoevoeging)));

        PersoonConverterConfiguratie configuratie =
            new PersoonConverterConfiguratie(PersoonConverterGroep.IDENTIFICATIE_NUMMERS,
                    PersoonConverterGroep.GESLACHTS_AANDUIDING, PersoonConverterGroep.SAMENGESTELDE_NAAM,
                    PersoonConverterGroep.GEBOORTE, PersoonConverterGroep.AFGELEIDE_ADMINISTRATIE,
                    PersoonConverterGroep.BETROKKENHEDEN, PersoonConverterGroep.ADRESSEN,
                    PersoonConverterGroep.INDICATIES);

        List<Persoon> personen = new ArrayList<Persoon>();

        for (PersistentPersoon pp : persistentPersonen) {
            // Voor deze vraag moeten de groepen in de betrokkenheden worden gelimiteerd.
            personen.add(PersoonConverter.converteerOperationeelNaarLogisch(pp, true, configuratie, true));
        }

        return personen;
    }

    @Override
    public Persoon haalPersoonOpMetBurgerservicenummer(final String bsn) {
        StringBuilder query = new StringBuilder(bouwQueryVoorVolledigPersoon());
        query.append("WHERE persoon.burgerservicenummer = :burgerservicenummer");

        PersistentPersoon persoon = haalPersoonOpViaQuery(query.toString(), new NameValue("burgerservicenummer", bsn));
        // kan ook tegen een null pointer.
        return PersoonConverter.converteerOperationeelNaarLogisch(persoon, true);
    }

    @Override
    public Long opslaanNieuwPersoon(final Persoon persoon, final Integer datumAanvangGeldigheid,
            final Date tijdstipRegistratie)
    {
        Long id;
        // Controlleer of de persoon niet al bestaat op basis van de BSN nummer
        if (!isBSNAlIngebruik(persoon.getIdentificatienummers().getBurgerservicenummer())) {
            // Opslaan in A- en C-laag
            PersistentPersoon nieuwPersoon = new PersistentPersoon();

            // Zet de Bijhoudingsverantwoordelijkheid op de standaard verantwoordelijke 'College'.
            nieuwPersoon.setVerantwoordelijke(Verantwoordelijke.COLLEGE);

            nieuwPersoon.setInschrijvingDatum(datumAanvangGeldigheid);
            // Zet versienummer standaard op 1.
            nieuwPersoon.setInschrijvingVersienummer(1L);
            nieuwPersoon.setANummer(persoon.getIdentificatienummers().getAdministratienummer());
            nieuwPersoon.setBurgerservicenummer(persoon.getIdentificatienummers().getBurgerservicenummer());
            nieuwPersoon.setSoortPersoon(persoon.getIdentiteit().getSoort());

            if (persoon.getBijhoudingGemeente() != null && persoon.getBijhoudingGemeente().getGemeente() != null
                && persoon.getBijhoudingGemeente().getDatumInschrijving() != null
                && persoon.getBijhoudingGemeente().getIndOnverwerktDocumentAanwezig() != null)
            {
                nieuwPersoon.setBijhoudingsGemeente(referentieDataRepository.vindGemeenteOpCode(persoon
                        .getBijhoudingGemeente().getGemeente().getGemeentecode()));
                nieuwPersoon.setBijhoudingsGemeenteDatumInschrijving(persoon.getBijhoudingGemeente()
                        .getDatumInschrijving());
                nieuwPersoon.setBijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig(persoon.getBijhoudingGemeente()
                        .getIndOnverwerktDocumentAanwezig());
            }

            vulAanVoornamen(persoon, nieuwPersoon);
            vulAanGeslachtsnaamComponenten(persoon, nieuwPersoon);
            vulAanGeboorte(persoon, nieuwPersoon);
            vulAanGeslachtsAanduiding(persoon, nieuwPersoon);
            vulAdresAan(persoon, nieuwPersoon);

            // Persisteer samen gestelde naam indien deze afgeleid en aanwezig is.
            if (persoon.getSamengesteldenaam() != null) {
                vulAanSamengesteldeNaam(persoon.getSamengesteldenaam(), nieuwPersoon);
                nieuwPersoon.setSamengesteldeNaamStatusHis(StatusHistorie.A);
            } else {
                nieuwPersoon.setSamengesteldeNaamStatusHis(StatusHistorie.X);
            }

            // TODO Hosing: tijdelijk hardcoded naar StatusHistory.A
            nieuwPersoon.setStatushistorie(StatusHistorie.A);
            nieuwPersoon.setAanschrijvingStatusHis(StatusHistorie.A);
            nieuwPersoon.setOverlijdenStatusHis(StatusHistorie.A);
            nieuwPersoon.setVerblijfsrechtStatusHis(StatusHistorie.A);
            nieuwPersoon.setUitsluitingNLKiesrechtStatusHis(StatusHistorie.A);
            nieuwPersoon.setEUVerkiezingenStatusHis(StatusHistorie.A);
            nieuwPersoon.setBijhoudingsverantwoordelijkheidStatusHis(StatusHistorie.A);
            nieuwPersoon.setOpschortingStatusHis(StatusHistorie.A);
            nieuwPersoon.setBijhoudingsgemeenteStatusHis(StatusHistorie.A);
            nieuwPersoon.setPersoonskaartStatusHis(StatusHistorie.A);
            nieuwPersoon.setImmigratieStatusHis(StatusHistorie.A);
            nieuwPersoon.setInschrijvingStatusHis(StatusHistorie.A);

            nieuwPersoon.setLaatstGewijzigd(tijdstipRegistratie);
            em.persist(nieuwPersoon);

            werkHistorieBij(nieuwPersoon, persoon, tijdstipRegistratie, datumAanvangGeldigheid);
            id = nieuwPersoon.getId();
        } else {
            // Persoon bestaat al
            LOGGER.error("Persoon bestaat al met de bsn: {}", persoon.getIdentificatienummers()
                    .getBurgerservicenummer());
            throw new ObjectReedsBestaandExceptie(ObjectReedsBestaandExceptie.ReferentieVeld.BSN, persoon
                    .getIdentificatienummers().getBurgerservicenummer(), null);
        }
        return id;
    }

    @Override
    public void werkbijBijhoudingsGemeente(final String bsn, final PersoonBijhoudingsGemeente bijhoudingsGemeente,
            final Integer datumAanvangGeldigheid, final Date tijdstipRegistratie)
    {
        StringBuilder query = new StringBuilder(bouwQueryVoorVolledigPersoon());
        query.append("WHERE persoon.burgerservicenummer = :burgerservicenummer");

        PersistentPersoon persoon = haalPersoonOpViaQuery(query.toString(), new NameValue("burgerservicenummer", bsn));

        persoon.setBijhoudingsGemeente(referentieDataRepository.vindGemeenteOpCode(bijhoudingsGemeente.getGemeente()
                .getGemeentecode()));
        persoon.setBijhoudingsGemeenteDatumInschrijving(bijhoudingsGemeente.getDatumInschrijving());
        persoon.setBijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig(bijhoudingsGemeente
                .getIndOnverwerktDocumentAanwezig());

        em.merge(persoon);

        bijhoudingsGemeenteHistorieRepository.persisteerHistorie(persoon, datumAanvangGeldigheid, null,
                tijdstipRegistratie);
    }

    /**
     * Vult de samengestelde naam groep in hoofdtabel persoon.
     *
     * @param samengesteldenaam De samengestelde naam groep die de gegevens bevat die in nieuwPersoon moeten komen.
     * @param nieuwPersoon De persoon die aangemaakt wordt.
     */
    private void vulAanSamengesteldeNaam(final PersoonSamengesteldeNaam samengesteldenaam,
            final PersistentPersoon nieuwPersoon)
    {
        nieuwPersoon.setVoornaam(samengesteldenaam.getVoornamen());
        nieuwPersoon.setPredikaat(samengesteldenaam.getPredikaat());
        nieuwPersoon.setAdellijkeTitel(samengesteldenaam.getAdellijkeTitel());
        nieuwPersoon.setVoorvoegsel(samengesteldenaam.getVoorvoegsel());
        nieuwPersoon.setScheidingsTeken(samengesteldenaam.getScheidingsTeken());
        nieuwPersoon.setGeslachtsNaam(samengesteldenaam.getGeslachtsnaam());
        nieuwPersoon.setIndAlgoritmischAfgeleid(samengesteldenaam.getIndAlgoritmischAfgeleid());
        nieuwPersoon.setIndReeksAlsGeslachtnaam(samengesteldenaam.getIndNamenreeksAlsGeslachtsnaam());
    }

    /**
     * Deze methode zorgt ervoor dat de voornamen gevuld zijn voor het persisteren.
     *
     * @param persoon {@link Persoon} uit het logische model
     * @param persistentPersoon {@link PersistentPersoon} uit het operationeel model
     */
    private void vulAanVoornamen(final Persoon persoon, final PersistentPersoon persistentPersoon) {
        if (CollectionUtils.isNotEmpty(persoon.getPersoonVoornamen())) {
            for (PersoonVoornaam persoonVoornaam : persoon.getPersoonVoornamen()) {
                // Sla op in A-laag
                PersistentPersoonVoornaam persistentPersoonVoornaam = new PersistentPersoonVoornaam();
                persistentPersoonVoornaam.setPersoon(persistentPersoon);
                persistentPersoonVoornaam.setNaam(persoonVoornaam.getNaam());
                persistentPersoonVoornaam.setVolgnummer(persoonVoornaam.getVolgnummer());
                // TODO Hosing: tijdelijk hardcoded naar StatusHistory.A
                persistentPersoonVoornaam.setPersoonVoornaamStatusHis(StatusHistorie.A);
                persistentPersoon.voegPersoonVoornaamToe(persistentPersoonVoornaam);
            }
        }
    }

    /**
     * Deze methode zorgt ervoor dat de geslachtsnamen gevuld zijn voor het persisteren.
     *
     * @param persoon {@link Persoon} uit het logische model
     * @param persistentPersoon {@link PersistentPersoon} uit het operationeel model
     */
    private void vulAanGeslachtsnaamComponenten(final Persoon persoon, final PersistentPersoon persistentPersoon) {
        if (CollectionUtils.isNotEmpty(persoon.getGeslachtsnaamcomponenten())) {
            for (PersoonGeslachtsnaamcomponent persoonGeslachtsnaam : persoon.getGeslachtsnaamcomponenten()) {
                // Sla op in A-laag
                PersistentPersoonGeslachtsnaamcomponent persistentGeslachtsnaam =
                    new PersistentPersoonGeslachtsnaamcomponent();
                persistentGeslachtsnaam.setPersoon(persistentPersoon);
                persistentGeslachtsnaam.setNaam(persoonGeslachtsnaam.getNaam());
                persistentGeslachtsnaam.setVolgnummer(persoonGeslachtsnaam.getVolgnummer());
                persistentGeslachtsnaam.setVoorvoegsel(persoonGeslachtsnaam.getVoorvoegsel());

                persistentGeslachtsnaam.setScheidingsteken(persoonGeslachtsnaam.getScheidingsTeken());
                persistentGeslachtsnaam.setPredikaat(persoonGeslachtsnaam.getPredikaat());
                persistentGeslachtsnaam.setAdellijkeTitel(persoonGeslachtsnaam.getAdellijkeTitel());

                // TODO Hosing: tijdelijk hardcoded naar StatusHistory.A
                persistentGeslachtsnaam.setPersoonGeslachtsnaamcomponentStatusHis(StatusHistorie.A);

                persistentPersoon.voegPersoonGeslachtsnaamcomponentenToe(persistentGeslachtsnaam);
            }
        }

    }

    /**
     * Deze methode zorgt ervoor dat geboorte gegevens gevuld zijn voor het persisteren.
     *
     * @param persoon {@link Persoon} uit het logische model
     * @param persistentPersoon {@link PersistentPersoon} uit het operationeel model
     */
    private void vulAanGeboorte(final Persoon persoon, final PersistentPersoon persistentPersoon) {
        final PersoonGeboorte geboorte = persoon.getGeboorte();
        if (geboorte != null) {
            // Sla op in A-laag
            persistentPersoon.setDatumGeboorte(geboorte.getDatumGeboorte());
            if (geboorte.getGemeenteGeboorte() != null
                && StringUtils.isNotBlank(geboorte.getGemeenteGeboorte().getGemeentecode()))
            {

                persistentPersoon.setGemeenteGeboorte(referentieDataRepository.vindGemeenteOpCode(geboorte
                        .getGemeenteGeboorte().getGemeentecode()));
            }

            if (geboorte.getLandGeboorte() != null && StringUtils.isNotBlank(geboorte.getLandGeboorte().getLandcode()))
            {

                persistentPersoon.setLandGeboorte(referentieDataRepository.vindLandOpCode(geboorte.getLandGeboorte()
                        .getLandcode()));
            }

            if (geboorte.getWoonplaatsGeboorte() != null
                && StringUtils.isNotBlank(geboorte.getWoonplaatsGeboorte().getWoonplaatscode()))
            {

                persistentPersoon.setWoonplaatsGeboorte(referentieDataRepository.findWoonplaatsOpCode(geboorte
                        .getWoonplaatsGeboorte().getWoonplaatscode()));
            }
        }
        // TODO Hosing: tijdelijk hardcoded naar StatusHistory.A
        persistentPersoon.setGeboorteStatusHis(StatusHistorie.A);
    }

    /**
     * Deze methode zorgt ervoor dat geboorte gegevens gevuld zijn voor het persisteren.
     *
     * @param persoon {@link Persoon} uit het logische model
     * @param persistentPersoon {@link PersistentPersoon} uit het operationeel model
     */
    private void vulAanGeslachtsAanduiding(final Persoon persoon, final PersistentPersoon persistentPersoon) {
        // Sla op in A-laag
        persistentPersoon.setGeslachtsAanduiding(persoon.getPersoonGeslachtsAanduiding().getGeslachtsAanduiding());
        // TODO Hosing: tijdelijk hardcoded naar StatusHistory.A
        persistentPersoon.setGeslachtsaanduidingStatusHis(StatusHistorie.A);
    }

    /**
     * Deze methode zorgt ervoor dat de adresgegevens gevuld zijn voor het persisteren.
     *
     * @param persoon {@link Persoon} uit het logische model
     * @param persistentPersoon {@link PersistentPersoon} uit het operationeel model
     */
    private void vulAdresAan(final Persoon persoon, final PersistentPersoon persistentPersoon) {
        if (CollectionUtils.isNotEmpty(persoon.getAdressen())) {
            Set<PersistentPersoonAdres> adressen = new HashSet<PersistentPersoonAdres>();
            for (PersoonAdres persoonAdres : persoon.getAdressen()) {
                PersistentPersoonAdres persistentPersoonAdres = new PersistentPersoonAdres();
                persistentPersoonAdres.setPersoon(persistentPersoon);
                persistentPersoonAdres.setSoort(persoonAdres.getSoort());
                persistentPersoonAdres.setRedenWijziging(persoonAdres.getRedenWijziging());
                persistentPersoonAdres.setAangeverAdreshouding(persoonAdres.getAangeverAdreshouding());
                persistentPersoonAdres.setDatumAanvangAdreshouding(persoonAdres.getDatumAanvangAdreshouding());
                persistentPersoonAdres.setAdresseerbaarObject(persoonAdres.getAdresseerbaarObject());
                persistentPersoonAdres.setIdentificatiecodeNummeraanduiding(persoonAdres
                        .getIdentificatiecodeNummeraanduiding());
                persistentPersoonAdres.setGemeente(persoonAdres.getGemeente());
                persistentPersoonAdres.setNaamOpenbareRuimte(persoonAdres.getNaamOpenbareRuimte());
                persistentPersoonAdres.setAfgekorteNaamOpenbareRuimte(persoonAdres.getAfgekorteNaamOpenbareRuimte());
                persistentPersoonAdres.setLocatieOmschrijving(persoonAdres.getLocatieOmschrijving());
                persistentPersoonAdres.setLocatietovAdres(persoonAdres.getLocatieTovAdres());
                persistentPersoonAdres.setGemeentedeel(persoonAdres.getGemeentedeel());
                persistentPersoonAdres.setHuisnummer(persoonAdres.getHuisnummer());
                persistentPersoonAdres.setHuisletter(persoonAdres.getHuisletter());
                persistentPersoonAdres.setHuisnummertoevoeging(persoonAdres.getHuisnummertoevoeging());
                persistentPersoonAdres.setPostcode(persoonAdres.getPostcode());
                persistentPersoonAdres.setWoonplaats(persoonAdres.getWoonplaats());
                persistentPersoonAdres.setLand(persoonAdres.getLand());
                persistentPersoonAdres.setPersoonAdresStatusHis(persoonAdres.getPersoonAdresStatusHis());
                persistentPersoonAdres.setBuitenlandsAdresRegel1(persoonAdres.getBuitenlandsAdresRegel1());
                persistentPersoonAdres.setBuitenlandsAdresRegel2(persoonAdres.getBuitenlandsAdresRegel2());
                persistentPersoonAdres.setBuitenlandsAdresRegel3(persoonAdres.getBuitenlandsAdresRegel3());
                persistentPersoonAdres.setBuitenlandsAdresRegel4(persoonAdres.getBuitenlandsAdresRegel4());
                persistentPersoonAdres.setBuitenlandsAdresRegel5(persoonAdres.getBuitenlandsAdresRegel5());
                persistentPersoonAdres.setBuitenlandsAdresRegel6(persoonAdres.getBuitenlandsAdresRegel6());
                persistentPersoonAdres.setDatumVertrekUitNederland(persoonAdres.getDatumVertrekUitNederland());
                adressen.add(persistentPersoonAdres);
            }
            persistentPersoon.setAdressen(adressen);
        }
    }

    /**
     * Werk de C-laag bij voor PersistentPersoon.
     *
     * @param ppersoon De zojuist toegevoegde persoon.
     * @param persoon Oorspronkelijke persoon uit de business laag.
     * @param datumTijdRegistratie tijdstip van registratie
     * @param datumAanvangGeldigheid datum van aanvang geldigheid
     */
    private void werkHistorieBij(final PersistentPersoon ppersoon, final Persoon persoon,
            final Date datumTijdRegistratie, final Integer datumAanvangGeldigheid)
    {
        // Sla voornamen historie op in de C-laag
        for (PersistentPersoonVoornaam voornaam : ppersoon.getPersoonVoornamen()) {
            persoonVoornaamHistorieRepository.opslaanHistorie(voornaam, datumAanvangGeldigheid, null,
                    datumTijdRegistratie);
        }

        // Sla geslachtsnaamcomponenten historie op in de C-laag
        for (PersistentPersoonGeslachtsnaamcomponent geslachtsnaamcomponent : ppersoon
                .getPersoonGeslachtsnaamcomponenten())
        {
            persoonGeslachtsnaamcomponentHistorieRepository.opslaanHistorie(geslachtsnaamcomponent,
                    datumAanvangGeldigheid, null, datumTijdRegistratie);
        }

        // Historie Samengestelde naam
        if (persoon.getSamengesteldenaam() != null) {
            persoonSamengesteldeNaamHistorieRepository.persisteerHistorie(ppersoon, datumAanvangGeldigheid, null,
                    datumTijdRegistratie);
        }

        // Sla adressen op in de C-laag
        for (PersistentPersoonAdres persoonAdres : ppersoon.getAdressen()) {
            persoonAdresHistorieRepository.opslaanHistorie(persoonAdres, datumAanvangGeldigheid, null,
                    datumTijdRegistratie);
        }

        // Sla PersoonGeboorte in historie op in de C-laag
        HisPersoonGeboorte hisPersoonGeboorte = new HisPersoonGeboorte();
        hisPersoonGeboorte.setPersoon(ppersoon);
        hisPersoonGeboorte.setDatumGeboorte(ppersoon.getDatumGeboorte());
        hisPersoonGeboorte.setDatumTijdRegistratie(datumTijdRegistratie);
        hisPersoonGeboorte.setGemeenteGeboorte(ppersoon.getGemeenteGeboorte());
        hisPersoonGeboorte.setLandGeboorte(ppersoon.getLandGeboorte());
        hisPersoonGeboorte.setWoonplaatsGeboorte(ppersoon.getWoonplaatsGeboorte());
        em.persist(hisPersoonGeboorte);

        // Sla PersoonGeslachtsaanduiging historie op in de C-laag
        HisPersoonGeslachtsaanduiding hisPersoonGeslachtsaanduiding = new HisPersoonGeslachtsaanduiding();
        hisPersoonGeslachtsaanduiding.setPersoon(ppersoon);
        hisPersoonGeslachtsaanduiding.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonGeslachtsaanduiding.setDatumEindeGeldigheid(null);
        hisPersoonGeslachtsaanduiding.setDatumTijdRegistratie(datumTijdRegistratie);
        hisPersoonGeslachtsaanduiding.setGeslachtsAanduiding(ppersoon.getGeslachtsAanduiding());
        em.persist(hisPersoonGeslachtsaanduiding);

        // Sla PersoonInschrijving in historie op in de C-laag
        HisPersoonInschrijving hisPersoonInschrijving = new HisPersoonInschrijving();
        hisPersoonInschrijving.setPersoon(ppersoon);
        hisPersoonInschrijving.setDatInschr(ppersoon.getInschrijvingDatum());
        hisPersoonInschrijving.setDatumTijdRegistratie(datumTijdRegistratie);
        hisPersoonInschrijving.setVersienr(1);
        em.persist(hisPersoonInschrijving);

        // Sla Bijhoudingsgemeente in historie op in de C-laag
        if (ppersoon.getBijhoudingsGemeente() != null && ppersoon.getBijhoudingsGemeenteDatumInschrijving() != null
            && ppersoon.getBijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig())
        {
            bijhoudingsGemeenteHistorieRepository.persisteerHistorie(ppersoon, datumAanvangGeldigheid, null,
                    datumTijdRegistratie);
        }

        // Sla BijhoudingsVerwantwoordelijkheid op in de C-laag
        HisPersoonBijhoudingsVerantwoordelijkheid hisPersoonBijhoudingsVerwantwoordelijkheid =
            new HisPersoonBijhoudingsVerantwoordelijkheid();
        hisPersoonBijhoudingsVerwantwoordelijkheid.setPersoon(ppersoon);
        hisPersoonBijhoudingsVerwantwoordelijkheid.setVerwantwoordelijke(ppersoon.getVerantwoordelijke());
        hisPersoonBijhoudingsVerwantwoordelijkheid.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonBijhoudingsVerwantwoordelijkheid.setDatumTijdRegistratie(datumTijdRegistratie);
        em.persist(hisPersoonBijhoudingsVerwantwoordelijkheid);

        // Sla PersoonIdentificatienummers op in de C-laag
        HisPersoonIdentificatienummers hisPersoonIdentificatienummers = new HisPersoonIdentificatienummers();
        hisPersoonIdentificatienummers.setPersoon(ppersoon);
        hisPersoonIdentificatienummers.setANummer(ppersoon.getANummer());
        hisPersoonIdentificatienummers.setBurgerservicenummer(ppersoon.getBurgerservicenummer());
        hisPersoonIdentificatienummers.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonIdentificatienummers.setDatumTijdRegistratie(datumTijdRegistratie);
        em.persist(hisPersoonIdentificatienummers);
    }

    @Override
    public boolean isBSNAlIngebruik(final String bsn) {
        return findByBurgerservicenummer(bsn) != null;
    }
}
