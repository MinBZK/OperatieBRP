/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.Applicatienaam;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.Organisatienaam;
import nl.bzk.brp.model.attribuuttype.Sleutelwaardetekst;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.BerichtStuurgegevensGroepBasis;
import org.hibernate.annotations.Type;

/**
 *.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractBerichtStuurgegevensGroep extends AbstractGroep
        implements BerichtStuurgegevensGroepBasis
{

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Organisatie"))
    private Organisatienaam organisatie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Applicatie"))
    private Applicatienaam applicatie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Referentienr"))
    private Sleutelwaardetekst referentienummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "CrossReferentienr"))
    private Sleutelwaardetekst crossReferentienummer;

    @Column(name = "IndPrevalidatie")
    @Type(type = "Ja")
    private Ja indPrevalidatie;

    /**
     * Copy constructor.
     * @param berichtStuurgegevensGroepBasis Bericht stuurgegevens waarvan gekopieerd dient te worden.
     */
    public AbstractBerichtStuurgegevensGroep(final BerichtStuurgegevensGroepBasis berichtStuurgegevensGroepBasis) {
        organisatie = berichtStuurgegevensGroepBasis.getOrganisatie();
        applicatie = berichtStuurgegevensGroepBasis.getApplicatie();
        referentienummer = berichtStuurgegevensGroepBasis.getReferentienummer();
        crossReferentienummer = berichtStuurgegevensGroepBasis.getCrossReferentienummer();
        indPrevalidatie = berichtStuurgegevensGroepBasis.getIndPrevalidatie();
    }

    /**
     * Default constructor.
     */
    protected AbstractBerichtStuurgegevensGroep() {
    }

    public Organisatienaam getOrganisatie() {
        return organisatie;
    }

    public Applicatienaam getApplicatie() {
        return applicatie;
    }

    public Sleutelwaardetekst getReferentienummer() {
        return referentienummer;
    }

    public Sleutelwaardetekst getCrossReferentienummer() {
        return crossReferentienummer;
    }

    public Ja getIndPrevalidatie() {
        return indPrevalidatie;
    }
}
