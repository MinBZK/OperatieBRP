/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.verconv;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3FoutcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3VerwerkingsmeldingAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.verconv.LO3BerichtConversieGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractLO3BerichtConversieGroepModel implements LO3BerichtConversieGroepBasis {

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsConversie"))
    @JsonProperty
    private DatumTijdAttribuut tijdstipConversie;

    @Embedded
    @AttributeOverride(name = LO3FoutcodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Foutcode"))
    @JsonProperty
    private LO3FoutcodeAttribuut foutcode;

    @Embedded
    @AttributeOverride(name = LO3VerwerkingsmeldingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Verwerkingsmelding"))
    @JsonProperty
    private LO3VerwerkingsmeldingAttribuut verwerkingsmelding;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractLO3BerichtConversieGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param tijdstipConversie tijdstipConversie van Conversie.
     * @param foutcode foutcode van Conversie.
     * @param verwerkingsmelding verwerkingsmelding van Conversie.
     */
    public AbstractLO3BerichtConversieGroepModel(
        final DatumTijdAttribuut tijdstipConversie,
        final LO3FoutcodeAttribuut foutcode,
        final LO3VerwerkingsmeldingAttribuut verwerkingsmelding)
    {
        this.tijdstipConversie = tijdstipConversie;
        this.foutcode = foutcode;
        this.verwerkingsmelding = verwerkingsmelding;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getTijdstipConversie() {
        return tijdstipConversie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3FoutcodeAttribuut getFoutcode() {
        return foutcode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3VerwerkingsmeldingAttribuut getVerwerkingsmelding() {
        return verwerkingsmelding;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (tijdstipConversie != null) {
            attributen.add(tijdstipConversie);
        }
        if (foutcode != null) {
            attributen.add(foutcode);
        }
        if (verwerkingsmelding != null) {
            attributen.add(verwerkingsmelding);
        }
        return attributen;
    }

}
