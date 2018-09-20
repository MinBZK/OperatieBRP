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
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonDeelnameEUVerkiezingenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonDeelnameEUVerkiezingenGroepBasis;

/**
 * Gegevens over de eventuele deelname aan EU verkiezingen.
 *
 * EU-burgers die in een andere lidstaat wonen hebben het recht om daar te stemmen. EU burgers van andere lidstaten, die
 * in Nederland wonen, en gebruik willen maken van hun stemrecht, doen daartoe een verzoek.
 *
 * Vorm van historie: conform 'NL verkiezingen' zou een materiële tijdslijn wel betekenis hebben, maar ontbreekt de
 * business case om deze vast te leggen. Om die reden wordt de materiële tijdslijn onderdrukt, en is in de BRP alleen de
 * formele tijdslijn vastgelegd. Zie ook NL verkiezingen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonDeelnameEUVerkiezingenGroepModel implements PersoonDeelnameEUVerkiezingenGroepBasis {

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndDeelnEUVerkiezingen"))
    @JsonProperty
    private JaNeeAttribuut indicatieDeelnameEUVerkiezingen;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanlAanpDeelnEUVerkiezing"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanleidingAanpassingDeelnameEUVerkiezingen;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatVoorzEindeUitslEUVerkiezi"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingEUVerkiezingen;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonDeelnameEUVerkiezingenGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieDeelnameEUVerkiezingen indicatieDeelnameEUVerkiezingen van Deelname EU verkiezingen.
     * @param datumAanleidingAanpassingDeelnameEUVerkiezingen datumAanleidingAanpassingDeelnameEUVerkiezingen van
     *            Deelname EU verkiezingen.
     * @param datumVoorzienEindeUitsluitingEUVerkiezingen datumVoorzienEindeUitsluitingEUVerkiezingen van Deelname EU
     *            verkiezingen.
     */
    public AbstractPersoonDeelnameEUVerkiezingenGroepModel(
        final JaNeeAttribuut indicatieDeelnameEUVerkiezingen,
        final DatumEvtDeelsOnbekendAttribuut datumAanleidingAanpassingDeelnameEUVerkiezingen,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingEUVerkiezingen)
    {
        this.indicatieDeelnameEUVerkiezingen = indicatieDeelnameEUVerkiezingen;
        this.datumAanleidingAanpassingDeelnameEUVerkiezingen = datumAanleidingAanpassingDeelnameEUVerkiezingen;
        this.datumVoorzienEindeUitsluitingEUVerkiezingen = datumVoorzienEindeUitsluitingEUVerkiezingen;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonDeelnameEUVerkiezingenGroep te kopieren groep.
     */
    public AbstractPersoonDeelnameEUVerkiezingenGroepModel(final PersoonDeelnameEUVerkiezingenGroep persoonDeelnameEUVerkiezingenGroep) {
        this.indicatieDeelnameEUVerkiezingen = persoonDeelnameEUVerkiezingenGroep.getIndicatieDeelnameEUVerkiezingen();
        this.datumAanleidingAanpassingDeelnameEUVerkiezingen = persoonDeelnameEUVerkiezingenGroep.getDatumAanleidingAanpassingDeelnameEUVerkiezingen();
        this.datumVoorzienEindeUitsluitingEUVerkiezingen = persoonDeelnameEUVerkiezingenGroep.getDatumVoorzienEindeUitsluitingEUVerkiezingen();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieDeelnameEUVerkiezingen() {
        return indicatieDeelnameEUVerkiezingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanleidingAanpassingDeelnameEUVerkiezingen() {
        return datumAanleidingAanpassingDeelnameEUVerkiezingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumVoorzienEindeUitsluitingEUVerkiezingen() {
        return datumVoorzienEindeUitsluitingEUVerkiezingen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieDeelnameEUVerkiezingen != null) {
            attributen.add(indicatieDeelnameEUVerkiezingen);
        }
        if (datumAanleidingAanpassingDeelnameEUVerkiezingen != null) {
            attributen.add(datumAanleidingAanpassingDeelnameEUVerkiezingen);
        }
        if (datumVoorzienEindeUitsluitingEUVerkiezingen != null) {
            attributen.add(datumVoorzienEindeUitsluitingEUVerkiezingen);
        }
        return attributen;
    }

}
