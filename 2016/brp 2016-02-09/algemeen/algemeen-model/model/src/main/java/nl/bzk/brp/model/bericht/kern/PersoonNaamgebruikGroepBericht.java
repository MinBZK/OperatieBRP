/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonNaamgebruikGroep;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVeld;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVelden;


/**
 * 1. De gegevens over naamgebruik (voornamen , voorvoegsel etc etc) worden - gecontroleerd - redundant opgeslagen. De reden hiervoor is dat er situaties
 * zijn waarin een afnemer NIET geautoriseerd wordt voor de relaties die een persoon heeft (en dus bijvoorbeeld NIET mag weten wie de partner is), terwijl
 * de afnemer WEL geautoriseerd is voor de gegevens over het naamgebruik, aangezien zij de persoon wel 'juist' moet kunnen aanschrijven. Tot en met het
 * operationele model zijn de gegevens (dus) redundant opgenomen; het is aan een DBA om te beslissen of ook in het uiteindelijke technische model (dan wel
 * de technische modellen) ook sprake is van redundantie, of dat ��n-en-ander alleen wordt afgeleid. Beslissing d.d. 21 juni 2011, RvdP, aangepast 20
 * december 2013. 2. De gegevens in de groep Naamgebruik lijken heel erg op de gegevens in de groep Samengestelde naam. De optie om de groep Samengestelde
 * naam uit te breiden met de gegevens uit de groep Naamgebruik (en deze laatste groep dus eigenlijk te laten vervallen) is vervallen op grond van het
 * volgende argument: het wijzigen van de Samengestelde (formele!) naam is een formeel proces (o.a. de Koning dient de wijziging goed te keuren). De
 * wijziging van Naamgebruik is (veel!) minder formeel. Beslissing d.d. 21 juni 2011, RvdP, aangepast 20 december 2013. 3. De motivatie voor de groep
 * aanschrijving is als volgt: - Er is (vanuit afnemers) behoefte aan een ��nduidige vastlegging van de (wijze) van aanschrijving. Door stuurgroep is
 * daarom besloten om hiertoe een gegevensgroep op te nemen. - Er zijn een aantal situaties die de aanschrijving moet aankunnen, zoals: -- het
 * (casu�stiek!) door de rechter bekrachtigde verzoek voor de weduwe om de naam van haar VOORlaatste partner te mogen voeren. -- de door de Hoge Raad van
 * Adel erkende 'maatschappelijk gebruik' om de echtgenoot van de Baron met Barones aan te duiden, ondanks het feit dat de echtgenote in kwestie zelf geen
 * adellijke titel heeft. Er is voor gekozen om het RESULTAAT hiervan vast te leggen (dus: 'adellijke titel te gebruiken bij aanschrijving'), in plaats van
 * situatie-specifieke indicaties ('indicatie gebruik titel partner' en 'indicatie gebruik naamgegevens voorlaatste ex-partner'). RvdP 8 juni 2012. 4. In
 * geval van correcties e.d. is het bijhouden van de materi�le historie relatief complex, doordat meerdere factoren de (algoritmische) afleiding kunnen
 * be�nvloeden. Tegelijkertijd is er geen business case voor het willen weten "wat had de goede aanschrijving geweest voor x dagen geleden met de kennis
 * van vandaag"; of te wel FORMELE HISTORIE volstaat. Om die reden is de historie aangepast tot alleen formele historie. RvdP 26 oktober 2012.
 */
