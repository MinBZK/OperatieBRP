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
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.RelatieUtils;

/**
 * Implementatie @brp.bedrijfsregel BRBY0126: Kind met zelfde gegevens mag niet al geregristreerd zijn in GBA.
 */
public class BRBY0126 implements ActieBedrijfsRegel<Relatie> {

    @Inject
    PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BRBY0126";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie, final Actie actie) {
        List<Melding> meldingen = new ArrayList<Melding>();

        Persoon kind = RelatieUtils.haalKindUitRelatie(nieuweSituatie);
        if (kind != null) {
            for (Persoon ouderBericht : RelatieUtils.haalOudersUitRelatie(nieuweSituatie)) {
                final PersoonModel ouder = persoonRepository.findByBurgerservicenummer(
                        ouderBericht.getIdentificatienummers().getBurgerservicenummer());
                if (ouder != null) {
                    if (isKindAlEerderGeregistreerd(ouder, kind)) {
                        meldingen.add(new Melding(Soortmelding.OVERRULEBAAR,
                                                  MeldingCode.BRBY0126,
                                                  (Identificeerbaar) kind, "kind"));
                        break;
                    }
                }
            }
        }
        return meldingen;
    }

    private boolean isKindAlEerderGeregistreerd(final PersoonModel ouder, final Persoon kind) {
        for (Persoon geregistreerdKind : RelatieUtils.haalAlleKinderenUitPersoon(ouder)) {
            // do all the checks
            if (geregistreerdKind.getGeboorte().getDatumGeboorte().op(kind.getGeboorte().getDatumGeboorte())
                    && geregistreerdKind.getSamengesteldeNaam().getVoornamen()
                        .equals(kind.getSamengesteldeNaam().getVoornamen())
                    && geregistreerdKind.getSamengesteldeNaam().getGeslachtsnaam()
                        .equals(kind.getSamengesteldeNaam().getGeslachtsnaam())
                    && geregistreerdKind.getSamengesteldeNaam().getScheidingsteken()
                        .equals(kind.getSamengesteldeNaam().getScheidingsteken())
                    && geregistreerdKind.getSamengesteldeNaam().getVoorvoegsel()
                        .equals(kind.getSamengesteldeNaam().getVoorvoegsel())
                    && geregistreerdKind.getGeslachtsaanduiding().getGeslachtsaanduiding()
                        .equals(kind.getGeslachtsaanduiding().getGeslachtsaanduiding())
                    )
            {
                return true;
            }
        }
        return false;
    }
}
