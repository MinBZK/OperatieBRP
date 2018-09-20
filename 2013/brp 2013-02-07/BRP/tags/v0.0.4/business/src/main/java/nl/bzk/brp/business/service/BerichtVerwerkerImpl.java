/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.business.actie.ActieFactory;
import nl.bzk.brp.business.actie.ActieUitvoerder;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.PersistentRootObject;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Standaard implementatie van de BerichtVerwerker.
 */
@Service
public class BerichtVerwerkerImpl implements BerichtVerwerker {

    private static final Logger  LOGGER = LoggerFactory.getLogger(BerichtVerwerkerImpl.class);

    @Inject
    private ActieFactory         actieFactory;

    @Inject
    private BedrijfsRegelManager bedrijfsRegelManager;

    @Inject
    private PersoonRepository    persoonRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public BerichtResultaat verwerkBericht(final BRPBericht bericht) {
        LOGGER.debug("Berichtverwerker start verwerking bericht: " + bericht);

        final List<Melding> meldingen = new ArrayList<Melding>();
        try {
            if (bericht.getBrpActies() != null) {
                // Controlleer bericht en voeg meldingen toe
                meldingen.addAll(controleerBericht(bericht));

                // Als meldingen leeg is of enkel waarschuwingen bevat, dan mag de actie worden uitgevoerd.
                if (meldingen.isEmpty() || bevatAlleenWaarschuwingen(meldingen)) {
                    for (BRPActie actie : bericht.getBrpActies()) {
                        meldingen.addAll(voerActieUit(actie));
                    }
                }
            }
        } catch (Throwable t) {
            LOGGER.error("Fout opgetreden in de bericht verwerking.", t);
            meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001));
        }

        return new BerichtResultaat(meldingen);
    }

    /**
     * Controleert of meldingen enkel waarschuwingen bevat.
     *
     * @param meldingen De meldingen die gecontroleerd dient te worden.
     * @return true indien alleen waarschuwingen aanwezig zijn. Anders false.
     */
    private boolean bevatAlleenWaarschuwingen(final List<Melding> meldingen) {
        for (Melding melding : meldingen) {
            if (!(SoortMelding.WAARSCHUWING == melding.getSoort())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Controlleer of bericht klopt en uitgevoerd mag worden.
     *
     * @param bericht bericht dat gecontrolleerd moet worden
     * @return lijst met fout meldingen, wanneer het bericht klopt dan wordt een lege lijst teruggegegven
     */
    private List<Melding> controleerBericht(final BRPBericht bericht) {
        final List<Melding> bedrijfsRegelMeldingen = new ArrayList<Melding>();

        for (BRPActie actie : bericht.getBrpActies()) {
            // Valideer actie
            if (isActieGeldig(actie, bedrijfsRegelMeldingen)) {
                bedrijfsRegelMeldingen.addAll(controleerActieTegenBedrijfsRegels(actie));
            }
        }

        return bedrijfsRegelMeldingen;
    }

    /**
     * Controleert een actie uit een inkomend bericht tegen alle geldende bedrijfsregels.
     *
     * @param actie De actie die gecontroleerd wordt.
     * @return Lijst van eventuele bedrijfsregel meldingen.
     */
    private List<Melding> controleerActieTegenBedrijfsRegels(final BRPActie actie) {
        final List<Melding> bedrijfsRegelFouten = new ArrayList<Melding>();

        final PersistentRootObject huidigeSituatie = haalHuidigeSituatieOp(actie);
        final RootObject nieuweSituatie = actie.getRootObjecten().get(0);

        for (BedrijfsRegel<PersistentRootObject, RootObject> bedrijfsRegel
                : bedrijfsRegelManager.getUitTeVoerenBedrijfsRegels(actie.getSoort()))
        {
            final Melding melding = bedrijfsRegel.executeer(huidigeSituatie, nieuweSituatie);
            if (melding != null) {
                bedrijfsRegelFouten.add(melding);
            }
        }
        return bedrijfsRegelFouten;
    }

    /**
     * Omdat bedrijfsregels gebruik maken van de huidige situatie in de BRP database, moet de huidige situatie ook
     * via de DAL laag opgehaald worden. Dat gebeurt in deze functie.
     *
     * @param actie De actie die uitegevoerd dient te worden uit het inkomende bericht.
     * @return Een PersistentRootObject wat in feite een instantie is van een Persoon of Relatie sinds deze 2 objecten
     *         als RootObject worden gezien.
     */
    private PersistentRootObject haalHuidigeSituatieOp(final BRPActie actie) {
        final PersistentRootObject huidigeSituatie;
        switch (actie.getSoort()) {
            case VERHUIZING:
                huidigeSituatie =
                    persoonRepository.findByBurgerservicenummer(((Persoon) actie.getRootObjecten().get(0))
                            .getIdentificatienummers().getBurgerservicenummer());
                break;
            default:
                throw new IllegalStateException(actie.getSoort()
                    + " wordt nog niet ondersteund door de berichtverwerker");
        }
        return huidigeSituatie;
    }

    /**
     * Executeert de actie, door de juiste {@link ActieUitvoerder} instantie op te halen, en retourneert de meldingen
     * die zijn opgetreden tijdens het uitvoeren van de actie. Indien er geen meldingen zijn, wordt er een lege lijst
     * van meldingen geretourneerd.
     *
     * @param actie de actie die moet worden doorgevoerd.
     * @return de meldingen die zijn opgetreden tijdens het uitvoeren van de actie.
     */
    private List<Melding> voerActieUit(final BRPActie actie) {
        ActieUitvoerder uitvoerder = actieFactory.getActieUitvoerder(actie);
        List<Melding> meldingen = new ArrayList<Melding>();

        if (uitvoerder != null) {
            List<Melding> actieMeldingen = uitvoerder.voerUit(actie);
            if (actieMeldingen != null) {
                meldingen.addAll(actieMeldingen);
            }
        } else {
            LOGGER.error("Berichtverwerker kan geen ActieUitvoerder vinden voor actie: " + actie);

            Melding melding =
                new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                        "Systeem kan actie niet uitvoeren vanwege onbekende configuratie.");
            meldingen.add(melding);
        }
        return meldingen;
    }

    /**
     * Valideer de actie.
     *
     * @param actie de BRP actie die gevalideerd moet worden, alleen de eerste rootObject wordt gecontrolleerd binnen de
     *            actie.
     * @param meldingen lijst die aangevuld wordt met meldingen
     * @return true als actie geldig is en false wanneer er fouten zijn
     */
    private boolean isActieGeldig(final BRPActie actie, final List<Melding> meldingen) {
        boolean resultaat = true;

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<RootObject>> violations =
            validator.validate(actie.getRootObjecten().get(0), Default.class);

        for (ConstraintViolation<RootObject> v : violations) {
            String code = v.getMessageTemplate().substring(1, v.getMessageTemplate().length() - 1);
            // TODO er wordt hier nog niet gebruik gemaakt van i18n berichten, dit zou gerefactored kunnen worden
            meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.valueOf(code), MeldingCode.valueOf(
                    code).getOmschrijving()));
            resultaat = false;
        }

        return resultaat;
    }
}
