/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonIndicatiesBasis;
import nl.bzk.brp.model.objecttype.statisch.SoortIndicatie;
import org.hibernate.annotations.Type;


/**
 * Implementatie voor objecttype Persoon voornaam.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonIndicatieMdl extends AbstractDynamischObjectType implements PersoonIndicatiesBasis {

    @Id
    @SequenceGenerator(name = "PERSOONINDICATIE", sequenceName = "Kern.seq_PersIndicatie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOONINDICATIE")
    private Long                             id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    @NotNull
    private PersoonMdl                       persoon;

    @Column(name = "Srt")
    @NotNull
    private SoortIndicatie                   soort;

    @Column(name = "Waarde")
    @Type(type = "JaNee")
    private JaNee                            waarde;

    public Long getId() {
        return id;
    }

    @Override
    public PersoonMdl getPersoon() {
        return persoon;
    }

    @Override
    public SoortIndicatie getSoort() {
        return soort;
    }

    @Override
    public JaNee getWaarde() {
        return waarde;
    }

    public void setSoort(final SoortIndicatie soort) {
        this.soort = soort;
    }

    public void setWaarde(final JaNee waarde) {
        this.waarde = waarde;
    }
}
