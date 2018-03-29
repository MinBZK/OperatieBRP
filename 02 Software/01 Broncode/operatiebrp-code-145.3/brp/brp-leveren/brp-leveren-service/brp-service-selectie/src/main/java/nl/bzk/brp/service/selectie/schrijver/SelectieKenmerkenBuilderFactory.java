/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.domain.berichtmodel.SelectieKenmerken;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;

/**
 * SelectieKenmerkenBuilderFactory.
 */
public final class SelectieKenmerkenBuilderFactory {


    private SelectieKenmerkenBuilderFactory() {
    }

    /**
     * @param toegangLeveringsAutorisatie toegangLeveringsAutorisatie
     * @param dienst dienst
     * @param maakSelectieResultaatTaak maakSelectieResultaatTaak
     * @param volgnummer volgnummer
     * @return selectiekenmerken builder
     */
    static SelectieKenmerken.Builder maak(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final Dienst dienst,
                                          final MaakSelectieResultaatTaak maakSelectieResultaatTaak, final Integer volgnummer) {
        return SelectieKenmerken.builder()
                .metDienst(dienst)
                .metLeveringsautorisatie(toegangLeveringsAutorisatie.getLeveringsautorisatie())
                .metSelectietaakId(maakSelectieResultaatTaak.getSelectietaakId())
                .metDatumUitvoer(maakSelectieResultaatTaak.getDatumUitvoer())
                .metPeilmomentMaterieelResultaat(maakSelectieResultaatTaak.getPeilmomentMaterieelResultaat())
                .metPeilmomentFormeelResultaat(maakSelectieResultaatTaak.getPeilmomentFormeelResultaat())
                .metSoortSelectie(maakSelectieResultaatTaak.getSoortSelectie())
                .metSoortSelectieresultaatVolgnummer(volgnummer);
    }
}
