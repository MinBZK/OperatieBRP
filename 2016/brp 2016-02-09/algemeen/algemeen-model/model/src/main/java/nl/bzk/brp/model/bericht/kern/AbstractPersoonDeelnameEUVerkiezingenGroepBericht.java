/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonDeelnameEUVerkiezingenGroepBasis;

/**
 * Gegevens over de eventuele deelname aan EU verkiezingen.
 *
 * EU-burgers die in een andere lidstaat wonen hebben het recht om daar te stemmen. EU burgers van andere lidstaten, die
 * in Nederland wonen, en gebruik willen maken van hun stemrecht, doen daartoe een verzoek.
 *
 * Vorm van historie: conform 'NL verkiezingen' zou een materiële tijdslijn wel betekenis hebben, maar ontbreekt de
 * business case om deze vast te leggen. Om die reden wordt de materiële tijdslijn onderdrukt, en is in de BRP alleen de
 * formele tijdslijn vastgelegd. Zie ook NL verkiezingen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonDeelnameEUVerkiezingenGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep,
        PersoonDeelnameEUVerkiezingenGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3901;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3320, 3562, 3564);
    private JaNeeAttribuut indicatieDeelnameEUVerkiezingen;
    private DatumEvtDeelsOnbekendAttribuut datumAanleidingAanpassingDeelnameEUVerkiezingen;
    private DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingEUVerkiezingen;

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieDeelnameEUVerkiezingen() {
        return indicatieDeelnameEUVerkiezingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanleidingAanpassingDeelnameEUVerkiezingen() {
        return datumAanleidingAanpassingDeelnameEUVerkiezingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumVoorzienEindeUitsluitingEUVerkiezingen() {
        return datumVoorzienEindeUitsluitingEUVerkiezingen;
    }

    /**
     * Zet Deelname EU verkiezingen? van Deelname EU verkiezingen.
     *
     * @param indicatieDeelnameEUVerkiezingen Deelname EU verkiezingen?.
     */
    public void setIndicatieDeelnameEUVerkiezingen(final JaNeeAttribuut indicatieDeelnameEUVerkiezingen) {
        this.indicatieDeelnameEUVerkiezingen = indicatieDeelnameEUVerkiezingen;
    }

    /**
     * Zet Datum aanleiding aanpassing deelname EU verkiezingen van Deelname EU verkiezingen.
     *
     * @param datumAanleidingAanpassingDeelnameEUVerkiezingen Datum aanleiding aanpassing deelname EU verkiezingen.
     */
    public void setDatumAanleidingAanpassingDeelnameEUVerkiezingen(final DatumEvtDeelsOnbekendAttribuut datumAanleidingAanpassingDeelnameEUVerkiezingen) {
        this.datumAanleidingAanpassingDeelnameEUVerkiezingen = datumAanleidingAanpassingDeelnameEUVerkiezingen;
    }

    /**
     * Zet Datum voorzien einde uitsluiting EU verkiezingen van Deelname EU verkiezingen.
     *
     * @param datumVoorzienEindeUitsluitingEUVerkiezingen Datum voorzien einde uitsluiting EU verkiezingen.
     */
    public void setDatumVoorzienEindeUitsluitingEUVerkiezingen(final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingEUVerkiezingen) {
        this.datumVoorzienEindeUitsluitingEUVerkiezingen = datumVoorzienEindeUitsluitingEUVerkiezingen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieDeelnameEUVerkiezingen != null) {
            attributen.add(indicatieDeelnameEUVerkiezingen);
        }
        if (datumAanleidingAanpassingDeelnameEUVerkiezingen != null) {
            attributen.add(datumAanleidingAanpassingDeelnameEUVerkiezingen);
        }
        if (datumVoorzienEindeUitsluitingEUVerkiezingen != null) {
            attributen.add(datumVoorzienEindeUitsluitingEUVerkiezingen);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}
