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
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.RelatieUtils;

/**
 * @brp.bedrijfsregel BRBY0129 Geboortedatum ouders voor geboortedatum kind
 */
public class BRBY0129 implements ActieBedrijfsRegel<Relatie> {

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BRBY0129";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie, final Actie actie) {
        List<Melding> meldingen = new ArrayList<Melding>();
        Persoon kindBericht = RelatieUtils.haalKindUitRelatie(nieuweSituatie);
        Datum kindGeboorteDatum = RelatieUtils.haalKindUitRelatie(nieuweSituatie).getGeboorte().getDatumGeboorte();
        for (Persoon ouderBericht : RelatieUtils.haalOudersUitRelatie(nieuweSituatie)) {
            Persoon ouder = persoonRepository
                    .findByBurgerservicenummer(ouderBericht.getIdentificatienummers().getBurgerservicenummer());
            if (ouder != null) {
                if (kindGeboorteDatum.op(ouder.getGeboorte().getDatumGeboorte())
                    || kindGeboorteDatum.voor(ouder.getGeboorte().getDatumGeboorte()))
                {
                    meldingen.add(new Melding(Soortmelding.FOUT, MeldingCode.BRBY0129,
                            (Identificeerbaar) kindBericht, "kind"));
                }
            }
        }
        return meldingen;
    }
}
