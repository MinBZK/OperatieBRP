/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;

/**
 * Service voor het bepalen van BAG-sleutel (Adres.IdentificatiecodeNummeraanduiding) van het adres van een persoon op een materieel peilmoment ahv een
 * identificatiecriterium (bsn, anr of objectsleutel). De BAG-sleutel wordt alleen geleverd indien het identificatiecriterium herleidbaar is tot één
 * persoon die op peildatum : (1) niet overleden is, (2) ingeschreven staat op een Nederlands adres en (3) niet-vervallen is.
 */
@FunctionalInterface
interface GeefMedebewonersBepaalBAGSleutelService {

    /**
     * Bepaalt de BAG sleutel op basis van een {@link Persoonslijst}.
     * @param persoonslijst de persoonslijst
     * @param materieelPeilmoment het materiele peilmoment
     * @return BAG-sleutel (Persoon.Adres.IdentificatiecodeNummeraanduiding)
     * @throws StapMeldingException inndien BAG sleutel bepaling faalt
     */
    String bepaalBAGSleutel(Persoonslijst persoonslijst, Integer materieelPeilmoment) throws StapMeldingException;

}
