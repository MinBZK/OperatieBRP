/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres.BRBY0024;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY0152;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY9901;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY9902;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY9905;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY9906;
import nl.bzk.brp.business.regels.NaActieRegel;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.business.regels.VoorBerichtRegel;
import nl.bzk.brp.dataaccess.repository.RegelRepository;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.attribuuttype.brm.RegelCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Service voor Regels in de bijhouding.
 */
@Service
public class BijhoudingRegelService {

    @Inject
    private ApplicationContext applicationContext;

    @Inject
    private ActieRegelManagerFactory actieRegelManagerFactory;

    @Inject
    private RegelRepository regelRepository;

    /**
     * Haalt het {@link RegelParameters} object op dat hoort bij de gegeven Regel.
     * @param regel de regel waarvoor de paramaters opgehaald worden
     * @return de regelparameters
     */
    public RegelParameters getRegelParametersVoorRegel(final Regel regel) {
        return regelRepository.getRegelParametersVoorRegel(new RegelCodeAttribuut(regel.getCode()));
    }

    /**
     * Geeft de {@link VoorBerichtRegel}s op basis van de gegeven parameter.
     * @param soortBericht het soort bericht
     * @return de lijst van voorberichtregels
     */
    public List<VoorBerichtRegel> getVoorBerichtRegels(
        @NotNull final SoortBericht soortBericht)
    {
        final List<VoorBerichtRegel> regels = new ArrayList<>();
        regels.add(getVoorBerichtBean(BRBY9901.class));
        regels.add(getVoorBerichtBean(BRBY9902.class));
        regels.add(getVoorBerichtBean(BRBY9905.class));
        regels.add(getVoorBerichtBean(BRBY9906.class));
        regels.add(getVoorBerichtBean(BRBY0152.class));
        if (soortBericht == SoortBericht.BHG_VBA_CORRIGEER_ADRES) {
            regels.add(getVoorBerichtBean(BRBY0024.class));
        }

        return regels;
    }

    /**
     * Geeft de {@link VoorActieRegel}s op basis van de gegeven parameters.
     * @param soortAdministratieveHandeling de soort administratieve handeling
     * @param soortActie die soort actie
     * @return de lijst van vooractieregels
     */
    public List<VoorActieRegel> getVoorActieRegels(
        @NotNull final SoortAdministratieveHandeling soortAdministratieveHandeling,
        @NotNull final SoortActie soortActie)
    {
        final ActieRegelManager actieRegelManager = vindActieRegelManager(soortActie);
        final List<Class<? extends VoorActieRegel>> regelKlassen = actieRegelManager.getVoorActieRegels(soortAdministratieveHandeling);
        return getVoorActieRegels(regelKlassen);
    }

    /**
     * Geeft de {@link NaActieRegel}s op basis van de gegeven parameters.
     * @param soortAdministratieveHandeling de soort administratieve handeling
     * @param soortActie de soort actie
     * @return de lijst van na-actieregels
     */
    public List<NaActieRegel> getNaActieRegels(
        @NotNull final SoortAdministratieveHandeling soortAdministratieveHandeling,
        @NotNull final SoortActie soortActie)
    {
        final ActieRegelManager actieRegelManager = vindActieRegelManager(soortActie);
        final List<Class<? extends NaActieRegel>> regelKlassen = actieRegelManager.getNaActieRegels(soortAdministratieveHandeling);
        return getNaActieRegels(regelKlassen);
    }

    private ActieRegelManager vindActieRegelManager(@NotNull final SoortActie soortActie) {
        final Class<? extends ActieRegelManager> actieRegelManagerKlasse = actieRegelManagerFactory.getActieRegelManager(soortActie);
        return applicationContext.getBean(actieRegelManagerKlasse);
    }

    private List<VoorActieRegel> getVoorActieRegels(final List<Class<? extends VoorActieRegel>> regelKlassen) {
        final List<VoorActieRegel> regels = new ArrayList<>();
        for (Class<? extends VoorActieRegel> regelKlasse : regelKlassen) {
            regels.add(getVoorActieBean(regelKlasse));
        }
        return regels;
    }

    private List<NaActieRegel> getNaActieRegels(final List<Class<? extends NaActieRegel>> regelKlassen) {
        final List<NaActieRegel> regels = new ArrayList<>();
        for (Class<? extends NaActieRegel> regelKlasse : regelKlassen) {
            regels.add(getNaActieBean(regelKlasse));
        }
        return regels;
    }

    private VoorActieRegel getVoorActieBean(final Class<? extends VoorActieRegel> regelClass) {
        return (VoorActieRegel) applicationContext.getBean(regelClass.getSimpleName());
    }

    private NaActieRegel getNaActieBean(final Class<? extends NaActieRegel> regelClass) {
        return (NaActieRegel) applicationContext.getBean(regelClass.getSimpleName());
    }

    private VoorBerichtRegel getVoorBerichtBean(final Class<? extends VoorBerichtRegel> regelClass) {
        return (VoorBerichtRegel) applicationContext.getBean(regelClass.getSimpleName());
    }


}
