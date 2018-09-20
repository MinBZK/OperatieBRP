/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.levering.business.bepalers.BerichtVerwerkingssoortBepaler;
import nl.bzk.brp.levering.business.bepalers.BerichtVerwerkingssoortZetter;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;

/**
 * De betrokkenheden in deze klasse zijn ingedeeld in 2 niveaus:
 * Niveau 1: De betrokkenheden van een persoon.
 * Niveau 2: De betrokkenheden van een relatie.
 * Voor meer informatie, zie de @BerichtVerwerkingssoortZetter.
 * <p/>
 * brp.bedrijfsregel VR00061
 */
@Regels(Regel.VR00061)
public class BerichtVerwerkingssoortZetterImpl implements BerichtVerwerkingssoortZetter {

    private final BerichtVerwerkingssoortBepaler berichtVerwerkingssoortBepaler;

    /**
     * Constructor met de benodigde afhankelijkheden.
     *
     * @param bepaler een verwerkingssoort bepaler
     */
    public BerichtVerwerkingssoortZetterImpl(final BerichtVerwerkingssoortBepaler bepaler)
    {
        berichtVerwerkingssoortBepaler = bepaler;
    }

    @Override
    public final void voegVerwerkingssoortenToe(final List<PersoonHisVolledigView> bijgehoudenPersoonViews, final Long administratieveHandelingId) {
        for (final PersoonHisVolledigView persoonView : bijgehoudenPersoonViews) {
            bepaalEnZetVerwerkingssoortVoorPersoon(administratieveHandelingId, persoonView, null);
        }
    }

    @Override
    public final void voegVerwerkingssoortenToe(final PersoonHisVolledigView persoonView, final Long administratieveHandelingId) {
        voegVerwerkingssoortenToe(Collections.singletonList(persoonView), administratieveHandelingId);
    }

    /**
     * Bepaalt en zet de verwerkingssoort voor een bijgehouden persoon óf persoon op niveau 2 (betrokkene).
     *
     * @param administratieveHandelingId De id van de huidige administratieve handeling.
     * @param persoonHisVolledig         De persoon his volledig delta view.
     * @param soortBetrokkenheid         De soort betrokkenheid van betrokken persoon, null als het om een bijgehouden persoon gaat.
     */
    @SuppressWarnings("unchecked")
    private void bepaalEnZetVerwerkingssoortVoorPersoon(final Long administratieveHandelingId, final PersoonHisVolledigView persoonHisVolledig,
        final SoortBetrokkenheid soortBetrokkenheid)
    {
        final Iterable<? extends HistorieEntiteit> historieEntiteitLijst = bepaalIdentificerendeHistorieEntiteiten(persoonHisVolledig);
        for (final HistorieEntiteit entiteit : historieEntiteitLijst) {
            bepaalEnZetVerwerkingssoort(entiteit, administratieveHandelingId);
        }
        if (soortBetrokkenheid == null) {
            final Verwerkingssoort verwerkingsSoortPersoon =
                berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortPersoon(administratieveHandelingId, persoonHisVolledig);
            persoonHisVolledig.setVerwerkingssoort(verwerkingsSoortPersoon);

            bepaalEnZetVerwerkingssoortOpBetrokkenhedenEnRelaties(persoonHisVolledig.getBetrokkenheden(), administratieveHandelingId);
            // Loop door de nieuwe/aangepaste betrokkenheden, de ongewijzigde zijn hier al uit de lijst gefilterd!
            bepaalEnZetVerwerkingssoortOpOverigeEntiteiten(persoonHisVolledig, administratieveHandelingId);
        } else if (SoortBetrokkenheid.KIND.equals(soortBetrokkenheid)) {
            final Verwerkingssoort verwerkingsSoortPersoon =
                berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortBetrokkenKind(administratieveHandelingId, persoonHisVolledig);
            persoonHisVolledig.setVerwerkingssoort(verwerkingsSoortPersoon);
        } else {
            final Verwerkingssoort verwerkingsSoortPersoon =
                berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortBetrokkenPersoon(administratieveHandelingId, persoonHisVolledig);
            persoonHisVolledig.setVerwerkingssoort(verwerkingsSoortPersoon);
        }
    }

