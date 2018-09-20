/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.DatumUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementatie van bedrijfsregel BRBY0417.
 *
 * Beoogd partner mag de datum aanvang huwelijk (of geregistreerd partnerschap) niet betrokken zijn in een
 * niet-beeindigd huwelijksrelatie of geregistreerd partnerschapsrelatie.
 * Als de datum aanvang huwelijk (of geregistreerd partnerschap) in de toekomst ligt (datum aanvang huwelijk >
 * systeemdatum) dan is controle op basis van systeemdatum.
 *
 * @brp.bedrijfsregel BRBY0417
 */
public class BRBY0417 implements ActieBedrijfsRegel<Relatie> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0417.class);

    @Inject
    private PersoonRepository   persoonRepository;

    @Inject
    private RelatieRepository   relatieRepository;

    @Override
    public String getCode() {
        return "BRBY0417";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
            final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();
        Set<? extends Betrokkenheid> partners = ((RelatieBericht) nieuweSituatie).getPartnerBetrokkenheden();

        if (CollectionUtils.isNotEmpty(partners)) {
            for (Betrokkenheid partner : partners) {
                Melding melding = controlleer(partner, nieuweSituatie.getGegevens().getDatumAanvang());
                if (melding != null) {
                    meldingen.add(melding);
                }
            }
        }

        return meldingen;
    }

    /**
     * Controleer de betrokkenheden of deze niet al partners hebben.
     *
     * @param betrokkenheid Betrokkenheid
     * @param datumAanvang datum aanvang van huwelijk of partnerschap.
     * @return melding
     */
    private Melding controlleer(final Betrokkenheid betrokkenheid, final Datum datumAanvang) {
        Melding melding = null;

        Persoon persoonBericht = betrokkenheid.getBetrokkene();
        if (persoonBericht.getIdentificatienummers() != null) {
            Persoon persoon =
                persoonRepository.findByBurgerservicenummer(persoonBericht.getIdentificatienummers()
                        .getBurgerservicenummer());
            if (persoon == null) {
                LOGGER.info("Kan de persoon met bsn %s niet vinden", persoonBericht.getIdentificatienummers()
                        .getBurgerservicenummer().getWaarde());
            } else {
                Datum peilDatum;
                Datum vandaag = DatumUtil.vandaag();

                if (datumAanvang == null || (datumAanvang.getWaarde() > vandaag.getWaarde())) {
                    peilDatum = vandaag;
                } else {
                    peilDatum = datumAanvang;
                }

                if (relatieRepository.heeftPartners(((PersoonModel) persoon).getId(), peilDatum)) {
                    melding =
                        new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0417,
                                (Identificeerbaar) betrokkenheid.getBetrokkene().getIdentificatienummers(),
                                "burgerservicenummer");
                }
            }
        }

        return melding;
    }

}
