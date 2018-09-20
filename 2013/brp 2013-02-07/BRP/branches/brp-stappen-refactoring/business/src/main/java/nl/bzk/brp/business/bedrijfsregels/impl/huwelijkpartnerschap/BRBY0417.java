/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementatie van bedrijfsregel BRBY0417.
 * <p/>
 * Beoogd partner mag de datum aanvang huwelijk (of geregistreerd partnerschap) niet betrokken zijn in een
 * niet-beeindigd huwelijksrelatie of geregistreerd partnerschapsrelatie.
 * Als de datum aanvang huwelijk (of geregistreerd partnerschap) in de toekomst ligt (datum aanvang huwelijk >
 * systeemdatum) dan is controle op basis van systeemdatum.
 *
 * @brp.bedrijfsregel BRBY0417
 */
public class BRBY0417 implements ActieBedrijfsRegel<HuwelijkGeregistreerdPartnerschap> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0417.class);

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @Override
    public String getCode() {
        return "BRBY0417";
    }

    /** {@inheritDoc} */
    @Override
    public List<Melding> executeer(final HuwelijkGeregistreerdPartnerschap huidigeSituatie,
        final HuwelijkGeregistreerdPartnerschap nieuweSituatie,
        final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();
        Collection<? extends Betrokkenheid> partners = nieuweSituatie.getBetrokkenheden();

        if (CollectionUtils.isNotEmpty(partners)) {
            for (Betrokkenheid partner : partners) {
                Melding melding = controlleer(partner, nieuweSituatie.getStandaard().getDatumAanvang());
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

        Persoon persoonBericht = betrokkenheid.getPersoon();
        if (persoonBericht.getIdentificatienummers() != null) {
            Persoon persoon =
                persoonRepository.findByBurgerservicenummer(persoonBericht.getIdentificatienummers()
                                                                          .getBurgerservicenummer());
            if (persoon == null) {
                LOGGER.info("Kan de persoon met bsn %s niet vinden", persoonBericht.getIdentificatienummers()
                                                                                   .getBurgerservicenummer()
                                                                                   .getWaarde());
            } else {
                Datum peilDatum;
                Datum vandaag = DatumUtil.vandaag();

                if (datumAanvang == null || (datumAanvang.getWaarde() > vandaag.getWaarde())) {
                    peilDatum = vandaag;
                } else {
                    peilDatum = datumAanvang;
                }

                if (relatieRepository.heeftPartners(((PersoonModel) persoon).getID(), peilDatum)) {
                    melding =
                        new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.BRBY0417,
                            (Identificeerbaar) betrokkenheid.getPersoon().getIdentificatienummers(),
                            "burgerservicenummer");
                }
            }
        }

        return melding;
    }

}
