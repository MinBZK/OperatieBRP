/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.logisch.kern.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonEUVerkiezingenGroepModel;


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
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:36 CET 2012.
 */
@Embeddable
public class PersoonEUVerkiezingenGroepModel extends AbstractPersoonEUVerkiezingenGroepModel implements
        PersoonEUVerkiezingenGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonEUVerkiezingenGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieDeelnameEUVerkiezingen indicatieDeelnameEUVerkiezingen van EU verkiezingen.
     * @param datumAanleidingAanpassingDeelnameEUVerkiezing datumAanleidingAanpassingDeelnameEUVerkiezing van EU
     *            verkiezingen.
     * @param datumEindeUitsluitingEUKiesrecht datumEindeUitsluitingEUKiesrecht van EU verkiezingen.
     */
    public PersoonEUVerkiezingenGroepModel(final JaNee indicatieDeelnameEUVerkiezingen,
            final Datum datumAanleidingAanpassingDeelnameEUVerkiezing, final Datum datumEindeUitsluitingEUKiesrecht)
    {
        super(indicatieDeelnameEUVerkiezingen, datumAanleidingAanpassingDeelnameEUVerkiezing,
                datumEindeUitsluitingEUKiesrecht);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonEUVerkiezingenGroep te kopieren groep.
     */
    public PersoonEUVerkiezingenGroepModel(final PersoonEUVerkiezingenGroep persoonEUVerkiezingenGroep) {
        super(persoonEUVerkiezingenGroep);
    }

}
