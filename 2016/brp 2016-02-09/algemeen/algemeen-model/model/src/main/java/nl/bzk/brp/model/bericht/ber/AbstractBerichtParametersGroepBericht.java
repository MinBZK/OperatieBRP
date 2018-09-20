/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.StamgegevenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicatiesAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingssituatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.HistorievormAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RolAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.ber.BerichtParametersGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractBerichtParametersGroepBericht extends AbstractBerichtIdentificeerbaar implements Groep, BerichtParametersGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 9219;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(
        6255,
        21583,
        21927,
        11276,
        10519,
        11281,
        11282,
        11412,
        6256,
        6257,
        11398,
        11362,
        21444);
    private VerwerkingswijzeAttribuut verwerkingswijze;
    private SleutelwaardetekstAttribuut bezienVanuitPersoon;
    private RolAttribuut rol;
    private SoortSynchronisatieAttribuut soortSynchronisatie;
    private String leveringsautorisatieID;
    private LeveringsautorisatieAttribuut leveringsautorisatie;
    private String dienstID;
    private DienstAttribuut dienst;
    private EffectAfnemerindicatiesAttribuut effectAfnemerindicatie;
    private DatumAttribuut peilmomentMaterieelSelectie;
    private DatumAttribuut peilmomentMaterieelResultaat;
    private DatumTijdAttribuut peilmomentFormeelResultaat;
    private HistorievormAttribuut historievorm;
    private StamgegevenAttribuut stamgegeven;
    private BijhoudingssituatieAttribuut redenNotificatie;

    /**
     * {@inheritDoc}
     */
    @Override
    public VerwerkingswijzeAttribuut getVerwerkingswijze() {
        return verwerkingswijze;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SleutelwaardetekstAttribuut getBezienVanuitPersoon() {
        return bezienVanuitPersoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RolAttribuut getRol() {
        return rol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortSynchronisatieAttribuut getSoortSynchronisatie() {
        return soortSynchronisatie;
    }

    /**
     * Retourneert Leveringsautorisatie van Parameters.
     *
     * @return Leveringsautorisatie.
     */
    public String getLeveringsautorisatieID() {
        return leveringsautorisatieID;
    }

    /**
     * Retourneert Leveringsautorisatie van Parameters.
     *
     * @return Leveringsautorisatie.
     */
    public LeveringsautorisatieAttribuut getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Retourneert Dienst van Parameters.
     *
     * @return Dienst.
     */
    public String getDienstID() {
        return dienstID;
    }

    /**
     * Retourneert Dienst van Parameters.
     *
     * @return Dienst.
     */
    public DienstAttribuut getDienst() {
        return dienst;
    }

    /**
     * Retourneert Effect afnemerindicatie van Parameters.
     *
     * @return Effect afnemerindicatie.
     */
    public EffectAfnemerindicatiesAttribuut getEffectAfnemerindicatie() {
        return effectAfnemerindicatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumAttribuut getPeilmomentMaterieelSelectie() {
        return peilmomentMaterieelSelectie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumAttribuut getPeilmomentMaterieelResultaat() {
        return peilmomentMaterieelResultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getPeilmomentFormeelResultaat() {
        return peilmomentFormeelResultaat;
    }

    /**
     * Retourneert Historievorm van Parameters.
     *
     * @return Historievorm.
     */
    public HistorievormAttribuut getHistorievorm() {
        return historievorm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StamgegevenAttribuut getStamgegeven() {
        return stamgegeven;
    }

    /**
     * Retourneert Reden notificatie van Parameters.
     *
     * @return Reden notificatie.
     */
    public BijhoudingssituatieAttribuut getRedenNotificatie() {
        return redenNotificatie;
    }

    /**
     * Zet Verwerkingswijze van Parameters.
     *
     * @param verwerkingswijze Verwerkingswijze.
     */
    public void setVerwerkingswijze(final VerwerkingswijzeAttribuut verwerkingswijze) {
        this.verwerkingswijze = verwerkingswijze;
    }

    /**
     * Zet Bezien vanuit Persoon van Parameters.
     *
     * @param bezienVanuitPersoon Bezien vanuit Persoon.
     */
    public void setBezienVanuitPersoon(final SleutelwaardetekstAttribuut bezienVanuitPersoon) {
        this.bezienVanuitPersoon = bezienVanuitPersoon;
    }

    /**
     * Zet Rol van Parameters.
     *
     * @param rol Rol.
     */
    public void setRol(final RolAttribuut rol) {
        this.rol = rol;
    }

    /**
     * Zet Soort synchronisatie van Parameters.
     *
     * @param soortSynchronisatie Soort synchronisatie.
     */
    public void setSoortSynchronisatie(final SoortSynchronisatieAttribuut soortSynchronisatie) {
        this.soortSynchronisatie = soortSynchronisatie;
    }

    /**
     * Zet Leveringsautorisatie van Parameters.
     *
     * @param leveringsautorisatieID Leveringsautorisatie.
     */
    public void setLeveringsautorisatieID(final String leveringsautorisatieID) {
        this.leveringsautorisatieID = leveringsautorisatieID;
    }

    /**
     * Zet Leveringsautorisatie van Parameters.
     *
     * @param leveringsautorisatie Leveringsautorisatie.
     */
    public void setLeveringsautorisatie(final LeveringsautorisatieAttribuut leveringsautorisatie) {
        this.leveringsautorisatie = leveringsautorisatie;
    }

    /**
     * Zet Dienst van Parameters.
     *
     * @param dienstID Dienst.
     */
    public void setDienstID(final String dienstID) {
        this.dienstID = dienstID;
    }

    /**
     * Zet Dienst van Parameters.
     *
     * @param dienst Dienst.
     */
    public void setDienst(final DienstAttribuut dienst) {
        this.dienst = dienst;
    }

    /**
     * Zet Effect afnemerindicatie van Parameters.
     *
     * @param effectAfnemerindicatie Effect afnemerindicatie.
     */
    public void setEffectAfnemerindicatie(final EffectAfnemerindicatiesAttribuut effectAfnemerindicatie) {
        this.effectAfnemerindicatie = effectAfnemerindicatie;
    }

    /**
     * Zet Peilmoment materieel selectie van Parameters.
     *
     * @param peilmomentMaterieelSelectie Peilmoment materieel selectie.
     */
    public void setPeilmomentMaterieelSelectie(final DatumAttribuut peilmomentMaterieelSelectie) {
        this.peilmomentMaterieelSelectie = peilmomentMaterieelSelectie;
    }

    /**
     * Zet Peilmoment materieel resultaat van Parameters.
     *
     * @param peilmomentMaterieelResultaat Peilmoment materieel resultaat.
     */
    public void setPeilmomentMaterieelResultaat(final DatumAttribuut peilmomentMaterieelResultaat) {
        this.peilmomentMaterieelResultaat = peilmomentMaterieelResultaat;
    }

    /**
     * Zet Peilmoment formeel resultaat van Parameters.
     *
     * @param peilmomentFormeelResultaat Peilmoment formeel resultaat.
     */
    public void setPeilmomentFormeelResultaat(final DatumTijdAttribuut peilmomentFormeelResultaat) {
        this.peilmomentFormeelResultaat = peilmomentFormeelResultaat;
    }

    /**
     * Zet Historievorm van Parameters.
     *
     * @param historievorm Historievorm.
     */
    public void setHistorievorm(final HistorievormAttribuut historievorm) {
        this.historievorm = historievorm;
    }

    /**
     * Zet Stamgegeven van Parameters.
     *
     * @param stamgegeven Stamgegeven.
     */
    public void setStamgegeven(final StamgegevenAttribuut stamgegeven) {
        this.stamgegeven = stamgegeven;
    }

    /**
     * Zet Reden notificatie van Parameters.
     *
     * @param redenNotificatie Reden notificatie.
     */
    public void setRedenNotificatie(final BijhoudingssituatieAttribuut redenNotificatie) {
        this.redenNotificatie = redenNotificatie;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (verwerkingswijze != null) {
            attributen.add(verwerkingswijze);
        }
        if (bezienVanuitPersoon != null) {
            attributen.add(bezienVanuitPersoon);
        }
        if (rol != null) {
            attributen.add(rol);
        }
        if (soortSynchronisatie != null) {
            attributen.add(soortSynchronisatie);
        }
        if (leveringsautorisatie != null) {
            attributen.add(leveringsautorisatie);
        }
        if (dienst != null) {
            attributen.add(dienst);
        }
        if (effectAfnemerindicatie != null) {
            attributen.add(effectAfnemerindicatie);
        }
        if (peilmomentMaterieelSelectie != null) {
            attributen.add(peilmomentMaterieelSelectie);
        }
        if (peilmomentMaterieelResultaat != null) {
            attributen.add(peilmomentMaterieelResultaat);
        }
        if (peilmomentFormeelResultaat != null) {
            attributen.add(peilmomentFormeelResultaat);
        }
        if (historievorm != null) {
            attributen.add(historievorm);
        }
        if (stamgegeven != null) {
            attributen.add(stamgegeven);
        }
        if (redenNotificatie != null) {
            attributen.add(redenNotificatie);
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
