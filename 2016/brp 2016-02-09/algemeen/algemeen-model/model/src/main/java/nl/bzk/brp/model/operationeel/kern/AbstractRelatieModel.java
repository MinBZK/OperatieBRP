/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.Relatie;
import nl.bzk.brp.model.logisch.kern.RelatieBasis;

/**
 * De relatie tussen personen.
 *
 * Een relatie tussen twee of meer personen is als aparte object opgenomen. Het relatie-object beschrijft om wat voor
 * soort relatie het gaat, en waar en wanneer deze begonnen en/of beëindigd is. Het koppelen van een persoon aan een
 * relatie gebeurt via een object van het type betrokkenheid.
 *
 * 1. Naast de onderkende relatievormen (Huwelijk, geregistreerd partnerschap en familierechtelijkebetrekking) is er
 * lange tijd sprake geweest van nog een aantal binaire relatievormen: erkenning ongeboren vrucht, ontkenning ouderschap
 * en naamskeuze ongeboren vrucht. Deze relatievormen zijn in een laat stadium alsnog geschrapt uit de gegevensset. In
 * een nog later stadium zijn ze weer teruggekomen als 'gegevens van de ABS' (ambtenaar burgerzaken systeem). De gekozen
 * constructie van o.a. Relatie is mede daarom nog steeds gebaseerd op mogelijke toevoegingen: het is nu eenvoudig om in
 * de toekomst eventuele nieuwe (binaire) relatievormen toe te voegen.
 *
 * 2. Naar Nederlands recht heeft een kind altijd tenminste een moeder, die eventueel onbekend kan zijn. Naar
 * buitenlands recht kan het echter voorkomen dat een kind vastgesteld geen moeder heeft. In de BRP leggen we dat vast
 * door alleen een familierechtelijke betrekking aan te maken zonder dat die ook een betrokkenheid 'Ouder' heeft. Er kan
 * dan geen verantwoording vastgelegd worden over dit feit. Dit is volledig terecht. In tegenstelling tot het GBA-model,
 * waar de 02 Ouder 1 en 03 Ouder 2 verplichte categorieën waren. Dat heeft tot gevolg dat er iets moet worden opgenomen
 * in deze categorieën, ook al zou die juridisch gezien gewoon niet moeten voorkomen.
 *
 * 3. Er is redelijk wat discussie geweest over de correcte modellering van relaties; had dit niet geimplementeerd
 * moeten worden middels (meerdere) binaire relaties tussen twee personen? De overweging voor de huidige wijze (relatie
 * met betrokkenheden) is vanuit het perspectief dat een Huwelijk, Partnerschap etc. een concept is waar je feiten over
 * vast wilt leggen, ongeacht de betrokkenheden. Dat is bij binaire relaties niet eenduidig mogelijk. Daarnaast hebben
 * binaire relaties altijd volgorde. Dit is niet het geval bij de 'set van betrokkenheden'.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractRelatieModel extends AbstractDynamischObject implements RelatieBasis, ModelIdentificeerbaar<Integer> {

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = SoortRelatieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt", updatable = false, insertable = false))
    @JsonProperty
    private SoortRelatieAttribuut soort;

    @Embedded
    @JsonProperty
    private RelatieStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Relatie")
    @JsonProperty
    private Set<BetrokkenheidModel> betrokkenheden;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractRelatieModel() {
        betrokkenheden = new HashSet<BetrokkenheidModel>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Relatie.
     */
    public AbstractRelatieModel(final SoortRelatieAttribuut soort) {
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
        if (relatie.getStandaard() != null) {
            this.standaard = new RelatieStandaardGroepModel(relatie.getStandaard());
        }

    }

    /**
     * Retourneert ID van Relatie.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "RELATIE", sequenceName = "Kern.seq_Relatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "RELATIE")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortRelatieAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelatieStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BetrokkenheidModel> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Integer id) {
        this.iD = id;
    }

    /**
     * Zet Standaard van Relatie.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final RelatieStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (standaard != null) {
            groepen.add(standaard);
        }
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (soort != null) {
            attributen.add(soort);
        }
        return attributen;
    }

}
