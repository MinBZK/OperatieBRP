/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingKiesrechtGroep;


/**
 * Gegevens over een eventuele uitsluiting van (Nederlandse) verkiezingen
 * <p/>
 * Vorm van historie: alleen formeel. Motivatie: weliswaar heeft een materi�le tijdslijn betekenis (over welke periode was er uitsluiting, los van het
 * geregistreerd zijn hiervan); echter er is IN KADER VAN DE BRP g��n behoefte om deze te kennen: het is voldoende om te weten of er 'nu' sprake is van
 * uitsluiting. Om die reden wordt de materi�le tijdslijn onderdrukt, en blijft alleen de formele tijdslijn over. Dit is overigens conform LO GBA 3.x.
 */
@Embeddable
public class PersoonUitsluitingKiesrechtGroepModel extends AbstractPersoonUitsluitingKiesrechtGroepModel implements
    PersoonUitsluitingKiesrechtGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonUitsluitingKiesrechtGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieUitsluitingKiesrecht indicatieUitsluitingKiesrecht van Uitsluiting kiesrecht.
     * @param datumVoorzienEindeUitsluitingKiesrecht
     *                                      datumVoorzienEindeUitsluitingKiesrecht van Uitsluiting kiesrecht.
     */
    public PersoonUitsluitingKiesrechtGroepModel(final JaAttribuut indicatieUitsluitingKiesrecht,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingKiesrecht)
    {
        super(indicatieUitsluitingKiesrecht, datumVoorzienEindeUitsluitingKiesrecht);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonUitsluitingKiesrechtGroep
     *         te kopieren groep.
     */
    public PersoonUitsluitingKiesrechtGroepModel(
        final PersoonUitsluitingKiesrechtGroep persoonUitsluitingKiesrechtGroep)
    {
        super(persoonUitsluitingKiesrechtGroep);
    }

}
