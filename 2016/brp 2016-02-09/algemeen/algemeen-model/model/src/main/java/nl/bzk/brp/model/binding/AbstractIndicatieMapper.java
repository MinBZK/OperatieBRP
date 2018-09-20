/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;

import org.jibx.runtime.IAliasable;
import org.jibx.runtime.IMarshaller;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshaller;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.MarshallingContext;


/**
 * <p>
 * Specifieke marshaller/unmarshaller voor de {@link nl.bzk.brp.model.logisch.kern.PersoonIndicatie} en
 * {@link nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel} instanties. Deze mapper class verzorgd zowel de
 * JiBX marshalling als unmarshalling van de indicaties van en naar XML. Hierbij wordt bij de marshalling voor elke
 * indicatie, de soort van de indicatie gebruikt als de naam van de indicatie node/element en wordt binnen het indicatie
 * element de verdere content van de indicatie getoond, zoals de waarde en eventuele historische data. Bij de
 * unmarshalling wordt dus het indicatie element gebruikt om een nieuwe indicatie aan de persoon toe te voegen, waarbij
 * de naam van het indicatie element wordt gebruikt om het soort van de indicatie te bepalen. Verder zal dan uiteraard
 * de content van het indicatie element gebruikt worden om de waarde van de indicatie te zetten.
 * </p>
 * <p>
 * Het netto effect van deze marshaller/unmarshaller is dat de XML structuur in het volgende formaat zal zijn:
 * </p>
 * <p/>
 * <p/>
 * 
 * <pre>
 *   &lt;onderCuratele>
 *     &lt;waarde>J&lt;/waarde>
 *   &lt;/onderCuratele>
 * 
 *   &lt;verstrekkingsbeperking>
 *     &lt;waarde>J&lt;/waarde>
 *   &lt;/verstrekkingsbeperking>
 * </pre>
 * <p/>
 */
public abstract class AbstractIndicatieMapper implements IMarshaller, IAliasable, IUnmarshaller {

    /**
     * Logger.
     */
    protected static final Logger                    LOGGER                              = LoggerFactory.getLogger();

    private static final String                      STANDAARD_WAARDE_ELEMENT_NAAM       = "waarde";
    private static final String                      NAMESPACE_URI_BRP                   =
                                                                                             "http://www.bzk.nl/brp/brp0200";
    private static final String                      SOORT_INDICATIE_NAAM_SEPERATOR      = "_";
    private static final Map<String, SoortIndicatie> INDICATIENAAM_MAP;
    private static final String                      XML_TAG_NAAM_TIJDSTIPREGISTRATIE    = "tijdstipRegistratie";
    private static final String                      XML_TAG_NAAM_ACTIEAANPGELDIGHEID    = "actieAanpassingGeldigheid";
    private static final String                      XML_TAG_NAAM_DATUMEINDEGELDIGHEID   = "datumEindeGeldigheid";
    private static final String                      XML_TAG_NAAM_DATUMAANVANGGELDIGHEID = "datumAanvangGeldigheid";
    private static final String                      XML_TAG_NAAM_ACTIEVERVAL            = "actieVerval";
    private static final String                      XML_TAG_NAAM_TIJDSTIPVERVAL         = "tijdstipVerval";
    private static final String                      XML_TAG_NAAM_ACTIEINHOUD            = "actieInhoud";

    static {
        INDICATIENAAM_MAP = new HashMap<>();
        for (SoortIndicatie soortIndicatie : SoortIndicatie.values()) {
            INDICATIENAAM_MAP.put(bepaalIndicatieElementNaam(soortIndicatie), soortIndicatie);
        }
    }

    /**
     * Moeten historie velden gemarshalled worden? Bij betrokkenen bijvoorbeeld niet.
     *
     * @return true indien historie velden gemarshalled moeten worden.
     */
    protected abstract boolean historieVeldenMarshallen();

