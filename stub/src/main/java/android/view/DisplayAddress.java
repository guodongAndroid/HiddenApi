package android.view;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * Created by guodongAndroid on 2025/8/7
 */
@RequiresApi(Build.VERSION_CODES.S)
public abstract class DisplayAddress implements Parcelable {

    @NonNull
    public static Physical fromPhysicalDisplayId(long physicalDisplayId) {
        return new Physical(physicalDisplayId);
    }

    @NonNull
    public static Physical fromPortAndModel(int port, Long model) {
        return new Physical(port, model);
    }

    @NonNull
    public static Network fromMacAddress(String macAddress) {
        return new Network(macAddress);
    }

    public static final class Physical extends DisplayAddress {

        private Physical(long physicalDisplayId) {
            throw new RuntimeException();
        }

        private Physical(int port, Long model) {
            throw new RuntimeException();
        }

        public long getPhysicalDisplayId() {
            throw new RuntimeException();
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            throw new RuntimeException();
        }

        public static final @NonNull Parcelable.Creator<Physical> CREATOR =
                new Parcelable.Creator<Physical>() {
                    @Override
                    public Physical createFromParcel(Parcel in) {
                        return new Physical(in.readLong());
                    }

                    @Override
                    public Physical[] newArray(int size) {
                        return new Physical[size];
                    }
                };
    }

    public static final class Network extends DisplayAddress {

        @Override
        public void writeToParcel(Parcel out, int flags) {
            throw new RuntimeException();
        }

        private Network(String macAddress) {
            throw new RuntimeException();
        }

        public static final @NonNull Parcelable.Creator<Network> CREATOR =
                new Parcelable.Creator<Network>() {
                    @Override
                    public Network createFromParcel(Parcel in) {
                        return new Network(in.readString());
                    }

                    @Override
                    public Network[] newArray(int size) {
                        return new Network[size];
                    }
                };
    }

    @Override
    public int describeContents() {
        throw new RuntimeException();
    }
}
