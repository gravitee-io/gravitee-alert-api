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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class ThresholdRangeCondition extends AbstractCondition implements Filter {

    public enum Operator {
        INCLUSIVE,
        EXCLUSIVE,
    }

    private final String property;

    private final Operator operatorLow;

    private final Double thresholdLow;

    private final Operator operatorHigh;

    private final Double thresholdHigh;

    @JsonCreator
    private ThresholdRangeCondition(
        @JsonProperty(value = "property", required = true) String property,
        @JsonProperty(value = "operatorLow", required = true) Operator operatorLow,
        @JsonProperty(value = "thresholdLow", required = true) Double thresholdLow,
        @JsonProperty(value = "operatorHigh", required = true) Operator operatorHigh,
        @JsonProperty(value = "thresholdHigh", required = true) Double thresholdHigh
    ) {
        super(Condition.Type.THRESHOLD_RANGE);
        this.property = property;
        this.operatorLow = operatorLow;
        this.thresholdLow = thresholdLow;
        this.operatorHigh = operatorHigh;
        this.thresholdHigh = thresholdHigh;
    }

    public static FilterBuilder between(String property, Double thresholdLow, Double thresholdHigh) {
        return new FilterBuilder(property, Operator.INCLUSIVE, thresholdLow, Operator.INCLUSIVE, thresholdHigh);
    }

    public static FilterBuilder between(
        String property,
        Operator operatorLow,
        Double thresholdLow,
        Operator operatorHigh,
        Double thresholdHigh
    ) {
        return new FilterBuilder(property, operatorLow, thresholdLow, operatorHigh, thresholdHigh);
    }

    public static class FilterBuilder {

        private final String property;
        private final Operator operatorLow;
        private final Double thresholdLow;
        private final Operator operatorHigh;
        private final Double thresholdHigh;

        FilterBuilder(String property, Operator operatorLow, Double thresholdLow, Operator operatorHigh, Double thresholdHigh) {
            this.property = property;
            this.operatorLow = operatorLow;
            this.thresholdLow = thresholdLow;
            this.operatorHigh = operatorHigh;
            this.thresholdHigh = thresholdHigh;
        }

        public ThresholdRangeCondition build() {
            return new ThresholdRangeCondition(property, operatorLow, thresholdLow, operatorHigh, thresholdHigh);
        }
    }

    public String getProperty() {
        return property;
    }

    public Operator getOperatorLow() {
        return operatorLow;
    }

    public Double getThresholdLow() {
        return thresholdLow;
    }

    public Operator getOperatorHigh() {
        return operatorHigh;
    }

    public Double getThresholdHigh() {
        return thresholdHigh;
    }
}
