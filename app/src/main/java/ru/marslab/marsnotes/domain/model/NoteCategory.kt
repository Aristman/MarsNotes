package ru.marslab.marsnotes.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteCategory implements Parcelable {
    static final String NO_CATEGORY = "No category";
    static final int ID_NO_CATEGORY = 0;

    protected NoteCategory(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<NoteCategory> CREATOR = new Creator<NoteCategory>() {
        @Override
        public NoteCategory createFromParcel(Parcel in) {
            return new NoteCategory(in);
        }

        @Override
        public NoteCategory[] newArray(int size) {
            return new NoteCategory[size];
        }
    };

    public static NoteCategory getInstance() {
        return new NoteCategory(ID_NO_CATEGORY, NO_CATEGORY);
    }

    private final int id;
    private final String name;

    public NoteCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getCategoryId() {
        return id;
    }

    public String getCategoryName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
