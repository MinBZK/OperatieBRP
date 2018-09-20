/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingKiesrechtGroep;
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
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonUitsluitingKiesrechtGroepModel implements PersoonUitsluitingKiesrechtGroepBasis {

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndUitslKiesr"))
    @JsonProperty
    private JaAttribuut indicatieUitsluitingKiesrecht;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatVoorzEindeUitslKiesr"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingKiesrecht;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonUitsluitingKiesrechtGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieUitsluitingKiesrecht indicatieUitsluitingKiesrecht van Uitsluiting kiesrecht.
     * @param datumVoorzienEindeUitsluitingKiesrecht datumVoorzienEindeUitsluitingKiesrecht van Uitsluiting kiesrecht.
     */
    public AbstractPersoonUitsluitingKiesrechtGroepModel(
        final JaAttribuut indicatieUitsluitingKiesrecht,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingKiesrecht)
    {
        this.indicatieUitsluitingKiesrecht = indicatieUitsluitingKiesrecht;
        this.datumVoorzienEindeUitsluitingKiesrecht = datumVoorzienEindeUitsluitingKiesrecht;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonUitsluitingKiesrechtGroep te kopieren groep.
     */
    public AbstractPersoonUitsluitingKiesrechtGroepModel(final PersoonUitsluitingKiesrechtGroep persoonUitsluitingKiesrechtGroep) {
        this.indicatieUitsluitingKiesrecht = persoonUitsluitingKiesrechtGroep.getIndicatieUitsluitingKiesrecht();
        this.datumVoorzienEindeUitsluitingKiesrecht = persoonUitsluitingKiesrechtGroep.getDatumVoorzienEindeUitsluitingKiesrecht();

    }

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

}
