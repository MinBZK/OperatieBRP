/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonDeelnameEUVerkiezingenGroep;


/**
 * Gegevens over de eventuele deelname aan EU verkiezingen.
 * <p/>
 * EU-burgers die in een andere lidstaat wonen hebben het recht om daar te stemmen. EU burgers van andere lidstaten, die in Nederland wonen, en gebruik
 * willen maken van hun stemrecht, doen daartoe een verzoek.
 * <p/>
 * Vorm van historie: conform 'NL verkiezingen' zou een materi�le tijdslijn wel betekenis hebben, maar ontbreekt de business case om deze vast te leggen.
 * Om die reden wordt de materi�le tijdslijn onderdrukt, en is in de BRP alleen de formele tijdslijn vastgelegd. Zie ook NL verkiezingen.
 */
@Embeddable
public class PersoonDeelnameEUVerkiezingenGroepModel extends AbstractPersoonDeelnameEUVerkiezingenGroepModel implements
    PersoonDeelnameEUVerkiezingenGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonDeelnameEUVerkiezingenGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieDeelnameEUVerkiezingen
     *         indicatieDeelnameEUVerkiezingen van Deelname EU verkiezingen.
     * @param datumAanleidingAanpassingDeelnameEUVerkiezingen
     *         datumAanleidingAanpassingDeelnameEUVerkiezingen van Deelname EU verkiezingen.
     * @param datumVoorzienEindeUitsluitingEUVerkiezingen
     *         datumVoorzienEindeUitsluitingEUVerkiezingen van Deelname EU verkiezingen.
     */
    public PersoonDeelnameEUVerkiezingenGroepModel(final JaNeeAttribuut indicatieDeelnameEUVerkiezingen,
        final DatumEvtDeelsOnbekendAttribuut datumAanleidingAanpassingDeelnameEUVerkiezingen,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingEUVerkiezingen)
    {
        super(indicatieDeelnameEUVerkiezingen, datumAanleidingAanpassingDeelnameEUVerkiezingen,
            datumVoorzienEindeUitsluitingEUVerkiezingen);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonDeelnameEUVerkiezingenGroep
     *         te kopieren groep.
     */
    public PersoonDeelnameEUVerkiezingenGroepModel(
        final PersoonDeelnameEUVerkiezingenGroep persoonDeelnameEUVerkiezingenGroep)
    {
        super(persoonDeelnameEUVerkiezingenGroep);
    }

}
