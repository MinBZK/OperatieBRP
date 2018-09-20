/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.RelatieUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementatie van bedrijfsregel BRBY0168.
 * Er is precies 1 persoon aanwezig in het bericht die in de rol van ouder betrokken is bij de relatie van
 * de soort familierechtelijke betrekking en de indicatie 'ouder uit wie het kind geboren is' heeft in het bericht.
 * <p/>
 * Let op: De de indicatie 'ouder uit wie het kind geboren is' is geimplementeerd als 'indicatie adres gevend'.
 *
 * @brp.bedrijfsregel BRBY0168
 */
public class BRBY0168 implements ActieBedrijfsRegel<FamilierechtelijkeBetrekking> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0168.class);

    @Override
    public String getCode() {
        return "BRBY0168";
    }

    @Override
    public List<Melding> executeer(final FamilierechtelijkeBetrekking huidigeSituatie,
            final FamilierechtelijkeBetrekking nieuweSituatie, final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (null != nieuweSituatie) {
            List<Persoon> moeders = RelatieUtils.haalAlleMoedersUitRelatie(nieuweSituatie);
            if (CollectionUtils.isEmpty(moeders) || moeders.size() != 1) {
                LOGGER.error("# gevonden moeders: " + moeders.size());

                meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.BRBY0168, (Identificeerbaar) nieuweSituatie,
                        "ouder"));
            }
        } else {
            LOGGER.error("De nieuwe situatie zou niet null mogen zijn, kan BRBY0168 niet valideren.");
        }
        return meldingen;
    }

}
