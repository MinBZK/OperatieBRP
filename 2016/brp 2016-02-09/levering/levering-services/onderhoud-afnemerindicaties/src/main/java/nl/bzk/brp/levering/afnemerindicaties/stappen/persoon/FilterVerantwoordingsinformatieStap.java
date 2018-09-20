/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import javax.inject.Inject;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.VerantwoordingsinformatieFilter;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * Stap die de consistentie in de verantwoordingsinformatie onderaan de persoonhisvolledigview filtert, zodat deze alleen de relevante
 * verantwoordingsinformatie toont.
 * <p/>
 * VR00086: In het resultaat  van een levering mogen geen verantwoordingsgroepen ‘Actie’ worden opgenomen waarnaar geen enkele verwijzing bestaat vanuit
 * een inhoudelijke groep in hetzelfde resultaat. Dit betekent dat wanneer een Actie alleen groepen heeft geraakt die door autorisatie of een ander
 * filtermechanisme niet in het bericht komen, de Actie dus niets meer 'verantwoordt' en zelf ook uit het bericht verwijderd moet worden.
 * <p/>
 * VR00087: In het resultaat van een levering mogen geen verantwoordingsgroepen ‘Administratieve handeling’ en onderliggende groepen ‘Bron’ en ‘Document’
 * worden opgenomen als er binnen die Administratieve handeling  geen enkele Actie voorkomt waarvoor een verwijzing bestaat vanuit een inhoudelijke groep
 * uit hetzelfde resultaat.
 */
@Regels({ Regel.VR00086, Regel.VR00087 })
public class FilterVerantwoordingsinformatieStap extends
    AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht, OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerantwoordingsinformatieFilter verantwoordingsinformatieFilter;

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht onderwerp, final OnderhoudAfnemerindicatiesBerichtContext context,
        final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        final VolledigBericht volledigBericht = context.getVolledigBericht();
        final PersoonHisVolledigView persoonHisVolledigView = volledigBericht.getAdministratieveHandeling().getBijgehoudenPersonen().get(0);

        LOGGER.debug("Filteren van verantwoordingsinformatie voor persoon {}", persoonHisVolledigView.getID());
        verantwoordingsinformatieFilter.filter(persoonHisVolledigView, context.getLeveringinformatie());

        return DOORGAAN;
    }
}
