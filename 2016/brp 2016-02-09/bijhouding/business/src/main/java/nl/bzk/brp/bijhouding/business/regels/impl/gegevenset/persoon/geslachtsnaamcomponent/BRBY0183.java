/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsnaamcomponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.util.All;
import nl.bzk.brp.bijhouding.business.util.Any;
import nl.bzk.brp.bijhouding.business.util.BusinessUtils;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.kern.Relatie;
import nl.bzk.brp.util.RelatieUtils;

/**
 * BRBY0183: Naamwijziging mag geen gevolgen hebben voor kinderen :
 * <p/>
 * Als een (afgeleide) Bijhouding betrekking heeft op een onderstaand gegeven, dan mag de Persoon op
 * Datum[Aanvang/Einde]Geldigheid daarvan geen Ouderschap hebben over een Kind:
 * <p/>
 * ◾ Voorvoegsel, Scheidingsteken, Geslachtsnaam, Namenreeks  in de Geslachtsnaam;
 * ◾ AdellijkeTitel, Predikaat in groep Geslachtsnaam mits de Persoon op Datum[Aanvang/Einde]Geldigheid de
 * Geslachtsaanduiding "Man" heeft met een Geslachtsnaam gelijk aan die van het Kind (beide uit Geslachtsnaam).
 *
 * @brp.bedrijfsregel BRBY0183
 */
@Named("BRBY0183")
public class BRBY0183 implements VoorActieRegelMetMomentopname<ModelRootObject, BerichtRootObject> {

    @Override
    public List<BerichtEntiteit> voerRegelUit(final ModelRootObject huidigeSituatie,
                                              final BerichtRootObject nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        if (huidigeSituatie != null) {
            if (huidigeSituatie instanceof Persoon) {
                voerRegelUitVoorHuidigeSituatiePersoon((Persoon) huidigeSituatie, nieuweSituatie, objectenDieDeRegelOvertreden);
            } else if (huidigeSituatie instanceof Relatie) {
                voerRegelUitVoorHuidigeSituatieRelatie((Relatie) huidigeSituatie, (RelatieBericht) nieuweSituatie, objectenDieDeRegelOvertreden);
            } else {
                throw new IllegalArgumentException("Rootobject type kan alleen Persoon of Relatie zijn.");
            }
        } else {
            // Huidige situatie is null, in geval van een persoon als rootobject hoeven we niks te doen, want die
            // persoon kan ook geen ouderschap hebben. In geval van een relatie moeten we de bestaande betrokkenen
            // controleren.
            if (nieuweSituatie instanceof Relatie) {
                voerRegelUitVoorNieuweSituatieRelatie((Relatie) nieuweSituatie, bestaandeBetrokkenen, objectenDieDeRegelOvertreden);
            }
        }
        return objectenDieDeRegelOvertreden;
    }

    private void voerRegelUitVoorNieuweSituatieRelatie(final Relatie nieuweSituatie, final Map<String, PersoonView> bestaandeBetrokkenen,
                                                       final List<BerichtEntiteit> objectenDieDeRegelOvertreden)
    {
        for (Betrokkenheid betrokkenheid : nieuweSituatie.getBetrokkenheden()) {
            final PersoonBericht persoonBericht = (PersoonBericht) betrokkenheid.getPersoon();
            final Persoon huidigePersoon = bestaandeBetrokkenen.get(persoonBericht.getIdentificerendeSleutel());
            // Bij een eerste inschrijving hebben we natuurlijk nog geen persoon. Bij huwelijk weer wel.
            if (huidigePersoon != null && wordtRegelOvertredenVoorPersoon(huidigePersoon, persoonBericht)) {
                objectenDieDeRegelOvertreden.add(persoonBericht);
            }
        }
    }

    /**
     * @param huidigeSituatie              Relatie
     * @param nieuweSituatie               RelatieBericht
     * @param objectenDieDeRegelOvertreden List<BerichtEntiteit>
     */
    private void voerRegelUitVoorHuidigeSituatieRelatie(final Relatie huidigeSituatie, final RelatieBericht nieuweSituatie,
                                                        final List<BerichtEntiteit> objectenDieDeRegelOvertreden)
    {
        for (Betrokkenheid betrokkenheid : huidigeSituatie.getBetrokkenheden()) {
            final PersoonBericht persoonBericht =
                    BusinessUtils.matchPersoonInRelatieBericht((PersoonView) betrokkenheid.getPersoon(), nieuweSituatie);

            // persoonBericht kan null zijn (bijv. kind in registratie erkenning), omdat er dan zoiezo geen
            // geslachtsnaamcomponenten in kunnen zitten hoeft er verder niet gecheckt te worden.
            if (persoonBericht != null && wordtRegelOvertredenVoorPersoon(betrokkenheid.getPersoon(), persoonBericht)) {
                objectenDieDeRegelOvertreden.add(persoonBericht);
            }
        }
    }

    /**
     * @param huidigeSituatie              Persoon
     * @param nieuweSituatie               BerichtRootObject
     * @param objectenDieDeRegelOvertreden List<BerichtEntiteit>
     */
    private void voerRegelUitVoorHuidigeSituatiePersoon(final Persoon huidigeSituatie, final BerichtRootObject nieuweSituatie,
                                                        final List<BerichtEntiteit> objectenDieDeRegelOvertreden)
    {
        if (wordtRegelOvertredenVoorPersoon(huidigeSituatie, (PersoonBericht) nieuweSituatie)) {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }
    }

