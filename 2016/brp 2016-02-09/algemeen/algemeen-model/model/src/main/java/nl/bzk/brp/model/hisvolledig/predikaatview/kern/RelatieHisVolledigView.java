/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidComparator;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Relatie.
 */
public abstract class RelatieHisVolledigView extends AbstractRelatieHisVolledigView implements RelatieHisVolledig {

    private PersoonHisVolledig                persoonVanwaarIsGenavigeerdInView;
    private Set<BetrokkenheidHisVolledigView> betrokkenhedenCache;
    private boolean magLeveren;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param relatieHisVolledig relatie
     * @param predikaat          predikaat
     */
    public RelatieHisVolledigView(final RelatieHisVolledig relatieHisVolledig, final Predicate predikaat)
    {
        super(relatieHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param relatieHisVolledig               relatie
     * @param predikaat                        predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public RelatieHisVolledigView(final RelatieHisVolledig relatieHisVolledig, final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(relatieHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    /**
     * Get persoon vanwaar is genavigeerd in de views.
     *
     * @return de persoon
     * @deprecated Deze methode mag niet {@code public}/aanwezig zijn.
     */
    @Deprecated
    public PersoonHisVolledig getPersoonHisVolledig() {
        return persoonVanwaarIsGenavigeerdInView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends BetrokkenheidHisVolledig> getBetrokkenheden() {
        if (betrokkenhedenCache == null) {
            betrokkenhedenCache = new TreeSet<>(new BetrokkenheidComparator());
            final Set<? extends BetrokkenheidHisVolledig> betrokkenheden = super.getBetrokkenheden();

            for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : betrokkenheden) {
                if (betrokkenheidIsNietPersoonZelf(betrokkenheidHisVolledig)) {
                    betrokkenhedenCache.add((BetrokkenheidHisVolledigView) betrokkenheidHisVolledig);
                }
            }
            // vanuit een ouder betrokkenheid, wordt enkel de kindbetrokkenheid getoond. De (optionele)
            // andere ouder wordt hier weggefilterd.
            // LET OP: deze code moet gerefactored worden, onderstaande methode hoort hier niet (geen filtering in getter),
            // wegfilteren doen we in de bindings.
            verwijderOudersUitRelatieMetKindBetrokkenheid();
        }
        return betrokkenhedenCache;
    }

    @Override
    public final boolean heeftBetrokkenheden() {
        return CollectionUtils.isNotEmpty(getBetrokkenheden());
    }

    @Override
    public final boolean heeftBetrokkenhedenVoorLeveren() {
        return heeftBetrokkenheden();
    }

    /**
     * Geeft aan of er ouder betrokkenheden aanwezig zijn.
     *
     * @return true als ouder betrokkenheden aanwezig zijn, anders false.
     */
    public final boolean heeftOuderBetrokkenheden() {
        return bevatBetrokkenheidVanType(OuderHisVolledigView.class);
    }

    @Override
    public final KindHisVolledigView getKindBetrokkenheid() {
        for (final BetrokkenheidHisVolledig betrokkenheidHisVolledigView : getBetrokkenheden()) {
            if (betrokkenheidHisVolledigView instanceof KindHisVolledigView) {
                return (KindHisVolledigView) betrokkenheidHisVolledigView;
            }
        }
        return null;
    }

    @Override
    public final Set<OuderHisVolledigView> getOuderBetrokkenheden() {
        final Set<OuderHisVolledigView> resultaat = new LinkedHashSet<>();
        for (final BetrokkenheidHisVolledig betrokkenheidHisVolledigView : getBetrokkenheden()) {
            if (betrokkenheidHisVolledigView instanceof OuderHisVolledigView) {
                resultaat.add((OuderHisVolledigView) betrokkenheidHisVolledigView);
            }
        }
        return resultaat;
    }

    public final boolean isMagLeveren() {
        return magLeveren;
    }

    public final void setMagLeveren(final boolean magLeveren) {
        this.magLeveren = magLeveren;
    }

    /**
     * Geeft de betrokkenheden terug als deze geleverd mogen worden, hieronder vallen niet de betrokkenheden richting de hoofdpersoon.
     * Dit wordt in de JIBX bindings gebruikt.
     *
     * @return betrokkenheden voor leveren als deze geleverd mag worden.
     */
    public final Set<? extends BetrokkenheidHisVolledig> getBetrokkenhedenVoorLeveren() {
        final Set<BetrokkenheidHisVolledigView> teLeverenBetrokkenheden = new LinkedHashSet<>();
        if (betrokkenhedenCache != null) {
            for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : betrokkenhedenCache) {
                if (betrokkenheidHisVolledigView.isMagLeveren()) {
                    teLeverenBetrokkenheden.add(betrokkenheidHisVolledigView);
                }
            }
        }
        return teLeverenBetrokkenheden;
    }

    /**
     * Setter om aan te geven vanwaar is genavigeerd in de views. Dit op de "bron" van de navigatie niet terug te zien op lagere niveau's.
     *
     * @param persoonHisVolledig de bron persoon
     */
    public void setPersoonHisVolledig(final PersoonHisVolledig persoonHisVolledig) {
        this.persoonVanwaarIsGenavigeerdInView = persoonHisVolledig;
    }

    /**
     * Geef de objectsleutel voor dit voorkomen.
     *
     * @return een objectsleutel
     */
    public final String getObjectSleutel() {
        return getID().toString();
    }

    /**
     * Verwijder ouders uit relatie met kind betrokkenheid.
     */
    private void verwijderOudersUitRelatieMetKindBetrokkenheid() {
        if (betrokkenhedenCache.size() > 1) {
            final boolean verwijderOuders = heeftKindBetrokkenheid();

            if (verwijderOuders) {
                verwijderOuderBetrokkenheden();
            }
        }
    }

    /**
     * Verwijder ouder betrokkenheden.
     */
    private void verwijderOuderBetrokkenheden() {
        final Iterator<BetrokkenheidHisVolledigView> betrokkenhedenIterator = betrokkenhedenCache.iterator();
        while (betrokkenhedenIterator.hasNext()) {
            final BetrokkenheidHisVolledig betrokkenheidHisVolledigView = betrokkenhedenIterator.next();
            if (betrokkenheidHisVolledigView instanceof OuderHisVolledig) {
                betrokkenhedenIterator.remove();
            }
        }
    }

    /**
     * Controleert of betrokkenheid niet persoon zelf is.
     *
     * @param betrokkenheid de betrokkenheid
     * @return true als het niet persoon zelf is
     */
    private boolean betrokkenheidIsNietPersoonZelf(final BetrokkenheidHisVolledig betrokkenheid) {
        return betrokkenheid.getPersoon() == null || !betrokkenheid.getPersoon().getID().equals(persoonVanwaarIsGenavigeerdInView.getID());
    }

    /**
     * Controleert of er sprake is van kind betrokkenheid.
     *
     * @return true als betrokkenheid bestaat
     */
    private boolean heeftKindBetrokkenheid() {
        return bevatBetrokkenheidVanType(KindHisVolledig.class);
    }

    /**
     * Controleert of er een of meerdere betrokkenheden van een bepaald type aanwezig zijn.
     *
     * @param type the type
     * @return the boolean
     */
    private boolean bevatBetrokkenheidVanType(final Class<?> type) {
        for (final BetrokkenheidHisVolledig betrokkenheid : getBetrokkenheden()) {
            if (type.isInstance(betrokkenheid)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Verwerkingssoort getVerwerkingssoort() {
        return relatie.getVerwerkingssoort();
    }

    @Override
    public void setVerwerkingssoort(final Verwerkingssoort verwerkingssoort) {
        relatie.setVerwerkingssoort(verwerkingssoort);
    }
}
