/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderlijkGezagGroepModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderschapGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Ouder.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractOuderHisVolledigImpl extends BetrokkenheidHisVolledigImpl implements OuderHisVolledigBasis, ALaagAfleidbaar,
        ElementIdentificeerbaar
{

    @Embedded
    private OuderOuderschapGroepModel ouderschap;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "betrokkenheid", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisOuderOuderschapModel> hisOuderOuderschapLijst;

    @Transient
    private MaterieleHistorieSet<HisOuderOuderschapModel> ouderOuderschapHistorie;

    @Embedded
    private OuderOuderlijkGezagGroepModel ouderlijkGezag;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "betrokkenheid", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisOuderOuderlijkGezagModel> hisOuderOuderlijkGezagLijst;

    @Transient
    private MaterieleHistorieSet<HisOuderOuderlijkGezagModel> ouderOuderlijkGezagHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractOuderHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Ouder.
     * @param persoon persoon van Ouder.
     */
    public AbstractOuderHisVolledigImpl(final RelatieHisVolledigImpl relatie, final PersoonHisVolledigImpl persoon) {
        super(relatie, new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.OUDER), persoon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leidALaagAf() {
        final HisOuderOuderschapModel actueelOuderschap = getOuderOuderschapHistorie().getActueleRecord();
        if (actueelOuderschap != null) {
            this.ouderschap =
                    new OuderOuderschapGroepModel(actueelOuderschap.getIndicatieOuder(), actueelOuderschap.getIndicatieOuderUitWieKindIsGeboren());
        } else {
            this.ouderschap = null;
        }
        final HisOuderOuderlijkGezagModel actueelOuderlijkGezag = getOuderOuderlijkGezagHistorie().getActueleRecord();
        if (actueelOuderlijkGezag != null) {
            this.ouderlijkGezag = new OuderOuderlijkGezagGroepModel(actueelOuderlijkGezag.getIndicatieOuderHeeftGezag());
        } else {
            this.ouderlijkGezag = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisOuderOuderschapModel> getOuderOuderschapHistorie() {
        if (hisOuderOuderschapLijst == null) {
            hisOuderOuderschapLijst = new HashSet<>();
        }
        if (ouderOuderschapHistorie == null) {
            ouderOuderschapHistorie = new MaterieleHistorieSetImpl<HisOuderOuderschapModel>(hisOuderOuderschapLijst);
        }
        return ouderOuderschapHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisOuderOuderlijkGezagModel> getOuderOuderlijkGezagHistorie() {
        if (hisOuderOuderlijkGezagLijst == null) {
            hisOuderOuderlijkGezagLijst = new HashSet<>();
        }
        if (ouderOuderlijkGezagHistorie == null) {
            ouderOuderlijkGezagHistorie = new MaterieleHistorieSetImpl<HisOuderOuderlijkGezagModel>(hisOuderOuderlijkGezagLijst);
        }
        return ouderOuderlijkGezagHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.OUDER;
    }

}
