/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.notificatie.impl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.notificatie.AbstractNotificatieBericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.generated.GroepBerichtParameters;
import nl.bzk.migratiebrp.bericht.model.notificatie.generated.GroepBerichtStuurgegevens;
import nl.bzk.migratiebrp.bericht.model.notificatie.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.notificatie.generated.VrijBerichtVerwerkVrijBericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.xml.NotificatieXml;

/**
 * Vrij bericht.
 */
public final class VrijBerichtNotificatieBericht extends AbstractNotificatieBericht<VrijBerichtVerwerkVrijBericht> {

    private static final long serialVersionUID = 1L;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public VrijBerichtNotificatieBericht() {
        this(new VrijBerichtVerwerkVrijBericht());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     * @param vrijbericht het vrijbericht type
     */
    public VrijBerichtNotificatieBericht(final VrijBerichtVerwerkVrijBericht vrijbericht) {
        super("VrijBerichtVerwerkVrijBericht", "uc501-brp", vrijbericht);
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return NotificatieXml.SINGLETON.elementToString(new ObjectFactory().createVrbVrbVerwerkVrijBericht(getInhoud()));
    }

    @Override
    public JAXBElement<VrijBerichtVerwerkVrijBericht> parse(final String inhoud) throws JAXBException {
        return (JAXBElement<VrijBerichtVerwerkVrijBericht>) NotificatieXml.SINGLETON.stringToElement(inhoud);
    }

    @Override
    public String getVerzendendePartij() {
        final GroepBerichtStuurgegevens stuurgegevens = getStuurGegevens();
        if (stuurgegevens != null && stuurgegevens.getZendendePartij() != null) {
            return stuurgegevens.getZendendePartij().getValue().getValue();
        } else {
            return null;
        }
    }

    @Override
    public String getOntvangendePartij() {
        final GroepBerichtStuurgegevens stuurgegevens = getStuurGegevens();
        if (stuurgegevens != null && stuurgegevens.getOntvangendePartij() != null) {
            return stuurgegevens.getOntvangendePartij().getValue().getValue();
        } else {
            return null;
        }
    }

    public String getZenderVrijBericht() {
        final GroepBerichtParameters parameters = getParameters();
        if (parameters != null && parameters.getZenderVrijBericht() != null) {
            return parameters.getZenderVrijBericht().getValue().getValue();
        } else {
            return null;
        }
    }

    public String getOntvangerVrijBericht() {
        final GroepBerichtParameters parameters = getParameters();
        if (parameters != null && parameters.getOntvangerVrijBericht() != null) {
            return parameters.getOntvangerVrijBericht().getValue().getValue();
        } else {
            return null;
        }
    }

    public String getReferentieNummer() {
        final GroepBerichtStuurgegevens stuurgegevens = getStuurGegevens();
        if (stuurgegevens != null && stuurgegevens.getReferentienummer() != null) {
            return stuurgegevens.getReferentienummer().getValue().getValue();
        } else {
            return null;
        }
    }

    public String getVrijBerichtInhoud() {
        if (getInhoud() != null && getInhoud().getVrijBericht() != null && getInhoud().getVrijBericht().getValue().getInhoud() != null) {
            return getInhoud().getVrijBericht().getValue().getInhoud().getValue().getValue();
        } else {
            return null;
        }
    }

    private GroepBerichtStuurgegevens getStuurGegevens() {
        if (getInhoud() != null && getInhoud().getStuurgegevens() != null) {
            return getInhoud().getStuurgegevens().getValue();
        } else {
            return null;
        }
    }

    private GroepBerichtParameters getParameters() {
        if (getInhoud() != null && getInhoud().getParameters() != null) {
            return getInhoud().getParameters().getValue();
        } else {
            return null;
        }
    }
}
