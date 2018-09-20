/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorte;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduiding;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaam;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonVoornaam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersistentPersoon} class en standaard implementatie van de {@link PersoonRepository} class.
 */
@Repository
public class PersoonJpaRepository implements PersoonRepository {

    private static final Logger      LOGGER = LoggerFactory.getLogger(PersoonJpaRepository.class);

    @PersistenceContext
    private EntityManager            em;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

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

    @Override
    public void opslaanNieuwPersoon(final Persoon persoon, final Integer datumAanvangGeldigheid) {
        // Controlleer of de persoon niet al bestaat op basis van de BSN nummer
        if (isNieuwPersoon(persoon)) {
            final Date registratieTijd = new Date();

            // Opslaan in A- en C-laag

            PersistentPersoon nieuwPersoon = new PersistentPersoon();

            nieuwPersoon.setANummer(persoon.getIdentificatienummers().getAdministratienummer());
            nieuwPersoon.setBurgerservicenummer(persoon.getIdentificatienummers().getBurgerservicenummer());
            nieuwPersoon.setSoortPersoon(persoon.getIdentiteit().getSoort());

            vulAanVoornamen(persoon, nieuwPersoon, registratieTijd, datumAanvangGeldigheid);
            vulAanGeslachtsnaamComponenten(persoon, nieuwPersoon, registratieTijd, datumAanvangGeldigheid);
            vulAanGeboorte(persoon, nieuwPersoon, registratieTijd, datumAanvangGeldigheid);
            vulAanGeslachtsAanduiding(persoon, nieuwPersoon, registratieTijd, datumAanvangGeldigheid);

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
     * @param datumTijdRegistratie tijd van registratie
     * @param datumAanvangGeldigheid datum aanvang van geldigheid
     */
    private void vulAanVoornamen(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final Date datumTijdRegistratie, final Integer datumAanvangGeldigheid)
    {

        if (null != persoon.getPersoonVoornamen() && persoon.getPersoonVoornamen().size() > 0) {
            for (PersoonVoornaam persoonVoornaam : persoon.getPersoonVoornamen()) {
                // Sla op in A-laag
                PersistentPersoonVoornaam persistentPersoonVoornaam = new PersistentPersoonVoornaam();
                persistentPersoonVoornaam.setPersoon(persistentPersoon);
                persistentPersoonVoornaam.setNaam(persoonVoornaam.getNaam());
                persistentPersoonVoornaam.setVolgnummer(persoonVoornaam.getVolgnummer());
                // TODO Hosing: tijdelijk hardcoded naar StatusHistory.A
                persistentPersoonVoornaam.setPersoonVoornaamStatusHis(StatusHistorie.A);

                // Sla op in C-laag
                HisPersoonVoornaam hisPersoonVoornaam = new HisPersoonVoornaam();
                hisPersoonVoornaam.setPersoonVoornaam(persistentPersoonVoornaam);

                hisPersoonVoornaam.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
                hisPersoonVoornaam.setDatumEindeGeldigheid(null);
                hisPersoonVoornaam.setDatumTijdRegistratie(datumTijdRegistratie);
                hisPersoonVoornaam.setNaam(persoonVoornaam.getNaam());

                persistentPersoonVoornaam.addHisPersoonVoornaam(hisPersoonVoornaam);

                persistentPersoon.voegPersoonVoornaamToe(persistentPersoonVoornaam);
            }
        }
    }

    /**
     * Deze methode zorgt ervoor dat de geslachtsnamen gevuld zijn voor het persisteren.
     *
     * @param persoon {@link Persoon} uit het logische model
     * @param persistentPersoon {@link PersistentPersoon} uit het operationeel model
     * @param datumTijdRegistratie tijd van registratie
     * @param datumAanvangGeldigheid datum aanvang van geldigheid
     */
    private void vulAanGeslachtsnaamComponenten(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final Date datumTijdRegistratie, final Integer datumAanvangGeldigheid)
    {
        if (null != persoon.getGeslachtsnaamcomponenten() && persoon.getGeslachtsnaamcomponenten().size() > 0) {
            for (PersoonGeslachtsnaamcomponent persoonGeslachtsnaam : persoon.getGeslachtsnaamcomponenten()) {
                // Sla op in A-laag
                PersistentPersoonGeslachtsnaamcomponent persistentGeslachtsnaam =
                    new PersistentPersoonGeslachtsnaamcomponent();
                persistentGeslachtsnaam.setPersoon(persistentPersoon);
                persistentGeslachtsnaam.setNaam(persoonGeslachtsnaam.getNaam());
                persistentGeslachtsnaam.setVolgnummer(persoonGeslachtsnaam.getVolgnummer());
                persistentGeslachtsnaam.setVoorvoegsel(persoonGeslachtsnaam.getVoorvoegsel());
                // TODO Hosing: tijdelijk hardcoded naar StatusHistory.A
                persistentGeslachtsnaam.setPersoonGeslachtsnaamcomponentStatusHis(StatusHistorie.A);

                // Sla op in C-laag
                HisPersoonGeslachtsnaamcomponent hisPersoonGeslachtsnaamcomponent =
                    new HisPersoonGeslachtsnaamcomponent();
                hisPersoonGeslachtsnaamcomponent.setPersoonGeslachtsnaamcomponent(persistentGeslachtsnaam);

                hisPersoonGeslachtsnaamcomponent.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
                hisPersoonGeslachtsnaamcomponent.setDatumEindeGeldigheid(null);
                hisPersoonGeslachtsnaamcomponent.setDatumTijdRegistratie(datumTijdRegistratie);
                hisPersoonGeslachtsnaamcomponent.setNaam(persoonGeslachtsnaam.getNaam());
                hisPersoonGeslachtsnaamcomponent.setVoorvoegsel(persoonGeslachtsnaam.getVoorvoegsel());

                persistentGeslachtsnaam.addHisPersoonGeslachtsnaamcomponent(hisPersoonGeslachtsnaamcomponent);

                persistentPersoon.voegPersoonGeslachtsnaamcomponentenToe(persistentGeslachtsnaam);
            }
        }

    }

    /**
     * Deze methode zorgt ervoor dat geboorte gegevens gevuld zijn voor het persisteren.
     *
     * @param persoon {@link Persoon} uit het logische model
     * @param persistentPersoon {@link PersistentPersoon} uit het operationeel model
     * @param datumTijdRegistratie tijd van registratie
     * @param datumAanvangGeldigheid datum aanvang van geldigheid
     */
    private void vulAanGeboorte(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final Date datumTijdRegistratie, final Integer datumAanvangGeldigheid)
    {

        // Sla op in A-laag
        persistentPersoon.setDatumGeboorte(persoon.getPersoonGeboorte().getDatumGeboorte());
        persistentPersoon.setGemeenteGeboorte(referentieDataRepository.vindGemeenteOpCode(persoon.getPersoonGeboorte()
                .getGemeenteGeboorte().getGemeentecode()));
        persistentPersoon.setLandGeboorte(referentieDataRepository.vindLandOpCode(persoon.getPersoonGeboorte()
                .getLandGeboorte().getLandcode()));
        if (persoon.getPersoonGeboorte().getWoonplaatsGeboorte().getWoonplaatscode() != null) {
            persistentPersoon.setWoonplaatsGeboorte(referentieDataRepository.findWoonplaatsOpCode(persoon
                    .getPersoonGeboorte().getWoonplaatsGeboorte().getWoonplaatscode()));
        }
        // TODO Hosing: tijdelijk hardcoded naar StatusHistory.A
        persistentPersoon.setGeboorteStatusHis(StatusHistorie.A);

        // Sla op in C-laag
        HisPersoonGeboorte hisPersoonGeboorte = new HisPersoonGeboorte();
        hisPersoonGeboorte.setPersoon(persistentPersoon);
        hisPersoonGeboorte.setDatumGeboorte(persistentPersoon.getDatumGeboorte());
        hisPersoonGeboorte.setDatumTijdRegistratie(datumTijdRegistratie);
        hisPersoonGeboorte.setGemeenteGeboorte(persistentPersoon.getGemeenteGeboorte());
        hisPersoonGeboorte.setLandGeboorte(persistentPersoon.getLandGeboorte());
        hisPersoonGeboorte.setWoonplaatsGeboorte(persistentPersoon.getWoonplaatsGeboorte());

        persistentPersoon.voegHisPersoonGeboorteToe(hisPersoonGeboorte);
    }

    /**
     * Deze methode zorgt ervoor dat geboorte gegevens gevuld zijn voor het persisteren.
     *
     * @param persoon {@link Persoon} uit het logische model
     * @param persistentPersoon {@link PersistentPersoon} uit het operationeel model
     * @param datumTijdRegistratie tijd van registratie
     * @param datumAanvangGeldigheid datum aanvang van geldigheid
     */
    private void vulAanGeslachtsAanduiding(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final Date datumTijdRegistratie, final Integer datumAanvangGeldigheid)
    {
        // Sla op in A-laag
        persistentPersoon.setGeslachtsAanduiding(persoon.getPersoonGeslachtsAanduiding().getGeslachtsAanduiding());
        // TODO Hosing: tijdelijk hardcoded naar StatusHistory.A
        persistentPersoon.setGeslachtsaanduidingStatusHis(StatusHistorie.A);

        // Sla op in C-laag
        HisPersoonGeslachtsaanduiding hisPersoonGeslachtsaanduiding = new HisPersoonGeslachtsaanduiding();
        hisPersoonGeslachtsaanduiding.setPersoon(persistentPersoon);
        hisPersoonGeslachtsaanduiding.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonGeslachtsaanduiding.setDatumEindeGeldigheid(null);
        hisPersoonGeslachtsaanduiding.setDatumTijdRegistratie(datumTijdRegistratie);
        hisPersoonGeslachtsaanduiding.setGeslachtsAanduiding(persistentPersoon.getGeslachtsAanduiding());

        persistentPersoon.voegToeHisPersoonGeslachtsaanduiding(hisPersoonGeslachtsaanduiding);
    }

    /**
     * Controlleer of het om een nieuw persoon gaat gebasseerd op bsn nummer.
     *
     * @param persoon te controlleren persoon
     *
     * @return true als persoon al bestaat
     */
    private boolean isNieuwPersoon(final Persoon persoon) {
        return findByBurgerservicenummer(persoon.getIdentificatienummers().getBurgerservicenummer()) == null;
    }
}