    /**
     * Moet de object sleutel gemarshalled worden? Bij leveringen niet, bij bevraging wel.
     *
     * @return true indien de technische sleutel gemarshalled moet worden, anders false.
     */
    protected abstract boolean marshalObjectSleutel();

    /**
     * Moet de voorkomen sleutel gemarshalled worden? Bij leveringen niet, bij bevraging wel.
     *
     * @return true indien de technische sleutel gemarshalled moet worden, anders false.
     */
    protected abstract boolean marshalVoorkomenSleutel();

    @Override
    public final void marshal(final Object obj, final IMarshallingContext marshallingContext) throws JiBXException {
        // controleer de parameters
        if (!(obj instanceof HisPersoonIndicatieModel)) {
            LOGGER.error(String.format("Onverwacht (en niet ondersteund) type voor JiBX Marshaller: %s", obj.getClass()));
            throw new JiBXException("Niet ondersteund type voor marshaller object");
        } else {
            final MarshallingContext context = (MarshallingContext) marshallingContext;
            final HisPersoonIndicatieModel indicatie = (HisPersoonIndicatieModel) obj;

            // Autorisatie
            if (isAutorisatieVanKracht() && !indicatie.isMagGeleverdWorden()) {
                return;
            }

            final int namespaceIndexBrp = bepaalNamespaceIndex(NAMESPACE_URI_BRP, context);

            // start met element voor de indicatie
            context.startTagAttributes(namespaceIndexBrp, bepaalIndicatieElementNaam(indicatie.getPersoonIndicatie()
                    .getSoort().getWaarde()));

            context.attribute(namespaceIndexBrp, "objecttype", "PersoonIndicatie");

            if (marshalObjectSleutel()) {
                context.attribute(namespaceIndexBrp, "objectSleutel", indicatie.getPersoonIndicatie().getID());
            }

            if (marshalVoorkomenSleutel()) {
                context.attribute(namespaceIndexBrp, "voorkomenSleutel", indicatie.getID());
            }

            if (indicatie.getVerwerkingssoort() != null) {
                context.attribute(namespaceIndexBrp, "verwerkingssoort", indicatie.getVerwerkingssoort()
                        .getVasteWaarde());
            }
            context.closeStartContent();

            if (historieVeldenMarshallen()) {
                marshalHistorieVelden(context, indicatie, namespaceIndexBrp);
            }

            // Geef de content van het indicatie element
            if (indicatie.getWaarde() != null) {
                context.startTagAttributes(namespaceIndexBrp, STANDAARD_WAARDE_ELEMENT_NAAM);
                context.closeStartContent();
                context.content(getXmlContent(indicatie.getWaarde()));
                context.endTag(namespaceIndexBrp, STANDAARD_WAARDE_ELEMENT_NAAM);
            }

            // sluit het element
            context.endTag(namespaceIndexBrp, bepaalIndicatieElementNaam(indicatie.getPersoonIndicatie().getSoort()
                    .getWaarde()));
        }
    }

    private void marshalHistorieVelden(final MarshallingContext context, final HisPersoonIndicatieModel indicatie,
            final int namespaceIndexBrp) throws JiBXException
    {
        marshalTijdstipRegistratie(context, indicatie, namespaceIndexBrp);

        if (marshalVerantwoording()) {
            marshalActieInhoud(context, indicatie, namespaceIndexBrp);
        }

        marshalTijdstipVerval(context, indicatie, namespaceIndexBrp);

        if (marshalVerantwoording()) {
            marshalActieVerval(context, indicatie, namespaceIndexBrp);
        }

        if (isMaterieelHistorisch(indicatie)) {
            final MaterieelHistorisch materieleIndicatie = (MaterieelHistorisch) indicatie;
            marshalDatumAanvangGeldigheid(context, materieleIndicatie, namespaceIndexBrp);
            marshalDatumEindeGeldigheid(context, materieleIndicatie, namespaceIndexBrp);
        }

        if (marshalVerantwoording()) {
            marshalActieAanpassingGeldigheid(context, indicatie, namespaceIndexBrp);
        }
    }

