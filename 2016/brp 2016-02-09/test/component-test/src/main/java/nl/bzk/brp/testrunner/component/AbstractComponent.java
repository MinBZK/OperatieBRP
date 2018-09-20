/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.testrunner.omgeving.Component;
import nl.bzk.brp.testrunner.omgeving.ComponentException;
import nl.bzk.brp.testrunner.omgeving.Link;
import nl.bzk.brp.testrunner.omgeving.LogischeNaam;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import nl.bzk.brp.testrunner.omgeving.OmgevingIncompleetException;
import nl.bzk.brp.testrunner.component.util.PoortManager;

/**
 * Hoogste abstractie voor een component.
 */
public abstract class AbstractComponent implements Component {

    public enum Status {
        INIT,
        GESTART,
        GESTOPT
    }

    private final Omgeving omgeving;

    private Status                status   = Status.INIT;
    private Map<Integer, Integer> poortMap = new HashMap<>();


    protected AbstractComponent(final Omgeving omgeving) {
        this.omgeving = omgeving;

    }

    public final Omgeving getOmgeving() {
        return omgeving;
    }

    @Override
    public final String getLogischeNaam() {
        return getClass().getAnnotation(LogischeNaam.class).value();
    }

    @Override
    public boolean isGestart() {
        return status == Status.GESTART;
    }

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return isGestart();
    }

    @Override
    public boolean isGestopt() {
        return status == Status.GESTOPT;
    }

    @Override
    public void herstart() {
        throw new UnsupportedOperationException();
    }

    public final void start() {
        if (status != Status.INIT) {
            throw new ComponentException("Component kan niet gestart worden");
        }
        doStart();
        setGestart();
    }

    public final void stop() {
        if (status != Status.GESTART) {
            throw new ComponentException("Component kan niet gestopt worden, want het is nooit gestart");
        }
        doStop();
        setGestopt();
    }

    public final void preStart() {
        final Link annotation = getClass().getAnnotation(Link.class);
        if (annotation != null) {
            for (Class<? extends AbstractDockerComponent> component : annotation.value()) {
                final String afhankelijkComponent = component.getAnnotation(LogischeNaam.class).value();
                if (!omgeving.bevat(afhankelijkComponent)) {
                    throw new OmgevingIncompleetException(
                        String.format("Omgeving is incompleet %s is afhankelijk van %s", getLogischeNaam(), afhankelijkComponent));
                }
            }
        }


        for (int internePoort : geefInternePoorten()) {
            poortMap.put(internePoort, geefExternePoort(internePoort));
        }

        doPrestart();
    }

    @Override
    public Map<Integer, Integer> getPoortMap() {
        return Collections.unmodifiableMap(poortMap);
    }

    protected Map<String, String> geefInjectOmgevingsVariabelen() {
        return new HashMap<>();
    }

    protected Map<String, String> geefAddHostParameters() {
        return new HashMap<>();
    }

    protected abstract void doStop();

    protected abstract void doStart();

    protected void doPrestart() {};

    protected List<Integer> geefInternePoorten() {
        return new LinkedList<>();
    }

    private void setGestart() {
        if (this.status != Status.INIT) {
            throw new IllegalStateException();
        }
        this.status = Status.GESTART;
    }


    private void setGestopt() {
        if (this.status != Status.GESTART) {
            throw new IllegalStateException();
        }
        this.status = Status.GESTOPT;
    }


    protected int geefExternePoort(int internePoort) {
        return PoortManager.get().geefVrijePoort();
    }


}
