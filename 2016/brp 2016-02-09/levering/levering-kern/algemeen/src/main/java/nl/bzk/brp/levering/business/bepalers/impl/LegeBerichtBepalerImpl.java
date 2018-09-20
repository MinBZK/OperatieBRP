/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import nl.bzk.brp.levering.business.bepalers.LegeBerichtBepaler;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import org.springframework.stereotype.Component;


/**
 * Implementatie van bepaler die zegt wanneer de persoon "leeg" is.
 * <p/>
 * 1. Er zijn geen groepen waarvoor geldt: er mag geleverd worden o.b.v. de autorisatie<br/>
 * 2. Er is minimaal een (1) groep die gewijzigd is <br/>
 * 3. Afgeleid administratief is niet de enige groep die gewijzigd is
 *
 * @brp.bedrijfsregel VR00089
 * @brp.bedrijfsregel VR00093
 * @brp.bedrijfsregel R1989
 */
@Regels({ Regel.VR00089, Regel.VR00093, Regel.R1989 })
@Component
public class LegeBerichtBepalerImpl implements LegeBerichtBepaler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final boolean magPersoonGeleverdWorden(final PersoonHisVolledigView persoonView) {
        boolean magPersoonGeleverdWorden = false;
        if (persoonView != null && persoonView.isMagGeleverdWorden()) {
            if (heeftWijzigingenInIdentificerendeGroep(persoonView)) {
                magPersoonGeleverdWorden = true;
            } else if (isAfgeleidAdministratiefInhoudelijkGewijzigd(persoonView)) {
                // VR00093:
                magPersoonGeleverdWorden = true;
            } else if (heeftWijzigingenBuitenIdentificerendeGroep(persoonView)) {
                magPersoonGeleverdWorden = true;
            } else if (persoonView.heeftBetrokkenhedenVoorLeveren()) {
                magPersoonGeleverdWorden = true;
            } else if (heeftWijzigingenDoorOnderzoek(persoonView)) {
                magPersoonGeleverdWorden = true;
            }
        }
        return magPersoonGeleverdWorden;
    }

    /**
     * Controleert of er wijzigingen in attributen zijn door door het plaatsen, wijzigen of beeindigen van onderzoeken.
     *
     * @param persoonView de persoon view
     * @return true als er attributen in onderzoek zijn gezet, anders false
     */
    private boolean heeftWijzigingenDoorOnderzoek(final PersoonHisVolledigView persoonView) {
        for (final HistorieEntiteit historieEntiteit : persoonView.getTotaleLijstVanHisElementenOpPersoonsLijst()) {
            if (historieEntiteit instanceof Groep) {
                final Groep groep = (Groep) historieEntiteit;
                for (final Attribuut attribuut : groep.getAttributen()) {
                    if (attribuut.isInOnderzoek() && attribuut.isMagGeleverdWorden()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Heeft wijzigingen in voorkomens buiten identificerende groepen.
     *
     * @param persoonView de persoon view
     * @return true als er wijzigingen zijn, anders false
     */
    private boolean heeftWijzigingenBuitenIdentificerendeGroep(final PersoonHisVolledigView persoonView) {
        for (final HistorieEntiteit historieEntiteit : persoonView.getTotaleLijstVanHisElementenOpPersoonsLijst()) {
            if (!isIdentificerend(historieEntiteit)
                && !(historieEntiteit instanceof HisPersoonAfgeleidAdministratiefModel)
                && !(historieEntiteit instanceof HisOnderzoekModel))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean magBetrokkenPersoonGeleverdWorden(final PersoonHisVolledigView persoonView) {
        return heeftWijzigingenInIdentificerendeGroep(persoonView) || heeftWijzigingenDoorOnderzoek(persoonView);
    }

    @Regels(Regel.VR00093)
    private boolean isAfgeleidAdministratiefInhoudelijkGewijzigd(final PersoonHisVolledigView persoonView) {
        boolean resultaat = false;
        final FormeleHistorieSet<HisPersoonAfgeleidAdministratiefModel> persoonAfgeleidAdministratiefHistorie =
            persoonView.getPersoonAfgeleidAdministratiefHistorie();

        // Check of er uberhaupt autorisatie is op de groep Afgeleid administratief:
        if (!persoonAfgeleidAdministratiefHistorie.isLeeg()) {

            // Het aantal voorkomens moet exact 2 zijn, omdat dit een formele historie groep is:
            if (persoonAfgeleidAdministratiefHistorie.getAantal() != 2) {
                final String foutmelding =
                    "Er worden exact 2 voorkomens van de formeel historische groep AfgeleidAdministratief verwacht in een mutatie levering. Aantal "
                        + "voorkomens: " + persoonAfgeleidAdministratiefHistorie.getAantal();
                LOGGER.error(foutmelding);
                throw new IllegalStateException(foutmelding);
            }

            final Iterator<HisPersoonAfgeleidAdministratiefModel> iterator =
                persoonAfgeleidAdministratiefHistorie.iterator();
            final HisPersoonAfgeleidAdministratiefModel record1 = iterator.next();
            final HisPersoonAfgeleidAdministratiefModel record2 = iterator.next();

            /**
             * Let op: Er is 1 inhoudelijk attribuuut dat gecontroleerd dient te worden;
             * - indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig
             * De rest van de attributen zijn altijd gewijzigd bij een mutatie en zijn niet interessant voor een
             * afnemer. Zie VR00093
             */
            resultaat = bepaalOfAttributenGewijzigdZijnTovElkaarEnErAutorisatieIsOpHetAttribuut(
                record1.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(),
                record2.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig());
        }

        if (!resultaat) {
            LOGGER.debug(String
                    .format("VR00093: Persoon met id %1s wordt niet geleverd omdat het bericht (na alle autorisaties) enkel een mutatie kent op niet "
                        + "relevante attributen van de groep AfgeleidAdministratief.", persoonView.getID()));
        }
        return resultaat;
    }

    /**
     * Is de historie entiteit een identificerende groep.
     *
     * @param historieEntiteit de historieEntiteit
     * @return true als groep identificerend is, anders false.
     */
    private boolean isIdentificerend(final HistorieEntiteit historieEntiteit) {
        return historieEntiteit instanceof HisPersoonIdentificatienummersModel
            || historieEntiteit instanceof HisPersoonSamengesteldeNaamModel
            || historieEntiteit instanceof HisPersoonGeboorteModel
            || historieEntiteit instanceof HisPersoonGeslachtsaanduidingModel;
    }

    private boolean bepaalOfAttributenGewijzigdZijnTovElkaarEnErAutorisatieIsOpHetAttribuut(final JaNeeAttribuut attribuut1,
        final JaNeeAttribuut attribuut2)
    {
        final boolean resultaat;

        if (attribuut1 == null && attribuut2 == null) {
            resultaat = false;
        } else if (attribuut2 == null) {
            resultaat = attribuut1.isMagGeleverdWorden();
        } else if (attribuut1 == null) {
            resultaat = attribuut2.isMagGeleverdWorden();
        } else {
            // Het maakt hier niet uit naar welk vlaggetje je kijkt; van attribuut 1 of van attribuut 2.
            resultaat = !attribuut1.equals(attribuut2) && attribuut1.isMagGeleverdWorden();
        }
        return resultaat;
    }

    /**
     * Controleert of er wijzigingen zijn binnen de identificerende groepen. Aangezien we hier met delta-views
     * werken wordt een groep als gewijzigd bestempeld als er meer dan 1 voorkomen van is.
     *
     * @param persoonView De persoon view.
     * @return Boolean true als er wijzigingen zijn in identificerende groepen, anders false.
     */
    private boolean heeftWijzigingenInIdentificerendeGroep(final PersoonHisVolledigView persoonView) {
        final List<? extends HistorieSet> identificerendeHistorieSets =
            Arrays.asList(persoonView.getPersoonIdentificatienummersHistorie(),
                    persoonView.getPersoonSamengesteldeNaamHistorie(), persoonView.getPersoonGeboorteHistorie(),
                    persoonView.getPersoonGeslachtsaanduidingHistorie());
        final boolean persoonIsNieuw = persoonView.getPersoonAfgeleidAdministratiefHistorie().getAantal() == 1;
        for (final HistorieSet historieSet : identificerendeHistorieSets) {
            final int aantalHisRecords = historieSet.getHistorie().size();
            // voor nieuwe persoon kijken we of het aantal hisrecords precies 1 is, voor bestaande
            // personen mogen er meer records zijn.
            if ((persoonIsNieuw && aantalHisRecords == 1) || (!persoonIsNieuw && aantalHisRecords > 1)) {
                return true;
            }
        }
        return false;
    }
}
