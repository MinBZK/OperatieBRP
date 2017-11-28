/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoonopadres;

import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonOphalenPersoonServiceImpl;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import org.springframework.stereotype.Service;

/**
 * ZoekPersoonOpAdresOphalenPersoonServiceImpl.
 */
@Bedrijfsregel(Regel.R2376)
@Service("zoekPersoonOpAdresOphalenPersoonService")
final class ZoekPersoonOpAdresOphalenPersoonServiceImpl extends AbstractZoekPersoonOphalenPersoonServiceImpl<ZoekPersoonOpAdresVerzoek> {

    private static final GroepElement PERSOON_ADRES_STANDAARD =
            ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD);

    @Override
    protected void valideerAantalZoekResultaten(final List<Persoonslijst> gefilterdePersoonslijstSet, final Autorisatiebundel autorisatiebundel,
                                                final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters)
            throws StapMeldingException {
        boolean bagAanduidingGelijk = false;
        if (zoekBereikParameters == null || zoekBereikParameters.getZoekBereik() == null
                || zoekBereikParameters.getZoekBereik() != Zoekbereik.MATERIELE_PERIODE) {
            bagAanduidingGelijk = isBagAanduidingGelijkOverAdresStandaardGroepen(gefilterdePersoonslijstSet, zoekBereikParameters);
        }
        if (!bagAanduidingGelijk) {
            Integer maxAantalZoekResultaten = autorisatiebundel.getDienst().getMaximumAantalZoekresultaten();
            if (maxAantalZoekResultaten == null) {
                maxAantalZoekResultaten = MAX_RESULTS_DEFAULT;
            }
            if (gefilterdePersoonslijstSet.size() > maxAantalZoekResultaten) {
                throw new StapMeldingException(Regel.R2289);
            }
        }
    }

    private boolean isBagAanduidingGelijkOverAdresStandaardGroepen(final List<Persoonslijst> gefilterdePersoonslijstSet,
                                                                   final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters) {
        String nummerAanduidingVergelijk = null;
        boolean bagAanduidingGelijk = false;
        for (final Persoonslijst persoonslijst : gefilterdePersoonslijstSet) {
            final Set<MetaGroep> adresStandaardGroepen = persoonslijst.getModelIndex().geefGroepenVanElement(PERSOON_ADRES_STANDAARD);
            for (final MetaGroep adresStandaardGroep : adresStandaardGroepen) {
                MetaRecord metaRecord = bepaalAdresMetaRecord(persoonslijst, zoekBereikParameters, adresStandaardGroep);
                if (metaRecord == null || metaRecord.getAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING) == null) {
                    bagAanduidingGelijk = false;
                    break;
                }
                final String nummerAanduiding = metaRecord.getAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING).getWaarde();
                if (nummerAanduidingVergelijk == null) {
                    nummerAanduidingVergelijk = nummerAanduiding;
                }
                bagAanduidingGelijk = nummerAanduidingVergelijk.equals(nummerAanduiding);
            }

            if (!bagAanduidingGelijk) {
                break;
            }
        }
        return bagAanduidingGelijk;
    }

    private MetaRecord bepaalAdresMetaRecord(final Persoonslijst persoonslijst,
                                             final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters,
                                             final MetaGroep adresStandaardGroep) {
        MetaRecord metaRecord = null;
        if (zoekBereikParameters != null && zoekBereikParameters.getPeilmomentMaterieel() != null) {
            // Gebruik record op peilmoment, als deze is opgegeven.
            final int peilmoment = Integer.parseInt(zoekBereikParameters.getPeilmomentMaterieel().replace("-", ""));
            for (final MetaRecord adresRecord : adresStandaardGroep.getRecords()) {
                if (DatumUtil
                        .valtDatumBinnenPeriode(peilmoment, adresRecord.getDatumAanvangGeldigheid(), adresRecord.getDatumEindeGeldigheid())) {
                    metaRecord = adresRecord;
                    break;
                }
            }
        } else {
            // Gebruik anders actuele record.
            metaRecord = persoonslijst.getActueleRecord(adresStandaardGroep).orElse(null);
        }
        return metaRecord;
    }

}
