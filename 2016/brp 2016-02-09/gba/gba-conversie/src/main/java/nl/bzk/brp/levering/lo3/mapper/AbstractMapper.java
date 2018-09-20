/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

/**
 * Basis mapper voor BRP naar Conversie model.
 *
 * @param <B>
 *            BRP Basis object type
 * @param <H>
 *            BRP Historie type
 * @param <G>
 *            Conversie model groep inhoud type
 */
public abstract class AbstractMapper<B, H extends ModelIdentificeerbaar<?>, G extends BrpGroepInhoud> {

    /**
     * Map.
     *
     * @param volledig
     *            het volledige object
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param actieHisVolledigLocator
     *            actieHisVolledig locator
     * @return gemapte stapel
     */
    public final BrpStapel<G> map(final B volledig, final OnderzoekMapper onderzoekMapper, final ActieHisVolledigLocator actieHisVolledigLocator) {
        final List<BrpGroep<G>> groepen = new ArrayList<>();

        final Iterable<H> histories = getHistorieIterable(volledig);
        if (histories != null) {
            for (final H historie : histories) {
                if (historie != null) {
                    if (!behoortTotStatischePersoonlijst(historie)) {
                        continue;
                    }

                    final BrpGroep<G> groep = mapGroep(historie, onderzoekMapper, actieHisVolledigLocator);
                    if (groep != null) {
                        groepen.add(groep);
                    }
                }
            }
        }

        if (groepen.isEmpty()) {
            return null;
        } else {
            return new BrpStapel<G>(groepen);
        }
    }

    /**
     * Indien de indicatie voorkomen tbv levering mutaties 'Ja' is dan behoort het record niet tot de statische
     * persoonslijst, maar enkel tot de dynamische persoonslijst.
     *
     * @param historie
     *            record
     * @return true, als het historie behoort tot de statische persoonslijst
     */
    private boolean behoortTotStatischePersoonlijst(final H historie) {
        if (historie instanceof VerantwoordingTbvLeveringMutaties) {
            final JaAttribuut indicatie = ((VerantwoordingTbvLeveringMutaties) historie).getIndicatieVoorkomenTbvLeveringMutaties();

            if (indicatie != null && Ja.J.equals(indicatie.getWaarde())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Map een BRP historie naar een conversie groep.
     *
     * @param historie
     *            BRP historie
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param actieHisVolledigLocator
     *            actieHisVolledig locator
     * @return Conversie groep
     */
    public abstract BrpGroep<G> mapGroep(final H historie, final OnderzoekMapper onderzoekMapper, final ActieHisVolledigLocator actieHisVolledigLocator);

    /**
     * Geef de iterable die gemapped moet worden.
     *
     * @param volledig
     *            het BRP object waaruit de iterable gehaald moet worden
     * @return iterable
     */
    protected abstract Iterable<H> getHistorieIterable(final B volledig);

    /**
     * Map de BRP historie naar conversie inhoud.
     *
     * @param historie
     *            BRP historie
     * @param onderzoekMapper
     *            onderzoek mapper
     * @return conversie inhoud
     */
    protected abstract G mapInhoud(H historie, final OnderzoekMapper onderzoekMapper);

    /**
     * Geef de identificatie voor een stapel (binnen de lo3 categorieen).
     *
     * @param historie
     *            historie entiteit
     * @return identificatie, null als geen stapel identificatie nodig is
     */
    protected Integer getStapelNummer(final H historie) {
        // Hook
        return null;
    }

    /**
     * Geef de actie inhoud van het gegeven historie record.
     *
     * @param historie
     *            historie record
     * @return actie inhoud, indien het historie record formeel verantwoordbaar met acties is, anders null
     */
    protected final ActieModel getActieInhoud(final H historie) {
        final ActieModel result;
        if (historie instanceof FormeelVerantwoordbaar<?>) {
            final Object actieInhoud = ((FormeelVerantwoordbaar<?>) historie).getVerantwoordingInhoud();
            if (actieInhoud instanceof ActieModel) {
                result = (ActieModel) actieInhoud;
            } else {
                result = null;
            }
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Geef de actie verval van het gegeven historie record.
     *
     * @param historie
     *            historie record
     * @return actie verval, indien het historie record formeel verantwoordbaar met acties is, anders null
     */
    protected final ActieModel getActieVerval(final H historie) {
        final ActieModel result;
        if (historie instanceof FormeelVerantwoordbaar<?>) {
            final Object actieVerval = ((FormeelVerantwoordbaar<?>) historie).getVerantwoordingVerval();
            if (actieVerval instanceof ActieModel) {
                result = (ActieModel) actieVerval;
            } else {
                result = null;
            }
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Geef de actie aanpassing geldigheid van het gegeven historie record.
     *
     * @param historie
     *            historie record
     * @return actie aanpassing geldigheid, indien het historie record materieel verantwoordbaar met acties is, anders
     *         null
     */
    protected final ActieModel getActieAanpassingGeldigheid(final H historie) {
        final ActieModel result;
        if (historie instanceof MaterieelVerantwoordbaar<?>) {
            final Object actieVerval = ((MaterieelVerantwoordbaar<?>) historie).getVerantwoordingAanpassingGeldigheid();
            if (actieVerval instanceof ActieModel) {
                result = (ActieModel) actieVerval;
            } else {
                result = null;
            }
        } else {
            result = null;
        }
        return result;
    }

}
