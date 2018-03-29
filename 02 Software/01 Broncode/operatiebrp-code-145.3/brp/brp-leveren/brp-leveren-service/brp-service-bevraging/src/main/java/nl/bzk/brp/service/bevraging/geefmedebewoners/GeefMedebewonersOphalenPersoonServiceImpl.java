/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonOphalenPersoonServiceImpl;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoonopadres.ZoekPersoonOpAdresVerzoek;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoon.OphalenPersoonService} voor Geef Medebewoners.
 */
@Service("geefMedebewonersOphalenPersoonService")
final class GeefMedebewonersOphalenPersoonServiceImpl extends AbstractZoekPersoonOphalenPersoonServiceImpl<ZoekPersoonOpAdresVerzoek> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private GeefMedebewonersBepaalBAGSleutelService bepaalBAGSleutelService;

    private GeefMedebewonersOphalenPersoonServiceImpl() {

    }

    @Override
    protected void valideerAantalZoekResultaten(final List<Persoonslijst> gefilterdePersoonsgegevens, final Autorisatiebundel autorisatiebundel,
                                                final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters) throws StapMeldingException {
        final Set<String> bagSleutels = Sets.newHashSet();
        final List<Persoonslijst> teVerwijderenPersoonslijsten = Lists.newArrayList();
        for (final Persoonslijst persoonslijst : gefilterdePersoonsgegevens) {
            final String peilmomentMaterieelVerzoek = zoekBereikParameters.getPeilmomentMaterieel();
            // Beetje jammer dat we hier opnieuw peilmomentMaterieel moeten converteren, zou beter zijn al gevonverteerde data
            // in verzoeken te hebben. Willen we niet converteren, dan moet deze code naar GeefMedebewonersMaakBerichtServiceImpl.
            final int peilmomentMaterieel = peilmomentMaterieelVerzoek == null ? BrpNu.get().alsIntegerDatumNederland()
                    : DatumUtil.vanDatumNaarInteger(datumService.parseDate(peilmomentMaterieelVerzoek));
            try {
                final String bagSleutel = bepaalBAGSleutelService.bepaalBAGSleutel(persoonslijst, peilmomentMaterieel);
                bagSleutels.add(bagSleutel);
            } catch (final StapException e) {
                // Persoon overleden of geen adresgegevens (wat niet voor kan komen?) of geen BAG-sleutel.
                LOGGER.debug("verwijder overleden persoon uit zoekresultaten: {}", e);
                teVerwijderenPersoonslijsten.add(persoonslijst);
            }
        }
        if (bagSleutels.size() > 1) {
            throw new StapMeldingException(Regel.R2392);
        }
        if (!teVerwijderenPersoonslijsten.isEmpty()) {
            gefilterdePersoonsgegevens.removeAll(teVerwijderenPersoonslijsten);
        }
    }

    @Inject
    void setBepaalBAGSleutelService(final GeefMedebewonersBepaalBAGSleutelService bepaalBAGSleutelService) {
        this.bepaalBAGSleutelService = bepaalBAGSleutelService;
    }
}