@ConditioneelVelden({
    @ConditioneelVeld(wanneerInhoudVanVeld = "indicatieNaamgebruikAfgeleid",
        isGelijkAan = ConditioneelVeld.NAAMGEBRUIK_ALGORITMISCH_AFGELEID_BOOLEAN_JA,
        danVoldoetRegelInInhoudVanVeld = "predicaatNaamgebruik",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        code = Regel.BRAL0512, message = "BRAL0512_1",
        dbObject = DatabaseObjectKern.PERSOON__PREDICAAT_NAAMGEBRUIK),
    @ConditioneelVeld(wanneerInhoudVanVeld = "indicatieNaamgebruikAfgeleid",
        isGelijkAan = ConditioneelVeld.NAAMGEBRUIK_ALGORITMISCH_AFGELEID_BOOLEAN_JA,
        danVoldoetRegelInInhoudVanVeld = "adellijkeTitelNaamgebruik",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        code = Regel.BRAL0512, message = "BRAL0512_2",
        dbObject = DatabaseObjectKern.PERSOON__ADELLIJKE_TITEL_NAAMGEBRUIK),
    @ConditioneelVeld(wanneerInhoudVanVeld = "indicatieNaamgebruikAfgeleid",
        isGelijkAan = ConditioneelVeld.NAAMGEBRUIK_ALGORITMISCH_AFGELEID_BOOLEAN_JA,
        danVoldoetRegelInInhoudVanVeld = "scheidingstekenNaamgebruik",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        code = Regel.BRAL0512, message = "BRAL0512_3",
        dbObject = DatabaseObjectKern.PERSOON__SCHEIDINGSTEKEN_NAAMGEBRUIK),
    @ConditioneelVeld(wanneerInhoudVanVeld = "indicatieNaamgebruikAfgeleid",
        isGelijkAan = ConditioneelVeld.NAAMGEBRUIK_ALGORITMISCH_AFGELEID_BOOLEAN_JA,
        danVoldoetRegelInInhoudVanVeld = "voorvoegselNaamgebruik",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        code = Regel.BRAL0512, message = "BRAL0512_4",
        dbObject = DatabaseObjectKern.PERSOON__VOORVOEGSEL_NAAMGEBRUIK),
    @ConditioneelVeld(wanneerInhoudVanVeld = "indicatieNaamgebruikAfgeleid",
        isGelijkAan = ConditioneelVeld.NAAMGEBRUIK_ALGORITMISCH_AFGELEID_BOOLEAN_JA,
        danVoldoetRegelInInhoudVanVeld = "geslachtsnaamstamNaamgebruik",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        code = Regel.BRAL0512, message = "BRAL0512_5",
        dbObject = DatabaseObjectKern.PERSOON__GESLACHTSNAAMSTAM_NAAMGEBRUIK),
    @ConditioneelVeld(wanneerInhoudVanVeld = "indicatieNaamgebruikAfgeleid",
        isGelijkAan = ConditioneelVeld.NAAMGEBRUIK_ALGORITMISCH_AFGELEID_BOOLEAN_JA,
        danVoldoetRegelInInhoudVanVeld = "voornamenNaamgebruik",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        code = Regel.BRAL0512, message = "BRAL0512_6",
        dbObject = DatabaseObjectKern.PERSOON__VOORNAMEN_NAAMGEBRUIK),
    @ConditioneelVeld(wanneerInhoudVanVeld = "indicatieNaamgebruikAfgeleid",
        isGelijkAan = ConditioneelVeld.NAAMGEBRUIK_ALGORITMISCH_AFGELEID_BOOLEAN_NEE,
        danVoldoetRegelInInhoudVanVeld = "geslachtsnaamstamNaamgebruik",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL0516, message = "BRAL0516",
        dbObject = DatabaseObjectKern.PERSOON__GESLACHTSNAAMSTAM_NAAMGEBRUIK),
    // predicaat en adelijke titel mogen niet tegelijkertijd gevuld zijn.
    @ConditioneelVeld(wanneerInhoudVanVeld = "adellijkeTitelNaamgebruik",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "predicaatNaamgebruik",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        code = Regel.BRAL0213, message = "BRAL0213",
        dbObject = DatabaseObjectKern.PERSOON__ADELLIJKE_TITEL_NAAMGEBRUIK) })
public final class PersoonNaamgebruikGroepBericht extends AbstractPersoonNaamgebruikGroepBericht implements Groep,
    PersoonNaamgebruikGroep, MetaIdentificeerbaar
{

}
