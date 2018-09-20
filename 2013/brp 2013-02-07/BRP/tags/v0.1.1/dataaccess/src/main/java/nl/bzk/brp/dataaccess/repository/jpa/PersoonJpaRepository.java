/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import nl.bzk.brp.dataaccess.repository.historie.PersoonGeslachtsnaamcomponentHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonVoornaamHistorieRepository;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorte;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduiding;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonVoornaam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersistentPersoon} class en standaard implementatie van de {@link PersoonRepository}
 * class.
 */
@Repository
public class PersoonJpaRepository implements PersoonRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonJpaRepository.class);

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
        private final  String naam;
        private final Object waarde;

        /**
         * Een pure shortcut om 2 objecten bij elkaar te houden.
         * @param naam de naam
         * @param waarde de waarde
         */
        private NameValue(final String naam, final Object waarde) {
            this.naam = naam; this.waarde = waarde;
        }
    }

    @PersistenceContext
    private EntityManager em;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Inject
    private PersoonVoornaamHistorieRepository persoonVoornaamHistorieRepository;

    @Inject
    private PersoonGeslachtsnaamcomponentHistorieRepository persoonGeslachtsnaamcomponentHistorieRepository;

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
     * @return de opgebouwde query
     */
    private String bouwQueryVoorBasisPersoonMetAdres() {
        return new StringBuilder(SELECT_PERSOON)
            .append(JOIN_ADRES)
            .append(JOIN_INDICATIE)
            .toString();
    }

    /**
     * Bouw een query op om een persoon (voor referentie) met betrokkenen + adres op te halen.
     * Deze persoon heeft betrokkenen, adressen en indicaties.
     * Dit wordt in allerlei queries gebruikt oa. voor opzoeken ven personen op adres.
     * @return de opgebouwde query
     */
    private String bouwQueryVoorPersoonMetBetrokkenenEnAdres() {
        return new StringBuilder(SELECT_PERSOON)
            .append(JOIN_ADRES)
            .append(JOIN_INDICATIE)
            .append(JOIN_BETROKKENE)
            .toString();
    }

    /**
     * Bouw een query op om een volledig persoon op te halen.
     * Deze persoon heeft ook geslachtnaam, voornamen, betrokkenen, nationaliteiten, indicaties, adressen.
     * @return de opgebouwde text, letop alle where, order by, ... moeten nog toegevoegd worden.
     */
    private String bouwQueryVoorVolledigPersoon() {
        return new StringBuilder(SELECT_PERSOON)
            .append(JOIN_GESLACHTNAAM)
            .append(JOIN_VOORNAAM)
            .append(JOIN_ADRES)
            .append(JOIN_INDICATIE)
            .append(JOIN_NATIONALIEIT)
            .append(JOIN_BETROKKENE)
            .toString();
    }

    /**
     * Voer een query uit en return de gevonden persoon. Deze query geeft een null als er meerdere gevonden
     * worden.
     * @param query de uit te voeren query als text.
     * @param parameters variabele pameters (combinatie van naam, waarde)
     * @return de persistent persoon of null als het niet (of MEERDERE) gevonden wordt.
     */
    private PersistentPersoon haalPersoonOpViaQuery(final String query, final NameValue ... parameters) {
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
     * @param query de uit te voeren query als text.
     * @param parameters variabele pameters (combinatie van naam, waarde)
     * @return de persistent persoon of null als het niet gevonden wordt.
     */
    private List<PersistentPersoon> haalPersonenOpViaQuery(final String query, final NameValue ... parameters) {
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
    public List<Persoon> haalPotentieleVaderViaBsnMoeder(final String bsn) {
        StringBuilder query = new StringBuilder(bouwQueryVoorBasisPersoonMetAdres());
        query.append("WHERE persoon.burgerservicenummer = :burgerservicenummer");
        List<Persoon> personen = new ArrayList<Persoon>();
        PersoonConverterConfiguratie configuratie = new PersoonConverterConfiguratie(
                PersoonConverterGroep.IDENTIFICATIE_NUMMERS
                , PersoonConverterGroep.GESLACHTS_AANDUIDING
                , PersoonConverterGroep.SAMENGESTELDE_NAAM
                , PersoonConverterGroep.GEBOORTE
                , PersoonConverterGroep.AFGELEIDE_ADMINISTRATIE
                , PersoonConverterGroep.ADRESSEN
                , PersoonConverterGroep.INDICATIES
        );
        List<PersistentPersoon> persistentPersonen = haalPersonenOpViaQuery(query.toString(),
                new NameValue("burgerservicenummer", bsn));

        for (PersistentPersoon pp : persistentPersonen) {
            personen.add(PersoonConverter.converteerOperationeelNaarLogisch(pp, true, configuratie));
        }
        return personen;
    }

    /**
     * Zoek een (of meerdere) personen met zijn adres adv.ijn bsn nummer. We verwachten een als resultaat,
     * maar kan ook mmerdere in exceptionele gevallen.
     * @param bsn de bsn
     * @return een lijst met logische personen (of lege lijst)
     */
    @Override
    public List<Persoon> haalPersonenMetAdresOpViaBurgerservicenummer(final String bsn) {
        StringBuilder query = new StringBuilder(bouwQueryVoorPersoonMetBetrokkenenEnAdres());
        query.append("WHERE persoon.burgerservicenummer = :burgerservicenummer");
        List<Persoon> personen = new ArrayList<Persoon>();
        PersoonConverterConfiguratie configuratie = new PersoonConverterConfiguratie(
                PersoonConverterGroep.IDENTIFICATIE_NUMMERS
                , PersoonConverterGroep.GESLACHTS_AANDUIDING
                , PersoonConverterGroep.SAMENGESTELDE_NAAM
                , PersoonConverterGroep.GEBOORTE
                , PersoonConverterGroep.AFGELEIDE_ADMINISTRATIE
                , PersoonConverterGroep.BETROKKENHEDEN
                , PersoonConverterGroep.ADRESSEN
                , PersoonConverterGroep.INDICATIES
        );
        List<PersistentPersoon> persistentPersonen = haalPersonenOpViaQuery(query.toString(),
                new NameValue("burgerservicenummer", bsn));

        for (PersistentPersoon pp : persistentPersonen) {
            //Voor deze vraag moeten de groepen in de betrokkenheden worden gelimiteerd.
            personen.add(PersoonConverter.converteerOperationeelNaarLogisch(pp, true, configuratie, true));
        }
        return personen;
    }

    /**
     * Zoek een persoon wonende op een adres.
     * @param postcode postcode Postcode van het adres.
     * @param huisnummer huisnummer Huisnummer van het adres.
     * @param huisletter huisletter Huisletter van het aders.
     * @param huisnummertoevoeging huisnummertoevoeging Huisnummertoevoeging van het adres.
     * @return Lijst van gevonden personen op het adres.
     */
    public List<Persoon> haalPersonenOpMetAdresViaPostcodeHuisnummer(final String postcode, final String huisnummer,
            final String huisletter, final String huisnummertoevoeging)
    {
        StringBuilder query = new StringBuilder(bouwQueryVoorPersoonMetBetrokkenenEnAdres());
        query.append("WHERE adres.postcode = :postcode ");
        query.append("AND adres.huisnummer = :huisnummer ");
        query.append("AND adres.huisletter = :huisletter ");
        query.append("AND adres.huisnummertoevoeging = :huisnummertoevoeging ");
        query.append("AND adres.soort = 1");

        List<PersistentPersoon> persistentPersonen =
            haalPersonenOpViaQuery(query.toString(),
                    new NameValue("postcode", postcode),
                    new NameValue("huisnummer", huisnummer),
                    new NameValue("huisletter", huisletter),
                    new NameValue("huisnummertoevoeging", huisnummertoevoeging));

        PersoonConverterConfiguratie configuratie =
            new PersoonConverterConfiguratie(PersoonConverterGroep.IDENTIFICATIE_NUMMERS,
                    PersoonConverterGroep.GESLACHTS_AANDUIDING, PersoonConverterGroep.SAMENGESTELDE_NAAM,
                    PersoonConverterGroep.GEBOORTE, PersoonConverterGroep.AFGELEIDE_ADMINISTRATIE,
                    PersoonConverterGroep.BETROKKENHEDEN, PersoonConverterGroep.ADRESSEN,
                    PersoonConverterGroep.INDICATIES);

        List<Persoon> personen = new ArrayList<Persoon>();

        for (PersistentPersoon pp : persistentPersonen) {
            //Voor deze vraag moeten de groepen in de betrokkenheden worden gelimiteerd.
            personen.add(PersoonConverter.converteerOperationeelNaarLogisch(pp, true, configuratie, true));
        }

        return personen;
    }

    @Override
    public Persoon haalPersoonOpMetBurgerservicenummer(final String bsn) {
        StringBuilder query = new StringBuilder(bouwQueryVoorVolledigPersoon());
        query.append("WHERE persoon.burgerservicenummer = :burgerservicenummer");

        PersistentPersoon persoon = haalPersoonOpViaQuery(query.toString(),
                new NameValue("burgerservicenummer", bsn));
        // kan ook tegen een null pointer.
        return PersoonConverter.converteerOperationeelNaarLogisch(persoon, true);
    }

    @Override
    public void opslaanNieuwPersoon(final Persoon persoon, final Integer datumAanvangGeldigheid) {
        // Controlleer of de persoon niet al bestaat op basis van de BSN nummer
        if (!isBSNAlIngebruik(persoon.getIdentificatienummers().getBurgerservicenummer())) {
            final Date registratieTijd = new Date();

            // Opslaan in A- en C-laag

            PersistentPersoon nieuwPersoon = new PersistentPersoon();

            nieuwPersoon.setANummer(persoon.getIdentificatienummers().getAdministratienummer());
            nieuwPersoon.setBurgerservicenummer(persoon.getIdentificatienummers().getBurgerservicenummer());
            nieuwPersoon.setSoortPersoon(persoon.getIdentiteit().getSoort());

            vulAanVoornamen(persoon, nieuwPersoon);
            vulAanGeslachtsnaamComponenten(persoon, nieuwPersoon);
            vulAanGeboorte(persoon, nieuwPersoon);
            vulAanGeslachtsAanduiding(persoon, nieuwPersoon);

            // TODO Hosing: tijdelijk hardcoded naar StatusHistory.A
            nieuwPersoon.setStatushistorie(StatusHistorie.A);
            nieuwPersoon.setSamengesteldeNaamStatusHis(StatusHistorie.A);
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

            em.persist(nieuwPersoon);

            werkHistorieBij(nieuwPersoon, registratieTijd, datumAanvangGeldigheid);
        } else {
            // Persoon bestaat al
            LOGGER.error("Persoon bestaat al met de bsn: {}", persoon.getIdentificatienummers()
                                                                     .getBurgerservicenummer());
            throw new ObjectReedsBestaandExceptie(ObjectReedsBestaandExceptie.ReferentieVeld.BSN, persoon
                .getIdentificatienummers().getBurgerservicenummer(), null);
        }
    }

    /**
     * Deze methode zorgt ervoor dat de voornamen gevuld zijn voor het persisteren.
     *
     * @param persoon {@link Persoon} uit het logische model
     * @param persistentPersoon {@link PersistentPersoon} uit het operationeel model
     */
    private void vulAanVoornamen(final Persoon persoon, final PersistentPersoon persistentPersoon) {

        if (null != persoon.getPersoonVoornamen() && persoon.getPersoonVoornamen().size() > 0) {
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
        if (null != persoon.getGeslachtsnaamcomponenten() && persoon.getGeslachtsnaamcomponenten().size() > 0) {
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

        // Sla op in A-laag
        persistentPersoon.setDatumGeboorte(persoon.getGeboorte().getDatumGeboorte());
        persistentPersoon.setGemeenteGeboorte(referentieDataRepository.vindGemeenteOpCode(persoon.getGeboorte()
                                                                                                 .getGemeenteGeboorte()
                                                                                                 .getGemeentecode()));
        persistentPersoon.setLandGeboorte(referentieDataRepository.vindLandOpCode(persoon.getGeboorte()
                                                                                         .getLandGeboorte()
                                                                                         .getLandcode()));
        if (persoon.getGeboorte().getWoonplaatsGeboorte().getWoonplaatscode() != null) {
            persistentPersoon.setWoonplaatsGeboorte(referentieDataRepository.findWoonplaatsOpCode(persoon
                .getGeboorte().getWoonplaatsGeboorte().getWoonplaatscode()));
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
     * Werk de C-laag bij voor PersistentPersoon.
     *
     * @param persoon PeristentPersoon
     * @param datumTijdRegistratie tijdstip van registratie
     * @param datumAanvangGeldigheid datum van aanvang geldigheid
     */
    private void werkHistorieBij(final PersistentPersoon persoon, final Date datumTijdRegistratie,
        final Integer datumAanvangGeldigheid)
    {
        // Sla voornamen historie op in de C-laag
        for (PersistentPersoonVoornaam voornaam : persoon.getPersoonVoornamen()) {
            persoonVoornaamHistorieRepository.opslaanHistorie(voornaam, datumAanvangGeldigheid, null,
                datumTijdRegistratie);
        }

        // Sla geslachtsnaamcomponenten historie op in de C-laag
        for (PersistentPersoonGeslachtsnaamcomponent geslachtsnaamcomponent : persoon
            .getPersoonGeslachtsnaamcomponenten())
        {
            persoonGeslachtsnaamcomponentHistorieRepository.opslaanHistorie(geslachtsnaamcomponent,
                datumAanvangGeldigheid, null, datumTijdRegistratie);
        }

        // Sla PersoonGeboorte in historie op in de C-laag
        HisPersoonGeboorte hisPersoonGeboorte = new HisPersoonGeboorte();
        hisPersoonGeboorte.setPersoon(persoon);
        hisPersoonGeboorte.setDatumGeboorte(persoon.getDatumGeboorte());
        hisPersoonGeboorte.setDatumTijdRegistratie(datumTijdRegistratie);
        hisPersoonGeboorte.setGemeenteGeboorte(persoon.getGemeenteGeboorte());
        hisPersoonGeboorte.setLandGeboorte(persoon.getLandGeboorte());
        hisPersoonGeboorte.setWoonplaatsGeboorte(persoon.getWoonplaatsGeboorte());
        em.persist(hisPersoonGeboorte);

        // Sla PersoonGeslachtsaanduiging historie op in de C-laag
        HisPersoonGeslachtsaanduiding hisPersoonGeslachtsaanduiding = new HisPersoonGeslachtsaanduiding();
        hisPersoonGeslachtsaanduiding.setPersoon(persoon);
        hisPersoonGeslachtsaanduiding.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonGeslachtsaanduiding.setDatumEindeGeldigheid(null);
        hisPersoonGeslachtsaanduiding.setDatumTijdRegistratie(datumTijdRegistratie);
        hisPersoonGeslachtsaanduiding.setGeslachtsAanduiding(persoon.getGeslachtsAanduiding());
        em.persist(hisPersoonGeslachtsaanduiding);
    }

    @Override
    public boolean isBSNAlIngebruik(final String bsn) {
        return findByBurgerservicenummer(bsn) != null;
    }
}
