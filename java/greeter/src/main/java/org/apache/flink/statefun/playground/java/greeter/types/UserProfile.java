package org.apache.flink.statefun.playground.java.greeter.types;

import java.util.Objects;

public class UserProfile {

    private String name;
    private long lastSeenDeltaMs;
    private String loginLocation;
    private int seenCount;

    public UserProfile(String name, long lastSeendDeltaMs, String loginLocation, int seenCount) {
        this.name = name;
        this.lastSeenDeltaMs = lastSeendDeltaMs;
        this.loginLocation = loginLocation;
        this.seenCount = seenCount;
    }

    public UserProfile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastSeenDeltaMs() {
        return lastSeenDeltaMs;
    }

    public void setLastSeenDeltaMs(long lastSeenDeltaMs) {
        this.lastSeenDeltaMs = lastSeenDeltaMs;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public int getSeenCount() {
        return seenCount;
    }

    public void setSeenCount(int seenCount) {
        this.seenCount = seenCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return lastSeenDeltaMs == that.lastSeenDeltaMs && seenCount == that.seenCount && Objects.equals(name, that.name) && Objects.equals(loginLocation, that.loginLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastSeenDeltaMs, loginLocation, seenCount);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserProfile{");
        sb.append("name='").append(name).append('\'');
        sb.append(", lastSeendDeltaMs=").append(lastSeenDeltaMs);
        sb.append(", loginLocation='").append(loginLocation).append('\'');
        sb.append(", seenCount=").append(seenCount);
        sb.append('}');
        return sb.toString();
    }
}
