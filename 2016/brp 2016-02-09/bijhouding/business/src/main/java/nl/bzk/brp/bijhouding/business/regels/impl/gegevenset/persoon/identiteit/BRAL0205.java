/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identiteit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * Voor een Persoon van de soort Niet-Ingeschrevene zijn alleen de groepen Identiteit, Geslachtsaanduiding,
 * Geboorte en Samengestelde naam toegestaan en bovendien verplicht.
 *
 * Let op: deze bedrijfsregel staat nog ter discussie. De xsd staat namelijk ook de groep identificatienummers toe,
 * maar wanneer deze meegegeven wordt, zal deze bedrijfsregel afgaan. Dit is waarschijnlijk niet de bedoeling en dit
 * wordt door functioneel uitgezocht.
 *
 * @brp.bedrijfsregel BRAL0205
 */
@Named("BRAL0205")
public class BRAL0205 implements VoorActieRegelMetMomentopname<ModelRootObject, BerichtRootObject> {

    @Override
    public final Regel getRegel() {
        return Regel.BRAL0205;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final ModelRootObject huidigeSituatie,
            final BerichtRootObject nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (nieuweSituatie instanceof PersoonBericht) {
            if (((PersoonBericht) nieuweSituatie).getSoort().getWaarde() == SoortPersoon.NIET_INGESCHREVENE
                    && !isValideNietIngeschreve((PersoonBericht) nieuweSituatie))
            {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        } else if (nieuweSituatie instanceof RelatieBericht) {
            for (BetrokkenheidBericht betrokkenheid : ((RelatieBericht) nieuweSituatie).getBetrokkenheden()) {
                if (betrokkenheid.getPersoon().getSoort().getWaarde() == SoortPersoon.NIET_INGESCHREVENE
                        && !isValideNietIngeschreve(betrokkenheid.getPersoon()))
                {
                    objectenDieDeRegelOvertreden.add(betrokkenheid.getPersoon());
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleer of de niet ingeschrevene een valide persoon is volgende regels over de groepen.
     * De verplichte groepen moeten aanwezig zijn en geen enkele andere groep.
     *
     * @param persoon de persoon
     * @return of de persoon valide is
     */
    private boolean isValideNietIngeschreve(final PersoonBericht persoon) {
        boolean geslachtsAanduidingAanwezig = false;
        boolean geboorteAanwezig = false;
        boolean samengesteldeNaamAanwezig = false;
        boolean andereGroepAanwezig = false;
        final List<BerichtEntiteitGroep> berichtEntiteitGroepen = persoon.getBerichtEntiteitGroepen();

        for (BerichtEntiteitGroep berichtEntiteitGroep : berichtEntiteitGroepen) {
            if (berichtEntiteitGroep != null) {
                if (berichtEntiteitGroep instanceof PersoonSamengesteldeNaamGroepBericht) {
                    samengesteldeNaamAanwezig = true;
                } else if (berichtEntiteitGroep instanceof PersoonGeboorteGroepBericht) {
                    geboorteAanwezig = true;
                } else if (berichtEntiteitGroep instanceof PersoonGeslachtsaanduidingGroepBericht) {
                    geslachtsAanduidingAanwezig = true;
                } else if (!(berichtEntiteitGroep instanceof PersoonIdentificatienummersGroepBericht)) {
                    andereGroepAanwezig = true;
                }
            }
        }
        return !andereGroepAanwezig && geslachtsAanduidingAanwezig && geboorteAanwezig && samengesteldeNaamAanwezig;
    }

}
