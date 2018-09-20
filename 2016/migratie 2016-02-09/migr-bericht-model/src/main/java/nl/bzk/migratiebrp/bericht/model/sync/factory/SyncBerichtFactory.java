/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.factory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import nl.bzk.migratiebrp.bericht.model.BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatiesAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatiesType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AnummerWijzigingNotificatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ArchiveerInBrpVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringInfoAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringInfoVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.DeblokkeringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.DeblokkeringVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesAutorisatieRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesAutorisatieRegisterVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesGemeenteRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesGemeenteRegisterVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesUitBrpAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesUitBrpVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PlaatsAfnemersindicatieVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkAfnemersindicatieAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkAfnemersindicatieVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkToevalligeGebeurtenisAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkToevalligeGebeurtenisVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonOpActueleGegevensVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonOpHistorischeGegevensVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AnummerWijzigingNotificatie;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ArchiveringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.OngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.PlaatsAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkAfnemersindicatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwijderAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpHistorischeGegevensVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Vertaal een binnengekomen Sync bericht naar een ESB Sync Bericht object.
 */
public enum SyncBerichtFactory implements BerichtFactory {

    /**
     * We willen een singleton object hebben hiervan.
     */
    SINGLETON;

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * {@inheritDoc}
     */
    @Override
    public SyncBericht getBericht(final String bericht) {
        try {
            final JAXBElement<?> element = SyncXml.SINGLETON.stringToElement(bericht);
            return maakBericht(element.getValue(), element.getName());

        } catch (final JAXBException je) {
            LOG.info("Kon bericht-text niet omzetten naar een Bericht object", je);
            return new OngeldigBericht(bericht, je.getMessage());
        }
    }

    /**
     * Maakt het bericht op basis van de meegegeven value (representatie van het bericht).
     *
     * @param value
     *            De meegegeven value.
     * @return Het bericht.
     */
    private SyncBericht maakBericht(final Object value, final QName name) {

        final SyncBericht bericht;
        if (value instanceof BlokkeringVerzoekType) {
            bericht = new BlokkeringVerzoekBericht((BlokkeringVerzoekType) value);
        } else if (value instanceof BlokkeringAntwoordType) {
            bericht = new BlokkeringAntwoordBericht((BlokkeringAntwoordType) value);
        } else if (value instanceof BlokkeringInfoVerzoekType) {
            bericht = new BlokkeringInfoVerzoekBericht((BlokkeringInfoVerzoekType) value);
        } else if (value instanceof BlokkeringInfoAntwoordType) {
            bericht = new BlokkeringInfoAntwoordBericht((BlokkeringInfoAntwoordType) value);
        } else if (value instanceof DeblokkeringVerzoekType) {
            bericht = new DeblokkeringVerzoekBericht((DeblokkeringVerzoekType) value);
        } else if (value instanceof DeblokkeringAntwoordType) {
            bericht = new DeblokkeringAntwoordBericht((DeblokkeringAntwoordType) value);
        } else if (value instanceof SynchroniseerNaarBrpVerzoekType) {
            bericht = new SynchroniseerNaarBrpVerzoekBericht((SynchroniseerNaarBrpVerzoekType) value);
        } else if (value instanceof SynchroniseerNaarBrpAntwoordType) {
            bericht = new SynchroniseerNaarBrpAntwoordBericht((SynchroniseerNaarBrpAntwoordType) value);
        } else if (value instanceof LeesUitBrpVerzoekType) {
            bericht = new LeesUitBrpVerzoekBericht((LeesUitBrpVerzoekType) value);
        } else if (value instanceof LeesUitBrpAntwoordType) {
            bericht = new LeesUitBrpAntwoordBericht((LeesUitBrpAntwoordType) value);
        } else if (value instanceof ZoekPersoonOpActueleGegevensVerzoekType) {
            bericht = new ZoekPersoonOpActueleGegevensVerzoekBericht((ZoekPersoonOpActueleGegevensVerzoekType) value);
        } else if (value instanceof ZoekPersoonOpHistorischeGegevensVerzoekType) {
            bericht = new ZoekPersoonOpHistorischeGegevensVerzoekBericht((ZoekPersoonOpHistorischeGegevensVerzoekType) value);
        } else if (value instanceof ZoekPersoonAntwoordType) {
            bericht = new ZoekPersoonAntwoordBericht((ZoekPersoonAntwoordType) value);
        } else if (value instanceof LeesGemeenteRegisterVerzoekType) {
            bericht = new LeesGemeenteRegisterVerzoekBericht((LeesGemeenteRegisterVerzoekType) value);
        } else if (value instanceof LeesGemeenteRegisterAntwoordType) {
            bericht = new LeesGemeenteRegisterAntwoordBericht((LeesGemeenteRegisterAntwoordType) value);
        } else if (value instanceof LeesAutorisatieRegisterVerzoekType) {
            bericht = new LeesAutorisatieRegisterVerzoekBericht((LeesAutorisatieRegisterVerzoekType) value);
        } else if (value instanceof LeesAutorisatieRegisterAntwoordType) {
            bericht = new LeesAutorisatieRegisterAntwoordBericht((LeesAutorisatieRegisterAntwoordType) value);
        } else if (value instanceof PlaatsAfnemersindicatieVerzoekType) {
            bericht = new PlaatsAfnemersindicatieVerzoekBericht((PlaatsAfnemersindicatieVerzoekType) value);
        } else if (value instanceof VerwerkAfnemersindicatieVerzoekType) {
            bericht = new VerwijderAfnemersindicatieVerzoekBericht((VerwerkAfnemersindicatieVerzoekType) value);
        } else if (value instanceof VerwerkAfnemersindicatieAntwoordType) {
            bericht = new VerwerkAfnemersindicatieAntwoordBericht((VerwerkAfnemersindicatieAntwoordType) value);

            // ARCHIVERING
        } else if (value instanceof ArchiveerInBrpVerzoekType) {
            bericht = new ArchiveringVerzoekBericht((ArchiveerInBrpVerzoekType) value);

            // NOTIFICATIES
        } else if (value instanceof AnummerWijzigingNotificatieType) {
            bericht = new AnummerWijzigingNotificatie((AnummerWijzigingNotificatieType) value);

            // INITIELE VULLING SPECIFIEKE BERICHTEN
        } else if (value instanceof AfnemersindicatiesType) {
            bericht = new AfnemersindicatiesBericht((AfnemersindicatiesType) value);
        } else if (value instanceof AfnemersindicatiesAntwoordType) {
            bericht = new AfnemersindicatiesAntwoordBericht((AfnemersindicatiesAntwoordType) value);
        } else if (value instanceof AutorisatieType) {
            bericht = new AutorisatieBericht((AutorisatieType) value);
        } else if (value instanceof AutorisatieAntwoordType) {
            bericht = new AutorisatieAntwoordBericht((AutorisatieAntwoordType) value);

            // Toevallige gebeurtenis
        } else if (value instanceof VerwerkToevalligeGebeurtenisAntwoordType) {
            bericht = new VerwerkToevalligeGebeurtenisAntwoordBericht((VerwerkToevalligeGebeurtenisAntwoordType) value);
        } else if (value instanceof VerwerkToevalligeGebeurtenisVerzoekType) {
            bericht = new VerwerkToevalligeGebeurtenisVerzoekBericht((VerwerkToevalligeGebeurtenisVerzoekType) value);
        } else {
            throw new IllegalArgumentException("Onbekende input kan niet worden vertaald in een SyncBericht. Type: " + value.getClass());
        }
        return bericht;
    }

}
