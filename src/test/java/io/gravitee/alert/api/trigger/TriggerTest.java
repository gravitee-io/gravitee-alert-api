/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.alert.api.trigger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gravitee.alert.api.condition.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class TriggerTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test (expected = IllegalStateException.class)
    public void shouldNotBuild_noCondition() {
        Trigger.on("my-source").build();
    }

    @Test
    public void shouldExportToJson_noSeverity() throws IOException {
        Trigger trigger = Trigger
                .on("my-source")
                .name("shouldExportToJson_stringCondition")
                .condition(StringCondition.equals("a-field", "a-value").build())
                .build();

        String json = mapper.writeValueAsString(trigger);

        Assert.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assert.assertEquals(trigger, trigger2);
        Assert.assertEquals(Trigger.Severity.INFO, trigger.getSeverity());
        Assert.assertEquals(Trigger.Severity.INFO, trigger2.getSeverity());
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

        Assert.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assert.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_thresholdCondition() throws IOException {
        Trigger trigger = Trigger
                .on("my-source")
                .name("shouldExportToJson_thresholdCondition")
                .condition(ThresholdCondition.greaterThan("a-field", 100d).build())
                .build();

        String json = mapper.writeValueAsString(trigger);

        Assert.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assert.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_thresholdRangeCondition() throws IOException {
        Trigger trigger = Trigger
                .on("my-source")
                .name("shouldExportToJson_thresholdRangeCondition")
                .condition(ThresholdRangeCondition
                        .between("a-field", 100d, 500d).build())
                .build();

        String json = mapper.writeValueAsString(trigger);

        Assert.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assert.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_compareCondition() throws IOException {
        Trigger trigger = Trigger
                .on("my-source")
                .name("shouldExportToJson_compareCondition")
                .condition(CompareCondition
                        .lowerThan("a-field", 0.1d, "b-field").build())
                .build();

        String json = mapper.writeValueAsString(trigger);

        Assert.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assert.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_stringCompareCondition() throws IOException {
        Trigger trigger = Trigger
                .on("my-source")
                .name("shouldExportToJson_stringCompareCondition")
                .condition(StringCompareCondition
                        .equals("a-field", "b-field", false).build())
                .build();

        String json = mapper.writeValueAsString(trigger);

        Assert.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assert.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_aggregationCondition_count() throws IOException {
        Trigger trigger = Trigger
                .on("my-source")
                .name("shouldExportToJson_thresholdAccumulateCondition_count")
                .condition(
                        AggregationCondition
                            .count()
                            .duration(1, TimeUnit.MINUTES)
                            .greaterThan(50d)
                            .build())
                .filter(StringCondition.equals("api", "my-api").build())
                .build();

        String json = mapper.writeValueAsString(trigger);

        Assert.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assert.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_aggregationCondition_average() throws IOException {
        Trigger trigger = Trigger
                .on("my-source")
                .name("shouldExportToJson_thresholdAccumulateCondition_average")
                .condition(
                        AggregationCondition
                                .avg("response-time")
                                .duration(1, TimeUnit.MINUTES)
                                .greaterThan(50d)
                                .build())
                .filters(
                        Arrays.asList(
                                StringCondition.equals("api", "my-api").build(),
                                ThresholdCondition.greaterThan("status", 400d).build()
                        ))
                .build();

        String json = mapper.writeValueAsString(trigger);

        Assert.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assert.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_rateCondition() throws IOException {
        Trigger trigger = Trigger
                .on("my-source")
                .name("shouldExportToJson_rateCondition")
                .condition(
                        RateCondition
                                .of(ThresholdCondition.greaterThan("response-time", 100d).build())
                                .duration(10000)
                                .greaterThan(50d)
                                .build()
                )
                .build();

        String json = mapper.writeValueAsString(trigger);

        Assert.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assert.assertEquals(trigger, trigger2);
    }

    @Test
    public void shouldExportToJson_frequencyCondition() throws IOException {
        Trigger trigger = Trigger
                .on("my-source")
                .name("shouldExportToJson_frequencyCondition")
                .condition(
                        FrequencyCondition
                                .of(ThresholdCondition.greaterThan("status", 400d).build())
                                .duration(1, TimeUnit.MINUTES)
                                .greaterThanOrEquals(40)
                                .build()
                )
                .filter(StringCondition.equals("api", "my-api").build())
                .build();

        String json = mapper.writeValueAsString(trigger);

        Assert.assertNotNull(json);

        Trigger trigger2 = mapper.readValue(json, Trigger.class);

        Assert.assertEquals(trigger, trigger2);
    }
}
