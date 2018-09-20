/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVeld;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVelden;
import org.jibx.runtime.IUnmarshallingContext;


/**
 * Vorm van historie: beiden. Motivatie: net als bijvoorbeeld de Samengestelde naam kan een individuele geslachtsnaamcomponent (bijv. die met volgnummer 1
 * voor persoon X) in de loop van de tijd veranderen, dus nog los van eventuele registratiefouten. Er is dus ��k sprake van materi�le historie. RvdP 17 jan
 * 2012.
 */
@ConditioneelVelden({
    // predikaat en adelijke titel mogen niet tegelijkertijd gevuld zijn.
    @ConditioneelVeld(wanneerInhoudVanVeld = "adellijkeTitel", isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "predicaat",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        code = Regel.BRAL0213, message = "BRAL0213",
        dbObject = DatabaseObjectKern.PERSOON_GESLACHTSNAAMCOMPONENT__PREDICAAT),
    @ConditioneelVeld(wanneerInhoudVanVeld = "scheidingsteken", isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "voorvoegsel",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON, code = Regel.BRAL0212,
        message = "BRAL0212", dbObject = DatabaseObjectKern.PERSOON_GESLACHTSNAAMCOMPONENT__VOORVOEGSEL) })
public final class PersoonGeslachtsnaamcomponentStandaardGroepBericht extends
    AbstractPersoonGeslachtsnaamcomponentStandaardGroepBericht implements
    PersoonGeslachtsnaamcomponentStandaardGroep
{

    /**
     * Hook voor Jibx om de communicatieId van de encapsulerende object te zetten. De standaard groep wordt niet geregistreerd in de CommunicatieIdMap.
     *
     * @param ctx de jibx context
     */
    public void jibxPostSetCommunicatieId(final IUnmarshallingContext ctx) {
        setCommunicatieID(((BerichtIdentificeerbaar) ctx.getStackObject(1)).getCommunicatieID());
    }
}
