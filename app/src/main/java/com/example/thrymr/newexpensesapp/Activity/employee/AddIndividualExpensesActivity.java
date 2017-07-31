package com.example.thrymr.newexpensesapp.Activity.employee;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.thrymr.newexpensesapp.BuildConfig;
import com.example.thrymr.newexpensesapp.R;
import com.example.thrymr.newexpensesapp.Views.CustomEditText;
import com.example.thrymr.newexpensesapp.Views.CustomFontTextView;
import com.example.thrymr.newexpensesapp.models.IndividualExpenses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

public class AddIndividualExpensesActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int CODE_FOR_CAMERA = 1;
    private static final int CODE_FOR_GALLARY = 2;
    private Spinner categorySpinner;
    private CustomEditText selectDateEdtv, detailsEdtv, totalAmountEdtv;
    private ImageView imagePic;
    private CustomFontTextView saveButton;
    private CustomEditText selectImg;
    public String[] expenses = {"Choose your Expenses", "Travel", "Food", "Health", "Furniture", "Grocery", "Gadgets", "Stationary", "OtherThings"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Expenses");
        setValues();
        getEditiableDate();


    }

    private void getEditiableDate() {
        Intent getEditableDataIntent = getIntent();
        String dateOfBill = getEditableDataIntent.getStringExtra("dateOfBill");
        String details = getEditableDataIntent.getStringExtra("details");
        String amount = getEditableDataIntent.getStringExtra("billAmount");
        selectDateEdtv.setText(dateOfBill);
        detailsEdtv.setText(details);
        totalAmountEdtv.setText(amount);


    }

    private void setValues() {
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        selectDateEdtv = (CustomEditText) findViewById(R.id.select_date_edtv);
        selectDateEdtv.setInputType(InputType.TYPE_NULL);
        detailsEdtv = (CustomEditText) findViewById(R.id.details_edtv);
        totalAmountEdtv = (CustomEditText) findViewById(R.id.total_amount_edtv);
        imagePic = (ImageView) findViewById(R.id.image_pic);
        saveButton = (CustomFontTextView) findViewById(R.id.upload_data_btn);
        selectImg= (CustomEditText) findViewById(R.id.upload_bill_et);
        selectImg.setRawInputType(InputType.TYPE_NULL);
        selectImg.setFocusable(true);
        saveButton.setOnClickListener(this);
        imagePic.setOnClickListener(this);
        selectImg.setOnClickListener(this);
        selectDateEdtv.setOnClickListener(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenses);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerArrayAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_date_edtv:
                setDatepicker();
                break;
            case R.id.image_pic:
                //showingDailogImage();
                break;
            case R.id.upload_data_btn:
                finish();
                break;
            case R.id.upload_bill_et:
                selectImage();
        }

    }


    public void setDatepicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                selectDateEdtv.setText(dayOfMonth + "/"
                        + (monthOfYear + 1) + "/" + year);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    //select image from mobile camera and gallery
    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".share", f));
                    startActivityForResult(intent, CODE_FOR_CAMERA);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, CODE_FOR_GALLARY);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        imagePic.setVisibility(View.VISIBLE);
        if (resultCode == RESULT_OK) {
            if (requestCode == CODE_FOR_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    imagePic.setImageBitmap(bitmap);

                    String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 300, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CODE_FOR_GALLARY) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                imagePic.setImageBitmap(thumbnail);
            }


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    /*public void showingDailogImage() {
        //inside onPostExecute()
        AlertDialog.Builder builder = new AlertDialog.Builder(AddIndividualExpensesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View imageLayoutView = inflater.inflate(R.layout.alert_image_view, null);
        ImageView image = (ImageView) imageLayoutView.findViewById(R.id.alert_image_view);
        //an URI example
        image.setImageURI();
        builder.setView(imageLayoutView)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.create();
        builder.show();
    }*/
}
