/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVeld;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVeld.ConditieRegel;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVelden;


/**
 * De naam zoals die ontstaat door samenvoegen van alle exemplaren van Voornaam en van Geslachtsnaamcomponent van een Persoon.
 * <p/>
 * De Samengestelde naam is vrijwel altijd via een algoritme af te leiden uit de exemplaren van Voornaam en Geslachtsnaamcomponent van een Persoon. In
 * uitzonderingssituaties is dat niet mogelijk.
 * <p/>
 * De groep Samengestelde naam bevat de naam zoals die is opgebouwd uit de naamgegevens uit de groepen voornaam en geslachtsnaamcomponent. Deze
 * samengestelde gegevens hoeven bij het bijhouden van de groepen voornaam en geslachtsnaamcomponent niet door de voor de bijhouding verantwoordelijke
 * partij te worden ingevoerd. De centrale voorzieningen stellen de gegevens uit de groep samengestelde naam op dat moment samen op basis van de groepen
 * voornaam en geslachtsnaamcomponent volgens het onderstaande voorschrift:
 * <p/>
 * � Voornamen � de naam zoals opgenomen in de actuele groep voornaam met volgnummer ��n, gevolgd de naam zoals opgenomen in de actuele groep voornaam met
 * volgnummer twee, enzovoort. De voornamen worden gescheiden door een spatie;
 * <p/>
 * � Predikaat � het predikaat zoals opgenomen in de actuele groep geslachtsnaamcomponent;
 * <p/>
 * � Adelijke titel � de adelijke titel zoals opgenomen in de actuele groep geslachtsnaamcomponent;
 * <p/>
 * � Voorvoegsel � het voorvoegsel zoals opgenomen in de actuele groep geslachtsnaamcomponent;
 * <p/>
 * � Scheidingsteken � het scheidingsteken zoals opgenomen in de actuele groep geslachtsnaamcomponent;
 * <p/>
 * � Geslachtsnaam � de naam zoals opgenomen in de actuele groep geslachtsnaamcomponent.
 * <p/>
 * <p/>
 * <p/>
 * Verplicht aanwezig bij persoon
 * <p/>
 * Historie: beide vormen van historie, aangezien de samengestelde naam ook kan wijzigen ZONDER dat er sprake is van terugwerkende kracht (met andere
 * woorden: 'vanaf vandaag heet ik...' ipv 'en deze moet met terugwerkende kracht gelden vanaf de geboorte'). RvdP 9 jan 2012
 */
@ConditioneelVelden({
    @ConditioneelVeld(wanneerInhoudVanVeld = "indicatieNamenreeks",
        aanConditieRegel = ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        isGelijkAan = ConditioneelVeld.SAMENGESTELDENAAM_INDICATIE_NAMENREEKS_BOOLEAN_JA,
        danVoldoetRegelInInhoudVanVeld = "voorvoegsel", code = Regel.BRAL0505, message = "BRAL0505",
        dbObject = DatabaseObjectKern.PERSOON__VOORVOEGSEL),
    @ConditioneelVeld(wanneerInhoudVanVeld = "scheidingsteken", aanConditieRegel = ConditieRegel.SYNCHROON,
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "voorvoegsel", code = Regel.BRAL0212, message = "BRAL0212",
        dbObject = DatabaseObjectKern.PERSOON__VOORVOEGSEL),
    @ConditioneelVeld(wanneerInhoudVanVeld = "predicaat",
        aanConditieRegel = ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "adellijkeTitel", code = Regel.BRAL0213, message = "BRAL0213",
        dbObject = DatabaseObjectKern.PERSOON__ADELLIJKE_TITEL) })
public final class PersoonSamengesteldeNaamGroepBericht extends AbstractPersoonSamengesteldeNaamGroepBericht implements
    PersoonSamengesteldeNaamGroep
{

}
