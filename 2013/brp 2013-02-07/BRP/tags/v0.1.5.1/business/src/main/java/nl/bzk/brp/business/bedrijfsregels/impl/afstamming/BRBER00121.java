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


/**
 * Implementatie van bedrijfsregel BRBER00121.
 * Bedrijfsregel die controleert of een BSN dat bij een nieuwe inschrijving is opgegeven niet reeds in gebruik is.
 *
 * @brp.bedrijfsregel BRBER00121
 */
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
     * Controleer de relatie.
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
     * Controleer de persoon: controleer of het BSN van de persoon niet al in gebruik is.
     *
     * @param persoon Persoon
     * @return melding
     */
    private Melding executeer(final Persoon persoon) {
        Melding melding = null;
        if (persoon != null && persoon.getIdentificatienummers() != null
            && persoonRepository.isBSNAlIngebruik(persoon.getIdentificatienummers().getBurgerservicenummer()))
        {
            melding = new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.BRBER00121);
        }
        return melding;
    }

}
