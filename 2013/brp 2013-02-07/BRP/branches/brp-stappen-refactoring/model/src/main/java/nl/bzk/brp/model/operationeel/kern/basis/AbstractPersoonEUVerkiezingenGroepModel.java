/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.logisch.kern.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonEUVerkiezingenGroepBasis;


/**
 * Gegevens over de eventuele deelname aan EU verkiezingen.
 *
 * EU-burgers die in een andere lidstaat wonen hebben het recht om daar te stemmen. EU burgers van andere lidstaten, die
 * in Nederland wonen, en gebruik willen maken van hun stemrecht, doen daartoe een verzoek.
 *
 * Vorm van historie: conform 'NL verkiezingen' zou een materi�le tijdslijn wel betekenis hebben, maar ontbreekt de
 * business case om deze vast te leggen. Om die reden wordt de materi�le tijdslijn onderdrukt, en is in de BRP alleen de
 * formele tijdslijn vastgelegd. Zie ook NL verkiezingen.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractPersoonEUVerkiezingenGroepModel implements PersoonEUVerkiezingenGroepBasis {

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndDeelnEUVerkiezingen"))
    @JsonProperty
    private JaNee indicatieDeelnameEUVerkiezingen;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanlAanpDeelnEUVerkiezing"))
    @JsonProperty
    private Datum datumAanleidingAanpassingDeelnameEUVerkiezing;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEindeUitslEUKiesr"))
    @JsonProperty
    private Datum datumEindeUitsluitingEUKiesrecht;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonEUVerkiezingenGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieDeelnameEUVerkiezingen indicatieDeelnameEUVerkiezingen van EU verkiezingen.
     * @param datumAanleidingAanpassingDeelnameEUVerkiezing datumAanleidingAanpassingDeelnameEUVerkiezing van EU
     *            verkiezingen.
     * @param datumEindeUitsluitingEUKiesrecht datumEindeUitsluitingEUKiesrecht van EU verkiezingen.
     */
    public AbstractPersoonEUVerkiezingenGroepModel(final JaNee indicatieDeelnameEUVerkiezingen,
            final Datum datumAanleidingAanpassingDeelnameEUVerkiezing, final Datum datumEindeUitsluitingEUKiesrecht)
    {
        this.indicatieDeelnameEUVerkiezingen = indicatieDeelnameEUVerkiezingen;
        this.datumAanleidingAanpassingDeelnameEUVerkiezing = datumAanleidingAanpassingDeelnameEUVerkiezing;
        this.datumEindeUitsluitingEUKiesrecht = datumEindeUitsluitingEUKiesrecht;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonEUVerkiezingenGroep te kopieren groep.
     */
    public AbstractPersoonEUVerkiezingenGroepModel(final PersoonEUVerkiezingenGroep persoonEUVerkiezingenGroep) {
        this.indicatieDeelnameEUVerkiezingen = persoonEUVerkiezingenGroep.getIndicatieDeelnameEUVerkiezingen();
        this.datumAanleidingAanpassingDeelnameEUVerkiezing =
            persoonEUVerkiezingenGroep.getDatumAanleidingAanpassingDeelnameEUVerkiezing();
        this.datumEindeUitsluitingEUKiesrecht = persoonEUVerkiezingenGroep.getDatumEindeUitsluitingEUKiesrecht();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieDeelnameEUVerkiezingen() {
        return indicatieDeelnameEUVerkiezingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumAanleidingAanpassingDeelnameEUVerkiezing() {
        return datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumEindeUitsluitingEUKiesrecht() {
        return datumEindeUitsluitingEUKiesrecht;
    }

}
