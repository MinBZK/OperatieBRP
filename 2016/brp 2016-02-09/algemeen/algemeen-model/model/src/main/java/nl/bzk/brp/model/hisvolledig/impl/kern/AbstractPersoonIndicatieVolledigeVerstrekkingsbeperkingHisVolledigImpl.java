/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import java.util.HashSet;
import javax.annotation.Generated;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel;

/**
 * Subtype klasse voor indicatie Volledige verstrekkingsbeperking?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl extends
        PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel> implements
        PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigBasis
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl() {
    }

    /**
     * Constructor met backreference naar persoon.
     *
     * @param persoon backreference
     */
    public AbstractPersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl(final PersoonHisVolledigImpl persoon) {
        super(persoon, new SoortIndicatieAttribuut(SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel> getPersoonIndicatieHistorie() {
        if (hisPersoonIndicatieLijst == null) {
            hisPersoonIndicatieLijst = new HashSet<>();
        }
        if (persoonIndicatieHistorie == null) {
            persoonIndicatieHistorie = new FormeleHistorieSetImpl<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel>(hisPersoonIndicatieLijst);
        }
        return (FormeleHistorieSet<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel>) persoonIndicatieHistorie;

    }

}
