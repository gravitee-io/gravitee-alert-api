/*
 * Copyright © 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.alert.api.trigger;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gravitee.alert.api.condition.*;
import io.gravitee.notifier.api.Period;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class TriggerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldNotBuild_noCondition() {
        assertThrows(IllegalStateException.class, () -> Trigger.on("my-source").build());
    }

    @Test
    public void shouldExportToJson_noSeverity() throws IOException {
        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_stringCondition")
            .condition(StringCondition.equals("a-field", "a-value").build())
            .build();

        String json = mapper.writeValueAsString(trigger);

        Assertions.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assertions.assertEquals(trigger, trigger2);
        Assertions.assertEquals(Trigger.Severity.INFO, trigger.getSeverity());
        Assertions.assertEquals(Trigger.Severity.INFO, trigger2.getSeverity());
    }

    @Test
    public void shouldExportToJson_stringCondition() throws IOException {
        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_stringCondition")
            .condition(StringCondition.equals("a-field", "a-value").build())
            .severity(Trigger.Severity.WARNING)
            .build();

        String json = mapper.writeValueAsString(trigger);

        Assertions.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assertions.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_thresholdCondition() throws IOException {
        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_thresholdCondition")
            .condition(ThresholdCondition.greaterThan("a-field", 100d).build())
            .build();

        String json = mapper.writeValueAsString(trigger);

        Assertions.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assertions.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_thresholdRangeCondition() throws IOException {
        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_thresholdRangeCondition")
            .condition(ThresholdRangeCondition.between("a-field", 100d, 500d).build())
            .build();

        String json = mapper.writeValueAsString(trigger);

        Assertions.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assertions.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_compareCondition() throws IOException {
        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_compareCondition")
            .condition(CompareCondition.lowerThan("a-field", 0.1d, "b-field").build())
            .build();

        String json = mapper.writeValueAsString(trigger);

        Assertions.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assertions.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_stringCompareCondition() throws IOException {
        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_stringCompareCondition")
            .condition(StringCompareCondition.equals("a-field", "b-field", false).build())
            .build();

        String json = mapper.writeValueAsString(trigger);

        Assertions.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assertions.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_aggregationCondition_count() throws IOException {
        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_thresholdAccumulateCondition_count")
            .condition(AggregationCondition.count().duration(1, TimeUnit.MINUTES).greaterThan(50d).build())
            .filter(StringCondition.equals("api", "my-api").build())
            .build();

        String json = mapper.writeValueAsString(trigger);

        Assertions.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assertions.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_aggregationCondition_average() throws IOException {
        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_thresholdAccumulateCondition_average")
            .condition(AggregationCondition.avg("response-time").duration(1, TimeUnit.MINUTES).greaterThan(50d).build())
            .filters(Arrays.asList(StringCondition.equals("api", "my-api").build(), ThresholdCondition.greaterThan("status", 400d).build()))
            .build();

        String json = mapper.writeValueAsString(trigger);

        Assertions.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assertions.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_rateCondition() throws IOException {
        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_rateCondition")
            .condition(
                RateCondition.of(ThresholdCondition.greaterThan("response-time", 100d).build()).duration(10000).greaterThan(50d).build()
            )
            .build();

        String json = mapper.writeValueAsString(trigger);

        Assertions.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assertions.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_missingDataCondition() throws IOException {
        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_rmissingDataCondition")
            .condition(MissingDataCondition.duration(10, TimeUnit.SECONDS).build())
            .build();

        String json = mapper.writeValueAsString(trigger);

        Assertions.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assertions.assertEquals(trigger, trigger2);
    }

    @Test
    public void canNotify_included() {
        final long now = System.currentTimeMillis();

        final Period period = new Period.Builder()
            .beginHour(LocalTime.of(0, 0, 0).toSecondOfDay())
            .endHour(LocalTime.of(23, 59, 59).toSecondOfDay())
            .build();

        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_rmissingDataCondition")
            .condition(MissingDataCondition.duration(10, TimeUnit.SECONDS).build())
            .notificationPeriod(period)
            .build();

        assertTrue(trigger.canNotify(now));
    }

    @Test
    public void canNotify_notIncluded() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime hourBefore = now.minusHours(1);

        if (now.getDayOfWeek() != hourBefore.getDayOfWeek()) {
            // Be sure we are on the same day (I know some people capable to run this test at midnight! ;-) ).
            now = now.plusHours(1);
            hourBefore = hourBefore.plusHours(1);
        }

        final Period period = new Period.Builder()
            .beginHour(LocalTime.of(0, 0, 0).toSecondOfDay())
            .endHour(LocalTime.of(hourBefore.getHour(), 59, 59).toSecondOfDay())
            .build();

        Trigger trigger = Trigger
            .on("my-source")
            .name("shouldExportToJson_rmissingDataCondition")
            .condition(MissingDataCondition.duration(10, TimeUnit.SECONDS).build())
            .notificationPeriod(period)
            .build();

        assertFalse(trigger.canNotify(now.toEpochSecond(ZoneOffset.UTC) * 1000));
    }
}
