/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De basis actie voor het registreren van wijzigingen op een persoon.
 */
public abstract class AbstractPersoonWijzigingActieElement extends AbstractActieElement {

    private static final int LEEFTIJD_18 = 18;
    private final PersoonGegevensElement persoon;

    /**
     * Maakt een AbstractPersoonWijzigingActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon de persoon
     */
    public AbstractPersoonWijzigingActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
        ValidatieHelper.controleerOpNullWaarde(persoon, "persoon");
        final String objectSleutel = persoon.getObjectSleutel();
        final String referentieId = persoon.getReferentieId();

        if ((objectSleutel != null) == (referentieId != null)) {
            throw new IllegalStateException("Objectsleutel of referentieId moet gevuld zijn");
        }

        this.persoon = persoon;
    }

    /**
     * Geef de persoonElement terug
     * @return persoon sleutel
     */
    public final PersoonGegevensElement getPersoon() {
        return persoon;
    }

    @Override
    public final List<BijhoudingPersoon> getHoofdPersonen() {
        return Collections.singletonList(getPersoon().getPersoonEntiteit());
    }

    @Override
    public final List<PersoonElement> getPersoonElementen() {
        return Collections.singletonList(getPersoon());
    }

    /**
     * geeft aan of een een persoon minderjarig is.
     * waar indien persoon 18 of ouder is, dan wel betrokken is/was in een huwelijk/gp.
     * @param bijhoudingPersoon persoon
     * @return true or false
     */
    public boolean isPersoonMinderJarig(Persoon bijhoudingPersoon) {
        int leeftijd = bijhoudingPersoon.bepaalLeeftijd(DatumUtil.vandaag());
        if (leeftijd < LEEFTIJD_18) {
            for (Betrokkenheid partner : bijhoudingPersoon.getActuelePartners()) {
                RelatieHistorie actueel = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(partner.getRelatie().getRelatieHistorieSet());
                if (actueel.getDatumAanvang() <= getPeilDatum().getWaarde()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * geeft aan of de persoon op dit moment ouders heeft.
     * @param persoon persoon
     * @return true als er ouders zijn
     */
    public boolean hasActueelOuderschap(final BijhoudingPersoon persoon) {
        boolean hasActueelOuderschap = false;
        Set<Betrokkenheid> actueleOuderBetrokkeneheden = persoon.getActueleOuders();
        for (Betrokkenheid ouderBetrokkenheid : actueleOuderBetrokkeneheden) {
            if (FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(ouderBetrokkenheid.getBetrokkenheidOuderHistorieSet()) != null) {
                hasActueelOuderschap = true;
            }
        }
        return hasActueelOuderschap;
    }

    /**
     * valideerd of DAG na de dag van het actueel voorkomen van groep Bijhouding ligt.
     * Deze validatie is noodzakelijk voor:
     * - registratie overlijden
     * -- registratie adres Verhuizing Intergemeentelijk
     * - registratie adres Vestiging niet ingezetene
     * -- registratie adres Wijziging gemeente infra
     * -- registratie adres GBA - verhuizing intergemeentelijk GBA naar BRP
     * -- registratie migratie verhuizing buitenland
     * - registratie bijhouding Wijzig gemeente infrastructureel bij overledene
     * @param meldingen lijst met meldingen waaraan eventueel melding wordt toegevoegd
     */
    @Bedrijfsregel(Regel.R2672)
    public void controleerAanvangGeldigheidTegenBijhouding(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon persoonEntiteit = getPersoon().getPersoonEntiteit();
        if (r2672Uitvoeren() && persoonEntiteit != null
                && !persoonEntiteit.isDatumNaActueleBijhouding(getDatumAanvangGeldigheid())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2672, this));
        }
    }

    boolean r2672Uitvoeren() {
        switch (getVerzoekBericht().getAdministratieveHandeling().getSoort()) {
            case OVERLIJDEN:
            case WIJZIGING_GEMEENTE_INFRASTRUCTUREEL_OVERLIJDEN:
            case REGISTRATIE_VESTIGING_NIET_INGEZETENE:
            case VERHUIZING_INTERGEMEENTELIJK:
            case GBA_VERHUIZING_INTERGEMEENTELIJK_GBA_NAAR_BRP:
            case VERHUIZING_NAAR_BUITENLAND:
            case WIJZIGING_GEMEENTE_INFRASTRUCTUREEL:
                return true;
            default:
                return false;
        }
    }
}
