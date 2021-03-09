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
package io.gravitee.alert.api.condition;

import io.gravitee.alert.api.condition.projection.Projections;
import io.gravitee.alert.api.condition.projection.PropertyProjection;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * The rate / percentage calculation is based on a time window and must be define with a duration.
 *
 * Unlike {@link StringCondition} or {@link ThresholdCondition}, the condition is based on multiple events within
 * the given timeframe.
 *
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class RateConditionTest {

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the percentage (rate) of events (which match the given filters) for which the "latency" value is
     * greater or equals than 500 milliseconds during the last minute with a minimum sample size set to 20 samples.
     *
     * The condition is evaluated to true if the calculated rate is lower than 40%.
     */
    @Test
    public void shouldBuildRateCondition_lowerThan() {
        RateCondition condition = RateCondition
                .of(ThresholdCondition.greaterThanOrEquals("latency", 500d).build())
                .duration(1, TimeUnit.MINUTES)
                .lowerThan(40d)
                .projection(Projections.property("api"))
                .sampleSize(20)
                .build();

        // Check condition
        Assert.assertNotNull(condition);
        Assert.assertEquals(40d, condition.getThreshold(), 0);
        Assert.assertEquals(RateCondition.Operator.LT, condition.getOperator());
        Assert.assertEquals(1, condition.getDuration());
        Assert.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assert.assertEquals(60 * 1000, condition.getWindowTime());
        Assert.assertEquals(20, condition.getSampleSize());

        // Check comparison
        Assert.assertNotNull(condition.getComparison());
        Assert.assertEquals(ThresholdCondition.class, condition.getComparison().getClass());
        Assert.assertEquals("latency", ((ThresholdCondition)condition.getComparison()).getProperty());
        Assert.assertEquals(ThresholdCondition.Operator.GTE, ((ThresholdCondition)condition.getComparison()).getOperator());
        Assert.assertEquals((Double) 500d, ((ThresholdCondition)condition.getComparison()).getThreshold());

        // Check projection
        Assert.assertNotNull(condition.getProjections());
        Assert.assertEquals(1, condition.getProjections().size());
        Assert.assertEquals("api", ((PropertyProjection) condition.getProjections().get(0)).getProperty());
    }

    /**
     * This rule is testing that minimum sample size is set to 1 if not defined.
     */
    @Test
    public void shouldBuildRateCondition_defaultSampleSize() {
        RateCondition condition = RateCondition
                .of(ThresholdCondition.greaterThanOrEquals("latency", 500d).build())
                .duration(1, TimeUnit.MINUTES)
                .lowerThan(40d)
                .projection(Projections.property("api"))
                .build();

        // Check condition
        Assert.assertNotNull(condition);
        Assert.assertEquals(40d, condition.getThreshold(), 0);
        Assert.assertEquals(RateCondition.Operator.LT, condition.getOperator());
        Assert.assertEquals(1, condition.getDuration());
        Assert.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assert.assertEquals(60 * 1000, condition.getWindowTime());
        Assert.assertEquals(1, condition.getSampleSize());
    }
}
