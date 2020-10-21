package com.fasterxml.jackson.datatype.jsr310.deser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.temporal.ChronoUnit;

import org.junit.Test;

import com.fasterxml.jackson.datatype.jsr310.ModuleTestBase;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer.DurationUnitConverter;

public class DurationUnitConverterTest
    extends ModuleTestBase
{
    @Test
    public void shouldMapToTemporalUnit() {
        for (ChronoUnit inputUnit : new ChronoUnit[] {
                ChronoUnit.NANOS,
                ChronoUnit.MICROS,
                ChronoUnit.MILLIS,
                ChronoUnit.SECONDS,
                ChronoUnit.MINUTES,
                ChronoUnit.HOURS,
                ChronoUnit.HALF_DAYS,
                ChronoUnit.DAYS,
        }) {
            DurationUnitConverter conv = DurationUnitConverter.from(inputUnit.name());
            assertNotNull(conv);
            assertEquals(inputUnit, conv.unit);
            // is case-sensitive:
            assertNull(DurationUnitConverter.from(inputUnit.name().toLowerCase()));
        }
    }

    @Test
    public void shouldNotMapToTemporalUnit() {
        for (String invalid : new String[] {
                // Inaccurate units not (yet?) supported
                "WEEKS",
                "MONTHS",
                "YEARS",
                "DECADES",
                "CENTURIES",
                "MILLENNIA",
                "ERAS",
                "FOREVER",

                // Not matching at all
                "DOESNOTMATCH", "", "   "
        }) {
            assertNull("Should not map pattern '"+invalid+"'",
                    DurationUnitConverter.from(invalid));
        }
    }
}