    private boolean moetAttribuutGemarshalledWorden(final Attribuut attribuut) {
        boolean marshal = true;
        if (isAutorisatieVanKracht()) {
            marshal = attribuut.isMagGeleverdWorden();
        }
        return marshal;
    }

    private boolean moetActieGemarshalledWorden(final ActieModel actie) {
        boolean marshal = true;
        if (isAutorisatieVanKracht()) {
            marshal = actie.isMagGeleverdWorden();
        }
        return marshal;
    }

    private void marshalTijdstipRegistratie(final MarshallingContext context, final HisPersoonIndicatieModel indicatie,
            final int namespaceIndexBrp) throws JiBXException
    {
        final DatumTijdAttribuut tijdstipRegistratie = getTijdstipRegistratie(indicatie);
        if (moetAttribuutGemarshalledWorden(tijdstipRegistratie)) {
            context.startTagAttributes(namespaceIndexBrp, XML_TAG_NAAM_TIJDSTIPREGISTRATIE);
            context.closeStartContent();
            context.content(BindingUtilAttribuutTypen.datumTijdNaarXml(tijdstipRegistratie.getWaarde()));
            context.endTag(namespaceIndexBrp, XML_TAG_NAAM_TIJDSTIPREGISTRATIE);
        }
    }

    private void marshalActieAanpassingGeldigheid(final MarshallingContext context,
            final HisPersoonIndicatieModel indicatie, final int namespaceIndexBrp) throws JiBXException
    {
        final ActieModel actieAanpassingGeldigheid = getActieAanpassingGeldigheid(indicatie);
        if (actieAanpassingGeldigheid != null && moetActieGemarshalledWorden(actieAanpassingGeldigheid)) {
            context.startTagAttributes(namespaceIndexBrp, XML_TAG_NAAM_ACTIEAANPGELDIGHEID);
            context.closeStartContent();
            context.content(actieAanpassingGeldigheid.getObjectSleutel());
            context.endTag(namespaceIndexBrp, XML_TAG_NAAM_ACTIEAANPGELDIGHEID);
        }
    }

    private void marshalDatumEindeGeldigheid(final MarshallingContext context,
            final MaterieelHistorisch materieleIndicatie, final int namespaceIndexBrp) throws JiBXException
    {
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid =
            materieleIndicatie.getMaterieleHistorie().getDatumEindeGeldigheid();
        if (datumEindeGeldigheid != null && moetAttribuutGemarshalledWorden(datumEindeGeldigheid)) {
            context.startTagAttributes(namespaceIndexBrp, XML_TAG_NAAM_DATUMEINDEGELDIGHEID);
            context.closeStartContent();
            context.content(BindingUtilAttribuutTypen.datumNaarXml(materieleIndicatie.getMaterieleHistorie()
                    .getDatumEindeGeldigheid().getWaarde()));
            context.endTag(namespaceIndexBrp, XML_TAG_NAAM_DATUMEINDEGELDIGHEID);
        }
    }

    private void marshalDatumAanvangGeldigheid(final MarshallingContext context, final MaterieelHistorisch indicatie,
            final int namespaceIndexBrp) throws JiBXException
    {
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid =
            indicatie.getMaterieleHistorie().getDatumAanvangGeldigheid();
        if (moetAttribuutGemarshalledWorden(datumAanvangGeldigheid)) {
            context.startTagAttributes(namespaceIndexBrp, XML_TAG_NAAM_DATUMAANVANGGELDIGHEID);
            context.closeStartContent();
            context.content(BindingUtilAttribuutTypen.datumNaarXml(datumAanvangGeldigheid.getWaarde()));
            context.endTag(namespaceIndexBrp, XML_TAG_NAAM_DATUMAANVANGGELDIGHEID);
        }
    }

