/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonNaamgebruikGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNaamgebruikGroepBasis;

/**
 * 1. De gegevens over naamgebruik (voornamen , voorvoegsel etc etc) worden - gecontroleerd - redundant opgeslagen. De
 * reden hiervoor is dat er situaties zijn waarin een afnemer NIET geautoriseerd wordt voor de relaties die een persoon
 * heeft (en dus bijvoorbeeld NIET mag weten wie de partner is), terwijl de afnemer WEL geautoriseerd is voor de
 * gegevens over het naamgebruik, aangezien zij de persoon wel 'juist' moet kunnen aanschrijven. Tot en met het
 * operationele model zijn de gegevens (dus) redundant opgenomen; het is aan een DBA om te beslissen of ook in het
 * uiteindelijke technische model (dan wel de technische modellen) ook sprake is van redundantie, of dat één-en-ander
 * alleen wordt afgeleid.
 *
 * 2. De gegevens in de groep Naamgebruik lijken heel erg op de gegevens in de groep Samengestelde naam. De optie om de
 * groep Samengestelde naam uit te breiden met de gegevens uit de groep Naamgebruik (en deze laatste groep dus eigenlijk
 * te laten vervallen) is vervallen op grond van het volgende argument: het wijzigen van de Samengestelde (formele!)
 * naam is een formeel proces (o.a. de Koning dient de wijziging goed te keuren). De wijziging van Naamgebruik is
 * (veel!) minder formeel.
 *
 * 3. De motivatie voor de groep aanschrijving is als volgt: - Er is (vanuit afnemers) behoefte aan een éénduidige
 * vastlegging van de (wijze) van aanschrijving. Door stuurgroep is daarom besloten om hiertoe een gegevensgroep op te
 * nemen. - Er zijn een aantal situaties die de aanschrijving moet aankunnen, zoals: -- het (casuïstiek!) door de
 * rechter bekrachtigde verzoek voor de weduwe om de naam van haar VOORlaatste partner te mogen voeren. -- de door de
 * Hoge Raad van Adel erkende 'maatschappelijk gebruik' om de echtgenoot van de Baron met Barones aan te duiden, ondanks
 * het feit dat de echtgenote in kwestie zelf geen adellijke titel heeft. Er is voor gekozen om het RESULTAAT hiervan
 * vast te leggen (dus: 'adellijke titel te gebruiken bij aanschrijving'), in plaats van situatie-specifieke indicaties
 * ('indicatie gebruik titel partner' en 'indicatie gebruik naamgegevens voorlaatste ex-partner').
 *
 * 4. In geval van correcties e.d. is het bijhouden van de materiële historie relatief complex, doordat meerdere
 * factoren de (algoritmische) afleiding kunnen beïnvloeden. Tegelijkertijd is er geen business case voor het willen
 * weten "wat had de goede aanschrijving geweest voor x dagen geleden met de kennis van vandaag"; of te wel FORMELE
 * HISTORIE volstaat. Om die reden is de historie aangepast tot alleen formele historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonNaamgebruikGroepModel implements PersoonNaamgebruikGroepBasis {

    @Embedded
    @AttributeOverride(name = NaamgebruikAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naamgebruik"))
    @JsonProperty
    private NaamgebruikAttribuut naamgebruik;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndNaamgebruikAfgeleid"))
    @JsonProperty
    private JaNeeAttribuut indicatieNaamgebruikAfgeleid;

    @Embedded
    @AssociationOverride(name = PredicaatAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "PredicaatNaamgebruik"))
    @JsonProperty
    private PredicaatAttribuut predicaatNaamgebruik;

    @Embedded
    @AttributeOverride(name = VoornamenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VoornamenNaamgebruik"))
    @JsonProperty
    private VoornamenAttribuut voornamenNaamgebruik;

    @Embedded
    @AssociationOverride(name = AdellijkeTitelAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "AdellijkeTitelNaamgebruik"))
    @JsonProperty
    private AdellijkeTitelAttribuut adellijkeTitelNaamgebruik;

    @Embedded
    @AttributeOverride(name = VoorvoegselAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VoorvoegselNaamgebruik"))
    @JsonProperty
    private VoorvoegselAttribuut voorvoegselNaamgebruik;

    @Embedded
    @AttributeOverride(name = ScheidingstekenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ScheidingstekenNaamgebruik"))
    @JsonProperty
    private ScheidingstekenAttribuut scheidingstekenNaamgebruik;

    @Embedded
    @AttributeOverride(name = GeslachtsnaamstamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "GeslnaamstamNaamgebruik"))
    @JsonProperty
    private GeslachtsnaamstamAttribuut geslachtsnaamstamNaamgebruik;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonNaamgebruikGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param naamgebruik naamgebruik van Naamgebruik.
     * @param indicatieNaamgebruikAfgeleid indicatieNaamgebruikAfgeleid van Naamgebruik.
     * @param predicaatNaamgebruik predicaatNaamgebruik van Naamgebruik.
     * @param voornamenNaamgebruik voornamenNaamgebruik van Naamgebruik.
     * @param adellijkeTitelNaamgebruik adellijkeTitelNaamgebruik van Naamgebruik.
     * @param voorvoegselNaamgebruik voorvoegselNaamgebruik van Naamgebruik.
     * @param scheidingstekenNaamgebruik scheidingstekenNaamgebruik van Naamgebruik.
     * @param geslachtsnaamstamNaamgebruik geslachtsnaamstamNaamgebruik van Naamgebruik.
     */
    public AbstractPersoonNaamgebruikGroepModel(
        final NaamgebruikAttribuut naamgebruik,
        final JaNeeAttribuut indicatieNaamgebruikAfgeleid,
        final PredicaatAttribuut predicaatNaamgebruik,
        final VoornamenAttribuut voornamenNaamgebruik,
        final AdellijkeTitelAttribuut adellijkeTitelNaamgebruik,
        final VoorvoegselAttribuut voorvoegselNaamgebruik,
        final ScheidingstekenAttribuut scheidingstekenNaamgebruik,
        final GeslachtsnaamstamAttribuut geslachtsnaamstamNaamgebruik)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.naamgebruik = naamgebruik;
        this.indicatieNaamgebruikAfgeleid = indicatieNaamgebruikAfgeleid;
        this.predicaatNaamgebruik = predicaatNaamgebruik;
        this.voornamenNaamgebruik = voornamenNaamgebruik;
        this.adellijkeTitelNaamgebruik = adellijkeTitelNaamgebruik;
        this.voorvoegselNaamgebruik = voorvoegselNaamgebruik;
        this.scheidingstekenNaamgebruik = scheidingstekenNaamgebruik;
        this.geslachtsnaamstamNaamgebruik = geslachtsnaamstamNaamgebruik;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonNaamgebruikGroep te kopieren groep.
     */
    public AbstractPersoonNaamgebruikGroepModel(final PersoonNaamgebruikGroep persoonNaamgebruikGroep) {
        this.naamgebruik = persoonNaamgebruikGroep.getNaamgebruik();
        this.indicatieNaamgebruikAfgeleid = persoonNaamgebruikGroep.getIndicatieNaamgebruikAfgeleid();
        this.predicaatNaamgebruik = persoonNaamgebruikGroep.getPredicaatNaamgebruik();
        this.voornamenNaamgebruik = persoonNaamgebruikGroep.getVoornamenNaamgebruik();
        this.adellijkeTitelNaamgebruik = persoonNaamgebruikGroep.getAdellijkeTitelNaamgebruik();
        this.voorvoegselNaamgebruik = persoonNaamgebruikGroep.getVoorvoegselNaamgebruik();
        this.scheidingstekenNaamgebruik = persoonNaamgebruikGroep.getScheidingstekenNaamgebruik();
        this.geslachtsnaamstamNaamgebruik = persoonNaamgebruikGroep.getGeslachtsnaamstamNaamgebruik();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamgebruikAttribuut getNaamgebruik() {
        return naamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieNaamgebruikAfgeleid() {
        return indicatieNaamgebruikAfgeleid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PredicaatAttribuut getPredicaatNaamgebruik() {
        return predicaatNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VoornamenAttribuut getVoornamenNaamgebruik() {
        return voornamenNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdellijkeTitelAttribuut getAdellijkeTitelNaamgebruik() {
        return adellijkeTitelNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VoorvoegselAttribuut getVoorvoegselNaamgebruik() {
        return voorvoegselNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheidingstekenAttribuut getScheidingstekenNaamgebruik() {
        return scheidingstekenNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeslachtsnaamstamAttribuut getGeslachtsnaamstamNaamgebruik() {
        return geslachtsnaamstamNaamgebruik;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (naamgebruik != null) {
            attributen.add(naamgebruik);
        }
        if (indicatieNaamgebruikAfgeleid != null) {
            attributen.add(indicatieNaamgebruikAfgeleid);
        }
        if (predicaatNaamgebruik != null) {
            attributen.add(predicaatNaamgebruik);
        }
        if (voornamenNaamgebruik != null) {
            attributen.add(voornamenNaamgebruik);
        }
        if (adellijkeTitelNaamgebruik != null) {
            attributen.add(adellijkeTitelNaamgebruik);
        }
        if (voorvoegselNaamgebruik != null) {
            attributen.add(voorvoegselNaamgebruik);
        }
        if (scheidingstekenNaamgebruik != null) {
            attributen.add(scheidingstekenNaamgebruik);
        }
        if (geslachtsnaamstamNaamgebruik != null) {
            attributen.add(geslachtsnaamstamNaamgebruik);
        }
        return attributen;
    }

}
