/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;


/**
 * Er mogen geen rechtsfeiten zijn geregistreerd die na de datum overlijden hebben plaatsgevonden.
 * Opmerking:
 *  Eenvoudige doch brute implementatie is: datum overlijden > tijdstip laatste wijziging
 *  (groep Afgeleid administratief). Het is dan echter niet zeker of daadwerkelijk een later rechtsfeit is geregistreerd
 *  (denk bijvoorbeeld een historische correctie?).
 *
 * Idealiter zouden gebruikers een lijst van rechtsfeiten willen zien die na overlijden hebben plaatsgevonden
 *
 * Foutmelding: Er zijn al rechtsfeiten geregistreerd die hebben plaatsgevonden na de datum van overlijden.
 * Niveau: Warning
 *
 * @brp.bedrijfsregel BRBY0907
 *
 */
public class BRBY0907 implements ActieBedrijfsRegel<Persoon> {

    @Override
    public String getCode() {
        return "BRBY0907";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie,
            final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (null != nieuweSituatie && null != huidigeSituatie
            && null != nieuweSituatie.getOverlijden()
            && null != nieuweSituatie.getOverlijden().getDatumOverlijden()
            && null != huidigeSituatie.getAfgeleidAdministratief()
            && null != huidigeSituatie.getAfgeleidAdministratief().getTijdstipLaatsteWijziging()
            )
        {
            final Datum datumOverLijden = nieuweSituatie.getOverlijden().getDatumOverlijden();
            final Datum laatsteWijzigingDatum = DatumUtil.dateToDatum(
                    huidigeSituatie.getAfgeleidAdministratief().getTijdstipLaatsteWijziging().getWaarde());
            if (!datumOverLijden.na(laatsteWijzigingDatum)) {
                meldingen.add(new Melding(Soortmelding.WAARSCHUWING, MeldingCode.BRBY0907,
                        (Identificeerbaar) nieuweSituatie.getOverlijden(), "datum"));
            }
        }
        return meldingen;
    }
}
