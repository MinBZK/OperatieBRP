/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.ActieBron;


/**
 * BRBY0593: Land/gebied migratie is verplicht tenzij VOW.
 * In de groep Migratie mag Land/gebied geen waarde hebben als Soort de waarde "Emigratie" heeft
 * én de groep wordt verantwoord door AmbtshalveBesluitVerblijfplaats, anders is Land/gebied juist verplicht.
 *
 * Opmerking:
 * Een ambtshalve emigratie betreft een zogenaamd "Vertrek onbekend waarheen"; dan mag Land/gebied juist niet gevuld
 * worden.
 *
 * @brp.bedrijfsregel BRBY0593
 */
@Named("BRBY0593")
public class BRBY0593 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView persoonView, final PersoonBericht persoonBericht,
            final Actie actie, final Map<String, PersoonView> stringPersoonViewMap)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (SoortMigratie.EMIGRATIE == persoonBericht.getMigratie().getSoortMigratie().getWaarde()
            && persoonBericht.getMigratie().getLandGebiedMigratie() == null
            && !actieWordtVerantwoordDoorAmbtshalveBesluit(actie))
        {
            objectenDieDeRegelOvertreden.add(persoonBericht);
        }
        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of de actie wordt verantwoord door een ambtshalve besluit verblijfplaats.
     *
     * @param actie de actie waarvoor het verantwoordingsdocument wordt gecontroleerd
     * @return true indien verantwoording een ambtshalve besluit verblijfplaats betreft, anders false
     */
    private boolean actieWordtVerantwoordDoorAmbtshalveBesluit(final Actie actie) {
        boolean resultaat = false;
        if (actie.getBronnen() != null) {
            for (ActieBron actieBron : actie.getBronnen()) {
                // Bron kan een rechtsgrond zijn!
                if (actieBron.getDocument() != null
                    && actieBron.getDocument().getSoort().getWaarde().getNaam()
                            .equals(NaamEnumeratiewaardeAttribuut.DOCUMENT_NAAM_AMBTSHALVE_BESLUIT_VERBLIJFPLAATS))
                {
                    resultaat = true;
                }
            }
        }
        return resultaat;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0593;
    }
}
