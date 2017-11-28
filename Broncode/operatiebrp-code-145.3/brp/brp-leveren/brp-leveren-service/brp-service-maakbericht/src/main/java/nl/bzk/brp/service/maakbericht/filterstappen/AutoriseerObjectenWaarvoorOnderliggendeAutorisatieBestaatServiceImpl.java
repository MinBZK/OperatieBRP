/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Sets;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.ChildFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Alleen objecten waarvoor autorisatie bestaat worden geleverd. Object is geautoriseerd en zichtbaar in bericht indien het geautoriseerd is voor een
 * geautoriseerd attrbuut (autaut) dat aanwezig HAD kunnen zijn (autorisatie tabel). Autorisatie is dus leidend hier en niet de daadwerkelijke aanwezigheid
 * van de groepen of attributen. De objecten boom wordt child-first geevalueerd, als een object geautoriseerd wordt dan recursed dat automatisch naar het
 * top-level object.
 * <p>
 * Voorbeeld: Onderstaande PL snippet beschrijft een gerelateerde ouder betrokkenheid.
 * <pre><code>&nbsp;&nbsp;[o] Ouder id=94118
 * &nbsp;&nbsp;&nbsp;[o] FamilierechtelijkeBetrekking id=92259
 * &nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeOuder id=94119
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeOuder.Persoon id=91988
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] GerelateerdeOuder.Persoon.Identiteit
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=91988
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeOuder.Persoon.SoortCode, 'I'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] GerelateerdeOuder.Persoon.Geboorte
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=92044
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeOuder.Persoon.Geboorte.ActieInhoud, '96277'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeOuder.Persoon.Geboorte.Datum, '19720928'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeOuder.Persoon.Geboorte.LandGebiedCode, '6030'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeOuder.Persoon.Geboorte.TijdstipRegistratie, 'Thu Sep 28 12:53:56 CET 1972'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeOuder.Persoon.Geboorte.Woonplaatsnaam, 'Honselersdijk'
 * </code></pre>
 *
 * Met autorisatie enkel op attribuut <tt>GerelateerdeOuder.Persoon.Geboorte.GemeenteCode</tt> wordt de autorisatieboom:
 * <pre><code>&nbsp;&nbsp;[o] Ouder id=94118
 * &nbsp;&nbsp;&nbsp;[o] FamilierechtelijkeBetrekking id=92259
 * &nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeOuder id=94119
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeOuder.Persoon id=91988
 * </code></pre>
 *
 * <tt>GerelateerdeOuder.Persoon.Geboorte.GemeenteCode</tt> staat niet op de PL maar er is wel autorisatie. Het object
 * <tt>GerelateerdeOuder.Persoon</tt> wordt dan geautoriseerd.
 */
@Component
@Bedrijfsregel(Regel.R1976)
final class AutoriseerObjectenWaarvoorOnderliggendeAutorisatieBestaatServiceImpl implements MaakBerichtStap {

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        berichtgegevens.getPersoonslijst().getMetaObject().accept(new ObjectVisitor(berichtgegevens));
    }

    /**
     * Child-first object visitor.
     */
    private static final class ObjectVisitor extends ChildFirstModelVisitor {

        private final Berichtgegevens berichtgegevens;
        private final Dienst dienst;

        ObjectVisitor(final Berichtgegevens berichtgegevens) {
            this.berichtgegevens = berichtgegevens;
            this.dienst = berichtgegevens.getAutorisatiebundel().getDienst();
        }

        @Override
        protected void doVisit(final MetaObject metaObject) {
            if (berichtgegevens.isGeautoriseerd(metaObject)) {
                return;
            }
            //bepaal alle mogelijk aanwezige groepen
            final Set<GroepElement> alleGroepElementen = Sets.newHashSet(metaObject.getObjectElement().getGroepen());
            //bestaat er een attribuutautorisatie voor één van deze groepen en indien van toepassing is het ook gevraagd.
            for (final DienstbundelGroep dienstbundelGroep : dienst.getDienstbundel().getDienstbundelGroepSet()) {
                if (alleGroepElementen.contains(ElementHelper.getGroepElement(dienstbundelGroep.getGroep().getId())) && !dienstbundelGroep
                        .getDienstbundelGroepAttribuutSet().isEmpty()) {
                    berichtgegevens.autoriseer(metaObject);
                    return;
                }
            }
        }
    }
}