    /**
     * Bepaal en zet verwerkingssoort op overige entiteiten.
     *
     * @param persoonHisVolledig         De persoon his volledig delta view.
     * @param administratieveHandelingId De id van de huidige administratieve handeling.
     */
    @SuppressWarnings("unchecked")
    private void bepaalEnZetVerwerkingssoortOpOverigeEntiteiten(final PersoonHisVolledigView persoonHisVolledig, final Long administratieveHandelingId) {
        for (final HistorieEntiteit historieEntiteit : persoonHisVolledig.getIdentificerendeHistorieRecords()) {
            bepaalEnZetVerwerkingssoort(historieEntiteit, administratieveHandelingId);
        }

        for (final HistorieEntiteit historieEntiteit : persoonHisVolledig.getNietIdentificerendeHistorieRecords()) {
            bepaalEnZetVerwerkingssoort(historieEntiteit, administratieveHandelingId);
        }

        final Iterable<? extends HistorieEntiteit> overigeHistorieEntiteiten = persoonHisVolledig.getNietIdentificerendeHistorieRecords();
        for (final HistorieEntiteit historieEntiteit : overigeHistorieEntiteiten) {
            bepaalEnZetVerwerkingssoort(historieEntiteit, administratieveHandelingId);
        }

        for (final PersoonAdresHisVolledig adresHisVolledig : persoonHisVolledig.getAdressen()) {
            bepaalEnZetVerwerkingssoortOpHistorieSet(adresHisVolledig.getPersoonAdresHistorie(),
                administratieveHandelingId);
        }
        for (final PersoonGeslachtsnaamcomponentHisVolledig geslachtsnaamcomponentHisVolledig : persoonHisVolledig.getGeslachtsnaamcomponenten()) {
            bepaalEnZetVerwerkingssoortOpHistorieSet(geslachtsnaamcomponentHisVolledig.getPersoonGeslachtsnaamcomponentHistorie().getHistorie(),
                                                     administratieveHandelingId);
        }
        for (final PersoonIndicatieHisVolledig indicatieHisVolledig : persoonHisVolledig.getIndicaties()) {
            bepaalEnZetVerwerkingssoortOpHistorieSet(indicatieHisVolledig.getPersoonIndicatieHistorie().getHistorie(),
                    administratieveHandelingId);
        }
        for (final PersoonNationaliteitHisVolledig nationaliteitHisVolledig : persoonHisVolledig.getNationaliteiten()) {
            bepaalEnZetVerwerkingssoortOpHistorieSet(nationaliteitHisVolledig.getPersoonNationaliteitHistorie()
                    .getHistorie(), administratieveHandelingId);
        }
        for (final PersoonReisdocumentHisVolledig reisdocumentHisVolledig : persoonHisVolledig.getReisdocumenten()) {
            bepaalEnZetVerwerkingssoortOpHistorieSet(reisdocumentHisVolledig.getPersoonReisdocumentHistorie()
                    .getHistorie(), administratieveHandelingId);
        }
        for (final PersoonVoornaamHisVolledig voornaamHisVolledig : persoonHisVolledig.getVoornamen()) {
            bepaalEnZetVerwerkingssoortOpHistorieSet(voornaamHisVolledig.getPersoonVoornaamHistorie().getHistorie(),
                administratieveHandelingId);
        }
        for (final PersoonVerificatieHisVolledig verificatieHisVolledig : persoonHisVolledig.getVerificaties()) {
            bepaalEnZetVerwerkingssoortOpHistorieSet(verificatieHisVolledig.getPersoonVerificatieHistorie()
                                                         .getHistorie(), administratieveHandelingId);
        }
    }

