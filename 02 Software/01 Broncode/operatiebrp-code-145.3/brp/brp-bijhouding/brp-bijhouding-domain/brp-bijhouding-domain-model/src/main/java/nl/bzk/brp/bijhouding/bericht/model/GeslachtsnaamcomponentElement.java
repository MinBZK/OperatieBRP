/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChild;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * De GeslachtsnaamcomponentElement voor een bijhoudingsbericht.
 */
@XmlElement("geslachtsnaamcomponent")
public final class GeslachtsnaamcomponentElement extends AbstractNaamElement {
    @XmlChild(volgorde = 50)
    private final StringElement stam;

    /**
     * Maakt een GeslachtsnaamcomponentElement object.
     * @param attributen attributen
     * @param predicaatCode predicaatCode
     * @param adellijkeTitelCode adellijkeTitelCode
     * @param voorvoegsel voorvoegsel
     * @param scheidingsteken scheidingsteken
     * @param stam stam
     */
    public GeslachtsnaamcomponentElement(
            final Map<String, String> attributen,
            final StringElement predicaatCode,
            final StringElement adellijkeTitelCode,
            final StringElement voorvoegsel,
            final CharacterElement scheidingsteken,
            final StringElement stam) {
        super(attributen, predicaatCode, adellijkeTitelCode, voorvoegsel, scheidingsteken);
        ValidatieHelper.controleerOpNullWaarde(stam, "stam");
        this.stam = stam;
    }

    /**
     * Geef de waarde van stam.
     * @return stam
     */
    public StringElement getStam() {
        return stam;
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        return VALIDATIE_OK;
    }

    @Override
    protected Regel getRegelVoorNietBestaandVoorvoegselScheidingstekenPaar() {
        return Regel.R2161;
    }

    @Override
    protected Regel getRegelVoorvoegselOfscheidingstekenBeideLeegOfGevuld() {
        return Regel.R2228;
    }

    @Override
    protected Regel getRegelVoorAdellijkeTitelEnPredikaatBeideAanwezig() {
        return Regel.R2163;
    }

    /**
     * vergelijkt dit element met een ander GeslachtsComponentElement.
     * @param ander GeslachtsnaamcomponentElement
     * @return true als voorvoegsel, scheidingsteken en stam gelijk zijn, anders false
     */
    public boolean vergelijkMetAndereGeslachtsNaamComponent(final GeslachtsnaamcomponentElement ander) {
        return NaamGegevens.getInstance(this).isGelijk(NaamGegevens.getInstance(ander));
    }

    /**
     * vergelijkt dit element inhoudelijk met een PersoonGeslachtsnaamcomponentHistorie.
     * @param comp {@link PersoonGeslachtsnaamcomponent}
     * @return true als voorvoegsel, scheidingsteken en stam gelijk zijn, anders false
     */
    public boolean vergelijkMetPersoonsGeslachtsNaamComponent(final PersoonGeslachtsnaamcomponentHistorie comp) {
        return NaamGegevens.getInstance(this).isGelijk(NaamGegevens.getInstance(comp));
    }

    /**
     * Vergelijkt dit element met de gegeven samengesteldenaam entiteit.
     * @param samengesteldeNaam de samengestelde naam entiteit
     * @return true als voorvoegsel, scheidingsteken en stam gelijk zijn, anders false
     */
    public boolean vergelijkMetSamenGesteldeNaam(final PersoonSamengesteldeNaamHistorie samengesteldeNaam) {
        return NaamGegevens.getInstance(this).isGelijk(NaamGegevens.getInstance(samengesteldeNaam));
    }

    /**
     * Vergelijkt dit element met de gegeven BijhoudingGeslachtsNaamComponent.
     *
     * @param geslachtsNaamComponentKind BijhoudingGeslachtsNaamComponent
     * @return true als voorvoegsel, scheidingsteken en stam gelijk zijn, anders false
     */
    public boolean vergelijkMetSamenGesteldeNaam(final BijhoudingGeslachtsNaamComponent geslachtsNaamComponentKind) {
        return NaamGegevens.getInstance(this).isGelijk(NaamGegevens.getInstance(geslachtsNaamComponentKind));
    }

    private static final class NaamGegevens {

        private final String voorvoegsel;
        private final Character scheidingsteken;
        private final String stam;

        private NaamGegevens(final String voorvoegsel, final Character scheidingsteken, final String stam) {
            this.voorvoegsel = voorvoegsel;
            this.scheidingsteken = scheidingsteken;
            this.stam = stam;
        }

        private boolean isGelijk(final NaamGegevens ander) {
            return new EqualsBuilder()
                    .append(voorvoegsel, ander.voorvoegsel)
                    .append(scheidingsteken, ander.scheidingsteken)
                    .append(stam, ander.stam)
                    .isEquals();
        }

        private static NaamGegevens getInstance(final PersoonSamengesteldeNaamHistorie samengesteldeNaam) {
            return new NaamGegevens(samengesteldeNaam.getVoorvoegsel(), samengesteldeNaam.getScheidingsteken(), samengesteldeNaam.getGeslachtsnaamstam());
        }

        private static NaamGegevens getInstance(final PersoonGeslachtsnaamcomponentHistorie geslachtsnaamcomponent) {
            return new NaamGegevens(geslachtsnaamcomponent.getVoorvoegsel(), geslachtsnaamcomponent.getScheidingsteken(), geslachtsnaamcomponent.getStam());
        }

        private static NaamGegevens getInstance(final GeslachtsnaamcomponentElement geslachtsnaamcomponentElement) {
            return new NaamGegevens(BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getVoorvoegsel()),
                    BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getScheidingsteken()),
                    BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getStam()));
        }

        private static NaamGegevens getInstance(final BijhoudingGeslachtsNaamComponent geslachtsNaamComponentKind) {
            return new NaamGegevens(geslachtsNaamComponentKind.getVoorvoegsel(),
                    geslachtsNaamComponentKind.getScheidingsteken(),
                    geslachtsNaamComponentKind.getStam());
        }
    }
}
