/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import javax.validation.Valid;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;


/**
 * Bericht voor opvragen van de personen die op hetzelfde adres wonen en hun onderlinge relaties.
 */
@SuppressWarnings("serial")
public class VraagKandidaatVaderBericht extends AbstractBevragingsBericht {

    @Valid
    private KandidaatVaderVraag vraag;

    public VraagKandidaatVaderBericht() {
        super(SoortBericht.A_L_G_BEPAAL_KANDIDAAT_VADER_VI);
    }

    @Override
    public KandidaatVaderVraag getVraag() {
        return vraag;
    }

}
