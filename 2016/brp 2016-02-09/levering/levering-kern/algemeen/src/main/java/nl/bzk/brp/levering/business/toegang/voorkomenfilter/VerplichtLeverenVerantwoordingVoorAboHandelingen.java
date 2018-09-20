/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.voorkomenfilter;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;

/**
 * Als de bij de ActieInhoud, ActieAanpassingGeldigheid en/of ActieVerval behorende administratieve handeling een Partij heeft waarbij een Partij/Rol
 * voorkomt met Rol = (4) ‘Bestuursorgaan Minister’ (de persoon wordt onderhouden door een ABO), dan moeten de velden ActieInhoud,
 * ActieAanpassingGeldigheid en ActieVerval in de groep/het bericht aanwezig blijven. Hierdoor wordt gegarandeerd dat de bijbehorende administratieve
 * handeling (incl. Partij en Bron) wordt geleverd.
 */
@Regels(value = { Regel.VR00075, Regel.VR00083 })
public interface VerplichtLeverenVerantwoordingVoorAboHandelingen {

    /**
     * Voert de regel uit
     *
     * @param totaleLijstVanHisElementenOpPersoonsLijst de totale lijst van his elementen
     * @param administratieveHandelingen                de administratieve handelingen.
     */
    void voerRegelUit(List<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst,
        List<AdministratieveHandelingHisVolledig> administratieveHandelingen);
}
