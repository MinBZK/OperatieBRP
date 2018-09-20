/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.Calendar;
import javax.inject.Inject;

import nl.bzk.brp.business.stappen.BerichtenIds;
import nl.bzk.brp.dataaccess.special.BerichtRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Berichtdata;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.logisch.ber.BerichtParametersGroep;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroep;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroep;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.ber.BerichtParametersGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtResultaatGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/** Standaard implementatie van de {@link ArchiveringService} die archiveert naar de database. */
@Service
public class ArchiveringServiceImpl implements ArchiveringService {

    @Inject
    private BerichtRepository berichtRepository;

    /** {@inheritDoc} */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BerichtenIds archiveer(final String ingaandBerichtdata) {

        BerichtModel ingaandBerichtModel = new BerichtModel(
            null, /*final SoortBericht soort, */
            null, /*new AdministratieveHandelingModel(), */
            new Berichtdata(ingaandBerichtdata), /* Berichtdata */
            new DatumTijd(Calendar.getInstance().getTime()),        /*final DatumTijd datumTijdOntvangst, */
            null, /*final DatumTijd datumTijdVerzenden, */
            null, /*final BerichtModel antwoordOp, */
            Richting.INGAAND /*final Richting richting */
        );

        berichtRepository.save(ingaandBerichtModel);

        /** downward compatible, datumTijdOntvangst OOK op null gezet */
        BerichtModel uitgaandBerichtModel = new BerichtModel(
            null, /*final SoortBericht soort, */
            null, /*final AdministratieveHandelingModel administratieveHandeling, */
            new Berichtdata("<Wordt nader bepaald>"), /*final Berichtdata data, */
            null, /*final DatumTijd datumTijdOntvangst, */
            null, /*final DatumTijd datumTijdVerzenden, */
            ingaandBerichtModel, /*final BerichtModel antwoordOp, */
            Richting.UITGAAND /*final Richting richting */
        );
        berichtRepository.save(uitgaandBerichtModel);
        return new BerichtenIds(ingaandBerichtModel.getID(), uitgaandBerichtModel.getID());
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void werkDataBij(final Long uitgaandBerichtId, final String data) {
        BerichtModel uitgaandBerichtModel = berichtRepository.findOne(uitgaandBerichtId);
        if (uitgaandBerichtModel == null) {
            throw new RuntimeException(String.format("uitgaand bericht %s ontbreekt in database", uitgaandBerichtId));
        }
        uitgaandBerichtModel.setData(new Berichtdata(data));
        uitgaandBerichtModel.setDatumTijdVerzenden(new DatumTijd(Calendar.getInstance().getTime()));
        berichtRepository.save(uitgaandBerichtModel);
    }

    /** {@inheritDoc} */
    @Override
    public void werkIngaandBerichtInfoBij(final Long ingaandBerichtId,
        final BerichtStuurgegevensGroep berichtStuurgegevens,
        final BerichtParametersGroep parameters,
        final SoortBericht soortBericht)
    {
        final BerichtModel ingaandBericht = berichtRepository.findOne(ingaandBerichtId);
        final BerichtStuurgegevensGroepBericht stuurgegevensGroep = new BerichtStuurgegevensGroepBericht();
        stuurgegevensGroep.setApplicatie(berichtStuurgegevens.getApplicatie());
        stuurgegevensGroep.setOrganisatie(berichtStuurgegevens.getOrganisatie());
        stuurgegevensGroep.setReferentienummer(berichtStuurgegevens.getReferentienummer());
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepModel(stuurgegevensGroep));

        // wat vroeger prevalidatie was.
        if (null != parameters && null != parameters.getVerwerkingswijze()) {
            if (null == ingaandBericht.getParameters()) {
                ingaandBericht.setParameters(new BerichtParametersGroepModel(parameters.getVerwerkingswijze(),
                    parameters.getPeilmomentMaterieel(), parameters.getPeilmomentFormeel(),
                    parameters.getAanschouwer()));
            }
        }

        if (null != soortBericht) {
            ingaandBericht.setSoort(soortBericht);
        }
        berichtRepository.save(ingaandBericht);
    }

    /** {@inheritDoc} */
    @Override
    public void werkUitgaandBerichtInfoBij(final Long uitgaandBerichtId,
        final BerichtStuurgegevensGroep stuurgegevensGroep,
        final BerichtResultaatGroep berichtResultaatGroep)
    {
        BerichtModel uitgaandBericht = berichtRepository.findOne(uitgaandBerichtId);
        uitgaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepModel(stuurgegevensGroep));
        uitgaandBericht.setResultaat(new BerichtResultaatGroepModel(berichtResultaatGroep));

        berichtRepository.save(uitgaandBericht);
    }

}
