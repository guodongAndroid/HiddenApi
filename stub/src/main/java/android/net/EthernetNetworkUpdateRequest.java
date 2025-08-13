package android.net;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by guodongAndroid on 2025/8/12
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
public class EthernetNetworkUpdateRequest implements Parcelable {
    private EthernetNetworkUpdateRequest(@NonNull final Parcel source) {
    }

    @Nullable
    public IpConfigurationHidden getIpConfiguration() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public NetworkCapabilities getNetworkCapabilities() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    public static final Parcelable.Creator<EthernetNetworkUpdateRequest> CREATOR =
            new Parcelable.Creator<EthernetNetworkUpdateRequest>() {
                @Override
                public EthernetNetworkUpdateRequest[] newArray(int size) {
                    return new EthernetNetworkUpdateRequest[size];
                }

                @Override
                public EthernetNetworkUpdateRequest createFromParcel(@NonNull Parcel source) {
                    return new EthernetNetworkUpdateRequest(source);
                }
            };

    public static final class Builder {
        public Builder() {
        }

        public Builder(@NonNull final EthernetNetworkUpdateRequest request) {
        }

        public Builder setIpConfiguration(@Nullable final IpConfiguration ipConfig) {
            throw new UnsupportedOperationException();
        }

        public Builder setNetworkCapabilities(@Nullable final NetworkCapabilities nc) {
            throw new UnsupportedOperationException();
        }

        @NonNull
        public EthernetNetworkUpdateRequest build() {
            throw new UnsupportedOperationException();
        }
    }
}
