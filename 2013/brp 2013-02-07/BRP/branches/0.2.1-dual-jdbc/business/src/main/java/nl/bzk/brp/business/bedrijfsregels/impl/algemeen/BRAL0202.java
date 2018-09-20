/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.algemeen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;


/** Een persoon die bij een relatie betrokken is heeft precies een rol (een Soort betrokkenheid). */
public class BRAL0202 implements ActieBedrijfsRegel<Relatie> {

    @Override
    public String getCode() {
        return "BRAL0202";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie huwelijk, final Actie actie) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        final Set<Burgerservicenummer> bsnInRelatie = new HashSet<Burgerservicenummer>();

        for (Betrokkenheid betrokkenheid : huwelijk.getBetrokkenheden()) {
            // TODO: regel werkt alleen op personen met een BSN, maar zou ook voor personen zonder BSN moeten werken
            if (betrokkenheid.getBetrokkene() != null && !bsnInRelatie
                .add(betrokkenheid.getBetrokkene().getIdentificatienummers().getBurgerservicenummer()))
            {
                meldingen.add(new Melding(Soortmelding.FOUT, MeldingCode.BRAL0202,
                    (Identificeerbaar) betrokkenheid.getBetrokkene(), "persoon"));
            }
        }
        return meldingen;
    }
}
