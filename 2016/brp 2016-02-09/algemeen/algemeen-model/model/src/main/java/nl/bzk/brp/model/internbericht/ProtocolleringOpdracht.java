/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.internbericht;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Historievorm;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;


/**
 * Deze klasse bevat alle gegevens die in de protocollering moeten worden weggeschreven. De inhoud van deze klasse wordt via JMS gecommuniceerd vanaf de
 * module die wil protocolleren naar de protocollering verwerker.
 */
public final class ProtocolleringOpdracht {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final List<SoortDienst> SOORTDIENSTEN_MET_SOORT_SYNCHRONISATIE_VERPLICHT =
        Arrays.asList(
            SoortDienst.ATTENDERING,
            SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE,
            SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING,
            SoortDienst.SYNCHRONISATIE_PERSOON);

    @JsonProperty
    private LeveringModel levering;

    @JsonProperty
    private Set<LeveringPersoonModel> personen;

    /**
     * Catalogus optie van de aanroepende dienst, benodigd voor validatie.
     */
    @JsonProperty
    private SoortDienst soortDienst;

    /**
     * Historievorm van de levering, benodigd voor validatie.
     */
    @JsonProperty
    private Historievorm historievorm;

    /**
     * Default constructor nodig voor JSON de-serialisatie.
     */
    public ProtocolleringOpdracht() {

    }

    /**
     * Constructor met verwachte attributen.
     *
     * @param levering de levering waarover deze opdracht gaat
     * @param personen de lijst met personen voor deze opdracht
     */
    public ProtocolleringOpdracht(final LeveringModel levering, final Set<LeveringPersoonModel> personen) {
        this.levering = levering;
        this.personen = personen;
    }

    public LeveringModel getLevering() {
        return levering;
    }

    public void setLevering(final LeveringModel levering) {
        this.levering = levering;
    }

    public Set<LeveringPersoonModel> getPersonen() {
        return personen;
    }

    public void setPersonen(final Set<LeveringPersoonModel> personen) {
        this.personen = personen;
    }

    /**
     * Valideert het object, zonder SoortDienst. Let op! Deze functie is minder accuraat dan de versie waarbij de SoortDienst beschikbaar is.
     * Gebruik dus altijd de versie met SoortDienst als deze beschikbaar is.
     *
     * @return Boolean true als het object valide is, anders false.
     */
    public boolean isValide() {
        boolean resultaat;

        if (levering == null) {
            resultaat = false;
            LOGGER.debug("Levering dient gevuld te zijn.");

        } else if (personen == null || personen.isEmpty()) {
            resultaat = false;
            LOGGER.debug("Personen dient gevuld te zijn.");

        } else if (levering.getToegangLeveringsautorisatieId() == null) {
            resultaat = false;
            LOGGER.debug("ToegangAbonnementId dient gevuld te zijn.");

        } else if (levering.getDienstId() == null) {
            resultaat = false;
            LOGGER.debug("DienstId dient gevuld te zijn.");

        } else if (levering.getDatumTijdKlaarzettenLevering() == null
            || levering.getDatumTijdKlaarzettenLevering().heeftGeenWaarde())
        {
            resultaat = false;
            LOGGER.debug("DatumTijdKlaarzettenLevering dient gevuld te zijn.");

        } else if (getSoortDienst() == null) {
            resultaat = false;
            LOGGER.debug("Soort dienst gevuld te zijn.");

            // Nog nodig en volledig geimplementeerd?
        } else if ((SOORTDIENSTEN_MET_SOORT_SYNCHRONISATIE_VERPLICHT
            .contains(getSoortDienst())) && (levering.getSoortSynchronisatie() == null || levering.getSoortSynchronisatie().heeftGeenWaarde()))
        {
            resultaat = false;
            LOGGER.debug("Soort synchronisatie dient gevuld te zijn voor deze categorie dienst: {}", getSoortDienst());

        } else {
            switch (soortDienst) {
                case ATTENDERING:
                case MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING:
                    resultaat = isValideAttenderingOfMutatieLeveringDoelbinding();
                    break;
                case GEEF_DETAILS_PERSOON:
                case GEEF_DETAILS_PERSOON_BULK:
                    resultaat = isValideGeefDetailsPersoon(getHistorievorm());
                    break;
                case MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE:
                case PLAATSEN_AFNEMERINDICATIE:
                case VERWIJDEREN_AFNEMERINDICATIE:
                    resultaat = isValideAfnemerindicatie();
                    break;
                case SYNCHRONISATIE_PERSOON:
                    resultaat = isValideSynchronisatiePersoon();
                    break;
                case GEEF_MEDEBEWONERS_VAN_PERSOON:
                    resultaat = isValideGeefMedebewonersVanPersoon();
                    break;
                default:
                    final String foutmelding =
                        "Voor deze catalogusoptie is geen protocollering validatie ingesteld: " + soortDienst;
                    LOGGER.error(foutmelding);
                    throw new IllegalArgumentException(foutmelding);
            }

            if (!resultaat) {
                LOGGER.debug("De protocollering is niet valide voor de catalogusoptie: {}, "
                    + "datum materieel selectie: {}, datum aanvang materiele periode: {}, "
                    + "datum einde materiele periode: {}, datum tijd aanv form periode: {}, "
                    + "datum tijd einde form periode: {}, historievorm: {}", soortDienst,
                    levering.getDatumMaterieelSelectie(), levering.getDatumAanvangMaterielePeriodeResultaat(),
                    levering.getDatumEindeMaterielePeriodeResultaat(),
                    levering.getDatumTijdAanvangFormelePeriodeResultaat(),
                    levering.getDatumTijdEindeFormelePeriodeResultaat(), historievorm);
            }
        }

        return resultaat;
    }

