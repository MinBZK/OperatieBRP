/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;

/**
 * Groep global identexpressies: Groepen kunnen globaal geresolved worden obv de indentexpressie.
 * Dit kan alleen is de gevallen dat de expressie uniek is.
 * Groepen Standaard en Identiteit zijn bijvoorbeeld niet uniek en worden genegeerd.
 */
final class ResolverMap {

    private static final ResolverMap INSTANCE = new ResolverMap();
    private static final String DOT = ".";

    private final Multimap<String, Resolver> solverMap = HashMultimap.create();

    /**
     * Constructor.
     */
    private ResolverMap() {
        addAttribuutSolvers();
        addGroepSolvers();
        addObjectSolvers();
    }

    private void addAttribuutSolvers() {
        for (AttribuutElement attribuutElement : ElementHelper.getAttributen()) {

            //solver voor attribuut op elementnaam
            //Persoon.Identificatienummers.Burgerservicenummer
            //Persoon.Geboorte.LandGebiedCode
            //GerelateerdeKind.Persoon.Identificatienummers.Burgerservicenummer
            solverMap.put(attribuutElement.getNaam(), (modelIndex, context) -> modelIndex
                    .geefAttributen(ElementHelper.getAttribuutElement(attribuutElement.getNaam())));

            final Set<String> attribuutIdentifiers =
                    Sets.newHashSet(attribuutElement.getNaamAlias(), attribuutElement.getElement().getElementWaarde().getIdentiteitExpressie());
            attribuutIdentifiers.remove(null);

            for (final String attribuutIdentifier : attribuutIdentifiers) {

                //solver voor attribuut op alias van groep + alias van attribuut binnen de context van een object
                //x.Naamgebruik.Voorvoegsel
                final AttribuutOpObjectResolver aliasOpGroepAliasResolver
                        = new AttribuutOpObjectResolver(attribuutElement, Prioriteit.HOOG);
                if (attribuutElement.getGroep().getNaamAlias() != null) {
                    solverMap.put(attribuutElement.getGroep().getNaamAlias() + DOT
                            + attribuutIdentifier, aliasOpGroepAliasResolver);
                }

                //MAP(GerelateerdeKind.Persoon, x, x.Identificatienummers.Burgerservicenummer)
                if (attribuutElement.getGroep().getElement().getElementWaarde().getIdentiteitExpressie() != null) {
                    solverMap.put(attribuutElement.getGroep().getElement().getElementWaarde().getIdentiteitExpressie() + DOT
                            + attribuutIdentifier, aliasOpGroepAliasResolver);
                }

                //solve attribuut op variabele x, waar x een groep is
                //x.voorvoegsel (x = groep Naamgebruik)
                //x.burgerservicenummer (x = groep Identificatienummers)
                solverMap.put(attribuutIdentifier, new AttribuutOpGroepResolver(attribuutElement));

                //solve attribuut op variabele x, waar x een MetaRecord is
                //x.voorvoegsel (x = MetaRecord van groep Naamgebruik)
                //x.burgerservicenummer (x = MetaRecord van groep Identificatienummers)
                solverMap.put(attribuutIdentifier, new AttribuutOpMetaRecordResolver(attribuutElement));

                //solve attribuut op variabele x, waar x een object is
                solverMap.put(attribuutIdentifier, new AttribuutOpObjectResolver(attribuutElement, Prioriteit.LAAG));
            }
        }
    }

    private void addGroepSolvers() {
        for (GroepElement element : ElementHelper.getGroepen()) {
            //solver voor groep op elementnaam
            solverMap.put(element.getNaam(), (modelIndex, context) -> modelIndex
                    .geefGroepenVanElement(ElementHelper.getGroepElement(element.getNaam())));

            //solver voor groep obv alias
            //[uitsluiting_kiesrecht, geboorte, samengestelde_naam, versie, verblijfsrecht, nummerverwijzing, deelname_eu_verkiezingen, overlijden,
            // migratie, bijhouding, geslachtsaanduiding, identificatienummers, naamgebruik, afgeleid_administratief, identiteit, persoonskaart, inschrijving]
            if (element.getNaamAlias() != null) {
                solverMap.put(element.getNaamAlias(), (modelIndex, context) -> modelIndex
                        .geefGroepenVanElement(element));
            }
        }
    }

    private void addObjectSolvers() {
        //solver voor object op elementnaam
        for (ObjectElement objectElement : ElementHelper.getObjecten()) {
            solverMap.put(objectElement.getNaam(), (modelIndex, context) -> modelIndex
                    .geefObjecten(ElementHelper.getObjectElement(objectElement.getNaam())));

            if (objectElement.getNaamAlias() != null) {
                solverMap.put(objectElement.getNaamAlias(), (modelIndex, contextObject) -> modelIndex.geefObjecten(objectElement));
            }
        }
    }

    /**
     * Geeft een lijst met {@link Resolver}s voor de gegeven elementExpressie.
     *
     * @param elementExpressie een expressie dat een element aanwijst.
     * @return een lijst met solvers.
     */
    static List<Resolver> getSolver(final String elementExpressie) {
        return Lists.newArrayList(INSTANCE.solverMap.get(elementExpressie));
    }

}
