package com.jw.studio.geckodevmaster.session;

import android.os.Parcel;

import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoSessionSettings;
import org.mozilla.geckoview.WebExtension;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

public class TabSession extends GeckoSession {
    private String title;
    private String uri;
    public WebExtension.Action action;

    public TabSession() { super(); }

    public TabSession(GeckoSessionSettings settings) {
        super(settings);
    }

    public String getTitle() {
        return title == null || title.length() == 0 ? "about:blank" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public void loadUri(@NonNull String uri) {
        super.loadUri(uri);
        this.uri = uri;
    }

    public void onLocationChange(@NonNull String uri) {
        this.uri = uri;
    }

    @Override // Parcelable
    @UiThread
    public void writeToParcel(final Parcel out, final int flags) {
        super.writeToParcel(out, flags);
        out.writeString(title);
        out.writeString(uri);
    }

    // AIDL code may call readFromParcel even though it's not part of Parcelable.
    @UiThread
    public void readFromParcel(final @NonNull Parcel source) {
        super.readFromParcel(source);
        title = source.readString();
        uri = source.readString();
    }

    public static final Creator<GeckoSession> CREATOR = new Creator<GeckoSession>() {
        @Override
        @UiThread
        public TabSession createFromParcel(final Parcel in) {
            final TabSession session = new TabSession();
            session.readFromParcel(in);
            return session;
        }

        @Override
        @UiThread
        public TabSession[] newArray(final int size) {
            return new TabSession[size];
        }
    };
}
