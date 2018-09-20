/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.brm;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisRegelsituatie extends AbstractFormeelHistorischMetActieVerantwoording implements ModelIdentificeerbaar<Integer> {

    @Id
    @SequenceGenerator(name = "HIS_REGELSITUATIE", sequenceName = "BRM.seq_His_Regelsituatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_REGELSITUATIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Regelsituatie")
    private Regelsituatie regelsituatie;

    @Embedded
    @AttributeOverride(name = BijhoudingsaardAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Bijhaard"))
    @JsonProperty
    private BijhoudingsaardAttribuut bijhoudingsaard;

    @Embedded
    @AttributeOverride(name = NadereBijhoudingsaardAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NadereBijhaard"))
    @JsonProperty
    private NadereBijhoudingsaardAttribuut nadereBijhoudingsaard;

    @Embedded
    @AttributeOverride(name = RegeleffectAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Effect"))
    @JsonProperty
    private RegeleffectAttribuut effect;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndActief"))
    @JsonProperty
    private JaNeeAttribuut indicatieActief;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisRegelsituatie() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param regelsituatie regelsituatie van HisRegelsituatie
     * @param bijhoudingsaard bijhoudingsaard van HisRegelsituatie
     * @param nadereBijhoudingsaard nadereBijhoudingsaard van HisRegelsituatie
     * @param effect effect van HisRegelsituatie
     * @param indicatieActief indicatieActief van HisRegelsituatie
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisRegelsituatie(
        final Regelsituatie regelsituatie,
        final BijhoudingsaardAttribuut bijhoudingsaard,
        final NadereBijhoudingsaardAttribuut nadereBijhoudingsaard,
        final RegeleffectAttribuut effect,
        final JaNeeAttribuut indicatieActief,
        final ActieModel actieInhoud)
    {
        this.regelsituatie = regelsituatie;
        this.bijhoudingsaard = bijhoudingsaard;
        this.nadereBijhoudingsaard = nadereBijhoudingsaard;
        this.effect = effect;
        this.indicatieActief = indicatieActief;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisRegelsituatie(final AbstractHisRegelsituatie kopie) {
        super(kopie);
        regelsituatie = kopie.getRegelsituatie();
        bijhoudingsaard = kopie.getBijhoudingsaard();
        nadereBijhoudingsaard = kopie.getNadereBijhoudingsaard();
        effect = kopie.getEffect();
        indicatieActief = kopie.getIndicatieActief();

    }

    /**
     * Retourneert ID van His Regelsituatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Regelsituatie van His Regelsituatie.
     *
     * @return Regelsituatie.
     */
    public Regelsituatie getRegelsituatie() {
        return regelsituatie;
    }

    /**
     * Retourneert Bijhoudingsaard van His Regelsituatie.
     *
     * @return Bijhoudingsaard.
     */
    public BijhoudingsaardAttribuut getBijhoudingsaard() {
        return bijhoudingsaard;
    }

    /**
     * Retourneert Nadere bijhoudingsaard van His Regelsituatie.
     *
     * @return Nadere bijhoudingsaard.
     */
    public NadereBijhoudingsaardAttribuut getNadereBijhoudingsaard() {
        return nadereBijhoudingsaard;
    }

    /**
     * Retourneert Effect van His Regelsituatie.
     *
     * @return Effect.
     */
    public RegeleffectAttribuut getEffect() {
        return effect;
    }

    /**
     * Retourneert Actief? van His Regelsituatie.
     *
     * @return Actief?.
     */
    public JaNeeAttribuut getIndicatieActief() {
        return indicatieActief;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (bijhoudingsaard != null) {
            attributen.add(bijhoudingsaard);
        }
        if (nadereBijhoudingsaard != null) {
            attributen.add(nadereBijhoudingsaard);
        }
        if (effect != null) {
            attributen.add(effect);
        }
        if (indicatieActief != null) {
            attributen.add(indicatieActief);
        }
        return attributen;
    }

}
