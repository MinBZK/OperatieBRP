/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import nl.bzk.brp.model.hisvolledig.impl.kern.AbstractPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledig;


/**
 * Subtype klasse voor indicatie Signalering met betrekking tot verstrekken reisdocument?
 */
@Entity
@DiscriminatorValue(value = "6")
public class PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl extends
    AbstractPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl implements
    PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledig
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     */
    protected PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl() {
        super();
    }

    /**
     * Constructor met backreference naar persoon.
     *
     * @param persoon backreference
     */
    public PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl(
        final PersoonHisVolledigImpl persoon)
    {
        super(persoon);
    }

}
