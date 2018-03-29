/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;

/**
 * Util klasse voor het bepalen van de subset relevante administratievehandelingen tbv leveren.
 */
final class AdministratievehandelingFilter {

    private AdministratievehandelingFilter() {
    }

    /**
     * Bepaalt de subset administratievehandelingen welke gebruikt wordt in de verantwoording van de persoon. De relevante groepen zijn alle groepen van de
     * hoofdpersoon, groepen op de eigen betrokkenheid en groepen op relaties. Administratiehandelingen welke enkel verantwoord worden door een
     * gerelateerde betrokkenheid of door een gerelateerde persoon worden hier weggefilterd.
     * @param persoon metaObject van de hoofdpersoon
     * @return een (sub) set administratievehandelingen.
     */
    static Set<AdministratieveHandeling> bepaalHandelingenOpHoofdPersoon(final MetaObject persoon) {
        final Set<AdministratieveHandeling> alleHandelingen = Sets.newHashSet();
        persoon.accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaAttribuut attribuut) {
                if (attribuut.getAttribuutElement().isVerantwoording()) {
                    alleHandelingen.add(attribuut.<Actie>getWaarde().getAdministratieveHandeling());
                }
            }
        });
        final Set<AdministratieveHandeling> handelingenOpHoofdPersoon = new HashSet<>();
        maakHandelingen(handelingenOpHoofdPersoon, persoon);
        alleHandelingen.retainAll(handelingenOpHoofdPersoon);
        return alleHandelingen;
    }

    private static void maakHandelingen(final Set<AdministratieveHandeling> administratievehandelingenHoofdPersoon, final MetaObject metaObject) {
        if (metaObject.getParentObject() != null && metaObject.getParentObject().getObjectElement().isVanType(ElementConstants.RELATIE)) {
            return;
        }
        for (final MetaGroep groep : metaObject.getGroepen()) {
            for (MetaRecord metaRecord : groep.getRecords()) {
                verwerkActie(metaRecord.getActieInhoud(), administratievehandelingenHoofdPersoon);
                verwerkActie(metaRecord.getActieVerval(), administratievehandelingenHoofdPersoon);
                verwerkActie(metaRecord.getActieAanpassingGeldigheid(), administratievehandelingenHoofdPersoon);
                verwerkActie(metaRecord.getActieVervalTbvLeveringMutaties(), administratievehandelingenHoofdPersoon);
            }
        }
        for (MetaObject kindObject : metaObject.getObjecten()) {
            maakHandelingen(administratievehandelingenHoofdPersoon, kindObject);
        }
    }

    private static void verwerkActie(final Actie actie, final Set<AdministratieveHandeling> administratievehandelingenHoofdPersoon) {
        if (actie != null && actie.getAdministratieveHandeling() != null) {
            administratievehandelingenHoofdPersoon.add(actie.getAdministratieveHandeling());
        }
    }

}
