package android.net;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * Created by guodongAndroid on 2025/8/12
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
public class EthernetNetworkManagementException extends RuntimeException implements Parcelable {

    public EthernetNetworkManagementException(@NonNull final String errorMessage) {
        super(errorMessage);
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(getMessage());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    public static final Parcelable.Creator<EthernetNetworkManagementException> CREATOR =
            new Parcelable.Creator<EthernetNetworkManagementException>() {
                @Override
                public EthernetNetworkManagementException[] newArray(int size) {
                    return new EthernetNetworkManagementException[size];
                }

                @Override
                public EthernetNetworkManagementException createFromParcel(@NonNull Parcel source) {
                    return new EthernetNetworkManagementException(source.readString());
                }
            };
}
