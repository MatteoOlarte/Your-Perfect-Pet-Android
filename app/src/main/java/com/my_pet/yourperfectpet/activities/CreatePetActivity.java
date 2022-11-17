package com.my_pet.yourperfectpet.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.my_pet.yourperfectpet.App;
import com.my_pet.yourperfectpet.R;
import com.my_pet.yourperfectpet.entity.Pet;
import com.my_pet.yourperfectpet.util.Pets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CreatePetActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final ArrayList<EditText> editTexts = new ArrayList<>();
    private LinearProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);
        setSupportActionBar(findViewById(R.id.pet_activity_toolbar));

        var pictureUriWrapper = new Object() {
            Uri uri = null;
        };
        TextInputEditText inputName = findViewById(R.id.pet_activity_input_pet_name);
        TextInputEditText inputAge = findViewById(R.id.pet_activity_input_pet_age);
        TextInputEditText inputWeight = findViewById(R.id.pet_activity_input_pet_weight);
        AutoCompleteTextView inputType = findViewById(R.id.pet_activity_input_pet_type);
        TextInputEditText inputRace = findViewById(R.id.pet_activity_input_pet_race);
        TextInputEditText inputDescription = findViewById(R.id.pet_activity_input_pet_description);
        MaterialButton addImage = findViewById(R.id.pet_activity_btn_image);
        MaterialButton addPet = findViewById(R.id.pet_activity_btn_add);
        ImageView mainImg = findViewById(R.id.pet_activity_img_main);
        CheckBox isVaccinated = findViewById(R.id.pet_activity_checkbox_health1);
        List<String> petTypes = Arrays.asList(getResources().getStringArray(R.array.pets));

        ActivityResultLauncher<Intent> imagePick = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    var intent = result.getData();
                    if (intent != null) {
                        try {
                            var uri = intent.getData();
                            var bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                            pictureUriWrapper.uri = uri;
                            mainImg.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            Log.e("Image_picker", e.getMessage(), e);
                        }
                    }
                }
        );

        progressIndicator = findViewById(R.id.pet_activity_progress_indicator);
        editTexts.add(inputName);
        editTexts.add(inputAge);
        editTexts.add(inputWeight);
        editTexts.add(inputType);
        editTexts.add(inputRace);
        inputType.setAdapter(new ArrayAdapter<>(this, R.layout.list_layout, petTypes));

        addImage.setOnClickListener(view -> {
            var intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            imagePick.launch(intent);
        });

        addPet.setOnClickListener(view -> {
            progressIndicator.show();




            if (App.notEmptyFields(editTexts) && pictureUriWrapper.uri != null) {
                Log.e("Image_picker", pictureUriWrapper.uri.toString());

                var id = UUID.randomUUID().toString();
                var name = inputName.getText().toString();
                var age = Integer.parseInt(inputAge.getText().toString());
                var weight = Float.parseFloat(inputWeight.getText().toString());
                var type = Pets.of(this, inputType.getText().toString());
                var race = inputRace.getText().toString();
                var description = inputDescription.getText().toString();

                if (type == Pets.CAT) {
                    Pet cat = new Pet(
                            auth.getCurrentUser().getUid(),
                            name,
                            Pets.CAT,
                            age,
                            weight,
                            isVaccinated.isChecked(),
                            null,
                            description
                    );

                    storage.getReference().child("pets_img").child(id).putFile(pictureUriWrapper.uri).addOnSuccessListener(t -> {
                        cat.setId(id);

                        db.collection("pets").add(cat).addOnSuccessListener(t2 -> {
                            progressIndicator.hide();
                            progressIndicator.setVisibility(View.GONE);

                            var errorDialog = new MaterialAlertDialogBuilder(this);
                            errorDialog.setTitle("Pet was added");
                            errorDialog.setCancelable(false);
                            errorDialog.setPositiveButton(R.string.text_accept, (dialog, which) -> {
                                progressIndicator.hide();
                                progressIndicator.setVisibility(View.GONE);
                                startActivity(new Intent(this, MainActivity.class));
                                dialog.dismiss();
                            });
                            errorDialog.show();

                        }).addOnFailureListener(exception -> {
                            var errorDialog = new MaterialAlertDialogBuilder(this);
                            errorDialog.setTitle(R.string.text_error);
                            errorDialog.setMessage(exception.getMessage());
                            errorDialog.setCancelable(false);
                            errorDialog.setPositiveButton(R.string.text_accept, (dialog, which) -> {
                                progressIndicator.hide();
                                progressIndicator.setVisibility(View.GONE);
                                dialog.dismiss();
                            });
                            errorDialog.show();
                        });

                    }).addOnFailureListener(exception -> {
                        var errorDialog = new MaterialAlertDialogBuilder(this);
                        errorDialog.setTitle(R.string.text_error);
                        errorDialog.setMessage(exception.getMessage());
                        errorDialog.setCancelable(false);
                        errorDialog.setPositiveButton(R.string.text_accept, (dialog, which) -> {
                            progressIndicator.hide();
                            progressIndicator.setVisibility(View.GONE);
                            dialog.dismiss();
                        });
                        errorDialog.show();
                    });

                } else {
                    Pet dog = new Pet(
                            auth.getCurrentUser().getUid(),
                            name,
                            Pets.DOG,
                            age,
                            weight,
                            isVaccinated.isChecked(),
                            null,
                            description
                    );

                    storage.getReference().child("pets_img").child(id).putFile(pictureUriWrapper.uri).addOnSuccessListener(t -> {
                        dog.setId(id);
                        dog.setDescription(dog.getDescription());

                        db.collection("pets").add(dog).addOnSuccessListener(t2 -> {
                            progressIndicator.hide();
                            progressIndicator.setVisibility(View.GONE);

                            var errorDialog = new MaterialAlertDialogBuilder(this);
                            errorDialog.setTitle("Pet was added");
                            errorDialog.setCancelable(false);
                            errorDialog.setPositiveButton(R.string.text_accept, (dialog, which) -> {
                                progressIndicator.hide();
                                progressIndicator.setVisibility(View.GONE);
                                startActivity(new Intent(this, MainActivity.class));
                                dialog.dismiss();
                            });
                            errorDialog.show();

                        }).addOnFailureListener(exception -> {
                            var errorDialog = new MaterialAlertDialogBuilder(this);
                            errorDialog.setTitle(R.string.text_error);
                            errorDialog.setMessage(exception.getMessage());
                            errorDialog.setCancelable(false);
                            errorDialog.setPositiveButton(R.string.text_accept, (dialog, which) -> {
                                progressIndicator.hide();
                                progressIndicator.setVisibility(View.GONE);
                                dialog.dismiss();
                            });
                            errorDialog.show();
                        });

                    }).addOnFailureListener(exception -> {
                        var errorDialog = new MaterialAlertDialogBuilder(this);
                        errorDialog.setTitle(R.string.text_error);
                        errorDialog.setMessage(exception.getMessage());
                        errorDialog.setCancelable(false);
                        errorDialog.setPositiveButton(R.string.text_accept, (dialog, which) -> {
                            progressIndicator.hide();
                            progressIndicator.setVisibility(View.GONE);
                            dialog.dismiss();
                        });
                        errorDialog.show();
                    });
                }

            } else {
                Toast.makeText(this, R.string.msg_empty_fields, Toast.LENGTH_SHORT).show();
                progressIndicator.hide();
                progressIndicator.setVisibility(View.GONE);
            }
        });
    }
}