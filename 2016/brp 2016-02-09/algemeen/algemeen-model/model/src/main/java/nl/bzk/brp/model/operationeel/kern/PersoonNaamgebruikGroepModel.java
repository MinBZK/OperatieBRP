/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonNaamgebruikGroep;


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
@Embeddable
public class PersoonNaamgebruikGroepModel extends AbstractPersoonNaamgebruikGroepModel implements
    PersoonNaamgebruikGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonNaamgebruikGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param naamgebruik                  naamgebruik van Naamgebruik.
     * @param indicatieNaamgebruikAfgeleid indicatieNaamgebruikAfgeleid van Naamgebruik.
     * @param predicaatNaamgebruik         predicaatNaamgebruik van Naamgebruik.
     * @param voornamenNaamgebruik         voornamenNaamgebruik van Naamgebruik.
     * @param adellijkeTitelNaamgebruik    adellijkeTitelNaamgebruik van Naamgebruik.
     * @param voorvoegselNaamgebruik       voorvoegselNaamgebruik van Naamgebruik.
     * @param scheidingstekenNaamgebruik   scheidingstekenNaamgebruik van Naamgebruik.
     * @param geslachtsnaamstamNaamgebruik geslachtsnaamstamNaamgebruik van Naamgebruik.
     */
    public PersoonNaamgebruikGroepModel(final NaamgebruikAttribuut naamgebruik,
        final JaNeeAttribuut indicatieNaamgebruikAfgeleid, final PredicaatAttribuut predicaatNaamgebruik,
        final VoornamenAttribuut voornamenNaamgebruik, final AdellijkeTitelAttribuut adellijkeTitelNaamgebruik,
        final VoorvoegselAttribuut voorvoegselNaamgebruik,
        final ScheidingstekenAttribuut scheidingstekenNaamgebruik,
        final GeslachtsnaamstamAttribuut geslachtsnaamstamNaamgebruik)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(naamgebruik, indicatieNaamgebruikAfgeleid, predicaatNaamgebruik, voornamenNaamgebruik,
            adellijkeTitelNaamgebruik, voorvoegselNaamgebruik, scheidingstekenNaamgebruik,
            geslachtsnaamstamNaamgebruik);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonNaamgebruikGroep te kopieren groep.
     */
    public PersoonNaamgebruikGroepModel(final PersoonNaamgebruikGroep persoonNaamgebruikGroep) {
        super(persoonNaamgebruikGroep);
    }

}
