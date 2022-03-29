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

/**
 * Threshold condition is based on a single-event value.
 *
 *  @author David BRASSELY (david.brassely at graviteesource.com)
 *  @author GraviteeSource Team
 */
public class ThresholdConditionTest {

    /**
     * Check that the latency for a given event is strictly lower than 40.
     */
    @Test
    public void shouldBuildThresholdCondition_lowerThan() {
        ThresholdCondition condition = ThresholdCondition.lowerThan("latency", 40d).build();

        Assert.assertNotNull(condition);
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(ThresholdCondition.Operator.LT, condition.getOperator());
    }

    /**
     * Check that the latency for a given event is lower than or equals to 40.
     */
    @Test
    public void shouldBuildThresholdCondition_lowerThanOrEquals() {
        ThresholdCondition condition = ThresholdCondition.lowerThanOrEquals("latency", 40d).build();

        Assert.assertNotNull(condition);
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(ThresholdCondition.Operator.LTE, condition.getOperator());
    }

    /**
     * Check that the latency for a given event is strictly greater than 40.
     */
    @Test
    public void shouldBuildThresholdCondition_greaterThan() {
        ThresholdCondition condition = ThresholdCondition.greaterThan("latency", 40d).build();

        Assert.assertNotNull(condition);
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(ThresholdCondition.Operator.GT, condition.getOperator());
    }

    /**
     * Check that the latency for a given event is greater than or equals to 40.
     */
    @Test
    public void shouldBuildThresholdCondition_greaterThanOrEquals() {
        ThresholdCondition condition = ThresholdCondition.greaterThanOrEquals("latency", 40d).build();

        Assert.assertNotNull(condition);
        Assert.assertEquals((Double) 40d, condition.getThreshold());
        Assert.assertEquals(ThresholdCondition.Operator.GTE, condition.getOperator());
    }
}