    private void marshalActieVerval(final MarshallingContext context, final HisPersoonIndicatieModel indicatie,
            final int namespaceIndexBrp) throws JiBXException
    {
        final ActieModel actieVerval = getActieVerval(indicatie);
        if (actieVerval != null && moetActieGemarshalledWorden(actieVerval)) {
            context.startTagAttributes(namespaceIndexBrp, XML_TAG_NAAM_ACTIEVERVAL);
            context.closeStartContent();
            context.content(actieVerval.getObjectSleutel());
            context.endTag(namespaceIndexBrp, XML_TAG_NAAM_ACTIEVERVAL);
        }
    }

    private void marshalTijdstipVerval(final MarshallingContext context, final HisPersoonIndicatieModel indicatie,
            final int namespaceIndexBrp) throws JiBXException
    {
        final DatumTijdAttribuut datumTijdVerval = getDatumTijdVerval(indicatie);
        if (datumTijdVerval != null && moetAttribuutGemarshalledWorden(datumTijdVerval)) {
            context.startTagAttributes(namespaceIndexBrp, XML_TAG_NAAM_TIJDSTIPVERVAL);
            context.closeStartContent();
            context.content(BindingUtilAttribuutTypen.datumTijdNaarXml(datumTijdVerval.getWaarde()));
            context.endTag(namespaceIndexBrp, XML_TAG_NAAM_TIJDSTIPVERVAL);
        }
    }

    private void marshalActieInhoud(final MarshallingContext context, final HisPersoonIndicatieModel indicatie,
            final int namespaceIndexBrp) throws JiBXException
    {
        final ActieModel actieInhoud = getActieInhoud(indicatie);
        if (moetActieGemarshalledWorden(actieInhoud)) {
            // Actie inhoud
            context.startTagAttributes(namespaceIndexBrp, XML_TAG_NAAM_ACTIEINHOUD);
            context.closeStartContent();
            context.content(actieInhoud.getObjectSleutel());
            context.endTag(namespaceIndexBrp, XML_TAG_NAAM_ACTIEINHOUD);
        }
    }

    /**
     * Moet er bij het marshallen rekening worden gehouden met autorisaties op de velden.
     *
     * @return true indien er rekening moet worden gehouden met autorisaties.
     */
    protected abstract boolean isAutorisatieVanKracht();

    /**
     * Moet de verantwoordingsinformatie gemarshalled worden?
     *
     * @return true indien de verantwoording gemarshalled moet worden.
     */
    protected abstract boolean marshalVerantwoording();

