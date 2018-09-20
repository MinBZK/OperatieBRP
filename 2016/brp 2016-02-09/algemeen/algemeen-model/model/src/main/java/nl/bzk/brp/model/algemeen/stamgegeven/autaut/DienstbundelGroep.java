/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * De koppeling tussen leveringsautorisatie enerzijds en groep anderzijds.
 *
 * Afnemers verkrijgen gegevens uit ��n of meerdere groepen via een leveringsautorisatie. Voor elke groep waartoe deze gegevens
 * behoren, wordt aangegeven of er extra informatie mag worden verstrekt in kader van dat leveringsautorisatie. Zo mag in bepaalde
 * gevallen een ontvanger van gegevens uit de groep samengestelde_naam ook gegevens verkrijgen over de materi�le
 * historie hiervan.
 *
 *
 *
 */
@Table(schema = "AutAut", name = "DienstbundelGroep")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class DienstbundelGroep extends AbstractDienstbundelGroep {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "dienstbundelgroep", referencedColumnName = "id")
    private Set<DienstbundelGroepAttribuutImpl> attributen;


    /**

     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected DienstbundelGroep() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param dienstbundel dienstbundel van DienstbundelGroep.
     * @param groep groep van DienstbundelGroep.
     * @param indicatieFormeleHistorie indicatieFormeleHistorie van DienstbundelGroep.
     * @param indicatieMaterieleHistorie indicatieMaterieleHistorie van DienstbundelGroep.
     * @param indicatieVerantwoording indicatieVerantwoording van DienstbundelGroep.
     */
    protected DienstbundelGroep(
        final Dienstbundel dienstbundel,
        final Element groep,
        final JaNeeAttribuut indicatieFormeleHistorie,
        final JaNeeAttribuut indicatieMaterieleHistorie,
        final JaNeeAttribuut indicatieVerantwoording)
    {
        super(dienstbundel, groep, indicatieFormeleHistorie, indicatieMaterieleHistorie, indicatieVerantwoording);
    }

    public Set<DienstbundelGroepAttribuutImpl> getAttributen() {
        return attributen;
    }

    public void setAttributen(final Set<DienstbundelGroepAttribuutImpl> attributen) {
        this.attributen = attributen;
    }

}
