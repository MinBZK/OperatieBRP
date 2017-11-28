/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.Set;
import java.util.function.BooleanSupplier;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import org.springframework.stereotype.Service;

/**
 * GeefMedebewonersBepaalBAGSleutelService : Service voor het bepalen van BAG-sleutel (Adres.IdentificatiecodeNummeraanduiding) van het adres van een
 * persoon op een gegeven materieel peilmoment ahv een identificatiecriterium (bsn, anr of objectsleutel).
 */
@Bedrijfsregel(Regel.R2275)
@Bedrijfsregel(Regel.R2277)
@Bedrijfsregel(Regel.R2383)
@Bedrijfsregel(Regel.R2392)
@Bedrijfsregel(Regel.R2404)
@Service
final class GeefMedebewonersBepaalBAGSleutelServiceImpl implements GeefMedebewonersBepaalBAGSleutelService {

    private static final GroepElement PERSOON_ADRES_STANDAARD =
            ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD);

    @Override
    public String bepaalBAGSleutel(final Persoonslijst persoonslijstNu, final Integer materieelPeilmoment) throws StapMeldingException {
        final Persoonslijst persoonslijst = persoonslijstNu.beeldVan().materieelPeilmoment(materieelPeilmoment);

        if (isPersoonOverleden(persoonslijst)) {
            throw new StapMeldingException(Regel.R2404);
        }

        final Set<MetaGroep> adresStandaardGroepSet = persoonslijst.getModelIndex()
                .geefGroepenVanElement(PERSOON_ADRES_STANDAARD);
        if (adresStandaardGroepSet.isEmpty()) {
            throw new StapMeldingException(Regel.R2383);
        }
        final MetaGroep adresStandaardGroep = Iterables.getOnlyElement(adresStandaardGroepSet);
        return persoonslijst.getActueleRecord(adresStandaardGroep)
                //het gevonden adres moet een Nederlands adres zijn
                .filter(this::isNederlandsAdres)
                //Hier kunnen we ook het Adres record retourneren in geval we moeten zoeken op het adres van deze persoon.
                .map(adresRecord -> adresRecord.getAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING)))
                .map(MetaAttribuut::<String>getWaarde)
                .orElseThrow(() -> new StapMeldingException(Regel.R2383));
    }


    // Checkt of een adres een Nederlands adres is, zoals gedefinieerd in R2302: Land/gebied = "Nederland" (landcode
    // 6030) EN Adres.Buitenland adres regel 1 t/m 6 zijn leeg.
    private boolean isNederlandsAdres(final MetaRecord adresRecord) {
        final BooleanSupplier landGebiedCodeisNL = () ->
                adresRecord.getAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE) != null
                        && adresRecord.getAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE).<String>getWaarde().equals(LandOfGebied.CODE_NEDERLAND);

        final BooleanSupplier buitenlandsAdresRegelsZijnLeeg = () ->
                Lists.newArrayList(
                        Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1,
                        Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2,
                        Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3,
                        Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4,
                        Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5,
                        Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6).stream().allMatch(elem ->
                        adresRecord.getAttribuut(elem) == null || adresRecord.getAttribuut(elem).getWaarde() == null);

        return landGebiedCodeisNL.getAsBoolean() && buitenlandsAdresRegelsZijnLeeg.getAsBoolean();
    }

    //check of persoon is overleden, definitie in R2404
    private boolean isPersoonOverleden(final Persoonslijst persoonslijst) {
        return persoonslijst.<String>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE))
                .map(NadereBijhoudingsaard.OVERLEDEN.getCode()::equals)
                .orElse(false);
    }
}
