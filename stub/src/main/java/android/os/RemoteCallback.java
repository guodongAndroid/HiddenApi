package android.os;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RemoteCallback implements Parcelable {

    public interface OnResultListener {
        void onResult(@Nullable Bundle result);
    }

    public RemoteCallback(OnResultListener listener) {
        throw new RuntimeException();
    }

    public RemoteCallback(@NonNull OnResultListener listener, @Nullable Handler handler) {
        throw new RuntimeException();
    }

    RemoteCallback(Parcel parcel) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
    }

    public static final Creator<RemoteCallback> CREATOR
            = new Creator<RemoteCallback>() {
        public RemoteCallback createFromParcel(Parcel parcel) {
            return new RemoteCallback(parcel);
        }

        public RemoteCallback[] newArray(int size) {
            return new RemoteCallback[size];
        }
    };
}
