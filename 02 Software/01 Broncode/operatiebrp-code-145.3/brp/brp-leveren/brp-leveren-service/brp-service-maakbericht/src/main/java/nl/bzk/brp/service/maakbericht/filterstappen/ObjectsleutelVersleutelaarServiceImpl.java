/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Iterables;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * De objectsleutel die een Persoon aanwijst dient versleuteld te zijn. De stap maakt versleutelde objectsleutels voor {@link MetaObject}en van type
 * Persoon. Het resultaat wordt opgeslagen in {@link Berichtgegevens} en gebruikt bij het wegschrijven van het bericht.
 */
@Component
@Bedrijfsregel(Regel.R1565)
@Bedrijfsregel(Regel.R1834)
final class ObjectsleutelVersleutelaarServiceImpl implements MaakBerichtStap {

    @Inject
    private ObjectSleutelService objectSleutelService;

    private ObjectsleutelVersleutelaarServiceImpl() {
    }

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final Set<MetaObject> geautoriseerdeObjecten = berichtgegevens.getGeautoriseerdeObjecten();
        for (final MetaObject object : geautoriseerdeObjecten) {
            final ObjectElement objectElement = object.getObjectElement();
            if (objectElement.isVanType(ElementConstants.PERSOON) || objectElement.isAliasVan(ElementConstants.PERSOON)) {
                final int persoonId = (int) object.getObjectsleutel();
                final long
                        versie =
                        Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex().geefAttributen(Element.PERSOON_VERSIE_LOCK)).getWaarde();
                berichtgegevens.addVersleuteldeObjectSleutel(object, objectSleutelService.maakPersoonObjectSleutel(persoonId, versie).maskeren());
            }
        }
    }
}