    /**
     * Bepaal en zet verwerkingssoort op betrokkenheden.
     *
     * @param betrokkenheden             de betrokkenheden
     * @param administratieveHandelingId de administratieve handeling
     */
    private void bepaalEnZetVerwerkingssoortOpBetrokkenhedenEnRelaties(final Set<BetrokkenheidHisVolledigView> betrokkenheden,
        final Long administratieveHandelingId)
    {
        for (final BetrokkenheidHisVolledig betrokkenheidHisVolledigNiveau1 : betrokkenheden) {
            bepaalEnZetVerwerkingssoortOpBetrokkenheden(administratieveHandelingId, betrokkenheidHisVolledigNiveau1);
            final RelatieHisVolledigView relatieHisVolledigView = (RelatieHisVolledigView) betrokkenheidHisVolledigNiveau1.getRelatie();
            bepaalEnZetVerwerkingssoortOpHistorieSet(relatieHisVolledigView.getAlleHistorieRecords(), administratieveHandelingId);
            relatieHisVolledigView
                .setVerwerkingssoort(berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortVoorRelaties(relatieHisVolledigView,
                                                                                                       administratieveHandelingId));
            for (final BetrokkenheidHisVolledig betrokkenheidHisVolledigNiveau2: relatieHisVolledigView.getBetrokkenheden()) {
                bepaalEnZetVerwerkingssoortOpBetrokkenheden(administratieveHandelingId, betrokkenheidHisVolledigNiveau2);

                // Als er geen betrokken persoon bestaat dient hier ook geen verwerkingssoort voor te worden bepaald.
                if (betrokkenheidHisVolledigNiveau2.getPersoon() != null) {
                    bepaalEnZetVerwerkingssoortVoorPersoon(administratieveHandelingId,
                        (PersoonHisVolledigView) betrokkenheidHisVolledigNiveau2.getPersoon(), betrokkenheidHisVolledigNiveau2.getRol().getWaarde());
                }
            }
        }
    }

    private void bepaalEnZetVerwerkingssoortOpBetrokkenheden(final Long administratieveHandelingId,
        final BetrokkenheidHisVolledig betrokkenheidHisVolledig)
    {
        final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView = (BetrokkenheidHisVolledigView) betrokkenheidHisVolledig;
        bepaalEnZetVerwerkingssoortOpHistorieSet(betrokkenheidHisVolledigView.getAlleHistorieRecords(), administratieveHandelingId);
        betrokkenheidHisVolledigView.setVerwerkingssoort(
            berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortVoorBetrokkenheden(betrokkenheidHisVolledigView, administratieveHandelingId));
    }

    /**
     * Bepaal lijst van historie sets.
     *
     * @param persoonHisVolledig persoon his volledig van betrokkene
     * @return lijst van historie sets
     */
    private Iterable<? extends HistorieEntiteit> bepaalIdentificerendeHistorieEntiteiten(
            final PersoonHisVolledigView persoonHisVolledig)
    {
        return persoonHisVolledig.getIdentificerendeHistorieRecords();
    }

    /**
     * Bepaalt en zet de verwerkingssoort op historie sets.
     *
     * @param historieEntiteiten         Set van historie entiteiten
     * @param administratieveHandelingId De id van de administratieve handeling
     */
    private void bepaalEnZetVerwerkingssoortOpHistorieSet(
            final Iterable<? extends HistorieEntiteit> historieEntiteiten,
            final Long administratieveHandelingId)
    {
        if (historieEntiteiten != null) {
            for (final HistorieEntiteit historieEntiteit : historieEntiteiten) {
                bepaalEnZetVerwerkingssoort(historieEntiteit, administratieveHandelingId);
            }
        }
    }

    /**
     * Bepaalt en zet de verwerkingssoort voor een formele of materiele historie element.
     *
     * @param historieEntiteit           Het materiele of formele historie element.
     * @param administratieveHandelingId De id van de huidige administratieve handeling.
     */
    private void bepaalEnZetVerwerkingssoort(final HistorieEntiteit historieEntiteit, final Long administratieveHandelingId) {
        historieEntiteit.setVerwerkingssoort(berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(historieEntiteit, administratieveHandelingId));
    }
}
