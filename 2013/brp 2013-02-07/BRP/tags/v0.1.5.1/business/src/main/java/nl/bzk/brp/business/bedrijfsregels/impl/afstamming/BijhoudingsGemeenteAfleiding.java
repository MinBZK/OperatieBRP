/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsGemeente;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.lang.BooleanUtils;


/**
 * Afleidingsregel: ten tijde van geboorte; Bijhouding- en Adreshistorie kind volgt situatie moeder vanaf geboortedatum.
 *
 */
public class BijhoudingsGemeenteAfleiding implements BedrijfsRegel<PersistentRelatie, Relatie> {

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BijhoudingsGemeenteAfleiding";
    }

    @Override
    public Melding executeer(final PersistentRelatie huidigeSituatie, final Relatie nieuweSituatie,
            final Integer datumAanvangGeldigheid)
    {

        Melding melding = null;
        PersistentPersoon ouder = null;

        for (Betrokkenheid betrokkenheid : nieuweSituatie.getOuderBetrokkenheden()) {
            if (BooleanUtils.isTrue(betrokkenheid.isIndAdresGevend())) {
                if (betrokkenheid.getBetrokkene().getIdentificatienummers() != null) {
                    ouder = persoonRepository.findByBurgerservicenummer(
                            betrokkenheid.getBetrokkene().getIdentificatienummers()
                                    .getBurgerservicenummer());
                }

                if (ouder != null) {
                    PersoonBijhoudingsGemeente bijhoudingsGemeente = new PersoonBijhoudingsGemeente();
                    bijhoudingsGemeente.setDatumInschrijving(datumAanvangGeldigheid);
                    // TODO nog niet in scope, voorlopig op false gezet
                    bijhoudingsGemeente.setIndOnverwerktDocumentAanwezig(false);
                    bijhoudingsGemeente.setGemeente(ouder.getBijhoudingsGemeente());

                    nieuweSituatie.getKindBetrokkenheid().getBetrokkene().setBijhoudingGemeente(bijhoudingsGemeente);
                } else {
                    if (betrokkenheid.getBetrokkene().getIdentificatienummers() != null) {
                        melding =
                                new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001,
                                        "Kan ouder niet vinden met BSN: "
                                    + betrokkenheid.getBetrokkene().getIdentificatienummers().getBurgerservicenummer());
                    } else {
                        melding =
                                new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001,
                                        "Het BSN van de ouder is niet opgegeven.");
                    }
                }

                break;
            }
        }

        return melding;
    }

}
