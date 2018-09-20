/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres.BRBY0525;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identiteit.BRAL0207;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.springframework.stereotype.Component;


/**
 * In deze stap worden de regels uitgevoerd die na de verwerking van een bericht uitgevoerd moeten worden.
 */
@Component
public class NaBerichtRegelStap {
    @Inject
    private MeldingFactory meldingFactory;

    @Inject
    private BRAL0207 bral0207;

    @Inject
    private BRBY0525 brby0525;

    /**
     * Voer de stap uit.
     *
     * @param bericht het bijhoudingsbericht
     * @param context de bijhoudingberichtcontext
     * @return het resultaat
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        final Set<ResultaatMelding> meldingen = new HashSet<>();

        for (BerichtIdentificeerbaar overtreder : voerUitBral0207(bericht, context)) {
            meldingen.add(meldingFactory.maakResultaatMelding(bral0207.getRegel(), overtreder, null));
        }

        if (bericht.getSoort().getWaarde().equals(SoortBericht.BHG_VBA_CORRIGEER_ADRES)) {
            for (BerichtIdentificeerbaar overtreder : voerUitBrby0525(bericht, context)) {
                meldingen.add(meldingFactory.maakResultaatMelding(brby0525.getRegel(), overtreder, null));
            }
        }

        return Resultaat.builder().withMeldingen(meldingen).build();
    }

    private List<BerichtIdentificeerbaar> voerUitBrby0525(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        final PersoonHisVolledig persoonHisVolledig =
            (PersoonHisVolledig) context.getBestaandeBijhoudingsRootObjecten().values().iterator().next();
        return brby0525
            .voerRegelUit(bericht, persoonHisVolledig, context.getTijdstipVerwerking());
    }

    private List<BerichtIdentificeerbaar> voerUitBral0207(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        final List<PersoonBericht> alleBerichtPersonen = verzamelAllePersonen(bericht);
        final List<BerichtEntiteit> berichtNietIngeschrevenenMetMeerdereBetrokkenheden =
            context.getNietIngeschrevenBerichtPersonenMetMeerdereBetrokkenheden(alleBerichtPersonen);
        return bral0207
            .voerRegelUit(bericht, berichtNietIngeschrevenenMetMeerdereBetrokkenheden);
    }

    /**
     * Verzamel alle personen die in het bericht voorkomen.
     *
     * @param bericht het bericht
     * @return de lijst met alle personen
     */
    private List<PersoonBericht> verzamelAllePersonen(final BijhoudingsBericht bericht) {
        final List<PersoonBericht> personenInBericht = new ArrayList<>();
        for (final ActieBericht actie : bericht.getAdministratieveHandeling().getActies()) {
            if (actie.getRootObject() instanceof PersoonBericht) {
                personenInBericht.add((PersoonBericht) actie.getRootObject());
            } else if (actie.getRootObject() instanceof RelatieBericht) {
                for (final BetrokkenheidBericht betrokkenheid
                    : ((RelatieBericht) actie.getRootObject()).getBetrokkenheden())
                {
                    personenInBericht.add(betrokkenheid.getPersoon());
                }
            }
        }
        return personenInBericht;
    }

}
