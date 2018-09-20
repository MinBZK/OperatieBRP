/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
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
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroepBasis;

/**
 * 1. De vorm van historie is conform bij geboorte: ondanks dat er een materieel tijdsaspect is (datum overlijden) is er
 * géén sprake van een 'materieel historiepatroon'. Immers, het is absurd om te stellen dat iemand vorig jaar overleden
 * is in plaats X en dit jaar in plaats Y.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonOverlijdenGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep, PersoonOverlijdenGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 3515;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3546, 3551, 3544, 3552, 3556, 3555, 3558);
    private DatumEvtDeelsOnbekendAttribuut datumOverlijden;
    private String gemeenteOverlijdenCode;
    private GemeenteAttribuut gemeenteOverlijden;
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamOverlijden;
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsOverlijden;
    private BuitenlandseRegioAttribuut buitenlandseRegioOverlijden;
    private LocatieomschrijvingAttribuut omschrijvingLocatieOverlijden;
    private String landGebiedOverlijdenCode;
    private LandGebiedAttribuut landGebiedOverlijden;

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * Retourneert Gemeente overlijden van Overlijden.
     *
     * @return Gemeente overlijden.
     */
    public String getGemeenteOverlijdenCode() {
        return gemeenteOverlijdenCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeenteAttribuut getGemeenteOverlijden() {
        return gemeenteOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaamOverlijden() {
        return woonplaatsnaamOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegioAttribuut getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    /**
     * Retourneert Land/gebied overlijden van Overlijden.
     *
     * @return Land/gebied overlijden.
     */
    public String getLandGebiedOverlijdenCode() {
        return landGebiedOverlijdenCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebiedOverlijden() {
        return landGebiedOverlijden;
    }

    /**
     * Zet Datum overlijden van Overlijden.
     *
     * @param datumOverlijden Datum overlijden.
     */
    public void setDatumOverlijden(final DatumEvtDeelsOnbekendAttribuut datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    /**
     * Zet Gemeente overlijden van Overlijden.
     *
     * @param gemeenteOverlijdenCode Gemeente overlijden.
     */
    public void setGemeenteOverlijdenCode(final String gemeenteOverlijdenCode) {
        this.gemeenteOverlijdenCode = gemeenteOverlijdenCode;
    }

    /**
     * Zet Gemeente overlijden van Overlijden.
     *
     * @param gemeenteOverlijden Gemeente overlijden.
     */
    public void setGemeenteOverlijden(final GemeenteAttribuut gemeenteOverlijden) {
        this.gemeenteOverlijden = gemeenteOverlijden;
    }

    /**
     * Zet Woonplaatsnaam overlijden van Overlijden.
     *
     * @param woonplaatsnaamOverlijden Woonplaatsnaam overlijden.
     */
    public void setWoonplaatsnaamOverlijden(final NaamEnumeratiewaardeAttribuut woonplaatsnaamOverlijden) {
        this.woonplaatsnaamOverlijden = woonplaatsnaamOverlijden;
    }

    /**
     * Zet Buitenlandse plaats overlijden van Overlijden.
     *
     * @param buitenlandsePlaatsOverlijden Buitenlandse plaats overlijden.
     */
    public void setBuitenlandsePlaatsOverlijden(final BuitenlandsePlaatsAttribuut buitenlandsePlaatsOverlijden) {
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    /**
     * Zet Buitenlandse regio overlijden van Overlijden.
     *
     * @param buitenlandseRegioOverlijden Buitenlandse regio overlijden.
     */
    public void setBuitenlandseRegioOverlijden(final BuitenlandseRegioAttribuut buitenlandseRegioOverlijden) {
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
    }

    /**
     * Zet Omschrijving locatie overlijden van Overlijden.
     *
     * @param omschrijvingLocatieOverlijden Omschrijving locatie overlijden.
     */
    public void setOmschrijvingLocatieOverlijden(final LocatieomschrijvingAttribuut omschrijvingLocatieOverlijden) {
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
    }

    /**
     * Zet Land/gebied overlijden van Overlijden.
     *
     * @param landGebiedOverlijdenCode Land/gebied overlijden.
     */
    public void setLandGebiedOverlijdenCode(final String landGebiedOverlijdenCode) {
        this.landGebiedOverlijdenCode = landGebiedOverlijdenCode;
    }

    /**
     * Zet Land/gebied overlijden van Overlijden.
     *
     * @param landGebiedOverlijden Land/gebied overlijden.
     */
    public void setLandGebiedOverlijden(final LandGebiedAttribuut landGebiedOverlijden) {
        this.landGebiedOverlijden = landGebiedOverlijden;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumOverlijden != null) {
            attributen.add(datumOverlijden);
        }
        if (gemeenteOverlijden != null) {
            attributen.add(gemeenteOverlijden);
        }
        if (woonplaatsnaamOverlijden != null) {
            attributen.add(woonplaatsnaamOverlijden);
        }
        if (buitenlandsePlaatsOverlijden != null) {
            attributen.add(buitenlandsePlaatsOverlijden);
        }
        if (buitenlandseRegioOverlijden != null) {
            attributen.add(buitenlandseRegioOverlijden);
        }
        if (omschrijvingLocatieOverlijden != null) {
            attributen.add(omschrijvingLocatieOverlijden);
        }
        if (landGebiedOverlijden != null) {
            attributen.add(landGebiedOverlijden);
        }
        return attributen;
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
