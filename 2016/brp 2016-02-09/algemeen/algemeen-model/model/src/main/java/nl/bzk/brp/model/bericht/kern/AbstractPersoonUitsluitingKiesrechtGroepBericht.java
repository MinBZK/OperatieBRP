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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingKiesrechtGroepBasis;

/**
 * Gegevens over een eventuele uitsluiting van (Nederlandse) verkiezingen
 *
 * Vorm van historie: alleen formeel. Motivatie: weliswaar heeft een materiële tijdslijn betekenis (over welke periode
 * was er uitsluiting, los van het geregistreerd zijn hiervan); echter er is IN KADER VAN DE BRP géén behoefte om deze
 * te kennen: het is voldoende om te weten of er 'nu' sprake is van uitsluiting. Om die reden wordt de materiële
 * tijdslijn onderdrukt, en blijft alleen de formele tijdslijn over. Dit is overigens conform LO GBA 3.x.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonUitsluitingKiesrechtGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep,
        PersoonUitsluitingKiesrechtGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3519;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3322, 3559);
    private JaAttribuut indicatieUitsluitingKiesrecht;
    private DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingKiesrecht;

    /**
     * {@inheritDoc}
     */
    @Override
    public JaAttribuut getIndicatieUitsluitingKiesrecht() {
        return indicatieUitsluitingKiesrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumVoorzienEindeUitsluitingKiesrecht() {
        return datumVoorzienEindeUitsluitingKiesrecht;
    }

    /**
     * Zet Uitsluiting kiesrecht? van Uitsluiting kiesrecht.
     *
     * @param indicatieUitsluitingKiesrecht Uitsluiting kiesrecht?.
     */
    public void setIndicatieUitsluitingKiesrecht(final JaAttribuut indicatieUitsluitingKiesrecht) {
        this.indicatieUitsluitingKiesrecht = indicatieUitsluitingKiesrecht;
    }

    /**
     * Zet Datum voorzien einde uitsluiting kiesrecht van Uitsluiting kiesrecht.
     *
     * @param datumVoorzienEindeUitsluitingKiesrecht Datum voorzien einde uitsluiting kiesrecht.
     */
    public void setDatumVoorzienEindeUitsluitingKiesrecht(final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingKiesrecht) {
        this.datumVoorzienEindeUitsluitingKiesrecht = datumVoorzienEindeUitsluitingKiesrecht;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieUitsluitingKiesrecht != null) {
            attributen.add(indicatieUitsluitingKiesrecht);
        }
        if (datumVoorzienEindeUitsluitingKiesrecht != null) {
            attributen.add(datumVoorzienEindeUitsluitingKiesrecht);
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
