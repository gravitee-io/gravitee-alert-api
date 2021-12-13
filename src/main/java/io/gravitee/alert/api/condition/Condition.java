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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.gravitee.alert.api.condition.projection.Projection;

import java.io.Serializable;
import java.util.List;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringCondition.class, name = "STRING"),
        @JsonSubTypes.Type(value = StringCondition.class, name = "string"),
        @JsonSubTypes.Type(value = ThresholdCondition.class, name = "THRESHOLD"),
        @JsonSubTypes.Type(value = ThresholdCondition.class, name = "threshold"),
        @JsonSubTypes.Type(value = ThresholdRangeCondition.class, name = "THRESHOLD_RANGE"),
        @JsonSubTypes.Type(value = ThresholdRangeCondition.class, name = "threshold_range"),
        @JsonSubTypes.Type(value = AggregationCondition.class, name = "AGGREGATION"),
        @JsonSubTypes.Type(value = AggregationCondition.class, name = "aggregation"),
        @JsonSubTypes.Type(value = RateCondition.class, name = "RATE"),
        @JsonSubTypes.Type(value = RateCondition.class, name = "rate"),
        @JsonSubTypes.Type(value = CompareCondition.class, name = "COMPARE"),
        @JsonSubTypes.Type(value = CompareCondition.class, name = "compare"),
        @JsonSubTypes.Type(value = StringCompareCondition.class, name = "STRING_COMPARE"),
        @JsonSubTypes.Type(value = StringCompareCondition.class, name = "string_compare"),
        @JsonSubTypes.Type(value = MissingDataCondition.class, name = "MISSING_DATA"),
        @JsonSubTypes.Type(value = MissingDataCondition.class, name = "missing_data")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Condition extends Serializable {

    List<Projection> getProjections();

    enum Type {
        STRING, THRESHOLD, THRESHOLD_RANGE, RATE, FREQUENCY, AGGREGATION, COMPARE, STRING_COMPARE, MISSING_DATA
    }

    Type getType();
}
