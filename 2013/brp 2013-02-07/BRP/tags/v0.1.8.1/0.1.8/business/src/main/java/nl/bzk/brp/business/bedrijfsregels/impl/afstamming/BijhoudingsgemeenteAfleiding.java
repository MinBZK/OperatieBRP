/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsgemeenteGroepBericht;
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
public class BijhoudingsgemeenteAfleiding implements BedrijfsRegel<Relatie> {

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BijhoudingsgemeenteAfleiding";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
        final Datum datumAanvangGeldigheid)
    {

        List<Melding> meldingen = new ArrayList<Melding>();
        PersoonModel ouder = null;

        for (Betrokkenheid betrokkenheid : nieuweSituatie.getOuderBetrokkenheden()) {
            if (betrokkenheid.getBetrokkenheidOuderschap() != null
                && Ja.Ja == betrokkenheid.getBetrokkenheidOuderschap().getIndAdresGevend())
            {
                if (betrokkenheid.getBetrokkene().getIdentificatienummers() != null) {
                    ouder = persoonRepository.findByBurgerservicenummer(
                        betrokkenheid.getBetrokkene().getIdentificatienummers()
                                     .getBurgerservicenummer());
                }

                if (ouder != null) {
                    if (ouder.getBijhoudingsgemeente() != null) {
                        final PersoonBijhoudingsgemeenteGroepBericht bijhGem =
                                new PersoonBijhoudingsgemeenteGroepBericht();
                        bijhGem.setDatumInschrijvingInGemeente(datumAanvangGeldigheid);
                        bijhGem.setIndOnverwerktDocumentAanwezig(JaNee.Nee);
                        bijhGem.setBijhoudingsgemeente(ouder.getBijhoudingsgemeente().getBijhoudingsgemeente());
                        ((RelatieBericht) nieuweSituatie).getKindBetrokkenheid().getBetrokkene().setBijhoudingsgemeente(
                                bijhGem);
                    }

                } else {
                    if (betrokkenheid.getBetrokkene().getIdentificatienummers() != null) {
                        meldingen.add(new Melding(
                            SoortMelding.FOUT_ONOVERRULEBAAR,
                            MeldingCode.REF0001,
                            "Kan ouder niet vinden met BSN: "
                                + betrokkenheid.getBetrokkene().getIdentificatienummers()
                                    .getBurgerservicenummer().getWaarde(),
                            (Identificeerbaar) betrokkenheid.getBetrokkene().getIdentificatienummers(),
                            "burgerservicenummer"));
                    } else {
                        meldingen.add(
                            new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001,
                                "Het BSN van de ouder is niet opgegeven."
                                , (Identificeerbaar) betrokkenheid.getBetrokkene()
                                , "burgerservicenummer"));
                    }
                }

                break;
            }
        }

        return meldingen;
    }

}
