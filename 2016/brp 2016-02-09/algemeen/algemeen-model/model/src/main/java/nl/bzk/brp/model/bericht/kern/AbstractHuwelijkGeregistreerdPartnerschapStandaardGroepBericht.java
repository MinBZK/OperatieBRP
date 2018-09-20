/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBasis;

/**
 * Gegevens over de aanvang en einde van een Relatie
 *
 * 1. Niet van toepassing op de familierechtelijke betrekking. Zie ook overkoepelend memo over Relatie & Betrokkenheid.
 * Het lijkt erop dat de attributen waarmee de 'plaats' (woonplaats, gemeente, land etc etc) wordt aangeduid, alleen van
 * belang is voor huwelijk en geregistreerd partnerschap. Opnemen van de velden voor andere relaties is alleen reden
 * voor verwarring. We kiezen er daarom voor om 'plaats' velden alleen te vullen voor huwelijk en geregistreerd
 * partnerschap. 2. Vorm van historie: alleen formeel. Motivatie: alle (materi�le) tijdsaspecten zijn uitgemodelleerd
 * (met datum aanvang en datum einde), waardoor dus geen (extra) materi�le historie van toepassing is. Verder 'herleeft'
 * een Huwelijk niet, en wordt het ene Huwelijk niet door een ander Huwelijk be�indigd. Met andere woorden: twee
 * personen die eerst met elkaar Huwen, vervolgens scheiden, en vervolgens weer Huwen, hebben TWEE (verschillende)
 * exemplaren van Relatie: het eerste Huwelijk, en het tweede. Door deze zienswijze (die volgt uit de definitie van
 * Relatie) is er DUS geen sprake van materi�le historie, en volstaat dus de formele historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractHuwelijkGeregistreerdPartnerschapStandaardGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep,
        HuwelijkGeregistreerdPartnerschapStandaardGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3599;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(
        3754,
        3755,
        3756,
        3757,
        3759,
        3758,
        3760,
        3207,
        3762,
        3763,
        3764,
        3765,
        3767,
        3766,
        3768);
    private DatumEvtDeelsOnbekendAttribuut datumAanvang;
    private String gemeenteAanvangCode;
    private GemeenteAttribuut gemeenteAanvang;
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamAanvang;
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang;
    private BuitenlandseRegioAttribuut buitenlandseRegioAanvang;
    private LocatieomschrijvingAttribuut omschrijvingLocatieAanvang;
    private String landGebiedAanvangCode;
    private LandGebiedAttribuut landGebiedAanvang;
    private String redenEindeCode;
    private RedenEindeRelatieAttribuut redenEinde;
    private DatumEvtDeelsOnbekendAttribuut datumEinde;
    private String gemeenteEindeCode;
    private GemeenteAttribuut gemeenteEinde;
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamEinde;
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde;
    private BuitenlandseRegioAttribuut buitenlandseRegioEinde;
    private LocatieomschrijvingAttribuut omschrijvingLocatieEinde;
    private String landGebiedEindeCode;
    private LandGebiedAttribuut landGebiedEinde;

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Retourneert Gemeente aanvang van Standaard.
     *
     * @return Gemeente aanvang.
     */
    public String getGemeenteAanvangCode() {
        return gemeenteAanvangCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeenteAttribuut getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaamAanvang() {
        return woonplaatsnaamAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegioAttribuut getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Retourneert Land/gebied aanvang van Standaard.
     *
     * @return Land/gebied aanvang.
     */
    public String getLandGebiedAanvangCode() {
        return landGebiedAanvangCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebiedAanvang() {
        return landGebiedAanvang;
    }

    /**
     * Retourneert Reden einde van Standaard.
     *
     * @return Reden einde.
     */
    public String getRedenEindeCode() {
        return redenEindeCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenEindeRelatieAttribuut getRedenEinde() {
        return redenEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Gemeente einde van Standaard.
     *
     * @return Gemeente einde.
     */
    public String getGemeenteEindeCode() {
        return gemeenteEindeCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeenteAttribuut getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaamEinde() {
        return woonplaatsnaamEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegioAttribuut getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Retourneert Land/gebied einde van Standaard.
     *
     * @return Land/gebied einde.
     */
    public String getLandGebiedEindeCode() {
        return landGebiedEindeCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebiedEinde() {
        return landGebiedEinde;
    }

    /**
     * Zet Datum aanvang van Standaard.
     *
     * @param datumAanvang Datum aanvang.
     */
    public void setDatumAanvang(final DatumEvtDeelsOnbekendAttribuut datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    /**
     * Zet Gemeente aanvang van Standaard.
     *
     * @param gemeenteAanvangCode Gemeente aanvang.
     */
    public void setGemeenteAanvangCode(final String gemeenteAanvangCode) {
        this.gemeenteAanvangCode = gemeenteAanvangCode;
    }

    /**
     * Zet Gemeente aanvang van Standaard.
     *
     * @param gemeenteAanvang Gemeente aanvang.
     */
    public void setGemeenteAanvang(final GemeenteAttribuut gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    /**
     * Zet Woonplaatsnaam aanvang van Standaard.
     *
     * @param woonplaatsnaamAanvang Woonplaatsnaam aanvang.
     */
    public void setWoonplaatsnaamAanvang(final NaamEnumeratiewaardeAttribuut woonplaatsnaamAanvang) {
        this.woonplaatsnaamAanvang = woonplaatsnaamAanvang;
    }

    /**
     * Zet Buitenlandse plaats aanvang van Standaard.
     *
     * @param buitenlandsePlaatsAanvang Buitenlandse plaats aanvang.
     */
    public void setBuitenlandsePlaatsAanvang(final BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang) {
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    /**
     * Zet Buitenlandse regio aanvang van Standaard.
     *
     * @param buitenlandseRegioAanvang Buitenlandse regio aanvang.
     */
    public void setBuitenlandseRegioAanvang(final BuitenlandseRegioAttribuut buitenlandseRegioAanvang) {
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    /**
     * Zet Omschrijving locatie aanvang van Standaard.
     *
     * @param omschrijvingLocatieAanvang Omschrijving locatie aanvang.
     */
    public void setOmschrijvingLocatieAanvang(final LocatieomschrijvingAttribuut omschrijvingLocatieAanvang) {
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    /**
     * Zet Land/gebied aanvang van Standaard.
     *
     * @param landGebiedAanvangCode Land/gebied aanvang.
     */
    public void setLandGebiedAanvangCode(final String landGebiedAanvangCode) {
        this.landGebiedAanvangCode = landGebiedAanvangCode;
    }

    /**
     * Zet Land/gebied aanvang van Standaard.
     *
     * @param landGebiedAanvang Land/gebied aanvang.
     */
    public void setLandGebiedAanvang(final LandGebiedAttribuut landGebiedAanvang) {
        this.landGebiedAanvang = landGebiedAanvang;
    }

    /**
     * Zet Reden einde van Standaard.
     *
     * @param redenEindeCode Reden einde.
     */
    public void setRedenEindeCode(final String redenEindeCode) {
        this.redenEindeCode = redenEindeCode;
    }

    /**
     * Zet Reden einde van Standaard.
     *
     * @param redenEinde Reden einde.
     */
    public void setRedenEinde(final RedenEindeRelatieAttribuut redenEinde) {
        this.redenEinde = redenEinde;
    }

    /**
     * Zet Datum einde van Standaard.
     *
     * @param datumEinde Datum einde.
     */
    public void setDatumEinde(final DatumEvtDeelsOnbekendAttribuut datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Zet Gemeente einde van Standaard.
     *
     * @param gemeenteEindeCode Gemeente einde.
     */
    public void setGemeenteEindeCode(final String gemeenteEindeCode) {
        this.gemeenteEindeCode = gemeenteEindeCode;
    }

    /**
     * Zet Gemeente einde van Standaard.
     *
     * @param gemeenteEinde Gemeente einde.
     */
    public void setGemeenteEinde(final GemeenteAttribuut gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    /**
     * Zet Woonplaatsnaam einde van Standaard.
     *
     * @param woonplaatsnaamEinde Woonplaatsnaam einde.
     */
    public void setWoonplaatsnaamEinde(final NaamEnumeratiewaardeAttribuut woonplaatsnaamEinde) {
        this.woonplaatsnaamEinde = woonplaatsnaamEinde;
    }

    /**
     * Zet Buitenlandse plaats einde van Standaard.
     *
     * @param buitenlandsePlaatsEinde Buitenlandse plaats einde.
     */
    public void setBuitenlandsePlaatsEinde(final BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde) {
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    /**
     * Zet Buitenlandse regio einde van Standaard.
     *
     * @param buitenlandseRegioEinde Buitenlandse regio einde.
     */
    public void setBuitenlandseRegioEinde(final BuitenlandseRegioAttribuut buitenlandseRegioEinde) {
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }

    /**
     * Zet Omschrijving locatie einde van Standaard.
     *
     * @param omschrijvingLocatieEinde Omschrijving locatie einde.
     */
    public void setOmschrijvingLocatieEinde(final LocatieomschrijvingAttribuut omschrijvingLocatieEinde) {
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    /**
     * Zet Land/gebied einde van Standaard.
     *
     * @param landGebiedEindeCode Land/gebied einde.
     */
    public void setLandGebiedEindeCode(final String landGebiedEindeCode) {
        this.landGebiedEindeCode = landGebiedEindeCode;
    }

    /**
     * Zet Land/gebied einde van Standaard.
     *
     * @param landGebiedEinde Land/gebied einde.
     */
    public void setLandGebiedEinde(final LandGebiedAttribuut landGebiedEinde) {
        this.landGebiedEinde = landGebiedEinde;
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
