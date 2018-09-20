/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.PersistentRootObject;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;


/** Bedrijfsregel die controleert dat een BSN maar een keer gebruikt wordt bij een inschrijving. */
public class BRBER00121 implements BedrijfsRegel<PersistentRootObject, RootObject> {

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BRBER00121";
    }

    @Override
    public Melding executeer(final PersistentRootObject huidigeSituatie, final RootObject nieuweSituatie,
            final Integer datumAanvangGeldigheid)
    {
        Melding melding = null;
        if (nieuweSituatie instanceof Relatie) {
            melding = executeer((Relatie) nieuweSituatie);
        } else {
            throw new IllegalArgumentException("Alleen object van het type Relatie is hier toegestaan.");
        }
        return melding;
    }

    /**
     * Controlleer de relatie.
     *
     * @param relatie Relatie
     * @return melding
     */
    private Melding executeer(final Relatie relatie) {
        Melding melding = null;
        if (relatie.getKindBetrokkenheid() != null) {
            melding = executeer(relatie.getKindBetrokkenheid().getBetrokkene());
        }
        return melding;
    }

    /**
     * Controlleer de persoon.
     *
     * @param persoon Persoon
     * @return melding
     */
    private Melding executeer(final Persoon persoon) {
        Melding melding = null;
        if (persoon != null
            && persoonRepository.isBSNAlIngebruik(persoon.getIdentificatienummers().getBurgerservicenummer()))
        {
            melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBER00121, "BSN is reeds aanwezig.");
        }
        return melding;
    }

}
