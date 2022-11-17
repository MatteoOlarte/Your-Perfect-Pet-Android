package com.my_pet.yourperfectpet.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.my_pet.yourperfectpet.App;
import com.my_pet.yourperfectpet.R;
import com.my_pet.yourperfectpet.entity.AppUser;
import com.my_pet.yourperfectpet.entity.BasicUser;
import com.my_pet.yourperfectpet.entity.Pet;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class PublicPetsAdapter extends FirestoreRecyclerAdapter<Pet, PublicPetsAdapter.PublicPetsHolder> {

    public PublicPetsAdapter(@NonNull FirestoreRecyclerOptions<Pet> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PublicPetsHolder holder, int position, @NonNull Pet model) {
        holder.name.setText(model.getName());
        holder.race.setText(model.getRace().getValue(holder.imageView.getContext()));
        holder.description.setText(model.getDescription());

        if (model.isVaccinesApplied())
            holder.vaccines.setText("Completamente vacunado");
        else
            holder.vaccines.setText("No vacunado");

        FirebaseFirestore.getInstance().collection("users").document(model.getOwnerId()).get().addOnSuccessListener(
                t -> {
                    if (t.getData() != null) {
                        AppUser appUser = t.toObject(AppUser.class);
                        holder.owner.setText("Dueño: " + appUser.getUserName());
                    }
                });
    }

    @NonNull
    @Override
    public PublicPetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pet_public_view, parent, false);
        return new PublicPetsHolder(view);
    }

    class PublicPetsHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, race, description, vaccines, owner;

        public PublicPetsHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.pet_public_view_image);
            race = itemView.findViewById(R.id.pet_public_view_race);
            name = itemView.findViewById(R.id.pet_public_view_name);
            description = itemView.findViewById(R.id.pet_public_view_description);
            vaccines = itemView.findViewById(R.id.pet_public_view_applied_vaccines);
            owner = itemView.findViewById(R.id.pet_public_view_applied_owner);
        }
    }
}
