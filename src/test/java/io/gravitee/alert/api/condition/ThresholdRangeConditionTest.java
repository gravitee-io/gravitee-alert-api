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
 * Threshold range condition is based on a single-event value.
 *
 *  @author David BRASSELY (david.brassely at graviteesource.com)
 *  @author GraviteeSource Team
 */
public class ThresholdRangeConditionTest {

    /**
     * Check that the latency for a given event is between 100 and 500 (both inclusive).
     */
    @Test
    public void shouldBuildThresholdCondition_betweenInclusive() {
        ThresholdRangeCondition condition = ThresholdRangeCondition.between("latency", 100d, 500d).build();

        Assert.assertNotNull(condition);
        Assert.assertEquals((Double) 100d, condition.getThresholdLow());
        Assert.assertEquals((Double) 500d, condition.getThresholdHigh());
        Assert.assertEquals(ThresholdRangeCondition.Operator.INCLUSIVE, condition.getOperatorLow());
        Assert.assertEquals(ThresholdRangeCondition.Operator.INCLUSIVE, condition.getOperatorHigh());
    }

    /**
     * Check that the latency for a given event is between 100 and 500 (both exclusive).
     */
    @Test
    public void shouldBuildThresholdCondition_betweenExclusive() {
        ThresholdRangeCondition condition = ThresholdRangeCondition
            .between("latency", ThresholdRangeCondition.Operator.EXCLUSIVE, 100d, ThresholdRangeCondition.Operator.EXCLUSIVE, 500d)
            .build();

        Assert.assertNotNull(condition);
        Assert.assertEquals((Double) 100d, condition.getThresholdLow());
        Assert.assertEquals((Double) 500d, condition.getThresholdHigh());
        Assert.assertEquals(ThresholdRangeCondition.Operator.EXCLUSIVE, condition.getOperatorLow());
        Assert.assertEquals(ThresholdRangeCondition.Operator.EXCLUSIVE, condition.getOperatorHigh());
    }
}
