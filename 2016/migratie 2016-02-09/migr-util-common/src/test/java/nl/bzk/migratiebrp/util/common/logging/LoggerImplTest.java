/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.*;

/**
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MDC.class)
public class LoggerImplTest {

    public static final String NAME = "LoggerTest";
    public static final String STRING_DEBUG_LOGGING = "Debug Logging 1$ 2$";
    public static final String STRING_INFO_LOGGING = "info Logging 1$ 2$";
    public static final String STRING_WARN_LOGGING = "warn Logging 1$ 2$";
    public static final String STRING_ERROR_LOGGING = "error Logging 1$ 2$";
    public static final String FM_INFO = "Functionele melding 1$ 2$";
    List<String> debugLogging = new ArrayList<>();
    List<String> infoLogging = new ArrayList<>();
    List<String> warnLogging = new ArrayList<>();
    List<String> errorLogging = new ArrayList<>();
    List<Marker> markersInfo = new ArrayList<>();
    List<Marker> markersWarn= new ArrayList<>();
    List<Marker> markersError= new ArrayList<>();

    private LoggerImpl loggerEnabled;
    private LoggerImpl loggerDisabled;
    private LoggerImpl loggerException;

    @Before
    public void setup() {
        loggerEnabled = new LoggerImpl(new LoggerTest(true,false));
        loggerDisabled = new LoggerImpl(new LoggerTest(false,false));
        loggerException = new LoggerImpl(new LoggerTest(true,false));
        assertEquals(NAME, loggerEnabled.getName());
    }

    @Test
    public void debugLogging() {
        assertEquals(0, debugLogging.size());
        loggerEnabled.debug(STRING_DEBUG_LOGGING);
        assertEquals(STRING_DEBUG_LOGGING, debugLogging.get(0));
        loggerEnabled.debug(STRING_DEBUG_LOGGING, "iets", "niets");
        assertEquals(String.format(STRING_DEBUG_LOGGING,"iets","niets"), debugLogging.get(1));
        Map<String, String> data = new HashMap<>();
        data.put("een","twee");
        loggerEnabled.debug(data,STRING_DEBUG_LOGGING);
        assertEquals(STRING_DEBUG_LOGGING, debugLogging.get(2));
        loggerEnabled.debug(data,STRING_DEBUG_LOGGING, "iets", "niets");
        assertEquals(String.format(STRING_DEBUG_LOGGING,"iets","niets"), debugLogging.get(3));
    }

    @Test
    public void infoLogging() {
        assertEquals(0, infoLogging.size());
        loggerEnabled.info(STRING_INFO_LOGGING);
        assertEquals(STRING_INFO_LOGGING, infoLogging.get(0));
        loggerEnabled.info(STRING_INFO_LOGGING, "iets", "niets");
        assertEquals(String.format(STRING_INFO_LOGGING,"iets","niets"), infoLogging.get(1));
        Map<String, String> data = new HashMap<>();
        data.put("een","twee");
        loggerEnabled.info(data,STRING_INFO_LOGGING);
        assertEquals(STRING_INFO_LOGGING, infoLogging.get(2));
        loggerEnabled.info(data,STRING_INFO_LOGGING, "iets", "niets");
        assertEquals(String.format(STRING_INFO_LOGGING,"iets","niets"), infoLogging.get(3));
        loggerEnabled.info(FunctioneleMelding.ISC_JOB_VERWERKT);
        assertEquals(1, markersInfo.size());
        assertEquals(FunctioneleMelding.ISC_JOB_VERWERKT.getOmschrijving(),infoLogging.get(4));
        loggerEnabled.info(FunctioneleMelding.ISC_JOB_VERWERKT,STRING_INFO_LOGGING);
        assertEquals(2, markersInfo.size());
        assertEquals(STRING_INFO_LOGGING,infoLogging.get(5));
        loggerEnabled.info(FunctioneleMelding.ISC_JOB_VERWERKT,STRING_INFO_LOGGING, "iets", "niets");
        assertEquals(3, markersInfo.size());
        assertEquals(String.format(STRING_INFO_LOGGING,"iets","niets"), infoLogging.get(6));
        loggerEnabled.info(FunctioneleMelding.ISC_JOB_VERWERKT, data);
        assertEquals(4, markersInfo.size());
        assertEquals(FunctioneleMelding.ISC_JOB_VERWERKT.getOmschrijving(),infoLogging.get(7));
        loggerEnabled.info(FunctioneleMelding.ISC_JOB_VERWERKT,data,STRING_INFO_LOGGING);
        assertEquals(5, markersInfo.size());
        assertEquals(STRING_INFO_LOGGING,infoLogging.get(8));
        loggerEnabled.info(FunctioneleMelding.ISC_JOB_VERWERKT,data,STRING_INFO_LOGGING, "iets", "niets");
        assertEquals(6, markersInfo.size());
        assertEquals(String.format(STRING_INFO_LOGGING,"iets","niets"), infoLogging.get(9));
    }

    @Test
    public void warnLogging() {
        assertEquals(0, warnLogging.size());
        loggerEnabled.warn(STRING_WARN_LOGGING);
        assertEquals(STRING_WARN_LOGGING, warnLogging.get(0));
        loggerEnabled.warn(STRING_WARN_LOGGING, "iets", "niets");
        assertEquals(String.format(STRING_WARN_LOGGING,"iets","niets"), warnLogging.get(1));
        Map<String, String> data = new HashMap<>();
        data.put("een","twee");
        loggerEnabled.warn(data,STRING_WARN_LOGGING);
        assertEquals(STRING_WARN_LOGGING, warnLogging.get(2));
        loggerEnabled.warn(data,STRING_WARN_LOGGING, "iets", "niets");
        assertEquals(String.format(STRING_WARN_LOGGING,"iets","niets"), warnLogging.get(3));
        loggerEnabled.warn(FunctioneleMelding.ISC_JOB_VERWERKT);
        assertEquals(1, markersWarn.size());
        assertEquals(FunctioneleMelding.ISC_JOB_VERWERKT.getOmschrijving(),warnLogging.get(4));
        loggerEnabled.warn(FunctioneleMelding.ISC_JOB_VERWERKT,STRING_WARN_LOGGING);
        assertEquals(2, markersWarn.size());
        assertEquals(STRING_WARN_LOGGING,warnLogging.get(5));
        loggerEnabled.warn(FunctioneleMelding.ISC_JOB_VERWERKT,STRING_WARN_LOGGING, "iets", "niets");
        assertEquals(3, markersWarn.size());
        assertEquals(String.format(STRING_WARN_LOGGING,"iets","niets"), warnLogging.get(6));
        loggerEnabled.warn(FunctioneleMelding.ISC_JOB_VERWERKT, data);
        assertEquals(4, markersWarn.size());
        assertEquals(FunctioneleMelding.ISC_JOB_VERWERKT.getOmschrijving(),warnLogging.get(7));
        loggerEnabled.warn(FunctioneleMelding.ISC_JOB_VERWERKT,data,STRING_WARN_LOGGING);
        assertEquals(5, markersWarn.size());
        assertEquals(STRING_WARN_LOGGING,warnLogging.get(8));
        loggerEnabled.warn(FunctioneleMelding.ISC_JOB_VERWERKT,data,STRING_WARN_LOGGING, "iets", "niets");
        assertEquals(6, markersWarn.size());
        assertEquals(String.format(STRING_WARN_LOGGING,"iets","niets"), warnLogging.get(9));
    }

    @Test
    public void errorLogging() {
        assertEquals(0, errorLogging.size());
        loggerEnabled.error(STRING_ERROR_LOGGING);
        assertEquals(STRING_ERROR_LOGGING, errorLogging.get(0));
        loggerEnabled.error(STRING_ERROR_LOGGING, "iets", "niets");
        assertEquals(String.format(STRING_ERROR_LOGGING,"iets","niets"), errorLogging.get(1));
        Map<String, String> data = new HashMap<>();
        data.put("een","twee");
        loggerEnabled.error(data,STRING_ERROR_LOGGING);
        assertEquals(STRING_ERROR_LOGGING, errorLogging.get(2));
        loggerEnabled.error(data,STRING_ERROR_LOGGING, "iets", "niets");
        assertEquals(String.format(STRING_ERROR_LOGGING,"iets","niets"), errorLogging.get(3));
        loggerEnabled.error(FunctioneleMelding.ISC_JOB_VERWERKT);
        assertEquals(1, markersError.size());
        assertEquals(FunctioneleMelding.ISC_JOB_VERWERKT.getOmschrijving(),errorLogging.get(4));
        loggerEnabled.error(FunctioneleMelding.ISC_JOB_VERWERKT,STRING_ERROR_LOGGING);
        assertEquals(2, markersError.size());
        assertEquals(STRING_ERROR_LOGGING,errorLogging.get(5));
        loggerEnabled.error(FunctioneleMelding.ISC_JOB_VERWERKT,STRING_ERROR_LOGGING, "iets", "niets");
        assertEquals(3, markersError.size());
        assertEquals(String.format(STRING_ERROR_LOGGING,"iets","niets"), errorLogging.get(6));
        loggerEnabled.error(FunctioneleMelding.ISC_JOB_VERWERKT, data);
        assertEquals(4, markersError.size());
        assertEquals(FunctioneleMelding.ISC_JOB_VERWERKT.getOmschrijving(),errorLogging.get(7));
        loggerEnabled.error(FunctioneleMelding.ISC_JOB_VERWERKT,data,STRING_ERROR_LOGGING);
        assertEquals(5, markersError.size());
        assertEquals(STRING_ERROR_LOGGING,errorLogging.get(8));
        loggerEnabled.error(FunctioneleMelding.ISC_JOB_VERWERKT,data,STRING_ERROR_LOGGING, "iets", "niets");
        assertEquals(6, markersError.size());
        assertEquals(String.format(STRING_ERROR_LOGGING,"iets","niets"), errorLogging.get(9));
    }

    @Test
    public void testIsEnabled(){
        assertTrue(loggerEnabled.isDebugEnabled());
        assertTrue(loggerEnabled.isErrorEnabled());
        assertTrue(loggerEnabled.isInfoEnabled());
        assertTrue(loggerEnabled.isWarnEnabled());
        assertFalse(loggerDisabled.isDebugEnabled());
        assertFalse(loggerDisabled.isErrorEnabled());
        assertFalse(loggerDisabled.isInfoEnabled());
        assertFalse(loggerDisabled.isWarnEnabled());
    }

    @Test
    public void testMdc(){
        debugLogging = new ArrayList<>();
        infoLogging = new ArrayList<>();
        warnLogging = new ArrayList<>();
        errorLogging = new ArrayList<>();
        markersInfo = new ArrayList<>();
        markersWarn= new ArrayList<>();
        markersError= new ArrayList<>();
        PowerMockito.mockStatic(MDC.class);
        Mockito.when(MDC.putData(Mockito.anyMap())).thenThrow(new RuntimeException());
        Map<String, String> data = new HashMap<>();
        data.put("een","twee");
        try {
            loggerEnabled.debug(data, STRING_ERROR_LOGGING);
            fail();
        } catch(RuntimeException r){
        }
        assertTrue(verifyEmpty());
    }

    private boolean verifyEmpty(){
        return debugLogging.isEmpty() &&
                infoLogging.isEmpty() &&
                warnLogging.isEmpty() &&
                errorLogging.isEmpty() &&
                markersError.isEmpty() &&
                markersWarn.isEmpty() &&
                markersInfo.isEmpty();
    }

    private class LoggerTest implements org.slf4j.Logger {
        boolean traceEnabled;
        boolean debugEnabled;
        boolean infoEnabled;
        boolean warnEnabled;
        boolean errorEnabled;
        boolean throwEx;

        LoggerTest(boolean enableAllLogging,boolean throwException){
            traceEnabled = enableAllLogging;
            debugEnabled = enableAllLogging;
            warnEnabled = enableAllLogging;
            infoEnabled = enableAllLogging;
            errorEnabled = enableAllLogging;
            throwEx = throwException;

        }
        @Override
        public String getName() {
            return NAME;
        }

        @Override
        public boolean isTraceEnabled() {
            return traceEnabled;
        }

        @Override
        public void trace(String s) {

        }

        @Override
        public void trace(String s, Object o) {

        }

        @Override
        public void trace(String s, Object o, Object o1) {

        }

        @Override
        public void trace(String s, Object... objects) {

        }

        @Override
        public void trace(String s, Throwable throwable) {

        }

        @Override
        public boolean isTraceEnabled(Marker marker) {
            return !traceEnabled;
        }

        @Override
        public void trace(Marker marker, String s) {

        }

        @Override
        public void trace(Marker marker, String s, Object o) {

        }

        @Override
        public void trace(Marker marker, String s, Object o, Object o1) {

        }

        @Override
        public void trace(Marker marker, String s, Object... objects) {

        }

        @Override
        public void trace(Marker marker, String s, Throwable throwable) {

        }

        @Override
        public boolean isDebugEnabled() {
            if(throwEx)
            {
                throw new RuntimeException();
            }
            return debugEnabled;
        }

        @Override
        public void debug(String s) {
            debugLogging.add(s);
        }

        @Override
        public void debug(String s, Object o) {

        }

        @Override
        public void debug(String s, Object o, Object o1) {

        }

        @Override
        public void debug(String s, Object... objects) {
            debugLogging.add(String.format(s, objects));
        }

        @Override
        public void debug(String s, Throwable throwable) {

        }

        @Override
        public boolean isDebugEnabled(Marker marker) {
            return !debugEnabled;
        }

        @Override
        public void debug(Marker marker, String s) {

        }

        @Override
        public void debug(Marker marker, String s, Object o) {

        }

        @Override
        public void debug(Marker marker, String s, Object o, Object o1) {

        }

        @Override
        public void debug(Marker marker, String s, Object... objects) {

        }

        @Override
        public void debug(Marker marker, String s, Throwable throwable) {

        }

        @Override
        public boolean isInfoEnabled() {
            return infoEnabled;
        }

        @Override
        public void info(String s) {
            infoLogging.add(s);
        }

        @Override
        public void info(String s, Object o) {

        }

        @Override
        public void info(String s, Object o, Object o1) {

        }

        @Override
        public void info(String s, Object... objects) {
            infoLogging.add(String.format(s, objects));
        }

        @Override
        public void info(String s, Throwable throwable) {

        }

        @Override
        public boolean isInfoEnabled(Marker marker) {
            return !infoEnabled;
        }

        @Override
        public void info(Marker marker, String s) {
            markersInfo.add(marker);
            infoLogging.add(s);

        }

        @Override
        public void info(Marker marker, String s, Object o) {

        }

        @Override
        public void info(Marker marker, String s, Object o, Object o1) {

        }

        @Override
        public void info(Marker marker, String s, Object... objects) {
            markersInfo.add(marker);
            infoLogging.add(String.format(s,objects));
        }

        @Override
        public void info(Marker marker, String s, Throwable throwable) {

        }

        @Override
        public boolean isWarnEnabled() {
            return warnEnabled;
        }

        @Override
        public void warn(String s) {
            warnLogging.add(s);
        }

        @Override
        public void warn(String s, Object o) {

        }

        @Override
        public void warn(String s, Object... objects) {
            warnLogging.add(String.format(s, objects));
        }

        @Override
        public void warn(String s, Object o, Object o1) {

        }

        @Override
        public void warn(String s, Throwable throwable) {

        }

        @Override
        public boolean isWarnEnabled(Marker marker) {
            return !warnEnabled;
        }

        @Override
        public void warn(Marker marker, String s) {
            markersWarn.add(marker);
            warnLogging.add(s);
        }

        @Override
        public void warn(Marker marker, String s, Object o) {

        }

        @Override
        public void warn(Marker marker, String s, Object o, Object o1) {

        }

        @Override
        public void warn(Marker marker, String s, Object... objects) {
            markersWarn.add(marker);
            warnLogging.add(String.format(s, objects));
        }

        @Override
        public void warn(Marker marker, String s, Throwable throwable) {

        }

        @Override
        public boolean isErrorEnabled() {
            return errorEnabled;
        }

        @Override
        public void error(String s) {
            errorLogging.add(s);
        }

        @Override
        public void error(String s, Object o) {

        }

        @Override
        public void error(String s, Object o, Object o1) {

        }

        @Override
        public void error(String s, Object... objects) {
            errorLogging.add(String.format(s, objects));
        }

        @Override
        public void error(String s, Throwable throwable) {

        }

        @Override
        public boolean isErrorEnabled(Marker marker) {
            return !errorEnabled;
        }

        @Override
        public void error(Marker marker, String s) {
            markersError.add(marker);
            errorLogging.add(s);
        }

        @Override
        public void error(Marker marker, String s, Object o) {

        }

        @Override
        public void error(Marker marker, String s, Object o, Object o1) {

        }

        @Override
        public void error(Marker marker, String s, Object... objects) {
            markersError.add(marker);
            errorLogging.add(String.format(s, objects));
        }

        @Override
        public void error(Marker marker, String s, Throwable throwable) {

        }
    }
}