    @SuppressWarnings("unchecked")
    private ActieModel getActieInhoud(final HisPersoonIndicatieModel indicatie) {
        if (indicatie instanceof FormeelVerantwoordbaar) {
            return ((FormeelVerantwoordbaar<ActieModel>) indicatie).getVerantwoordingInhoud();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private ActieModel getActieVerval(final HisPersoonIndicatieModel indicatie) {
        if (indicatie instanceof FormeelVerantwoordbaar
            && ((FormeelVerantwoordbaar<ActieModel>) indicatie).getVerantwoordingVerval() != null)
        {
            return ((FormeelVerantwoordbaar<ActieModel>) indicatie).getVerantwoordingVerval();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private ActieModel getActieAanpassingGeldigheid(final HisPersoonIndicatieModel indicatie) {
        if (indicatie instanceof MaterieelVerantwoordbaar
            && ((MaterieelVerantwoordbaar<ActieModel>) indicatie).getVerantwoordingAanpassingGeldigheid() != null)
        {
            return ((MaterieelVerantwoordbaar<ActieModel>) indicatie).getVerantwoordingAanpassingGeldigheid();
        }
        return null;
    }

    private DatumTijdAttribuut getTijdstipRegistratie(final HisPersoonIndicatieModel hisObject) {
        DatumTijdAttribuut tijdstipRegistratie;
        if (isMaterieelHistorisch(hisObject)) {
            tijdstipRegistratie = ((MaterieelHistorisch) hisObject).getMaterieleHistorie().getTijdstipRegistratie();
        } else {
            tijdstipRegistratie = ((FormeelHistorisch) hisObject).getFormeleHistorie().getTijdstipRegistratie();
        }
        return tijdstipRegistratie;
    }

    private DatumTijdAttribuut getDatumTijdVerval(final HisPersoonIndicatieModel hisObject) {
        DatumTijdAttribuut datumTijdVerval;
        if (isMaterieelHistorisch(hisObject)) {
            datumTijdVerval = ((MaterieelHistorisch) hisObject).getMaterieleHistorie().getDatumTijdVerval();
        } else {
            datumTijdVerval = ((FormeelHistorisch) hisObject).getFormeleHistorie().getDatumTijdVerval();
        }
        return datumTijdVerval;
    }

    private boolean isMaterieelHistorisch(final HisPersoonIndicatieModel hisObject) {
        return hisObject instanceof MaterieelHistorisch;
    }

    @Override
    public final Object unmarshal(final Object obj, final IUnmarshallingContext ctx) throws JiBXException {
        throw new UnsupportedOperationException("Historie records van persoon indicaties worden niet ondersteund, "
            + "en kunnen niet worden ge-unmarshalled.");
    }

    /**
     * Bepaalt de element naam voor de marshalling op basis van de soort van de indicatie. De naam van de indicatie
     * soort wordt namelijk gebruikt (in een
     * bepaalde vorm, namelijk CamelCase en zonder underscores) voor de element naam.
     *
     * @param soortIndicatie de soort van de indicatie.
     * @return de te gebruiken element naam voor de soort indicatie.
     */
    protected static String bepaalIndicatieElementNaam(final SoortIndicatie soortIndicatie) {
        String enumeratieNaam = soortIndicatie.name().toLowerCase();
        enumeratieNaam = enumeratieNaam.replace("indicatie_", "");
        final String[] enumeratieNaamElementen = enumeratieNaam.split(SOORT_INDICATIE_NAAM_SEPERATOR);

        final StringBuilder indicatieElementNaam = new StringBuilder();
        for (int index = 0; index < enumeratieNaamElementen.length; index++) {
            if (index == 0) {
                indicatieElementNaam.append(enumeratieNaamElementen[index]);
            } else {
                indicatieElementNaam.append(enumeratieNaamElementen[index].substring(0, 1).toUpperCase());
                indicatieElementNaam.append(enumeratieNaamElementen[index].substring(1));
            }
        }
        return indicatieElementNaam.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isPresent(final IUnmarshallingContext ctx) throws JiBXException {
        boolean isPresent = false;
        for (String indicatieNaam : INDICATIENAAM_MAP.keySet()) {
            isPresent |= ctx.isAt(NAMESPACE_URI_BRP, indicatieNaam);
        }
        return isPresent;
    }

    /**
     * Bepaalt en retourneert de namespace index. Hiervoor zal op basis van de standaard namespace van dit element en de
     * aanwezige namespaces in de context
     * de waarde worden bepaalt en geretourneerd. Indien de standaard namespace van het element niet bekend is binnen de
     * context, zal '0' worden
     * geretourneerd.
     *
     * @param namespace de namespace
     * @param context de context waar binnen de marshalling plaats vindt.
     * @return Een int.
     */
    protected final int bepaalNamespaceIndex(final String namespace, final MarshallingContext context) {
        int namespaceIndex = 0;
        if (context.getNamespaces() != null) {
            final String[] namespaces = context.getNamespaces();
            for (int i = 0; i < namespaces.length; i++) {
                if (namespace.equals(namespaces[i])) {
                    namespaceIndex = i;
                }
            }
        }
        return namespaceIndex;
    }

    /**
     * Geeft de xml tag inhoud voor een ja object.
     *
     * @param waarde ja object
     * @return de xml tag inhoud
     */
    protected final String getXmlContent(final JaAttribuut waarde) {
        if (waarde.getWaarde().getVasteWaarde()) {
            return "J";
        } else {
            throw new IllegalArgumentException("Een indicatie van het type \"Ja\" kan in zijn bestaan "
                + "alleen maar true zijn.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isExtension(final String mapnaam) {
        return false;
    }

}
