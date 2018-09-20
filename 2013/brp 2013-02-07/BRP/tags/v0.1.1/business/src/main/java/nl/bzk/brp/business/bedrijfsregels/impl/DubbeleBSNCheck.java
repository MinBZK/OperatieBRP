/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;

/**
 * Bedrijfsregel die controleert dat een BSN maar een keer gebruikt wordt bij een inschrijving.
 */
public class DubbeleBSNCheck implements BedrijfsRegel<PersistentPersoon, Persoon> {

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "TMP01";
    }

    @Override
    public Melding executeer(final PersistentPersoon huidigeSituatie, final Persoon nieuweSituatie) {
        Melding melding = null;
        if (persoonRepository.isBSNAlIngebruik(nieuweSituatie.getIdentificatienummers().getBurgerservicenummer())) {
            melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.REF0010, "BSN is reeds aanwezig.");
        }
        return melding;
    }
}
