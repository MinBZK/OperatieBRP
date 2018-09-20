/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;


/**
 * De implementatie van het filter om de verantwoordingsinformatie te filteren op relevante verantwoordingsinformatie.
 * <p/>
 * VR00086: In het resultaat van een levering mogen geen verantwoordingsgroepen ‘Actie’ worden opgenomen waarnaar geen enkele verwijzing bestaat vanuit een
 * inhoudelijke groep in hetzelfde resultaat. Dit betekent dat wanneer een Actie alleen groepen heeft geraakt die door autorisatie of een ander
 * filtermechanisme niet in het bericht komen, de Actie dus niets meer 'verantwoordt' en zelf ook uit het bericht verwijderd moet worden.
 * <p/>
 * VR00087: In het resultaat van een levering mogen geen verantwoordingsgroepen ‘Administratieve handeling’ en onderliggende groepen ‘Bron’ en ‘Document’
 * worden opgenomen als er binnen die Administratieve handeling geen enkele Actie voorkomt waarvoor een verwijzing bestaat vanuit een inhoudelijke groep
 * uit hetzelfde resultaat.
 * <p/>
 * VR00095: In een Inhoudelijke groep van een mutatiebericht worden alleen actieverwijzingen opgenomen die samenhangen met de onderhanden Administratieve
 * handeling.
 */
@Regels({ Regel.VR00086, Regel.VR00087, Regel.VR00095 })
public interface MutatieLeveringVerantwoordingsinformatieFilter {

    /**
     * Filtert de verantwoordingsinformatie op de persoon his volledig view voor mutatie levering.
     *
     * @param persoonHisVolledigView     de persoon his volledig view
     * @param administratieveHandelingId id van de administratieve handeling voor de mutatie levering.
     * @param leveringAutorisatie        de leveringautorisatie
     */
    void filter(PersoonHisVolledigView persoonHisVolledigView, Long administratieveHandelingId, Leveringinformatie leveringAutorisatie);
}
