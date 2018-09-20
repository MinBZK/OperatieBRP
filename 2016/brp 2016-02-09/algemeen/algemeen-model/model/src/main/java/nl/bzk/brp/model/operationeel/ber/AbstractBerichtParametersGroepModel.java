/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.StamgegevenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RolAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.ber.BerichtParametersGroep;
import nl.bzk.brp.model.logisch.ber.BerichtParametersGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractBerichtParametersGroepModel implements BerichtParametersGroepBasis {

    @Embedded
    @AttributeOverride(name = VerwerkingswijzeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Verwerkingswijze"))
    @JsonProperty
    private VerwerkingswijzeAttribuut verwerkingswijze;

    @Transient
    @JsonProperty
    private SleutelwaardetekstAttribuut bezienVanuitPersoon;

    @Embedded
    @AttributeOverride(name = RolAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rol"))
    @JsonProperty
    private RolAttribuut rol;

    @Embedded
    @AttributeOverride(name = SoortSynchronisatieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SrtSynchronisatie"))
    @JsonProperty
    private SoortSynchronisatieAttribuut soortSynchronisatie;

    @JsonProperty
    @Column(name = "Levsautorisatie")
    private Integer leveringsautorisatieId;

    @JsonProperty
    @Column(name = "Dienst")
    private Integer dienstId;

    @Transient
    @JsonProperty
    private DatumAttribuut peilmomentMaterieelSelectie;

    @Transient
    @JsonProperty
    private DatumAttribuut peilmomentMaterieelResultaat;

    @Transient
    @JsonProperty
    private DatumTijdAttribuut peilmomentFormeelResultaat;

    @Transient
    @JsonProperty
    private StamgegevenAttribuut stamgegeven;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractBerichtParametersGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param verwerkingswijze verwerkingswijze van Parameters.
     * @param bezienVanuitPersoon bezienVanuitPersoon van Parameters.
     * @param rol rol van Parameters.
     * @param soortSynchronisatie soortSynchronisatie van Parameters.
     * @param leveringsautorisatieId leveringsautorisatieId van Parameters.
     * @param dienstId dienstId van Parameters.
     * @param peilmomentMaterieelSelectie peilmomentMaterieelSelectie van Parameters.
     * @param peilmomentMaterieelResultaat peilmomentMaterieelResultaat van Parameters.
     * @param peilmomentFormeelResultaat peilmomentFormeelResultaat van Parameters.
     * @param stamgegeven stamgegeven van Parameters.
     */
    public AbstractBerichtParametersGroepModel(
        final VerwerkingswijzeAttribuut verwerkingswijze,
        final SleutelwaardetekstAttribuut bezienVanuitPersoon,
        final RolAttribuut rol,
        final SoortSynchronisatieAttribuut soortSynchronisatie,
        final Integer leveringsautorisatieId,
        final Integer dienstId,
        final DatumAttribuut peilmomentMaterieelSelectie,
        final DatumAttribuut peilmomentMaterieelResultaat,
        final DatumTijdAttribuut peilmomentFormeelResultaat,
        final StamgegevenAttribuut stamgegeven)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.verwerkingswijze = verwerkingswijze;
        this.bezienVanuitPersoon = bezienVanuitPersoon;
        this.rol = rol;
        this.soortSynchronisatie = soortSynchronisatie;
        this.leveringsautorisatieId = leveringsautorisatieId;
        this.dienstId = dienstId;
        this.peilmomentMaterieelSelectie = peilmomentMaterieelSelectie;
        this.peilmomentMaterieelResultaat = peilmomentMaterieelResultaat;
        this.peilmomentFormeelResultaat = peilmomentFormeelResultaat;
        this.stamgegeven = stamgegeven;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtParametersGroep te kopieren groep.
     */
    public AbstractBerichtParametersGroepModel(final BerichtParametersGroep berichtParametersGroep) {
        this.verwerkingswijze = berichtParametersGroep.getVerwerkingswijze();
        this.bezienVanuitPersoon = berichtParametersGroep.getBezienVanuitPersoon();
        this.rol = berichtParametersGroep.getRol();
        this.soortSynchronisatie = berichtParametersGroep.getSoortSynchronisatie();
        this.peilmomentMaterieelSelectie = berichtParametersGroep.getPeilmomentMaterieelSelectie();
        this.peilmomentMaterieelResultaat = berichtParametersGroep.getPeilmomentMaterieelResultaat();
        this.peilmomentFormeelResultaat = berichtParametersGroep.getPeilmomentFormeelResultaat();
        this.stamgegeven = berichtParametersGroep.getStamgegeven();

    }

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
    public Integer getLeveringsautorisatieId() {
        return leveringsautorisatieId;
    }

    /**
     * Retourneert Dienst van Parameters.
     *
     * @return Dienst.
     */
    public Integer getDienstId() {
        return dienstId;
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
     * {@inheritDoc}
     */
    @Override
    public StamgegevenAttribuut getStamgegeven() {
        return stamgegeven;
    }

    /**
     * Zet Leveringsautorisatie van Parameters.
     *
     * @param leveringsautorisatieId Leveringsautorisatie.
     */
    public void setLeveringsautorisatieId(final Integer leveringsautorisatieId) {
        this.leveringsautorisatieId = leveringsautorisatieId;
    }

    /**
     * Zet Dienst van Parameters.
     *
     * @param dienstId Dienst.
     */
    public void setDienstId(final Integer dienstId) {
        this.dienstId = dienstId;
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
        if (peilmomentMaterieelSelectie != null) {
            attributen.add(peilmomentMaterieelSelectie);
        }
        if (peilmomentMaterieelResultaat != null) {
            attributen.add(peilmomentMaterieelResultaat);
        }
        if (peilmomentFormeelResultaat != null) {
            attributen.add(peilmomentFormeelResultaat);
        }
        if (stamgegeven != null) {
            attributen.add(stamgegeven);
        }
        return attributen;
    }

}
