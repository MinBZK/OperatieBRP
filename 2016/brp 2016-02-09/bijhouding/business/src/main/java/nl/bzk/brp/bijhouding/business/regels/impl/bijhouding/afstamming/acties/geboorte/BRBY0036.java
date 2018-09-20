/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.List;

import javax.inject.Named;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


/**
 * Vader moet voldoen aan kandidaatregels.
 * <p/>
 * Als volgens de kandidaatregels een kandidaat vader is geregistreerd in de BRP, dan en slechts dan moet door Actie
 * RegistratieGeboorte het Ouderschap van deze vader worden geregistreerd.
 * <p/>
 * Opmerkingen: 1. Vader is de Persoon waarbij de indicatie OuderUitWieHetKindIsVoortgekomen ontbreekt in de groep
 * Ouderschap. 2. Melding [1] van toepassing als in het Bericht geen vader is opgenomen terwijl die volgens de
 * kandidaatregels wel bestaat, bij elke andere foutsituatie is [2] van toepassing. 3. I.c.m. BRBY0147 voorkomt deze
 * regel tevens dat ErkenningNaGeboorte wordt uitgevoerd terwijl er wel degelijk een juridisch vader is. 4. Als er
 * volgens de kandidaatregels meerdere kandidaten zijn, dan moet één daarvan als vader worden geregistreerd.
 * <p/>
 * In deze klasse wordt punt 4 getoetst.
 *
 * @brp.bedrijfsregel BRBY0033
 */
@Named("BRBY0036")
public class BRBY0036 extends AbstractKandidaatVaderRegel {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0036;
    }

    @Override
    protected boolean overtreedtKandidaatVaderGesteldeVoorwaarden(final List<PersoonView> kandidatenVaders,
            final PersoonBericht vader)
    {
        return kandidatenVaders.size() > 0 && vader == null;
    }
}
