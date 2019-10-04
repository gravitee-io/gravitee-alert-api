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
 * The frequency threshold condition is based on a time window and must be define with a duration.
 *
 * Unlike {@link StringCondition} or {@link ThresholdCondition}, this condition is based on multiple events within
 * a given time period.
 *
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class FrequencyConditionTest {

    /**
     * This rule is testing the following use-case:
     *
     * Count the number of occurrences based on events which match the given comparison during the last minute and compare
     * the computed value to the given threshold (must be grater or equals to 40).
     */
    @Test
    public void shouldBuildFrequencyCondition_count() {
        FrequencyCondition condition = FrequencyCondition
                .of(ThresholdCondition.greaterThan("status", 400d).build())
                .duration(1, TimeUnit.MINUTES)
                .greaterThanOrEquals(40)
                .build();

        Assert.assertNotNull(condition);
        Assert.assertEquals(Integer.valueOf(40), condition.getThreshold());
        Assert.assertEquals(FrequencyCondition.Operator.GTE, condition.getOperator());
        Assert.assertEquals(1, condition.getDuration());
        Assert.assertEquals(TimeUnit.MINUTES, condition.getTimeUnit());
        Assert.assertEquals(60 * 1000, condition.getWindowTime());

        Assert.assertNotNull(condition.getComparison());
        Assert.assertEquals(ThresholdCondition.class, condition.getComparison().getClass());
        Assert.assertEquals("status", ((ThresholdCondition)condition.getComparison()).getProperty());
        Assert.assertEquals(ThresholdCondition.Operator.GT, ((ThresholdCondition)condition.getComparison()).getOperator());
        Assert.assertEquals((Double) 400d, ((ThresholdCondition)condition.getComparison()).getThreshold());
    }
}
