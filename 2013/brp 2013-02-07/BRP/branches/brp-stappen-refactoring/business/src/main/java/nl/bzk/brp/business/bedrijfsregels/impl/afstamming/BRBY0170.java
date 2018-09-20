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
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
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
 * De persoon die in het bericht is aangemerkt als de ouder uit wie het kind NIET geboren is,
 * moet op de datum geboorte van het kind van het mannelijke geslacht zijn.
 * <p/>
 * Let op: De de indicatie 'ouder uit wie het kind geboren is' is geimplementeerd als 'indicatie adres gevend'. Deze
 * regel gaat van uit dat de ouders van het kind van het mannelijk + vrouwelijk geslacht is. En gaat van uit dat de man
 * niet de ouder vanwaar het kind geboren is. Ouders van 2x man en 2x vrouw is hiermee uitgesloten.
 * <p/>
 * Let op: deze regel moet plaats vinden NA BRPUC00112 ('moeder' is van geslacht 'V') NA BRBY0168 ('moeder' moet exact 1
 * zijn).
 *
 * @brp.bedrijfsregel BRBY0170
 */
public class BRBY0170 implements ActieBedrijfsRegel<FamilierechtelijkeBetrekking> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0170.class);

    @Inject
    private PersoonRepository   persoonRepository;

    @Override
    public String getCode() {
        return "BRBY0170";
    }

    @Override
    public List<Melding> executeer(final FamilierechtelijkeBetrekking huidigeSituatie,
            final FamilierechtelijkeBetrekking nieuweSituatie, final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (null != nieuweSituatie) {
            List<Persoon> nietMoeders = RelatieUtils.haalAlleNietMoedersUitRelatie(nieuweSituatie);

            if (CollectionUtils.isNotEmpty(nietMoeders)) {
                for (Persoon ouder : nietMoeders) {
                    Persoon pVader =
                        persoonRepository.findByBurgerservicenummer(ouder.getIdentificatienummers()
                                .getBurgerservicenummer());
                    if (null != pVader) {
                        if (pVader.getGeslachtsaanduiding().getGeslachtsaanduiding() != Geslachtsaanduiding.MAN) {
                            // de niet moeder moet man zijn.
                            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.BRBY0170, (Identificeerbaar) ouder
                                    .getGeslachtsaanduiding(), "geslachtsnaamduiding"));
                        }
                    } else {
                        LOGGER.error(String.format("Kan de persoon met bsn '%s' niet vinden."
                            + " Validatie %s niet uitgevoerd.", ouder.getIdentificatienummers()
                                .getBurgerservicenummer().getWaarde(), getCode()));
                    }
                }
            }
        } else {
            LOGGER.error(String
                    .format("De nieuwe situatie zou niet null mogen zijn, kan %s niet valideren.", getCode()));
        }
        return meldingen;
    }

}
