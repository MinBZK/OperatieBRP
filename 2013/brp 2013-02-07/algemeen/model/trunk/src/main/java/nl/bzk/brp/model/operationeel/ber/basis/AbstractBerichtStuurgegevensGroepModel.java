/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Applicatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Organisatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Sleutelwaardetekst;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroep;
import nl.bzk.brp.model.logisch.ber.basis.BerichtStuurgegevensGroepBasis;


/**
 *
 *
 */
@MappedSuperclass
public abstract class AbstractBerichtStuurgegevensGroepModel implements BerichtStuurgegevensGroepBasis {

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Organisatie"))
    @JsonProperty
    private Organisatienaam    organisatie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Applicatie"))
    @JsonProperty
    private Applicatienaam     applicatie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Referentienr"))
    @JsonProperty
    private Sleutelwaardetekst referentienummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "CrossReferentienr"))
    @JsonProperty
    private Sleutelwaardetekst crossReferentienummer;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractBerichtStuurgegevensGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param organisatie organisatie van Stuurgegevens.
     * @param applicatie applicatie van Stuurgegevens.
     * @param referentienummer referentienummer van Stuurgegevens.
     * @param crossReferentienummer crossReferentienummer van Stuurgegevens.
     */
    public AbstractBerichtStuurgegevensGroepModel(final Organisatienaam organisatie, final Applicatienaam applicatie,
            final Sleutelwaardetekst referentienummer, final Sleutelwaardetekst crossReferentienummer)
    {
        this.organisatie = organisatie;
        this.applicatie = applicatie;
        this.referentienummer = referentienummer;
        this.crossReferentienummer = crossReferentienummer;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtStuurgegevensGroep te kopieren groep.
     */
    public AbstractBerichtStuurgegevensGroepModel(final BerichtStuurgegevensGroep berichtStuurgegevensGroep) {
        this.organisatie = berichtStuurgegevensGroep.getOrganisatie();
        this.applicatie = berichtStuurgegevensGroep.getApplicatie();
        this.referentienummer = berichtStuurgegevensGroep.getReferentienummer();
        this.crossReferentienummer = berichtStuurgegevensGroep.getCrossReferentienummer();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Organisatienaam getOrganisatie() {
        return organisatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Applicatienaam getApplicatie() {
        return applicatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sleutelwaardetekst getReferentienummer() {
        return referentienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sleutelwaardetekst getCrossReferentienummer() {
        return crossReferentienummer;
    }

}
