package ru.marslab.marsnotes.data;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.marslab.marsnotes.data.model.NoteFirestore;
import ru.marslab.marsnotes.domain.Callback;
import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.model.NoteCategory;
import ru.marslab.marsnotes.domain.model.NoteColor;


@RequiresApi(api = Build.VERSION_CODES.N)
public class RepositoryImplFirestore implements Repository {

    private final String COLLECTION_NOTES = "marsNotes";
    private final String COLLECTION_NOTES_CATEGORY = "noteCategories";
    private final String CATEGORY_NAME = "name";
    public static final int NO_ITEM_INDEX = -1;


    private List<NoteCategory> categories = null;
    private List<Note> notes = null;


    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        if (notes == null) {
            fireStore.collection(COLLECTION_NOTES)
                    .get()
                    .addOnSuccessListener(command -> {
                        notes = new ArrayList<>();
                        command.getDocuments().forEach(documentSnapshot ->
                                {
                                    NoteFirestore newNote = documentSnapshot.toObject(NoteFirestore.class);
                                    if (newNote != null) {
                                        notes.add(newNote.toNote(documentSnapshot.getId()));
                                    }
                                }
                        );
                        callback.onSuccess(notes);
                    });
        } else {
            callback.onSuccess(notes);
        }
    }

    @Override
    public void getCategories(Callback<List<NoteCategory>> callback) {
        if (categories == null) {
            fireStore.collection(COLLECTION_NOTES_CATEGORY)
                    .get()
                    .addOnCompleteListener(task -> {
                        List<NoteCategory> result = new ArrayList<>();
                        QuerySnapshot resultDocuments = task.getResult();
                        if (resultDocuments != null) {
                            for (DocumentSnapshot document : resultDocuments.getDocuments()) {
                                result.add(new NoteCategory(
                                        Integer.parseInt(document.getId()),
                                        document.getString(CATEGORY_NAME)
                                ));
                            }
                        }
                        categories = result;
                        callback.onSuccess(categories);
                    });
        }
        callback.onSuccess(categories);
    }

    @Override
    public void getCategoryNames(Callback<List<String>> callback) {
        if (categories == null) {
            fireStore.collection(COLLECTION_NOTES_CATEGORY)
                    .get()
                    .addOnCompleteListener(task -> {
                        List<String> result = new ArrayList<>();
                        QuerySnapshot resultDocuments = task.getResult();
                        if (resultDocuments != null) {
                            for (DocumentSnapshot document : resultDocuments.getDocuments()) {
                                result.add(document.getString(CATEGORY_NAME));
                            }
                        }
                        callback.onSuccess(result);
                    });
        }
        callback.onSuccess(categories.stream().map(NoteCategory::getCategoryName).collect(Collectors.toList()));

    }

    @Override
    public void getCategory(int categoryId, Callback<NoteCategory> callback) {
        if (categories == null) {
            fireStore.collection(COLLECTION_NOTES_CATEGORY)
                    .get()
                    .addOnCompleteListener(task -> {
                        QuerySnapshot resultDocuments = task.getResult();
                        if (resultDocuments != null) {
                            for (DocumentSnapshot document : resultDocuments.getDocuments()) {
                                if (document.getId().equals(String.valueOf(categoryId))) {
                                    callback.onSuccess(new NoteCategory(
                                            categoryId,
                                            document.getString(CATEGORY_NAME)
                                    ));
                                }
                            }
                        }
                    });
        } else {
            for (NoteCategory category : categories) {
                if (category.getCategoryId() == categoryId) {
                    callback.onSuccess(category);
                }
            }
        }

    }

    @Override
    public void getCategoryByName(String categoryName, Callback<NoteCategory> callback) {
        if (categories == null) {
            fireStore.collection(COLLECTION_NOTES_CATEGORY)
                    .get()
                    .addOnCompleteListener(task -> {
                        QuerySnapshot resultDocuments = task.getResult();
                        if (resultDocuments != null) {
                            for (DocumentSnapshot document : resultDocuments.getDocuments()) {
                                if (Objects.equals(document.getString(CATEGORY_NAME), String.valueOf(categoryName))) {
                                    callback.onSuccess(new NoteCategory(
                                            Integer.parseInt(document.getId()),
                                            categoryName
                                    ));
                                }
                            }
                        }
                    });
        } else {
            for (NoteCategory category : categories) {
                if (category.getCategoryName().equals(categoryName)) {
                    callback.onSuccess(category);
                }
            }
        }
    }

    @Override
    public void deleteNote(int deleteIndex, Callback<Note> callback) {
        Note removeNote = notes.remove(deleteIndex);
        fireStore.collection(COLLECTION_NOTES)
                .document(removeNote.getId())
                .delete()
                .addOnSuccessListener(command -> callback.onSuccess(removeNote))
                .addOnFailureListener(command -> notes.add(deleteIndex, removeNote));

    }

    @Override
    public void deleteNote(Note note, Callback<Boolean> callback) {
        fireStore.collection(COLLECTION_NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(command -> callback.onSuccess(notes.remove(note)));
    }

    @Override
    public void addNote(Note note, Callback<Integer> callback) {
        fireStore.collection(COLLECTION_NOTES)
                .add(note.toFirestore())
                .addOnSuccessListener(command -> {
                    if (notes.add(note)) {
                        callback.onSuccess(notes.size() - 1);
                    } else {
                        callback.onSuccess(NO_ITEM_INDEX);
                    }
                });
    }

    @Override
    public void modifyNote(@NonNull Note note, @Nullable String title, @Nullable String description, @Nullable
            Date date, @Nullable NoteCategory category, @Nullable NoteColor color, Callback<Note> callback) {
        NoteFirestore noteFirestore = note.toFirestore();
        if (title != null) {
            noteFirestore.setTitle(title);
        }
        if (description != null) {
            noteFirestore.setDescription(description);
        }
        if (date != null) {
            noteFirestore.setDate(date);
        }
        if (category != null) {
            noteFirestore.setCategory(category.getCategoryId());
        }
        if (color != null) {
            noteFirestore.setColor(color.name());
        }
        fireStore.collection(COLLECTION_NOTES)
                .document(note.getId())
                .set(noteFirestore)
                .addOnSuccessListener(command -> {
                    int replaceIndex = notes.indexOf(note);
                    notes.remove(note);
                    notes.add(replaceIndex, noteFirestore.toNote(note.getId()));
                    callback.onSuccess(notes.get(replaceIndex));
                });

    }
}
