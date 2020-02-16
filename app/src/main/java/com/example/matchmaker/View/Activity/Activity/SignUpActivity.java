package com.example.matchmaker.View.Activity.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matchmaker.R;
import com.example.matchmaker.View.Activity.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {
    private ImageView backBtn;
    private TextInputEditText nameET, emailET, contactET, passwordET;
    private Button signUpBT;
    private String userGender;
    private String userId;
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    protected static EditText userBirthDate;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;

    private static final String TAG = "SignUpActivity";

    public SignUpActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final FragmentManager fm = getSupportFragmentManager();
        initView();
        final RadioGroup rbg=(RadioGroup) findViewById(R.id.gender);
        userBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create the datePickerFragment
                AppCompatDialogFragment newFragment = new DatePickerFragment1();

                newFragment.show(fm, "datePicker");

            }
        });
        backBtn = findViewById(R.id.arrow);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = rbg.getCheckedRadioButtonId();

                RadioButton gender = findViewById(selected);
                userGender = (String) gender.getText();
                Toast.makeText(SignUpActivity.this, "Gender is "+ userGender, Toast.LENGTH_LONG).show();
                beforeSignIn();
            }
        });


    }


    private void beforeSignIn() {

        String nameUser = nameET.getText().toString().trim();
        String emailUser = emailET.getText().toString().trim();
        String contactUser = contactET.getText().toString().trim();
        String passwordUser = passwordET.getText().toString().trim();
        String dateOfBirth = userBirthDate.getText().toString().trim();

        if (nameUser.isEmpty()) {
            nameET.setError("Name is required!");
            nameET.requestFocus();
            return;
        }

        if (emailUser.isEmpty()) {
            emailET.setError("Email is required!");
            emailET.requestFocus();
            return;
        }

        if (contactUser.isEmpty()) {
            contactET.setError("Contact is required!");
            contactET.requestFocus();
            return;
        }

        if (passwordUser.isEmpty()) {
            passwordET.setError("Password is required!");
            passwordET.requestFocus();
            return;
        }

        if (dateOfBirth.isEmpty()) {
            userBirthDate.setError("Password is required!");
            userBirthDate.requestFocus();
            return;
        }
        if (passwordUser.length() < 6) {
            passwordET.setError("Password must be at least character!");
            passwordET.requestFocus();
            return;
        }

        if (!emailUser.matches(EMAIL_PATTERN)) {
            emailET.setError("Invalid email");
            emailET.requestFocus();
            return;
        }

        signUp(nameUser, emailUser, dateOfBirth, contactUser, passwordUser, userGender);

    }

    private void signUp(final String nameUser, final String emailUser, final String dateOfBirth, final String contactUser,
                        final String passwordUser, final String userGender) {
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(emailUser, passwordUser)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            userId = firebaseAuth.getCurrentUser().getUid();
                            User user = new User(userId, nameUser, emailUser, dateOfBirth, contactUser, userGender);
                            DatabaseReference userRef = databaseReference.child("User").child(userGender).child(userId);
                            userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Successfully Sign up!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                    }
                                }
                            });
                        }
                    }
                });


    }

    private void initView() {

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        backBtn = findViewById(R.id.arrow);
        userBirthDate = findViewById(R.id.dateET);
        nameET = findViewById(R.id.nameTextInputEditText);
        emailET = findViewById(R.id.emailTextInputEditText);
        contactET = findViewById(R.id.contactTextInputEditText);
        passwordET = findViewById(R.id.passwordTextInputEditText);
        signUpBT = findViewById(R.id.signUpBTN);
        progressBar = findViewById(R.id.progressBar);
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    //DatePickerMethods
    @SuppressLint("ValidFragment")
    public static class DatePickerFragment1 extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the chosen date
            userBirthDate = getActivity().findViewById(R.id.dateET);
           /* int actualMonth = month+1; // Because month index start from zero
            // Display the unformatted date to TextView
            tvDate.setText("Year : " + year + ", Month : " + actualMonth + ", Day : " + day + "\n\n");*/

            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            // Format the date using style medium and UK locale
            DateFormat df_medium_uk = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
            String df_medium_uk_str = df_medium_uk.format(chosenDate);
            // Display the formatted date
            userBirthDate.setText(df_medium_uk_str);
        }
    }
    //End of DatePickerMethods
}
