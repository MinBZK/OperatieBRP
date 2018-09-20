/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPartijBijhouding extends AbstractFormeelHistorischMetActieVerantwoording implements ModelIdentificeerbaar<Integer>,
        ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PARTIJBIJHOUDING", sequenceName = "Kern.seq_His_PartijBijhouding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PARTIJBIJHOUDING")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Partij")
    private Partij partij;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndAutoFiat"))
    @JsonProperty
    private JaNeeAttribuut indicatieAutomatischFiatteren;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatOvergangNaarBRP"))
    @JsonProperty
    private DatumAttribuut datumOvergangNaarBRP;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPartijBijhouding() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param partij partij van HisPartijBijhouding
     * @param indicatieAutomatischFiatteren indicatieAutomatischFiatteren van HisPartijBijhouding
     * @param datumOvergangNaarBRP datumOvergangNaarBRP van HisPartijBijhouding
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPartijBijhouding(
        final Partij partij,
        final JaNeeAttribuut indicatieAutomatischFiatteren,
        final DatumAttribuut datumOvergangNaarBRP,
        final ActieModel actieInhoud)
    {
        this.partij = partij;
        this.indicatieAutomatischFiatteren = indicatieAutomatischFiatteren;
        this.datumOvergangNaarBRP = datumOvergangNaarBRP;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPartijBijhouding(final AbstractHisPartijBijhouding kopie) {
        super(kopie);
        partij = kopie.getPartij();
        indicatieAutomatischFiatteren = kopie.getIndicatieAutomatischFiatteren();
        datumOvergangNaarBRP = kopie.getDatumOvergangNaarBRP();

    }

    /**
     * Retourneert ID van His Partij Bijhouding.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij van His Partij Bijhouding.
     *
     * @return Partij.
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Automatisch fiatteren? van His Partij Bijhouding.
     *
     * @return Automatisch fiatteren?.
     */
    public JaNeeAttribuut getIndicatieAutomatischFiatteren() {
        return indicatieAutomatischFiatteren;
    }

    /**
     * Retourneert Datum overgang naar BRP van His Partij Bijhouding.
     *
     * @return Datum overgang naar BRP.
     */
    public DatumAttribuut getDatumOvergangNaarBRP() {
        return datumOvergangNaarBRP;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieAutomatischFiatteren != null) {
            attributen.add(indicatieAutomatischFiatteren);
        }
        if (datumOvergangNaarBRP != null) {
            attributen.add(datumOvergangNaarBRP);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PARTIJ_BIJHOUDING;
    }

}
