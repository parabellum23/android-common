package org.solovyev.android.network;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * User: serso
 * Date: 7/26/12
 * Time: 5:33 PM
 */
final class NetworkDataImpl implements NetworkData {

    @Nonnull
    private NetworkState state = NetworkState.UNKNOWN;

    @Nullable
    private String reason;

    private boolean failover = false;

    @Nullable
    private NetworkInfo networkInfo;

    @Nullable
    private NetworkInfo otherNetworkInfo;

    private NetworkDataImpl() {
    }

    @Nonnull
    static NetworkData newUnknownNetworkData() {
        return new NetworkDataImpl();
    }

    @Nonnull
    static NetworkData fromIntent(@Nonnull Intent intent) {
        assert ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction());

        final NetworkDataImpl result = new NetworkDataImpl();

        boolean connected = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

        if (connected) {
            result.state = NetworkState.CONNECTED;
        } else {
            result.state = NetworkState.NOT_CONNECTED;
        }

        result.networkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        result.otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

        result.reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
        result.failover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

        return result;
    }

    @Nonnull
    @Override
    public NetworkState getState() {
        return this.state;
    }

    @Nullable
    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public boolean isFailover() {
        return this.failover;
    }

    @Nullable
    @Override
    public NetworkInfo getNetworkInfo() {
        return this.networkInfo;
    }

    @Nullable
    @Override
    public NetworkInfo getOtherNetworkInfo() {
        return this.otherNetworkInfo;
    }

    @Override
    public String toString() {
        return "NetworkDataImpl{" +
                "state=" + state +
                ", reason='" + reason + '\'' +
                ", failover=" + failover +
                ", networkInfo=" + networkInfo +
                ", otherNetworkInfo=" + otherNetworkInfo +
                '}';
    }
}
