/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.EnumMap;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Voor de diensten {@link SoortDienst#ZOEK_PERSOON}, {@link SoortDienst#ZOEK_PERSOON_OP_ADRESGEGEVENS} en
 * {@link SoortDienst#GEEF_MEDEBEWONERS_VAN_PERSOON} wordt een zeer beperkt aantal gegevens geleverd. Dit wordt gedaan door de
 * autorisaties in te perken.
 * <p>
 * De stap verwijdert objecten, groepen en attributen.
 */
@Component
final class BerichtRestrictieStap implements MaakBerichtStap {

    /**
     * Set van diensten voor welke specifieke element restricties gelden.
     */
    private static EnumMap<SoortDienst, SoortBericht> dienstMetRestrictie = new EnumMap<>(SoortDienst.class);

    /**
     * Te tonen groepen bij het gegeven bericht.
     */
    private static EnumMap<SoortBericht, Set<GroepElement>> groepenInBericht = new EnumMap<>(SoortBericht.class);
    /**
     * Te tonen objecten bij een gegeven bericht.
     */
    private static EnumMap<SoortBericht, Set<ObjectElement>> objectenInBericht = new EnumMap<>(SoortBericht.class);

    static {
        dienstMetRestrictie.put(SoortDienst.ZOEK_PERSOON, SoortBericht.LVG_BVG_ZOEK_PERSOON_R);
        dienstMetRestrictie.put(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS, SoortBericht.LVG_BVG_ZOEK_PERSOON_OP_ADRES_R);
        dienstMetRestrictie.put(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON, SoortBericht.LVG_BVG_GEEF_MEDEBEWONERS_R);

        for (SoortBericht soortBericht : dienstMetRestrictie.values()) {
            final Set<GroepElement> groepSet = ElementHelper.getGroepen().stream()
                    .filter(groepElement -> groepElement.getElement().getElementWaarde().inBericht(soortBericht))
                    .collect(Collectors.toSet());
            groepenInBericht.put(soortBericht, groepSet);
            objectenInBericht.put(soortBericht, groepSet.stream()
                    .map(GroepElement::getObjectElement).collect(Collectors.toSet()));
        }
    }

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final SoortDienst soortDienst = berichtgegevens.getAutorisatiebundel().getSoortDienst();
        final SoortBericht soortBericht = dienstMetRestrictie.get(soortDienst);
        if (soortBericht == null) {
            return;
        }

        //verwijder objecten
        final Set<ObjectElement> teBehoudenObjecten = objectenInBericht.get(soortBericht);
        for (final MetaObject metaObject : berichtgegevens.getGeautoriseerdeObjecten()) {
            if (!teBehoudenObjecten.contains(metaObject.getObjectElement())) {
                berichtgegevens.verwijderAutorisatie(metaObject);
            }
        }
        //verwijder groepen
        final Set<GroepElement> teBehoudenGroepen = groepenInBericht.get(soortBericht);
        for (final MetaGroep groep : berichtgegevens.getGeautoriseerdeGroepen()) {
            if (!teBehoudenGroepen.contains(groep.getGroepElement())) {
                berichtgegevens.verwijderAutorisatie(groep);
            }
        }
    }
}
