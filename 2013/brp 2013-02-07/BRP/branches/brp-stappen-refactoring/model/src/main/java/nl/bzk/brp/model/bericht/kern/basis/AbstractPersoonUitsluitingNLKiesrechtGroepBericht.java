/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonUitsluitingNLKiesrechtGroepBasis;


/**
 * Gegevens over een eventuele uitsluiting van Nederlandse verkiezingen
 *
 * Vorm van historie: alleen formeel. Motivatie: weliswaar heeft een materi�le tijdslijn betekenis (over welke periode
 * was er uitsluiting, los van het geregistreerd zijn hiervan); echter er is IN KADER VAN DE BRP g��n behoefte om deze
 * te kennen: het is voldoende om te weten of er 'nu' sprake is van uitsluiting. Om die reden wordt de materi�le
 * tijdslijn onderdrukt, en blijft alleen de formele tijdslijn over. Dit is overigens conform LO GBA 3.x.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractPersoonUitsluitingNLKiesrechtGroepBericht extends AbstractGroepBericht implements
        PersoonUitsluitingNLKiesrechtGroepBasis
{

    private Ja    indicatieUitsluitingNLKiesrecht;
    private Datum datumEindeUitsluitingNLKiesrecht;

    /**
     * {@inheritDoc}
     */
    @Override
    public Ja getIndicatieUitsluitingNLKiesrecht() {
        return indicatieUitsluitingNLKiesrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumEindeUitsluitingNLKiesrecht() {
        return datumEindeUitsluitingNLKiesrecht;
    }

    /**
     * Zet Uitsluiting NL kiesrecht? van Uitsluiting NL kiesrecht.
     *
     * @param indicatieUitsluitingNLKiesrecht Uitsluiting NL kiesrecht?.
     */
    public void setIndicatieUitsluitingNLKiesrecht(final Ja indicatieUitsluitingNLKiesrecht) {
        this.indicatieUitsluitingNLKiesrecht = indicatieUitsluitingNLKiesrecht;
    }

    /**
     * Zet Datum einde uitsluiting NL kiesrecht van Uitsluiting NL kiesrecht.
     *
     * @param datumEindeUitsluitingNLKiesrecht Datum einde uitsluiting NL kiesrecht.
     */
    public void setDatumEindeUitsluitingNLKiesrecht(final Datum datumEindeUitsluitingNLKiesrecht) {
        this.datumEindeUitsluitingNLKiesrecht = datumEindeUitsluitingNLKiesrecht;
    }

}
