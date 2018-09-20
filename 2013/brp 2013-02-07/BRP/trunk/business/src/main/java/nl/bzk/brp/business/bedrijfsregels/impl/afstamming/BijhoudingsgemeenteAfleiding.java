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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingsgemeenteGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;


/**
 * Afleidingsregel: ten tijde van geboorte; Bijhouding- en Adreshistorie kind volgt situatie moeder vanaf
 * geboortedatum.
 */
public class BijhoudingsgemeenteAfleiding implements ActieBedrijfsRegel<FamilierechtelijkeBetrekking> {

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BijhoudingsgemeenteAfleiding";
    }

    @Override
    public List<Melding> executeer(final FamilierechtelijkeBetrekking huidigeSituatie,
        final FamilierechtelijkeBetrekking nieuweSituatie, final Actie actie)
    {

        List<Melding> meldingen = new ArrayList<Melding>();
        PersoonModel ouder = null;

        for (OuderBericht betrokkenheid : ((FamilierechtelijkeBetrekkingBericht) nieuweSituatie)
            .getOuderBetrokkenheden())
        {
            if (betrokkenheid.getOuderschap() != null
                && Ja.J == betrokkenheid.getOuderschap().getIndicatieOuderUitWieKindIsVoortgekomen())
            {
                if (betrokkenheid.getPersoon().getIdentificatienummers() != null) {
                    ouder =
                        persoonRepository.findByBurgerservicenummer(betrokkenheid.getPersoon()
                                                                                 .getIdentificatienummers()
                                                                                 .getBurgerservicenummer());
                }

                if (ouder != null) {
                    if (ouder.getBijhoudingsgemeente() != null) {
                        final PersoonBijhoudingsgemeenteGroepBericht bijhGem =
                            new PersoonBijhoudingsgemeenteGroepBericht();
                        if (actie != null) {
                            bijhGem.setDatumInschrijvingInGemeente(actie.getDatumAanvangGeldigheid());
                        }
                        bijhGem.setIndicatieOnverwerktDocumentAanwezig(JaNee.NEE);
                        bijhGem.setBijhoudingsgemeente(ouder.getBijhoudingsgemeente().getBijhoudingsgemeente());
                        ((FamilierechtelijkeBetrekkingBericht) nieuweSituatie).getKindBetrokkenheid().getPersoon()
                                                                              .setBijhoudingsgemeente(bijhGem);
                    }

                } else {
                    // TODO: bolie, moet omgezet worden naar technische sleutel
                    if (betrokkenheid.getPersoon().getIdentificatienummers() != null) {
                        meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.REF0001,
                            "Kan ouder niet vinden met BSN: "
                                + betrokkenheid.getPersoon().getIdentificatienummers().getBurgerservicenummer()
                                               .getWaarde(), betrokkenheid.getPersoon().getIdentificatienummers(),
                            "burgerservicenummer"));
                    } else {
                        meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.REF0001,
                            "Het BSN van de ouder is niet opgegeven.", betrokkenheid.getPersoon(),
                            "burgerservicenummer"));
                    }
                }

                break;
            }
        }

        return meldingen;
    }

}
