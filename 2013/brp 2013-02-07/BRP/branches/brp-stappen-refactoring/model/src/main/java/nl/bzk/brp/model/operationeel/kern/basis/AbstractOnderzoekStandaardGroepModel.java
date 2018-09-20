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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnderzoekOmschrijving;
import nl.bzk.brp.model.logisch.kern.OnderzoekStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.OnderzoekStandaardGroepBasis;


/**
 * Vorm van historie: formeel. Motivatie: 'onderzoek' is een construct om vast te leggen dat een bepaald gegeven
 * onderwerp is van onderzoek. Hierbij is het in principe alleen relevant of een gegeven NU in onderzoek is. Verder is
 * het voldoende om te weten of tijdens een bepaalde levering een gegeven wel of niet als 'in onderzoek' stond
 * geregistreerd. NB: de gegevens over het onderzoek zelf staan niet in de BRP, maar in bijvoorbeeld de zaaksystemen.
 * Omdat formele historie dus volstaat, wordt de materi�le historie onderdrukt. RvdP 17 jan 2012.
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
public abstract class AbstractOnderzoekStandaardGroepModel implements OnderzoekStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatBegin"))
    @JsonProperty
    private Datum                 datumBegin;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
    @JsonProperty
    private Datum                 datumEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    @JsonProperty
    private OnderzoekOmschrijving omschrijving;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractOnderzoekStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumBegin datumBegin van Standaard.
     * @param datumEinde datumEinde van Standaard.
     * @param omschrijving omschrijving van Standaard.
     */
    public AbstractOnderzoekStandaardGroepModel(final Datum datumBegin, final Datum datumEinde,
            final OnderzoekOmschrijving omschrijving)
    {
        this.datumBegin = datumBegin;
        this.datumEinde = datumEinde;
        this.omschrijving = omschrijving;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumBegin() {
        return datumBegin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekOmschrijving getOmschrijving() {
        return omschrijving;
    }

}
