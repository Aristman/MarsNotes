package ru.marslab.marsnotes.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Note implements Parcelable {

    private final int id;
    private String title;
    private String description;
    private Date date;
    private Calendar calendar = Calendar.getInstance();
    private int categoryId;
    private NoteColor color;
    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private final SimpleDateFormat timeFormat =
            new SimpleDateFormat("HH:mm", Locale.getDefault());

    public Note(int id, String title, String description, Date created, int categoryId, NoteColor color) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = created;
        calendar.setTime(created);
        this.categoryId = categoryId;
        this.color = color;
    }

    public Note(int id, String title, String description) {
        this(
                id,
                title,
                description,
                Calendar.getInstance().getTime(),
                NoteCategory.ID_NO_CATEGORY,
                NoteColor.YELLOW
        );
    }

    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        categoryId = in.readInt();
        date.setTime(in.readLong());
        color = NoteColor.valueOf(in.readString());
        calendar.setTime(date);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
        calendar.setTime(date);
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public String getTime() {
        return timeFormat.format(date);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendarDate(int year, int month, int day) {
        calendar.set(year, month, day);
        date = calendar.getTime();
    }

    public void setCalendarTime(int hour, int minute) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hour, minute);
        date = calendar.getTime();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategory(int categoryId) {
        this.categoryId = categoryId;
    }

    public NoteColor getColor() {
        return color;
    }

    public void setColor(NoteColor color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(categoryId);
        dest.writeLong(date.getTime());
        dest.writeString(color.name());
    }
}