    /**
     * Controleert de bedrijfsregel voor een persoon.
     *
     * @param huidigeSituatie de huidige persoon in de database.
     * @param nieuweSituatie  de persoon uit het bericht
     * @return true indien de regel wordt overtreden voor persoon.
     */
    private boolean wordtRegelOvertredenVoorPersoon(final Persoon huidigeSituatie,
                                                    final PersoonBericht nieuweSituatie)
    {
        boolean resultaat = false;
        if (nieuweSituatie.getGeslachtsnaamcomponenten() != null
                && !nieuweSituatie.getGeslachtsnaamcomponenten().isEmpty()
                && SoortPersoon.INGESCHREVENE == huidigeSituatie.getSoort().getWaarde()
                && heeftOuderschapOverEenKind(huidigeSituatie))
        {
            if (bevatAlleenTitulatuurWijziging(nieuweSituatie.getGeslachtsnaamcomponenten().iterator().next())) {
                // Persoon moet een MAN zijn met een geslachtsnaam gelijk aan het kind.
                if (Geslachtsaanduiding.MAN
                        == huidigeSituatie.getGeslachtsaanduiding().getGeslachtsaanduiding().getWaarde()
                        && heeftKindMetDezelfdeGeslachtsnaam(huidigeSituatie))
                {
                    resultaat = true;
                }
            } else {
                resultaat = true;
            }
        }
        return resultaat;
    }

    /**
     * Controleert of een persoon een kind heeft met dezelfde geslachtsnaam.
     *
     * @param huidigeSituatie de persoon
     * @return true indien persoon een kind heeft met dezelfde geslachtsnaam.
     */
    private boolean heeftKindMetDezelfdeGeslachtsnaam(final Persoon huidigeSituatie) {
        boolean resultaat = false;
        final PersoonGeslachtsnaamcomponent geslCompOuder =
                huidigeSituatie.getGeslachtsnaamcomponenten().iterator().next();
        final String geslachtsnaamOuder = bouwGeslachtsnaamString(geslCompOuder);
        for (Persoon persoon : RelatieUtils.haalAlleKinderenUitPersoon(huidigeSituatie)) {
            if (persoon.getGeslachtsnaamcomponenten() != null
                    && !persoon.getGeslachtsnaamcomponenten().isEmpty())
            {
                final PersoonGeslachtsnaamcomponent geslCompKind =
                        persoon.getGeslachtsnaamcomponenten().iterator().next();
                final String geslachtsnaamKind = bouwGeslachtsnaamString(geslCompKind);

                if (geslachtsnaamOuder.equals(geslachtsnaamKind)) {
                    resultaat = true;
                }
            }
        }
        return resultaat;
    }

    /**
     * Bouwt van een geslachtsnaamcomponent een string op om twee componenten met elkaar te kunnen vergelijken.
     *
     * @param geslComp de geslachtsnaamcomponent.
     * @return string met alle elementen geconcateneert.
     */
    private String bouwGeslachtsnaamString(final PersoonGeslachtsnaamcomponent geslComp) {
        final StringBuilder geslNaamBuilder = new StringBuilder();
        final String spatie = " ";
        if (geslComp.getStandaard().getVoorvoegsel() != null) {
            geslNaamBuilder.append(geslComp.getStandaard().getVoorvoegsel().getWaarde());
            geslNaamBuilder.append(spatie);
        }
        if (geslComp.getStandaard().getScheidingsteken() != null) {
            geslNaamBuilder.append(geslComp.getStandaard().getScheidingsteken().getWaarde());
            geslNaamBuilder.append(spatie);
        }
        if (geslComp.getStandaard().getStam() != null) {
            geslNaamBuilder.append(geslComp.getStandaard().getStam().getWaarde());
            geslNaamBuilder.append(spatie);
        }
        return geslNaamBuilder.toString();
    }

    /**
     * Controleert of in het bericht alleen de Titulatuur is meegegeven in de geslachtsnaam.
     *
     * @param geslCompBericht geslachtsnaamcomponent uit bericht.
     * @return true indien alleen titulatuur is opgegeven.
     */
    private boolean bevatAlleenTitulatuurWijziging(final PersoonGeslachtsnaamcomponentBericht geslCompBericht) {
        return (Any.isNotNull(geslCompBericht.getStandaard().getAdellijkeTitel(), geslCompBericht.getStandaard().getPredicaat()))
                && All.isNull(geslCompBericht.getStandaard().getStam(),
            geslCompBericht.getStandaard().getScheidingsteken(),
            geslCompBericht.getStandaard().getVoorvoegsel());
    }

    /**
     * Controleert of de persoon een (actieve) ouderschap heeft over een kind.
     *
     * @param huidigeSituatie de te controleren persoon.
     * @return true indien persoon ouderschap heeft over een kind, anders false.
     */
    private boolean heeftOuderschapOverEenKind(final Persoon huidigeSituatie) {
        boolean resultaat = false;
        for (Betrokkenheid betrokkenheid : huidigeSituatie.getBetrokkenheden()) {
            if (betrokkenheid instanceof Ouder
                    && ((Ouder) betrokkenheid).getOuderschap() != null)
            {
                resultaat = true;
            }
        }
        return resultaat;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0183;
    }
}
