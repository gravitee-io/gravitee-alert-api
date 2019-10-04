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

import java.util.List;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class ThresholdCondition extends AbstractCondition implements Filter {

    public enum Operator {
        LT, LTE, GTE, GT
    }

    private final String property;

    private final Operator operator;

    private final Double threshold;

    @JsonCreator
    private ThresholdCondition(
            @JsonProperty(value = "property", required = true) String property,
            @JsonProperty(value = "operator", required = true) Operator operator,
            @JsonProperty(value = "threshold", required = true) Double threshold) {
        super(Type.THRESHOLD);

        this.property = property;
        this.operator = operator;
        this.threshold = threshold;
    }

    public static FilterBuilder lowerThan(String property, Double threshold) {
        return new FilterBuilder(property, Operator.LT, threshold);
    }

    public static FilterBuilder lowerThanOrEquals(String property, Double threshold) {
        return new FilterBuilder(property, Operator.LTE, threshold);
    }

    public static FilterBuilder greaterThan(String property, Double threshold) {
        return new FilterBuilder(property, Operator.GT, threshold);
    }

    public static FilterBuilder greaterThanOrEquals(String property, Double threshold) {
        return new FilterBuilder(property, Operator.GTE, threshold);
    }

    public static class FilterBuilder {

        private final String property;
        private final Operator operator;
        private final Double threshold;

        FilterBuilder(String property, Operator operator, Double threshold) {
            this.property = property;
            this.operator = operator;
            this.threshold = threshold;
        }

        public ThresholdCondition build() {
            return new ThresholdCondition(property, operator, threshold);
        }
    }

    public String getProperty() {
        return property;
    }

    public Operator getOperator() {
        return operator;
    }

    public Double getThreshold() {
        return threshold;
    }
}
