/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Het object GerelateerdeOuder is nooit zichtbaar op een ouder betrokkenheid. Voert een correctie op de autorisatie uit zodat het object GerelateerdeOuder
 * nooit zichtbaar is op een ouder betrokkenheid. Dit is iets wat gecorrigeerd moet worden omdat het niet via de element autorisatie mogelijk is. Maakt
 * eventuele autorisatie op het object 'GerelateerdeOuder' ongedaan indien het gelinked is via een 'Ouder' betrokkenheid.
 * <p>
 * Verwijdert autorisatie op gerelateerde ouder objecten en de onderliggende autorisaties.
 * <pre><code>&nbsp;[o] Persoon id=90192
 * &nbsp;&nbsp;[o] Ouder id=90250
 * &nbsp;&nbsp;&nbsp;[o] FamilierechtelijkeBetrekking id=90107
 * &nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeOuder id=90251
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeOuder.Persoon id=90193</code></pre>
 * <p>
 * wordt:
 * <pre><code>&nbsp;[o] Persoon id=90192
 * &nbsp;&nbsp;[o] Ouder id=90250
 * &nbsp;&nbsp;&nbsp;[o] FamilierechtelijkeBetrekking id=90107
 * </code></pre>
 * <p>
 * In het geval de gerelateerde ouder op een Kind betrokkenheid zit gebeurt er niets:
 * <pre><code>&nbsp;[o] Persoon id=90192
 * &nbsp;&nbsp;[o] Kind id=90180
 * &nbsp;&nbsp;&nbsp;[o] FamilierechtelijkeBetrekking id=90084
 * &nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeOuder id=90181
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeOuder.Persoon id=90123</code></pre>
 */
@Component
final class VerwijderGerelateerdeOuderServiceImpl implements MaakBerichtStap {


    private ElementObject persoonOuder = ElementHelper.getObjectElement(Element.PERSOON_OUDER);

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final Set<MetaObject> gerelateerdeOuders = berichtgegevens.getPersoonslijst().
                getModelIndex().geefObjecten(Element.GERELATEERDEOUDER);
        for (final MetaObject gerelateerdeOuder : gerelateerdeOuders) {
            final boolean heeftOuderAlsParentObject = persoonOuder.equals(gerelateerdeOuder
                    .getParentObject().getParentObject().getObjectElement());
            if (heeftOuderAlsParentObject) {
                berichtgegevens.verwijderAutorisatie(gerelateerdeOuder);
            }
        }
    }
}
