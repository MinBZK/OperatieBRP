/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersonenOpAdresAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersonenOpAdresVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersoonAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersoonVerzoekType;
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
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesPartijRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesPartijRegisterVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesUitBrpAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesUitBrpVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PlaatsAfnemersindicatieVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ProtocolleringAntwoordLijstType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ProtocolleringLijstType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkAfnemersindicatieAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkAfnemersindicatieVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkToevalligeGebeurtenisAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkToevalligeGebeurtenisVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VrijBerichtAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VrijBerichtVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonOpActueleGegevensVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonOpHistorischeGegevensVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonVerzoekBericht;
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
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.OngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.PlaatsAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkAfnemersindicatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwijderAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpHistorischeGegevensVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Vertaal een binnengekomen Sync bericht naar een ESB Sync Bericht object.
 */
public enum SyncBerichtFactory implements BerichtFactory {

    /**
     * We willen een singleton object hebben hiervan.
     */
    SINGLETON;

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Map<Class, Function<Object, SyncBericht>> typeMap = new HashMap<>();

    static {
        typeMap.put(BlokkeringVerzoekType.class, value -> new BlokkeringVerzoekBericht((BlokkeringVerzoekType) value));
        typeMap.put(BlokkeringAntwoordType.class, value -> new BlokkeringAntwoordBericht((BlokkeringAntwoordType) value));
        typeMap.put(BlokkeringInfoVerzoekType.class, value -> new BlokkeringInfoVerzoekBericht((BlokkeringInfoVerzoekType) value));
        typeMap.put(BlokkeringInfoAntwoordType.class, value -> new BlokkeringInfoAntwoordBericht((BlokkeringInfoAntwoordType) value));
        typeMap.put(DeblokkeringVerzoekType.class, value -> new DeblokkeringVerzoekBericht((DeblokkeringVerzoekType) value));
        typeMap.put(DeblokkeringAntwoordType.class, value -> new DeblokkeringAntwoordBericht((DeblokkeringAntwoordType) value));
        typeMap.put(SynchroniseerNaarBrpVerzoekType.class, value -> new SynchroniseerNaarBrpVerzoekBericht((SynchroniseerNaarBrpVerzoekType) value));
        typeMap.put(SynchroniseerNaarBrpAntwoordType.class, value -> new SynchroniseerNaarBrpAntwoordBericht((SynchroniseerNaarBrpAntwoordType) value));
        typeMap.put(LeesUitBrpVerzoekType.class, value -> new LeesUitBrpVerzoekBericht((LeesUitBrpVerzoekType) value));
        typeMap.put(LeesUitBrpAntwoordType.class, value -> new LeesUitBrpAntwoordBericht((LeesUitBrpAntwoordType) value));
        typeMap.put(ZoekPersoonOpActueleGegevensVerzoekType.class,
                value -> new ZoekPersoonOpActueleGegevensVerzoekBericht((ZoekPersoonOpActueleGegevensVerzoekType) value));
        typeMap.put(ZoekPersoonOpHistorischeGegevensVerzoekType.class,
                value -> new ZoekPersoonOpHistorischeGegevensVerzoekBericht((ZoekPersoonOpHistorischeGegevensVerzoekType) value));
        typeMap.put(ZoekPersoonAntwoordType.class, value -> new ZoekPersoonAntwoordBericht((ZoekPersoonAntwoordType) value));
        typeMap.put(LeesPartijRegisterVerzoekType.class, value -> new LeesPartijRegisterVerzoekBericht((LeesPartijRegisterVerzoekType) value));
        typeMap.put(LeesPartijRegisterAntwoordType.class, value -> new LeesPartijRegisterAntwoordBericht((LeesPartijRegisterAntwoordType) value));
        typeMap.put(PlaatsAfnemersindicatieVerzoekType.class, value -> new PlaatsAfnemersindicatieVerzoekBericht((PlaatsAfnemersindicatieVerzoekType) value));
        typeMap.put(VerwerkAfnemersindicatieVerzoekType.class,
                value -> new VerwijderAfnemersindicatieVerzoekBericht((VerwerkAfnemersindicatieVerzoekType) value));
        typeMap.put(VerwerkAfnemersindicatieAntwoordType.class,
                value -> new VerwerkAfnemersindicatieAntwoordBericht((VerwerkAfnemersindicatieAntwoordType) value));
        typeMap.put(AdHocZoekPersoonVerzoekType.class, value -> new AdHocZoekPersoonVerzoekBericht((AdHocZoekPersoonVerzoekType) value));
        typeMap.put(AdHocZoekPersoonAntwoordType.class, value -> new AdHocZoekPersoonAntwoordBericht((AdHocZoekPersoonAntwoordType) value));
        typeMap.put(AdHocZoekPersonenOpAdresVerzoekType.class,
                value -> new AdHocZoekPersonenOpAdresVerzoekBericht((AdHocZoekPersonenOpAdresVerzoekType) value));
        typeMap.put(AdHocZoekPersonenOpAdresAntwoordType.class,
                value -> new AdHocZoekPersonenOpAdresAntwoordBericht((AdHocZoekPersonenOpAdresAntwoordType) value));

        // ARCHIVERING
        typeMap.put(ArchiveerInBrpVerzoekType.class, value -> new ArchiveringVerzoekBericht((ArchiveerInBrpVerzoekType) value));

        // NOTIFICATIES
        typeMap.put(AnummerWijzigingNotificatieType.class, value -> new AnummerWijzigingNotificatie((AnummerWijzigingNotificatieType) value));

        // INITIELE VULLING SPECIFIEKE BERICHTEN
        typeMap.put(AfnemersindicatiesType.class, value -> new AfnemersindicatiesBericht((AfnemersindicatiesType) value));
        typeMap.put(AfnemersindicatiesAntwoordType.class, value -> new AfnemersindicatiesAntwoordBericht((AfnemersindicatiesAntwoordType) value));
        typeMap.put(AutorisatieType.class, value -> new AutorisatieBericht((AutorisatieType) value));
        typeMap.put(AutorisatieAntwoordType.class, value -> new AutorisatieAntwoordBericht((AutorisatieAntwoordType) value));
        typeMap.put(ProtocolleringLijstType.class, value -> new ProtocolleringBericht((ProtocolleringLijstType) value));
        typeMap.put(ProtocolleringAntwoordLijstType.class, value -> new ProtocolleringAntwoordBericht((ProtocolleringAntwoordLijstType) value));

        // Toevallige gebeurtenis
        typeMap.put(VerwerkToevalligeGebeurtenisAntwoordType.class,
                value -> new VerwerkToevalligeGebeurtenisAntwoordBericht((VerwerkToevalligeGebeurtenisAntwoordType) value));
        typeMap.put(VerwerkToevalligeGebeurtenisVerzoekType.class,
                value -> new VerwerkToevalligeGebeurtenisVerzoekBericht((VerwerkToevalligeGebeurtenisVerzoekType) value));

        // Vrij bericht
        typeMap.put(VrijBerichtVerzoekType.class,
                value -> new VrijBerichtVerzoekBericht((VrijBerichtVerzoekType) value));
        typeMap.put(VrijBerichtAntwoordType.class,
                value -> new VrijBerichtAntwoordBericht((VrijBerichtAntwoordType) value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SyncBericht getBericht(final String bericht) {
        try {
            final JAXBElement<?> element = SyncXml.SINGLETON.stringToElement(bericht);
            return maakBericht(element.getValue());

        } catch (final JAXBException je) {
            LOG.info("Kon bericht-text niet omzetten naar een Bericht object", je);
            return new OngeldigBericht(bericht, je.getMessage());
        }
    }

    /**
     * Maakt het bericht op basis van de meegegeven value (representatie van het bericht).
     * @param value De meegegeven value.
     * @return Het bericht.
     */
    private SyncBericht maakBericht(final Object value) {
        return typeMap.getOrDefault(value.getClass(), this::onbekendType).apply(value);
    }

    private SyncBericht onbekendType(final Object value) {
        throw new IllegalArgumentException("Onbekende input kan niet worden vertaald in een SyncBericht. Type: " + value.getClass());
    }
}
