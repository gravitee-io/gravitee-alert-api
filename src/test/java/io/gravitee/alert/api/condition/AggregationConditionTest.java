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
package io.gravitee.alert.api.condition;

import io.gravitee.alert.api.condition.projection.Projections;
import io.gravitee.alert.api.condition.projection.PropertyProjection;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The aggregation function condition is based on a time window and must be define with a duration.
 *
 * Unlike {@link StringCondition} or {@link ThresholdCondition}, this condition is based on multiple events within
 * the given timeframe.
 *
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class AggregationConditionTest {

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the average value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_average() {
        AggregationCondition condition = AggregationCondition
            .avg("latency")
            .duration(1, TimeUnit.MINUTES)
            .lowerThan(40d)
            .projection(Projections.property("api"))
            .build();

        Assertions.assertNotNull(condition);
        Assertions.assertEquals(AggregationCondition.Function.AVG, condition.getFunction());
        Assertions.assertEquals("latency", condition.getProperty());
        Assertions.assertEquals((Double) 40d, condition.getThreshold());
        Assertions.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assertions.assertEquals(1, condition.getDuration());
        Assertions.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assertions.assertEquals(60 * 1000, condition.getWindowTime());

        // Check projection
        Assertions.assertNotNull(condition.getProjections());
        Assertions.assertEquals(1, condition.getProjections().size());
        Assertions.assertEquals("api", ((PropertyProjection) condition.getProjections().get(0)).getProperty());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Count the number of occurrences based on events which match the given filters during the last minute and compare
     * the computed value to the given threshold (must be grater or equals to 40).
     */
    @Test
    public void shouldBuildThresholdCondition_count() {
        AggregationCondition condition = AggregationCondition.count().duration(1, TimeUnit.MINUTES).greaterThanOrEquals(40d).build();

        Assertions.assertNotNull(condition);
        Assertions.assertEquals(AggregationCondition.Function.COUNT, condition.getFunction());
        Assertions.assertNull(condition.getProperty());
        Assertions.assertEquals((Double) 40d, condition.getThreshold());
        Assertions.assertEquals(AggregationCondition.Operator.GTE, condition.getOperator());

        Assertions.assertEquals(1, condition.getDuration());
        Assertions.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assertions.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the min value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_minimum() {
        AggregationCondition condition = AggregationCondition.min("latency").duration(1, TimeUnit.MINUTES).lowerThan(40d).build();

        Assertions.assertNotNull(condition);
        Assertions.assertEquals(AggregationCondition.Function.MIN, condition.getFunction());
        Assertions.assertEquals("latency", condition.getProperty());
        Assertions.assertEquals((Double) 40d, condition.getThreshold());
        Assertions.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assertions.assertEquals(1, condition.getDuration());
        Assertions.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assertions.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the max value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_maximum() {
        AggregationCondition condition = AggregationCondition.max("latency").duration(1, TimeUnit.MINUTES).lowerThan(40d).build();

        Assertions.assertNotNull(condition);
        Assertions.assertEquals(AggregationCondition.Function.MAX, condition.getFunction());
        Assertions.assertEquals("latency", condition.getProperty());
        Assertions.assertEquals((Double) 40d, condition.getThreshold());
        Assertions.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assertions.assertEquals(1, condition.getDuration());
        Assertions.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assertions.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the percentile 50 value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_percentile50() {
        AggregationCondition condition = AggregationCondition.p50("latency").duration(1, TimeUnit.MINUTES).lowerThan(40d).build();

        Assertions.assertNotNull(condition);
        Assertions.assertEquals(AggregationCondition.Function.P50, condition.getFunction());
        Assertions.assertEquals("latency", condition.getProperty());
        Assertions.assertEquals((Double) 40d, condition.getThreshold());
        Assertions.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assertions.assertEquals(1, condition.getDuration());
        Assertions.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assertions.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the percentile 90 value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_percentile90() {
        AggregationCondition condition = AggregationCondition.p90("latency").duration(1, TimeUnit.MINUTES).lowerThan(40d).build();

        Assertions.assertNotNull(condition);
        Assertions.assertEquals(AggregationCondition.Function.P90, condition.getFunction());
        Assertions.assertEquals("latency", condition.getProperty());
        Assertions.assertEquals((Double) 40d, condition.getThreshold());
        Assertions.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assertions.assertEquals(1, condition.getDuration());
        Assertions.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assertions.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the percentile 95 value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_percentile95() {
        AggregationCondition condition = AggregationCondition.p95("latency").duration(1, TimeUnit.MINUTES).lowerThan(40d).build();

        Assertions.assertNotNull(condition);
        Assertions.assertEquals(AggregationCondition.Function.P95, condition.getFunction());
        Assertions.assertEquals("latency", condition.getProperty());
        Assertions.assertEquals((Double) 40d, condition.getThreshold());
        Assertions.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assertions.assertEquals(1, condition.getDuration());
        Assertions.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assertions.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the percentile 99 value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_percentile99() {
        AggregationCondition condition = AggregationCondition.p99("latency").duration(1, TimeUnit.MINUTES).lowerThan(40d).build();

        Assertions.assertNotNull(condition);
        Assertions.assertEquals(AggregationCondition.Function.P99, condition.getFunction());
        Assertions.assertEquals("latency", condition.getProperty());
        Assertions.assertEquals((Double) 40d, condition.getThreshold());
        Assertions.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assertions.assertEquals(1, condition.getDuration());
        Assertions.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assertions.assertEquals(60 * 1000, condition.getWindowTime());
    }
}
