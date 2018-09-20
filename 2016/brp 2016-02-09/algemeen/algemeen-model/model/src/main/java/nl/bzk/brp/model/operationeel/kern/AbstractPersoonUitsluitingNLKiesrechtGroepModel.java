/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingNLKiesrechtGroepBasis;


/**
 * Gegevens over een eventuele uitsluiting van Nederlandse verkiezingen
 * <p/>
 * Vorm van historie: alleen formeel. Motivatie: weliswaar heeft een materi�le tijdslijn betekenis (over welke periode was er uitsluiting, los van het
 * geregistreerd zijn hiervan); echter er is IN KADER VAN DE BRP g��n behoefte om deze te kennen: het is voldoende om te weten of er 'nu' sprake is van
 * uitsluiting. Om die reden wordt de materi�le tijdslijn onderdrukt, en blijft alleen de formele tijdslijn over. Dit is overigens conform LO GBA 3.x.
 */
@MappedSuperclass
public abstract class AbstractPersoonUitsluitingNLKiesrechtGroepModel implements
    PersoonUitsluitingNLKiesrechtGroepBasis
{

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndUitslNLKiesr"))
    @JsonProperty
    private JaAttribuut indicatieUitsluitingNLKiesrecht;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeUitslNLKiesr"))
    @JsonProperty
    private DatumAttribuut datumEindeUitsluitingNLKiesrecht;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected AbstractPersoonUitsluitingNLKiesrechtGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieUitsluitingNLKiesrecht
     *         indicatieUitsluitingNLKiesrecht van Uitsluiting NL kiesrecht.
     * @param datumEindeUitsluitingNLKiesrecht
     *         datumEindeUitsluitingNLKiesrecht van Uitsluiting NL kiesrecht.
     */
    public AbstractPersoonUitsluitingNLKiesrechtGroepModel(final JaAttribuut indicatieUitsluitingNLKiesrecht,
        final DatumAttribuut datumEindeUitsluitingNLKiesrecht)
    {
        this.indicatieUitsluitingNLKiesrecht = indicatieUitsluitingNLKiesrecht;
        this.datumEindeUitsluitingNLKiesrecht = datumEindeUitsluitingNLKiesrecht;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonUitsluitingNLKiesrechtGroep
     *         te kopieren groep.
     */
    public AbstractPersoonUitsluitingNLKiesrechtGroepModel(
        final PersoonUitsluitingNLKiesrechtGroep persoonUitsluitingNLKiesrechtGroep)
    {
        this.indicatieUitsluitingNLKiesrecht = persoonUitsluitingNLKiesrechtGroep.getIndicatieUitsluitingNLKiesrecht();
        this.datumEindeUitsluitingNLKiesrecht =
            persoonUitsluitingNLKiesrechtGroep.getDatumEindeUitsluitingNLKiesrecht();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaAttribuut getIndicatieUitsluitingNLKiesrecht() {
        return indicatieUitsluitingNLKiesrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumAttribuut getDatumEindeUitsluitingNLKiesrecht() {
        return datumEindeUitsluitingNLKiesrecht;
    }

}
