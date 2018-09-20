/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;


/**
 * Implementatie van bedrijfsregel BRBER00121.
 * Bedrijfsregel die controleert of een BSN dat bij een nieuwe inschrijving is opgegeven niet reeds in gebruik is.
 *
 * @brp.bedrijfsregel BRBER00121
 */
public class BRBER00121 implements ActieBedrijfsRegel<RootObject> {

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BRBER00121";
    }

    @Override
    public List<Melding> executeer(final RootObject huidigeSituatie, final RootObject nieuweSituatie,
        final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();
        if (nieuweSituatie instanceof Relatie) {
            Melding melding = executeer((Relatie) nieuweSituatie);
            if (melding != null) {
                meldingen.add(melding);
            }
        } else {
            throw new IllegalArgumentException("Alleen object van het type Relatie is hier toegestaan.");
        }
        return meldingen;
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
            melding = new Melding(SoortMelding.FOUT, MeldingCode.BRBER00121,
                (Identificeerbaar) persoon.getIdentificatienummers(), "burgerservicenummer");
        }
        return melding;
    }

}
