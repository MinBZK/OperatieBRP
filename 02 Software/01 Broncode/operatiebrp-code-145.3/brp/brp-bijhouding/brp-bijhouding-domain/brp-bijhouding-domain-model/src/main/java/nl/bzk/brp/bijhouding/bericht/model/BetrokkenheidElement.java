/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementen;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Een betrokkenheid uit het bijhoudingsbericht.
 */
@XmlElementen(enumType = BetrokkenheidElementSoort.class, methode = "getSoort")
public final class BetrokkenheidElement extends AbstractBmrGroepReferentie implements BmrEntiteit<BijhoudingBetrokkenheid> {

    private final BetrokkenheidElementSoort soort;
    private final PersoonGegevensElement persoon;
    private OuderschapElement ouderschap;
    private final FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekking;
    private final RelatieElement relatieElement;

    /**
     * Maakt een BetrokkenheidElement object.
     * @param basisAttribuutGroep de basis attribuutgroep, mag niet null zijn
     * @param soort soort, mag niet null zijn
     * @param persoon niet-ingeschreven of onbekend persoon
     * @param ouderschap ouderschap gegevens
     * @param familierechtelijkeBetrekking de familie rechtelijke betrekking
     * @param relatieElement het huwelijk element
     */
    public BetrokkenheidElement(final Map<String, String> basisAttribuutGroep, final BetrokkenheidElementSoort soort, final PersoonGegevensElement persoon,
                                final OuderschapElement ouderschap,
                                final FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekking,
                                final RelatieElement relatieElement) {
        super(basisAttribuutGroep);
        ValidatieHelper.controleerOpNullWaarde(soort, "soort");
        this.soort = soort;
        this.persoon = persoon;
        this.ouderschap = ouderschap;
        this.familierechtelijkeBetrekking = familierechtelijkeBetrekking;
        if (this.familierechtelijkeBetrekking != null) {
            familierechtelijkeBetrekking.setBezienVanuit(this);
        }
        this.relatieElement = relatieElement;
    }

    /**
     * Geef de waarde van soort.
     * @return soort
     */
    public BetrokkenheidElementSoort getSoort() {
        return getElement().soort;
    }

    /**
     * Geef de waarde van persoon.
     * @return persoon
     */
    public PersoonGegevensElement getPersoon() {
        return getElement().persoon;
    }

    /**
     * Geeft de waarde van ouderschap.
     * @return ouderschap
     */
    public OuderschapElement getOuderschap() {
        return getElement().ouderschap;
    }

    /**
     * geeft familierechtelijkebetrekking
     * @return familierechtelijkeBetrekking
     */
    public FamilierechtelijkeBetrekkingElement getFamilierechtelijkeBetrekking() {
        return familierechtelijkeBetrekking != null ? familierechtelijkeBetrekking : getElement().familierechtelijkeBetrekking;
    }

    public RelatieElement getRelatieElement() {
        return getElement().relatieElement;
    }

    @Override
    public boolean inObjectSleutelIndex() {
        return true;
    }

    @Override
    public Class<BijhoudingBetrokkenheid> getEntiteitType() {
        return BijhoudingBetrokkenheid.class;
    }

    @Override
    @Bedrijfsregel(Regel.R2617)
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        if (getReferentie() == null) {
            controleerOuwkigIsBekend(meldingen);
        }
        return meldingen;
    }

    private void controleerOuwkigIsBekend(final List<MeldingElement> meldingen) {
        if (ouderschap != null && ouderschap.getIndicatieOuderUitWieKindIsGeboren().getWaarde() && persoon == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2617, this));
        }
    }

    /**
     * Maakt een nieuwe Betrokkenheid entiteit o.b.v. het bijhoudingsbericht.
     * @param relatie de relatie
     * @param actie de actie
     * @param datumAanvangGeldigheid dat datum aanvang
     */
    void maakBetrokkenheidEntiteit(final BijhoudingRelatie relatie, final BRPActie actie, final int datumAanvangGeldigheid) {
        final BijhoudingBetrokkenheid result =
                BijhoudingBetrokkenheid.decorate(new Betrokkenheid(SoortBetrokkenheid.valueOf(getSoort().toString()), relatie.getDelegate()));
        result.voegBetrokkenheidHistorieToe(actie);
        if (getPersoon() != null) {
            result.voegPersoonToe(getPersoon(), actie, datumAanvangGeldigheid);
        }
        if (getOuderschap() != null) {
            if(Objects.equals(getVerzoekBericht().getAdministratieveHandeling().getSoort(), AdministratieveHandelingElementSoort.ERKENNING)) {
                result.voegOuderschapToe(null, actie, datumAanvangGeldigheid);
            }else{
                    result.voegOuderschapToe(getOuderschap(), actie, datumAanvangGeldigheid);
                }

        }
    }

    /**
     * Geef de betrokkenheid entiteit die hoort bij de objectsleutel van dit betrokkenheid element.
     * @return de betrokkenheid entiteit
     */
    BijhoudingBetrokkenheid getBetrokkenheidEntiteit() {
        final BijhoudingBetrokkenheid result;
        if (getReferentieId() != null) {
            result = getReferentie().getBetrokkenheidEntiteit();
        } else if (getObjectSleutel() != null) {
            result = getVerzoekBericht().getEntiteitVoorObjectSleutel(getEntiteitType(), getObjectSleutel());
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Maakt een betrokkenheid element.
     * @param soort soort, mag niet null zijn
     * @param persoon niet-ingeschreven of onbekend persoon
     * @param ouderschap ouderschap gegevens
     * @return een nieuw betrokkenheid element
     */
    public static BetrokkenheidElement getInstance(final BetrokkenheidElementSoort soort, final PersoonGegevensElement persoon,
                                                   final OuderschapElement ouderschap) {
        return new BetrokkenheidElement(new AttributenBuilder().objecttype("Betrokkenheid").build(), soort, persoon, ouderschap, null, null);
    }

    /**
     * geeft aan of deze betrokkenheid een persoonElement bevat welke op zijn beurt een persoonEntiteit bevat.
     * @return true indien ja
     */
    public boolean heeftPersoonEntiteit() {
        return getPersoon() != null && getPersoon().heeftPersoonEntiteit();
    }

    /**
     * Maakt van deze betrokkenheid een OUWKIG.
     */
    void maakOuderOuwkig() {
        vulOuderschap(true);
    }

    private void vulOuderschap(final Boolean isOuwkig) {
        if (getElement().ouderschap != null) {
            throw new IllegalStateException("BetrokkenheidElement heeft al een OuderschapElement");
        }
        getElement().ouderschap = OuderschapElement.getInstance(isOuwkig);
    }

    /**
     * Maakt van deze betrokkenheid een NOUWKIG.
     * indien het een Erkenning AH betrefft zetten we de waarde ouderouderschap op null.
     */
    void maakOuderNouwkig() {
        vulOuderschap(false);
    }

    @Override
    public BetrokkenheidElement getReferentie() {
        return (BetrokkenheidElement) getGroep();
    }

    @Override
    public boolean verwijstNaarBestaandEnJuisteType() {
        return getReferentieId() == null || (getGroep() instanceof BetrokkenheidElement && getSoort().equals(((BetrokkenheidElement) getGroep()).getSoort()));
    }

    private BetrokkenheidElement getElement() {
        return getReferentie() != null ? getReferentie() : this;
    }
}
