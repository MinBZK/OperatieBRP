/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.Relatie;
import nl.bzk.brp.model.logisch.kern.basis.RelatieBasis;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;


/**
 * De Relatie tussen personen.
 *
 * Een Relatie tussen twee of meer Personen is als aparte object opgenomen. Het relatie-object beschrijft om wat voor
 * soort relatie het gaat, en waar en wanneer deze begonnen en/of be�indigd is. Het koppelen van een Persoon aan een
 * Relatie gebeurt via een object van het type Betrokkenheid.
 *
 * 1. Naast de nu onderkende relatievormen (Huwelijk, geregistreerd partnerschap en familierechtelijkebetrekking) is er
 * lange tijd sprake geweest van nog een aantal binaire relatievormen: erkenning ongeboren vrucht, ontkenning ouderschap
 * en naamskeuze ongeboren vrucht. Deze relatievormen zijn in een laat stadium alsnog geschrapt uit de gegevensset.
 * De gekozen constructie van o.a. Relatie is echter nog steeds gebaseerd op mogelijke toevoegingen.
 * De keuze om NIET terug te komen op de constructie is gebaseerd op enerzijds het late stadium waarin het schrappen van
 * de verschillende relatievormen is doorgevoerd, en anderzijds de mogelijkheid om in de toekomst eventuele nieuwe
 * (binaire) relatievormen eenvoudig te kunnen toevoegen.
 * RvdP, 5 augustus 2011
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractRelatieModel extends AbstractDynamischObjectType implements RelatieBasis {

    @Id
    @SequenceGenerator(name = "RELATIE", sequenceName = "Kern.seq_Relatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "RELATIE")
    @JsonProperty
    private Integer                 iD;

    @Enumerated
    @Column(name = "Srt", updatable = false, insertable = false)
    @JsonProperty
    private SoortRelatie            soort;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Relatie")
    @JsonProperty
    @Sort(type = SortType.NATURAL)
    private Set<BetrokkenheidModel> betrokkenheden;

    @Type(type = "StatusHistorie")
    @Column(name = "ErkenningOngeborenVruchtStat")
    @JsonProperty
    private StatusHistorie          erkenningOngeborenVruchtStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "HuwelijkGeregistreerdPartner")
    @JsonProperty
    private StatusHistorie          huwelijkGeregistreerdPartnerschapStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "NaamskeuzeOngeborenVruchtSta")
    @JsonProperty
    private StatusHistorie          naamskeuzeOngeborenVruchtStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractRelatieModel() {
        betrokkenheden = new TreeSet<BetrokkenheidModel>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Relatie.
     */
    public AbstractRelatieModel(final SoortRelatie soort) {
        this();
        this.soort = soort;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param relatie Te kopieren object type.
     */
    public AbstractRelatieModel(final Relatie relatie) {
        this();
        this.soort = relatie.getSoort();

    }

    /**
     * Retourneert ID van Relatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Relatie.
     *
     * @return Soort.
     */
    public SoortRelatie getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BetrokkenheidModel> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * Retourneert Erkenning ongeboren vrucht StatusHis van Relatie.
     *
     * @return Erkenning ongeboren vrucht StatusHis.
     */
    public StatusHistorie getErkenningOngeborenVruchtStatusHis() {
        return erkenningOngeborenVruchtStatusHis;
    }

    /**
     * Retourneert Huwelijk / Geregistreerd partnerschap StatusHis van Relatie.
     *
     * @return Huwelijk / Geregistreerd partnerschap StatusHis.
     */
    public StatusHistorie getHuwelijkGeregistreerdPartnerschapStatusHis() {
        return huwelijkGeregistreerdPartnerschapStatusHis;
    }

    /**
     * Retourneert Naamskeuze ongeboren vrucht StatusHis van Relatie.
     *
     * @return Naamskeuze ongeboren vrucht StatusHis.
     */
    public StatusHistorie getNaamskeuzeOngeborenVruchtStatusHis() {
        return naamskeuzeOngeborenVruchtStatusHis;
    }

    /**
     * Zet Erkenning ongeboren vrucht StatusHis van Relatie.
     *
     * @param erkenningOngeborenVruchtStatusHis Erkenning ongeboren vrucht StatusHis.
     */
    public void setErkenningOngeborenVruchtStatusHis(final StatusHistorie erkenningOngeborenVruchtStatusHis) {
        this.erkenningOngeborenVruchtStatusHis = erkenningOngeborenVruchtStatusHis;
    }

    /**
     * Zet Huwelijk / Geregistreerd partnerschap StatusHis van Relatie.
     *
     * @param huwelijkGeregistreerdPartnerschapStatusHis Huwelijk / Geregistreerd partnerschap StatusHis.
     */
    public void setHuwelijkGeregistreerdPartnerschapStatusHis(
            final StatusHistorie huwelijkGeregistreerdPartnerschapStatusHis)
    {
        this.huwelijkGeregistreerdPartnerschapStatusHis = huwelijkGeregistreerdPartnerschapStatusHis;
    }

    /**
     * Zet Naamskeuze ongeboren vrucht StatusHis van Relatie.
     *
     * @param naamskeuzeOngeborenVruchtStatusHis Naamskeuze ongeboren vrucht StatusHis.
     */
    public void setNaamskeuzeOngeborenVruchtStatusHis(final StatusHistorie naamskeuzeOngeborenVruchtStatusHis) {
        this.naamskeuzeOngeborenVruchtStatusHis = naamskeuzeOngeborenVruchtStatusHis;
    }

}
