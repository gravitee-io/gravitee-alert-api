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

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

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
                .build();

        Assert.assertNotNull(condition);
        Assert.assertEquals(AggregationCondition.Function.AVG, condition.getFunction());
        Assert.assertEquals("latency", condition.getProperty());
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assert.assertEquals(1, condition.getDuration());
        Assert.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assert.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Count the number of occurrences based on events which match the given filters during the last minute and compare
     * the computed value to the given threshold (must be grater or equals to 40).
     */
    @Test
    public void shouldBuildThresholdCondition_count() {
        AggregationCondition condition = AggregationCondition
                .count()
                .duration(1, TimeUnit.MINUTES)
                .greaterThanOrEquals(40d)
                .build();

        Assert.assertNotNull(condition);
        Assert.assertEquals(AggregationCondition.Function.COUNT, condition.getFunction());
        Assert.assertNull(condition.getProperty());
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(AggregationCondition.Operator.GTE, condition.getOperator());

        Assert.assertEquals(1, condition.getDuration());
        Assert.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assert.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the min value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_minimum() {
        AggregationCondition condition = AggregationCondition
                .min("latency")
                .duration(1, TimeUnit.MINUTES)
                .lowerThan(40d)
                .build();

        Assert.assertNotNull(condition);
        Assert.assertEquals(AggregationCondition.Function.MIN, condition.getFunction());
        Assert.assertEquals("latency", condition.getProperty());
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assert.assertEquals(1, condition.getDuration());
        Assert.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assert.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the max value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_maximum() {
        AggregationCondition condition = AggregationCondition
                .max("latency")
                .duration(1, TimeUnit.MINUTES)
                .lowerThan(40d)
                .build();

        Assert.assertNotNull(condition);
        Assert.assertEquals(AggregationCondition.Function.MAX, condition.getFunction());
        Assert.assertEquals("latency", condition.getProperty());
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assert.assertEquals(1, condition.getDuration());
        Assert.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assert.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the percentile 50 value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_percentile50() {
        AggregationCondition condition = AggregationCondition
                .p50("latency")
                .duration(1, TimeUnit.MINUTES)
                .lowerThan(40d)
                .build();

        Assert.assertNotNull(condition);
        Assert.assertEquals(AggregationCondition.Function.P50, condition.getFunction());
        Assert.assertEquals("latency", condition.getProperty());
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assert.assertEquals(1, condition.getDuration());
        Assert.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assert.assertEquals(60 * 1000, condition.getWindowTime());
    }


    /**
     * This rule is testing the following use-case:
     *
     * Calculate the percentile 90 value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_percentile90() {
        AggregationCondition condition = AggregationCondition
                .p90("latency")
                .duration(1, TimeUnit.MINUTES)
                .lowerThan(40d)
                .build();

        Assert.assertNotNull(condition);
        Assert.assertEquals(AggregationCondition.Function.P90, condition.getFunction());
        Assert.assertEquals("latency", condition.getProperty());
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assert.assertEquals(1, condition.getDuration());
        Assert.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assert.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the percentile 95 value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_percentile95() {
        AggregationCondition condition = AggregationCondition
                .p95("latency")
                .duration(1, TimeUnit.MINUTES)
                .lowerThan(40d)
                .build();

        Assert.assertNotNull(condition);
        Assert.assertEquals(AggregationCondition.Function.P95, condition.getFunction());
        Assert.assertEquals("latency", condition.getProperty());
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assert.assertEquals(1, condition.getDuration());
        Assert.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assert.assertEquals(60 * 1000, condition.getWindowTime());
    }

    /**
     * This rule is testing the following use-case:
     *
     * Calculate the percentile 99 value of "latency" based on events which match the given filters during the last minute
     * and compare the computed value to the given threshold (must be lower than 40).
     */
    @Test
    public void shouldBuildThresholdCondition_percentile99() {
        AggregationCondition condition = AggregationCondition
                .p99("latency")
                .duration(1, TimeUnit.MINUTES)
                .lowerThan(40d)
                .build();

        Assert.assertNotNull(condition);
        Assert.assertEquals(AggregationCondition.Function.P99, condition.getFunction());
        Assert.assertEquals("latency", condition.getProperty());
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(AggregationCondition.Operator.LT, condition.getOperator());

        Assert.assertEquals(1, condition.getDuration());
        Assert.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assert.assertEquals(60 * 1000, condition.getWindowTime());
    }
}
