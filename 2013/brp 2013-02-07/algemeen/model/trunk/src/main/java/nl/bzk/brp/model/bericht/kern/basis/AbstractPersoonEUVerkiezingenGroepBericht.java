/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.PersoonEUVerkiezingenGroepBasis;


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
 */
public abstract class AbstractPersoonEUVerkiezingenGroepBericht extends AbstractGroepBericht implements
        PersoonEUVerkiezingenGroepBasis
{

    private JaNee indicatieDeelnameEUVerkiezingen;
    private Datum datumAanleidingAanpassingDeelnameEUVerkiezing;
    private Datum datumEindeUitsluitingEUKiesrecht;

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieDeelnameEUVerkiezingen() {
        return indicatieDeelnameEUVerkiezingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumAanleidingAanpassingDeelnameEUVerkiezing() {
        return datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumEindeUitsluitingEUKiesrecht() {
        return datumEindeUitsluitingEUKiesrecht;
    }

    /**
     * Zet Deelname EU verkiezingen? van EU verkiezingen.
     *
     * @param indicatieDeelnameEUVerkiezingen Deelname EU verkiezingen?.
     */
    public void setIndicatieDeelnameEUVerkiezingen(final JaNee indicatieDeelnameEUVerkiezingen) {
        this.indicatieDeelnameEUVerkiezingen = indicatieDeelnameEUVerkiezingen;
    }

    /**
     * Zet Datum aanleiding aanpassing deelname EU verkiezing van EU verkiezingen.
     *
     * @param datumAanleidingAanpassingDeelnameEUVerkiezing Datum aanleiding aanpassing deelname EU verkiezing.
     */
    public void setDatumAanleidingAanpassingDeelnameEUVerkiezing(
            final Datum datumAanleidingAanpassingDeelnameEUVerkiezing)
    {
        this.datumAanleidingAanpassingDeelnameEUVerkiezing = datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    /**
     * Zet Datum einde uitsluiting EU kiesrecht van EU verkiezingen.
     *
     * @param datumEindeUitsluitingEUKiesrecht Datum einde uitsluiting EU kiesrecht.
     */
    public void setDatumEindeUitsluitingEUKiesrecht(final Datum datumEindeUitsluitingEUKiesrecht) {
        this.datumEindeUitsluitingEUKiesrecht = datumEindeUitsluitingEUKiesrecht;
    }

}
