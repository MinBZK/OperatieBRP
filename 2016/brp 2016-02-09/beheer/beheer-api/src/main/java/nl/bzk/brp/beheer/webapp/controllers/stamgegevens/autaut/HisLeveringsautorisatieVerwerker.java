/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Date;
import java.util.Set;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.VergelijkingUtil;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.beheer.autaut.HisLeveringsautorisatie;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;

/**
 * Leveringsautorisatie historie verwerker.
 */
public class HisLeveringsautorisatieVerwerker extends AbstractHistorieVerwerker<Leveringsautorisatie, HisLeveringsautorisatie> {

    @Override
    public final HisLeveringsautorisatie maakHistorie(final Leveringsautorisatie item) {
        // Controle inhoudelijk leeg
        if (VergelijkingUtil.and(
                item.getPopulatiebeperking() == null,
                item.getProtocolleringsniveau() == null,
                item.getDatumIngang() == null,
                item.getDatumEinde() == null,
                item.getToelichting() == null))
        {
            return null;
        }

        final HisLeveringsautorisatie historie = new HisLeveringsautorisatie();

        // A-laag
        historie.setLeveringsautorisatie(item);

        // Gegevens
        historie.setPopulatiebeperking(item.getPopulatiebeperking());
        historie.setProtocolleringsniveau(item.getProtocolleringsniveau());
        historie.setDatumIngang(item.getDatumIngang());
        historie.setDatumEinde(item.getDatumEinde());
        historie.setIndicatieAliasSoortAdministratieveHandelingLeveren(item.getIndicatieAliasSoortAdministratieveHandelingLeveren());
        historie.setIndicatiePopulatiebeperkingVolledigGeconverteerd(item.getIndicatiePopulatiebeperkingVolledigGeconverteerd());
        historie.setToelichting(item.getToelichting());

        // Historie
        historie.setDatumTijdRegistratie(new DatumTijdAttribuut(new Date()));

        return historie;
    }

    @Override
    public final boolean isHistorieInhoudelijkGelijk(final HisLeveringsautorisatie nieuweHistorie, final HisLeveringsautorisatie actueleRecord) {
        return VergelijkingUtil.and(
                VergelijkingUtil.isEqual(nieuweHistorie.getPopulatiebeperking(), actueleRecord.getPopulatiebeperking()),
                VergelijkingUtil.isEqual(nieuweHistorie.getProtocolleringsniveau(), actueleRecord.getProtocolleringsniveau()),
                VergelijkingUtil.isEqual(nieuweHistorie.getDatumIngang(), actueleRecord.getDatumIngang()),
                VergelijkingUtil.isEqual(nieuweHistorie.getDatumEinde(), actueleRecord.getDatumEinde()),
                VergelijkingUtil.isEqual(
                        nieuweHistorie.getIndicatieAliasSoortAdministratieveHandelingLeveren(),
                        actueleRecord.getIndicatieAliasSoortAdministratieveHandelingLeveren()),
                VergelijkingUtil.isEqual(
                        nieuweHistorie.getIndicatiePopulatiebeperkingVolledigGeconverteerd(),
                        actueleRecord.getIndicatiePopulatiebeperkingVolledigGeconverteerd()),
                VergelijkingUtil.isEqual(nieuweHistorie.getToelichting(), actueleRecord.getToelichting()));
    }

    @Override
    public final Set<HisLeveringsautorisatie> geefHistorie(final Leveringsautorisatie item) {
        return item.getHisLeveringsautorisatieLijst();
    }

    @Override
    public final void kopieerHistorie(final Leveringsautorisatie item, final Leveringsautorisatie managedItem) {
        managedItem.setPopulatiebeperking(item.getPopulatiebeperking());
        managedItem.setProtocolleringsniveau(item.getProtocolleringsniveau());
        managedItem.setDatumIngang(item.getDatumIngang());
        managedItem.setDatumEinde(item.getDatumEinde());
        managedItem.setIndicatieAliasSoortAdministratieveHandelingLeveren(item.getIndicatieAliasSoortAdministratieveHandelingLeveren());
        managedItem.setIndicatiePopulatiebeperkingVolledigGeconverteerd(item.getIndicatiePopulatiebeperkingVolledigGeconverteerd());
        managedItem.setToelichting(item.getToelichting());
    }
}
