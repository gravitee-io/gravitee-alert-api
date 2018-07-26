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
package io.gravitee.alert.api.trigger;

import io.gravitee.notifier.api.Notification;

import java.io.Serializable;
import java.util.*;

/**
 * @author Azize ELAMRANI (azize.elamrani at graviteesource.com)
 * @author GraviteeSource Team
 */
public class Trigger implements Serializable {

    private static final long serialVersionUID = 19504799855563L;

    private String id;
    private String name;
    private String eventType;
    private Map<String, String> context;
    private List<String> scopeProperties;
    private String condition;
    private List<Notification> notifications;
    private List<Link> links;
    private Boolean enabled;
    private Boolean notifyOnce;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public List<String> getScopeProperties() {
        return scopeProperties;
    }

    public void setScopeProperties(List<String> scopeProperties) {
        this.scopeProperties = scopeProperties;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getNotifyOnce() {
        return notifyOnce;
    }

    public void setNotifyOnce(Boolean notifyOnce) {
        this.notifyOnce = notifyOnce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trigger)) return false;
        Trigger trigger = (Trigger) o;
        return Objects.equals(id, trigger.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Trigger{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", eventType='" + eventType + '\'' +
                ", context=" + context +
                ", scopeProperties=" + scopeProperties +
                ", condition='" + condition + '\'' +
                ", notifications=" + notifications +
                ", links=" + links +
                ", enabled=" + enabled +
                ", notifyOnce=" + notifyOnce +
                '}';
    }

    public static class Builder {
        private String id;
        private String name;
        private String eventType;
        private Map<String, String> context;
        private List<String> scopeProperties;
        private String condition;
        private List<Link> links = new ArrayList<>();
        private List<Notification> notifications = new ArrayList<>();
        private Boolean enabled;
        private Boolean notifyOnce;

        public Trigger.Builder id(String id) {
            this.id = id;
            return this;
        }

        public Trigger.Builder name(String name) {
            this.name = name;
            return this;
        }

        public Trigger.Builder eventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public Trigger.Builder context(String contextKey, String contextValue) {
            if (context == null) {
                context = new LinkedHashMap<>();
            }
            this.context.put(contextKey, contextValue);
            return this;
        }

        public Trigger.Builder scopeProperty(String scopeProperty) {
            if (scopeProperties == null) {
                scopeProperties = new ArrayList<>();
            }
            this.scopeProperties.add(scopeProperty);
            return this;
        }

        public Trigger.Builder condition(String condition) {
            this.condition = condition;
            return this;
        }

        public Trigger.Builder link(String rel, String href) {
            final Link link = new Link();
            link.setRel(rel);
            link.setHref(href);
            links.add(link);
            return this;
        }

        public Trigger.Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Trigger.Builder notifyOnce(boolean notifyOnce) {
            this.notifyOnce = notifyOnce;
            return this;
        }

        public Trigger.Builder notification(String destination, String type, String configuration) {
            final Notification notification = new Notification();
            notification.setDestination(destination);
            notification.setType(type);
            notification.setConfiguration(configuration);
            notifications.add(notification);
            return this;
        }

        public Trigger build() {
            final Trigger alertTrigger = new Trigger();
            alertTrigger.setId(id);
            alertTrigger.setName(name);
            alertTrigger.setEventType(eventType);
            alertTrigger.setContext(context);
            alertTrigger.setScopeProperties(scopeProperties);
            alertTrigger.setCondition(condition);
            alertTrigger.setLinks(links);
            alertTrigger.setNotifications(notifications);
            alertTrigger.setEnabled(enabled);
            alertTrigger.setNotifyOnce(notifyOnce);
            return alertTrigger;
        }
    }
}
