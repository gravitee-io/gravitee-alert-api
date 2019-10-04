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
 *  String compare condition is based on a single-event value.
 *
 *  @author David BRASSELY (david.brassely at graviteesource.com)
 *  @author GraviteeSource Team
 */
public class StringCompareConditionTest {

    /**
     * Check that the latency for a given event is strictly lower than 40.
     */
    @Test
    public void shouldBuildStringCondition_lowerThan() {
        StringCompareCondition condition = StringCompareCondition
                .equals("latency", "latency2").build();

        Assert.assertNotNull(condition);
        Assert.assertEquals("latency", condition.getProperty());
        Assert.assertEquals("latency2", condition.getProperty2());
        Assert.assertEquals(StringCompareCondition.Operator.EQUALS, condition.getOperator());
    }
}
