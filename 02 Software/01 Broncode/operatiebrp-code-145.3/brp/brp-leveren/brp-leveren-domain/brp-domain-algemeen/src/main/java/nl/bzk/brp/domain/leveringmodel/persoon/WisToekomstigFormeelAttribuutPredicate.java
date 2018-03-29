/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.collect.Lists;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerantwoordingCategorie;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;

/**
 * Predikaat voor het wegfilteren van attributen welke niet meer relevant zijn vòòr een gegeven formeel punt.
 * <p>
 * Elke verantwoordingsattribuut dat verwijst naar een toekomstige {@link Actie} wordt verwijderd (AI, AV, AAG).
 * <p>
 * ActieVervalTbvLeveringMutaties wordt nooit leeggemaakt. Indien deze gevuld is wordt ActieVerval geleegd
 * door de gecombineerde actieverval regel.
 * </p>
 * <p>
 * ActieVervalTbvLeveringMutaties wordt gebruikt in de deltabepaling voor mutatielevering en in
 * de bepaling van de verwerkingssoort. Zonder dit haakje gaat deze bepalingen niet correct.
 * Alleen in deze gevallen is de actieverval tbv mutlev relevant. Op andere plekken mag niet gekeken
 * worden naar deze actieverval. In de actueelbepaling wordt dan ook alleen gekeken naar de actieverval.
 * Hierdoor zal van een gecorrigeerd 'oud' beeld de laatste stapel actueel zijn.
 * <p>
 * TijdstipVerval wordt weggefiltered indien actieVerval verwijst naar een toekomstige {@link Actie}
 * <p>
 * Het attribuut NadereAanduidingVerval wordt daarbij niet geschrapt, omdat we wettelijk verplicht
 * zijn aan te geven dat een gegeven onjuist is gebleken. Dit moet voorkomen dat een afnemer actie
 * gaat ondernemen op grond van een gegeven waarvan op het moment van leveren al bekend is dat dit later onjuist is gebleken.
 */
final class WisToekomstigFormeelAttribuutPredicate implements Predicate<MetaAttribuut> {

    private final Set<AdministratieveHandeling> toekomstigeHandelingen;

    /**
     * Constructor.
     * @param toekomstigeHandelingen set met toekomstige handelingen
     */
    WisToekomstigFormeelAttribuutPredicate(final Set<AdministratieveHandeling> toekomstigeHandelingen) {
        this.toekomstigeHandelingen = toekomstigeHandelingen;
    }

    @Override
    public boolean test(final MetaAttribuut metaAttribuut) {
        //actieinhoud, actieverval, actie aanpassing geldigheid
        final BooleanSupplier isToekomstigVerantwoordingAttribuut = () -> metaAttribuut.getAttribuutElement().isVerantwoording()
                && toekomstigeHandelingen.contains(metaAttribuut.<Actie>getWaarde().getAdministratieveHandeling());
        //ts verval, nadere aanduiding verval. Voor afnemerindicaties is alleen nu van belang bij bepaling actueel.
        final BooleanSupplier isToekomstigVervalGerelateerdAttribuut = () ->
                metaAttribuut.getAttribuutElement().isDatumTijdVerval()
                        && (metaAttribuut.getParentRecord().getParentGroep().getGroepElement().getVerantwoordingCategorie() == VerantwoordingCategorie.D
                        || toekomstigeHandelingen.contains(metaAttribuut.getParentRecord().getActieVerval().getAdministratieveHandeling()));
        //verwijder actieverval indien actievervalmutlev gevuld is EN niet in tot toekomstige handeling behoort (actieverval is in dit geval leidend)
        final BooleanSupplier isVervalMetMutLevVervalGevuld = () -> metaAttribuut.getAttribuutElement().isActieVerval()
                && metaAttribuut.getParentRecord().getActieVervalTbvLeveringMutaties() != null
                && toekomstigeHandelingen.contains(metaAttribuut.getParentRecord().getActieVervalTbvLeveringMutaties().getAdministratieveHandeling());
        //true is behouden
        return !Lists.newArrayList(isToekomstigVerantwoordingAttribuut, isToekomstigVervalGerelateerdAttribuut,
                isVervalMetMutLevVervalGevuld).stream().anyMatch(BooleanSupplier::getAsBoolean);
    }
}
