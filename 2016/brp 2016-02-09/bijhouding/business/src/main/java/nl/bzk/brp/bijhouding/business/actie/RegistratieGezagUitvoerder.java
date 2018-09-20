/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.OuderlijkGezagGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.gezagderde.GezagDerdeGroepVerwerker;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.util.RelatieUtils;



/**
 * Uitvoerder voor mededeling van gezagsregister.
 * Dit is niet een 'pure' registratie indicatie uitvoerder. Er kan namelijk in het bericht zitten:
 * - een indicatie gezag derde
 * - een of meerdere ouderlijk gezag groepen
 * - allebei
 *
 * Gezien de overlap is het toch handig om van AbstractRegistratieIndicatieUitvoerder te erven.
 */
public final class RegistratieGezagUitvoerder extends AbstractRegistratieIndicatieUitvoerder {

    @Override
    protected void verzamelVerwerkingsregels() {
        // Als er een indicatie in het bericht aanwezig is, moet het de gezag derde indicatie zijn.
        if (getBerichtRootObject().getIndicaties() != null && getBerichtRootObject().getIndicaties().size() > 0) {
            PersoonIndicatieDerdeHeeftGezagHisVolledigImpl indicatieHisVolledig =
                    getPersoonHisVolledig().getIndicatieDerdeHeeftGezag();
            if (indicatieHisVolledig == null) {
                indicatieHisVolledig = new PersoonIndicatieDerdeHeeftGezagHisVolledigImpl(getPersoonHisVolledig());
                getPersoonHisVolledig().setIndicatieDerdeHeeftGezag(indicatieHisVolledig);
            }
            this.voegVerwerkingsregelToe(new GezagDerdeGroepVerwerker(
                    getPersoonIndicatieBericht(), indicatieHisVolledig, getActieModel()));
        }
        // Als er een betrokkenheid in het bericht aanwezig is, dan zijn er via kind -> fam.recht.betr.
        // -> betrokkenheden een of meer ouder betrokkenheden aanwezig.
        if (getBerichtRootObject().getBetrokkenheden() != null) {
            final List<Ouder> ouderBetrokkenheden = RelatieUtils.haalOuderBetrokkenhedenUitRelatie(
                    getBerichtRootObject().getBetrokkenheden().iterator().next().getRelatie());
            for (final Ouder ouderBetrokkenheid : ouderBetrokkenheden) {
                final OuderBericht ouderBericht = (OuderBericht) ouderBetrokkenheid;
                final OuderHisVolledigImpl ouderHisVolledig = zoekOuderHisVolledig(getModelRootObject(), ouderBericht);
                this.voegVerwerkingsregelToe(new OuderlijkGezagGroepVerwerker(
                        ouderBericht, ouderHisVolledig, getActieModel()));
                // Let op: de personen die bij deze betrokkenheid horen zijn de ouders die het ouderlijk gezag
                // krijgen / verliezen. Dit zijn echter geen bijgehouden personen, omdat het ouderlijk gezag
                // op de juridische PL van het kind staat. Laat deze personen dus weg uit de bijgehouden personen.
                this.getContext().voegWelInBerichtMaarNietBijgehoudenPersoonToe(ouderHisVolledig.getPersoon());
            }
        }
    }

    /**
     * Zoek de ouder his volledig met het id van de technische sleutel uit het bericht.
     *
     * @param kind het kind (de persoon)
     * @param ouderBericht de ouder uit het bericht (de betrokkenheid)
     * @return de ouder his volledig met de juiste technische sleutel
     */
    private OuderHisVolledigImpl zoekOuderHisVolledig(
            final PersoonHisVolledigImpl kind, final OuderBericht ouderBericht)
    {
        OuderHisVolledigImpl ouderHisVolledig = null;

        for (final OuderHisVolledigImpl betrokkenheidHisVolledig : kind.getKindBetrokkenheid().getRelatie().getOuderBetrokkenheden())
        {
            if (ouderBericht.getObjectSleutel().equals(betrokkenheidHisVolledig.getID().toString())) {
                ouderHisVolledig = betrokkenheidHisVolledig;
                break;
            }
        }

        if (ouderHisVolledig == null) {
            throw new IllegalStateException("Geen ouder betrokkenheid gevonden met sleutel: '"
                    + ouderBericht.getObjectSleutel() + "'");
        }
        return ouderHisVolledig;
    }
}
