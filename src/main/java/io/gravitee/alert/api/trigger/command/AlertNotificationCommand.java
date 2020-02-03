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
package io.gravitee.alert.api.trigger.command;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class AlertNotificationCommand extends AbstractCommand {

    private final Alert alert;

    public AlertNotificationCommand(Alert alert) {
        super(alert.trigger);

        this.alert = alert;
    }

    public Alert getAlert() {
        return alert;
    }

    public static class Alert {
        private final String trigger;

        private final long timestamp;

        public Alert(String trigger, long timestamp) {
            this.trigger = trigger;
            this.timestamp = timestamp;
        }

        public String getTrigger() {
            return trigger;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
