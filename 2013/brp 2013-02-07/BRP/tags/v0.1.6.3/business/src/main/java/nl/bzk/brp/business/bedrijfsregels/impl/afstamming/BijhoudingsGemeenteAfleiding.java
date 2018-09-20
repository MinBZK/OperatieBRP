/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonMdlRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsGemeenteGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;


/**
 * Afleidingsregel: ten tijde van geboorte; Bijhouding- en Adreshistorie kind volgt situatie moeder vanaf
 * geboortedatum.
 */
public class BijhoudingsGemeenteAfleiding implements BedrijfsRegel<Relatie> {

    @Inject
    private PersoonMdlRepository persoonRepository;

    @Override
    public String getCode() {
        return "BijhoudingsGemeenteAfleiding";
    }

    @Override
    public Melding executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
        final Datum datumAanvangGeldigheid)
    {

        Melding melding = null;
        PersoonModel ouder = null;

        for (Betrokkenheid betrokkenheid : nieuweSituatie.getOuderBetrokkenheden()) {
            if (betrokkenheid.getBetrokkenheidOuderschap() != null
                && Ja.Ja == betrokkenheid.getBetrokkenheidOuderschap().getIndAdresGevend())
            {
                if (betrokkenheid.getBetrokkene().getIdentificatieNummers() != null) {
                    ouder = persoonRepository.findByBurgerservicenummer(
                        betrokkenheid.getBetrokkene().getIdentificatieNummers()
                                     .getBurgerServiceNummer());
                }

                if (ouder != null) {
                    final PersoonBijhoudingsGemeenteGroepBericht bijhGem =
                        new PersoonBijhoudingsGemeenteGroepBericht();
                    bijhGem.setDatumInschrijvingInGemeente(datumAanvangGeldigheid);
                    bijhGem.setIndOnverwerktDocumentAanwezig(JaNee.Nee);
                    bijhGem.setBijhoudingsGemeente(ouder.getBijhoudenGemeente().getBijhoudingsGemeente());

                    ((RelatieBericht) nieuweSituatie).getKindBetrokkenheid().getBetrokkene().setBijhoudenGemeente(
                        bijhGem);
                } else {
                    if (betrokkenheid.getBetrokkene().getIdentificatieNummers() != null) {
                        melding = new Melding(
                            SoortMelding.FOUT_ONOVERRULEBAAR,
                            MeldingCode.REF0001,
                            "Kan ouder niet vinden met BSN: "
                                + betrokkenheid.getBetrokkene().getIdentificatieNummers()
                                    .getBurgerServiceNummer().getWaarde(),
                            (Identificeerbaar) betrokkenheid.getBetrokkene().getIdentificatieNummers(),
                            "burgerservicenummer");
                    } else {
                        melding =
                            new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001,
                                "Het BSN van de ouder is niet opgegeven."
                                , (Identificeerbaar) betrokkenheid.getBetrokkene()
                                , "burgerservicenummer");
                    }
                }

                break;
            }
        }

        return melding;
    }

}
