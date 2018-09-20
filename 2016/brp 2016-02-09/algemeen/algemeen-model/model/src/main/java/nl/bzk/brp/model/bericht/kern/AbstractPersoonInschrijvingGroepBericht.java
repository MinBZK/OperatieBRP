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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VersienummerAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonInschrijvingGroepBasis;

/**
 * Deze verzameling van gegevens geeft weer wanneer de gegevens van een persoon in de BRP (voorheen GBA) zijn
 * ingeschreven enm het moment van de laatste actualisering.
 *
 * 1. Vorm van historie: Formeel. Motivatie & uitleg: De groep inschrijving behoeft een aparte beschrijving. Zie
 * hiervoor ook een overkoepelend memo over Volgend-Vorig BSN en Anummer. De gegevens uit de groep bestaan ook in LO3.x;
 * daar is in de betreffende categorie zowel een datum opneming (=formeel tijdsaspect) als een datum geldigheid
 * (=materieel tijdsaspect) gevuld. Echter, in geval van gebruik van vorig/volgend Anummer/BSN, is de datum geldigheid
 * altijd gelijk aan de datum opneming. (Of te wel: eigenlijk is de materiële tijdslijn niet van toepassing voor deze
 * groep van gegevens). De groep Inschrijving bevat in LO BRP: - technisch gegeven tbv synchronisatie (versienr).
 * Materiële historie is niet van toepassing, althans formele historie volstaat. - gegevens over 'volgend en vorig
 * persoon'. In het Logisch model vervallen de attributen vorig/volgend BSN en Anummer, ten faveure van vorig/volgend
 * persoon. Het LO GBA 3.x voorziet niet in het samenvoegen van drie of meer persoonslijsten in één keer. (In twee keer
 * kan: stel A, B en C moeten samenworden gevoegd tot één C; dit kan door A te laten verwijzen naar B, en B naar C. Of
 * dit in de praktijk is voorgekomen is schrijver dezes onbekend.) Het lijkt erop (toetsing: use case
 * persoon-samenvoegen!) dat formele historie voldoende is: de verwijzingen gemigreerd uit LO 3.x kunnen erin, en na een
 * samenvoeging wordt de 'oude' opgeschort en zal er dus (normaliter) nooit meer iets wijzigen, c.q. is er géén sprake
 * van een benodigde materiële historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonInschrijvingGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep, PersoonInschrijvingGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 3521;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3570, 3250, 11186);
    private DatumEvtDeelsOnbekendAttribuut datumInschrijving;
    private VersienummerAttribuut versienummer;
    private DatumTijdAttribuut datumtijdstempel;

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersienummerAttribuut getVersienummer() {
        return versienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getDatumtijdstempel() {
        return datumtijdstempel;
    }

    /**
     * Zet Datum inschrijving van Inschrijving.
     *
     * @param datumInschrijving Datum inschrijving.
     */
    public void setDatumInschrijving(final DatumEvtDeelsOnbekendAttribuut datumInschrijving) {
        this.datumInschrijving = datumInschrijving;
    }

    /**
     * Zet Versienummer van Inschrijving.
     *
     * @param versienummer Versienummer.
     */
    public void setVersienummer(final VersienummerAttribuut versienummer) {
        this.versienummer = versienummer;
    }

    /**
     * Zet Datumtijdstempel van Inschrijving.
     *
     * @param datumtijdstempel Datumtijdstempel.
     */
    public void setDatumtijdstempel(final DatumTijdAttribuut datumtijdstempel) {
        this.datumtijdstempel = datumtijdstempel;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumInschrijving != null) {
            attributen.add(datumInschrijving);
        }
        if (versienummer != null) {
            attributen.add(versienummer);
        }
        if (datumtijdstempel != null) {
            attributen.add(datumtijdstempel);
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