    /**
     * Controleert of protocollering opdracht voor attendering of mutatie levering doelbinding valide is.
     *
     * @return the boolean
     */
    private boolean isValideAttenderingOfMutatieLeveringDoelbinding() {
        return isGevuld(levering.getDatumTijdAanvangFormelePeriodeResultaat())
            && isGevuld(levering.getDatumTijdEindeFormelePeriodeResultaat());
    }

    /**
     * Controleert of protocollering opdracht voor plaatsen en verwijderen afnemerindicatie valide is.
     *
     * @return the boolean
     */
    private boolean isValideAfnemerindicatie() {
        return isGevuld(levering.getDatumTijdAanvangFormelePeriodeResultaat())
            && isGevuld(levering.getDatumTijdEindeFormelePeriodeResultaat());
    }

    /**
     * Controleert of protocollering opdracht voor geef details persoon valide is.
     *
     * @param checkHistorievorm de checkHistorievorm
     * @return true als valide, anders false.
     */
    private boolean isValideGeefDetailsPersoon(final Historievorm checkHistorievorm) {
        boolean isValide;

        if (checkHistorievorm == null || isGevuld(levering.getDatumMaterieelSelectie())) {
            return false;
        }

        switch (checkHistorievorm) {
            case GEEN:
                isValide =
                    isGevuld(levering.getDatumAanvangMaterielePeriodeResultaat())
                        && isGevuld(levering.getDatumEindeMaterielePeriodeResultaat())
                        && isGevuld(levering.getDatumTijdAanvangFormelePeriodeResultaat())
                        && isGevuld(levering.getDatumTijdEindeFormelePeriodeResultaat());
                break;
            case MATERIEEL:
                isValide =
                    isGevuld(levering.getDatumEindeMaterielePeriodeResultaat())
                        && isGevuld(levering.getDatumTijdAanvangFormelePeriodeResultaat())
                        && isGevuld(levering.getDatumTijdEindeFormelePeriodeResultaat());
                break;
            case MATERIEEL_FORMEEL:
                isValide =
                    isGevuld(levering.getDatumEindeMaterielePeriodeResultaat())
                        && isGevuld(levering.getDatumTijdEindeFormelePeriodeResultaat());
                break;
            default:
                final String foutmelding =
                    "Voor protocollering van geef details persoon dient een geldige "
                        + "checkHistorievorm meegegeven te worden: " + checkHistorievorm;
                LOGGER.error(foutmelding);
                throw new IllegalArgumentException(foutmelding);
        }

        return isValide;
    }

    /**
     * Controleert of protocollering opdracht voor geef medebewoners van persoon valide is.
     *
     * @return true als valide, anders false.
     */
    private boolean isValideGeefMedebewonersVanPersoon() {
        return isGevuld(levering.getDatumMaterieelSelectie())
            && isGevuld(levering.getDatumAanvangMaterielePeriodeResultaat())
            && isGevuld(levering.getDatumEindeMaterielePeriodeResultaat())
            && isGevuld(levering.getDatumTijdAanvangFormelePeriodeResultaat())
            && isGevuld(levering.getDatumTijdEindeFormelePeriodeResultaat());
    }

    /**
     * Controleert of protocollering opdracht voor synchronisatie persoon valide is.
     * <p/>
     * Let op: Voor synchronisatie persoon zijn twee varianten. Een op basis van afnemerindicatie en een op basis van mutatielevering op basis van
     * doelbinding. Voor de afnemerindicatie variant dient "Datum aanvang materiële periode resultaat" gevuld te zijn. Let op: deze valideer-methode checkt
     * dit niet, aangezien we niet over de gegevens beschikken welke andere dienst de afnemer in bezit heeft.
     *
     * @return true als valide, anders false.
     */
    private boolean isValideSynchronisatiePersoon() {
        /**
         * Voor synchronisatie persoon zijn twee varianten. Een op basis van afnemerindicatie en een op basis van
         * mutatielevering op basis van doelbinding. Voor de afnemerindicatie variant dient "Datum aanvang materiële
         * periode resultaat" gevuld te zijn. Let op: deze valideer-methode checkt dit niet, aangezien we niet over de
         * gegevens beschikken welke andere dienst de afnemer in bezit heeft.
         */

        return isGevuld(levering.getDatumTijdAanvangFormelePeriodeResultaat())
            && isGevuld(levering.getDatumTijdEindeFormelePeriodeResultaat());
    }

    /**
     * Controleert of datum tijd attribuut gevuld is.
     *
     * @param datumTijdAttribuut het datum tijd attribuut
     * @return true wanneer deze gevuld is, anders false.
     */
    private boolean isGevuld(final DatumTijdAttribuut datumTijdAttribuut) {
        return datumTijdAttribuut != null && datumTijdAttribuut.heeftWaarde();
    }

    /**
     * Controleert of datum attribuut gevuld is.
     *
     * @param datumAttribuut het datum attribuut
     * @return true wanneer deze gevuld is, anders false.
     */
    private boolean isGevuld(final DatumAttribuut datumAttribuut) {
        return datumAttribuut != null && datumAttribuut.heeftWaarde();
    }

    public SoortDienst getSoortDienst() {
        return soortDienst;
    }

    public void setSoortDienst(final SoortDienst soortDienst) {
        this.soortDienst = soortDienst;
    }

    public Historievorm getHistorievorm() {
        return historievorm;
    }

    public void setHistorievorm(final Historievorm historievorm) {
        this.historievorm = historievorm;
    }

}
