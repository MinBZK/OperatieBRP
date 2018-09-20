/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;

/**
 * Elke gevulde DatumAanvangGeldigheid van een Persoon in een Groep met MateriëleHistorie, moet op of ná DatumGeboorte
 * van die Persoon liggen.
 * Opmerking:
 * 1.De regel is ook van toepassing op Groepen van Betrokkenheid of Relatie  waarin de Persoon een rol speelt.
 */
@Named("BRAL2203")
public class BRAL2203 implements VoorActieRegelMetMomentopname<ModelRootObject, BerichtRootObject> {

    @Override
    public final Regel getRegel() {
        return Regel.BRAL2203;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final ModelRootObject huidigeSituatie,
                                              final BerichtRootObject nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid = actie.getDatumAanvangGeldigheid();
        final List<Persoon> teControlerenPersonen = new ArrayList<>();

        for (final RootObject rootObject : Arrays.asList(huidigeSituatie, nieuweSituatie)) {
            if (rootObject != null) {
                if (rootObject instanceof Relatie) {
                    final Relatie relatie = (Relatie) rootObject;
                    for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
                        teControlerenPersonen.add(betrokkenheid.getPersoon());
                    }
                } else if (rootObject instanceof Persoon) {
                    teControlerenPersonen.add((Persoon) rootObject);
                }
            }
        }

        for (final Persoon persoon : teControlerenPersonen) {
            if (regelWordtOvertredenVoorPersoon(persoon, datumAanvangGeldigheid)) {
                objectenDieDeRegelOvertreden.add((ActieBericht) actie);
                break;
            }
        }
        return objectenDieDeRegelOvertreden;
    }

    /**
     * Checkt of de regel wordt overtreden voor persoon.
     * @param persoon de te controleren persoon
     * @param datumAanvangGeldigheidActie datum aanvang geldigheid van de actie
     * @return true indien de regel wordt overtreden, anders false
     */
    private boolean regelWordtOvertredenVoorPersoon(final Persoon persoon,
                                                    final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheidActie)
    {
        boolean resultaat = false;
        if (persoon.getGeboorte() != null
                && persoon.getGeboorte().getDatumGeboorte() != null
                && persoon.getGeboorte().getDatumGeboorte().na(datumAanvangGeldigheidActie))
        {
            resultaat = true;
        }
        return resultaat;
    }

}
